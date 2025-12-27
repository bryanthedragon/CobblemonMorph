package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;

public final class DefaultCallTarget implements RootCallTarget {
   public static final String CALL_BOUNDARY_METHOD = "callDirectOrIndirect";
   private final RootNode rootNode;
   private volatile boolean initialized;
   private volatile boolean loaded;

   DefaultCallTarget(RootNode function) {
      this.rootNode = function;
      this.rootNode.adoptChildren();
   }

   @Override
   public String toString() {
      return this.rootNode.toString();
   }

   @Override
   public RootNode getRootNode() {
      return this.rootNode;
   }

   Object callDirectOrIndirect(final Node callNode, Object... args) {
      if (!this.initialized) {
         this.initialize();
      }

      VirtualFrame frame = new FrameWithoutBoxing(this.rootNode.getFrameDescriptor(), args);
      DefaultTruffleRuntime.DefaultFrameInstance callerFrame = DefaultTruffleRuntime.getRuntime().pushFrame(frame, this, callNode);

      Object var6;
      try {
         Object toRet = this.rootNode.execute(frame);
         TruffleSafepoint.poll(this.rootNode);
         var6 = toRet;
      } catch (Throwable var10) {
         DefaultRuntimeAccessor.LANGUAGE.onThrowable(callNode, this, var10, frame);
         throw var10;
      } finally {
         DefaultTruffleRuntime.getRuntime().popFrame(callerFrame);
      }

      return var6;
   }

   @Override
   public Object call(Object... args) {
      EncapsulatingNodeReference encapsulating = EncapsulatingNodeReference.getCurrent();
      Node parent = encapsulating.set(null);

      Object var4;
      try {
         var4 = this.callDirectOrIndirect(parent, args);
      } finally {
         encapsulating.set(parent);
      }

      return var4;
   }

   private void initialize() {
      synchronized (this) {
         if (!this.initialized) {
            DefaultRuntimeAccessor.INSTRUMENT.onFirstExecution(this.getRootNode(), true);
            this.initialized = true;
         }
      }
   }

   boolean isLoaded() {
      return this.loaded;
   }

   void setLoaded() {
      this.loaded = true;
   }
}
