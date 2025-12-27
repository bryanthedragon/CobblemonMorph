package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.SparseArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantEmptyPrototypeArray;
import com.oracle.truffle.js.runtime.array.dyn.LazyRegexResultArray;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;
import com.oracle.truffle.js.runtime.util.IteratorUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public abstract class JSAbstractArray extends JSNonProxy {
   public static final TruffleString LENGTH = Strings.constant("length");
   protected static final String ARRAY_LENGTH_NOT_WRITABLE = "array length is not writable";
   private static final String LENGTH_PROPERTY_NOT_WRITABLE = "length property not writable";
   protected static final String CANNOT_REDEFINE_PROPERTY_LENGTH = "Cannot redefine property: length";
   protected static final String MAKE_SLOW_ARRAY_NEVER_PART_OF_COMPILATION_MESSAGE = "do not convert to slow array from compiled code";
   public static final String ARRAY_PROTOTYPE_NO_ELEMENTS_INVALIDATION = "Array.prototype no element assumption";
   public static final HiddenKey LAZY_REGEX_RESULT_ID = new HiddenKey("lazyRegexResult");
   public static final HiddenKey LAZY_REGEX_ORIGINAL_INPUT_ID = new HiddenKey("lazyRegexResultOriginalInput");
   public static final Comparator<Object> DEFAULT_JSARRAY_COMPARATOR = new JSAbstractArray.DefaultJSArrayComparator();
   public static final Comparator<Object> DEFAULT_JSARRAY_INTEGER_COMPARATOR = new JSAbstractArray.DefaultJSArrayIntegerComparator();
   public static final Comparator<Object> DEFAULT_JSARRAY_DOUBLE_COMPARATOR = new JSAbstractArray.DefaultJSArrayDoubleComparator();

   public static ScriptArray arrayGetArrayType(JSDynamicObject thisObj) {
      assert JSArray.isJSArray(thisObj) || JSArgumentsArray.isJSArgumentsObject(thisObj) || JSObjectPrototype.isJSObjectPrototype(thisObj);

      return arrayAccess().getArrayType(thisObj);
   }

   public static long arrayGetLength(JSDynamicObject thisObj) {
      return arrayAccess().getLength(thisObj);
   }

   public static int arrayGetUsedLength(JSDynamicObject thisObj) {
      return arrayAccess().getUsedLength(thisObj);
   }

   public static long arrayGetIndexOffset(JSDynamicObject thisObj) {
      return arrayAccess().getIndexOffset(thisObj);
   }

   public static int arrayGetArrayOffset(JSDynamicObject thisObj) {
      return arrayAccess().getArrayOffset(thisObj);
   }

   public static void arraySetArrayType(JSDynamicObject thisObj, ScriptArray arrayType) {
      arrayAccess().setArrayType(thisObj, arrayType);
   }

   public static void arraySetLength(JSDynamicObject thisObj, int length) {
      assert length >= 0;

      arrayAccess().setLength(thisObj, length);
   }

   public static void arraySetLength(JSDynamicObject thisObj, long length) {
      assert JSRuntime.isValidArrayLength(length);

      arrayAccess().setLength(thisObj, length);
   }

   public static void arraySetUsedLength(JSDynamicObject thisObj, int usedLength) {
      assert usedLength >= 0;

      arrayAccess().setUsedLength(thisObj, usedLength);
   }

   public static void arraySetIndexOffset(JSDynamicObject thisObj, long indexOffset) {
      arrayAccess().setIndexOffset(thisObj, indexOffset);
   }

   public static void arraySetArrayOffset(JSDynamicObject thisObj, int arrayOffset) {
      assert arrayOffset >= 0;

      arrayAccess().setArrayOffset(thisObj, arrayOffset);
   }

   public static Object arrayGetArray(JSDynamicObject thisObj) {
      assert JSObject.hasArray(thisObj);

      return arrayAccess().getArray(thisObj);
   }

   public static void arraySetArray(JSDynamicObject thisObj, Object array) {
      assert JSObject.hasArray(thisObj);

      assert array != null && (array.getClass().isArray() || array instanceof TreeMap);

      arrayAccess().setArray(thisObj, array);
   }

   public static int arrayGetHoleCount(JSDynamicObject thisObj) {
      return arrayAccess().getHoleCount(thisObj);
   }

   public static void arraySetHoleCount(JSDynamicObject thisObj, int holeCount) {
      assert holeCount >= 0;

      arrayAccess().setHoleCount(thisObj, holeCount);
   }

   public static ArrayAllocationSite arrayGetAllocationSite(JSDynamicObject thisObj) {
      return arrayAccess().getAllocationSite(thisObj);
   }

   public static Object arrayGetRegexResult(JSDynamicObject thisObj, DynamicObjectLibrary lazyRegexResult) {
      assert JSArray.isJSArray(thisObj) && JSArray.arrayGetArrayType(thisObj) == LazyRegexResultArray.LAZY_REGEX_RESULT_ARRAY;

      return Properties.getOrDefault(lazyRegexResult, thisObj, LAZY_REGEX_RESULT_ID, null);
   }

   public static TruffleString arrayGetRegexResultOriginalInput(JSDynamicObject thisObj, DynamicObjectLibrary lazyRegexResultOriginalInput) {
      return (TruffleString)Properties.getOrDefault(lazyRegexResultOriginalInput, thisObj, LAZY_REGEX_ORIGINAL_INPUT_ID, null);
   }

   protected JSAbstractArray() {
   }

   protected static final ArrayAccess arrayAccess() {
      return ArrayAccess.SINGLETON;
   }

   public long getLength(JSDynamicObject thisObj) {
      return arrayGetLength(thisObj);
   }

   @CompilerDirectives.TruffleBoundary
   public boolean setLength(JSDynamicObject thisObj, long length, boolean doThrow) {
      if (length < 0L) {
         throw Errors.createRangeErrorInvalidArrayLength();
      } else {
         ScriptArray array = arrayGetArrayType(thisObj);
         if (length > 2147483647L && !(array instanceof SparseArray)) {
            array = SparseArray.makeSparseArray(thisObj, array);
         }

         if (array.isSealed()) {
            long minIndex = array.lastElementIndex(thisObj) + 1L;
            if (length < minIndex) {
               array = array.setLength(thisObj, minIndex, doThrow);
               arraySetArrayType(thisObj, array);
               return array.canDeleteElement(thisObj, minIndex - 1L, doThrow);
            }
         }

         arraySetArrayType(thisObj, array.setLength(thisObj, length, doThrow));
         return true;
      }
   }

   @Override
   public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
      return this.getClassName(object);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final Object getOwnHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
      long idx = JSRuntime.propertyKeyToArrayIndex(key);
      return JSRuntime.isArrayIndex(idx)
         ? this.getOwnHelper(store, thisObj, idx, encapsulatingNode)
         : super.getOwnHelper(store, thisObj, key, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final boolean set(JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      if (receiver != thisObj) {
         return ordinarySetWithReceiver(thisObj, key, value, receiver, isStrict, encapsulatingNode);
      } else {
         assert receiver == thisObj;

         long idx = JSRuntime.propertyKeyToArrayIndex(key);
         return JSRuntime.isArrayIndex(idx)
            ? this.set(thisObj, idx, value, receiver, isStrict, encapsulatingNode)
            : super.set(thisObj, key, value, receiver, isStrict, encapsulatingNode);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      if (receiver != thisObj) {
         return ordinarySetWithReceiver(thisObj, Strings.fromLong(index), value, receiver, isStrict, encapsulatingNode);
      } else {
         assert receiver == thisObj;

         return arrayGetArrayType(thisObj).hasElement(thisObj, index)
            ? setElement(thisObj, index, value, isStrict)
            : setPropertySlow(thisObj, index, value, receiver, isStrict, encapsulatingNode);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static boolean setPropertySlow(JSDynamicObject thisObj, long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      if (!JSObject.getJSContext(thisObj).getArrayPrototypeNoElementsAssumption().isValid()
         && setPropertyPrototypes(thisObj, index, value, receiver, isStrict, encapsulatingNode)) {
         return true;
      } else if (!JSObject.isExtensible(thisObj)) {
         if (isStrict) {
            throw Errors.createTypeErrorNotExtensible(thisObj, Strings.fromLong(index));
         } else {
            return true;
         }
      } else {
         return setElement(thisObj, index, value, isStrict);
      }
   }

   private static boolean setPropertyPrototypes(JSDynamicObject thisObj, long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      JSDynamicObject current = JSObject.getPrototype(thisObj);

      for (Object propertyName = null; current != Null.instance; current = JSObject.getPrototype(current)) {
         if (JSProxy.isJSProxy(current)) {
            return JSObject.getJSClass(current).set(current, index, value, receiver, false, encapsulatingNode);
         }

         if (canHaveReadOnlyOrAccessorProperties(current) && JSObject.hasOwnProperty(current, index)) {
            if (propertyName == null) {
               propertyName = Strings.fromLong(index);
            }

            PropertyDescriptor desc = JSObject.getOwnProperty(current, propertyName);
            if (desc != null) {
               if (desc.isAccessorDescriptor()) {
                  invokeAccessorPropertySetter(desc, thisObj, propertyName, value, receiver, isStrict, encapsulatingNode);
                  return true;
               }

               if (!desc.getWritable()) {
                  if (isStrict) {
                     throw Errors.createTypeError("Cannot assign to read only property '" + index + "' of " + JSObject.defaultToString(thisObj));
                  }

                  return true;
               }
               break;
            }
         }
      }

      return false;
   }

   private static boolean canHaveReadOnlyOrAccessorProperties(JSDynamicObject current) {
      return !JSArrayBufferView.isJSArrayBufferView(current);
   }

   private static boolean setElement(JSDynamicObject thisObj, long index, Object value, boolean isStrict) {
      arraySetArrayType(thisObj, arrayGetArrayType(thisObj).setElement(thisObj, index, value, isStrict));
      return true;
   }

   @Override
   public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
      ScriptArray arrayType = arrayGetArrayType(thisObj);
      if (arrayType.canDeleteElement(thisObj, index, isStrict)) {
         arraySetArrayType(thisObj, arrayType.deleteElement(thisObj, index, isStrict));
         return true;
      } else {
         return false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
      ScriptArray array = arrayGetArrayType(store);
      return array.hasElement(store, index) ? array.getElement(store, index) : super.getOwnHelper(store, thisObj, Strings.fromLong(index), encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static Object[] toArray(JSDynamicObject thisObj) {
      return arrayGetArrayType(thisObj).toArray(thisObj);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
      if (super.hasOwnProperty(thisObj, key)) {
         return true;
      } else {
         long index = JSRuntime.propertyKeyToArrayIndex(key);
         return JSRuntime.isArrayIndex(index) ? arrayGetArrayType(thisObj).hasElement(thisObj, index) : false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final boolean hasOwnProperty(JSDynamicObject thisObj, long index) {
      ScriptArray array = arrayGetArrayType(thisObj);
      return array.hasElement(thisObj, index) ? true : super.hasOwnProperty(thisObj, Strings.fromLong(index));
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
      return ownPropertyKeysSlowArray(thisObj, strings, symbols);
   }

   @CompilerDirectives.TruffleBoundary
   protected static List<Object> ownPropertyKeysFastArray(JSDynamicObject thisObj, boolean strings, boolean symbols) {
      assert JSArray.isJSFastArray(thisObj) || JSArgumentsArray.isJSFastArgumentsObject(thisObj);

      List<Object> indices = strings ? arrayGetArrayType(thisObj).ownPropertyKeys(thisObj) : Collections.emptyList();
      List<Object> keyList = thisObj.getShape().getKeyList();
      if (keyList.isEmpty()) {
         return indices;
      } else {
         List<Object> list = new ArrayList<>(keyList.size());
         if (strings) {
            keyList.forEach(k -> {
               assert !Strings.isTString(k) || !JSRuntime.isArrayIndexString((TruffleString)k);

               if (Strings.isTString(k)) {
                  list.add(k);
               }
            });
         }

         if (symbols) {
            keyList.forEach(k -> {
               if (k instanceof Symbol) {
                  list.add(k);
               }
            });
         }

         return IteratorUtil.concatLists(indices, list);
      }
   }

   @CompilerDirectives.TruffleBoundary
   protected static List<Object> ownPropertyKeysSlowArray(JSDynamicObject thisObj, boolean strings, boolean symbols) {
      List<Object> list = new ArrayList<>();
      if (strings) {
         ScriptArray array = arrayGetArrayType(thisObj);

         for (long currentIndex = array.firstElementIndex(thisObj);
            currentIndex <= array.lastElementIndex(thisObj);
            currentIndex = array.nextElementIndex(thisObj, currentIndex)
         ) {
            list.add(Strings.fromLong(currentIndex));
         }
      }

      List<Object> keyList = thisObj.getShape().getKeyList();
      if (!keyList.isEmpty()) {
         if (strings) {
            int before = list.size();
            keyList.forEach(k -> {
               if (Strings.isTString(k) && JSRuntime.isArrayIndexString((TruffleString)k)) {
                  list.add(k);
               }
            });
            int after = list.size();
            if (after != before) {
               Collections.sort(list, (o1, o2) -> Long.compare(JSRuntime.propertyKeyToArrayIndex(o1), JSRuntime.propertyKeyToArrayIndex(o2)));
            }

            keyList.forEach(k -> {
               if (Strings.isTString(k) && !JSRuntime.isArrayIndexString((TruffleString)k)) {
                  list.add(k);
               }
            });
         }

         if (symbols) {
            keyList.forEach(k -> {
               if (k instanceof Symbol) {
                  list.add(k);
               }
            });
         }
      }

      return list;
   }

   protected static long toArrayLengthOrRangeError(Object obj) {
      Number len = JSRuntime.toNumber(obj);
      Number len32 = JSRuntime.toUInt32(len);
      Number numberLen = JSRuntime.toNumber(obj);
      return toArrayLengthOrRangeError(numberLen, len32);
   }

   public static long toArrayLengthOrRangeError(Number len, Number len32) {
      double d32 = JSRuntime.doubleValue(len32);
      double d = JSRuntime.doubleValue(len);
      if (d32 == d) {
         return JSRuntime.longValue(len32);
      } else if (d == 0.0) {
         return 0L;
      } else {
         throw Errors.createRangeErrorInvalidArrayLength();
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor descriptor, boolean doThrow) {
      if (Strings.isTString(key) && Strings.equals(LENGTH, (TruffleString)key)) {
         return this.defineOwnPropertyLength(thisObj, descriptor, doThrow);
      } else {
         return Strings.isTString(key) && JSRuntime.isArrayIndexString((TruffleString)key)
            ? this.defineOwnPropertyIndex(thisObj, (TruffleString)key, descriptor, doThrow)
            : super.defineOwnProperty(thisObj, key, descriptor, doThrow);
      }
   }

   private boolean defineOwnPropertyLength(JSDynamicObject thisObj, PropertyDescriptor descriptor, boolean doThrow) {
      if (!descriptor.hasValue()) {
         boolean success = DefinePropertyUtil.ordinaryDefineOwnProperty(thisObj, LENGTH, descriptor, doThrow);
         if (success && descriptor.hasWritable() && !descriptor.getWritable()) {
            setLengthNotWritable(thisObj);
         }

         return success;
      } else {
         long newLen = JSRuntime.toUInt32(descriptor.getValue());
         Number numberLen = JSRuntime.toNumber(descriptor.getValue());
         if (JSRuntime.doubleValue(numberLen) != newLen) {
            throw Errors.createRangeErrorInvalidArrayLength();
         } else {
            PropertyDescriptor lenDesc = this.getOwnProperty(thisObj, LENGTH);
            if (newLen >= this.getLength(thisObj)) {
               return this.definePropertyLength(thisObj, descriptor, lenDesc, newLen, doThrow);
            } else if (!lenDesc.getWritable()) {
               return DefinePropertyUtil.reject(doThrow, "array length is not writable");
            } else {
               long pos = this.getLength(thisObj);
               if (!this.definePropertyLength(thisObj, descriptor, lenDesc, newLen, doThrow)) {
                  return false;
               } else {
                  return JSSlowArray.isJSSlowArray(thisObj) ? this.deleteElementsAfterShortening(thisObj, descriptor, doThrow, newLen, lenDesc, pos) : true;
               }
            }
         }
      }
   }

   private static void setLengthNotWritable(JSDynamicObject thisObj) {
      arraySetArrayType(thisObj, arrayGetArrayType(thisObj).setLengthNotWritable());
   }

   private boolean deleteElementsAfterShortening(
      JSDynamicObject thisObj, PropertyDescriptor descriptor, boolean doThrow, long newLen, PropertyDescriptor lenDesc, long startPos
   ) {
      assert JSRuntime.isValidArrayLength(newLen);

      long pos = startPos;

      while (pos > newLen) {
         Object key = Strings.fromLong(--pos);
         Property prop = DefinePropertyUtil.getPropertyByKey(thisObj, key);
         if (prop != null) {
            if (!JSProperty.isConfigurable(prop)) {
               long len = pos + 1L;
               descriptor.setValue(JSRuntime.longToIntOrDouble(len));
               this.definePropertyLength(thisObj, descriptor, lenDesc, len, doThrow);
               DefinePropertyUtil.ordinaryDefineOwnProperty(thisObj, LENGTH, descriptor, false);
               return DefinePropertyUtil.reject(doThrow, "cannot set the length to expected value");
            }

            this.delete(thisObj, key, doThrow);
         }
      }

      return true;
   }

   private boolean definePropertyLength(JSDynamicObject thisObj, PropertyDescriptor descriptor, PropertyDescriptor currentDesc, long len, boolean doThrow) {
      assert JSRuntime.isValidArrayLength(len);

      assert !currentDesc.getConfigurable();

      boolean currentWritable = currentDesc.getWritable();
      boolean currentEnumerable = currentDesc.getEnumerable();
      boolean newWritable = descriptor.getIfHasWritable(currentWritable);
      boolean newEnumerable = descriptor.getIfHasEnumerable(currentEnumerable);
      boolean newConfigurable = descriptor.getIfHasConfigurable(false);
      if (!newConfigurable && newEnumerable == currentEnumerable) {
         if (currentWritable != newWritable || currentEnumerable != newEnumerable || descriptor.hasValue() && len != this.getLength(thisObj)) {
            if (!currentWritable) {
               return DefinePropertyUtil.reject(doThrow, "length property not writable");
            } else {
               try {
                  this.setLength(thisObj, len, doThrow);
               } finally {
                  int newAttr = JSAttributes.fromConfigurableEnumerableWritable(newConfigurable, newEnumerable, newWritable);
                  JSObjectUtil.changePropertyFlags(thisObj, LENGTH, newAttr);
               }

               if (!newWritable) {
                  setLengthNotWritable(thisObj);
               }

               return true;
            }
         } else {
            return true;
         }
      } else {
         return DefinePropertyUtil.reject(doThrow, "Cannot redefine property: length");
      }
   }

   protected boolean defineOwnPropertyIndex(JSDynamicObject thisObj, TruffleString name, PropertyDescriptor descriptor, boolean doThrow) {
      assert Strings.isTString(name);

      long index = JSRuntime.toUInt32(name);
      if (index >= this.getLength(thisObj)) {
         PropertyDescriptor lenDesc = this.getOwnProperty(thisObj, LENGTH);
         if (!lenDesc.getWritable()) {
            DefinePropertyUtil.reject(doThrow, "array length is not writable");
         }
      }

      boolean wasNotExtensible = !JSShape.isExtensible(thisObj.getShape());
      boolean success = JSObject.defineOwnProperty(this.makeSlowArray(thisObj), name, descriptor, doThrow);

      assert !wasNotExtensible || !JSShape.isExtensible(thisObj.getShape());

      return success;
   }

   protected JSDynamicObject makeSlowArray(JSDynamicObject thisObj) {
      CompilerAsserts.neverPartOfCompilation("do not convert to slow array from compiled code");
      if (this.isSlowArray(thisObj)) {
         return thisObj;
      } else {
         assert !JSSlowArray.isJSSlowArray(thisObj);

         JSDynamicObject.setJSClass(thisObj, JSSlowArray.INSTANCE);
         JSContext context = JSObject.getJSContext(thisObj);
         context.getFastArrayAssumption().invalidate("create slow ArgumentsObject");
         if (isArrayPrototype(thisObj)) {
            context.getArrayPrototypeNoElementsAssumption().invalidate("Array.prototype has no elements");
         }

         assert JSSlowArray.isJSSlowArray(thisObj);

         return thisObj;
      }
   }

   private static boolean isArrayPrototype(JSDynamicObject thisObj) {
      return arrayGetArrayType(thisObj) instanceof ConstantEmptyPrototypeArray;
   }

   @Override
   public boolean testIntegrityLevel(JSDynamicObject thisObj, boolean frozen) {
      ScriptArray array = arrayGetArrayType(thisObj);
      boolean arrayIs = frozen ? array.isFrozen() : array.isSealed();
      return arrayIs && JSNonProxy.testIntegrityLevelFast(thisObj, frozen);
   }

   @Override
   public boolean setIntegrityLevel(JSDynamicObject thisObj, boolean freeze, boolean doThrow) {
      if (this.testIntegrityLevel(thisObj, freeze)) {
         return true;
      } else {
         ScriptArray arr = arrayGetArrayType(thisObj);
         arraySetArrayType(thisObj, freeze ? arr.freeze() : arr.seal());
         return super.setIntegrityLevelFast(thisObj, freeze);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final boolean preventExtensions(JSDynamicObject thisObj, boolean doThrow) {
      boolean result = super.preventExtensions(thisObj, doThrow);
      ScriptArray arr = arrayGetArrayType(thisObj);
      arraySetArrayType(thisObj, arr.preventExtensions());

      assert !this.isExtensible(thisObj);

      return result;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
      long index = JSRuntime.propertyKeyToArrayIndex(key);
      return index >= 0L ? this.delete(thisObj, index, isStrict) : super.delete(thisObj, key, isStrict);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean setPrototypeOf(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
      JSObject.getJSContext(thisObj).getArrayPrototypeNoElementsAssumption().invalidate("Array.prototype no element assumption");
      return super.setPrototypeOf(thisObj, newPrototype);
   }

   @Override
   public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
      return ordinaryGetOwnPropertyArray(thisObj, key);
   }

   @CompilerDirectives.TruffleBoundary
   public static PropertyDescriptor ordinaryGetOwnPropertyArray(JSDynamicObject thisObj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      long idx = JSRuntime.propertyKeyToArrayIndex(key);
      if (JSRuntime.isArrayIndex(idx)) {
         ScriptArray array = arrayGetArrayType(thisObj);
         if (array.hasElement(thisObj, idx)) {
            Object value = array.getElement(thisObj, idx);
            return PropertyDescriptor.createData(value, true, !array.isFrozen(), !array.isSealed());
         }
      }

      Property prop = thisObj.getShape().getProperty(key);
      return prop == null ? null : JSNonProxy.ordinaryGetOwnPropertyIntl(thisObj, key, prop);
   }

   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      return JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()
         ? this.defaultToString(obj)
         : JSRuntime.objectToDisplayString(obj, allowSideEffects, format, depth, null);
   }

   protected boolean isSlowArray(JSDynamicObject thisObj) {
      return JSSlowArray.isJSSlowArray(thisObj);
   }

   @Override
   public boolean usesOrdinaryGetOwnProperty() {
      return false;
   }

   static final class DefaultJSArrayComparator implements Comparator<Object> {
      @Override
      public int compare(Object arg0, Object arg1) {
         if (arg0 == Undefined.instance) {
            return arg1 == Undefined.instance ? 0 : 1;
         } else if (arg1 == Undefined.instance) {
            return -1;
         } else {
            TruffleString str0 = JSRuntime.toString(arg0);
            TruffleString str1 = JSRuntime.toString(arg1);
            if (str0 == null) {
               return str1 == null ? 0 : 1;
            } else {
               return str1 == null ? -1 : Strings.compareTo(str0, str1);
            }
         }
      }
   }

   static final class DefaultJSArrayDoubleComparator implements Comparator<Object> {
      @Override
      public int compare(Object arg0, Object arg1) {
         double d1 = JSRuntime.doubleValue((Number)arg0);
         double d2 = JSRuntime.doubleValue((Number)arg1);
         if (d1 == d2) {
            return 0;
         } else if (d1 <= 0.0 && d2 > 0.0) {
            return -1;
         } else if (d2 <= 0.0 && d1 > 0.0) {
            return 1;
         } else {
            TruffleString str0 = JSRuntime.doubleToString(d1);
            TruffleString str1 = JSRuntime.doubleToString(d2);
            return Strings.compareTo(str0, str1);
         }
      }
   }

   static final class DefaultJSArrayIntegerComparator implements Comparator<Object> {
      @Override
      public int compare(Object arg0, Object arg1) {
         int i1 = (int)JSRuntime.toInteger((Number)arg0);
         int i2 = (int)JSRuntime.toInteger((Number)arg1);
         if (i1 == i2) {
            return 0;
         } else if (i1 <= 0 && i2 > 0) {
            return -1;
         } else if (i2 <= 0 && i1 > 0) {
            return 1;
         } else {
            TruffleString str0 = Strings.fromInt(i1);
            TruffleString str1 = Strings.fromInt(i2);
            return Strings.compareTo(str0, str1);
         }
      }
   }
}
