package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.concurrent.atomic.AtomicInteger;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class PolyglotThread extends Thread {
   private final PolyglotLanguageContext languageContext;
   private final CallTarget callTarget;
   volatile boolean hardExitNotificationThread;
   private static final AtomicInteger THREAD_INIT_NUMBER = new AtomicInteger(0);

   PolyglotThread(PolyglotLanguageContext languageContext, Runnable runnable, ThreadGroup group, long stackSize) {
      super(group, runnable, createDefaultName(languageContext), stackSize);
      this.languageContext = languageContext;
      this.setUncaughtExceptionHandler(languageContext.getPolyglotExceptionHandler());
      this.callTarget = PolyglotThread.ThreadSpawnRootNode.lookup(languageContext.getLanguageInstance());
   }

   private static String createDefaultName(PolyglotLanguageContext creator) {
      return "Polyglot-" + creator.language.getId() + "-" + THREAD_INIT_NUMBER.getAndIncrement();
   }

   PolyglotContextImpl getOwnerContext() {
      return this.languageContext.context;
   }

   @Override
   public synchronized void start() {
      PolyglotContextImpl polyglotContext = this.languageContext.context;
      Thread hardExitTriggeringThread = polyglotContext.closeExitedTriggerThread;
      if (hardExitTriggeringThread != null) {
         Thread currentThread = currentThread();
         if (hardExitTriggeringThread == currentThread
            || currentThread instanceof PolyglotThread
               && ((PolyglotThread)currentThread).getOwnerContext() == polyglotContext
               && ((PolyglotThread)currentThread).hardExitNotificationThread) {
            this.hardExitNotificationThread = true;
         }
      }

      super.start();
   }

   @Override
   public void run() {
      try {
         this.callTarget.call(this.languageContext, this, new PolyglotThread.PolyglotThreadRunnable() {
            @CompilerDirectives.TruffleBoundary
            @Override
            public void execute() {
               PolyglotThread.super.run();
            }
         });
      } catch (Throwable var2) {
         throw PolyglotImpl.engineToLanguageException(var2);
      }
   }

   private interface PolyglotThreadRunnable {
      void execute();
   }

   static final class ThreadSpawnRootNode extends RootNode {
      ThreadSpawnRootNode(PolyglotLanguageInstance languageInstance) {
         super(languageInstance.spi);
      }

      @Override
      public Object execute(VirtualFrame frame) {
         Object[] args = frame.getArguments();
         return executeImpl((PolyglotLanguageContext)args[0], (PolyglotThread)args[1], (PolyglotThread.PolyglotThreadRunnable)args[2]);
      }

      @CompilerDirectives.TruffleBoundary
      private static Object executeImpl(PolyglotLanguageContext languageContext, PolyglotThread thread, PolyglotThread.PolyglotThreadRunnable run) {
         Object[] prev = languageContext.enterThread(thread);

         assert prev == null;

         Object var5;
         try (AbstractPolyglotImpl.ThreadScope scope = languageContext.getImpl().getRootImpl().createThreadScope()) {
            run.execute();
            return null;
         } catch (PolyglotEngineImpl.CancelExecution var14) {
            if (PolyglotEngineOptions.TriggerUncaughtExceptionHandlerForCancel.getValue(languageContext.context.engine.getEngineOptionValues())) {
               throw var14;
            }

            var5 = null;
         } finally {
            languageContext.leaveAndDisposePolyglotThread(prev, thread);
         }

         return var5;
      }

      @Override
      public boolean isInternal() {
         return true;
      }

      public static CallTarget lookup(PolyglotLanguageInstance languageInstance) {
         CallTarget target = languageInstance.lookupCallTarget(PolyglotThread.ThreadSpawnRootNode.class);
         if (target == null) {
            target = languageInstance.installCallTarget(new PolyglotThread.ThreadSpawnRootNode(languageInstance));
         }

         return target;
      }
   }
}
