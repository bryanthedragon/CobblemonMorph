
package com.oracle.truffle.api;

import com.oracle.truffle.api.LanguageAccessor;
import com.oracle.truffle.api.TruffleContext;

public abstract class ContextThreadLocal<T> {
    protected ContextThreadLocal(Object polyglotObject) {
        if (!LanguageAccessor.ENGINE.isPolyglotObject(polyglotObject)) {
            throw new IllegalStateException("No custom subclasses of ContextLocal allowed.");
        }
    }

    public abstract T get();

    public abstract T get(Thread var1);

    public abstract T get(TruffleContext var1);

    public abstract T get(TruffleContext var1, Thread var2);
}

