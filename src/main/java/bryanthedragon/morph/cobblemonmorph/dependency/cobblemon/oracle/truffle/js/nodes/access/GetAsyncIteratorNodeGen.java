package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(GetAsyncIteratorNode.class)
public final class GetAsyncIteratorNodeGen extends GetAsyncIteratorNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private GetAsyncIteratorNodeGen.GetIteratorData getIterator_cache;

   private GetAsyncIteratorNodeGen(JSContext context, JavaScriptNode objectNode) {
      super(context, objectNode);
   }

   @Override
   public IteratorRecord execute(Object objectNodeValue) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         GetAsyncIteratorNodeGen.GetIteratorData s0_ = this.getIterator_cache;
         if (s0_ != null) {
            return this.doGetIterator(objectNodeValue, s0_.isCallableNode_, s0_.methodCallNode_, s0_.isObjectNode_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(objectNodeValue);
   }

   @Override
   public IteratorRecord execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object objectNodeValue_ = super.objectNode.execute(frameValue);
      if (state_0 != 0) {
         GetAsyncIteratorNodeGen.GetIteratorData s0_ = this.getIterator_cache;
         if (s0_ != null) {
            return this.doGetIterator(objectNodeValue_, s0_.isCallableNode_, s0_.methodCallNode_, s0_.isObjectNode_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(objectNodeValue_);
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.execute(frameValue);
   }

   private IteratorRecord executeAndSpecialize(Object objectNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      IteratorRecord var6;
      try {
         int state_0 = this.state_0_;
         GetAsyncIteratorNodeGen.GetIteratorData s0_ = super.insert(new GetAsyncIteratorNodeGen.GetIteratorData());
         s0_.isCallableNode_ = s0_.insertAccessor(IsCallableNode.create());
         s0_.methodCallNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
         s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
         VarHandle.storeStoreFence();
         this.getIterator_cache = s0_;
         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var6 = this.doGetIterator(objectNodeValue, s0_.isCallableNode_, s0_.methodCallNode_, s0_.isObjectNode_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var6;
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
      Object[] s = new Object[]{"doGetIterator", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         GetAsyncIteratorNodeGen.GetIteratorData s0_ = this.getIterator_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.isCallableNode_, s0_.methodCallNode_, s0_.isObjectNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static GetAsyncIteratorNode create(JSContext context, JavaScriptNode objectNode) {
      return new GetAsyncIteratorNodeGen(context, objectNode);
   }

   @GeneratedBy(GetAsyncIteratorNode.class)
   private static final class GetIteratorData extends Node {
      @Node.Child
      IsCallableNode isCallableNode_;
      @Node.Child
      JSFunctionCallNode methodCallNode_;
      @Node.Child
      IsJSObjectNode isObjectNode_;

      GetIteratorData() {
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
