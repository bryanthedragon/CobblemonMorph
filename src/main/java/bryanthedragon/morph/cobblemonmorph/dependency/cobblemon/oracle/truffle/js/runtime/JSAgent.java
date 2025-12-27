package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.builtins.JSFinalizationRegistry;
import com.oracle.truffle.js.runtime.builtins.JSFinalizationRegistryObject;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import org.graalvm.collections.EconomicSet;
import org.graalvm.collections.Equivalence;

public abstract class JSAgent {
   private static final AtomicInteger signifierGenerator = new AtomicInteger(0);
   private final int signifier;
   private boolean canBlock;
   private final Deque<JSFunctionObject> promiseJobsQueue;
   private int interopCallStackDepth;
   private EconomicSet<Object> weakRefTargets;
   private final Deque<WeakReference<JSFinalizationRegistryObject>> finalizationRegistryQueue;
   private final Deque<JSAgentWaiterList.WaiterRecord> waitAsyncJobsQueue;
   private final PromiseRejectionTracker promiseRejectionTracker;

   public JSAgent(boolean canBlock) {
      this(null, canBlock);
   }

   public JSAgent(PromiseRejectionTracker promiseRejectionTracker, boolean canBlock) {
      this.promiseRejectionTracker = promiseRejectionTracker;
      this.signifier = signifierGenerator.incrementAndGet();
      this.canBlock = canBlock;
      this.promiseJobsQueue = new ArrayDeque<>();
      this.waitAsyncJobsQueue = new ConcurrentLinkedDeque<>();
      this.finalizationRegistryQueue = new ArrayDeque<>(4);
   }

   public abstract void wake();

   public int getSignifier() {
      return this.signifier;
   }

   public boolean canBlock() {
      return this.canBlock;
   }

   @CompilerDirectives.TruffleBoundary
   public final void enqueuePromiseJob(JSFunctionObject job) {
      this.promiseJobsQueue.push(job);
   }

   @CompilerDirectives.TruffleBoundary
   public void enqueueWaitAsyncPromiseJob(JSAgentWaiterList.WaiterRecord waiter) {
      this.waitAsyncJobsQueue.push(waiter);
      if (waiter.isReadyToResolve()) {
         waiter.getAgent().wake();
      }
   }

   @CompilerDirectives.TruffleBoundary
   public final void processAllPromises(boolean processWeakRefs) {
      try {
         this.interopBoundaryEnter();
         boolean checkWaiterRecords = !this.waitAsyncJobsQueue.isEmpty();

         while (!this.promiseJobsQueue.isEmpty() || checkWaiterRecords) {
            if (checkWaiterRecords) {
               checkWaiterRecords = this.processWaitAsyncJobs();
            }

            if (!this.promiseJobsQueue.isEmpty()) {
               JSFunctionObject nextJob = this.promiseJobsQueue.pollLast();
               if (JSFunction.isJSFunction(nextJob)) {
                  checkWaiterRecords = true;
                  JSFunction.call(nextJob, Undefined.instance, JSArguments.EMPTY_ARGUMENTS_ARRAY);
               }
            }
         }
      } catch (Throwable var7) {
         this.promiseJobsQueue.clear();
         this.waitAsyncJobsQueue.clear();
         throw var7;
      } finally {
         this.interopBoundaryExit();
         if (processWeakRefs) {
            if (this.weakRefTargets != null) {
               this.weakRefTargets.clear();
            }

            this.cleanupFinalizers();
         }

         if (this.promiseRejectionTracker != null) {
            this.promiseRejectionTracker.promiseReactionJobsProcessed();
         }
      }
   }

   private boolean processWaitAsyncJobs() {
      boolean checkWaiterRecords = false;
      Iterator<JSAgentWaiterList.WaiterRecord> iter = this.waitAsyncJobsQueue.descendingIterator();

      while (iter.hasNext()) {
         JSAgentWaiterList.WaiterRecord wr = iter.next();
         JSAgentWaiterList.JSAgentWaiterListEntry wl = wr.getWaiterListEntry();
         wl.enterCriticalSection();
         boolean isReadyToResolve = wr.isReadyToResolve();

         try {
            if (isReadyToResolve) {
               iter.remove();
               checkWaiterRecords = true;
               if (wl.contains(wr)) {
                  wr.setResult(Strings.TIMED_OUT);
                  wl.remove(wr);
               }
            }
         } finally {
            wl.leaveCriticalSection();
         }

         if (isReadyToResolve) {
            JSDynamicObject resolve = (JSDynamicObject)wr.getPromiseCapability().getResolve();

            assert JSFunction.isJSFunction(resolve);

            Object result = wr.getResult();
            JSFunction.call(JSArguments.createOneArg(Undefined.instance, resolve, result));
         }
      }

      return checkWaiterRecords;
   }

   private void cleanupFinalizers() {
      Iterator<WeakReference<JSFinalizationRegistryObject>> iter = this.finalizationRegistryQueue.iterator();

      while (iter.hasNext()) {
         WeakReference<JSFinalizationRegistryObject> ref = iter.next();
         JSFinalizationRegistryObject fr = ref.get();
         if (fr == null) {
            iter.remove();
         } else {
            JSFinalizationRegistry.hostCleanupFinalizationRegistry(fr);
         }
      }
   }

   public final void interopBoundaryEnter() {
      this.interopCallStackDepth++;
   }

   public final boolean interopBoundaryExit() {
      return --this.interopCallStackDepth == 0;
   }

   @CompilerDirectives.TruffleBoundary
   public boolean addWeakRefTargetToSet(Object target) {
      if (this.weakRefTargets == null) {
         this.weakRefTargets = EconomicSet.create(Equivalence.IDENTITY);
      }

      return this.weakRefTargets.add(target);
   }

   @CompilerDirectives.TruffleBoundary
   public void registerFinalizationRegistry(JSFinalizationRegistryObject finalizationRegistry) {
      this.finalizationRegistryQueue.add(new WeakReference<>(finalizationRegistry));
   }

   @CompilerDirectives.TruffleBoundary
   public int getAsyncWaitersToBeResolved(JSAgentWaiterList.JSAgentWaiterListEntry wl) {
      int result = 0;

      for (JSAgentWaiterList.WaiterRecord wr : this.waitAsyncJobsQueue) {
         if (wr.getWaiterListEntry() == wl) {
            wl.enterCriticalSection();

            try {
               if (wr.isReadyToResolve()) {
                  result++;
               }
            } finally {
               wl.leaveCriticalSection();
            }
         }
      }

      return result;
   }

   public void setCanBlock(boolean canBlock) {
      this.canBlock = canBlock;
   }

   public abstract void terminate();
}
