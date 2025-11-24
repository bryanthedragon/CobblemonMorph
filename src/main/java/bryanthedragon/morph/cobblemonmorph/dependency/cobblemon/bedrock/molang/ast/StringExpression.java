
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.StringHolder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;

public final class StringExpression
extends StringHolder
implements Expression {
    private final String string;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        return new StringValue(this.string);
    }

    public StringExpression(String string) {
        this.string = string;
    }

    public String getString() {
        return this.string;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StringExpression)) {
            return false;
        }
        StringExpression other = (StringExpression)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$string = this.getString();
        String other$string = other.getString();
        return !(this$string == null ? other$string != null : !this$string.equals(other$string));
    }

    protected boolean canEqual(Object other) {
        return other instanceof StringExpression;
    }

    @SuppressWarnings("unused")
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $string = this.getString();
        result = result * 59 + ($string == null ? 43 : $string.hashCode());
        return result;
    }

    public String toString() {
        return "StringExpression(string=" + this.getString() + ")";
    }
}

