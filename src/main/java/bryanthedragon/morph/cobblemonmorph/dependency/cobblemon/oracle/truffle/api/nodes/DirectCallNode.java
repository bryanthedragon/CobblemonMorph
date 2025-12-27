package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;

public abstract class DirectCallNode extends Node {
   protected final CallTarget callTarget;

   protected DirectCallNode(CallTarget callTarget) {
      this.callTarget = callTarget;
   }

   public abstract Object call(Object... arguments);

   public CallTarget getCallTarget() {
      return this.callTarget;
   }

   public abstract boolean isInlinable();

   public abstract boolean isInliningForced();

   public abstract void forceInlining();

   public abstract boolean isCallTargetCloningAllowed();

   public abstract boolean cloneCallTarget();

   public final boolean isCallTargetCloned() {
      return this.getClonedCallTarget() != null;
   }

   public abstract CallTarget getClonedCallTarget();

   public CallTarget getCurrentCallTarget() {
      CallTarget split = this.getClonedCallTarget();
      return split != null ? split : this.getCallTarget();
   }

   public final RootNode getCurrentRootNode() {
      CallTarget target = this.getCurrentCallTarget();
      return target instanceof RootCallTarget ? ((RootCallTarget)target).getRootNode() : null;
   }

   @Override
   public String toString() {
      return String.format("%s(target=%s)", this.getClass().getSimpleName(), this.getCurrentCallTarget());
   }

   public static DirectCallNode create(CallTarget target) {
      return Truffle.getRuntime().createDirectCallNode(target);
   }
}
