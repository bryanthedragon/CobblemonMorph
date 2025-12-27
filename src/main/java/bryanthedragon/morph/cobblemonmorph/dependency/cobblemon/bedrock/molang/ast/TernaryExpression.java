package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;

public final class TernaryExpression extends StringHolder implements Expression {
   private final Expression condition;
   private final Expression thenExpr;
   private final Expression elseExpr;

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      if (this.condition.evaluate(scope, environment).equals(DoubleValue.ONE)) {
         return this.thenExpr == null ? this.condition.evaluate(scope, environment) : this.thenExpr.evaluate(scope, environment);
      } 
      else {
         return (MoValue)(this.elseExpr != null ? this.elseExpr.evaluate(scope, environment) : DoubleValue.ZERO);
      }
   }

   public TernaryExpression(Expression condition, Expression thenExpr, Expression elseExpr) {
      this.condition = condition;
      this.thenExpr = thenExpr;
      this.elseExpr = elseExpr;
   }

   public Expression getCondition() {
      return this.condition;
   }

   public Expression getThenExpr() {
      return this.thenExpr;
   }

   public Expression getElseExpr() {
      return this.elseExpr;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } 
      else if (!(o instanceof TernaryExpression other)) {
         return false;
      } 
      else if (!other.canEqual(this)) {
         return false;
      } 
      else {
         Object this$condition = this.getCondition();
         Object other$condition = other.getCondition();
         if (this$condition == null ? other$condition == null : this$condition.equals(other$condition)) {
            Object this$thenExpr = this.getThenExpr();
            Object other$thenExpr = other.getThenExpr();
            if (this$thenExpr == null ? other$thenExpr == null : this$thenExpr.equals(other$thenExpr)) {
               Object this$elseExpr = this.getElseExpr();
               Object other$elseExpr = other.getElseExpr();
               return this$elseExpr == null ? other$elseExpr == null : this$elseExpr.equals(other$elseExpr);
            } 
            else {
               return false;
            }
         } 
         else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof TernaryExpression;
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $condition = this.getCondition();
      result = result * 59 + ($condition == null ? 43 : $condition.hashCode());
      Object $thenExpr = this.getThenExpr();
      result = result * 59 + ($thenExpr == null ? 43 : $thenExpr.hashCode());
      Object $elseExpr = this.getElseExpr();
      return result * 59 + ($elseExpr == null ? 43 : $elseExpr.hashCode());
   }

   @Override
   public String toString() {
      return "TernaryExpression(condition=" + this.getCondition() + ", thenExpr=" + this.getThenExpr() + ", elseExpr=" + this.getElseExpr() + ")";
   }
}
