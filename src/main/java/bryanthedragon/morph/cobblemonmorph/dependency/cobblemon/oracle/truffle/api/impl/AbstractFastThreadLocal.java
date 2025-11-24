
package com.oracle.truffle.api.impl;

public abstract class AbstractFastThreadLocal {
    protected AbstractFastThreadLocal() {
    }

    public abstract void set(Object[] var1);

    public abstract <C> Object[] get();

    public abstract <C> C fastGet(int var1, Class<C> var2, boolean var3);
}

