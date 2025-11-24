
package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.impl.AbstractFastThreadLocal;

final class DefaultContextThreadLocal
extends AbstractFastThreadLocal {
    static final DefaultContextThreadLocal SINGLETON = new DefaultContextThreadLocal();
    private final ThreadLocal<Object[]> threadLocal = new ThreadLocal();

    DefaultContextThreadLocal() {
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void set(Object[] data) {
        this.threadLocal.set(data);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public <C> Object[] get() {
        return this.threadLocal.get();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public <C> C fastGet(int index, Class<C> castType, boolean invalidateOnNull) {
        Object[] data = this.get();
        if (data == null) {
            return null;
        }
        Object result = data[index];
        assert (castType == null || result == null || result.getClass() == castType) : "invalid type";
        return (C)result;
    }
}

