package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.js.runtime.JSConfig;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public abstract class DebugCounter {
   private DebugCounter() {
   }

   public abstract long get();

   public abstract void inc();

   public static DebugCounter create(String name) {
      return JSConfig.DebugCounters ? DebugCounter.DebugCounterImpl.createImpl(name) : DebugCounter.Dummy.INSTANCE;
   }

   public static void dumpCounters() {
      if (JSConfig.DebugCounters) {
         DebugCounter.DebugCounterImpl.dumpCounters(System.out);
      }
   }

   private static final class DebugCounterImpl extends DebugCounter {
      private static final ArrayList<DebugCounter> allCounters = new ArrayList<>();
      private final String name;
      private final AtomicLong value;

      private DebugCounterImpl(String name) {
         this.name = name;
         this.value = new AtomicLong();
         allCounters.add(this);
      }

      private static DebugCounter createImpl(String name) {
         return new DebugCounter.DebugCounterImpl(name);
      }

      @Override
      public long get() {
         return this.value.get();
      }

      @Override
      public void inc() {
         this.value.incrementAndGet();
      }

      @Override
      public String toString() {
         return this.name + ": " + this.get();
      }

      private static void dumpCounters(PrintStream out) {
         for (DebugCounter counter : allCounters) {
            out.println(counter);
         }
      }

      static {
         assert JSConfig.DebugCounters;

         Runtime.getRuntime().addShutdownHook(new Thread(() -> dumpCounters(System.out)));
      }
   }

   private static final class Dummy extends DebugCounter {
      static final DebugCounter INSTANCE = new DebugCounter.Dummy();

      @Override
      public long get() {
         return 0L;
      }

      @Override
      public void inc() {
      }
   }
}
