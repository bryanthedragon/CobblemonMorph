package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.ast.binaryop.ArrowExpression;
import com.bedrockk.molang.ast.binaryop.BooleanAndExpression;
import com.bedrockk.molang.ast.binaryop.BooleanOrExpression;
import com.bedrockk.molang.ast.binaryop.CoalesceExpression;
import com.bedrockk.molang.ast.binaryop.DivideExpression;
import com.bedrockk.molang.ast.binaryop.EqualExpression;
import com.bedrockk.molang.ast.binaryop.GreaterExpression;
import com.bedrockk.molang.ast.binaryop.GreaterOrEqualExpression;
import com.bedrockk.molang.ast.binaryop.MinusExpression;
import com.bedrockk.molang.ast.binaryop.NotEqualExpression;
import com.bedrockk.molang.ast.binaryop.PlusExpression;
import com.bedrockk.molang.ast.binaryop.PowExpression;
import com.bedrockk.molang.ast.binaryop.SmallerExpression;
import com.bedrockk.molang.ast.binaryop.SmallerOrEqualExpression;
import com.bedrockk.molang.parser.InfixParselet;
import com.bedrockk.molang.parser.MoLangParser;
import com.bedrockk.molang.parser.Precedence;
import com.bedrockk.molang.parser.tokenizer.Token;

public final class GenericBinaryOpParselet implements InfixParselet {
   private final Precedence precedence;

   @Override
   public Expression parse(MoLangParser parser, Token token, Expression leftExpr) {
      Expression rightExpr = parser.parseExpression(this.getPrecedence());
      switch (token.getType()) {
         case ARROW:
            return new ArrowExpression(leftExpr, rightExpr);
         case AND:
            return new BooleanAndExpression(leftExpr, rightExpr);
         case OR:
            return new BooleanOrExpression(leftExpr, rightExpr);
         case COALESCE:
            return new CoalesceExpression(leftExpr, rightExpr);
         case SLASH:
            return new DivideExpression(leftExpr, rightExpr);
         case EQUALS:
            return new EqualExpression(leftExpr, rightExpr);
         case GREATER:
            return new GreaterExpression(leftExpr, rightExpr);
         case GREATER_OR_EQUALS:
            return new GreaterOrEqualExpression(leftExpr, rightExpr);
         case MINUS:
            return new MinusExpression(leftExpr, rightExpr);
         case NOT_EQUALS:
            return new NotEqualExpression(leftExpr, rightExpr);
         case PLUS:
            return new PlusExpression(leftExpr, rightExpr);
         case ASTERISK:
            return new PowExpression(leftExpr, rightExpr);
         case SMALLER:
            return new SmallerExpression(leftExpr, rightExpr);
         case SMALLER_OR_EQUALS:
            return new SmallerOrEqualExpression(leftExpr, rightExpr);
         default:
            return null;
      }
   }

   @Override
   public Precedence getPrecedence() {
      return this.precedence;
   }

   public GenericBinaryOpParselet(Precedence precedence) {
      this.precedence = precedence;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } 
      else if (!(o instanceof GenericBinaryOpParselet other)) {
         return false;
      } 
      else {
         Object this$precedence = this.getPrecedence();
         Object other$precedence = other.getPrecedence();
         return this$precedence == null ? other$precedence == null : this$precedence.equals(other$precedence);
      }
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $precedence = this.getPrecedence();
      return result * 59 + ($precedence == null ? 43 : $precedence.hashCode());
   }

   @Override
   public String toString() {
      return "GenericBinaryOpParselet(precedence=" + this.getPrecedence() + ")";
   }
}
