
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.DataViewPrototypeBuiltins;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSDataViewObject;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.function.Function;

public final class JSDataView
extends JSNonProxy
implements JSConstructorFactory.Default,
PrototypeSupplier {
    public static final TruffleString CLASS_NAME = Strings.constant("DataView");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("DataView.prototype");
    public static final JSDataView INSTANCE = new JSDataView();
    private static final TruffleString BYTE_LENGTH = Strings.constant("byteLength");
    private static final TruffleString BUFFER = Strings.constant("buffer");
    private static final TruffleString BYTE_OFFSET = Strings.constant("byteOffset");

    public static int typedArrayGetLength(Object thisObj) {
        assert (JSDataView.isJSDataView(thisObj));
        return JSDataViewObject.getLength(thisObj);
    }

    public static int typedArrayGetOffset(Object thisObj) {
        assert (JSDataView.isJSDataView(thisObj));
        return JSDataViewObject.getOffset(thisObj);
    }

    private JSDataView() {
    }

    public static JSArrayBufferObject getArrayBuffer(Object thisObj) {
        assert (JSDataView.isJSDataView(thisObj));
        return JSDataViewObject.getArrayBuffer(thisObj);
    }

    public static JSDynamicObject createDataView(JSContext context, JSRealm realm, JSDynamicObject arrayBuffer, int offset, int length) {
        assert (offset >= 0 && offset + length <= ((JSArrayBufferObject)arrayBuffer).getByteLength());
        JSObjectFactory factory = context.getDataViewFactory();
        JSDataViewObject dataView = JSDataViewObject.create(realm, factory, (JSArrayBufferObject)arrayBuffer, length, offset);
        return context.trackAllocation(dataView);
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSContext context = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(context, prototype, ctor);
        JSDataView.putGetters(realm, prototype);
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, DataViewPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putToStringTag(prototype, CLASS_NAME);
        return prototype;
    }

    private static void putGetters(JSRealm realm, JSObject prototype) {
        JSDataView.putGetter(realm, prototype, BUFFER, JSContext.BuiltinFunctionKey.DataViewBuffer, view -> JSDataView.getArrayBuffer(view));
        JSDataView.putGetter(realm, prototype, BYTE_LENGTH, JSContext.BuiltinFunctionKey.DataViewByteLength, view -> JSDataView.typedArrayGetLengthChecked(view));
        JSDataView.putGetter(realm, prototype, BYTE_OFFSET, JSContext.BuiltinFunctionKey.DataViewByteOffset, view -> JSDataView.typedArrayGetOffsetChecked(view));
    }

    public static int typedArrayGetLengthChecked(JSDynamicObject thisObj) {
        if (JSArrayBuffer.isDetachedBuffer(JSDataView.getArrayBuffer(thisObj))) {
            throw Errors.createTypeErrorDetachedBuffer();
        }
        return JSDataView.typedArrayGetLength(thisObj);
    }

    public static int typedArrayGetOffsetChecked(JSDynamicObject thisObj) {
        if (JSArrayBuffer.isDetachedBuffer(JSDataView.getArrayBuffer(thisObj))) {
            throw Errors.createTypeErrorDetachedBuffer();
        }
        return JSDataView.typedArrayGetOffset(thisObj);
    }

    private static void putGetter(JSRealm realm, JSObject prototype, TruffleString name, JSContext.BuiltinFunctionKey key, final Function<JSDynamicObject, Object> function) {
        JSFunctionData getterData = realm.getContext().getOrCreateBuiltinFunctionData(key, c -> JSFunctionData.createCallOnly(c, new JavaScriptRootNode(c.getLanguage(), null, null){

            @Override
            public Object execute(VirtualFrame frame) {
                Object obj = JSArguments.getThisObject(frame.getArguments());
                if (JSDataView.isJSDataView(obj)) {
                    return function.apply((JSDataViewObject)obj);
                }
                throw Errors.createTypeErrorNotADataView();
            }
        }.getCallTarget(), 0, Strings.concat(Strings.GET_SPC, name)));
        JSFunctionObject getter = JSFunction.create(realm, getterData);
        JSObjectUtil.putBuiltinAccessorProperty((JSDynamicObject)prototype, (Object)name, getter, Undefined.instance);
    }

    @Override
    public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
        Shape childTree = JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
        return childTree;
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm);
    }

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return this.getClassName();
    }

    public static boolean isJSDataView(Object obj) {
        return obj instanceof JSDataViewObject;
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getDataViewPrototype();
    }
}

