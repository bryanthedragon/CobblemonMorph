package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;
import java.util.Arrays;

public final class StatementExpression extends StringHolder implements Expression {
   private final Expression[] expressions;

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      for (Expression expression : this.expressions) {
         expression.evaluate(scope, environment);
         if (scope.getReturnValue() != null) {
            return scope.getReturnValue();
         }

         if (scope.isBreak() || scope.isContinue()) {
            break;
         }
      }

      return DoubleValue.ZERO;
   }

   public StatementExpression(Expression[] expressions) {
      this.expressions = expressions;
   }

   public Expression[] getExpressions() {
      return this.expressions;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } 
      else if (!(o instanceof StatementExpression other)) {
         return false;
      }
      else {
         return !other.canEqual(this) ? false : Arrays.deepEquals(this.getExpressions(), other.getExpressions());
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof StatementExpression;
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      return result * 59 + Arrays.deepHashCode(this.getExpressions());
   }

   @Override
   public String toString() {
      return "StatementExpression(expressions=" + Arrays.deepToString(this.getExpressions()) + ")";
   }
}
