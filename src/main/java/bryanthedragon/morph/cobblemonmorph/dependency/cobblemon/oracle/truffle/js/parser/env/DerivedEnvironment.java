package com.oracle.truffle.js.parser.env;

import com.oracle.truffle.js.nodes.JSFrameDescriptor;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class DerivedEnvironment extends Environment {
   private final Environment blockEnvironment;

   protected DerivedEnvironment(Environment parent, NodeFactory factory, JSContext context) {
      super(parent, factory, context);
      if (!(parent instanceof FunctionEnvironment) && !(parent instanceof BlockEnvironment)) {
         if (!(parent instanceof DerivedEnvironment)) {
            throw new IllegalArgumentException();
         }

         this.blockEnvironment = ((DerivedEnvironment)parent).blockEnvironment;
      } else {
         this.blockEnvironment = parent;
      }
   }

   @Override
   public final JSFrameDescriptor getBlockFrameDescriptor() {
      return this.block().getBlockFrameDescriptor();
   }

   @Override
   public final int getScopeLevel() {
      return this.block().getScopeLevel();
   }

   @Override
   public final JSFrameSlot getCurrentBlockScopeSlot() {
      return this.block().getCurrentBlockScopeSlot();
   }

   @Override
   public final JSFrameSlot declareInternalSlot(Object name) {
      return this.block().declareInternalSlot(name);
   }

   private Environment block() {
      return this.blockEnvironment;
   }
}
