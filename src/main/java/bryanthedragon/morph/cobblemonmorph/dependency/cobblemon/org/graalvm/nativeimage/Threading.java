
package org.graalvm.nativeimage;

import java.util.concurrent.TimeUnit;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.impl.ThreadingSupport;

public final class Threading {
    private Threading() {
    }

    public static void registerRecurringCallback(long interval, TimeUnit unit, RecurringCallback callback) {
        ImageSingletons.lookup(ThreadingSupport.class).registerRecurringCallback(interval, unit, callback);
    }

    public static interface RecurringCallbackAccess {
        public void throwException(Throwable var1);
    }

    @FunctionalInterface
    public static interface RecurringCallback {
        public void run(RecurringCallbackAccess var1);
    }
}

