
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.StringHolder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

public final class ThisExpression
extends StringHolder
implements Expression {
    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        return environment;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ThisExpression)) {
            return false;
        }
        ThisExpression other = (ThisExpression)o;
        return other.canEqual(this);
    }

    protected boolean canEqual(Object other) {
        return other instanceof ThisExpression;
    }

    @SuppressWarnings("unused")
    public int hashCode() {
        boolean result = true;
        return 1;
    }

    public String toString() {
        return "ThisExpression()";
    }
}

