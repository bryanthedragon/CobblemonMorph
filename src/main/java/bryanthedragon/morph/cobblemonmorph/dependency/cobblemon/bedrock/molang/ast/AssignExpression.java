package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.MoValue;

public final class AssignExpression extends StringHolder implements Expression {
   private final Expression variable;
   private final Expression expr;

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      MoValue value = this.expr.evaluate(scope, environment);
      this.variable.assign(scope, environment, value);
      return value;
   }

   public AssignExpression(Expression variable, Expression expr) {
      this.variable = variable;
      this.expr = expr;
   }

   public Expression getVariable() {
      return this.variable;
   }

   public Expression getExpr() {
      return this.expr;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } 
      else if (!(o instanceof AssignExpression other)) {
         return false;
      } 
      else if (!other.canEqual(this)) {
         return false;
      } 
      else {
         Object this$variable = this.getVariable();
         Object other$variable = other.getVariable();
         if (this$variable == null ? other$variable == null : this$variable.equals(other$variable)) {
            Object this$expr = this.getExpr();
            Object other$expr = other.getExpr();
            return this$expr == null ? other$expr == null : this$expr.equals(other$expr);
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof AssignExpression;
   }

   @Override   
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $variable = this.getVariable();
      result = result * 59 + ($variable == null ? 43 : $variable.hashCode());
      Object $expr = this.getExpr();
      return result * 59 + ($expr == null ? 43 : $expr.hashCode());
   }

   @Override
   public String toString() {
      return "AssignExpression(variable=" + this.getVariable() + ", expr=" + this.getExpr() + ")";
   }
}
