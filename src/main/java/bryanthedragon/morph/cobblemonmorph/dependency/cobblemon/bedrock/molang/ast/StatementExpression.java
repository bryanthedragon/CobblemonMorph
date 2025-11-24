
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.StringHolder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import java.util.Arrays;

public final class StatementExpression
extends StringHolder
implements Expression {
    private final Expression[] expressions;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        for (Expression expression : this.expressions) {
            expression.evaluate(scope, environment);
            if (scope.getReturnValue() != null) {
                return scope.getReturnValue();
            }
            if (scope.isBreak() || scope.isContinue()) break;
        }
        return DoubleValue.ZERO;
    }

    public StatementExpression(Expression[] expressions) {
        this.expressions = expressions;
    }

    public Expression[] getExpressions() {
        return this.expressions;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StatementExpression)) {
            return false;
        }
        StatementExpression other = (StatementExpression)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return Arrays.deepEquals(this.getExpressions(), other.getExpressions());
    }

    protected boolean canEqual(Object other) {
        return other instanceof StatementExpression;
    }

    @SuppressWarnings("unused")
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + Arrays.deepHashCode(this.getExpressions());
        return result;
    }

    public String toString() {
        return "StatementExpression(expressions=" + Arrays.deepToString(this.getExpressions()) + ")";
    }
}

