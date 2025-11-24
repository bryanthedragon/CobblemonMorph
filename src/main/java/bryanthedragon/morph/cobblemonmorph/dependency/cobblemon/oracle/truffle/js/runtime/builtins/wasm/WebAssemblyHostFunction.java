
package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.access.GetIteratorBaseNode;
import com.oracle.truffle.js.nodes.access.IterableToListNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.wasm.ToJSValueNode;
import com.oracle.truffle.js.nodes.wasm.ToWebAssemblyValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyValueTypes;
import com.oracle.truffle.js.runtime.interop.InteropArray;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;

@ExportLibrary(value=InteropLibrary.class)
public class WebAssemblyHostFunction
implements TruffleObject {
    private final Object fn;
    private final TruffleString[] resultTypes;
    private final boolean anyReturnTypeIsI64;
    private final boolean anyArgTypeIsI64;

    public WebAssemblyHostFunction(JSContext context, Object fn, TruffleString typeInfo) {
        assert (JSRuntime.isCallable(fn));
        this.fn = fn;
        int idxOpen = Strings.indexOf(typeInfo, '(');
        int idxClose = Strings.indexOf(typeInfo, ')');
        TruffleString returnTypes = Strings.lazySubstring(typeInfo, idxClose + 1);
        this.resultTypes = !Strings.isEmpty(returnTypes) ? Strings.split(context, returnTypes, Strings.SPACE) : new TruffleString[]{};
        this.anyReturnTypeIsI64 = Strings.indexOf(typeInfo, JSWebAssemblyValueTypes.I64, idxClose + 1) >= 0;
        this.anyArgTypeIsI64 = Strings.indexOf(typeInfo, JSWebAssemblyValueTypes.I64, idxOpen + 1, idxClose) >= 0;
    }

    @ExportMessage
    public static final boolean isExecutable(WebAssemblyHostFunction receiver) {
        return true;
    }

    @ExportMessage
    public final Object execute(Object[] args, @Cached ToWebAssemblyValueNode toWebAssemblyValueNode, @Cached ToJSValueNode toJSValueNode, @Cached(value="createCall()", uncached="getUncachedCall()") JSFunctionCallNode callNode, @Cached BranchProfile errorBranch, @Cached GetIteratorBaseNode getIteratorNode, @Cached IterableToListNode iterableToListNode, @CachedLibrary(value="this") InteropLibrary self) {
        JSContext context = JavaScriptLanguage.get(self).getJSContext();
        if (!context.getContextOptions().isWasmBigInt() && (this.anyReturnTypeIsI64 || this.anyArgTypeIsI64)) {
            errorBranch.enter();
            throw Errors.createTypeError("wasm function signature contains illegal type");
        }
        Object[] jsArgs = new Object[args.length];
        for (int i = 0; i < args.length; ++i) {
            jsArgs[i] = toJSValueNode.execute(args[i]);
        }
        Object result = callNode.executeCall(JSArguments.create(Undefined.instance, this.fn, jsArgs));
        if (this.resultTypes.length == 0) {
            return Undefined.instance;
        }
        if (this.resultTypes.length == 1) {
            return toWebAssemblyValueNode.execute(result, this.resultTypes[0]);
        }
        IteratorRecord iterator = getIteratorNode.execute(result);
        SimpleArrayList<Object> values = iterableToListNode.execute(iterator);
        if (this.resultTypes.length != values.size()) {
            errorBranch.enter();
            throw Errors.createTypeError("invalid result array arity");
        }
        Object[] wasmValues = new Object[values.size()];
        for (int i = 0; i < values.size(); ++i) {
            wasmValues[i] = toWebAssemblyValueNode.execute(values.get(i), this.resultTypes[i]);
        }
        return InteropArray.create(wasmValues);
    }
}

