
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ArrayBufferFunctionBuiltins;
import com.oracle.truffle.js.builtins.ArrayBufferPrototypeBuiltins;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSAbstractBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSSharedArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.util.DirectByteBufferHelper;
import java.nio.ByteBuffer;

public final class JSArrayBuffer
extends JSAbstractBuffer
implements JSConstructorFactory.WithFunctionsAndSpecies,
PrototypeSupplier {
    public static final TruffleString CLASS_NAME = Strings.constant("ArrayBuffer");
    public static final TruffleString PROTOTYPE_NAME = Strings.concat(CLASS_NAME, Strings.DOT_PROTOTYPE);
    public static final JSArrayBuffer HEAP_INSTANCE = new JSArrayBuffer();
    public static final JSArrayBuffer DIRECT_INSTANCE = new JSArrayBuffer();
    public static final JSArrayBuffer INTEROP_INSTANCE = new JSArrayBuffer();

    private JSArrayBuffer() {
    }

    public static JSArrayBufferObject createArrayBuffer(JSContext context, JSRealm realm, int length) {
        return JSArrayBuffer.createArrayBuffer(context, realm, new byte[length]);
    }

    public static JSArrayBufferObject createArrayBuffer(JSContext context, JSRealm realm, byte[] byteArray) {
        JSObjectFactory factory = context.getArrayBufferFactory();
        JSArrayBufferObject obj = JSArrayBufferObject.createHeapArrayBuffer(factory.getShape(realm), byteArray);
        factory.initProto(obj, realm);
        assert (JSArrayBuffer.isJSHeapArrayBuffer(obj));
        return context.trackAllocation(obj);
    }

    public static byte[] getByteArray(Object thisObj) {
        assert (JSArrayBuffer.isJSHeapArrayBuffer(thisObj));
        return JSArrayBufferObject.getByteArray(thisObj);
    }

    public static int getHeapByteLength(Object thisObj) {
        assert (JSArrayBuffer.isJSHeapArrayBuffer(thisObj));
        return JSArrayBuffer.getByteArray(thisObj).length;
    }

    public static int getDirectByteLength(Object thisObj) {
        return JSArrayBuffer.getDirectByteBuffer(thisObj).capacity();
    }

    public static ByteBuffer getDirectByteBuffer(Object thisObj) {
        assert (JSArrayBuffer.isJSDirectArrayBuffer(thisObj) || JSSharedArrayBuffer.isJSSharedArrayBuffer(thisObj));
        return JSArrayBufferObject.getDirectByteBuffer(thisObj);
    }

    public static JSArrayBufferObject createDirectArrayBuffer(JSContext context, JSRealm realm, int length) {
        return JSArrayBuffer.createDirectArrayBuffer(context, realm, DirectByteBufferHelper.allocateDirect(length));
    }

    public static JSArrayBufferObject createDirectArrayBuffer(JSContext context, JSRealm realm, ByteBuffer buffer) {
        JSObjectFactory factory = context.getDirectArrayBufferFactory();
        JSArrayBufferObject obj = JSArrayBufferObject.createDirectArrayBuffer(factory.getShape(realm), buffer);
        factory.initProto(obj, realm);
        assert (JSArrayBuffer.isJSDirectArrayBuffer(obj));
        return context.trackAllocation(obj);
    }

    public static Object getInteropBuffer(Object thisObj) {
        assert (JSArrayBuffer.isJSInteropArrayBuffer(thisObj));
        return JSArrayBufferObject.getInteropBuffer(thisObj);
    }

    public static JSArrayBufferObject createInteropArrayBuffer(JSContext context, JSRealm realm, Object buffer) {
        assert (InteropLibrary.getUncached().hasBufferElements(buffer));
        JSObjectFactory factory = context.getInteropArrayBufferFactory();
        JSArrayBufferObject obj = JSArrayBufferObject.createInteropArrayBuffer(factory.getShape(realm), buffer);
        factory.initProto(obj, realm);
        assert (JSArrayBuffer.isJSInteropArrayBuffer(obj));
        return context.trackAllocation(obj);
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSObject arrayBufferPrototype;
        JSContext context = realm.getContext();
        if (context.getEcmaScriptVersion() < 6) {
            Shape protoShape = JSShape.createPrototypeShape(context, HEAP_INSTANCE, realm.getObjectPrototype());
            arrayBufferPrototype = JSArrayBufferObject.createHeapArrayBuffer(protoShape, new byte[0]);
            JSObjectUtil.setOrVerifyPrototype(context, arrayBufferPrototype, realm.getObjectPrototype());
        } else {
            arrayBufferPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        }
        JSObjectUtil.putConstructorProperty(context, arrayBufferPrototype, ctor);
        JSObjectUtil.putFunctionsFromContainer(realm, arrayBufferPrototype, ArrayBufferPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putBuiltinAccessorProperty(arrayBufferPrototype, BYTE_LENGTH, realm.lookupAccessor(ArrayBufferPrototypeBuiltins.BUILTINS, BYTE_LENGTH));
        JSObjectUtil.putToStringTag(arrayBufferPrototype, CLASS_NAME);
        return arrayBufferPrototype;
    }

    @Override
    public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
        if (this == INTEROP_INSTANCE) {
            return JSObjectUtil.getProtoChildShape(prototype, INTEROP_INSTANCE, context);
        }
        if (this == HEAP_INSTANCE) {
            return JSObjectUtil.getProtoChildShape(prototype, HEAP_INSTANCE, context);
        }
        assert (this == DIRECT_INSTANCE);
        return JSObjectUtil.getProtoChildShape(prototype, DIRECT_INSTANCE, context);
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return HEAP_INSTANCE.createConstructorAndPrototype(realm, ArrayBufferFunctionBuiltins.BUILTINS);
    }

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return this.getClassName();
    }

    public static boolean isJSHeapArrayBuffer(Object obj) {
        return obj instanceof JSArrayBufferObject.Heap;
    }

    public static boolean isJSDirectArrayBuffer(Object obj) {
        return obj instanceof JSArrayBufferObject.Direct;
    }

    public static boolean isJSInteropArrayBuffer(Object obj) {
        return obj instanceof JSArrayBufferObject.Interop;
    }

    public static boolean isJSDirectOrSharedArrayBuffer(Object obj) {
        return JSArrayBuffer.isJSDirectArrayBuffer(obj) || JSSharedArrayBuffer.isJSSharedArrayBuffer(obj);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isDetachedBuffer(Object arrayBuffer) {
        assert (JSArrayBuffer.isJSAbstractBuffer(arrayBuffer));
        if (JSArrayBuffer.isJSHeapArrayBuffer(arrayBuffer)) {
            return JSArrayBuffer.getByteArray(arrayBuffer) == null;
        }
        if (JSArrayBuffer.isJSDirectOrSharedArrayBuffer(arrayBuffer)) {
            return JSArrayBuffer.getDirectByteBuffer(arrayBuffer) == null;
        }
        assert (JSArrayBuffer.isJSInteropArrayBuffer(arrayBuffer));
        return JSArrayBuffer.getInteropBuffer(arrayBuffer) == null;
    }

    @CompilerDirectives.TruffleBoundary
    public static void detachArrayBuffer(JSDynamicObject arrayBuffer) {
        assert (JSArrayBuffer.isJSAbstractBuffer(arrayBuffer));
        JSObject.getJSContext(arrayBuffer).getTypedArrayNotDetachedAssumption().invalidate("no detached array buffer");
        if (JSArrayBuffer.isJSDirectArrayBuffer(arrayBuffer)) {
            ((JSArrayBufferObject.Direct)arrayBuffer).detachArrayBuffer();
        } else if (JSArrayBuffer.isJSInteropArrayBuffer(arrayBuffer)) {
            ((JSArrayBufferObject.Interop)arrayBuffer).detachArrayBuffer();
        } else {
            assert (JSArrayBuffer.isJSHeapArrayBuffer(arrayBuffer));
            ((JSArrayBufferObject.Heap)arrayBuffer).detachArrayBuffer();
        }
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getArrayBufferPrototype();
    }
}

