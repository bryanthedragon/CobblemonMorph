
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.BinaryOpExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

public class MinusExpression
extends BinaryOpExpression {
    public MinusExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String getSigil() {
        return "-";
    }

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        return new DoubleValue(this.left.evaluate(scope, environment).asDouble() - this.right.evaluate(scope, environment).asDouble());
    }
}

