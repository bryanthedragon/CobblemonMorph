package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ExecutionEventNode;
import com.oracle.truffle.api.nodes.Node;
import java.util.Set;
import java.util.concurrent.Callable;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.EconomicSet;

abstract class DebuggerNode extends ExecutionEventNode implements InsertableNode {
   protected final EventContext context;
   private volatile boolean singleThreadSession = true;
   private volatile long cachedThreadId;
   private DebuggerSession cachedSessionDuplicate;
   private volatile EconomicMap<Thread, Object> duplicateInThreads;
   private volatile Assumption noDuplicateAssumption = Truffle.getRuntime().createAssumption("No duplicate node assumption");

   DebuggerNode(EventContext context) {
      this.context = context;
   }

   Breakpoint getBreakpoint() {
      return null;
   }

   abstract boolean isStepNode();

   abstract Set<SuspendAnchor> getSuspendAnchors();

   abstract boolean isActiveAt(SuspendAnchor anchor);

   final EventContext getContext() {
      return this.context;
   }

   @Override
   public void setParentOf(Node child) {
      this.insert(child);
   }

   @Override
   protected Object onUnwind(VirtualFrame frame, Object info) {
      return info instanceof ChangedReturnInfo ? ((ChangedReturnInfo)info).returnValue : super.onUnwind(frame, info);
   }

   void markAsDuplicate(DebuggerSession session) {
      CompilerAsserts.neverPartOfCompilation();
      this.noDuplicateAssumption.invalidate();
      if (this.singleThreadSession) {
         final long threadId = SetThreadSuspensionEnabledNode.currentThreadId();
         if (this.cachedThreadId == threadId && this.cachedSessionDuplicate == null) {
            this.cachedSessionDuplicate = session;
            return;
         }

         if (this.cachedThreadId == 0L) {
            Boolean marked = this.atomic(new Callable<Boolean>() {
               public Boolean call() {
                  if (DebuggerNode.this.cachedThreadId == 0L) {
                     DebuggerNode.this.cachedThreadId = threadId;
                     DebuggerNode.this.cachedSessionDuplicate = session;
                     return true;
                  } else {
                     return false;
                  }
               }
            });
            if (marked) {
               return;
            }
         }
      }

      this.singleThreadSession = false;
      this.markAsDuplicateSlowPath(session);
   }

   private void markAsDuplicateSlowPath(DebuggerSession session) {
      this.atomic(new Runnable() {
         @Override
         public void run() {
            if (DebuggerNode.this.duplicateInThreads == null) {
               DebuggerNode.this.duplicateInThreads = EconomicMap.create();
            }

            Thread thread = Thread.currentThread();
            Object sessions = DebuggerNode.this.duplicateInThreads.get(thread);
            if (sessions == null) {
               DebuggerNode.this.duplicateInThreads.put(thread, session);
            } else if (sessions instanceof DebuggerSession) {
               EconomicSet<DebuggerSession> set = EconomicSet.create();
               set.add((DebuggerSession)sessions);
               set.add(session);
               DebuggerNode.this.duplicateInThreads.put(thread, set);
            } else {
               ((EconomicSet)sessions).add(session);
            }
         }
      });
   }

   boolean consumeIsDuplicate(DebuggerSession session) {
      if (this.noDuplicateAssumption.isValid()) {
         return false;
      } else if (this.cachedThreadId == SetThreadSuspensionEnabledNode.currentThreadId() && this.cachedSessionDuplicate == session) {
         this.cachedSessionDuplicate = null;
         if (this.singleThreadSession) {
            this.noDuplicateAssumption = Truffle.getRuntime().createAssumption("No duplicate node assumption");
         }

         return true;
      } else {
         return this.singleThreadSession ? false : this.isDuplicateSlowPath(session);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private boolean isDuplicateSlowPath(DebuggerSession session) {
      return this.atomic(new Callable<Boolean>() {
         public Boolean call() {
            if (DebuggerNode.this.duplicateInThreads != null) {
               try {
                  Thread thread = Thread.currentThread();
                  Object sessions = DebuggerNode.this.duplicateInThreads.get(thread);
                  if (sessions == session) {
                     DebuggerNode.this.duplicateInThreads.removeKey(thread);
                     return true;
                  }

                  if (sessions instanceof EconomicSet) {
                     EconomicSet<DebuggerSession> set = (EconomicSet<DebuggerSession>)sessions;
                     boolean contains = set.contains(session);
                     if (contains) {
                        set.remove(session);
                        if (set.isEmpty()) {
                           DebuggerNode.this.duplicateInThreads.removeKey(thread);
                        }
                     }

                     return contains;
                  }
               } finally {
                  if (DebuggerNode.this.duplicateInThreads.isEmpty()) {
                     DebuggerNode.this.duplicateInThreads = null;
                     DebuggerNode.this.singleThreadSession = true;
                     if (DebuggerNode.this.cachedSessionDuplicate == null) {
                        DebuggerNode.this.cachedThreadId = 0L;
                        DebuggerNode.this.noDuplicateAssumption = Truffle.getRuntime().createAssumption("No duplicate node assumption");
                     }
                  }
               }
            }

            return false;
         }
      });
   }

   interface InputValuesProvider {
      Object[] getDebugInputValues(MaterializedFrame frame);
   }
}
