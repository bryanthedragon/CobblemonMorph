package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;

public final class UnaryMinusExpression extends StringHolder implements Expression {
   private final Expression expression;

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      return new DoubleValue(-this.expression.evaluate(scope, environment).asDouble());
   }

   public UnaryMinusExpression(Expression expression) {
      this.expression = expression;
   }

   public Expression getExpression() {
      return this.expression;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } 
      else if (!(o instanceof UnaryMinusExpression other)) {
         return false;
      } 
      else if (!other.canEqual(this)) {
         return false;
      } 
      else {
         Object this$expression = this.getExpression();
         Object other$expression = other.getExpression();
         return this$expression == null ? other$expression == null : this$expression.equals(other$expression);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof UnaryMinusExpression;
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $expression = this.getExpression();
      return result * 59 + ($expression == null ? 43 : $expression.hashCode());
   }

   @Override
   public String toString() {
      return "UnaryMinusExpression(expression=" + this.getExpression() + ")";
   }
}
