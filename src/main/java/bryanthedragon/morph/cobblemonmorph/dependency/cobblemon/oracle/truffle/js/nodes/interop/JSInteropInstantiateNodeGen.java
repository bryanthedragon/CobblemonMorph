package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsConstructorNode;
import com.oracle.truffle.js.nodes.unary.IsConstructorNodeGen;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSInteropInstantiateNode.class)
public final class JSInteropInstantiateNodeGen extends JSInteropInstantiateNode implements Introspection.Provider {
   private static final JSInteropInstantiateNodeGen.Uncached UNCACHED = new JSInteropInstantiateNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSInteropInstantiateNodeGen.DefaultData default_cache;

   private JSInteropInstantiateNodeGen() {
   }

   @Override
   public Object execute(JSDynamicObject arg0Value, Object[] arg1Value) throws UnsupportedMessageException {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         JSInteropInstantiateNodeGen.DefaultData s0_ = this.default_cache;
         if (s0_ != null) {
            return this.doDefault(arg0Value, arg1Value, s0_.isConstructorNode_, s0_.callNode_, s0_.importValueNode_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private Object executeAndSpecialize(JSDynamicObject arg0Value, Object[] arg1Value) throws UnsupportedMessageException {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var7;
      try {
         int state_0 = this.state_0_;
         JSInteropInstantiateNodeGen.DefaultData s0_ = super.insert(new JSInteropInstantiateNodeGen.DefaultData());
         s0_.isConstructorNode_ = s0_.insertAccessor(IsConstructorNode.create());
         s0_.callNode_ = s0_.insertAccessor(JSFunctionCallNode.createNew());
         s0_.importValueNode_ = s0_.insertAccessor(ImportValueNode.create());
         VarHandle.storeStoreFence();
         this.default_cache = s0_;
         int var11;
         this.state_0_ = var11 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var7 = this.doDefault(arg0Value, arg1Value, s0_.isConstructorNode_, s0_.callNode_, s0_.importValueNode_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var7;
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
         JSInteropInstantiateNodeGen.DefaultData s0_ = this.default_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.isConstructorNode_, s0_.callNode_, s0_.importValueNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static JSInteropInstantiateNode create() {
      return new JSInteropInstantiateNodeGen();
   }

   public static JSInteropInstantiateNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(JSInteropInstantiateNode.class)
   private static final class DefaultData extends Node {
      @Node.Child
      IsConstructorNode isConstructorNode_;
      @Node.Child
      JSFunctionCallNode callNode_;
      @Node.Child
      ImportValueNode importValueNode_;

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

   @GeneratedBy(JSInteropInstantiateNode.class)
   @DenyReplace
   private static final class Uncached extends JSInteropInstantiateNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public Object execute(JSDynamicObject arg0Value, Object[] arg1Value) throws UnsupportedMessageException {
         return this.doDefault(arg0Value, arg1Value, IsConstructorNodeGen.getUncached(), JSFunctionCallNode.getUncachedNew(), ImportValueNode.getUncached());
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
