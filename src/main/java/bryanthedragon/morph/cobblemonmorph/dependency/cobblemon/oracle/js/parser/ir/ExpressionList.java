package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public class ExpressionList extends Expression {
   private final List<? extends Expression> expressions;

   public ExpressionList(final long token, final int finish, final List<? extends Expression> expressions) {
      super(token, finish);
      this.expressions = List.copyOf(expressions);
   }

   public List<? extends Expression> getExpressions() {
      return this.expressions;
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      throw new UnsupportedOperationException();
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void toString(StringBuilder sb, boolean printType) {
      sb.append("(");
      boolean first = true;

      for (Expression expression : this.expressions) {
         if (first) {
            first = false;
         } else {
            sb.append(", ");
         }

         expression.toString(sb, printType);
      }

      sb.append(")");
   }
}
