package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import java.util.Objects;

public abstract class JSBinaryNode extends JavaScriptNode {
   @Node.Child
   @Executed
   protected JavaScriptNode leftNode;
   @Node.Child
   @Executed
   protected JavaScriptNode rightNode;

   protected JSBinaryNode(JavaScriptNode left, JavaScriptNode right) {
      this.leftNode = left;
      this.rightNode = right;
   }

   public final JavaScriptNode getLeft() {
      return this.leftNode;
   }

   public final JavaScriptNode getRight() {
      return this.rightNode;
   }

   @Override
   public String expressionToString() {
      if (this.getLeft() != null && this.getRight() != null) {
         NodeInfo annotation = this.getClass().getAnnotation(NodeInfo.class);
         if (annotation != null && !annotation.shortName().isEmpty()) {
            return "("
               + Objects.toString(this.getLeft().expressionToString(), "(intermediate value)")
               + " "
               + annotation.shortName()
               + " "
               + Objects.toString(this.getRight().expressionToString(), "(intermediate value)")
               + ")";
         }
      }

      return null;
   }

   protected static boolean largerThan2e32(double d) {
      return Math.abs(d) >= 4.2949673E9F;
   }

   protected final void ensureBothSameNumericType(Object a, Object b, BranchProfile mixedNumericTypes) {
      if (CompilerDirectives.injectBranchProbability(1.0E-4, a instanceof BigInt != (b instanceof BigInt))) {
         mixedNumericTypes.enter();
         throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
      }
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.BinaryOperationTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      NodeInfo annotation = this.getClass().getAnnotation(NodeInfo.class);
      if (annotation != null) {
         String shortName = annotation.shortName();
         if (!shortName.isEmpty()) {
            return JSTags.createNodeObjectDescriptor("operator", annotation.shortName());
         }
      }

      return null;
   }
}
