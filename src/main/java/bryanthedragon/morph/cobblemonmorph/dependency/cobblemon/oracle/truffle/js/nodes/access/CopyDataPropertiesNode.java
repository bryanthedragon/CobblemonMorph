package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.ListGetNode;
import com.oracle.truffle.js.builtins.helper.ListSizeNode;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.util.List;

@ImportStatic(JSConfig.class)
public abstract class CopyDataPropertiesNode extends JavaScriptBaseNode {
   protected final JSContext context;

   protected CopyDataPropertiesNode(JSContext context) {
      this.context = context;
   }

   public static CopyDataPropertiesNode create(JSContext context) {
      return CopyDataPropertiesNodeGen.create(context);
   }

   public final Object execute(Object target, Object source) {
      return this.executeImpl(target, source, null, false);
   }

   public final Object execute(Object target, Object source, Object[] excludedItems) {
      return this.executeImpl(target, source, excludedItems, true);
   }

   protected abstract Object executeImpl(Object target, Object source, Object[] excludedItems, boolean withExcluded);

   @Specialization(guards = "isNullOrUndefined(value)")
   protected static JSDynamicObject doNullOrUndefined(JSDynamicObject target, Object value, Object[] excludedItems, boolean withExcluded) {
      return target;
   }

   @Specialization(guards = "isJSObject(source)")
   protected static JSDynamicObject copyDataProperties(
      JSDynamicObject target,
      JSDynamicObject source,
      Object[] excludedItems,
      boolean withExcluded,
      @Cached("create(context)") ReadElementNode getNode,
      @Cached("create(false)") JSGetOwnPropertyNode getOwnProperty,
      @Cached ListSizeNode listSize,
      @Cached ListGetNode listGet,
      @Cached JSClassProfile classProfile,
      @Cached TruffleString.EqualNode equalsNode
   ) {
      List<Object> ownPropertyKeys = JSObject.ownPropertyKeys(source, classProfile);
      int size = listSize.execute(ownPropertyKeys);

      for (int i = 0; i < size; i++) {
         Object nextKey = listGet.execute(ownPropertyKeys, i);

         assert JSRuntime.isPropertyKey(nextKey);

         if (!isExcluded(withExcluded, excludedItems, nextKey, equalsNode)) {
            PropertyDescriptor desc = getOwnProperty.execute(source, nextKey);
            if (desc != null && desc.getEnumerable()) {
               Object propValue = getNode.executeWithTargetAndIndex(source, nextKey);
               JSRuntime.createDataPropertyOrThrow(target, nextKey, propValue);
            }
         }
      }

      return target;
   }

   private static boolean isExcluded(boolean withExcluded, Object[] excludedKeys, Object key, TruffleString.EqualNode equalsNode) {
      CompilerAsserts.partialEvaluationConstant(withExcluded);
      if (withExcluded) {
         for (Object e : excludedKeys) {
            assert JSRuntime.isPropertyKey(e);

            if (JSRuntime.propertyKeyEquals(equalsNode, e, key)) {
               return true;
            }
         }
      }

      return false;
   }

   @Specialization(guards = "!isJSDynamicObject(from)", limit = "InteropLibraryLimit")
   protected final JSDynamicObject copyDataPropertiesForeign(
      JSDynamicObject target,
      Object from,
      Object[] excludedItems,
      boolean withExcluded,
      @CachedLibrary("from") InteropLibrary objInterop,
      @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary iteratorInterop,
      @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary arrayInterop,
      @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary stringInterop,
      @Cached ImportValueNode importValue,
      @Cached JSToStringNode toString,
      @Cached TruffleString.EqualNode equalsNode
   ) {
      if (objInterop.isNull(from)) {
         return target;
      } else {
         try {
            if (this.context.getContextOptions().hasForeignHashProperties() && objInterop.hasHashEntries(from)) {
               Object entriesIterator = objInterop.getHashEntriesIterator(from);

               while (true) {
                  Object entry;
                  try {
                     entry = iteratorInterop.getIteratorNextElement(entriesIterator);
                  } catch (StopIterationException var20) {
                     break;
                  }

                  Object key = arrayInterop.readArrayElement(entry, 0L);
                  Object value = arrayInterop.readArrayElement(entry, 1L);
                  TruffleString stringKey = toString.executeString(importValue.executeWithTarget(key));
                  if (!isExcluded(withExcluded, excludedItems, stringKey, equalsNode)) {
                     JSRuntime.createDataPropertyOrThrow(target, stringKey, importValue.executeWithTarget(value));
                  }
               }
            } else if (objInterop.hasMembers(from)) {
               Object members = objInterop.getMembers(from);
               long length = JSInteropUtil.getArraySize(members, arrayInterop, this);

               for (long i = 0L; i < length; i++) {
                  Object key = arrayInterop.readArrayElement(members, i);

                  assert InteropLibrary.getUncached().isString(key);

                  TruffleString stringKey = Strings.interopAsTruffleString(stringInterop, key);
                  if (!isExcluded(withExcluded, excludedItems, stringKey, equalsNode)) {
                     Object value = objInterop.readMember(from, Strings.toJavaString(stringKey));
                     JSRuntime.createDataPropertyOrThrow(target, stringKey, importValue.executeWithTarget(value));
                  }
               }
            }

            return target;
         } catch (InvalidArrayIndexException | UnknownIdentifierException | UnsupportedMessageException var21) {
            throw Errors.createTypeErrorInteropException(from, var21, "CopyDataProperties", this);
         }
      }
   }
}
