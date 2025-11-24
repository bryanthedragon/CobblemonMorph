
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.StringHolder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

public final class BreakExpression
extends StringHolder
implements Expression {
    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        scope.setBreak(true);
        return DoubleValue.ZERO;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BreakExpression)) {
            return false;
        }
        BreakExpression other = (BreakExpression)o;
        return other.canEqual(this);
    }

    protected boolean canEqual(Object other) {
        return other instanceof BreakExpression;
    }

    public int hashCode() {
        boolean result = true;
        return 1;
    }

    public String toString() {
        return "BreakExpression()";
    }
}

