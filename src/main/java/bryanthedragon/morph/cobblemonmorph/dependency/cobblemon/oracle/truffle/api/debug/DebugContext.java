
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.debug.DebugValue;
import com.oracle.truffle.api.debug.Debugger;
import com.oracle.truffle.api.debug.DebuggerExecutionLifecycle;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.source.Source;
import java.io.IOException;
import java.util.function.Supplier;

public final class DebugContext {
    private final DebuggerExecutionLifecycle executionLifecycle;
    private final TruffleContext context;

    DebugContext(DebuggerExecutionLifecycle executionLifecycle, TruffleContext context) {
        this.executionLifecycle = executionLifecycle;
        this.context = context;
    }

    public DebugValue evaluate(String code, String languageId) {
        assert (code != null);
        Object prevContext = this.context.enter(null);
        try {
            Debugger debugger = this.executionLifecycle.getDebugger();
            CallTarget target = debugger.getEnv().parse(Source.newBuilder(languageId, code, "eval").build(), new String[0]);
            Object result = target.call(new Object[0]);
            LanguageInfo languageInfo = debugger.getEnv().getLanguages().get(languageId);
            DebugValue.HeapValue heapValue = new DebugValue.HeapValue(this.executionLifecycle.getSession(), languageInfo, null, result);
            return heapValue;
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        finally {
            this.context.leave(null, prevContext);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> T runInContext(Supplier<T> run2) {
        assert (run2 != null);
        Object prevContext = this.context.enter(null);
        try {
            T ret;
            T t = ret = run2.get();
            return t;
        }
        finally {
            this.context.leave(null, prevContext);
        }
    }

    public DebugContext getParent() {
        TruffleContext parent = this.context.getParent();
        if (parent == null) {
            return null;
        }
        return this.executionLifecycle.getCachedDebugContext(parent);
    }
}

