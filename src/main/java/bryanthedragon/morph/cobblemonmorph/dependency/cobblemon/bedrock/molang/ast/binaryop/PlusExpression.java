
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.BinaryOpExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;

public class PlusExpression
extends BinaryOpExpression {
    public PlusExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String getSigil() {
        return "+";
    }

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        MoValue leftValue = this.left.evaluate(scope, environment);
        MoValue rightValue = this.right.evaluate(scope, environment);
        if (leftValue instanceof StringValue || rightValue instanceof StringValue) {
            return new StringValue(leftValue.asString() + rightValue.asString());
        }
        return new DoubleValue(leftValue.asDouble() + rightValue.asDouble());
    }
}

