package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.runtime.JSContext;

public class BuiltinArgumentBuilder {
   private int fixedArgumentCount;
   private boolean hasThis;
   private boolean varArgs;
   private boolean newTarget;
   private boolean hasFunction;
   public static final JavaScriptNode[] EMPTY_NODE_ARRAY = new JavaScriptNode[0];

   BuiltinArgumentBuilder() {
   }

   public static BuiltinArgumentBuilder builder() {
      boolean ea = false;
      if (!$assertionsDisabled) {
         ea = true;
         if (false) {
            throw new AssertionError();
         }
      }

      return ea ? new BuiltinArgumentBuilder() {
         private int order;

         @Override
         protected void assertOrder(int order) {
            assert this.order < order;

            this.order = order;
         }
      } : new BuiltinArgumentBuilder();
   }

   public BuiltinArgumentBuilder withThis() {
      return this.withThis(true);
   }

   public BuiltinArgumentBuilder withThis(boolean hasThis) {
      this.hasThis = hasThis;
      this.assertOrder(1);
      return this;
   }

   public BuiltinArgumentBuilder function() {
      return this.function(true);
   }

   public BuiltinArgumentBuilder function(boolean function) {
      this.hasFunction = function;
      this.assertOrder(2);
      return this;
   }

   public BuiltinArgumentBuilder newTarget() {
      return this.newTarget(true);
   }

   public BuiltinArgumentBuilder newTarget(boolean newTarget) {
      this.newTarget = newTarget;
      this.assertOrder(3);
      return this;
   }

   public BuiltinArgumentBuilder fixedArgs(int fixedArgumentCount) {
      this.fixedArgumentCount = fixedArgumentCount;
      this.assertOrder(4);
      return this;
   }

   public BuiltinArgumentBuilder varArgs() {
      return this.varArgs(true);
   }

   public BuiltinArgumentBuilder varArgs(boolean varArgs) {
      this.varArgs = varArgs;
      this.assertOrder(5);
      return this;
   }

   public JavaScriptNode[] createArgumentNodes(JSContext context) {
      NodeFactory factory = NodeFactory.getInstance(context);
      int totalArgs = this.getTotalArgumentCount();
      JavaScriptNode[] callArgs = totalArgs == 0 ? EMPTY_NODE_ARRAY : new JavaScriptNode[totalArgs];
      int index = 0;
      if (this.hasThis) {
         callArgs[index++] = factory.createAccessThis();
      }

      if (this.hasFunction) {
         callArgs[index++] = factory.createAccessCallee(0);
      }

      int argIndex = 0;
      int size = this.varArgs ? totalArgs - 1 : totalArgs;

      for (int i = index; i < size; i++) {
         callArgs[i] = factory.createAccessArgument(argIndex++);
      }

      if (this.varArgs) {
         callArgs[size] = factory.createAccessVarArgs(argIndex++);
      }

      return callArgs;
   }

   private int getTotalArgumentCount() {
      return (this.hasThis ? 1 : 0) + (this.hasFunction ? 1 : 0) + (this.newTarget ? 1 : 0) + this.fixedArgumentCount + (this.varArgs ? 1 : 0);
   }

   protected void assertOrder(int order) {
   }
}
