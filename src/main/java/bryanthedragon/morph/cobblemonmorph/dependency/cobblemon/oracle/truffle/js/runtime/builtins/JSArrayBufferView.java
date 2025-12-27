package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ConstructorBuiltins;
import com.oracle.truffle.js.builtins.TypedArrayFunctionBuiltins;
import com.oracle.truffle.js.builtins.TypedArrayPrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.array.TypedArrayFactory;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.DirectByteBufferHelper;
import com.oracle.truffle.js.runtime.util.IteratorUtil;
import java.nio.ByteBuffer;
import java.util.List;

public final class JSArrayBufferView extends JSNonProxy {
   public static final TruffleString CLASS_NAME = Strings.constant("TypedArray");
   public static final TruffleString PROTOTYPE_NAME = Strings.concat(CLASS_NAME, Strings.DOT_PROTOTYPE);
   private static final TruffleString BYTES_PER_ELEMENT = Strings.constant("BYTES_PER_ELEMENT");
   private static final TruffleString BYTE_LENGTH = Strings.constant("byteLength");
   private static final TruffleString LENGTH = JSAbstractArray.LENGTH;
   private static final TruffleString BUFFER = Strings.constant("buffer");
   private static final TruffleString BYTE_OFFSET = Strings.constant("byteOffset");
   private static final TruffleString GET_SYMBOL_TO_STRING_TAG_NAME = Strings.constant("get [Symbol.toStringTag]");
   public static final JSArrayBufferView INSTANCE = new JSArrayBufferView();

   private static TypedArrayAccess typedArray() {
      return TypedArrayAccess.SINGLETON;
   }

   public static TypedArray typedArrayGetArrayType(JSDynamicObject thisObj) {
      assert isJSArrayBufferView(thisObj);

      return typedArray().getArrayType(thisObj);
   }

   public static int typedArrayGetLength(JSDynamicObject thisObj) {
      return typedArray().getLength(thisObj);
   }

   public static int typedArrayGetOffset(JSDynamicObject thisObj) {
      return typedArray().getOffset(thisObj);
   }

   public static byte[] typedArrayGetByteArray(JSDynamicObject thisObj) {
      return typedArray().getByteArray(thisObj);
   }

   public static ByteBuffer typedArrayGetByteBuffer(JSDynamicObject thisObj) {
      return DirectByteBufferHelper.cast(typedArray().getByteBuffer(thisObj));
   }

   private static TruffleString typedArrayGetName(JSDynamicObject thisObj) {
      return typedArrayGetArrayType(thisObj).getName();
   }

   private JSArrayBufferView() {
   }

   public static JSArrayBufferObject getArrayBuffer(JSDynamicObject thisObj) {
      assert isJSArrayBufferView(thisObj);

      return typedArray().getArrayBuffer(thisObj);
   }

   public static int getByteLength(JSDynamicObject store, JSContext ctx) {
      assert isJSArrayBufferView(store);

      if (hasDetachedBuffer(store, ctx)) {
         return 0;
      } else {
         TypedArray typedArray = typedArrayGetArrayType(store);
         return typedArray.lengthInt(store) * typedArray.bytesPerElement();
      }
   }

   public static int getByteLength(JSDynamicObject store, JSContext ctx, ValueProfile profile) {
      assert isJSArrayBufferView(store);

      if (hasDetachedBuffer(store, ctx)) {
         return 0;
      } else {
         TypedArray typedArray = profile.profile(typedArrayGetArrayType(store));
         return typedArray.lengthInt(store) * typedArray.bytesPerElement();
      }
   }

   public static int getByteOffset(JSDynamicObject store, JSContext ctx) {
      assert isJSArrayBufferView(store);

      return hasDetachedBuffer(store, ctx) ? 0 : typedArrayGetOffset(store);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getHelper(JSDynamicObject store, Object receiver, long index, Node encapsulatingNode) {
      return this.getOwnHelper(store, receiver, index, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object receiver, long index, Node encapsulatingNode) {
      return hasDetachedBuffer(store) ? Undefined.instance : typedArrayGetArrayType(store).getElement(store, index);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getHelper(JSDynamicObject store, Object receiver, Object key, Node encapsulatingNode) {
      assert JSRuntime.isPropertyKey(key);

      if (Strings.isTString(key)) {
         Object numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key);
         if (numericIndex != Undefined.instance) {
            return integerIndexedElementGet(store, numericIndex);
         }
      }

      return super.getHelper(store, receiver, key, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object receiver, Object key, Node encapsulatingNode) {
      assert JSRuntime.isPropertyKey(key);

      if (Strings.isTString(key)) {
         Object numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key);
         if (numericIndex != Undefined.instance) {
            return integerIndexedElementGet(store, numericIndex);
         }
      }

      return super.getOwnHelper(store, receiver, key, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   private static Object integerIndexedElementGet(JSDynamicObject thisObj, Object numericIndex) {
      assert JSRuntime.isNumber(numericIndex);

      if (hasDetachedBuffer(thisObj)) {
         return Undefined.instance;
      } else if (!JSRuntime.isInteger(numericIndex)) {
         return Undefined.instance;
      } else if (numericIndex instanceof Double && JSRuntime.isNegativeZero((Double)numericIndex)) {
         return Undefined.instance;
      } else {
         long index = ((Number)numericIndex).longValue();
         int length = typedArrayGetLength(thisObj);
         return index >= 0L && index < length ? typedArrayGetArrayType(thisObj).getElement(thisObj, index) : Undefined.instance;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      if (thisObj == receiver) {
         Object numValue = convertValue(thisObj, value);
         if (!hasDetachedBuffer(thisObj)) {
            typedArrayGetArrayType(thisObj).setElement(thisObj, index, numValue, isStrict);
         }

         return true;
      } else {
         return !isValidIntegerIndex(thisObj, index) ? true : super.set(thisObj, index, value, receiver, isStrict, encapsulatingNode);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      assert JSRuntime.isPropertyKey(key);

      if (Strings.isTString(key)) {
         Object numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key);
         if (numericIndex != Undefined.instance) {
            if (thisObj == receiver) {
               Object numValue = convertValue(thisObj, value);
               long index = validIntegerIndex(thisObj, (Number)numericIndex);
               if (index != -1L) {
                  typedArrayGetArrayType(thisObj).setElement(thisObj, index, numValue, isStrict);
               }

               return true;
            }

            if (!isValidIntegerIndex(thisObj, (Number)numericIndex)) {
               return true;
            }
         }
      }

      return super.set(thisObj, key, value, receiver, isStrict, encapsulatingNode);
   }

   public static boolean isValidIntegerIndex(JSDynamicObject thisObj, Number numericIndex) {
      return validIntegerIndex(thisObj, numericIndex) != -1L;
   }

   @CompilerDirectives.TruffleBoundary
   private static long validIntegerIndex(JSDynamicObject thisObj, Number numericIndex) {
      if (hasDetachedBuffer(thisObj)) {
         return -1L;
      } else if (!JSRuntime.isInteger(numericIndex)) {
         return -1L;
      } else if (numericIndex instanceof Double && JSRuntime.isNegativeZero((Double)numericIndex)) {
         return -1L;
      } else {
         int length = typedArrayGetLength(thisObj);
         long index = numericIndex.longValue();
         return 0L <= index && index < length ? index : -1L;
      }
   }

   private static Object convertValue(JSDynamicObject thisObj, Object value) {
      return isBigIntArrayBufferView(thisObj) ? JSRuntime.toBigInt(value) : JSRuntime.toNumber(value);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasProperty(JSDynamicObject thisObj, long index) {
      return this.hasOwnProperty(thisObj, index);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasProperty(JSDynamicObject thisObj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      if (Strings.isTString(key)) {
         Object numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key);
         if (numericIndex != Undefined.instance) {
            return hasNumericIndex(thisObj, numericIndex);
         }
      }

      return super.hasProperty(thisObj, key);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasOwnProperty(JSDynamicObject thisObj, long index) {
      return hasDetachedBuffer(thisObj) ? false : typedArrayGetArrayType(thisObj).hasElement(thisObj, index);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      if (Strings.isTString(key)) {
         Object numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key);
         if (numericIndex != Undefined.instance) {
            return hasNumericIndex(thisObj, numericIndex);
         }
      }

      return super.hasOwnProperty(thisObj, key);
   }

   private static boolean hasNumericIndex(JSDynamicObject thisObj, Object numericIndex) {
      if (hasDetachedBuffer(thisObj)) {
         return false;
      } else if (!JSRuntime.isInteger(numericIndex)) {
         return false;
      } else {
         double d = JSRuntime.doubleValue((Number)numericIndex);
         return !JSRuntime.isNegativeZero(d) && !(d < 0.0) ? d < typedArrayGetLength(thisObj) : false;
      }
   }

   public static JSTypedArrayObject createArrayBufferView(
      JSContext context, JSRealm realm, JSDynamicObject arrayBuffer, TypedArray arrayType, int offset, int length
   ) {
      CompilerAsserts.partialEvaluationConstant(arrayType);

      assert JSArrayBuffer.isJSAbstractBuffer(arrayBuffer);

      if (!context.getTypedArrayNotDetachedAssumption().isValid() && JSArrayBuffer.isDetachedBuffer(arrayBuffer)) {
         throw Errors.createTypeErrorDetachedBuffer();
      } else {
         JSObjectFactory objectFactory = context.getArrayBufferViewFactory(arrayType.getFactory());
         return createArrayBufferView(context, realm, objectFactory, arrayBuffer, arrayType, offset, length);
      }
   }

   public static JSTypedArrayObject createArrayBufferView(
      JSContext context, JSRealm realm, JSObjectFactory objectFactory, JSDynamicObject arrayBuffer, TypedArray arrayType, int offset, int length
   ) {
      return createArrayBufferView(context, realm, objectFactory, arrayBuffer, arrayType, offset, length, objectFactory.getPrototype(realm));
   }

   public static JSTypedArrayObject createArrayBufferViewWithProto(
      JSContext context,
      JSRealm realm,
      JSObjectFactory objectFactory,
      JSDynamicObject arrayBuffer,
      TypedArray arrayType,
      int offset,
      int length,
      JSDynamicObject prototype
   ) {
      return createArrayBufferView(context, realm, objectFactory, arrayBuffer, arrayType, offset, length, prototype);
   }

   private static JSTypedArrayObject createArrayBufferView(
      JSContext context,
      JSRealm realm,
      JSObjectFactory objectFactory,
      JSDynamicObject arrayBuffer,
      TypedArray arrayType,
      int offset,
      int length,
      JSDynamicObject prototype
   ) {
      assert !JSArrayBuffer.isDetachedBuffer(arrayBuffer);

      assert offset >= 0 && offset + length * arrayType.bytesPerElement() <= ((JSArrayBufferObject)arrayBuffer).getByteLength();

      assert offset != 0 == arrayType.hasOffset();

      JSTypedArrayObject obj = JSTypedArrayObject.create(objectFactory.getShape(realm), arrayType, (JSArrayBufferObject)arrayBuffer, length, offset);
      objectFactory.initProto(obj, prototype);
      return context.trackAllocation(obj);
   }

   private static JSObject createArrayBufferViewPrototype(
      JSRealm realm, JSDynamicObject ctor, int bytesPerElement, TypedArrayFactory factory, JSDynamicObject taPrototype
   ) {
      JSContext context = realm.getContext();
      JSObject prototype = context.getEcmaScriptVersion() >= 6
         ? JSObjectUtil.createOrdinaryPrototypeObject(realm, taPrototype)
         : createLegacyArrayBufferViewPrototype(realm, factory, taPrototype);
      JSObjectUtil.putDataProperty(context, prototype, BYTES_PER_ELEMENT, bytesPerElement, JSAttributes.notConfigurableNotEnumerableNotWritable());
      JSObjectUtil.putConstructorProperty(context, prototype, ctor);
      return prototype;
   }

   private static JSObject createLegacyArrayBufferViewPrototype(JSRealm realm, TypedArrayFactory factory, JSDynamicObject taPrototype) {
      JSContext context = realm.getContext();
      byte[] byteArray = new byte[0];
      JSObjectFactory bufferFactory = context.getArrayBufferFactory();
      JSArrayBufferObject emptyArrayBuffer = bufferFactory.initProto(JSArrayBufferObject.createHeapArrayBuffer(bufferFactory.getShape(realm), byteArray), realm);
      TypedArray arrayType = factory.createArrayType(context.isOptionDirectByteBuffer(), false);
      Shape shape = JSShape.createPrototypeShape(context, INSTANCE, taPrototype);
      JSObject prototype = JSTypedArrayObject.create(shape, arrayType, emptyArrayBuffer, 0, 0);
      JSObjectUtil.setOrVerifyPrototype(context, prototype, taPrototype);
      return prototype;
   }

   protected static void putArrayBufferViewPrototypeGetter(
      JSRealm realm, JSDynamicObject prototype, TruffleString key, JSContext.BuiltinFunctionKey functionKey, JSArrayBufferView.ArrayBufferViewGetter getter
   ) {
      assert JSRuntime.isPropertyKey(key);

      JSFunctionData lengthGetterData = realm.getContext()
         .getOrCreateBuiltinFunctionData(functionKey, c -> JSFunctionData.createCallOnly(c, (new JavaScriptRootNode(c.getLanguage(), null, null) {
            private final BranchProfile errorBranch = BranchProfile.create();

            @Override
            public Object execute(VirtualFrame frame) {
               Object obj = JSArguments.getThisObject(frame.getArguments());
               if (JSArrayBufferView.isJSArrayBufferView(obj)) {
                  return getter.apply((JSTypedArrayObject)obj);
               } else {
                  this.errorBranch.enter();
                  throw Errors.createTypeError("method called on incompatible receiver");
               }
            }
         }).getCallTarget(), 0, Strings.concat(Strings.GET_SPC, key)));
      JSDynamicObject lengthGetter = JSFunction.create(realm, lengthGetterData);
      JSObjectUtil.putBuiltinAccessorProperty(prototype, key, lengthGetter, Undefined.instance);
   }

   public static Shape makeInitialArrayBufferViewShape(JSContext ctx, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
   }

   public static JSConstructor createConstructor(JSRealm realm, TypedArrayFactory factory, JSConstructor taConstructor) {
      JSContext ctx = realm.getContext();
      JSFunctionObject arrayBufferViewConstructor = realm.lookupFunction(ConstructorBuiltins.BUILTINS, factory.getName());
      JSObject.setPrototype(arrayBufferViewConstructor, taConstructor.getFunctionObject());
      JSObject arrayBufferViewPrototype = createArrayBufferViewPrototype(
         realm, arrayBufferViewConstructor, factory.getBytesPerElement(), factory, taConstructor.getPrototype()
      );
      JSObjectUtil.putConstructorPrototypeProperty(ctx, arrayBufferViewConstructor, arrayBufferViewPrototype);
      JSObjectUtil.putDataProperty(
         ctx, arrayBufferViewConstructor, BYTES_PER_ELEMENT, factory.getBytesPerElement(), JSAttributes.notConfigurableNotEnumerableNotWritable()
      );
      putConstructorSpeciesGetter(realm, arrayBufferViewConstructor);
      return new JSConstructor(arrayBufferViewConstructor, arrayBufferViewPrototype);
   }

   private static JSObject createTypedArrayPrototype(final JSRealm realm, JSDynamicObject ctor) {
      final JSContext ctx = realm.getContext();
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(ctx, prototype, ctor);
      JSObjectUtil.putFunctionsFromContainer(realm, prototype, TypedArrayPrototypeBuiltins.BUILTINS);
      putArrayBufferViewPrototypeGetter(
         realm, prototype, LENGTH, JSContext.BuiltinFunctionKey.ArrayBufferViewLength, new JSArrayBufferView.ArrayBufferViewGetter() {
            private final ConditionProfile detachedBufferProfile = ConditionProfile.create();

            @Override
            public Object apply(JSDynamicObject view) {
               return this.detachedBufferProfile.profile(JSArrayBufferView.hasDetachedBuffer(view, ctx)) ? 0 : JSArrayBufferView.typedArrayGetLength(view);
            }
         }
      );
      putArrayBufferViewPrototypeGetter(
         realm, prototype, BUFFER, JSContext.BuiltinFunctionKey.ArrayBufferViewBuffer, new JSArrayBufferView.ArrayBufferViewGetter() {
            @Override
            public Object apply(JSDynamicObject view) {
               return JSArrayBufferView.getArrayBuffer(view);
            }
         }
      );
      putArrayBufferViewPrototypeGetter(
         realm, prototype, BYTE_LENGTH, JSContext.BuiltinFunctionKey.ArrayBufferViewByteLength, new JSArrayBufferView.ArrayBufferViewGetter() {
            @Override
            public Object apply(JSDynamicObject view) {
               return JSArrayBufferView.getByteLength(view, ctx);
            }
         }
      );
      putArrayBufferViewPrototypeGetter(
         realm, prototype, BYTE_OFFSET, JSContext.BuiltinFunctionKey.ArrayBufferViewByteByteOffset, new JSArrayBufferView.ArrayBufferViewGetter() {
            @Override
            public Object apply(JSDynamicObject view) {
               return JSArrayBufferView.getByteOffset(view, ctx);
            }
         }
      );
      JSFunctionData toStringData = realm.getContext()
         .getOrCreateBuiltinFunctionData(
            JSContext.BuiltinFunctionKey.ArrayBufferViewToString,
            c -> JSFunctionData.createCallOnly(ctx, (new JavaScriptRootNode(ctx.getLanguage(), null, null) {
               @Override
               public Object execute(VirtualFrame frame) {
                  Object obj = JSArguments.getThisObject(frame.getArguments());
                  return JSArrayBufferView.isJSArrayBufferView(obj) ? JSArrayBufferView.typedArrayGetName((JSTypedArrayObject)obj) : Undefined.instance;
               }
            }).getCallTarget(), 0, GET_SYMBOL_TO_STRING_TAG_NAME)
         );
      JSDynamicObject toStringTagGetter = JSFunction.create(realm, toStringData);
      JSObjectUtil.putBuiltinAccessorProperty(prototype, Symbol.SYMBOL_TO_STRING_TAG, toStringTagGetter, Undefined.instance);
      Object valuesFunction = JSDynamicObject.getOrNull(prototype, Strings.VALUES);
      JSObjectUtil.putDataProperty(ctx, prototype, Symbol.SYMBOL_ITERATOR, valuesFunction, JSAttributes.getDefaultNotEnumerable());
      Object toStringFunction = JSDynamicObject.getOrNull(realm.getArrayPrototype(), Strings.TO_STRING);
      JSObjectUtil.putDataProperty(ctx, prototype, Strings.TO_STRING, toStringFunction, JSAttributes.getDefaultNotEnumerable());
      return prototype;
   }

   public static JSConstructor createTypedArrayConstructor(JSRealm realm) {
      JSContext ctx = realm.getContext();
      JSFunctionObject taConstructor = realm.lookupFunction(ConstructorBuiltins.BUILTINS, CLASS_NAME);
      JSObject taPrototype = createTypedArrayPrototype(realm, taConstructor);
      JSObjectUtil.putConstructorPrototypeProperty(ctx, taConstructor, taPrototype);
      JSObjectUtil.putFunctionsFromContainer(realm, taConstructor, TypedArrayFunctionBuiltins.BUILTINS);
      putConstructorSpeciesGetter(realm, taConstructor);
      return new JSConstructor(taConstructor, taPrototype);
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return typedArrayGetName(object);
   }

   public static boolean isJSArrayBufferView(Object obj) {
      return obj instanceof JSTypedArrayObject;
   }

   public static boolean isBigIntArrayBufferView(JSDynamicObject obj) {
      return typedArrayGetArrayType(obj) instanceof TypedArray.TypedBigIntArray;
   }

   public static boolean hasDetachedBuffer(JSDynamicObject obj, JSContext ctx) {
      assert isJSArrayBufferView(obj);

      return ctx.getTypedArrayNotDetachedAssumption().isValid() ? false : hasDetachedBuffer(obj);
   }

   public static boolean hasDetachedBuffer(JSDynamicObject obj) {
      assert isJSArrayBufferView(obj);

      return JSArrayBuffer.isDetachedBuffer(getArrayBuffer(obj));
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
      if (!strings) {
         return super.getOwnPropertyKeys(thisObj, strings, symbols);
      } else {
         List<Object> indices = typedArrayGetArrayType(thisObj).ownPropertyKeys(thisObj);
         List<Object> keys = ordinaryOwnPropertyKeys(thisObj, strings, symbols);
         return IteratorUtil.concatLists(indices, keys);
      }
   }

   @Override
   public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor descriptor, boolean doThrow) {
      assert JSRuntime.isPropertyKey(key);

      if (Strings.isTString(key)) {
         Object numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key);
         if (numericIndex != Undefined.instance) {
            boolean success = defineOwnPropertyIndex(thisObj, (Number)numericIndex, descriptor);
            if (doThrow && !success) {
               throw Errors.createTypeError("Cannot defineOwnProperty on TypedArray");
            }

            return success;
         }
      }

      return super.defineOwnProperty(thisObj, key, descriptor, doThrow);
   }

   @CompilerDirectives.TruffleBoundary
   private static boolean defineOwnPropertyIndex(JSDynamicObject thisObj, Number numericIndex, PropertyDescriptor desc) {
      long index = validIntegerIndex(thisObj, numericIndex);
      if (index == -1L) {
         return false;
      } else if (desc.isAccessorDescriptor()) {
         return false;
      } else if (desc.hasConfigurable() && !desc.getConfigurable()) {
         return false;
      } else if (desc.hasEnumerable() && !desc.getEnumerable()) {
         return false;
      } else if (desc.hasWritable() && !desc.getWritable()) {
         return false;
      } else {
         if (desc.hasValue()) {
            Object value = desc.getValue();
            Object numValue = convertValue(thisObj, value);
            if (!hasDetachedBuffer(thisObj)) {
               assert index >= 0L && index < (long)typedArrayGetLength(thisObj);

               typedArrayGetArrayType(thisObj).setElement(thisObj, index, numValue, true);
            }
         }

         return true;
      }
   }

   @Override
   public boolean setIntegrityLevel(JSDynamicObject thisObj, boolean freeze, boolean doThrow) {
      this.preventExtensions(thisObj, doThrow);
      if (freeze && typedArrayGetLength(thisObj) > 0) {
         throwCannotRedefine();
      }

      return true;
   }

   @Override
   public boolean testIntegrityLevel(JSDynamicObject thisObj, boolean frozen) {
      return frozen && typedArrayGetLength(thisObj) > 0 ? false : JSNonProxy.testIntegrityLevelFast(thisObj, frozen);
   }

   private static void throwCannotRedefine() {
      throw Errors.createTypeError("Cannot redefine a property of an object with external array elements");
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      if (Strings.isTString(key)) {
         long numericIndex = JSRuntime.propertyKeyToIntegerIndex(key);
         if (numericIndex >= 0L) {
            Object value = this.getOwnHelper(thisObj, thisObj, numericIndex, null);
            if (value == Undefined.instance) {
               return null;
            }

            return PropertyDescriptor.createData(value, true, true, true);
         }
      }

      return ordinaryGetOwnProperty(thisObj, key);
   }

   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      return JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()
         ? this.defaultToString(obj)
         : JSRuntime.objectToDisplayString(obj, allowSideEffects, format, depth, typedArrayGetName(obj));
   }

   @Override
   public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
      assert JSRuntime.isPropertyKey(key);

      if (Strings.isTString(key)) {
         Object numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key);
         if (numericIndex != Undefined.instance) {
            if (hasNumericIndex(thisObj, numericIndex)) {
               if (isStrict) {
                  throw Errors.createTypeErrorNotConfigurableProperty(key);
               }

               return false;
            }

            return true;
         }
      }

      return super.delete(thisObj, key, isStrict);
   }

   @Override
   public boolean usesOrdinaryGetOwnProperty() {
      return false;
   }

   private abstract static class ArrayBufferViewGetter {
      public abstract Object apply(JSDynamicObject view);
   }
}
