
package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.wasm.WebAssemblyMemoryPrototypeBuiltins;
import com.oracle.truffle.js.runtime.Errors;
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
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyMemoryObject;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public class JSWebAssemblyMemory
extends JSNonProxy
implements JSConstructorFactory.Default,
PrototypeSupplier {
    public static final int MAX_MEMORY_SIZE = Short.MAX_VALUE;
    public static final TruffleString CLASS_NAME = Strings.constant("Memory");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("Memory.prototype");
    public static final TruffleString BUFFER = Strings.constant("buffer");
    public static final TruffleString WEB_ASSEMBLY_MEMORY = Strings.constant("WebAssembly.Memory");
    public static final JSWebAssemblyMemory INSTANCE = new JSWebAssemblyMemory();

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return this.getClassName();
    }

    public static boolean isJSWebAssemblyMemory(Object object) {
        return object instanceof JSWebAssemblyMemoryObject;
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject constructor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, prototype, constructor);
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, WebAssemblyMemoryPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putAccessorProperty(ctx, prototype, BUFFER, JSWebAssemblyMemory.createBufferGetterFunction(realm), null, JSAttributes.configurableEnumerableWritable());
        JSObjectUtil.putToStringTag(prototype, WEB_ASSEMBLY_MEMORY);
        return prototype;
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getWebAssemblyMemoryPrototype();
    }

    @Override
    public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm);
    }

    public static JSWebAssemblyMemoryObject create(JSContext context, JSRealm realm, Object wasmMemory) {
        Object embedderData = JSWebAssembly.getEmbedderData(realm, wasmMemory);
        if (embedderData instanceof JSWebAssemblyMemoryObject) {
            return (JSWebAssemblyMemoryObject)embedderData;
        }
        realm.getWebAssemblyMemoryGrowCallback().attachToMemory(wasmMemory);
        JSObjectFactory factory = context.getWebAssemblyMemoryFactory();
        JSWebAssemblyMemoryObject object = new JSWebAssemblyMemoryObject(factory.getShape(realm), wasmMemory);
        factory.initProto(object, realm);
        JSWebAssembly.setEmbedderData(realm, wasmMemory, object);
        return context.trackAllocation(object);
    }

    private static JSFunctionObject createBufferGetterFunction(final JSRealm realm) {
        final JSContext context = realm.getContext();
        JSFunctionData getterData = context.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.WebAssemblyMemoryGetBuffer, c -> {
            RootCallTarget callTarget = new JavaScriptRootNode(c.getLanguage(), null, null){
                private final BranchProfile errorBranch;
                {
                    super(lang, sourceSection, frameDescriptor);
                    this.errorBranch = BranchProfile.create();
                }

                @Override
                public Object execute(VirtualFrame frame) {
                    Object thiz = JSFrameUtil.getThisObj(frame);
                    if (JSWebAssemblyMemory.isJSWebAssemblyMemory(thiz)) {
                        return ((JSWebAssemblyMemoryObject)thiz).getBufferObject(context, realm);
                    }
                    this.errorBranch.enter();
                    throw Errors.createTypeError("WebAssembly.Memory.buffer: Receiver is not a WebAssembly.Memory", (Node)this);
                }
            }.getCallTarget();
            return JSFunctionData.createCallOnly(c, callTarget, 0, Strings.concat(Strings.GET_SPC, BUFFER));
        });
        return JSFunction.create(realm, getterData);
    }
}

