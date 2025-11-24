
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.StringHolder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class FuncCallExpression
extends StringHolder
implements Expression {
    private final Expression name;
    private final Expression[] args;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        List<Expression> params = Arrays.asList(this.args);
        ArrayList<String> names = new ArrayList<String>();
        if (this.name instanceof NameExpression) {
            names.addAll(((NameExpression)this.name).getNames());
        } else {
            Collections.addAll(names, this.name.evaluate(scope, environment).asString().split("\\."));
        }
        ArrayList<MoValue> paramsParsed = new ArrayList<MoValue>(params.size());
        for (Expression param : params) {
            paramsParsed.add(param.evaluate(scope, environment));
        }
        return environment.getValue(names.iterator(), new MoParams(paramsParsed));
    }

    public FuncCallExpression(Expression name, Expression[] args) {
        this.name = name;
        this.args = args;
    }

    public Expression getName() {
        return this.name;
    }

    public Expression[] getArgs() {
        return this.args;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FuncCallExpression)) {
            return false;
        }
        FuncCallExpression other = (FuncCallExpression)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Expression this$name = this.getName();
        Expression other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        return Arrays.deepEquals(this.getArgs(), other.getArgs());
    }

    protected boolean canEqual(Object other) {
        return other instanceof FuncCallExpression;
    }

    @SuppressWarnings("unused")
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Expression $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getArgs());
        return result;
    }

    public String toString() {
        return "FuncCallExpression(name=" + this.getName() + ", args=" + Arrays.deepToString(this.getArgs()) + ")";
    }
}

