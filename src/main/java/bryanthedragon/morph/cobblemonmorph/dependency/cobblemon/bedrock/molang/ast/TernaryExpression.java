
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.StringHolder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

public final class TernaryExpression
extends StringHolder
implements Expression {
    private final Expression condition;
    private final Expression thenExpr;
    private final Expression elseExpr;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        if (this.condition.evaluate(scope, environment).equals(DoubleValue.ONE)) {
            return this.thenExpr == null ? this.condition.evaluate(scope, environment) : this.thenExpr.evaluate(scope, environment);
        }
        if (this.elseExpr != null) {
            return this.elseExpr.evaluate(scope, environment);
        }
        return DoubleValue.ZERO;
    }

    public TernaryExpression(Expression condition2, Expression thenExpr, Expression elseExpr) {
        this.condition = condition2;
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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TernaryExpression)) {
            return false;
        }
        TernaryExpression other = (TernaryExpression)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Expression this$condition = this.getCondition();
        Expression other$condition = other.getCondition();
        if (this$condition == null ? other$condition != null : !this$condition.equals(other$condition)) {
            return false;
        }
        Expression this$thenExpr = this.getThenExpr();
        Expression other$thenExpr = other.getThenExpr();
        if (this$thenExpr == null ? other$thenExpr != null : !this$thenExpr.equals(other$thenExpr)) {
            return false;
        }
        Expression this$elseExpr = this.getElseExpr();
        Expression other$elseExpr = other.getElseExpr();
        return !(this$elseExpr == null ? other$elseExpr != null : !this$elseExpr.equals(other$elseExpr));
    }

    protected boolean canEqual(Object other) {
        return other instanceof TernaryExpression;
    }

    @SuppressWarnings("unused")
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Expression $condition = this.getCondition();
        result = result * 59 + ($condition == null ? 43 : $condition.hashCode());
        Expression $thenExpr = this.getThenExpr();
        result = result * 59 + ($thenExpr == null ? 43 : $thenExpr.hashCode());
        Expression $elseExpr = this.getElseExpr();
        result = result * 59 + ($elseExpr == null ? 43 : $elseExpr.hashCode());
        return result;
    }

    public String toString() {
        return "TernaryExpression(condition=" + this.getCondition() + ", thenExpr=" + this.getThenExpr() + ", elseExpr=" + this.getElseExpr() + ")";
    }
}

