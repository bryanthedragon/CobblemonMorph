
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.StringHolder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import java.util.ArrayList;

public final class ForEachExpression extends StringHolder implements Expression {
    private final Expression variable;
    private final Expression array;
    private final Expression body;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        MoValue array = this.array.evaluate(scope, environment);
        if (array instanceof VariableStruct) {
            VariableStruct struct2 = (VariableStruct)array;
            MoScope scope2 = new MoScope();
            for (MoValue value2 : new ArrayList<MoValue>(struct2.getMap().values())) {
                this.variable.assign(scope2, environment, value2);
                this.body.evaluate(scope2, environment);
                if (scope2.getReturnValue() != null) {
                    return scope2.getReturnValue();
                }
                if (!scope2.isBreak()) continue;
                break;
            }
        }
        return DoubleValue.ZERO;
    }

    public ForEachExpression(Expression variable, Expression array, Expression body) {
        this.variable = variable;
        this.array = array;
        this.body = body;
    }

    public Expression getVariable() {
        return this.variable;
    }

    public Expression getArray() {
        return this.array;
    }

    public Expression getBody() {
        return this.body;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ForEachExpression)) {
            return false;
        }
        ForEachExpression other = (ForEachExpression)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Expression this$variable = this.getVariable();
        Expression other$variable = other.getVariable();
        if (this$variable == null ? other$variable != null : !this$variable.equals(other$variable)) {
            return false;
        }
        Expression this$array = this.getArray();
        Expression other$array = other.getArray();
        if (this$array == null ? other$array != null : !this$array.equals(other$array)) {
            return false;
        }
        Expression this$body = this.getBody();
        Expression other$body = other.getBody();
        return !(this$body == null ? other$body != null : !this$body.equals(other$body));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ForEachExpression;
    }

    @SuppressWarnings("unused")
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Expression $variable = this.getVariable();
        result = result * 59 + ($variable == null ? 43 : $variable.hashCode());
        Expression $array = this.getArray();
        result = result * 59 + ($array == null ? 43 : $array.hashCode());
        Expression $body = this.getBody();
        result = result * 59 + ($body == null ? 43 : $body.hashCode());
        return result;
    }

    public String toString() {
        return "ForEachExpression(variable=" + this.getVariable() + ", array=" + this.getArray() + ", body=" + this.getBody() + ")";
    }
}

