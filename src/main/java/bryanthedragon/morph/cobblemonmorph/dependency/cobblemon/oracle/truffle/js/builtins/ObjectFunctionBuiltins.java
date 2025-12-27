package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.ListGetNode;
import com.oracle.truffle.js.builtins.helper.ListSizeNode;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.access.FromPropertyDescriptorNode;
import com.oracle.truffle.js.nodes.access.GetIteratorBaseNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.IsExtensibleNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.IteratorCloseNode;
import com.oracle.truffle.js.nodes.access.IteratorStepNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNode;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.RequireObjectCoercibleNode;
import com.oracle.truffle.js.nodes.access.ToPropertyDescriptorNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import com.oracle.truffle.js.runtime.util.Pair;
import com.oracle.truffle.js.runtime.util.UnmodifiableArrayList;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public final class ObjectFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<ObjectFunctionBuiltins.ObjectFunction> {
   public static final JSBuiltinsContainer BUILTINS = new ObjectFunctionBuiltins();
   public static final JSBuiltinsContainer BUILTINS_NASHORN_COMPAT = new ObjectFunctionBuiltins.ObjectFunctionNashornCompatBuiltins();

   protected ObjectFunctionBuiltins() {
      super(JSOrdinary.CLASS_NAME, ObjectFunctionBuiltins.ObjectFunction.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ObjectFunctionBuiltins.ObjectFunction builtinEnum) {
      switch (builtinEnum) {
         case create:
            return ObjectFunctionBuiltinsFactory.ObjectCreateNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case defineProperties:
            return ObjectFunctionBuiltinsFactory.ObjectDefinePropertiesNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case defineProperty:
            return ObjectFunctionBuiltinsFactory.ObjectDefinePropertyNodeGen.create(context, builtin, args().fixedArgs(3).createArgumentNodes(context));
         case freeze:
            return ObjectFunctionBuiltinsFactory.ObjectSetIntegrityLevelNodeGen.create(context, builtin, true, args().fixedArgs(1).createArgumentNodes(context));
         case getOwnPropertyDescriptor:
            return ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorNodeGen.create(
               context, builtin, args().fixedArgs(2).createArgumentNodes(context)
            );
         case getOwnPropertyDescriptors:
            return ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.create(
               context, builtin, args().fixedArgs(1).createArgumentNodes(context)
            );
         case getOwnPropertyNames:
            return ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyNamesOrSymbolsNodeGen.create(
               context, builtin, false, args().fixedArgs(1).createArgumentNodes(context)
            );
         case getPrototypeOf:
            return ObjectFunctionBuiltinsFactory.ObjectGetPrototypeOfNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case isExtensible:
            return ObjectFunctionBuiltinsFactory.ObjectIsExtensibleNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case isFrozen:
            return ObjectFunctionBuiltinsFactory.ObjectTestIntegrityLevelNodeGen.create(
               context, builtin, true, args().fixedArgs(1).createArgumentNodes(context)
            );
         case isSealed:
            return ObjectFunctionBuiltinsFactory.ObjectTestIntegrityLevelNodeGen.create(
               context, builtin, false, args().fixedArgs(1).createArgumentNodes(context)
            );
         case keys:
            return ObjectFunctionBuiltinsFactory.ObjectKeysNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case preventExtensions:
            return ObjectFunctionBuiltinsFactory.ObjectPreventExtensionsNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case seal:
            return ObjectFunctionBuiltinsFactory.ObjectSetIntegrityLevelNodeGen.create(
               context, builtin, false, args().fixedArgs(1).createArgumentNodes(context)
            );
         case setPrototypeOf:
            return ObjectFunctionBuiltinsFactory.ObjectSetPrototypeOfNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case is:
            return ObjectFunctionBuiltinsFactory.ObjectIsNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case getOwnPropertySymbols:
            return ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyNamesOrSymbolsNodeGen.create(
               context, builtin, true, args().fixedArgs(1).createArgumentNodes(context)
            );
         case assign:
            return ObjectFunctionBuiltinsFactory.ObjectAssignNodeGen.create(context, builtin, args().fixedArgs(1).varArgs().createArgumentNodes(context));
         case values:
            return ObjectFunctionBuiltinsFactory.ObjectValuesOrEntriesNodeGen.create(context, builtin, false, args().fixedArgs(1).createArgumentNodes(context));
         case entries:
            return ObjectFunctionBuiltinsFactory.ObjectValuesOrEntriesNodeGen.create(context, builtin, true, args().fixedArgs(1).createArgumentNodes(context));
         case fromEntries:
            return ObjectFunctionBuiltinsFactory.ObjectFromEntriesNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case hasOwn:
            return ObjectFunctionBuiltinsFactory.ObjectHasOwnNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         default:
            return null;
      }
   }

   @ImportStatic(JSConfig.class)
   abstract static class AssignPropertiesNode extends JavaScriptBaseNode {
      protected final JSContext context;

      protected AssignPropertiesNode(JSContext context) {
         this.context = context;
      }

      abstract void executeVoid(Object to, Object from, WriteElementNode write);

      @Specialization(guards = "isJSObject(from)")
      protected static void copyPropertiesFromJSObject(
         Object to,
         JSDynamicObject from,
         WriteElementNode write,
         @Cached("create(context)") ReadElementNode read,
         @Cached("create(false)") JSGetOwnPropertyNode getOwnProperty,
         @Cached ListSizeNode listSize,
         @Cached ListGetNode listGet,
         @Cached JSClassProfile classProfile
      ) {
         List<Object> ownPropertyKeys = JSObject.ownPropertyKeys(from, classProfile);
         int size = listSize.execute(ownPropertyKeys);

         for (int i = 0; i < size; i++) {
            Object nextKey = listGet.execute(ownPropertyKeys, i);

            assert JSRuntime.isPropertyKey(nextKey);

            PropertyDescriptor desc = getOwnProperty.execute(from, nextKey);
            if (desc != null && desc.getEnumerable()) {
               Object propValue = read.executeWithTargetAndIndex(from, nextKey);
               write.executeWithTargetAndIndexAndValue(to, nextKey, propValue);
            }
         }
      }

      @Specialization(guards = "!isJSObject(from)", limit = "InteropLibraryLimit")
      protected final void doObject(
         Object to,
         Object from,
         WriteElementNode write,
         @CachedLibrary("from") InteropLibrary fromInterop,
         @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary keysInterop,
         @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary stringInterop
      ) {
         if (!fromInterop.isNull(from)) {
            try {
               Object members = fromInterop.getMembers(from);
               long length = JSInteropUtil.getArraySize(members, keysInterop, this);

               for (long i = 0L; i < length; i++) {
                  Object key = keysInterop.readArrayElement(members, i);
                  String stringKey = Strings.interopAsString(stringInterop, key);
                  Object value = fromInterop.readMember(from, stringKey);
                  write.executeWithTargetAndIndexAndValue(to, Strings.fromJavaString(stringKey), value);
               }
            } catch (InvalidArrayIndexException | UnknownIdentifierException | UnsupportedMessageException var15) {
               throw Errors.createTypeErrorInteropException(from, var15, "CopyDataProperties", this);
            }
         }
      }
   }

   public abstract static class ObjectAssignNode extends JSBuiltinNode {
      protected static final boolean STRICT = true;

      public ObjectAssignNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object assign(
         Object target,
         Object[] sources,
         @Cached("createToObject(getContext())") JSToObjectNode toObjectNode,
         @Cached("create(getContext(), STRICT)") WriteElementNode write,
         @Cached("create(getContext())") ObjectFunctionBuiltins.AssignPropertiesNode assignProperties
      ) {
         Object to = toObjectNode.execute(target);
         if (sources.length == 0) {
            return to;
         } else {
            for (Object o : sources) {
               if (!JSRuntime.isNullOrUndefined(o)) {
                  Object from = toObjectNode.execute(o);
                  assignProperties.executeVoid(to, from, write);
               }
            }

            return to;
         }
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class ObjectBindPropertiesNode extends ObjectPrototypeBuiltins.ObjectOperation {
      @Node.Child
      private EnumerableOwnPropertyNamesNode enumerableOwnPropertyNamesNode;
      private final JSClassProfile sourceProfile = JSClassProfile.create();
      private final JSClassProfile targetProfile = JSClassProfile.create();

      public ObjectBindPropertiesNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "!isJSObject(target)")
      protected JSDynamicObject bindPropertiesInvalidTarget(Object target, Object source) {
         throw Errors.createTypeErrorNotAnObject(target, this);
      }

      @Specialization(guards = {"isJSObject(target)", "isJSDynamicObject(source)"})
      protected JSDynamicObject bindPropertiesDynamicObject(JSDynamicObject target, JSDynamicObject source) {
         JSDynamicObject sourceObject = this.toJSObject(source);
         boolean extensible = JSObject.isExtensible(target, this.targetProfile);
         JSClass sourceClass = this.sourceProfile.getJSClass(sourceObject);
         UnmodifiableArrayList<? extends Object> keys = this.enumerableOwnPropertyNames(sourceObject);
         int length = keys.size();

         for (int i = 0; i < length; i++) {
            Object key = keys.get(i);
            if (!JSObject.hasOwnProperty(target, key, this.targetProfile)) {
               if (!extensible) {
                  throw Errors.createTypeErrorNotExtensible(target, key);
               }

               PropertyDescriptor desc = JSObject.getOwnProperty(sourceObject, key, this.sourceProfile);
               if (desc.isAccessorDescriptor()) {
                  JSObject.defineOwnProperty(target, key, desc);
               } else {
                  JSObjectUtil.defineProxyProperty(
                     target, key, new ObjectFunctionBuiltins.ObjectBindPropertiesNode.BoundProperty(source, key, sourceClass), desc.getFlags()
                  );
               }
            }
         }

         return target;
      }

      @Specialization(guards = "isJSObject(target)")
      protected JSDynamicObject bindProperties(JSDynamicObject target, Symbol source) {
         return this.bindPropertiesDynamicObject(target, this.toJSObject(source));
      }

      @Specialization(guards = "isJSObject(target)")
      protected JSDynamicObject bindProperties(JSDynamicObject target, TruffleString source) {
         return this.bindPropertiesDynamicObject(target, this.toJSObject(source));
      }

      @Specialization(guards = "isJSObject(target)")
      protected JSDynamicObject bindProperties(JSDynamicObject target, SafeInteger source) {
         return this.bindPropertiesDynamicObject(target, this.toJSObject(source));
      }

      @Specialization(guards = "isJSObject(target)")
      protected JSDynamicObject bindProperties(JSDynamicObject target, BigInt source) {
         return this.bindPropertiesDynamicObject(target, this.toJSObject(source));
      }

      @Specialization(guards = {"isJSObject(target)", "!isTruffleObject(source)"})
      protected JSDynamicObject bindProperties(JSDynamicObject target, Object source) {
         return this.bindPropertiesDynamicObject(target, this.toJSObject(source));
      }

      @Specialization(guards = {"isJSObject(target)", "isForeignObject(source)"}, limit = "InteropLibraryLimit")
      protected JSDynamicObject bindProperties(
         JSDynamicObject target,
         Object source,
         @CachedLibrary("source") InteropLibrary interop,
         @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary members
      ) {
         if (interop.hasMembers(source)) {
            try {
               boolean extensible = JSObject.isExtensible(target, this.targetProfile);
               boolean hostObject = this.getRealm().getEnv().isHostObject(source);
               Object keysObj = interop.getMembers(source);
               long size = members.getArraySize(keysObj);

               for (int i = 0; i < size; i++) {
                  Object key = members.readArrayElement(keysObj, i);
                  String stringKey = Strings.interopAsString(key);
                  TruffleString tStringKey = Strings.interopAsTruffleString(key);
                  if (!JSObject.hasOwnProperty(target, tStringKey, this.targetProfile)) {
                     if (!extensible) {
                        throw Errors.createTypeErrorNotExtensible(target, key);
                     }

                     JSObjectUtil.defineProxyProperty(
                        target,
                        tStringKey,
                        new ObjectFunctionBuiltins.ObjectBindPropertiesNode.ForeignBoundProperty(source, stringKey),
                        JSAttributes.getDefault()
                     );
                  }

                  if (hostObject) {
                     String beanProperty;
                     if (stringKey.length() > 3
                        && (stringKey.charAt(0) == 's' || stringKey.charAt(0) == 'g')
                        && stringKey.charAt(1) == 'e'
                        && stringKey.charAt(2) == 't'
                        && Boundaries.characterIsUpperCase(stringKey.charAt(3))) {
                        beanProperty = beanProperty(stringKey, 3);
                     } else {
                        if (stringKey.length() <= 2
                           || stringKey.charAt(0) != 'i'
                           || stringKey.charAt(1) != 's'
                           || !Boundaries.characterIsUpperCase(stringKey.charAt(2))) {
                           continue;
                        }

                        beanProperty = beanProperty(stringKey, 2);
                     }

                     TruffleString tStringBeanProperty = Strings.fromJavaString(beanProperty);
                     if (!JSObject.hasOwnProperty(target, tStringBeanProperty, this.targetProfile) && !interop.isMemberExisting(source, beanProperty)) {
                        String getKey = beanAccessor("get", beanProperty);
                        String getter;
                        if (interop.isMemberExisting(source, getKey)) {
                           getter = getKey;
                        } else {
                           String isKey = beanAccessor("is", beanProperty);
                           if (interop.isMemberExisting(source, isKey)) {
                              getter = isKey;
                           } else {
                              getter = null;
                           }
                        }

                        String setKey = beanAccessor("set", beanProperty);
                        String setter = interop.isMemberExisting(source, setKey) ? setKey : null;
                        JSObjectUtil.defineProxyProperty(
                           target,
                           tStringBeanProperty,
                           new ObjectFunctionBuiltins.ObjectBindPropertiesNode.ForeignBoundBeanProperty(source, getter, setter),
                           JSAttributes.getDefault()
                        );
                     }
                  }
               }
            } catch (InvalidArrayIndexException | UnsupportedMessageException var20) {
            }

            return target;
         } else {
            throw Errors.createTypeErrorNotAnObject(target, this);
         }
      }

      private UnmodifiableArrayList<? extends Object> enumerableOwnPropertyNames(JSDynamicObject obj) {
         if (this.enumerableOwnPropertyNamesNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.enumerableOwnPropertyNamesNode = this.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
         }

         return this.enumerableOwnPropertyNamesNode.execute(obj);
      }

      @CompilerDirectives.TruffleBoundary
      private static String beanProperty(String accessor, int prefixLength) {
         char c = accessor.charAt(prefixLength);
         return Character.toLowerCase(c) + accessor.substring(prefixLength + 1);
      }

      @CompilerDirectives.TruffleBoundary
      private static String beanAccessor(String prefix, String beanProperty) {
         return prefix + Character.toUpperCase(beanProperty.charAt(0)) + beanProperty.substring(1);
      }

      static final class BoundProperty extends PropertyProxy {
         private final JSDynamicObject source;
         private final Object key;
         private final JSClass sourceClass;

         BoundProperty(JSDynamicObject source, Object key, JSClass sourceClass) {
            this.source = source;
            this.key = key;
            this.sourceClass = sourceClass;
         }

         @Override
         public Object get(JSDynamicObject store) {
            return this.sourceClass.get(this.source, this.key);
         }

         @Override
         public boolean set(JSDynamicObject store, Object value) {
            return this.sourceClass.set(this.source, this.key, value, this.source, false, null);
         }
      }

      static final class ForeignBoundBeanProperty extends PropertyProxy {
         private final Object source;
         private final String getKey;
         private final String setKey;

         ForeignBoundBeanProperty(Object source, String getKey, String setKey) {
            assert getKey != null || setKey != null;

            this.source = source;
            this.getKey = getKey;
            this.setKey = setKey;
         }

         @Override
         public Object get(JSDynamicObject store) {
            if (this.getKey != null) {
               InteropLibrary library = InteropLibrary.getFactory().getUncached(this.source);
               if (library.isMemberInvocable(this.source, this.getKey)) {
                  try {
                     return JSRuntime.importValue(library.invokeMember(this.source, this.getKey));
                  } catch (UnknownIdentifierException | UnsupportedTypeException | ArityException | UnsupportedMessageException var4) {
                  }
               }
            }

            return Undefined.instance;
         }

         @Override
         public boolean set(JSDynamicObject store, Object value) {
            if (this.setKey != null) {
               InteropLibrary library = InteropLibrary.getFactory().getUncached(this.source);
               if (library.isMemberInvocable(this.source, this.setKey)) {
                  try {
                     library.invokeMember(this.source, this.setKey, JSRuntime.exportValue(value));
                     return true;
                  } catch (UnknownIdentifierException | UnsupportedTypeException | ArityException | UnsupportedMessageException var5) {
                  }
               }
            }

            return false;
         }
      }

      static final class ForeignBoundProperty extends PropertyProxy {
         private final Object source;
         private final String key;

         ForeignBoundProperty(Object source, String key) {
            this.source = source;
            this.key = key;
         }

         @Override
         public Object get(JSDynamicObject store) {
            InteropLibrary library = InteropLibrary.getFactory().getUncached(this.source);
            if (library.isMemberReadable(this.source, this.key)) {
               try {
                  return JSRuntime.importValue(library.readMember(this.source, this.key));
               } catch (UnknownIdentifierException | UnsupportedMessageException var4) {
               }
            }

            return Undefined.instance;
         }

         @Override
         public boolean set(JSDynamicObject store, Object value) {
            InteropLibrary library = InteropLibrary.getFactory().getUncached(this.source);
            if (library.isMemberWritable(this.source, this.key)) {
               try {
                  library.writeMember(this.source, this.key, JSRuntime.exportValue(value));
                  return true;
               } catch (UnknownIdentifierException | UnsupportedTypeException | UnsupportedMessageException var5) {
               }
            }

            return false;
         }
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class ObjectCreateNode extends ObjectFunctionBuiltins.ObjectDefineOperation {
      @Node.Child
      private CreateObjectNode.CreateObjectWithPrototypeNode objectCreateNode;
      private final BranchProfile needDefineProperties = BranchProfile.create();

      public ObjectCreateNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSNull(prototype)")
      protected JSDynamicObject createPrototypeNull(Object prototype, Object properties) {
         JSDynamicObject ret = JSOrdinary.createWithNullPrototype(this.getContext());
         return this.objectDefineProperties(ret, properties);
      }

      @Specialization(guards = {"!isJSNull(prototype)", "!isJSObject(prototype)"}, limit = "InteropLibraryLimit")
      protected JSDynamicObject createForeignNullOrInvalidPrototype(
         Object prototype, Object properties, @CachedLibrary("prototype") InteropLibrary interop, @Cached("createBinaryProfile()") ConditionProfile isNull
      ) {
         assert prototype != null;

         if (isNull.profile(prototype != Undefined.instance && interop.isNull(prototype))) {
            return this.createPrototypeNull(Null.instance, properties);
         } else {
            throw Errors.createTypeErrorInvalidPrototype(prototype);
         }
      }

      @Specialization(guards = {"isJSObject(prototype)", "isJSObject(properties)"})
      protected JSDynamicObject createObjectObject(JSDynamicObject prototype, JSDynamicObject properties) {
         JSDynamicObject ret = this.createObjectWithPrototype(prototype);
         this.intlDefineProperties(ret, properties);
         return ret;
      }

      @Specialization(guards = {"isJSObject(prototype)", "!isJSNull(properties)"})
      protected JSDynamicObject createObjectNotNull(JSDynamicObject prototype, Object properties) {
         JSDynamicObject ret = this.createObjectWithPrototype(prototype);
         return this.objectDefineProperties(ret, properties);
      }

      @Specialization(guards = {"isJSObject(prototype)", "isJSNull(properties)"})
      protected JSDynamicObject createObjectNull(JSDynamicObject prototype, Object properties) {
         throw Errors.createTypeErrorNotObjectCoercible(properties, null, this.getContext());
      }

      private JSDynamicObject objectDefineProperties(JSDynamicObject ret, Object properties) {
         if (properties != Undefined.instance) {
            this.needDefineProperties.enter();
            this.intlDefineProperties(ret, this.toJSObject(properties));
         }

         return ret;
      }

      private JSDynamicObject createObjectWithPrototype(JSDynamicObject prototype) {
         if (this.objectCreateNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.objectCreateNode = this.insert(CreateObjectNode.createOrdinaryWithPrototype(this.getContext()));
         }

         return this.objectCreateNode.execute(prototype);
      }
   }

   protected abstract static class ObjectDefineOperation extends ObjectPrototypeBuiltins.ObjectOperation {
      @Node.Child
      private ToPropertyDescriptorNode toPropertyDescriptorNode;

      public ObjectDefineOperation(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected PropertyDescriptor toPropertyDescriptor(Object target) {
         if (this.toPropertyDescriptorNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toPropertyDescriptorNode = this.insert(ToPropertyDescriptorNode.create(this.getContext()));
         }

         return (PropertyDescriptor)this.toPropertyDescriptorNode.execute(target);
      }

      @CompilerDirectives.TruffleBoundary
      protected JSDynamicObject intlDefineProperties(JSDynamicObject obj, JSDynamicObject descs) {
         List<Pair<Object, PropertyDescriptor>> descriptors = new ArrayList<>();
         JSClass descsClass = JSObject.getJSClass(descs);

         for (Object key : descsClass.ownPropertyKeys(descs)) {
            PropertyDescriptor keyDesc = descsClass.getOwnProperty(descs, key);
            if (keyDesc != null && keyDesc.getEnumerable()) {
               PropertyDescriptor desc = this.toPropertyDescriptor(descsClass.get(descs, key));
               Boundaries.listAdd(descriptors, new Pair<>(key, desc));
            }
         }

         for (Pair<Object, PropertyDescriptor> descPair : descriptors) {
            JSRuntime.definePropertyOrThrow(obj, descPair.getFirst(), descPair.getSecond());
         }

         return obj;
      }
   }

   public abstract static class ObjectDefinePropertiesNode extends ObjectFunctionBuiltins.ObjectDefineOperation {
      public ObjectDefinePropertiesNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = {"isJSObject(thisObj)", "isJSObject(properties)"})
      protected JSDynamicObject definePropertiesObjectObject(JSDynamicObject thisObj, JSDynamicObject properties) {
         return this.intlDefineProperties(thisObj, properties);
      }

      @Specialization(replaces = "definePropertiesObjectObject")
      protected JSDynamicObject definePropertiesGeneric(Object thisObj, Object properties) {
         JSDynamicObject object = this.asJSObject(thisObj);
         return this.intlDefineProperties(object, this.toJSObject(properties));
      }
   }

   public abstract static class ObjectDefinePropertyNode extends ObjectFunctionBuiltins.ObjectDefineOperation {
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();

      public ObjectDefinePropertyNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSObject(thisObj)")
      protected JSDynamicObject definePropertyJSObjectTString(JSDynamicObject thisObj, TruffleString property, Object attributes) {
         PropertyDescriptor desc = this.toPropertyDescriptor(attributes);
         JSRuntime.definePropertyOrThrow(thisObj, property, desc);
         return thisObj;
      }

      @Specialization(replaces = "definePropertyJSObjectTString")
      protected JSDynamicObject definePropertyGeneric(Object thisObj, Object property, Object attributes) {
         JSDynamicObject object = this.asJSObject(thisObj);
         PropertyDescriptor desc = this.toPropertyDescriptor(attributes);
         Object propertyKey = this.toPropertyKeyNode.execute(property);
         JSRuntime.definePropertyOrThrow(object, propertyKey, desc);
         return object;
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return ObjectFunctionBuiltinsFactory.ObjectDefinePropertyNodeGen.create(
            this.getContext(), this.getBuiltin(), cloneUninitialized(this.getArguments(), materializedTags)
         );
      }
   }

   public abstract static class ObjectFromEntriesNode extends ObjectPrototypeBuiltins.ObjectOperation {
      @Node.Child
      private RequireObjectCoercibleNode requireObjectCoercibleNode = RequireObjectCoercibleNode.create();
      @Node.Child
      private GetIteratorBaseNode getIteratorNode;
      @Node.Child
      private IteratorStepNode iteratorStepNode;
      @Node.Child
      private IteratorValueNode iteratorValueNode;
      @Node.Child
      private IsObjectNode isObjectNode = IsObjectNode.create();
      @Node.Child
      private IteratorCloseNode iteratorCloseNode;
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();
      @Node.Child
      private ReadElementNode readElementNode;
      private final BranchProfile errorBranch = BranchProfile.create();

      public ObjectFromEntriesNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.getIteratorNode = GetIteratorBaseNode.create();
         this.iteratorStepNode = IteratorStepNode.create();
         this.iteratorValueNode = IteratorValueNode.create();
         this.readElementNode = ReadElementNode.create(context);
      }

      @Specialization
      protected JSDynamicObject entries(Object iterable) {
         this.requireObjectCoercibleNode.executeVoid(iterable);
         JSObject obj = JSOrdinary.create(this.getContext(), this.getRealm());
         return this.addEntriesFromIterable(obj, iterable);
      }

      private JSDynamicObject addEntriesFromIterable(JSDynamicObject target, Object iterable) {
         assert !JSRuntime.isNullOrUndefined(target);

         IteratorRecord iteratorRecord = this.getIteratorNode.execute(iterable);

         try {
            while (true) {
               Object next = this.iteratorStepNode.execute(iteratorRecord);
               if (next == Boolean.FALSE) {
                  return target;
               }

               Object nextItem = this.iteratorValueNode.execute(next);
               if (!this.isObjectNode.executeBoolean(nextItem)) {
                  this.errorBranch.enter();
                  throw Errors.createTypeErrorIteratorResultNotObject(nextItem, this);
               }

               Object k = this.readElementNode.executeWithTargetAndIndex(nextItem, 0);
               Object v = this.readElementNode.executeWithTargetAndIndex(nextItem, 1);
               this.createDataPropertyOnObject(target, k, v);
            }
         } catch (AbstractTruffleException var8) {
            this.errorBranch.enter();
            this.iteratorCloseAbrupt(iteratorRecord.getIterator());
            throw var8;
         }
      }

      private void createDataPropertyOnObject(JSDynamicObject thisObject, Object key, Object value) {
         assert JSRuntime.isObject(thisObject);

         Object propertyKey = this.toPropertyKeyNode.execute(key);
         JSRuntime.createDataPropertyOrThrow(thisObject, propertyKey, value);
      }

      private void iteratorCloseAbrupt(JSDynamicObject iterator) {
         if (this.iteratorCloseNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.iteratorCloseNode = this.insert(IteratorCloseNode.create(this.getContext()));
         }

         this.iteratorCloseNode.executeAbrupt(iterator);
      }
   }

   public static enum ObjectFunction implements BuiltinEnum<ObjectFunctionBuiltins.ObjectFunction> {
      create(2),
      defineProperties(2),
      defineProperty(3),
      freeze(1),
      getOwnPropertyDescriptor(2),
      getOwnPropertyNames(1),
      getPrototypeOf(1),
      isExtensible(1),
      isFrozen(1),
      isSealed(1),
      keys(1),
      preventExtensions(1),
      seal(1),
      setPrototypeOf(2),
      is(2),
      getOwnPropertySymbols(1),
      assign(2),
      getOwnPropertyDescriptors(1),
      values(1),
      entries(1),
      fromEntries(1),
      hasOwn(2);

      private final int length;

      private ObjectFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public int getECMAScriptVersion() {
         if (EnumSet.of(is, getOwnPropertySymbols, assign).contains(this)) {
            return 6;
         } else if (EnumSet.of(getOwnPropertyDescriptors, values, entries).contains(this)) {
            return 8;
         } else if (this == fromEntries) {
            return 10;
         } else {
            return this == hasOwn ? 13 : BuiltinEnum.super.getECMAScriptVersion();
         }
      }
   }

   public static final class ObjectFunctionNashornCompatBuiltins
      extends JSBuiltinsContainer.SwitchEnum<ObjectFunctionBuiltins.ObjectFunctionNashornCompatBuiltins.ObjectNashornCompat> {
      protected ObjectFunctionNashornCompatBuiltins() {
         super(ObjectFunctionBuiltins.ObjectFunctionNashornCompatBuiltins.ObjectNashornCompat.class);
      }

      protected Object createNode(
         JSContext context,
         JSBuiltin builtin,
         boolean construct,
         boolean newTarget,
         ObjectFunctionBuiltins.ObjectFunctionNashornCompatBuiltins.ObjectNashornCompat builtinEnum
      ) {
         switch (builtinEnum) {
            case bindProperties:
               return ObjectFunctionBuiltinsFactory.ObjectBindPropertiesNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
            default:
               return null;
         }
      }

      public static enum ObjectNashornCompat implements BuiltinEnum<ObjectFunctionBuiltins.ObjectFunctionNashornCompatBuiltins.ObjectNashornCompat> {
         bindProperties(2);

         private final int length;

         private ObjectNashornCompat(int length) {
            this.length = length;
         }

         @Override
         public int getLength() {
            return this.length;
         }
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class ObjectGetOwnPropertyDescriptorNode extends ObjectPrototypeBuiltins.ObjectOperation {
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();
      @Node.Child
      private JSGetOwnPropertyNode getOwnPropertyNode = JSGetOwnPropertyNode.create();
      @Node.Child
      private FromPropertyDescriptorNode fromPropertyDescriptorNode = FromPropertyDescriptorNode.create();

      public ObjectGetOwnPropertyDescriptorNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSObject(thisObj)")
      protected JSDynamicObject getJSObject(JSDynamicObject thisObj, Object property) {
         Object propertyKey = this.toPropertyKeyNode.execute(property);
         PropertyDescriptor desc = this.getOwnPropertyNode.execute(thisObj, propertyKey);
         return this.fromPropertyDescriptorNode.execute(desc, this.getContext());
      }

      @Specialization(guards = "isForeignObject(thisObj)", limit = "InteropLibraryLimit")
      protected JSDynamicObject getForeignObject(
         Object thisObj,
         Object property,
         @CachedLibrary("thisObj") InteropLibrary interop,
         @Cached("create()") ImportValueNode toJSType,
         @Cached TruffleString.ReadCharUTF16Node charAtNode
      ) {
         Object propertyKey = this.toPropertyKeyNode.execute(property);
         if (Strings.isTString(propertyKey)) {
            PropertyDescriptor desc = JSInteropUtil.getOwnProperty(thisObj, (TruffleString)propertyKey, interop, toJSType, charAtNode);
            if (desc != null) {
               return this.fromPropertyDescriptorNode.execute(desc, this.getContext());
            }
         }

         return Undefined.instance;
      }

      @Specialization(guards = {"!isJSObject(thisObj)", "!isForeignObject(thisObj)"})
      protected JSDynamicObject getDefault(Object thisObj, Object property) {
         Object object = this.toObject(thisObj);

         assert JSDynamicObject.isJSDynamicObject(object);

         return this.getJSObject((JSDynamicObject)object, property);
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class ObjectGetOwnPropertyDescriptorsNode extends ObjectPrototypeBuiltins.ObjectOperation {
      @Node.Child
      private FromPropertyDescriptorNode fromPropertyDescriptorNode = FromPropertyDescriptorNode.create();
      @Node.Child
      private DynamicObjectLibrary putPropDescNode = DynamicObjectLibrary.getFactory().createDispatched(5);

      public ObjectGetOwnPropertyDescriptorsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected abstract JSDynamicObject executeEvaluated(Object obj);

      @Specialization(guards = "isJSObject(thisObj)")
      protected JSDynamicObject getJSObject(
         JSDynamicObject thisObj,
         @Cached JSGetOwnPropertyNode getOwnPropertyNode,
         @Cached ListSizeNode listSize,
         @Cached ListGetNode listGet,
         @Cached JSClassProfile classProfile
      ) {
         JSDynamicObject retObj = JSOrdinary.create(this.getContext(), this.getRealm());
         List<Object> ownPropertyKeys = JSObject.ownPropertyKeys(thisObj, classProfile);
         int size = listSize.execute(ownPropertyKeys);

         for (int i = 0; i < size; i++) {
            Object key = listGet.execute(ownPropertyKeys, i);

            assert JSRuntime.isPropertyKey(key);

            PropertyDescriptor desc = getOwnPropertyNode.execute(thisObj, key);
            if (desc != null) {
               JSDynamicObject propDesc = this.fromPropertyDescriptorNode.execute(desc, this.getContext());
               Properties.putWithFlags(this.putPropDescNode, retObj, key, propDesc, JSAttributes.configurableEnumerableWritable());
            }
         }

         return retObj;
      }

      @Specialization(guards = "isForeignObject(thisObj)", limit = "InteropLibraryLimit")
      protected JSDynamicObject getForeignObject(
         Object thisObj,
         @CachedLibrary("thisObj") InteropLibrary interop,
         @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary members,
         @Cached("create()") ImportValueNode toJSType,
         @Cached BranchProfile errorBranch
      ) {
         JSDynamicObject result = JSOrdinary.create(this.getContext(), this.getRealm());

         try {
            label46:
            if (interop.hasMembers(thisObj)) {
               Object keysObj = interop.getMembers(thisObj);
               long size = members.getArraySize(keysObj);
               if (size >= 0L && size < 2147483647L) {
                  int i = 0;

                  while (true) {
                     if (i >= size) {
                        break label46;
                     }

                     String member = (String)members.readArrayElement(keysObj, i);
                     PropertyDescriptor desc = JSInteropUtil.getExistingMemberProperty(thisObj, member, interop, toJSType);
                     if (desc != null) {
                        JSDynamicObject propDesc = this.fromPropertyDescriptorNode.execute(desc, this.getContext());
                        Properties.putWithFlags(
                           this.putPropDescNode, result, Strings.fromJavaString(member), propDesc, JSAttributes.configurableEnumerableWritable()
                        );
                     }

                     i++;
                  }
               }

               errorBranch.enter();
               throw Errors.createRangeErrorInvalidArrayLength();
            }

            if (interop.hasArrayElements(thisObj)) {
               long size = interop.getArraySize(thisObj);
               if (size < 0L || size >= 2147483647L) {
                  errorBranch.enter();
                  throw Errors.createRangeErrorInvalidArrayLength();
               }

               for (long i = 0L; i < size; i++) {
                  PropertyDescriptor desc = JSInteropUtil.getArrayElementProperty(thisObj, i, interop, toJSType);
                  if (desc != null) {
                     JSDynamicObject propDesc = this.fromPropertyDescriptorNode.execute(desc, this.getContext());
                     Properties.putWithFlags(this.putPropDescNode, result, Strings.fromLong(i), propDesc, JSAttributes.configurableEnumerableWritable());
                  }
               }
            }
         } catch (InteropException var14) {
         }

         return result;
      }

      @Specialization(guards = {"!isJSObject(thisObj)", "!isForeignObject(thisObj)"})
      protected JSDynamicObject getDefault(Object thisObj, @Cached("createRecursive()") ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode recursive) {
         Object object = this.toObject(thisObj);
         return recursive.executeEvaluated(object);
      }

      ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode createRecursive() {
         return ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.create(this.getContext(), this.getBuiltin(), new JavaScriptNode[0]);
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class ObjectGetOwnPropertyNamesOrSymbolsNode extends ObjectPrototypeBuiltins.ObjectOperation {
      protected final boolean symbols;

      public ObjectGetOwnPropertyNamesOrSymbolsNode(JSContext context, JSBuiltin builtin, boolean symbols) {
         super(context, builtin);
         this.symbols = symbols;
      }

      @Specialization(guards = "isJSObject(thisObj)")
      protected JSDynamicObject getJSObject(
         JSDynamicObject thisObj,
         @Cached @Cached.Shared("jsclassProfile") JSClassProfile jsclassProfile,
         @Cached @Cached.Shared("listSize") ListSizeNode listSize
      ) {
         List<Object> ownPropertyKeys = jsclassProfile.getJSClass(thisObj).getOwnPropertyKeys(thisObj, !this.symbols, this.symbols);
         return JSArray.createLazyArray(this.getContext(), this.getRealm(), ownPropertyKeys, listSize.execute(ownPropertyKeys));
      }

      @Specialization(guards = {"!isJSObject(thisObj)", "!isForeignObject(thisObj)"})
      protected JSDynamicObject getDefault(
         Object thisObj, @Cached @Cached.Shared("jsclassProfile") JSClassProfile jsclassProfile, @Cached @Cached.Shared("listSize") ListSizeNode listSize
      ) {
         JSDynamicObject object = this.toOrAsJSObject(thisObj);
         return this.getJSObject(object, jsclassProfile, listSize);
      }

      @Specialization(guards = {"isForeignObject(thisObj)", "symbols"})
      protected JSDynamicObject getForeignObjectSymbols(Object thisObj) {
         return JSArray.createConstantEmptyArray(this.getContext(), this.getRealm());
      }

      @Specialization(guards = {"isForeignObject(thisObj)", "!symbols"})
      protected JSDynamicObject getForeignObjectNames(
         Object thisObj,
         @Cached("createKeys(getContext())") EnumerableOwnPropertyNamesNode enumerableOwnPropertyNamesNode,
         @Cached ConditionProfile hasElements
      ) {
         UnmodifiableArrayList<? extends Object> keyList = enumerableOwnPropertyNamesNode.execute(thisObj);
         int len = keyList.size();
         JSRealm realm = this.getRealm();
         if (hasElements.profile(len > 0)) {
            assert keyList.stream().allMatch(Strings::isTString);

            return JSArray.createConstant(this.getContext(), realm, keyList.toArray());
         } else {
            return JSArray.createEmptyChecked(this.getContext(), realm, 0L);
         }
      }
   }

   public abstract static class ObjectGetPrototypeOfNode extends ObjectPrototypeBuiltins.ObjectOperation {
      @Node.Child
      private ForeignObjectPrototypeNode foreignObjectPrototypeNode;

      public ObjectGetPrototypeOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "!isJSObject(object)")
      protected JSDynamicObject getPrototypeOfNonObject(Object object, @Cached("createBinaryProfile()") ConditionProfile isForeignProfile) {
         if (this.getContext().getEcmaScriptVersion() < 6) {
            if (JSRuntime.isJSPrimitive(object)) {
               throw Errors.createTypeErrorNotAnObject(object);
            } else {
               return Null.instance;
            }
         } else if (isForeignProfile.profile(JSRuntime.isForeignObject(object))) {
            if (InteropLibrary.getUncached(object).isNull(object)) {
               throw Errors.createTypeErrorNotAnObject(object);
            } else {
               return this.getContext().getContextOptions().hasForeignObjectPrototype() ? this.getForeignObjectPrototype(object) : Null.instance;
            }
         } else {
            assert JSRuntime.isJSPrimitive(object);

            Object tobject = this.toObject(object);
            return JSObject.getPrototype((JSDynamicObject)tobject);
         }
      }

      private JSDynamicObject getForeignObjectPrototype(Object truffleObject) {
         assert JSRuntime.isForeignObject(truffleObject);

         if (this.foreignObjectPrototypeNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.foreignObjectPrototypeNode = this.insert(ForeignObjectPrototypeNode.create());
         }

         return this.foreignObjectPrototypeNode.execute(truffleObject);
      }

      @Specialization(guards = "isJSObject(object)")
      protected JSDynamicObject getPrototypeOfJSObject(JSDynamicObject object, @Cached("create()") GetPrototypeNode getPrototypeNode) {
         return getPrototypeNode.execute(object);
      }
   }

   public abstract static class ObjectHasOwnNode extends ObjectPrototypeBuiltins.ObjectOperation {
      @Node.Child
      JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();
      @Node.Child
      JSHasPropertyNode hasOwnPropertyNode = JSHasPropertyNode.create(true);

      public ObjectHasOwnNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean hasOwn(Object o, Object p) {
         Object obj = this.toObject(o);
         Object key = this.toPropertyKeyNode.execute(p);
         return this.hasOwnPropertyNode.executeBoolean(obj, key);
      }
   }

   public abstract static class ObjectIsExtensibleNode extends ObjectPrototypeBuiltins.ObjectOperation {
      public ObjectIsExtensibleNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSObject(thisObj)")
      protected boolean isExtensibleObject(JSDynamicObject thisObj, @Cached IsExtensibleNode isExtensibleNode) {
         return isExtensibleNode.executeBoolean(thisObj);
      }

      @Specialization(guards = "!isJSObject(thisObj)")
      protected boolean isExtensibleNonObject(Object thisObj) {
         if (this.getContext().getEcmaScriptVersion() < 6) {
            throw this.createTypeErrorCalledOnNonObject(thisObj);
         } else {
            return false;
         }
      }
   }

   public abstract static class ObjectIsNode extends ObjectPrototypeBuiltins.ObjectOperation {
      public ObjectIsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean isInt(int a, int b) {
         return a == b;
      }

      @Specialization
      protected boolean isDouble(double a, double b) {
         if (a == 0.0 && b == 0.0) {
            return JSRuntime.isNegativeZero(a) == JSRuntime.isNegativeZero(b);
         } else {
            return Double.isNaN(a) ? Double.isNaN(b) : a == b;
         }
      }

      @Specialization(guards = "isNumberNumber(a,b)")
      protected boolean isNumberNumber(Number a, Number b, @Cached("createSameValue()") JSIdenticalNode doIdenticalNode) {
         return doIdenticalNode.executeBoolean(JSRuntime.doubleValue(a), JSRuntime.doubleValue(b));
      }

      @Specialization(guards = "!isNumberNumber(a, b)")
      protected boolean isObject(Object a, Object b, @Cached("createSameValue()") JSIdenticalNode doIdenticalNode) {
         return doIdenticalNode.executeBoolean(a, b);
      }

      protected boolean isNumberNumber(Object a, Object b) {
         return a instanceof Number && b instanceof Number;
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class ObjectKeysNode extends ObjectPrototypeBuiltins.ObjectOperation {
      @Node.Child
      private EnumerableOwnPropertyNamesNode enumerableOwnPropertyNamesNode;
      private final ConditionProfile hasElements = ConditionProfile.createBinaryProfile();

      public ObjectKeysNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      private JSDynamicObject keys(Object obj) {
         assert JSObject.isJSObject(obj) || JSRuntime.isForeignObject(obj);

         UnmodifiableArrayList<? extends Object> keyList = this.enumerableOwnPropertyNames(obj);
         int len = keyList.size();
         JSRealm realm = this.getRealm();
         if (this.hasElements.profile(len > 0)) {
            assert keyList.stream().allMatch(Strings::isTString);

            return JSArray.createConstant(this.getContext(), realm, keyList.toArray());
         } else {
            return JSArray.createEmptyChecked(this.getContext(), realm, 0L);
         }
      }

      @Specialization(guards = "isJSDynamicObject(thisObj)")
      protected JSDynamicObject keysDynamicObject(JSDynamicObject thisObj) {
         return this.keys(this.toOrAsJSObject(thisObj));
      }

      @Specialization
      protected JSDynamicObject keysSymbol(Symbol symbol) {
         return this.keys(this.toOrAsJSObject(symbol));
      }

      @Specialization
      protected JSDynamicObject keysString(TruffleString string) {
         return this.keys(this.toOrAsJSObject(string));
      }

      @Specialization
      protected JSDynamicObject keysSafeInt(SafeInteger largeInteger) {
         return this.keys(this.toOrAsJSObject(largeInteger));
      }

      @Specialization
      protected JSDynamicObject keysBigInt(BigInt bigInt) {
         return this.keys(this.toOrAsJSObject(bigInt));
      }

      @Specialization(guards = "!isTruffleObject(thisObj)")
      protected JSDynamicObject keysOther(Object thisObj) {
         return this.keys(this.toOrAsJSObject(thisObj));
      }

      @Specialization(guards = "isForeignObject(obj)")
      protected JSDynamicObject keysForeign(Object obj) {
         return this.keys(obj);
      }

      private UnmodifiableArrayList<? extends Object> enumerableOwnPropertyNames(Object obj) {
         if (this.enumerableOwnPropertyNamesNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.enumerableOwnPropertyNamesNode = this.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
         }

         return this.enumerableOwnPropertyNamesNode.execute(obj);
      }
   }

   public abstract static class ObjectPreventExtensionsNode extends ObjectPrototypeBuiltins.ObjectOperation {
      public ObjectPreventExtensionsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSObject(thisObj)")
      protected JSDynamicObject preventExtensionsObject(JSDynamicObject thisObj) {
         JSObject.preventExtensions(thisObj, true);
         return thisObj;
      }

      @Specialization(guards = "!isJSObject(thisObj)")
      protected Object preventExtensionsNonObject(Object thisObj) {
         if (this.getContext().getEcmaScriptVersion() < 6) {
            throw this.createTypeErrorCalledOnNonObject(thisObj);
         } else {
            return thisObj;
         }
      }
   }

   public abstract static class ObjectSetIntegrityLevelNode extends ObjectPrototypeBuiltins.ObjectOperation {
      private final boolean freeze;
      private final ConditionProfile isObject = ConditionProfile.createBinaryProfile();

      public ObjectSetIntegrityLevelNode(JSContext context, JSBuiltin builtin, boolean freeze) {
         super(context, builtin);
         this.freeze = freeze;
      }

      @Specialization
      protected Object setIntegrityLevel(Object thisObj) {
         if (this.isObject.profile(JSRuntime.isObject(thisObj))) {
            JSObject.setIntegrityLevel((JSDynamicObject)thisObj, this.freeze, true);
         } else if (this.getContext().getEcmaScriptVersion() < 6) {
            throw this.createTypeErrorCalledOnNonObject(thisObj);
         }

         return thisObj;
      }
   }

   public abstract static class ObjectSetPrototypeOfNode extends ObjectPrototypeBuiltins.ObjectOperation {
      private final BranchProfile errorBranch = BranchProfile.create();
      private final JSClassProfile classProfile = JSClassProfile.create();

      public ObjectSetPrototypeOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isValidPrototype(newProto)")
      final Object setPrototypeOfJSObject(JSObject object, JSDynamicObject newProto) {
         if (!JSObject.setPrototype(object, newProto, this.classProfile)) {
            this.errorBranch.enter();
            throw Errors.createTypeError("setPrototype failed");
         } else {
            return object;
         }
      }

      @Specialization(guards = "!isValidPrototype(newProto)")
      static Object setPrototypeOfJSObjectToInvalidNewProto(JSObject object, Object newProto) {
         throw Errors.createTypeErrorInvalidPrototype(newProto);
      }

      @Specialization(guards = "isNullOrUndefined(object)")
      final Object setPrototypeOfNonObjectCoercible(Object object, Object newProto) {
         throw this.createTypeErrorCalledOnNonObject(object);
      }

      @Specialization(guards = {"!isJSObject(object)", "!isNullOrUndefined(object)", "!isForeignObject(object)"})
      static Object setPrototypeOfValue(Object object, Object newProto) {
         return object;
      }

      @Specialization(guards = "isForeignObject(object)")
      final Object setPrototypeOfForeignObject(Object object, Object newProto) {
         throw this.createTypeErrorCalledOnNonObject(object);
      }
   }

   public abstract static class ObjectTestIntegrityLevelNode extends ObjectPrototypeBuiltins.ObjectOperation {
      private final boolean frozen;
      private final ConditionProfile isObject = ConditionProfile.createBinaryProfile();

      public ObjectTestIntegrityLevelNode(JSContext context, JSBuiltin builtin, boolean frozen) {
         super(context, builtin);
         this.frozen = frozen;
      }

      @Specialization
      protected boolean testIntegrityLevel(Object thisObj) {
         if (this.isObject.profile(JSRuntime.isObject(thisObj))) {
            return JSObject.testIntegrityLevel((JSDynamicObject)thisObj, this.frozen);
         } else if (this.getContext().getEcmaScriptVersion() < 6) {
            throw this.createTypeErrorCalledOnNonObject(thisObj);
         } else {
            return true;
         }
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class ObjectValuesOrEntriesNode extends ObjectPrototypeBuiltins.ObjectOperation {
      protected final boolean entries;
      @Node.Child
      private EnumerableOwnPropertyNamesNode enumerableOwnPropertyNamesNode;
      private final ConditionProfile hasElements = ConditionProfile.createBinaryProfile();

      public ObjectValuesOrEntriesNode(JSContext context, JSBuiltin builtin, boolean entries) {
         super(context, builtin);
         this.entries = entries;
      }

      protected abstract JSDynamicObject executeEvaluated(Object obj);

      @Specialization(guards = "isJSObject(obj)")
      protected JSDynamicObject valuesOrEntriesJSObject(JSDynamicObject obj) {
         return this.valuesOrEntries(obj);
      }

      private JSDynamicObject valuesOrEntries(Object obj) {
         assert JSObject.isJSObject(obj) || JSRuntime.isForeignObject(obj);

         UnmodifiableArrayList<? extends Object> list = this.enumerableOwnPropertyNames(obj);
         int len = list.size();
         JSRealm realm = this.getRealm();
         return this.hasElements.profile(len > 0)
            ? JSArray.createConstant(this.getContext(), realm, list.toArray())
            : JSArray.createEmptyChecked(this.getContext(), realm, 0L);
      }

      protected UnmodifiableArrayList<? extends Object> enumerableOwnPropertyNames(Object obj) {
         if (this.enumerableOwnPropertyNamesNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.enumerableOwnPropertyNamesNode = this.insert(
               this.entries
                  ? EnumerableOwnPropertyNamesNode.createKeysValues(this.getContext())
                  : EnumerableOwnPropertyNamesNode.createValues(this.getContext())
            );
         }

         return this.enumerableOwnPropertyNamesNode.execute(obj);
      }

      @Specialization(guards = "isForeignObject(thisObj)")
      protected JSDynamicObject valuesOrEntriesForeign(Object thisObj) {
         return this.valuesOrEntries(thisObj);
      }

      @Specialization(guards = {"!isJSObject(obj)", "!isForeignObject(obj)"})
      protected JSDynamicObject valuesOrEntriesGeneric(Object obj, @Cached("createRecursive()") ObjectFunctionBuiltins.ObjectValuesOrEntriesNode recursive) {
         Object thisObj = this.toObject(obj);
         return recursive.executeEvaluated(thisObj);
      }

      ObjectFunctionBuiltins.ObjectValuesOrEntriesNode createRecursive() {
         return ObjectFunctionBuiltinsFactory.ObjectValuesOrEntriesNodeGen.create(this.getContext(), this.getBuiltin(), this.entries, new JavaScriptNode[0]);
      }
   }
}
