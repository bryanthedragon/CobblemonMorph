
package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.wasm.ToJSValueNode;
import com.oracle.truffle.js.nodes.wasm.ToWebAssemblyValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssembly;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyGlobal;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyGlobalObject;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyInstanceObject;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyMemory;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyMemoryObject;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyTable;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyTableObject;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyValueTypes;
import com.oracle.truffle.js.runtime.builtins.wasm.WebAssemblyHostFunction;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class JSWebAssemblyInstance
extends JSNonProxy
implements JSConstructorFactory.Default,
PrototypeSupplier {
    public static final TruffleString CLASS_NAME = Strings.constant("Instance");
    public static final TruffleString EXPORTS = Strings.constant("exports");
    public static final TruffleString WEB_ASSEMBLY_INSTANCE = Strings.constant("WebAssembly.Instance");
    public static final JSWebAssemblyInstance INSTANCE = new JSWebAssemblyInstance();

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return this.getClassName();
    }

    public static boolean isJSWebAssemblyInstance(Object object) {
        return object instanceof JSWebAssemblyInstanceObject;
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject constructor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, prototype, constructor);
        JSObjectUtil.putAccessorProperty(ctx, prototype, EXPORTS, JSWebAssemblyInstance.createExportsGetterFunction(realm), Undefined.instance, JSAttributes.configurableEnumerableWritable());
        JSObjectUtil.putToStringTag(prototype, WEB_ASSEMBLY_INSTANCE);
        return prototype;
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getWebAssemblyInstancePrototype();
    }

    @Override
    public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm);
    }

    public static JSWebAssemblyInstanceObject create(JSContext context, JSRealm realm, Object wasmInstance, Object wasmModule) {
        JSObjectFactory factory = context.getWebAssemblyInstanceFactory();
        JSObject exportsObject = JSWebAssemblyInstance.createExportsObject(context, realm, wasmInstance, wasmModule);
        JSWebAssemblyInstanceObject object = new JSWebAssemblyInstanceObject(factory.getShape(realm), wasmInstance, exportsObject);
        factory.initProto(object, realm);
        return context.trackAllocation(object);
    }

    private static JSFunctionObject createExportsGetterFunction(JSRealm realm) {
        JSFunctionData getterData = realm.getContext().getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.WebAssemblyInstanceGetExports, c -> {
            RootCallTarget callTarget = new JavaScriptRootNode(c.getLanguage(), null, null){
                private final BranchProfile errorBranch = BranchProfile.create();

                @Override
                public Object execute(VirtualFrame frame) {
                    Object thiz = JSFrameUtil.getThisObj(frame);
                    if (JSWebAssemblyInstance.isJSWebAssemblyInstance(thiz)) {
                        return ((JSWebAssemblyInstanceObject)thiz).getExports();
                    }
                    this.errorBranch.enter();
                    throw Errors.createTypeError("WebAssembly.Instance.exports(): Receiver is not a WebAssembly.Instance", (Node)this);
                }
            }.getCallTarget();
            return JSFunctionData.createCallOnly(c, callTarget, 0, Strings.concat(Strings.GET_SPC, EXPORTS));
        });
        return JSFunction.create(realm, getterData);
    }

    private static JSObject createExportsObject(JSContext context, JSRealm realm, Object wasmInstance, Object wasmModule) {
        JSObject exports = JSOrdinary.createWithNullPrototype(context);
        try {
            Object exportsFunction = realm.getWASMModuleExports();
            Object exportsInfo = InteropLibrary.getUncached(exportsFunction).execute(exportsFunction, wasmModule);
            Object instanceExport = realm.getWASMInstanceExport();
            InteropLibrary exportsInterop = InteropLibrary.getUncached(exportsInfo);
            long size = exportsInterop.getArraySize(exportsInfo);
            for (long i = 0L; i < size; ++i) {
                Object value2;
                Object exportInfo = exportsInterop.readArrayElement(exportsInfo, i);
                InteropLibrary exportInterop = InteropLibrary.getUncached(exportInfo);
                TruffleString name = JSWebAssemblyInstance.asTString(exportInterop.readMember(exportInfo, "name"));
                TruffleString externtype = JSWebAssemblyInstance.asTString(exportInterop.readMember(exportInfo, "kind"));
                Object externval = InteropLibrary.getUncached().execute(instanceExport, wasmInstance, Strings.toJavaString(name));
                if (Strings.equals(Strings.FUNCTION, externtype)) {
                    TruffleString typeInfo = JSWebAssemblyInstance.asTString(exportInterop.readMember(exportInfo, "type"));
                    value2 = JSWebAssemblyInstance.exportFunction(context, realm, externval, typeInfo);
                } else if (Strings.equals(Strings.GLOBAL, externtype)) {
                    TruffleString type = JSWebAssemblyInstance.asTString(exportInterop.readMember(exportInfo, "type"));
                    int sepIndex = Strings.indexOf(type, ' ');
                    TruffleString valueType = Strings.substring(context, type, 0, sepIndex);
                    boolean mutable = Strings.regionEquals(type, sepIndex + 1, Strings.constant("mut"), 0, 3);
                    value2 = JSWebAssemblyGlobal.create(context, realm, externval, valueType, mutable);
                } else if (Strings.MEMORY.equals(externtype)) {
                    value2 = JSWebAssemblyMemory.create(context, realm, externval);
                } else {
                    assert (Strings.TABLE.equals(externtype));
                    value2 = JSWebAssemblyTable.create(context, realm, externval);
                }
                JSObject.set((JSDynamicObject)exports, name, value2);
            }
        }
        catch (InteropException ex) {
            throw Errors.shouldNotReachHere(ex);
        }
        JSObject.setIntegrityLevel(exports, true);
        return exports;
    }

    @CompilerDirectives.TruffleBoundary
    public static Object exportFunction(final JSContext context, final JSRealm realm, final Object export, TruffleString typeInfo) {
        Object embedderData = JSWebAssembly.getEmbedderData(realm, export);
        if (embedderData instanceof JSFunctionObject) {
            return embedderData;
        }
        int idxOpen = Strings.indexOf(typeInfo, '(');
        int idxClose = Strings.indexOf(typeInfo, ')');
        TruffleString name = Strings.substring(context, typeInfo, 0, idxOpen);
        TruffleString argTypes = Strings.lazySubstring(typeInfo, idxOpen + 1, idxClose - (idxOpen + 1));
        TruffleString returnTypes = Strings.lazySubstring(typeInfo, idxClose + 1);
        final TruffleString[] paramTypes = !Strings.isEmpty(argTypes) ? Strings.split(context, argTypes, Strings.SPACE) : new TruffleString[]{};
        TruffleString[] resultTypes = !Strings.isEmpty(returnTypes) ? Strings.split(context, returnTypes, Strings.SPACE) : new TruffleString[]{};
        final int argCount = paramTypes.length;
        final int returnLength = resultTypes.length;
        final boolean anyReturnTypeIsI64 = Strings.indexOf(typeInfo, JSWebAssemblyValueTypes.I64, idxClose + 1) >= 0;
        final boolean anyArgTypeIsI64 = Strings.indexOf(typeInfo, JSWebAssemblyValueTypes.I64, idxOpen + 1, idxClose) >= 0;
        RootCallTarget callTarget = new JavaScriptRootNode(context.getLanguage(), null, null){
            @Node.Child
            ToWebAssemblyValueNode toWebAssemblyValueNode;
            @Node.Child
            ToJSValueNode toJSValueNode;
            private final BranchProfile errorBranch;
            @Node.Child
            InteropLibrary exportFunctionLib;
            @Node.Child
            InteropLibrary readArrayElementLib;
            @CompilerDirectives.CompilationFinal(dimensions=1)
            TruffleString[] argTypesArray;
            {
                super(lang, sourceSection, frameDescriptor);
                this.toWebAssemblyValueNode = ToWebAssemblyValueNode.create();
                this.toJSValueNode = ToJSValueNode.create();
                this.errorBranch = BranchProfile.create();
                this.exportFunctionLib = InteropLibrary.getFactory().createDispatched(5);
                this.readArrayElementLib = InteropLibrary.getFactory().createDispatched(5);
                this.argTypesArray = paramTypes;
            }

            @Override
            public Object execute(VirtualFrame frame) {
                if (!context.getContextOptions().isWasmBigInt() && (anyReturnTypeIsI64 || anyArgTypeIsI64)) {
                    this.errorBranch.enter();
                    throw Errors.createTypeError("wasm function signature contains illegal type");
                }
                Object[] frameArguments = frame.getArguments();
                int userArgumentCount = JSArguments.getUserArgumentCount(frameArguments);
                Object[] wasmArgs = new Object[argCount];
                for (int i = 0; i < argCount; ++i) {
                    Object wasmArg = i < userArgumentCount ? JSArguments.getUserArgument(frameArguments, i) : Undefined.instance;
                    wasmArgs[i] = this.toWebAssemblyValueNode.execute(wasmArg, this.argTypesArray[i]);
                }
                try {
                    Object wasmResult;
                    try {
                        wasmResult = this.exportFunctionLib.execute(export, wasmArgs);
                    }
                    catch (GraalJSException jsex) {
                        this.errorBranch.enter();
                        throw jsex;
                    }
                    catch (AbstractTruffleException tex) {
                        this.errorBranch.enter();
                        ExceptionType type = InteropLibrary.getUncached(tex).getExceptionType(tex);
                        if (type == ExceptionType.INTERRUPT || type == ExceptionType.EXIT) {
                            throw tex;
                        }
                        throw Errors.createRuntimeError(tex, this);
                    }
                    if (returnLength == 0) {
                        return Undefined.instance;
                    }
                    if (returnLength == 1) {
                        return this.toJSValueNode.execute(wasmResult);
                    }
                    Object[] values = new Object[returnLength];
                    for (int i = 0; i < returnLength; ++i) {
                        values[i] = this.toJSValueNode.execute(this.readArrayElementLib.readArrayElement(wasmResult, i));
                    }
                    return JSArray.createConstantObjectArray(context, realm, values);
                }
                catch (InteropException ex) {
                    throw Errors.shouldNotReachHere(ex);
                }
            }
        }.getCallTarget();
        JSFunctionData functionData = JSFunctionData.createCallOnly(context, callTarget, argCount, name);
        JSFunctionObject result = JSFunction.create(realm, functionData);
        JSObjectUtil.putHiddenProperty(result, JSWebAssembly.FUNCTION_ADDRESS, export);
        JSWebAssembly.setEmbedderData(realm, export, result);
        return result;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @CompilerDirectives.TruffleBoundary
    public static Object transformImportObject(JSContext context, JSRealm realm, Object wasmModule, Object importObject) {
        try {
            JSObject transformedImportObject = JSOrdinary.create(context, realm);
            Object importsFn = realm.getWASMModuleImports();
            Object imports = InteropLibrary.getUncached(importsFn).execute(importsFn, wasmModule);
            InteropLibrary importsInterop = InteropLibrary.getUncached(imports);
            long size = importsInterop.getArraySize(imports);
            for (long i = 0L; i < size; ++i) {
                void var21_20;
                Object wasmValue;
                Object descriptor = importsInterop.readArrayElement(imports, i);
                InteropLibrary descriptorInterop = InteropLibrary.getUncached(descriptor);
                TruffleString module = JSWebAssemblyInstance.asTString(descriptorInterop.readMember(descriptor, "module"));
                Object moduleImportObject = JSInteropUtil.get(importObject, module);
                InteropLibrary moduleImportObjectInterop = InteropLibrary.getUncached(moduleImportObject);
                if (!moduleImportObjectInterop.hasMembers(moduleImportObject)) {
                    throw Errors.createTypeErrorNotAnObject(moduleImportObject);
                }
                TruffleString name = JSWebAssemblyInstance.asTString(descriptorInterop.readMember(descriptor, "name"));
                Object value2 = JSInteropUtil.get(moduleImportObject, name);
                TruffleString externType = JSWebAssemblyInstance.asTString(descriptorInterop.readMember(descriptor, "kind"));
                if (Strings.equals(Strings.FUNCTION, externType)) {
                    if (!JSRuntime.isCallable(value2)) {
                        throw Errors.createLinkError("Imported value is not callable");
                    }
                    if (JSWebAssembly.isExportedFunction(value2)) {
                        wasmValue = JSWebAssembly.getExportedFunction((JSDynamicObject)value2);
                    } else {
                        TruffleString truffleString = JSWebAssemblyInstance.asTString(descriptorInterop.readMember(descriptor, "type"));
                        wasmValue = JSWebAssemblyInstance.createHostFunction(context, value2, truffleString);
                    }
                } else if (Strings.equals(Strings.GLOBAL, externType)) {
                    boolean bl = JSRuntime.isNumber(value2);
                    boolean isBigInt = JSRuntime.isBigInt(value2);
                    if (bl || context.getContextOptions().isWasmBigInt() && isBigInt) {
                        TruffleString valueType = JSWebAssemblyInstance.asTString(descriptorInterop.readMember(descriptor, "type"));
                        boolean isI64 = JSWebAssemblyValueTypes.isI64(valueType);
                        if (!context.getContextOptions().isWasmBigInt() && isI64) {
                            throw Errors.createLinkError("Can't import the value of i64 WebAssembly.Global");
                        }
                        if (isI64 && bl) {
                            throw Errors.createLinkError("Value of valtype i64 must be BigInt");
                        }
                        if (!isI64 && isBigInt) {
                            throw Errors.createLinkError("BigInt can only be stored in valtype i64");
                        }
                        Object webAssemblyValue = ToWebAssemblyValueNode.getUncached().execute(value2, valueType);
                        try {
                            Object createGlobal = realm.getWASMGlobalAlloc();
                            wasmValue = InteropLibrary.getUncached(createGlobal).execute(createGlobal, valueType, false, webAssemblyValue);
                        }
                        catch (InteropException ex) {
                            throw Errors.shouldNotReachHere(ex);
                        }
                    } else {
                        if (!JSWebAssemblyGlobal.isJSWebAssemblyGlobal(value2)) throw Errors.createLinkError("Imported value is not WebAssembly.Global object");
                        wasmValue = ((JSWebAssemblyGlobalObject)value2).getWASMGlobal();
                    }
                } else if (Strings.equals(Strings.MEMORY, externType)) {
                    if (!JSWebAssemblyMemory.isJSWebAssemblyMemory(value2)) throw Errors.createLinkError("Imported value is not WebAssembly.Memory object");
                    wasmValue = ((JSWebAssemblyMemoryObject)value2).getWASMMemory();
                } else {
                    assert (Strings.equals(Strings.TABLE, externType)) : externType;
                    if (!JSWebAssemblyTable.isJSWebAssemblyTable(value2)) throw Errors.createLinkError("Imported value is not WebAssembly.Table object");
                    wasmValue = ((JSWebAssemblyTableObject)value2).getWASMTable();
                }
                if (JSObject.hasOwnProperty((JSDynamicObject)transformedImportObject, module)) {
                    JSDynamicObject jSDynamicObject = (JSDynamicObject)JSObject.get((JSDynamicObject)transformedImportObject, module);
                } else {
                    JSObject jSObject = JSOrdinary.create(context, realm);
                    JSObject.set((JSDynamicObject)transformedImportObject, module, (Object)jSObject);
                }
                JSObject.set((JSDynamicObject)var21_20, name, wasmValue);
            }
            return transformedImportObject;
        }
        catch (InteropException ex) {
            throw Errors.shouldNotReachHere(ex);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static Object createHostFunction(JSContext context, Object fn, TruffleString typeInfo) {
        return new WebAssemblyHostFunction(context, fn, typeInfo);
    }

    private static TruffleString asTString(Object string) throws UnsupportedMessageException {
        if (string instanceof String) {
            return Strings.fromJavaString((String)string);
        }
        return InteropLibrary.getUncached(string).asTruffleString(string);
    }
}

