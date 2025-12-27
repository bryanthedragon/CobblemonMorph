package com.oracle.js.parser.ir;

import com.oracle.js.parser.Token;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public abstract class TemplateLiteralNode extends Expression {
   protected TemplateLiteralNode(final long token, final int finish) {
      super(token, finish);
   }

   protected TemplateLiteralNode(final TemplateLiteralNode literalNode) {
      super(literalNode);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterTemplateLiteralNode(this);
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterTemplateLiteralNode(this) ? visitor.leaveTemplateLiteralNode(this) : this);
   }

   public static TemplateLiteralNode newTagged(final long token, final int finish, final List<Expression> rawStrings, final List<Expression> cookedStrings) {
      return new TemplateLiteralNode.TaggedTemplateLiteralNode(Token.withDelimiter(token), finish, rawStrings, cookedStrings);
   }

   public static TemplateLiteralNode newUntagged(final long token, final int finish, final List<Expression> expressions) {
      return new TemplateLiteralNode.UntaggedTemplateLiteralNode(Token.withDelimiter(token), finish, expressions);
   }

   public static class TaggedTemplateLiteralNode extends TemplateLiteralNode {
      private final List<Expression> rawStrings;
      private final List<Expression> cookedStrings;

      protected TaggedTemplateLiteralNode(long token, int finish, List<Expression> rawStrings, List<Expression> cookedStrings) {
         super(token, finish);
         this.rawStrings = List.copyOf(rawStrings);
         this.cookedStrings = List.copyOf(cookedStrings);
      }

      public List<Expression> getRawStrings() {
         return this.rawStrings;
      }

      public List<Expression> getCookedStrings() {
         return this.cookedStrings;
      }

      @Override
      public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
         return (Node)(visitor.enterTemplateLiteralNode(this) ? visitor.leaveTemplateLiteralNode(this) : this);
      }

      @Override
      public void toString(final StringBuilder sb, final boolean printType) {
         sb.append('`');

         for (int i = 0; i < this.rawStrings.size(); i++) {
            Expression expression = this.rawStrings.get(i);
            if (expression instanceof LiteralNode) {
               sb.append(((LiteralNode)expression).getString());
            } else {
               expression.toString(sb, printType);
            }

            if (i < this.rawStrings.size() - 1) {
               sb.append("${");
               sb.append(i);
               sb.append("}");
            }
         }

         sb.append('`');
      }
   }

   public static class UntaggedTemplateLiteralNode extends TemplateLiteralNode {
      private final List<Expression> expressions;

      protected UntaggedTemplateLiteralNode(long token, int finish, List<Expression> expressions) {
         super(token, finish);

         assert verifyStringLiterals(expressions);

         this.expressions = List.copyOf(expressions);
      }

      public UntaggedTemplateLiteralNode(TemplateLiteralNode.UntaggedTemplateLiteralNode literalNode, List<Expression> expressions) {
         super(literalNode);
         this.expressions = expressions;
      }

      public List<Expression> getExpressions() {
         return this.expressions;
      }

      @Override
      public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
         if (visitor.enterTemplateLiteralNode(this)) {
            List<Expression> newExpressions = Node.accept(visitor, this.expressions);
            return visitor.leaveTemplateLiteralNode(
               this.expressions != newExpressions ? new TemplateLiteralNode.UntaggedTemplateLiteralNode(this, newExpressions) : this
            );
         } else {
            return this;
         }
      }

      @Override
      public void toString(final StringBuilder sb, final boolean printType) {
         sb.append('`');

         for (int i = 0; i < this.expressions.size(); i++) {
            Expression expression = this.expressions.get(i);
            if (i % 2 == 0) {
               sb.append(((LiteralNode)expression).getString());
            } else {
               sb.append("${");
               expression.toString(sb, printType);
               sb.append("}");
            }
         }

         sb.append('`');
      }

      private static boolean verifyStringLiterals(List<Expression> expressions) {
         for (int i = 0; i < expressions.size(); i++) {
            if (i % 2 == 0) {
               Expression expression = expressions.get(i);
               if (!(expression instanceof LiteralNode)) {
                  return false;
               }
            }
         }

         return true;
      }
   }
}
