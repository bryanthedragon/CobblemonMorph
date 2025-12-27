package org.graalvm.nativeimage.impl;

import java.util.concurrent.TimeUnit;
import org.graalvm.nativeimage.Threading;

public interface ThreadingSupport {
   void registerRecurringCallback(long interval, TimeUnit unit, Threading.RecurringCallback callback);
}
