package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ThreadLocalAction;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.frame.FrameInstanceVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

final class PolyglotStackFramesRetriever {
   static FrameInstance[][] getStackFrames(PolyglotContextImpl context) {
      final Map<Thread, List<FrameInstance>> frameInstancesByThread = new ConcurrentHashMap<>();
      Thread[] threads;
      Future<Void> future;
      synchronized (context) {
         threads = context.getSeenThreads().keySet().toArray(new Thread[0]);
         if (!context.state.isClosed()) {
            future = context.threadLocalActions.submit(null, "engine", new ThreadLocalAction(false, false) {
               @Override
               protected void perform(ThreadLocalAction.Access access) {
                  final List<FrameInstance> frameInstances = new ArrayList<>();
                  Truffle.getRuntime().iterateFrames(new FrameInstanceVisitor<Object>() {
                     @Override
                     public Object visitFrame(FrameInstance frameInstance) {
                        return frameInstances.add(frameInstance);
                     }
                  });
                  frameInstancesByThread.put(access.getThread(), frameInstances);
               }
            }, false);
         } else {
            future = CompletableFuture.completedFuture(null);
         }
      }

      TruffleSafepoint.setBlockedThreadInterruptible(context.uncachedLocation, new TruffleSafepoint.Interruptible<Future<Void>>() {
         public void apply(Future<Void> arg) throws InterruptedException {
            try {
               arg.get();
            } catch (ExecutionException var3) {
               throw CompilerDirectives.shouldNotReachHere(var3);
            }
         }
      }, future);
      FrameInstance[][] toRet = new FrameInstance[threads.length][];

      for (int i = 0; i < threads.length; i++) {
         Thread thread = threads[i];
         List<FrameInstance> frameInstances = frameInstancesByThread.get(thread);
         if (frameInstances != null) {
            toRet[i] = frameInstances.toArray(new FrameInstance[0]);
         } else {
            toRet[i] = new FrameInstance[0];
         }
      }

      return toRet;
   }
}
