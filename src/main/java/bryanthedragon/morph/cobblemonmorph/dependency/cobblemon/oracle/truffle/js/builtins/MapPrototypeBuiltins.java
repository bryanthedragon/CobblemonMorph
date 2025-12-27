package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.helper.JSCollectionsNormalizeNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSMap;
import com.oracle.truffle.js.runtime.builtins.JSMapObject;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSHashMap;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;

public final class MapPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<MapPrototypeBuiltins.MapPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new MapPrototypeBuiltins();

   protected MapPrototypeBuiltins() {
      super(JSMap.PROTOTYPE_NAME, MapPrototypeBuiltins.MapPrototype.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, MapPrototypeBuiltins.MapPrototype builtinEnum) {
      switch (builtinEnum) {
         case clear:
            return MapPrototypeBuiltinsFactory.JSMapClearNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case delete:
            return MapPrototypeBuiltinsFactory.JSMapDeleteNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case set:
            return MapPrototypeBuiltinsFactory.JSMapSetNodeGen.create(context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context));
         case get:
            return MapPrototypeBuiltinsFactory.JSMapGetNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case has:
            return MapPrototypeBuiltinsFactory.JSMapHasNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case forEach:
            return MapPrototypeBuiltinsFactory.JSMapForEachNodeGen.create(context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context));
         case keys:
            return MapPrototypeBuiltinsFactory.CreateMapIteratorNodeGen.create(context, builtin, 1, args().withThis().createArgumentNodes(context));
         case values:
            return MapPrototypeBuiltinsFactory.CreateMapIteratorNodeGen.create(context, builtin, 2, args().withThis().createArgumentNodes(context));
         case entries:
            return MapPrototypeBuiltinsFactory.CreateMapIteratorNodeGen.create(context, builtin, 3, args().withThis().createArgumentNodes(context));
         default:
            return null;
      }
   }

   @ImportStatic({JSConfig.class, JSRuntime.class, MapPrototypeBuiltins.JSMapOperation.class})
   public abstract static class CreateMapIteratorNode extends JSBuiltinNode {
      private final int iterationKind;
      @Node.Child
      private CreateObjectNode.CreateObjectWithPrototypeNode createObjectNode;
      @Node.Child
      private PropertySetNode setNextIndexNode;
      @Node.Child
      private PropertySetNode setIteratedObjectNode;
      @Node.Child
      private PropertySetNode setIterationKindNode;

      public CreateMapIteratorNode(JSContext context, JSBuiltin builtin, int iterationKind) {
         super(context, builtin);
         this.iterationKind = iterationKind;
         this.createObjectNode = CreateObjectNode.createOrdinaryWithPrototype(context);
         this.setIteratedObjectNode = PropertySetNode.createSetHidden(JSRuntime.ITERATED_OBJECT_ID, context);
         this.setNextIndexNode = PropertySetNode.createSetHidden(JSRuntime.ITERATOR_NEXT_INDEX, context);
         this.setIterationKindNode = PropertySetNode.createSetHidden(JSMap.MAP_ITERATION_KIND_ID, context);
      }

      @Specialization
      protected JSDynamicObject doMap(JSMapObject map) {
         JSDynamicObject iterator = this.createObjectNode.execute(this.getRealm().getMapIteratorPrototype());
         this.setIteratedObjectNode.setValue(iterator, map);
         this.setNextIndexNode.setValue(iterator, JSMap.getInternalMap(map).getEntries());
         this.setIterationKindNode.setValueInt(iterator, this.iterationKind);
         return iterator;
      }

      @Specialization(guards = {"!isJSMap(map)", "isForeignHash(map, mapLib)"})
      protected JSDynamicObject doForeignMap(
         Object map,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib,
         @Cached("createSetHidden(ENUMERATE_ITERATOR_ID, getContext())") PropertySetNode setEnumerateIteratorNode
      ) {
         Object iterator;
         try {
            if (this.iterationKind == 1) {
               iterator = mapLib.getHashKeysIterator(map);
            } else if (this.iterationKind == 2) {
               iterator = mapLib.getHashValuesIterator(map);
            } else {
               assert this.iterationKind == 3;

               iterator = mapLib.getHashEntriesIterator(map);
            }
         } catch (UnsupportedMessageException var6) {
            throw Errors.createTypeErrorInteropException(map, var6, "get hash iterator", null);
         }

         JSDynamicObject iteratorObj = JSOrdinary.create(this.getContext(), this.getContext().getEnumerateIteratorFactory(), this.getRealm());
         setEnumerateIteratorNode.setValue(iteratorObj, iterator);
         return iteratorObj;
      }

      @Specialization(guards = {"!isJSMap(thisObj)", "!isForeignHash(thisObj, mapLib)"})
      protected JSDynamicObject doIncompatibleReceiver(
         Object thisObj, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib
      ) {
         throw Errors.createTypeErrorMapExpected();
      }
   }

   @ImportStatic({JSConfig.class, MapPrototypeBuiltins.JSMapOperation.class})
   public abstract static class JSMapClearNode extends JSBuiltinNode {
      public JSMapClearNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected static JSDynamicObject doMap(JSMapObject thisObj) {
         JSMap.getInternalMap(thisObj).clear();
         return Undefined.instance;
      }

      @Specialization(guards = {"!isJSMap(thisObj)", "isForeignHash(thisObj, mapLib)"})
      protected JSDynamicObject doForeignMap(
         Object thisObj,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib,
         @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary iteratorLib,
         @Cached BranchProfile growProfile
      ) {
         try {
            Object hashEntriesIterator = mapLib.getHashKeysIterator(thisObj);
            SimpleArrayList<Object> keys = SimpleArrayList.create(mapLib.getHashSize(thisObj));

            while (true) {
               try {
                  Object nextKey = iteratorLib.getIteratorNextElement(hashEntriesIterator);
                  keys.add(nextKey, growProfile);
               } catch (StopIterationException var12) {
                  Object[] e = keys.toArray();
                  int var8 = e.length;
                  int var9 = 0;

                  for (; var9 < var8; var9++) {
                     Object key = e[var9];

                     try {
                        mapLib.removeHashEntry(thisObj, key);
                     } catch (UnknownKeyException var13) {
                        continue;
                     }

                     TruffleSafepoint.poll(this);
                  }
                  break;
               }

               TruffleSafepoint.poll(this);
            }
         } catch (UnsupportedMessageException var14) {
            throw Errors.createTypeErrorInteropException(thisObj, var14, "clear", null);
         }

         return Undefined.instance;
      }

      @Specialization(guards = {"!isJSMap(thisObj)", "!isForeignHash(thisObj, mapLib)"})
      protected static JSDynamicObject notMap(Object thisObj, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib) {
         throw Errors.createTypeErrorMapExpected();
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSMapDeleteNode extends MapPrototypeBuiltins.JSMapOperation {
      public JSMapDeleteNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean doMap(JSMapObject thisObj, Object key) {
         Object normalizedKey = this.normalize(key);
         return JSMap.getInternalMap(thisObj).remove(normalizedKey);
      }

      @Specialization(guards = {"!isJSMap(thisObj)", "isForeignHash(thisObj, mapLib)"})
      protected boolean doForeignMap(Object thisObj, Object key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib) {
         Object normalizedKey = this.normalize(key);

         try {
            mapLib.removeHashEntry(thisObj, normalizedKey);
            return true;
         } catch (UnknownKeyException var6) {
            return false;
         } catch (UnsupportedMessageException var7) {
            throw Errors.createTypeErrorInteropException(thisObj, var7, "removeHashEntry", null);
         }
      }

      @Specialization(guards = {"!isJSMap(thisObj)", "!isForeignHash(thisObj, mapLib)"})
      protected static boolean notMap(Object thisObj, Object key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib) {
         throw Errors.createTypeErrorMapExpected();
      }
   }

   @ImportStatic({JSConfig.class, MapPrototypeBuiltins.JSMapOperation.class})
   public abstract static class JSMapForEachNode extends JSBuiltinNode {
      public JSMapForEachNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isCallable.executeBoolean(callback)", limit = "1")
      protected Object doMap(
         JSMapObject thisObj,
         Object callback,
         Object thisArg,
         @Cached @Cached.Shared("isCallable") IsCallableNode isCallable,
         @Cached("createCall()") @Cached.Shared("callNode") JSFunctionCallNode callNode
      ) {
         JSHashMap map = JSMap.getInternalMap(thisObj);
         JSHashMap.Cursor cursor = map.getEntries();

         while (cursor.advance()) {
            Object value = cursor.getValue();
            Object key = cursor.getKey();
            callNode.executeCall(JSArguments.create(thisArg, callback, value, key, thisObj));
         }

         return Undefined.instance;
      }

      @Specialization(guards = {"!isJSMap(thisObj)", "isForeignHash(thisObj, mapLib)", "isCallable.executeBoolean(callback)"}, limit = "1")
      protected Object doForeignMap(
         Object thisObj,
         Object callback,
         Object thisArg,
         @Cached @Cached.Shared("isCallable") IsCallableNode isCallable,
         @Cached("createCall()") @Cached.Shared("callNode") JSFunctionCallNode callNode,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib,
         @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary iteratorLib,
         @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary entryLib
      ) {
         try {
            Object hashEntriesIterator = mapLib.getHashEntriesIterator(thisObj);

            while (true) {
               try {
                  Object nextEntry = iteratorLib.getIteratorNextElement(hashEntriesIterator);
                  Object key = entryLib.readArrayElement(nextEntry, 0L);
                  Object value = entryLib.readArrayElement(nextEntry, 1L);
                  callNode.executeCall(JSArguments.create(thisArg, callback, value, key, thisObj));
               } catch (StopIterationException var13) {
                  return Undefined.instance;
               }
            }
         } catch (InvalidArrayIndexException | UnsupportedMessageException var14) {
            throw Errors.createTypeErrorInteropException(thisObj, var14, "forEach", null);
         }
      }

      @Specialization(guards = {"isJSMap(thisObj) || isForeignHash(thisObj, mapLib)", "!isCallable.executeBoolean(callback)"}, limit = "1")
      protected static Object invalidCallback(
         Object thisObj,
         Object callback,
         Object thisArg,
         @Cached @Cached.Shared("isCallable") IsCallableNode isCallable,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib
      ) {
         throw Errors.createTypeErrorCallableExpected();
      }

      @Specialization(guards = {"!isJSMap(thisObj)", "!isForeignHash(thisObj, mapLib)"})
      protected static Object notMap(
         Object thisObj, Object callback, Object thisArg, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib
      ) {
         throw Errors.createTypeErrorMapExpected();
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSMapGetNode extends MapPrototypeBuiltins.JSMapOperation {
      public JSMapGetNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object doMap(JSMapObject thisObj, Object key) {
         Object normalizedKey = this.normalize(key);
         Object value = JSMap.getInternalMap(thisObj).get(normalizedKey);
         return JSRuntime.nullToUndefined(value);
      }

      @Specialization(guards = {"!isJSMap(thisObj)", "isForeignHash(thisObj, mapLib)"})
      protected Object doForeignMap(
         Object thisObj,
         Object key,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib,
         @Cached ImportValueNode importValue
      ) {
         Object normalizedKey = this.normalize(key);

         try {
            return importValue.executeWithTarget(mapLib.readHashValueOrDefault(thisObj, normalizedKey, Undefined.instance));
         } catch (UnsupportedMessageException var7) {
            throw Errors.createTypeErrorInteropException(thisObj, var7, "readHashValue", null);
         }
      }

      @Specialization(guards = {"!isJSMap(thisObj)", "!isForeignHash(thisObj, mapLib)"})
      protected static Object notMap(Object thisObj, Object key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib) {
         throw Errors.createTypeErrorMapExpected();
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSMapHasNode extends MapPrototypeBuiltins.JSMapOperation {
      public JSMapHasNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean doMap(JSMapObject thisObj, Object key) {
         Object normalizedKey = this.normalize(key);
         return JSMap.getInternalMap(thisObj).has(normalizedKey);
      }

      @Specialization(guards = {"!isJSMap(thisObj)", "isForeignHash(thisObj, mapLib)"})
      protected Object doForeignMap(Object thisObj, Object key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib) {
         Object normalizedKey = this.normalize(key);
         return mapLib.isHashEntryReadable(thisObj, normalizedKey);
      }

      @Specialization(guards = {"!isJSMap(thisObj)", "!isForeignHash(thisObj, mapLib)"})
      protected static boolean notMap(Object thisObj, Object key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib) {
         throw Errors.createTypeErrorMapExpected();
      }
   }

   public abstract static class JSMapOperation extends JSBuiltinNode {
      @Node.Child
      private JSCollectionsNormalizeNode normalizeNode = JSCollectionsNormalizeNode.create();

      protected JSMapOperation(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected final Object normalize(Object value) {
         return this.normalizeNode.execute(value);
      }

      protected static boolean isForeignHash(Object value, InteropLibrary interopLibrary) {
         return interopLibrary.hasHashEntries(value) && !(value instanceof JSDynamicObject);
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSMapSetNode extends MapPrototypeBuiltins.JSMapOperation {
      public JSMapSetNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected JSDynamicObject doMap(JSMapObject thisObj, Object key, Object value) {
         Object normalizedKey = this.normalize(key);
         JSMap.getInternalMap(thisObj).put(normalizedKey, value);
         return thisObj;
      }

      @Specialization(guards = {"!isJSMap(thisObj)", "isForeignHash(thisObj, mapLib)"})
      protected Object doForeignMap(
         Object thisObj,
         Object key,
         Object value,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib,
         @Cached ExportValueNode exportValueNode
      ) {
         Object normalizedKey = this.normalize(key);
         Object exportedValue = exportValueNode.execute(value);

         try {
            mapLib.writeHashEntry(thisObj, normalizedKey, exportedValue);
            return thisObj;
         } catch (UnknownKeyException | UnsupportedTypeException | UnsupportedMessageException var9) {
            throw Errors.createTypeErrorInteropException(thisObj, var9, "writeHashEntry", null);
         }
      }

      @Specialization(guards = {"!isJSMap(thisObj)", "!isForeignHash(thisObj, mapLib)"})
      protected static JSDynamicObject notMap(
         Object thisObj, Object key, Object value, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("mapLib") InteropLibrary mapLib
      ) {
         throw Errors.createTypeErrorMapExpected();
      }
   }

   public static enum MapPrototype implements BuiltinEnum<MapPrototypeBuiltins.MapPrototype> {
      clear(0),
      delete(1),
      set(2),
      get(1),
      has(1),
      forEach(1),
      keys(0),
      values(0),
      entries(0);

      private final int length;

      private MapPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
