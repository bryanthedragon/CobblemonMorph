
package com.oracle.js.parser.ir;

import com.oracle.js.parser.Lexer;
import com.oracle.js.parser.ParserStrings;
import com.oracle.js.parser.Token;
import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LexicalContextNode;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.PropertyKey;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class LiteralNode<T>
extends Expression {
    protected final T value;

    protected LiteralNode(long token, int finish, T value2) {
        super(token, finish);
        this.value = value2;
    }

    protected LiteralNode(LiteralNode<T> literalNode) {
        this(literalNode, literalNode.value);
    }

    protected LiteralNode(LiteralNode<T> literalNode, T newValue) {
        super(literalNode);
        this.value = newValue;
    }

    public String getString() {
        if (this.value instanceof TruffleString) {
            return ((TruffleString)this.value).toJavaStringUncached();
        }
        return String.valueOf(this.value);
    }

    public Object getObject() {
        return this.value;
    }

    public boolean isArray() {
        return false;
    }

    public List<Expression> getElementExpressions() {
        return null;
    }

    public boolean isString() {
        return this.value instanceof TruffleString;
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterLiteralNode(this)) {
            return visitor.leaveLiteralNode(this);
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterLiteralNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        if (this.value == null) {
            sb.append("null");
        } else {
            sb.append(this.value.toString());
        }
    }

    public final T getValue() {
        return this.value;
    }

    private static Expression[] valueToArray(List<Expression> value2) {
        return value2.toArray(new Expression[value2.size()]);
    }

    public static LiteralNode<Object> newInstance(long token, int finish) {
        return new NullLiteralNode(token, finish);
    }

    public static LiteralNode<Boolean> newInstance(long token, int finish, boolean value2) {
        return new BooleanLiteralNode(token, finish, value2);
    }

    public static LiteralNode<Number> newInstance(long token, int finish, Number value2) {
        return new NumberLiteralNode(token, finish, value2, null);
    }

    public static LiteralNode<Number> newInstance(long token, int finish, Number value2, Function<Number, TruffleString> toStringConverter) {
        return new NumberLiteralNode(token, finish, value2, toStringConverter);
    }

    public static LiteralNode<TruffleString> newInstance(long token, TruffleString value2) {
        long tokenWithDelimiter = Token.withDelimiter(token);
        int newFinish = Token.descPosition(tokenWithDelimiter) + Token.descLength(tokenWithDelimiter);
        return new StringLiteralNode(tokenWithDelimiter, newFinish, value2);
    }

    public static LiteralNode<Lexer.LexerToken> newInstance(long token, int finish, Lexer.LexerToken value2) {
        return new LexerTokenLiteralNode(token, finish, value2);
    }

    public static LiteralNode<Expression[]> newInstance(long token, int finish, List<Expression> value2) {
        return LiteralNode.newInstance(token, finish, LiteralNode.valueToArray(value2));
    }

    public static LiteralNode<Expression[]> newInstance(long token, int finish, List<Expression> value2, boolean hasSpread, boolean hasTrailingComma) {
        return new ArrayLiteralNode(token, finish, LiteralNode.valueToArray(value2), hasSpread, hasTrailingComma);
    }

    public static LiteralNode<Expression[]> newInstance(long token, int finish, Expression[] value2) {
        return new ArrayLiteralNode(token, finish, value2);
    }

    public static final class ArrayLiteralNode
    extends LiteralNode<Expression[]>
    implements LexicalContextNode {
        private final boolean hasSpread;
        private final boolean hasTrailingComma;

        protected ArrayLiteralNode(long token, int finish, Expression[] value2) {
            this(token, finish, value2, false, false);
        }

        protected ArrayLiteralNode(long token, int finish, Expression[] value2, boolean hasSpread, boolean hasTrailingComma) {
            super(Token.recast(token, TokenType.ARRAY), finish, value2);
            this.hasSpread = hasSpread;
            this.hasTrailingComma = hasTrailingComma;
        }

        private ArrayLiteralNode(ArrayLiteralNode node, Expression[] value2) {
            super(node, value2);
            this.hasSpread = node.hasSpread;
            this.hasTrailingComma = node.hasTrailingComma;
        }

        @Override
        public boolean isArray() {
            return true;
        }

        public boolean hasSpread() {
            return this.hasSpread;
        }

        public boolean hasTrailingComma() {
            return this.hasTrailingComma;
        }

        @Override
        public List<Expression> getElementExpressions() {
            return Collections.unmodifiableList(Arrays.asList((Expression[])this.value));
        }

        @Override
        public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
            return LexicalContextNode.super.accept(visitor);
        }

        @Override
        public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
            return LexicalContextNode.super.accept(visitor);
        }

        @Override
        public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
            if (visitor.enterLiteralNode(this)) {
                List<Expression> newValue;
                List<Expression> oldValue = Arrays.asList((Expression[])this.value);
                return visitor.leaveLiteralNode(oldValue != (newValue = Node.accept(visitor, oldValue)) ? this.setValue(lc, newValue) : this);
            }
            return this;
        }

        @Override
        public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
            return visitor.enterLiteralNode(this);
        }

        private ArrayLiteralNode setValue(LexicalContext lc, Expression[] value2) {
            if (this.value == value2) {
                return this;
            }
            return Node.replaceInLexicalContext(lc, this, new ArrayLiteralNode(this, value2));
        }

        private ArrayLiteralNode setValue(LexicalContext lc, List<Expression> value2) {
            return this.setValue(lc, value2.toArray(new Expression[value2.size()]));
        }

        @Override
        public void toString(StringBuilder sb, boolean printType) {
            sb.append('[');
            boolean first = true;
            for (Expression node : (Expression[])this.value) {
                if (!first) {
                    sb.append(',');
                    sb.append(' ');
                }
                if (node == null) {
                    sb.append("undefined");
                } else {
                    node.toString(sb, printType);
                }
                first = false;
            }
            sb.append(']');
        }
    }

    private static final class NullLiteralNode
    extends PrimitiveLiteralNode<Object> {
        private NullLiteralNode(long token, int finish) {
            super(Token.recast(token, TokenType.OBJECT), finish, null);
        }
    }

    private static final class LexerTokenLiteralNode
    extends LiteralNode<Lexer.LexerToken> {
        private LexerTokenLiteralNode(long token, int finish, Lexer.LexerToken value2) {
            super(Token.recast(token, TokenType.STRING), finish, value2);
        }

        private LexerTokenLiteralNode(LexerTokenLiteralNode literalNode) {
            super(literalNode);
        }

        @Override
        public void toString(StringBuilder sb, boolean printType) {
            sb.append(((Lexer.LexerToken)this.value).toString());
        }
    }

    private static final class StringLiteralNode
    extends PrimitiveLiteralNode<TruffleString> {
        private StringLiteralNode(long token, int finish, TruffleString value2) {
            super(Token.recast(token, TokenType.STRING), finish, value2);
        }

        @Override
        public String getPropertyName() {
            return ((TruffleString)this.value).toJavaStringUncached();
        }

        @Override
        public TruffleString getPropertyNameTS() {
            return (TruffleString)this.value;
        }

        @Override
        public void toString(StringBuilder sb, boolean printType) {
            sb.append('\"');
            sb.append(this.value);
            sb.append('\"');
        }
    }

    private static final class NumberLiteralNode
    extends PrimitiveLiteralNode<Number> {
        private final Function<Number, TruffleString> toStringConverter;

        private NumberLiteralNode(long token, int finish, Number value2, Function<Number, TruffleString> toStringConverter) {
            super(Token.recast(token, TokenType.DECIMAL), finish, value2);
            this.toStringConverter = toStringConverter;
        }

        private NumberLiteralNode(NumberLiteralNode literalNode) {
            super(literalNode);
            this.toStringConverter = literalNode.toStringConverter;
        }

        @Override
        public String getPropertyName() {
            return this.toStringConverter == null ? super.getPropertyName() : this.toStringConverter.apply((Number)this.getValue()).toJavaStringUncached();
        }

        @Override
        public TruffleString getPropertyNameTS() {
            return this.toStringConverter == null ? super.getPropertyNameTS() : this.toStringConverter.apply((Number)this.getValue());
        }
    }

    private static final class BooleanLiteralNode
    extends PrimitiveLiteralNode<Boolean> {
        private BooleanLiteralNode(long token, int finish, boolean value2) {
            super(Token.recast(token, value2 ? TokenType.TRUE : TokenType.FALSE), finish, value2);
        }

        private BooleanLiteralNode(BooleanLiteralNode literalNode) {
            super(literalNode);
        }
    }

    public static class PrimitiveLiteralNode<T>
    extends LiteralNode<T>
    implements PropertyKey {
        private PrimitiveLiteralNode(long token, int finish, T value2) {
            super(token, finish, value2);
        }

        private PrimitiveLiteralNode(PrimitiveLiteralNode<T> literalNode) {
            super(literalNode);
        }

        @Override
        public String getPropertyName() {
            return String.valueOf(this.getObject());
        }

        @Override
        public TruffleString getPropertyNameTS() {
            return ParserStrings.fromJavaString(this.getPropertyName());
        }
    }
}

