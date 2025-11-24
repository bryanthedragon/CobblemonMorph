
package com.oracle.truffle.js.runtime.builtins;

import java.lang.ref.WeakReference;

public class FinalizationRecord {
    private WeakReference<Object> weakRefTarget;
    private Object heldValue;
    private WeakReference<Object> unregisterToken;

    public FinalizationRecord(WeakReference<Object> weakRefTarget, Object heldValue, Object unregisterToken) {
        assert (weakRefTarget != null && weakRefTarget.get() != null);
        this.weakRefTarget = weakRefTarget;
        this.heldValue = heldValue;
        this.unregisterToken = new WeakReference<Object>(unregisterToken);
    }

    public WeakReference<Object> getWeakRefTarget() {
        return this.weakRefTarget;
    }

    public Object getHeldValue() {
        return this.heldValue;
    }

    public WeakReference<Object> getUnregisterToken() {
        return this.unregisterToken;
    }
}

