
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class PromiseCapabilityRecord {
    private JSDynamicObject promise;
    private Object resolve;
    private Object reject;

    private PromiseCapabilityRecord(JSDynamicObject promise, JSDynamicObject resolve, JSDynamicObject reject) {
        this.promise = promise;
        this.resolve = resolve;
        this.reject = reject;
    }

    public static PromiseCapabilityRecord create(JSDynamicObject promise, JSDynamicObject resolve, JSDynamicObject reject) {
        return new PromiseCapabilityRecord(promise, resolve, reject);
    }

    public JSDynamicObject getPromise() {
        return this.promise;
    }

    public Object getResolve() {
        return this.resolve;
    }

    public Object getReject() {
        return this.reject;
    }

    public void setPromise(JSDynamicObject promise) {
        this.promise = promise;
    }

    public void setResolve(Object resolve) {
        this.resolve = resolve;
    }

    public void setReject(Object reject) {
        this.reject = reject;
    }
}

