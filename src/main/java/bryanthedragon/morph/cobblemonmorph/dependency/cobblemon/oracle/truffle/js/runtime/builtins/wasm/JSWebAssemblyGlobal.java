
package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.wasm.WebAssemblyGlobalPrototypeBuiltins;
import com.oracle.truffle.js.nodes.wasm.ToJSValueNode;
import com.oracle.truffle.js.nodes.wasm.ToWebAssemblyValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssembly;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyGlobalObject;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;

public class JSWebAssemblyGlobal
extends JSNonProxy
implements JSConstructorFactory.Default,
PrototypeSupplier {
    public static final TruffleString CLASS_NAME = Strings.constant("Global");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("Global.prototype");
    public static final TruffleString VALUE = Strings.constant("value");
    public static final TruffleString WEB_ASSEMBLY_GLOBAL = Strings.constant("WebAssembly.Global");
    public static final JSWebAssemblyGlobal INSTANCE = new JSWebAssemblyGlobal();

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return this.getClassName();
    }

    public static boolean isJSWebAssemblyGlobal(Object object) {
        return object instanceof JSWebAssemblyGlobalObject;
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject constructor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, prototype, constructor);
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, WebAssemblyGlobalPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putAccessorProperty(ctx, prototype, VALUE, JSWebAssemblyGlobal.createValueGetterFunction(realm), JSWebAssemblyGlobal.createValueSetterFunction(realm), JSAttributes.configurableEnumerableWritable());
        JSObjectUtil.putToStringTag(prototype, WEB_ASSEMBLY_GLOBAL);
        return prototype;
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getWebAssemblyGlobalPrototype();
    }

    @Override
    public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm);
    }

    public static JSWebAssemblyGlobalObject create(JSContext context, JSRealm realm, Object wasmGlobal, TruffleString valueType, boolean mutable) {
        Object embedderData = JSWebAssembly.getEmbedderData(realm, wasmGlobal);
        if (embedderData instanceof JSWebAssemblyGlobalObject) {
            return (JSWebAssemblyGlobalObject)embedderData;
        }
        JSObjectFactory factory = context.getWebAssemblyGlobalFactory();
        JSWebAssemblyGlobalObject object = new JSWebAssemblyGlobalObject(factory.getShape(realm), wasmGlobal, valueType, mutable);
        factory.initProto(object, realm);
        JSWebAssembly.setEmbedderData(realm, wasmGlobal, object);
        return context.trackAllocation(object);
    }

    private static JSFunctionObject createValueGetterFunction(final JSRealm realm) {
        JSFunctionData getterData = realm.getContext().getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.WebAssemblyGlobalGetValue, c -> {
            RootCallTarget callTarget = new JavaScriptRootNode(c.getLanguage(), null, null){
                @Node.Child
                ToJSValueNode toJSValueNode;
                @Node.Child
                InteropLibrary globalReadLib;
                private final BranchProfile errorBranch;
                {
                    super(lang, sourceSection, frameDescriptor);
                    this.toJSValueNode = ToJSValueNode.create();
                    this.globalReadLib = InteropLibrary.getFactory().createDispatched(5);
                    this.errorBranch = BranchProfile.create();
                }

                @Override
                public Object execute(VirtualFrame frame) {
                    Object thiz = JSFrameUtil.getThisObj(frame);
                    if (JSWebAssemblyGlobal.isJSWebAssemblyGlobal(thiz)) {
                        JSWebAssemblyGlobalObject object = (JSWebAssemblyGlobalObject)thiz;
                        Object wasmGlobal = object.getWASMGlobal();
                        Object globalRead = realm.getWASMGlobalRead();
                        try {
                            return this.toJSValueNode.execute(this.globalReadLib.execute(globalRead, wasmGlobal));
                        }
                        catch (InteropException ex) {
                            throw Errors.shouldNotReachHere(ex);
                        }
                    }
                    this.errorBranch.enter();
                    throw Errors.createTypeError("get WebAssembly.Global.value: Receiver is not a WebAssembly.Global", (Node)this);
                }
            }.getCallTarget();
            return JSFunctionData.createCallOnly(c, callTarget, 0, Strings.concat(Strings.GET_SPC, VALUE));
        });
        return JSFunction.create(realm, getterData);
    }

    private static JSFunctionObject createValueSetterFunction(final JSRealm realm) {
        JSFunctionData setterData = realm.getContext().getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.WebAssemblyGlobalSetValue, c -> {
            RootCallTarget callTarget = new JavaScriptRootNode(c.getLanguage(), null, null){
                @Node.Child
                ToWebAssemblyValueNode toWebAssemblyValueNode;
                @Node.Child
                InteropLibrary globalWriteLib;
                private final BranchProfile errorBranch;
                {
                    super(lang, sourceSection, frameDescriptor);
                    this.toWebAssemblyValueNode = ToWebAssemblyValueNode.create();
                    this.globalWriteLib = InteropLibrary.getFactory().createDispatched(5);
                    this.errorBranch = BranchProfile.create();
                }

                @Override
                public Object execute(VirtualFrame frame) {
                    Object[] args = frame.getArguments();
                    Object thiz = JSArguments.getThisObject(args);
                    if (JSWebAssemblyGlobal.isJSWebAssemblyGlobal(thiz)) {
                        JSWebAssemblyGlobalObject global = (JSWebAssemblyGlobalObject)thiz;
                        if (!global.isMutable()) {
                            this.errorBranch.enter();
                            throw Errors.createTypeError("set WebAssembly.Global.value: Can't set the value of an immutable global");
                        }
                        Object wasmGlobal = global.getWASMGlobal();
                        try {
                            if (JSArguments.getUserArgumentCount(args) == 0) {
                                this.errorBranch.enter();
                                throw Errors.createTypeError("set WebAssembly.Global.value: Argument 0 is required");
                            }
                            Object value2 = JSArguments.getUserArgument(args, 0);
                            Object webAssemblyValue = this.toWebAssemblyValueNode.execute(value2, global.getValueType());
                            Object globalWrite = realm.getWASMGlobalWrite();
                            this.globalWriteLib.execute(globalWrite, wasmGlobal, webAssemblyValue);
                            return Undefined.instance;
                        }
                        catch (InteropException ex) {
                            throw Errors.shouldNotReachHere(ex);
                        }
                        catch (AbstractTruffleException ex) {
                            this.errorBranch.enter();
                            throw Errors.createTypeError(ex, (Node)this);
                        }
                    }
                    this.errorBranch.enter();
                    throw Errors.createTypeError("set WebAssembly.Global.value: Receiver is not a WebAssembly.Global", (Node)this);
                }
            }.getCallTarget();
            return JSFunctionData.createCallOnly(c, callTarget, 1, Strings.concat(Strings.SET_SPC, VALUE));
        });
        return JSFunction.create(realm, setterData);
    }
}

