package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import java.util.ArrayList;
import java.util.Collections;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.StringHolder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

public final class ArrayAccessExpression extends StringHolder implements Expression 
{
    private final Expression array;
    private final Expression index;

    @SuppressWarnings("rawtypes")
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        ArrayList<Object> names = new ArrayList<>();
        if (this.array instanceof NameExpression) {
            names = ((NameExpression)this.array).getNames();
        } 
        else {
            Collections.addAll(names, this.array.evaluate(scope, environment).asString().split("\\."));
        }
        names.add(String.valueOf((int)this.index.evaluate(scope, environment).asDouble()));
        return environment.getValue(names.iterator());
    }

    public void assign(MoScope scope, MoLangEnvironment environment, MoValue value2) {
        ArrayList<Object> names = new ArrayList();
        if (this.array instanceof NameExpression) {
            names = ((NameExpression)this.array).getNames();
        }
        else {
            Collections.addAll(names, this.array.evaluate(scope, environment).asString().split("\\."));
        }
        names.add(String.valueOf((int)this.index.evaluate(scope, environment).asDouble()));
        environment.setValue(names.iterator(), value2);
    }

    public ArrayAccessExpression(Expression array, Expression index) {
        this.array = array;
        this.index = index;
    }

    public Expression getArray() {
        return this.array;
    }

    public Expression getIndex() {
        return this.index;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ArrayAccessExpression)) {
            return false;
        }
        ArrayAccessExpression other = (ArrayAccessExpression)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Expression this$array = this.getArray();
        Expression other$array = other.getArray();
        if (this$array == null ? other$array != null : !this$array.equals(other$array)) {
            return false;
        }
        Expression this$index = this.getIndex();
        Expression other$index = other.getIndex();
        return !(this$index == null ? other$index != null : !this$index.equals(other$index));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ArrayAccessExpression;
    }

    @SuppressWarnings("unused")
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Expression $array = this.getArray();
        result = result * 59 + ($array == null ? 43 : $array.hashCode());
        Expression $index = this.getIndex();
        result = result * 59 + ($index == null ? 43 : $index.hashCode());
        return result;
    }

    public String toString() {
        return "ArrayAccessExpression(array=" + this.getArray() + ", index=" + this.getIndex() + ")";
    }
}

