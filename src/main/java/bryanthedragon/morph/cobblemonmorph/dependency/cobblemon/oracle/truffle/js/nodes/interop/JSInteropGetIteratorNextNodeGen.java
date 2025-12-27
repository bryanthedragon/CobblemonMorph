package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNodeGen;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSInteropGetIteratorNextNode.class)
public final class JSInteropGetIteratorNextNodeGen extends JSInteropGetIteratorNextNode implements Introspection.Provider {
   private static final JSInteropGetIteratorNextNodeGen.Uncached UNCACHED = new JSInteropGetIteratorNextNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSInteropGetIteratorNextNodeGen.DefaultData default_cache;

   private JSInteropGetIteratorNextNodeGen() {
   }

   @Override
   protected Object execute(IteratorRecord arg0Value, JavaScriptLanguage arg1Value, Object arg2Value) throws StopIterationException {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         JSInteropGetIteratorNextNodeGen.DefaultData s0_ = this.default_cache;
         if (s0_ != null) {
            return this.doDefault(
               arg0Value,
               arg1Value,
               arg2Value,
               s0_.callNode_,
               s0_.donePropertyGetNode_,
               s0_.valuePropertyGetNode_,
               s0_.toBooleanNode_,
               s0_.exportValueNode_,
               s0_.exceptionBranch_
            );
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
   }

   private Object executeAndSpecialize(IteratorRecord arg0Value, JavaScriptLanguage arg1Value, Object arg2Value) throws StopIterationException {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var8;
      try {
         int state_0 = this.state_0_;
         JSInteropGetIteratorNextNodeGen.DefaultData s0_ = super.insert(new JSInteropGetIteratorNextNodeGen.DefaultData());
         s0_.callNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
         s0_.donePropertyGetNode_ = s0_.insertAccessor(PropertyGetNode.create(JSRuntime.DONE, arg1Value.getJSContext()));
         s0_.valuePropertyGetNode_ = s0_.insertAccessor(PropertyGetNode.create(JSRuntime.VALUE, arg1Value.getJSContext()));
         s0_.toBooleanNode_ = s0_.insertAccessor(JSToBooleanNode.create());
         s0_.exportValueNode_ = s0_.insertAccessor(ExportValueNode.create());
         s0_.exceptionBranch_ = BranchProfile.create();
         VarHandle.storeStoreFence();
         this.default_cache = s0_;
         int var12;
         this.state_0_ = var12 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var8 = this.doDefault(
            arg0Value,
            arg1Value,
            arg2Value,
            s0_.callNode_,
            s0_.donePropertyGetNode_,
            s0_.valuePropertyGetNode_,
            s0_.toBooleanNode_,
            s0_.exportValueNode_,
            s0_.exceptionBranch_
         );
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var8;
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doDefault", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSInteropGetIteratorNextNodeGen.DefaultData s0_ = this.default_cache;
         if (s0_ != null) {
            cached.add(
               Arrays.asList(s0_.callNode_, s0_.donePropertyGetNode_, s0_.valuePropertyGetNode_, s0_.toBooleanNode_, s0_.exportValueNode_, s0_.exceptionBranch_)
            );
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static JSInteropGetIteratorNextNode create() {
      return new JSInteropGetIteratorNextNodeGen();
   }

   public static JSInteropGetIteratorNextNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(JSInteropGetIteratorNextNode.class)
   private static final class DefaultData extends Node {
      @Node.Child
      JSFunctionCallNode callNode_;
      @Node.Child
      PropertyGetNode donePropertyGetNode_;
      @Node.Child
      PropertyGetNode valuePropertyGetNode_;
      @Node.Child
      JSToBooleanNode toBooleanNode_;
      @Node.Child
      ExportValueNode exportValueNode_;
      @CompilerDirectives.CompilationFinal
      BranchProfile exceptionBranch_;

      DefaultData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(JSInteropGetIteratorNextNode.class)
   @DenyReplace
   private static final class Uncached extends JSInteropGetIteratorNextNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      protected Object execute(IteratorRecord arg0Value, JavaScriptLanguage arg1Value, Object arg2Value) throws StopIterationException {
         return this.doDefault(
            arg0Value,
            arg1Value,
            arg2Value,
            JSFunctionCallNode.getUncachedCall(),
            JSInteropCallNode.getUncachedProperty(),
            JSInteropCallNode.getUncachedProperty(),
            JSToBooleanNodeGen.getUncached(),
            ExportValueNode.getUncached(),
            BranchProfile.getUncached()
         );
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @Override
      public boolean isAdoptable() {
         return false;
      }
   }
}
