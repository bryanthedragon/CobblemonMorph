
package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.wasm.WebAssemblyTablePrototypeBuiltins;
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
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyTableObject;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public class JSWebAssemblyTable
extends JSNonProxy
implements JSConstructorFactory.Default,
PrototypeSupplier {
    public static final int MAX_TABLE_SIZE = 10000000;
    public static final TruffleString CLASS_NAME = Strings.constant("Table");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("Table.prototype");
    public static final TruffleString LENGTH = Strings.constant("length");
    public static final TruffleString WEB_ASSEMBLY_TABLE = Strings.constant("WebAssembly.Table");
    public static final JSWebAssemblyTable INSTANCE = new JSWebAssemblyTable();

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return this.getClassName();
    }

    public static boolean isJSWebAssemblyTable(Object object) {
        return object instanceof JSWebAssemblyTableObject;
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject constructor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, prototype, constructor);
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, WebAssemblyTablePrototypeBuiltins.BUILTINS);
        JSObjectUtil.putAccessorProperty(ctx, prototype, LENGTH, JSWebAssemblyTable.createLengthGetterFunction(realm), null, JSAttributes.configurableEnumerableWritable());
        JSObjectUtil.putToStringTag(prototype, WEB_ASSEMBLY_TABLE);
        return prototype;
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getWebAssemblyTablePrototype();
    }

    @Override
    public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm);
    }

    public static JSWebAssemblyTableObject create(JSContext context, JSRealm realm, Object wasmTable) {
        Object embedderData = JSWebAssembly.getEmbedderData(realm, wasmTable);
        if (embedderData instanceof JSWebAssemblyTableObject) {
            return (JSWebAssemblyTableObject)embedderData;
        }
        JSObjectFactory factory = context.getWebAssemblyTableFactory();
        JSWebAssemblyTableObject object = new JSWebAssemblyTableObject(factory.getShape(realm), wasmTable);
        factory.initProto(object, realm);
        JSWebAssembly.setEmbedderData(realm, wasmTable, object);
        return context.trackAllocation(object);
    }

    private static JSFunctionObject createLengthGetterFunction(final JSRealm realm) {
        JSFunctionData getterData = realm.getContext().getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.WebAssemblyTableGetLength, c -> {
            RootCallTarget callTarget = new JavaScriptRootNode(c.getLanguage(), null, null){
                private final BranchProfile errorBranch;
                @Node.Child
                InteropLibrary tableLengthLib;
                {
                    super(lang, sourceSection, frameDescriptor);
                    this.errorBranch = BranchProfile.create();
                    this.tableLengthLib = InteropLibrary.getFactory().createDispatched(5);
                }

                @Override
                public Object execute(VirtualFrame frame) {
                    Object thiz = JSFrameUtil.getThisObj(frame);
                    if (!JSWebAssemblyTable.isJSWebAssemblyTable(thiz)) {
                        this.errorBranch.enter();
                        throw Errors.createTypeError("WebAssembly.Table.length(): Receiver is not a WebAssembly.Table", (Node)this);
                    }
                    Object wasmTable = ((JSWebAssemblyTableObject)thiz).getWASMTable();
                    try {
                        Object lengthFn = realm.getWASMTableLength();
                        return this.tableLengthLib.execute(lengthFn, wasmTable);
                    }
                    catch (InteropException ex) {
                        throw Errors.shouldNotReachHere(ex);
                    }
                }
            }.getCallTarget();
            return JSFunctionData.createCallOnly(c, callTarget, 0, Strings.concat(Strings.GET_SPC, LENGTH));
        });
        return JSFunction.create(realm, getterData);
    }
}

