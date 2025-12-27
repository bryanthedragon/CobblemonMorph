package com.oracle.truffle.js.nodes.module;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSModuleNamespaceObject;
import com.oracle.truffle.js.runtime.objects.ExportResolution;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ReadImportBindingNode.class)
public final class ReadImportBindingNodeGen extends ReadImportBindingNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private ReadImportBindingNodeGen.CachedData cached_cache;

   private ReadImportBindingNodeGen(JavaScriptNode resolutionNode) {
      super(resolutionNode);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object resolutionNodeValue_ = super.resolutionNode.execute(frameValue);
      if ((state_0 & 3) != 0 && resolutionNodeValue_ instanceof ExportResolution.Resolved) {
         ExportResolution.Resolved resolutionNodeValue__ = (ExportResolution.Resolved)resolutionNodeValue_;
         if ((state_0 & 1) != 0) {
            ReadImportBindingNodeGen.CachedData s0_ = this.cached_cache;
            if (s0_ != null
               && s0_.frameDescriptor_ == resolutionNodeValue__.getModule().getFrameDescriptor()
               && Strings.equals(s0_.equalNode_, s0_.bindingName_, resolutionNodeValue__.getBindingName())) {
               return ReadImportBindingNode.doCached(resolutionNodeValue__, s0_.frameDescriptor_, s0_.bindingName_, s0_.readFrameSlot_, s0_.equalNode_);
            }
         }

         if ((state_0 & 2) != 0) {
            return this.doUncached(resolutionNodeValue__);
         }
      }

      if ((state_0 & 4) != 0 && resolutionNodeValue_ instanceof JSModuleNamespaceObject) {
         JSModuleNamespaceObject resolutionNodeValue__x = (JSModuleNamespaceObject)resolutionNodeValue_;
         return ReadImportBindingNode.doNamespace(resolutionNodeValue__x);
      } else if ((state_0 & 8) != 0 && fallbackGuard_(state_0, resolutionNodeValue_)) {
         return ReadImportBindingNode.doUnresolved(resolutionNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(resolutionNodeValue_);
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.execute(frameValue);
   }

   private Object executeAndSpecialize(Object resolutionNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (resolutionNodeValue instanceof ExportResolution.Resolved) {
            ExportResolution.Resolved resolutionNodeValue_ = (ExportResolution.Resolved)resolutionNodeValue;
            if (exclude == 0) {
               ReadImportBindingNodeGen.CachedData s0_ = this.cached_cache;
               boolean Cached_duplicateFound_ = false;
               if ((state_0 & 1) != 0
                  && s0_.frameDescriptor_ == resolutionNodeValue_.getModule().getFrameDescriptor()
                  && Strings.equals(s0_.equalNode_, s0_.bindingName_, resolutionNodeValue_.getBindingName())) {
                  Cached_duplicateFound_ = true;
               }

               if (!Cached_duplicateFound_) {
                  FrameDescriptor frameDescriptor__ = resolutionNodeValue_.getModule().getFrameDescriptor();
                  if (frameDescriptor__ == resolutionNodeValue_.getModule().getFrameDescriptor()) {
                     TruffleString bindingName__ = resolutionNodeValue_.getBindingName();
                     TruffleString.EqualNode equalNode__ = super.insert(TruffleString.EqualNode.create());
                     if (Strings.equals(equalNode__, bindingName__, resolutionNodeValue_.getBindingName()) && (state_0 & 1) == 0) {
                        s0_ = super.insert(new ReadImportBindingNodeGen.CachedData());
                        s0_.frameDescriptor_ = frameDescriptor__;
                        s0_.bindingName_ = bindingName__;
                        s0_.readFrameSlot_ = s0_.insertAccessor(
                           JSReadFrameSlotNode.create(
                              frameDescriptor__, ReadImportBindingNode.findImportedSlotIndex(bindingName__, resolutionNodeValue_.getModule())
                           )
                        );
                        s0_.equalNode_ = s0_.insertAccessor(equalNode__);
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                        Cached_duplicateFound_ = true;
                     }
                  }
               }

               if (Cached_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return ReadImportBindingNode.doCached(resolutionNodeValue_, s0_.frameDescriptor_, s0_.bindingName_, s0_.readFrameSlot_, s0_.equalNode_);
               }
            }

            int var19;
            this.exclude_ = var19 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var16;
            this.state_0_ = var16 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doUncached(resolutionNodeValue_);
         } else if (!(resolutionNodeValue instanceof JSModuleNamespaceObject)) {
            int var18;
            this.state_0_ = var18 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return ReadImportBindingNode.doUnresolved(resolutionNodeValue);
         } else {
            JSModuleNamespaceObject resolutionNodeValue_ = (JSModuleNamespaceObject)resolutionNodeValue;
            int var17;
            this.state_0_ = var17 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return ReadImportBindingNode.doNamespace(resolutionNodeValue_);
         }
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[5];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doCached", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         ReadImportBindingNodeGen.CachedData s0_ = this.cached_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.frameDescriptor_, s0_.bindingName_, s0_.readFrameSlot_, s0_.equalNode_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doUncached", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doNamespace", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doUnresolved", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(int state_0, Object resolutionNodeValue) {
      return (state_0 & 2) == 0 && resolutionNodeValue instanceof ExportResolution.Resolved
         ? false
         : (state_0 & 4) != 0 || !(resolutionNodeValue instanceof JSModuleNamespaceObject);
   }

   public static ReadImportBindingNode create(JavaScriptNode resolutionNode) {
      return new ReadImportBindingNodeGen(resolutionNode);
   }

   @GeneratedBy(ReadImportBindingNode.class)
   private static final class CachedData extends Node {
      @CompilerDirectives.CompilationFinal
      FrameDescriptor frameDescriptor_;
      @CompilerDirectives.CompilationFinal
      TruffleString bindingName_;
      @Node.Child
      JSReadFrameSlotNode readFrameSlot_;
      @Node.Child
      TruffleString.EqualNode equalNode_;

      CachedData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }
}
