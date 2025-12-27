package org.graalvm.nativeimage;

import java.util.concurrent.TimeUnit;
import org.graalvm.nativeimage.impl.ThreadingSupport;

public final class Threading {
   private Threading() {
   }

   public static void registerRecurringCallback(long interval, TimeUnit unit, Threading.RecurringCallback callback) {
      ImageSingletons.lookup(ThreadingSupport.class).registerRecurringCallback(interval, unit, callback);
   }

   @FunctionalInterface
   public interface RecurringCallback {
      void run(Threading.RecurringCallbackAccess access);
   }

   public interface RecurringCallbackAccess {
      void throwException(Throwable t);
   }
}
