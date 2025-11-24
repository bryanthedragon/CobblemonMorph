
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.promise.UnwrapPromiseNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@ImportStatic(value={JSPromise.class})
public abstract class UnwrapPromiseNode
extends JavaScriptBaseNode {
    @Node.Child
    private PropertyGetNode getPromiseResult;
    private static final UnwrapPromiseNode UNCACHED = UnwrapPromiseNodeGen.create(null);

    protected UnwrapPromiseNode(JSContext context) {
        if (context != null) {
            this.getPromiseResult = PropertyGetNode.createGetHidden(JSPromise.PROMISE_RESULT, context);
        }
    }

    public static UnwrapPromiseNode create() {
        return UnwrapPromiseNodeGen.create(JavaScriptLanguage.get(null).getJSContext());
    }

    public final Object execute(JSDynamicObject promise) {
        if (this.getPromiseResult == null) {
            return this.doUncached(promise);
        }
        int promiseState = JSPromise.getPromiseState(promise);
        Object promiseResult = this.getPromiseResult.getValue(promise);
        return this.execute(promise, promiseState, promiseResult);
    }

    @CompilerDirectives.TruffleBoundary
    private Object doUncached(JSDynamicObject promise) {
        return this.execute(promise, JSPromise.getPromiseState(promise), JSDynamicObject.getOrNull(promise, JSPromise.PROMISE_RESULT));
    }

    protected abstract Object execute(JSDynamicObject var1, int var2, Object var3);

    @Specialization(guards={"promiseState == FULFILLED"})
    protected static Object fulfilled(JSDynamicObject promise, int promiseState, Object promiseResult) {
        return promiseResult;
    }

    @Specialization(guards={"promiseState == REJECTED"})
    protected static Object rejected(JSDynamicObject promise, int promiseState, Object promiseResult) {
        throw UserScriptException.create(promiseResult);
    }

    @Specialization(guards={"promiseState == PENDING"})
    protected static Object pending(JSDynamicObject promise, int promiseState, Object promiseResult) {
        throw Errors.createTypeError("Attempt to unwrap pending promise");
    }

    public static UnwrapPromiseNode getUncached() {
        return UNCACHED;
    }
}

