
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.StringHolder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

public final class BooleanExpression
extends StringHolder
implements Expression {
    private final boolean value;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        return new DoubleValue(this.value);
    }

    public BooleanExpression(boolean value2) {
        this.value = value2;
    }

    public boolean isValue() {
        return this.value;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BooleanExpression)) {
            return false;
        }
        BooleanExpression other = (BooleanExpression)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return this.isValue() == other.isValue();
    }

    protected boolean canEqual(Object other) {
        return other instanceof BooleanExpression;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isValue() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "BooleanExpression(value=" + this.isValue() + ")";
    }
}

