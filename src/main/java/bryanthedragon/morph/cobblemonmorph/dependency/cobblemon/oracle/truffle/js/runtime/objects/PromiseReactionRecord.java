
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;

public final class PromiseReactionRecord {
    private final PromiseCapabilityRecord capability;
    private final boolean fulfill;
    private final Object handler;

    private PromiseReactionRecord(PromiseCapabilityRecord capability, Object handler, boolean fulfill) {
        this.capability = capability;
        this.handler = handler;
        this.fulfill = fulfill;
    }

    public PromiseCapabilityRecord getCapability() {
        return this.capability;
    }

    public Object getHandler() {
        return this.handler;
    }

    public boolean isFulfill() {
        return this.fulfill;
    }

    public boolean isReject() {
        return !this.isFulfill();
    }

    public static PromiseReactionRecord create(PromiseCapabilityRecord capability, Object handler, boolean fulfill) {
        return new PromiseReactionRecord(capability, handler, fulfill);
    }
}

