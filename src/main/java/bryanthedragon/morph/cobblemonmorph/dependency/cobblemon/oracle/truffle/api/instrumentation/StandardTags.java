
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.instrumentation.Tag;

public final class StandardTags {
    static final Class[] ALL_TAGS = new Class[]{StatementTag.class, CallTag.class, RootTag.class, RootBodyTag.class, ExpressionTag.class, TryBlockTag.class, ReadVariableTag.class, WriteVariableTag.class};

    private StandardTags() {
    }

    @Tag.Identifier(value="WRITE_VARIABLE")
    public static final class WriteVariableTag
    extends Tag {
        public static final String NAME = "writeVariableName";

        private WriteVariableTag() {
        }
    }

    @Tag.Identifier(value="READ_VARIABLE")
    public static final class ReadVariableTag
    extends Tag {
        public static final String NAME = "readVariableName";

        private ReadVariableTag() {
        }
    }

    @Tag.Identifier(value="TRY_BLOCK")
    public static final class TryBlockTag
    extends Tag {
        public static final String CATCHES = "catches";

        private TryBlockTag() {
        }
    }

    @Tag.Identifier(value="EXPRESSION")
    public static final class ExpressionTag
    extends Tag {
        private ExpressionTag() {
        }
    }

    @Tag.Identifier(value="ROOT_BODY")
    public static final class RootBodyTag
    extends Tag {
        private RootBodyTag() {
        }
    }

    @Tag.Identifier(value="ROOT")
    public static final class RootTag
    extends Tag {
        private RootTag() {
        }
    }

    @Tag.Identifier(value="CALL")
    public static final class CallTag
    extends Tag {
        private CallTag() {
        }
    }

    @Tag.Identifier(value="STATEMENT")
    public static final class StatementTag
    extends Tag {
        private StatementTag() {
        }
    }
}

