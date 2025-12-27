package com.oracle.truffle.js.runtime.util;

import java.util.concurrent.atomic.AtomicLong;

public final class TimeProfiler {
   private static final String CLASS_NAME = "[" + TimeProfiler.class.getSimpleName() + "] ";
   private final AtomicLong counter = new AtomicLong();

   public void printElapsed(long startTime, String event) {
      long elapsed = System.nanoTime() - startTime;
      this.counter.addAndGet(elapsed);
      System.out.println(CLASS_NAME + event + " took: " + TimeUtil.format(elapsed));
   }

   public void printCumulative() {
      System.out.println(CLASS_NAME + "cumulative: " + TimeUtil.format(this.counter.get()));
   }
}
