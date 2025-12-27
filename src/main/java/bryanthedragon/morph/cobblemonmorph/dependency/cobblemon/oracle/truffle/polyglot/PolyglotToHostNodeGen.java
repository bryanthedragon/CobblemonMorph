package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import java.lang.reflect.Type;
import java.util.concurrent.locks.Lock;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

@GeneratedBy(PolyglotToHostNode.class)
final class PolyglotToHostNodeGen extends PolyglotToHostNode {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private AbstractPolyglotImpl.AbstractHostLanguageService host_;
   @Node.Child
   private Node toHostNode_;

   private PolyglotToHostNodeGen() {
   }

   @Override
   Object execute(PolyglotLanguageContext arg0Value, Object arg1Value, Class<?> arg2Value, Type arg3Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         return PolyglotToHostNode.doDefault(arg0Value, arg1Value, arg2Value, arg3Value, this.host_, this.toHostNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }
   }

   private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Class<?> arg2Value, Type arg3Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var8;
      try {
         int state_0 = this.state_0_;
         this.host_ = arg0Value.context.engine.host;
         this.toHostNode_ = super.insert(PolyglotToHostNode.createToHostNode(this.host_));
         int var12;
         this.state_0_ = var12 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var8 = PolyglotToHostNode.doDefault(arg0Value, arg1Value, arg2Value, arg3Value, this.host_, this.toHostNode_);
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

   public static PolyglotToHostNode create() {
      return new PolyglotToHostNodeGen();
   }
}
