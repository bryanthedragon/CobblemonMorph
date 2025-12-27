package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ArrayFunctionBuiltins;
import com.oracle.truffle.js.builtins.ArrayPrototypeBuiltins;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.SparseArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantByteArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantEmptyPrototypeArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantIntArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.LazyArray;
import com.oracle.truffle.js.runtime.array.dyn.LazyRegexResultArray;
import com.oracle.truffle.js.runtime.array.dyn.LazyRegexResultIndicesArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedIntArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedObjectArray;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import java.util.ArrayList;
import java.util.List;

public final class JSArray extends JSAbstractArray implements JSConstructorFactory.WithFunctionsAndSpecies, PrototypeSupplier {
   public static final TruffleString CLASS_NAME = Strings.constant("Array");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("Array.prototype");
   public static final TruffleString ITERATOR_CLASS_NAME = Strings.constant("Array Iterator");
   public static final TruffleString ITERATOR_PROTOTYPE_NAME = Strings.constant("Array Iterator.prototype");
   public static final TruffleString ENTRIES = Strings.constant("entries");
   public static final JSArray INSTANCE = new JSArray();
   static final JSArray.ArrayLengthProxyProperty ARRAY_LENGTH_PROPERTY_PROXY = new JSArray.ArrayLengthProxyProperty();
   public static final HiddenKey ARRAY_ITERATION_KIND_ID = new HiddenKey("ArrayIterationKind");

   private JSArray() {
   }

   public static JSArrayObject createConstant(JSContext context, JSRealm realm, Object[] elements) {
      return create(context, realm, ScriptArray.createConstantArray(elements), elements, elements.length);
   }

   public static JSArrayObject createEmpty(JSContext context, JSRealm realm, int length) {
      if (length < 0) {
         throw Errors.createRangeErrorInvalidArrayLength();
      } else {
         return createEmptyChecked(context, realm, length);
      }
   }

   private static JSArrayObject createEmptyChecked(JSContext context, JSRealm realm, int length) {
      return createConstantEmptyArray(context, realm, length);
   }

   public static JSArrayObject createEmpty(JSContext context, JSRealm realm, long length) {
      if (!JSRuntime.isValidArrayLength(length)) {
         throw Errors.createRangeErrorInvalidArrayLength();
      } else {
         return length > 2147483647L ? createSparseArray(context, realm, length) : createEmptyChecked(context, realm, length);
      }
   }

   public static JSArrayObject createEmptyChecked(JSContext context, JSRealm realm, long length) {
      assert 0L <= length && length <= 2147483647L;

      return createConstantEmptyArray(context, realm, (int)length);
   }

   public static JSArrayObject createEmptyZeroLength(JSContext context, JSRealm realm) {
      return createConstantEmptyArray(context, realm);
   }

   public static JSArrayObject create(JSContext context, JSRealm realm, ScriptArray arrayType, Object array, long length) {
      return create(context, realm, arrayType, array, length, 0);
   }

   public static JSArrayObject create(JSContext context, JSRealm realm, ScriptArray arrayType, Object array, long length, int usedLength) {
      return create(context, realm, arrayType, array, length, usedLength, 0, 0);
   }

   public static JSArrayObject create(
      JSContext context, JSRealm realm, ScriptArray arrayType, Object array, long length, int usedLength, int indexOffset, int arrayOffset
   ) {
      return create(context, realm, arrayType, array, length, usedLength, indexOffset, arrayOffset, 0);
   }

   public static JSArrayObject create(
      JSContext context, JSRealm realm, ScriptArray arrayType, Object array, long length, int usedLength, int indexOffset, int arrayOffset, int holeCount
   ) {
      return createImpl(context, realm, arrayType, array, null, length, usedLength, indexOffset, arrayOffset, holeCount);
   }

   public static JSArrayObject create(
      JSContext context,
      JSRealm realm,
      ScriptArray arrayType,
      Object array,
      ArrayAllocationSite site,
      long length,
      int usedLength,
      int indexOffset,
      int arrayOffset,
      int holeCount
   ) {
      return createImpl(context, realm, arrayType, array, site, length, usedLength, indexOffset, arrayOffset, holeCount);
   }

   private static JSArrayObject createImpl(
      JSContext context,
      JSRealm realm,
      ScriptArray arrayType,
      Object array,
      ArrayAllocationSite site,
      long length,
      int usedLength,
      int indexOffset,
      int arrayOffset,
      int holeCount
   ) {
      assert JSRuntime.isRepresentableAsUnsignedInt(length);

      JSObjectFactory factory = context.getArrayFactory();
      JSArrayObject obj = JSArrayObject.create(factory.getShape(realm), arrayType, array, site, length, usedLength, indexOffset, arrayOffset, holeCount);
      factory.initProto(obj, realm);
      return context.trackAllocation(obj);
   }

   public static boolean isJSArray(Object obj) {
      return obj instanceof JSArrayObject;
   }

   public static boolean isJSFastArray(Object obj) {
      return isJSArray(obj) && isJSFastArray((JSDynamicObject)((JSArrayObject)obj));
   }

   public static boolean isJSFastArray(JSDynamicObject obj) {
      return isInstance(obj, INSTANCE);
   }

   @Override
   public TruffleString getClassName() {
      return CLASS_NAME;
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return this.getClassName();
   }

   @Override
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
      JSContext ctx = realm.getContext();
      Shape protoShape = JSShape.createPrototypeShape(ctx, INSTANCE, realm.getObjectPrototype());
      JSObject arrayPrototype = JSArrayObject.createEmpty(protoShape, ConstantEmptyPrototypeArray.createConstantEmptyPrototypeArray());
      JSObjectUtil.setOrVerifyPrototype(ctx, arrayPrototype, realm.getObjectPrototype());
      JSObjectUtil.putConstructorProperty(ctx, arrayPrototype, ctor);
      JSObjectUtil.putFunctionsFromContainer(realm, arrayPrototype, ArrayPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putProxyProperty(arrayPrototype, LENGTH, ARRAY_LENGTH_PROPERTY_PROXY, JSAttributes.notConfigurableNotEnumerableWritable());
      if (ctx.getEcmaScriptVersion() >= 6) {
         JSObjectUtil.putDataProperty(
            ctx, arrayPrototype, Symbol.SYMBOL_ITERATOR, JSDynamicObject.getOrNull(arrayPrototype, Strings.VALUES), JSAttributes.getDefaultNotEnumerable()
         );
         JSObjectUtil.putDataProperty(
            ctx,
            arrayPrototype,
            Symbol.SYMBOL_UNSCOPABLES,
            createUnscopables(ctx, unscopableNameList(ctx)),
            JSAttributes.configurableNotEnumerableNotWritable()
         );
      }

      return arrayPrototype;
   }

   private static List<TruffleString> unscopableNameList(JSContext context) {
      List<TruffleString> names = new ArrayList<>();
      if (context.getEcmaScriptVersion() >= 13) {
         names.add(Strings.AT);
      }

      names.add(Strings.COPY_WITHIN);
      names.add(ENTRIES);
      names.add(Strings.FILL);
      names.add(Strings.FIND);
      names.add(Strings.FIND_INDEX);
      if (context.getEcmaScriptVersion() >= 14) {
         names.add(Strings.FIND_LAST);
         names.add(Strings.FIND_LAST_INDEX);
      }

      if (context.getEcmaScriptVersion() >= 10) {
         names.add(Strings.FLAT);
         names.add(Strings.FLAT_MAP);
      }

      if (context.getEcmaScriptVersion() >= 14) {
         names.add(Strings.GROUP);
         names.add(Strings.GROUP_TO_MAP);
      }

      if (context.getEcmaScriptVersion() >= 7) {
         names.add(Strings.INCLUDES);
      }

      names.add(Strings.KEYS);
      names.add(Strings.VALUES);

      assert isSorted(names);

      return names;
   }

   private static boolean isSorted(List<TruffleString> list) {
      for (int i = 0; i < list.size() - 1; i++) {
         if (list.get(i).compareCharsUTF16Uncached(list.get(i + 1)) > 0) {
            return false;
         }
      }

      return true;
   }

   private static JSObject createUnscopables(JSContext context, List<TruffleString> unscopableNames) {
      JSObject unscopables = JSOrdinary.createWithNullPrototypeInit(context);

      for (Object name : unscopableNames) {
         JSObjectUtil.putDataProperty(context, unscopables, name, true, JSAttributes.getDefault());
      }

      return unscopables;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      Shape initialShape = JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
      return Shape.newBuilder(initialShape)
         .addConstantProperty(LENGTH, ARRAY_LENGTH_PROPERTY_PROXY, JSAttributes.notConfigurableNotEnumerableWritable() | 16)
         .build();
   }

   @Override
   public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
      return ownPropertyKeysFastArray(thisObj, strings, symbols);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, ArrayFunctionBuiltins.BUILTINS);
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean setLength(JSDynamicObject store, Object value) {
      long arrLength = 0L;
      if (value instanceof Integer && (Integer)value >= 0) {
         arrLength = ((Integer)value).intValue();
      } else {
         arrLength = toArrayLengthOrRangeError(value);
      }

      assert arrLength >= 0L;

      return !JSAbstractArray.arrayGetArrayType(store).isLengthNotWritable()
         && ((JSAbstractArray)JSObject.getJSClass(store)).setLength(store, arrLength, false);
   }

   public static JSArrayObject createConstantEmptyArray(JSContext context, JSRealm realm, int capacity) {
      ScriptArray arrayType = ScriptArray.createConstantEmptyArray();
      return create(context, realm, arrayType, ScriptArray.EMPTY_OBJECT_ARRAY, capacity);
   }

   public static JSArrayObject createConstantEmptyArray(JSContext context, JSRealm realm) {
      return createConstantEmptyArray(context, realm, 0);
   }

   public static JSArrayObject createConstantEmptyArray(JSContext context, JSRealm realm, ArrayAllocationSite site) {
      return createConstantEmptyArray(context, realm, site, 0);
   }

   public static JSArrayObject createConstantEmptyArray(JSContext context, JSRealm realm, ArrayAllocationSite site, int capacity) {
      ScriptArray arrayType = ScriptArray.createConstantEmptyArray();
      return create(context, realm, arrayType, ScriptArray.EMPTY_OBJECT_ARRAY, site, capacity, 0, 0, 0, 0);
   }

   public static JSArrayObject createConstantByteArray(JSContext context, JSRealm realm, byte[] byteArray) {
      ScriptArray arrayType = ConstantByteArray.createConstantByteArray();
      return create(context, realm, arrayType, byteArray, byteArray.length);
   }

   public static JSArrayObject createConstantIntArray(JSContext context, JSRealm realm, int[] intArray) {
      ScriptArray arrayType = ConstantIntArray.createConstantIntArray();
      return create(context, realm, arrayType, intArray, intArray.length);
   }

   public static JSArrayObject createConstantDoubleArray(JSContext context, JSRealm realm, double[] doubleArray) {
      ScriptArray arrayType = ConstantDoubleArray.createConstantDoubleArray();
      return create(context, realm, arrayType, doubleArray, doubleArray.length);
   }

   public static JSArrayObject createConstantObjectArray(JSContext context, JSRealm realm, Object[] objectArray) {
      ScriptArray arrayType = ConstantObjectArray.createConstantObjectArray();
      return create(context, realm, arrayType, objectArray, objectArray.length);
   }

   public static JSArrayObject createZeroBasedHolesObjectArray(
      JSContext context, JSRealm realm, Object[] objectArray, int usedLength, int arrayOffset, int holeCount
   ) {
      return create(context, realm, HolesObjectArray.createHolesObjectArray(), objectArray, objectArray.length, usedLength, 0, arrayOffset, holeCount);
   }

   public static JSArrayObject createZeroBasedIntArray(JSContext context, JSRealm realm, int[] intArray) {
      return create(context, realm, ZeroBasedIntArray.createZeroBasedIntArray(), intArray, intArray.length, intArray.length, 0, 0);
   }

   public static JSArrayObject createZeroBasedDoubleArray(JSContext context, JSRealm realm, double[] doubleArray) {
      return create(context, realm, ZeroBasedDoubleArray.createZeroBasedDoubleArray(), doubleArray, doubleArray.length, doubleArray.length, 0, 0);
   }

   public static JSArrayObject createZeroBasedObjectArray(JSContext context, JSRealm realm, Object[] objectArray) {
      return create(context, realm, ZeroBasedObjectArray.createZeroBasedObjectArray(), objectArray, objectArray.length, objectArray.length, 0, 0);
   }

   public static JSArrayObject createZeroBasedJSObjectArray(JSContext context, JSRealm realm, JSDynamicObject[] objectArray) {
      return create(context, realm, ZeroBasedJSObjectArray.createZeroBasedJSObjectArray(), objectArray, objectArray.length, objectArray.length, 0, 0);
   }

   public static JSArrayObject createSparseArray(JSContext context, JSRealm realm, long length) {
      return create(context, realm, SparseArray.createSparseArray(), SparseArray.createArrayMap(), length);
   }

   public static JSArrayObject createLazyRegexArray(JSContext context, JSRealm realm, int length) {
      assert JSRuntime.isRepresentableAsUnsignedInt((long)length);

      Object[] array = new Object[length];
      return create(context, realm, LazyRegexResultArray.createLazyRegexResultArray(), array, length);
   }

   public static JSArrayObject createLazyRegexArray(
      JSContext context, JSRealm realm, int length, Object regexResult, TruffleString input, JSDynamicObject groups, JSDynamicObject indicesGroups
   ) {
      assert JSRuntime.isRepresentableAsUnsignedInt((long)length);

      JSArrayObject obj = createLazyRegexArray(context, realm, length);
      JSObjectUtil.putHiddenProperty(obj, LAZY_REGEX_RESULT_ID, regexResult);
      JSObjectUtil.putHiddenProperty(obj, LAZY_REGEX_ORIGINAL_INPUT_ID, input);
      JSObjectUtil.putProxyProperty(obj, JSRegExp.INDEX, JSRegExp.LAZY_INDEX_PROXY, JSAttributes.getDefault());
      JSObjectUtil.putDataProperty(context, obj, JSRegExp.INPUT, input, JSAttributes.getDefault());
      JSObjectUtil.putDataProperty(context, obj, JSRegExp.GROUPS, groups, JSAttributes.getDefault());
      if (context.isOptionRegexpMatchIndices()) {
         JSArrayObject indices = createLazyRegexIndicesArray(context, realm, length, regexResult, indicesGroups);
         JSObjectUtil.putDataProperty(context, obj, JSRegExp.INDICES, indices, JSAttributes.getDefault());
      }

      assert isJSArray(obj);

      return obj;
   }

   public static JSArrayObject createLazyRegexIndicesArray(JSContext context, JSRealm realm, int length) {
      assert JSRuntime.isRepresentableAsUnsignedInt((long)length);

      Object[] array = new Object[length];
      return create(context, realm, LazyRegexResultIndicesArray.createLazyRegexResultIndicesArray(), array, length);
   }

   private static JSArrayObject createLazyRegexIndicesArray(JSContext context, JSRealm realm, int length, Object regexResult, JSDynamicObject indicesGroups) {
      assert JSRuntime.isRepresentableAsUnsignedInt((long)length);

      Object[] array = new Object[length];
      JSArrayObject obj = create(context, realm, LazyRegexResultIndicesArray.createLazyRegexResultIndicesArray(), array, length);
      JSObjectUtil.putHiddenProperty(obj, LAZY_REGEX_RESULT_ID, regexResult);
      JSObjectUtil.putDataProperty(context, obj, JSRegExp.GROUPS, indicesGroups, JSAttributes.getDefault());

      assert isJSArray(obj);

      return obj;
   }

   public static JSArrayObject createLazyArray(JSContext context, JSRealm realm, List<?> list, int size) {
      assert list.size() == size;

      return create(context, realm, LazyArray.createLazyArray(), list, size);
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getArrayPrototype();
   }

   public static final class ArrayLengthProxyProperty extends PropertyProxy {
      @Override
      public Object get(JSDynamicObject store) {
         assert JSArray.isJSArray(store);

         long length = JSArray.INSTANCE.getLength(store);
         return (double)length;
      }

      @Override
      public boolean set(JSDynamicObject store, Object value) {
         assert JSArray.isJSArray(store);

         return JSArray.setLength(store, value);
      }
   }
}
