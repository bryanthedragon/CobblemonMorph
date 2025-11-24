
package com.oracle.truffle.js.runtime;

public final class JSNoSuchMethodAdapter {
    private final Object function;
    private final Object key;
    private final Object thisObject;

    public JSNoSuchMethodAdapter(Object function, Object key, Object thisObject) {
        this.function = function;
        this.key = key;
        this.thisObject = thisObject;
    }

    public Object getFunction() {
        return this.function;
    }

    public Object getKey() {
        return this.key;
    }

    public Object getThisObject() {
        return this.thisObject;
    }
}

