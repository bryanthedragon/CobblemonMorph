package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;

@GeneratedBy(IteratorIsDoneNode.class)
public final class IteratorIsDoneNodeGen extends IteratorIsDoneNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private IteratorIsDoneNodeGen(JavaScriptNode iteratorNode) {
      super(iteratorNode);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object iteratorNodeValue_ = super.iteratorNode.execute(frameValue);
      if (state_0 != 0 && iteratorNodeValue_ instanceof IteratorRecord) {
         IteratorRecord iteratorNodeValue__ = (IteratorRecord)iteratorNodeValue_;
         return IteratorIsDoneNode.doIterator(iteratorNodeValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(iteratorNodeValue_);
      }
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object iteratorNodeValue_ = super.iteratorNode.execute(frameValue);
      if (state_0 != 0 && iteratorNodeValue_ instanceof IteratorRecord) {
         IteratorRecord iteratorNodeValue__ = (IteratorRecord)iteratorNodeValue_;
         return IteratorIsDoneNode.doIterator(iteratorNodeValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(iteratorNodeValue_);
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.executeBoolean(frameValue);
   }

   private boolean executeAndSpecialize(Object iteratorNodeValue) {
      int state_0 = this.state_0_;
      if (iteratorNodeValue instanceof IteratorRecord) {
         IteratorRecord iteratorNodeValue_ = (IteratorRecord)iteratorNodeValue;
         int var4;
         this.state_0_ = var4 = state_0 | 1;
         return IteratorIsDoneNode.doIterator(iteratorNodeValue_);
      } else {
         throw new UnsupportedSpecializationException(this, new Node[]{super.iteratorNode}, iteratorNodeValue);
      }
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
      Object[] s = new Object[]{"doIterator", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static IteratorIsDoneNode create(JavaScriptNode iteratorNode) {
      return new IteratorIsDoneNodeGen(iteratorNode);
   }
}
