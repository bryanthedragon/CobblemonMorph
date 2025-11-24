
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.StringHolder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

import java.util.ArrayList;

public final class NameExpression
extends StringHolder
implements Expression {
    private final ArrayList<String> names;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        return environment.getValue(this.getNames().iterator());
    }

    @Override
    public void assign(MoScope scope, MoLangEnvironment environment, MoValue value2) {
        environment.setValue(this.getNames().iterator(), value2);
    }

    public NameExpression(ArrayList<String> names) {
        this.names = names;
    }

    public ArrayList<String> getNames() {
        return this.names;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NameExpression)) {
            return false;
        }
        NameExpression other = (NameExpression)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ArrayList<String> this$names = this.getNames();
        ArrayList<String> other$names = other.getNames();
        return !(this$names == null ? other$names != null : !((Object)this$names).equals(other$names));
    }

    protected boolean canEqual(Object other) {
        return other instanceof NameExpression;
    }

    @SuppressWarnings("unused")
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ArrayList<String> $names = this.getNames();
        result = result * 59 + ($names == null ? 43 : ((Object)$names).hashCode());
        return result;
    }

    public String toString() {
        return "NameExpression(names=" + this.getNames() + ")";
    }
}

