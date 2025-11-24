
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.BinaryOpExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

public class ArrowExpression
extends BinaryOpExpression {
    public ArrowExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String getSigil() {
        return "->";
    }

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        MoValue leftEnv = this.left.evaluate(scope, environment);
        if (leftEnv instanceof MoLangEnvironment) {
            return this.right.evaluate(scope, (MoLangEnvironment)leftEnv);
        }
        return null;
    }
}

