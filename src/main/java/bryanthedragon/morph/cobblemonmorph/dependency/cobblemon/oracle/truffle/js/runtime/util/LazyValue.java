
package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public final class LazyValue<T>
implements Supplier<T> {
    private final Supplier<T> supplier;
    private final AtomicReference<T> ref;

    public LazyValue(Supplier<T> supplier) {
        this.supplier = supplier;
        this.ref = new AtomicReference();
    }

    @Override
    public T get() {
        T value2 = this.ref.get();
        if (value2 == null) {
            value2 = this.init();
        }
        return value2;
    }

    @CompilerDirectives.TruffleBoundary
    private T init() {
        T value2 = this.supplier.get();
        if (!this.ref.compareAndSet(null, value2)) {
            value2 = this.ref.get();
        }
        return value2;
    }
}

