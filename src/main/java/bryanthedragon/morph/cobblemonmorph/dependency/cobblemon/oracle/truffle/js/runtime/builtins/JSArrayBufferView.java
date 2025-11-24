
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
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.builtins.TypedArrayAccess;
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

public final class JSArrayBufferView
extends JSNonProxy {
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
        assert (JSArrayBufferView.isJSArrayBufferView(thisObj));
        return JSArrayBufferView.typedArray().getArrayType(thisObj);
    }

    public static int typedArrayGetLength(JSDynamicObject thisObj) {
        return JSArrayBufferView.typedArray().getLength(thisObj);
    }

    public static int typedArrayGetOffset(JSDynamicObject thisObj) {
        return JSArrayBufferView.typedArray().getOffset(thisObj);
    }

    public static byte[] typedArrayGetByteArray(JSDynamicObject thisObj) {
        return JSArrayBufferView.typedArray().getByteArray(thisObj);
    }

    public static ByteBuffer typedArrayGetByteBuffer(JSDynamicObject thisObj) {
        return DirectByteBufferHelper.cast(JSArrayBufferView.typedArray().getByteBuffer(thisObj));
    }

    private static TruffleString typedArrayGetName(JSDynamicObject thisObj) {
        return JSArrayBufferView.typedArrayGetArrayType(thisObj).getName();
    }

    private JSArrayBufferView() {
    }

    public static JSArrayBufferObject getArrayBuffer(JSDynamicObject thisObj) {
        assert (JSArrayBufferView.isJSArrayBufferView(thisObj));
        return JSArrayBufferView.typedArray().getArrayBuffer(thisObj);
    }

    public static int getByteLength(JSDynamicObject store, JSContext ctx) {
        assert (JSArrayBufferView.isJSArrayBufferView(store));
        if (JSArrayBufferView.hasDetachedBuffer(store, ctx)) {
            return 0;
        }
        TypedArray typedArray = JSArrayBufferView.typedArrayGetArrayType(store);
        return typedArray.lengthInt(store) * typedArray.bytesPerElement();
    }

    public static int getByteLength(JSDynamicObject store, JSContext ctx, ValueProfile profile) {
        assert (JSArrayBufferView.isJSArrayBufferView(store));
        if (JSArrayBufferView.hasDetachedBuffer(store, ctx)) {
            return 0;
        }
        TypedArray typedArray = profile.profile(JSArrayBufferView.typedArrayGetArrayType(store));
        return typedArray.lengthInt(store) * typedArray.bytesPerElement();
    }

    public static int getByteOffset(JSDynamicObject store, JSContext ctx) {
        assert (JSArrayBufferView.isJSArrayBufferView(store));
        if (JSArrayBufferView.hasDetachedBuffer(store, ctx)) {
            return 0;
        }
        return JSArrayBufferView.typedArrayGetOffset(store);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getHelper(JSDynamicObject store, Object receiver, long index, Node encapsulatingNode) {
        return this.getOwnHelper(store, receiver, index, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object receiver, long index, Node encapsulatingNode) {
        if (JSArrayBufferView.hasDetachedBuffer(store)) {
            return Undefined.instance;
        }
        return JSArrayBufferView.typedArrayGetArrayType(store).getElement(store, index);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getHelper(JSDynamicObject store, Object receiver, Object key, Node encapsulatingNode) {
        Object numericIndex;
        assert (JSRuntime.isPropertyKey(key));
        if (Strings.isTString(key) && (numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key)) != Undefined.instance) {
            return JSArrayBufferView.integerIndexedElementGet(store, numericIndex);
        }
        return super.getHelper(store, receiver, key, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object receiver, Object key, Node encapsulatingNode) {
        Object numericIndex;
        assert (JSRuntime.isPropertyKey(key));
        if (Strings.isTString(key) && (numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key)) != Undefined.instance) {
            return JSArrayBufferView.integerIndexedElementGet(store, numericIndex);
        }
        return super.getOwnHelper(store, receiver, key, encapsulatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    private static Object integerIndexedElementGet(JSDynamicObject thisObj, Object numericIndex) {
        assert (JSRuntime.isNumber(numericIndex));
        if (JSArrayBufferView.hasDetachedBuffer(thisObj)) {
            return Undefined.instance;
        }
        if (!JSRuntime.isInteger(numericIndex)) {
            return Undefined.instance;
        }
        if (numericIndex instanceof Double && JSRuntime.isNegativeZero((Double)numericIndex)) {
            return Undefined.instance;
        }
        long index = ((Number)numericIndex).longValue();
        int length = JSArrayBufferView.typedArrayGetLength(thisObj);
        if (index < 0L || index >= (long)length) {
            return Undefined.instance;
        }
        return JSArrayBufferView.typedArrayGetArrayType(thisObj).getElement(thisObj, index);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, long index, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        if (thisObj == receiver) {
            Object numValue = JSArrayBufferView.convertValue(thisObj, value2);
            if (!JSArrayBufferView.hasDetachedBuffer(thisObj)) {
                JSArrayBufferView.typedArrayGetArrayType(thisObj).setElement(thisObj, index, numValue, isStrict);
            }
            return true;
        }
        if (!JSArrayBufferView.isValidIntegerIndex(thisObj, index)) {
            return true;
        }
        return super.set(thisObj, index, value2, receiver, isStrict, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, Object key, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        Object numericIndex;
        assert (JSRuntime.isPropertyKey(key));
        if (Strings.isTString(key) && (numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key)) != Undefined.instance) {
            if (thisObj == receiver) {
                Object numValue = JSArrayBufferView.convertValue(thisObj, value2);
                long index = JSArrayBufferView.validIntegerIndex(thisObj, (Number)numericIndex);
                if (index != -1L) {
                    JSArrayBufferView.typedArrayGetArrayType(thisObj).setElement(thisObj, index, numValue, isStrict);
                }
                return true;
            }
            if (!JSArrayBufferView.isValidIntegerIndex(thisObj, (Number)numericIndex)) {
                return true;
            }
        }
        return super.set(thisObj, key, value2, receiver, isStrict, encapsulatingNode);
    }

    public static boolean isValidIntegerIndex(JSDynamicObject thisObj, Number numericIndex) {
        return JSArrayBufferView.validIntegerIndex(thisObj, numericIndex) != -1L;
    }

    @CompilerDirectives.TruffleBoundary
    private static long validIntegerIndex(JSDynamicObject thisObj, Number numericIndex) {
        if (JSArrayBufferView.hasDetachedBuffer(thisObj)) {
            return -1L;
        }
        if (!JSRuntime.isInteger(numericIndex)) {
            return -1L;
        }
        if (numericIndex instanceof Double && JSRuntime.isNegativeZero((Double)numericIndex)) {
            return -1L;
        }
        int length = JSArrayBufferView.typedArrayGetLength(thisObj);
        long index = numericIndex.longValue();
        return 0L <= index && index < (long)length ? index : -1L;
    }

    private static Object convertValue(JSDynamicObject thisObj, Object value2) {
        return JSArrayBufferView.isBigIntArrayBufferView(thisObj) ? JSRuntime.toBigInt(value2) : JSRuntime.toNumber(value2);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasProperty(JSDynamicObject thisObj, long index) {
        return this.hasOwnProperty(thisObj, index);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasProperty(JSDynamicObject thisObj, Object key) {
        Object numericIndex;
        assert (JSRuntime.isPropertyKey(key));
        if (Strings.isTString(key) && (numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key)) != Undefined.instance) {
            return JSArrayBufferView.hasNumericIndex(thisObj, numericIndex);
        }
        return super.hasProperty(thisObj, key);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasOwnProperty(JSDynamicObject thisObj, long index) {
        if (JSArrayBufferView.hasDetachedBuffer(thisObj)) {
            return false;
        }
        return JSArrayBufferView.typedArrayGetArrayType(thisObj).hasElement(thisObj, index);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
        Object numericIndex;
        assert (JSRuntime.isPropertyKey(key));
        if (Strings.isTString(key) && (numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key)) != Undefined.instance) {
            return JSArrayBufferView.hasNumericIndex(thisObj, numericIndex);
        }
        return super.hasOwnProperty(thisObj, key);
    }

    private static boolean hasNumericIndex(JSDynamicObject thisObj, Object numericIndex) {
        if (JSArrayBufferView.hasDetachedBuffer(thisObj)) {
            return false;
        }
        if (!JSRuntime.isInteger(numericIndex)) {
            return false;
        }
        double d = JSRuntime.doubleValue((Number)numericIndex);
        if (JSRuntime.isNegativeZero(d) || d < 0.0) {
            return false;
        }
        return d < (double)JSArrayBufferView.typedArrayGetLength(thisObj);
    }

    public static JSTypedArrayObject createArrayBufferView(JSContext context, JSRealm realm, JSDynamicObject arrayBuffer, TypedArray arrayType, int offset, int length) {
        CompilerAsserts.partialEvaluationConstant(arrayType);
        assert (JSArrayBuffer.isJSAbstractBuffer(arrayBuffer));
        if (!context.getTypedArrayNotDetachedAssumption().isValid() && JSArrayBuffer.isDetachedBuffer(arrayBuffer)) {
            throw Errors.createTypeErrorDetachedBuffer();
        }
        JSObjectFactory objectFactory = context.getArrayBufferViewFactory(arrayType.getFactory());
        return JSArrayBufferView.createArrayBufferView(context, realm, objectFactory, arrayBuffer, arrayType, offset, length);
    }

    public static JSTypedArrayObject createArrayBufferView(JSContext context, JSRealm realm, JSObjectFactory objectFactory, JSDynamicObject arrayBuffer, TypedArray arrayType, int offset, int length) {
        return JSArrayBufferView.createArrayBufferView(context, realm, objectFactory, arrayBuffer, arrayType, offset, length, objectFactory.getPrototype(realm));
    }

    public static JSTypedArrayObject createArrayBufferViewWithProto(JSContext context, JSRealm realm, JSObjectFactory objectFactory, JSDynamicObject arrayBuffer, TypedArray arrayType, int offset, int length, JSDynamicObject prototype) {
        return JSArrayBufferView.createArrayBufferView(context, realm, objectFactory, arrayBuffer, arrayType, offset, length, prototype);
    }

    private static JSTypedArrayObject createArrayBufferView(JSContext context, JSRealm realm, JSObjectFactory objectFactory, JSDynamicObject arrayBuffer, TypedArray arrayType, int offset, int length, JSDynamicObject prototype) {
        assert (!JSArrayBuffer.isDetachedBuffer(arrayBuffer));
        assert (offset >= 0 && offset + length * arrayType.bytesPerElement() <= ((JSArrayBufferObject)arrayBuffer).getByteLength());
        assert (offset != 0 == arrayType.hasOffset());
        JSTypedArrayObject obj = JSTypedArrayObject.create(objectFactory.getShape(realm), arrayType, (JSArrayBufferObject)arrayBuffer, length, offset);
        objectFactory.initProto(obj, prototype);
        return context.trackAllocation(obj);
    }

    private static JSObject createArrayBufferViewPrototype(JSRealm realm, JSDynamicObject ctor, int bytesPerElement, TypedArrayFactory factory, JSDynamicObject taPrototype) {
        JSContext context = realm.getContext();
        JSObject prototype = context.getEcmaScriptVersion() >= 6 ? JSObjectUtil.createOrdinaryPrototypeObject(realm, taPrototype) : JSArrayBufferView.createLegacyArrayBufferViewPrototype(realm, factory, taPrototype);
        JSObjectUtil.putDataProperty(context, prototype, BYTES_PER_ELEMENT, bytesPerElement, JSAttributes.notConfigurableNotEnumerableNotWritable());
        JSObjectUtil.putConstructorProperty(context, prototype, ctor);
        return prototype;
    }

    private static JSObject createLegacyArrayBufferViewPrototype(JSRealm realm, TypedArrayFactory factory, JSDynamicObject taPrototype) {
        JSContext context = realm.getContext();
        byte[] byteArray = new byte[]{};
        JSObjectFactory bufferFactory = context.getArrayBufferFactory();
        JSArrayBufferObject emptyArrayBuffer = bufferFactory.initProto(JSArrayBufferObject.createHeapArrayBuffer(bufferFactory.getShape(realm), byteArray), realm);
        TypedArray arrayType = factory.createArrayType(context.isOptionDirectByteBuffer(), false);
        Shape shape = JSShape.createPrototypeShape(context, INSTANCE, taPrototype);
        JSTypedArrayObject prototype = JSTypedArrayObject.create(shape, arrayType, emptyArrayBuffer, 0, 0);
        JSObjectUtil.setOrVerifyPrototype(context, prototype, taPrototype);
        return prototype;
    }

    protected static void putArrayBufferViewPrototypeGetter(JSRealm realm, JSDynamicObject prototype, TruffleString key, JSContext.BuiltinFunctionKey functionKey, final ArrayBufferViewGetter getter) {
        assert (JSRuntime.isPropertyKey(key));
        JSFunctionData lengthGetterData = realm.getContext().getOrCreateBuiltinFunctionData(functionKey, c -> JSFunctionData.createCallOnly(c, new JavaScriptRootNode(c.getLanguage(), null, null){
            private final BranchProfile errorBranch;
            {
                super(lang, sourceSection, frameDescriptor);
                this.errorBranch = BranchProfile.create();
            }

            @Override
            public Object execute(VirtualFrame frame) {
                Object obj = JSArguments.getThisObject(frame.getArguments());
                if (JSArrayBufferView.isJSArrayBufferView(obj)) {
                    return getter.apply((JSTypedArrayObject)obj);
                }
                this.errorBranch.enter();
                throw Errors.createTypeError("method called on incompatible receiver");
            }
        }.getCallTarget(), 0, Strings.concat(Strings.GET_SPC, key)));
        JSFunctionObject lengthGetter = JSFunction.create(realm, lengthGetterData);
        JSObjectUtil.putBuiltinAccessorProperty(prototype, (Object)key, lengthGetter, Undefined.instance);
    }

    public static Shape makeInitialArrayBufferViewShape(JSContext ctx, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
    }

    public static JSConstructor createConstructor(JSRealm realm, TypedArrayFactory factory, JSConstructor taConstructor) {
        JSContext ctx = realm.getContext();
        JSFunctionObject arrayBufferViewConstructor = realm.lookupFunction(ConstructorBuiltins.BUILTINS, factory.getName());
        JSObject.setPrototype(arrayBufferViewConstructor, taConstructor.getFunctionObject());
        JSObject arrayBufferViewPrototype = JSArrayBufferView.createArrayBufferViewPrototype(realm, arrayBufferViewConstructor, factory.getBytesPerElement(), factory, taConstructor.getPrototype());
        JSObjectUtil.putConstructorPrototypeProperty(ctx, arrayBufferViewConstructor, arrayBufferViewPrototype);
        JSObjectUtil.putDataProperty(ctx, arrayBufferViewConstructor, BYTES_PER_ELEMENT, factory.getBytesPerElement(), JSAttributes.notConfigurableNotEnumerableNotWritable());
        JSArrayBufferView.putConstructorSpeciesGetter(realm, arrayBufferViewConstructor);
        return new JSConstructor(arrayBufferViewConstructor, arrayBufferViewPrototype);
    }

    private static JSObject createTypedArrayPrototype(JSRealm realm, JSDynamicObject ctor) {
        final JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, prototype, ctor);
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, TypedArrayPrototypeBuiltins.BUILTINS);
        JSArrayBufferView.putArrayBufferViewPrototypeGetter(realm, prototype, LENGTH, JSContext.BuiltinFunctionKey.ArrayBufferViewLength, new ArrayBufferViewGetter(){
            private final ConditionProfile detachedBufferProfile = ConditionProfile.create();

            @Override
            public Object apply(JSDynamicObject view) {
                if (this.detachedBufferProfile.profile(JSArrayBufferView.hasDetachedBuffer(view, ctx))) {
                    return 0;
                }
                return JSArrayBufferView.typedArrayGetLength(view);
            }
        });
        JSArrayBufferView.putArrayBufferViewPrototypeGetter(realm, prototype, BUFFER, JSContext.BuiltinFunctionKey.ArrayBufferViewBuffer, new ArrayBufferViewGetter(){

            @Override
            public Object apply(JSDynamicObject view) {
                return JSArrayBufferView.getArrayBuffer(view);
            }
        });
        JSArrayBufferView.putArrayBufferViewPrototypeGetter(realm, prototype, BYTE_LENGTH, JSContext.BuiltinFunctionKey.ArrayBufferViewByteLength, new ArrayBufferViewGetter(){

            @Override
            public Object apply(JSDynamicObject view) {
                return JSArrayBufferView.getByteLength(view, ctx);
            }
        });
        JSArrayBufferView.putArrayBufferViewPrototypeGetter(realm, prototype, BYTE_OFFSET, JSContext.BuiltinFunctionKey.ArrayBufferViewByteByteOffset, new ArrayBufferViewGetter(){

            @Override
            public Object apply(JSDynamicObject view) {
                return JSArrayBufferView.getByteOffset(view, ctx);
            }
        });
        JSFunctionData toStringData = realm.getContext().getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.ArrayBufferViewToString, c -> JSFunctionData.createCallOnly(ctx, new JavaScriptRootNode(ctx.getLanguage(), null, null){

            @Override
            public Object execute(VirtualFrame frame) {
                Object obj = JSArguments.getThisObject(frame.getArguments());
                if (JSArrayBufferView.isJSArrayBufferView(obj)) {
                    return JSArrayBufferView.typedArrayGetName((JSTypedArrayObject)obj);
                }
                return Undefined.instance;
            }
        }.getCallTarget(), 0, GET_SYMBOL_TO_STRING_TAG_NAME));
        JSFunctionObject toStringTagGetter = JSFunction.create(realm, toStringData);
        JSObjectUtil.putBuiltinAccessorProperty((JSDynamicObject)prototype, (Object)Symbol.SYMBOL_TO_STRING_TAG, toStringTagGetter, Undefined.instance);
        Object valuesFunction = JSDynamicObject.getOrNull(prototype, Strings.VALUES);
        JSObjectUtil.putDataProperty(ctx, prototype, Symbol.SYMBOL_ITERATOR, valuesFunction, JSAttributes.getDefaultNotEnumerable());
        Object toStringFunction = JSDynamicObject.getOrNull(realm.getArrayPrototype(), Strings.TO_STRING);
        JSObjectUtil.putDataProperty(ctx, prototype, Strings.TO_STRING, toStringFunction, JSAttributes.getDefaultNotEnumerable());
        return prototype;
    }

    public static JSConstructor createTypedArrayConstructor(JSRealm realm) {
        JSContext ctx = realm.getContext();
        JSFunctionObject taConstructor = realm.lookupFunction(ConstructorBuiltins.BUILTINS, CLASS_NAME);
        JSObject taPrototype = JSArrayBufferView.createTypedArrayPrototype(realm, taConstructor);
        JSObjectUtil.putConstructorPrototypeProperty(ctx, taConstructor, taPrototype);
        JSObjectUtil.putFunctionsFromContainer(realm, taConstructor, TypedArrayFunctionBuiltins.BUILTINS);
        JSArrayBufferView.putConstructorSpeciesGetter(realm, taConstructor);
        return new JSConstructor(taConstructor, taPrototype);
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return JSArrayBufferView.typedArrayGetName(object);
    }

    public static boolean isJSArrayBufferView(Object obj) {
        return obj instanceof JSTypedArrayObject;
    }

    public static boolean isBigIntArrayBufferView(JSDynamicObject obj) {
        return JSArrayBufferView.typedArrayGetArrayType(obj) instanceof TypedArray.TypedBigIntArray;
    }

    public static boolean hasDetachedBuffer(JSDynamicObject obj, JSContext ctx) {
        assert (JSArrayBufferView.isJSArrayBufferView(obj));
        if (ctx.getTypedArrayNotDetachedAssumption().isValid()) {
            return false;
        }
        return JSArrayBufferView.hasDetachedBuffer(obj);
    }

    public static boolean hasDetachedBuffer(JSDynamicObject obj) {
        assert (JSArrayBufferView.isJSArrayBufferView(obj));
        return JSArrayBuffer.isDetachedBuffer(JSArrayBufferView.getArrayBuffer(obj));
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
        if (!strings) {
            return super.getOwnPropertyKeys(thisObj, strings, symbols);
        }
        List<Object> indices = JSArrayBufferView.typedArrayGetArrayType(thisObj).ownPropertyKeys(thisObj);
        List<Object> keys = JSArrayBufferView.ordinaryOwnPropertyKeys(thisObj, strings, symbols);
        return IteratorUtil.concatLists(indices, keys);
    }

    @Override
    public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor descriptor, boolean doThrow) {
        Object numericIndex;
        assert (JSRuntime.isPropertyKey(key));
        if (Strings.isTString(key) && (numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key)) != Undefined.instance) {
            boolean success = JSArrayBufferView.defineOwnPropertyIndex(thisObj, (Number)numericIndex, descriptor);
            if (doThrow && !success) {
                throw Errors.createTypeError("Cannot defineOwnProperty on TypedArray");
            }
            return success;
        }
        return super.defineOwnProperty(thisObj, key, descriptor, doThrow);
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean defineOwnPropertyIndex(JSDynamicObject thisObj, Number numericIndex, PropertyDescriptor desc) {
        long index = JSArrayBufferView.validIntegerIndex(thisObj, numericIndex);
        if (index == -1L) {
            return false;
        }
        if (desc.isAccessorDescriptor()) {
            return false;
        }
        if (desc.hasConfigurable() && !desc.getConfigurable()) {
            return false;
        }
        if (desc.hasEnumerable() && !desc.getEnumerable()) {
            return false;
        }
        if (desc.hasWritable() && !desc.getWritable()) {
            return false;
        }
        if (desc.hasValue()) {
            Object value2 = desc.getValue();
            Object numValue = JSArrayBufferView.convertValue(thisObj, value2);
            if (!JSArrayBufferView.hasDetachedBuffer(thisObj)) {
                assert (index >= 0L && index < (long)JSArrayBufferView.typedArrayGetLength(thisObj));
                JSArrayBufferView.typedArrayGetArrayType(thisObj).setElement(thisObj, index, numValue, true);
            }
        }
        return true;
    }

    @Override
    public boolean setIntegrityLevel(JSDynamicObject thisObj, boolean freeze, boolean doThrow) {
        this.preventExtensions(thisObj, doThrow);
        if (freeze && JSArrayBufferView.typedArrayGetLength(thisObj) > 0) {
            JSArrayBufferView.throwCannotRedefine();
        }
        return true;
    }

    @Override
    public boolean testIntegrityLevel(JSDynamicObject thisObj, boolean frozen) {
        if (frozen && JSArrayBufferView.typedArrayGetLength(thisObj) > 0) {
            return false;
        }
        return JSNonProxy.testIntegrityLevelFast(thisObj, frozen);
    }

    private static void throwCannotRedefine() {
        throw Errors.createTypeError("Cannot redefine a property of an object with external array elements");
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
        long numericIndex;
        assert (JSRuntime.isPropertyKey(key));
        if (Strings.isTString(key) && (numericIndex = JSRuntime.propertyKeyToIntegerIndex(key)) >= 0L) {
            Object value2 = this.getOwnHelper(thisObj, (Object)thisObj, numericIndex, (Node)null);
            if (value2 == Undefined.instance) {
                return null;
            }
            return PropertyDescriptor.createData(value2, true, true, true);
        }
        return JSArrayBufferView.ordinaryGetOwnProperty(thisObj, key);
    }

    @Override
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
            return this.defaultToString(obj);
        }
        return JSRuntime.objectToDisplayString(obj, allowSideEffects, format, depth, JSArrayBufferView.typedArrayGetName(obj));
    }

    @Override
    public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
        Object numericIndex;
        assert (JSRuntime.isPropertyKey(key));
        if (Strings.isTString(key) && (numericIndex = JSRuntime.canonicalNumericIndexString((TruffleString)key)) != Undefined.instance) {
            if (JSArrayBufferView.hasNumericIndex(thisObj, numericIndex)) {
                if (isStrict) {
                    throw Errors.createTypeErrorNotConfigurableProperty(key);
                }
                return false;
            }
            return true;
        }
        return super.delete(thisObj, key, isStrict);
    }

    @Override
    public boolean usesOrdinaryGetOwnProperty() {
        return false;
    }

    private static abstract class ArrayBufferViewGetter {
        private ArrayBufferViewGetter() {
        }

        public abstract Object apply(JSDynamicObject var1);
    }
}

