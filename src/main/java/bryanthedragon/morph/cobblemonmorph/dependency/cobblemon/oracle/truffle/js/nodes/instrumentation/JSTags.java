
package com.oracle.truffle.js.nodes.instrumentation;

import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;

public final class JSTags {
    public static final Class<?>[] ALL = new Class[]{ObjectAllocationTag.class, BinaryOperationTag.class, UnaryOperationTag.class, WriteVariableTag.class, ReadElementTag.class, WriteElementTag.class, ReadPropertyTag.class, WritePropertyTag.class, ReadVariableTag.class, LiteralTag.class, FunctionCallTag.class, BuiltinRootTag.class, EvalCallTag.class, ControlFlowRootTag.class, ControlFlowBlockTag.class, ControlFlowBranchTag.class, DeclareTag.class};

    private JSTags() {
    }

    public static NodeObjectDescriptor createNodeObjectDescriptor() {
        return new NodeObjectDescriptor();
    }

    public static NodeObjectDescriptor createNodeObjectDescriptor(String name, Object value2) {
        NodeObjectDescriptor desc = new NodeObjectDescriptor();
        desc.addProperty(name, value2);
        return desc;
    }

    @Tag.Identifier(value="Declare")
    public static final class DeclareTag
    extends Tag {
        public static final String NAME = "declarationName";
        public static final String TYPE = "declarationType";

        private DeclareTag() {
        }
    }

    @Tag.Identifier(value="InputNode")
    public static final class InputNodeTag
    extends Tag {
        private InputNodeTag() {
        }
    }

    @Tag.Identifier(value="EvalCall")
    public static final class EvalCallTag
    extends Tag {
        private EvalCallTag() {
        }
    }

    @Tag.Identifier(value="BuiltinRoot")
    public static final class BuiltinRootTag
    extends Tag {
        private BuiltinRootTag() {
        }
    }

    @Tag.Identifier(value="ControlFlowBlockTag")
    public static final class ControlFlowBlockTag
    extends Tag {
        private ControlFlowBlockTag() {
        }
    }

    @Tag.Identifier(value="ControlFlowBranchTag")
    public static final class ControlFlowBranchTag
    extends Tag {
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

    @Tag.Identifier(value="ControlFlowRootTag")
    public static final class ControlFlowRootTag
    extends Tag {
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

    @Tag.Identifier(value="ReadProperty")
    public static final class ReadPropertyTag
    extends Tag {
        private ReadPropertyTag() {
        }
    }

    @Tag.Identifier(value="WriteProperty")
    public static final class WritePropertyTag
    extends Tag {
        private WritePropertyTag() {
        }
    }

    @Tag.Identifier(value="ReadElement")
    public static final class ReadElementTag
    extends Tag {
        private ReadElementTag() {
        }
    }

    @Tag.Identifier(value="WriteElement")
    public static final class WriteElementTag
    extends Tag {
        private WriteElementTag() {
        }
    }

    @Tag.Identifier(value="ReadVariable")
    public static final class ReadVariableTag
    extends Tag {
        private ReadVariableTag() {
        }
    }

    @Tag.Identifier(value="WriteVariable")
    public static final class WriteVariableTag
    extends Tag {
        private WriteVariableTag() {
        }
    }

    @Tag.Identifier(value="BinaryOperation")
    public static final class BinaryOperationTag
    extends Tag {
        private BinaryOperationTag() {
        }
    }

    @Tag.Identifier(value="UnaryOperation")
    public static final class UnaryOperationTag
    extends Tag {
        private UnaryOperationTag() {
        }
    }

    @Tag.Identifier(value="Literal")
    public static final class LiteralTag
    extends Tag {
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

    @Tag.Identifier(value="ObjectAllocation")
    public static final class ObjectAllocationTag
    extends Tag {
        private ObjectAllocationTag() {
        }
    }

    @Tag.Identifier(value="FunctionCall")
    public static final class FunctionCallTag
    extends Tag {
        private FunctionCallTag() {
        }
    }
}

