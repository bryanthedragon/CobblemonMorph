package com.oracle.truffle.js.nodes.instrumentation;

import com.oracle.truffle.api.instrumentation.Tag;

public final class JSTags {
   public static final Class<?>[] ALL = new Class[]{
      JSTags.ObjectAllocationTag.class,
      JSTags.BinaryOperationTag.class,
      JSTags.UnaryOperationTag.class,
      JSTags.WriteVariableTag.class,
      JSTags.ReadElementTag.class,
      JSTags.WriteElementTag.class,
      JSTags.ReadPropertyTag.class,
      JSTags.WritePropertyTag.class,
      JSTags.ReadVariableTag.class,
      JSTags.LiteralTag.class,
      JSTags.FunctionCallTag.class,
      JSTags.BuiltinRootTag.class,
      JSTags.EvalCallTag.class,
      JSTags.ControlFlowRootTag.class,
      JSTags.ControlFlowBlockTag.class,
      JSTags.ControlFlowBranchTag.class,
      JSTags.DeclareTag.class
   };

   private JSTags() {
   }

   public static NodeObjectDescriptor createNodeObjectDescriptor() {
      return new NodeObjectDescriptor();
   }

   public static NodeObjectDescriptor createNodeObjectDescriptor(String name, Object value) {
      NodeObjectDescriptor desc = new NodeObjectDescriptor();
      desc.addProperty(name, value);
      return desc;
   }

   @Tag.Identifier("BinaryOperation")
   public static final class BinaryOperationTag extends Tag {
      private BinaryOperationTag() {
      }
   }

   @Tag.Identifier("BuiltinRoot")
   public static final class BuiltinRootTag extends Tag {
      private BuiltinRootTag() {
      }
   }

   @Tag.Identifier("ControlFlowBlockTag")
   public static final class ControlFlowBlockTag extends Tag {
      private ControlFlowBlockTag() {
      }
   }

   @Tag.Identifier("ControlFlowBranchTag")
   public static final class ControlFlowBranchTag extends Tag {
      private ControlFlowBranchTag() {
      }

      public static enum Type {
         Condition,
         Continue,
         Break,
         Throw,
         Return,
         Await;
      }
   }

   @Tag.Identifier("ControlFlowRootTag")
   public static final class ControlFlowRootTag extends Tag {
      private ControlFlowRootTag() {
      }

      public static enum Type {
         Conditional,
         ExceptionHandler,
         ForOfIteration,
         ForAwaitOfIteration,
         ForInIteration,
         ForIteration,
         DoWhileIteration,
         WhileIteration,
         AsyncFunction;
      }
   }

   @Tag.Identifier("Declare")
   public static final class DeclareTag extends Tag {
      public static final String NAME = "declarationName";
      public static final String TYPE = "declarationType";

      private DeclareTag() {
      }
   }

   @Tag.Identifier("EvalCall")
   public static final class EvalCallTag extends Tag {
      private EvalCallTag() {
      }
   }

   @Tag.Identifier("FunctionCall")
   public static final class FunctionCallTag extends Tag {
      private FunctionCallTag() {
      }
   }

   @Tag.Identifier("InputNode")
   public static final class InputNodeTag extends Tag {
      private InputNodeTag() {
      }
   }

   @Tag.Identifier("Literal")
   public static final class LiteralTag extends Tag {
      public static final String TYPE = "literalType";

      private LiteralTag() {
      }

      public static enum Type {
         ObjectLiteral,
         ArrayLiteral,
         FunctionLiteral,
         NumericLiteral,
         BooleanLiteral,
         StringLiteral,
         NullLiteral,
         UndefinedLiteral,
         RegExpLiteral,
         BigIntLiteral;
      }
   }

   @Tag.Identifier("ObjectAllocation")
   public static final class ObjectAllocationTag extends Tag {
      private ObjectAllocationTag() {
      }
   }

   @Tag.Identifier("ReadElement")
   public static final class ReadElementTag extends Tag {
      private ReadElementTag() {
      }
   }

   @Tag.Identifier("ReadProperty")
   public static final class ReadPropertyTag extends Tag {
      private ReadPropertyTag() {
      }
   }

   @Tag.Identifier("ReadVariable")
   public static final class ReadVariableTag extends Tag {
      private ReadVariableTag() {
      }
   }

   @Tag.Identifier("UnaryOperation")
   public static final class UnaryOperationTag extends Tag {
      private UnaryOperationTag() {
      }
   }

   @Tag.Identifier("WriteElement")
   public static final class WriteElementTag extends Tag {
      private WriteElementTag() {
      }
   }

   @Tag.Identifier("WriteProperty")
   public static final class WritePropertyTag extends Tag {
      private WritePropertyTag() {
      }
   }

   @Tag.Identifier("WriteVariable")
   public static final class WriteVariableTag extends Tag {
      private WriteVariableTag() {
      }
   }
}
