
package org.graalvm.nativeimage.impl;

import java.util.concurrent.TimeUnit;
import org.graalvm.nativeimage.Threading;

public interface ThreadingSupport {
    public void registerRecurringCallback(long var1, TimeUnit var3, Threading.RecurringCallback var4);
}

