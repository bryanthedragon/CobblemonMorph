
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.StringHolder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

public final class AssignExpression
extends StringHolder
implements Expression {
    private final Expression variable;
    private final Expression expr;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        MoValue value2 = this.expr.evaluate(scope, environment);
        this.variable.assign(scope, environment, value2);
        return value2;
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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AssignExpression)) {
            return false;
        }
        AssignExpression other = (AssignExpression)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Expression this$variable = this.getVariable();
        Expression other$variable = other.getVariable();
        if (this$variable == null ? other$variable != null : !this$variable.equals(other$variable)) {
            return false;
        }
        Expression this$expr = this.getExpr();
        Expression other$expr = other.getExpr();
        return !(this$expr == null ? other$expr != null : !this$expr.equals(other$expr));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AssignExpression;
    }

    @SuppressWarnings("unused")
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Expression $variable = this.getVariable();
        result = result * 59 + ($variable == null ? 43 : $variable.hashCode());
        Expression $expr = this.getExpr();
        result = result * 59 + ($expr == null ? 43 : $expr.hashCode());
        return result;
    }

    public String toString() {
        return "AssignExpression(variable=" + this.getVariable() + ", expr=" + this.getExpr() + ")";
    }
}

