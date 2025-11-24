
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;

public final class AsyncGeneratorRequest {
    private final Completion.Type completionType;
    private final Object completionValue;
    private final PromiseCapabilityRecord promiseCapability;

    private AsyncGeneratorRequest(Completion.Type completionType, Object completionValue, PromiseCapabilityRecord promiseCapability) {
        this.completionType = completionType;
        this.completionValue = completionValue;
        this.promiseCapability = promiseCapability;
    }

    public Completion getCompletion() {
        return new Completion(this.completionType, this.completionValue);
    }

    public Object getCompletionValue() {
        return this.completionValue;
    }

    public PromiseCapabilityRecord getPromiseCapability() {
        return this.promiseCapability;
    }

    public boolean isNormal() {
        return this.completionType == Completion.Type.Normal;
    }

    public boolean isAbruptCompletion() {
        return this.completionType != Completion.Type.Normal;
    }

    public boolean isReturn() {
        return this.completionType == Completion.Type.Return;
    }

    public boolean isThrow() {
        return this.completionType == Completion.Type.Throw;
    }

    public static AsyncGeneratorRequest create(Completion completion, PromiseCapabilityRecord promiseCapability) {
        return new AsyncGeneratorRequest(completion.type, completion.value, promiseCapability);
    }
}

