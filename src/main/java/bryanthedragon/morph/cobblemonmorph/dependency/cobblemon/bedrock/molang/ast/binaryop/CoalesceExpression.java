
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.BinaryOpExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import java.util.List;

public class CoalesceExpression
extends BinaryOpExpression {
    public CoalesceExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String getSigil() {
        return "??";
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        MoValue evalLeft = this.left.evaluate(scope, environment);
        String leftString = evalLeft.asString();
        List leftNames = List.of((Object[])leftString.split("\\."));
        MoValue value2 = environment.getValue(leftNames.iterator());
        if (value2 == null || value2.equals(DoubleValue.ZERO)) {
            return this.right.evaluate(scope, environment);
        }
        return evalLeft;
    }
}

