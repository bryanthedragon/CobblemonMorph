
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.StringHolder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

public final class ReturnExpression
extends StringHolder
implements Expression {
    private final Expression expression;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        MoValue eval = this.expression.evaluate(scope, environment);
        scope.setReturnValue(eval);
        return eval;
    }

    public ReturnExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return this.expression;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ReturnExpression)) {
            return false;
        }
        ReturnExpression other = (ReturnExpression)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Expression this$expression = this.getExpression();
        Expression other$expression = other.getExpression();
        return !(this$expression == null ? other$expression != null : !this$expression.equals(other$expression));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ReturnExpression;
    }
    
    @SuppressWarnings("unused")
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Expression $expression = this.getExpression();
        result = result * 59 + ($expression == null ? 43 : $expression.hashCode());
        return result;
    }

    public String toString() {
        return "ReturnExpression(expression=" + this.getExpression() + ")";
    }
}

