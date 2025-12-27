package com.oracle.js.parser.ir;

import com.oracle.js.parser.Lexer;
import com.oracle.js.parser.ParserStrings;
import com.oracle.js.parser.Token;
import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class LiteralNode<T> extends Expression {
   protected final T value;

   protected LiteralNode(final long token, final int finish, final T value) {
      super(token, finish);
      this.value = value;
   }

   protected LiteralNode(final LiteralNode<T> literalNode) {
      this(literalNode, literalNode.value);
   }

   protected LiteralNode(final LiteralNode<T> literalNode, final T newValue) {
      super(literalNode);
      this.value = newValue;
   }

   public String getString() {
      return this.value instanceof TruffleString ? ((TruffleString)this.value).toJavaStringUncached() : String.valueOf(this.value);
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
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterLiteralNode(this) ? visitor.leaveLiteralNode(this) : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterLiteralNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      if (this.value == null) {
         sb.append("null");
      } else {
         sb.append(this.value.toString());
      }
   }

   public final T getValue() {
      return this.value;
   }

   private static Expression[] valueToArray(final List<Expression> value) {
      return value.toArray(new Expression[value.size()]);
   }

   public static LiteralNode<Object> newInstance(final long token, final int finish) {
      return new LiteralNode.NullLiteralNode(token, finish);
   }

   public static LiteralNode<Boolean> newInstance(final long token, final int finish, final boolean value) {
      return new LiteralNode.BooleanLiteralNode(token, finish, value);
   }

   public static LiteralNode<Number> newInstance(final long token, final int finish, final Number value) {
      return new LiteralNode.NumberLiteralNode(token, finish, value, null);
   }

   public static LiteralNode<Number> newInstance(
      final long token, final int finish, final Number value, final Function<Number, TruffleString> toStringConverter
   ) {
      return new LiteralNode.NumberLiteralNode(token, finish, value, toStringConverter);
   }

   public static LiteralNode<TruffleString> newInstance(final long token, final TruffleString value) {
      long tokenWithDelimiter = Token.withDelimiter(token);
      int newFinish = Token.descPosition(tokenWithDelimiter) + Token.descLength(tokenWithDelimiter);
      return new LiteralNode.StringLiteralNode(tokenWithDelimiter, newFinish, value);
   }

   public static LiteralNode<Lexer.LexerToken> newInstance(final long token, final int finish, final Lexer.LexerToken value) {
      return new LiteralNode.LexerTokenLiteralNode(token, finish, value);
   }

   public static LiteralNode<Expression[]> newInstance(final long token, final int finish, final List<Expression> value) {
      return newInstance(token, finish, valueToArray(value));
   }

   public static LiteralNode<Expression[]> newInstance(long token, int finish, List<Expression> value, boolean hasSpread, boolean hasTrailingComma) {
      return new LiteralNode.ArrayLiteralNode(token, finish, valueToArray(value), hasSpread, hasTrailingComma);
   }

   public static LiteralNode<Expression[]> newInstance(final long token, final int finish, final Expression[] value) {
      return new LiteralNode.ArrayLiteralNode(token, finish, value);
   }

   public static final class ArrayLiteralNode extends LiteralNode<Expression[]> implements LexicalContextNode {
      private final boolean hasSpread;
      private final boolean hasTrailingComma;

      protected ArrayLiteralNode(final long token, final int finish, final Expression[] value) {
         this(token, finish, value, false, false);
      }

      protected ArrayLiteralNode(final long token, final int finish, final Expression[] value, boolean hasSpread, boolean hasTrailingComma) {
         super(Token.recast(token, TokenType.ARRAY), finish, value);
         this.hasSpread = hasSpread;
         this.hasTrailingComma = hasTrailingComma;
      }

      private ArrayLiteralNode(final LiteralNode.ArrayLiteralNode node, final Expression[] value) {
         super(node, value);
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
         return Collections.unmodifiableList(Arrays.asList(this.value));
      }

      @Override
      public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
         return LexicalContextNode.super.accept(visitor);
      }

      @Override
      public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
         return LexicalContextNode.super.accept(visitor);
      }

      @Override
      public Node accept(final LexicalContext lc, final NodeVisitor<? extends LexicalContext> visitor) {
         if (visitor.enterLiteralNode(this)) {
            List<Expression> oldValue = Arrays.asList(this.value);
            List<Expression> newValue = Node.accept(visitor, oldValue);
            return visitor.leaveLiteralNode(oldValue != newValue ? this.setValue(lc, newValue) : this);
         } else {
            return this;
         }
      }

      @Override
      public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
         return visitor.enterLiteralNode(this);
      }

      private LiteralNode.ArrayLiteralNode setValue(final LexicalContext lc, final Expression[] value) {
         return this.value == value ? this : Node.replaceInLexicalContext(lc, this, new LiteralNode.ArrayLiteralNode(this, value));
      }

      private LiteralNode.ArrayLiteralNode setValue(final LexicalContext lc, final List<Expression> value) {
         return this.setValue(lc, value.toArray(new Expression[value.size()]));
      }

      @Override
      public void toString(final StringBuilder sb, final boolean printType) {
         sb.append('[');
         boolean first = true;

         for (Node node : this.value) {
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

   private static final class BooleanLiteralNode extends LiteralNode.PrimitiveLiteralNode<Boolean> {
      private BooleanLiteralNode(final long token, final int finish, final boolean value) {
         super(Token.recast(token, value ? TokenType.TRUE : TokenType.FALSE), finish, value);
      }

      private BooleanLiteralNode(final LiteralNode.BooleanLiteralNode literalNode) {
         super(literalNode);
      }
   }

   private static final class LexerTokenLiteralNode extends LiteralNode<Lexer.LexerToken> {
      private LexerTokenLiteralNode(final long token, final int finish, final Lexer.LexerToken value) {
         super(Token.recast(token, TokenType.STRING), finish, value);
      }

      private LexerTokenLiteralNode(final LiteralNode.LexerTokenLiteralNode literalNode) {
         super(literalNode);
      }

      @Override
      public void toString(final StringBuilder sb, final boolean printType) {
         sb.append(this.value.toString());
      }
   }

   private static final class NullLiteralNode extends LiteralNode.PrimitiveLiteralNode<Object> {
      private NullLiteralNode(final long token, final int finish) {
         super(Token.recast(token, TokenType.OBJECT), finish, null);
      }
   }

   private static final class NumberLiteralNode extends LiteralNode.PrimitiveLiteralNode<Number> {
      private final Function<Number, TruffleString> toStringConverter;

      private NumberLiteralNode(final long token, final int finish, final Number value, final Function<Number, TruffleString> toStringConverter) {
         super(Token.recast(token, TokenType.DECIMAL), finish, value);
         this.toStringConverter = toStringConverter;
      }

      private NumberLiteralNode(final LiteralNode.NumberLiteralNode literalNode) {
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

   public static class PrimitiveLiteralNode<T> extends LiteralNode<T> implements PropertyKey {
      private PrimitiveLiteralNode(final long token, final int finish, final T value) {
         super(token, finish, value);
      }

      private PrimitiveLiteralNode(final LiteralNode.PrimitiveLiteralNode<T> literalNode) {
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

   private static final class StringLiteralNode extends LiteralNode.PrimitiveLiteralNode<TruffleString> {
      private StringLiteralNode(final long token, final int finish, final TruffleString value) {
         super(Token.recast(token, TokenType.STRING), finish, value);
      }

      @Override
      public String getPropertyName() {
         return this.value.toJavaStringUncached();
      }

      @Override
      public TruffleString getPropertyNameTS() {
         return this.value;
      }

      @Override
      public void toString(final StringBuilder sb, final boolean printType) {
         sb.append('"');
         sb.append(this.value);
         sb.append('"');
      }
   }
}
