package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSAgent;
import com.oracle.truffle.js.runtime.JSInterruptedExecutionException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.PromiseRejectionTracker;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.Null;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DebugJSAgent extends JSAgent {
   private final Deque<Object> reportValues = new ConcurrentLinkedDeque<>();
   private final List<DebugJSAgent.AgentExecutor> spawnedAgents = new LinkedList<>();
   private boolean quit;
   private Object debugReceiveBroadcast;
   private final Lock queueLock = new ReentrantLock();
   private final Condition queueCondition = this.queueLock.newCondition();
   static final int POLL_TIMEOUT_MS = 100;

   public DebugJSAgent(PromiseRejectionTracker promiseRejectionTracker, boolean canBlock) {
      super(promiseRejectionTracker, canBlock);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return "DebugJSAgent{signifier=" + this.getSignifier() + "}";
   }

   @CompilerDirectives.TruffleBoundary
   public void startNewAgent(String sourceText) {
      final Source agentSource = Source.newBuilder("js", sourceText, "agent").build();
      TruffleLanguage.Env env = JavaScriptLanguage.getCurrentEnv();
      final TruffleContext agentContext = env.newContextBuilder().build();
      final CountDownLatch barrier = new CountDownLatch(1);
      Thread thread = env.createThread(new Runnable() {
         @Override
         public void run() {
            JSRealm innerContext = JavaScriptLanguage.getCurrentJSRealm();
            DebugJSAgent childAgent = (DebugJSAgent)innerContext.getAgent();
            DebugJSAgent parentAgent = DebugJSAgent.this;
            DebugJSAgent.AgentExecutor executor = parentAgent.registerChildAgent(Thread.currentThread(), childAgent, agentContext);
            CallTarget callTarget = innerContext.getEnv().parsePublic(agentSource);
            callTarget.call();
            barrier.countDown();

            try {
               while (true) {
                  childAgent.queueLock.lock();

                  try {
                     if (childAgent.quit) {
                        return;
                     }

                     childAgent.queueCondition.await(100L, TimeUnit.MILLISECONDS);
                  } finally {
                     childAgent.queueLock.unlock();
                  }

                  do {
                     Object next = executor.broadcasts.poll();
                     if (next != null) {
                        executor.executeBroadcastCallback(next);
                        if (childAgent.quit) {
                           return;
                        }
                     }

                     executor.processPromises();
                  } while (!executor.broadcasts.isEmpty());
               }
            } catch (InterruptedException var11) {
               System.err.println("Interrupted " + Thread.currentThread());
            } catch (AbstractTruffleException var12) {
               System.err.println("Uncaught error from " + Thread.currentThread() + ": " + var12.getMessage());
            }
         }
      }, agentContext);
      thread.setName("Debug-JSAgent-Worker-Thread");
      thread.start();

      try {
         barrier.await();
      } catch (InterruptedException var8) {
         throw JSInterruptedExecutionException.wrap(var8);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void setDebugReceiveBroadcast(Object lambda) {
      this.debugReceiveBroadcast = lambda;
   }

   @CompilerDirectives.TruffleBoundary
   public DebugJSAgent.AgentExecutor registerChildAgent(Thread thread, DebugJSAgent jsAgent, TruffleContext agentContext) {
      DebugJSAgent.AgentExecutor spawned = new DebugJSAgent.AgentExecutor(thread, jsAgent, agentContext);
      this.spawnedAgents.add(spawned);
      return spawned;
   }

   @CompilerDirectives.TruffleBoundary
   public void broadcast(Object sab) {
      for (DebugJSAgent.AgentExecutor e : this.spawnedAgents) {
         e.pushMessage(sab);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public Object getReport() {
      for (DebugJSAgent.AgentExecutor e : this.spawnedAgents) {
         if (e.jsAgent.reportValues.size() > 0) {
            return e.jsAgent.reportValues.pollLast();
         }
      }

      return Null.instance;
   }

   @CompilerDirectives.TruffleBoundary
   public void sleep(int time) {
      try {
         Thread.sleep(time);
      } catch (InterruptedException var3) {
         throw JSInterruptedExecutionException.wrap(var3);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void report(Object value) {
      this.reportValues.push(value);
   }

   @CompilerDirectives.TruffleBoundary
   public void leaving() {
      this.quit = true;
   }

   @Override
   public void wake() {
      CompilerAsserts.neverPartOfCompilation();
      this.queueLock.lock();

      try {
         this.queueCondition.signalAll();
      } finally {
         this.queueLock.unlock();
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void terminate() {
      if (!this.spawnedAgents.isEmpty()) {
         boolean alive = false;

         for (DebugJSAgent.AgentExecutor executor : this.spawnedAgents) {
            if (executor.thread.isAlive()) {
               alive = true;
               executor.thread.interrupt();
            }
         }

         if (alive) {
            for (DebugJSAgent.AgentExecutor executorx : this.spawnedAgents) {
               if (executorx.thread.isAlive()) {
                  try {
                     executorx.thread.join();
                  } catch (InterruptedException var5) {
                     Thread.currentThread().interrupt();
                     break;
                  }
               }
            }
         }

         this.spawnedAgents.clear();
      }
   }

   private static final class AgentExecutor {
      private final DebugJSAgent jsAgent;
      private final TruffleContext agentContext;
      private final Thread thread;
      final Queue<Object> broadcasts;

      AgentExecutor(Thread thread, DebugJSAgent jsAgent, TruffleContext agentContext) {
         CompilerAsserts.neverPartOfCompilation();
         this.thread = thread;
         this.jsAgent = jsAgent;
         this.agentContext = agentContext;
         this.broadcasts = new ConcurrentLinkedQueue<>();
      }

      void pushMessage(Object sab) {
         CompilerAsserts.neverPartOfCompilation();
         this.broadcasts.add(sab);
         this.jsAgent.wake();
      }

      void executeBroadcastCallback(Object sab) {
         CompilerAsserts.neverPartOfCompilation();

         assert this.agentContext.isEntered();

         JSFunctionObject cb = (JSFunctionObject)this.jsAgent.debugReceiveBroadcast;
         JSFunction.call(cb, cb, new Object[]{sab});
      }

      void processPromises() {
         CompilerAsserts.neverPartOfCompilation();

         assert this.agentContext.isEntered();

         this.jsAgent.processAllPromises(false);
      }
   }
}
