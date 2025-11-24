
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.StringHolder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

public final class LoopExpression
extends StringHolder
implements Expression {
    private final Expression count;
    private final Expression body;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        MoScope subScope = new MoScope();
        for (int loop = (int)this.count.evaluate(scope, environment).asDouble(); loop > 0; --loop) {
            this.body.evaluate(subScope, environment);
            if (subScope.getReturnValue() == null) continue;
            return subScope.getReturnValue();
        }
        return DoubleValue.ZERO;
    }

    public LoopExpression(Expression count, Expression body) {
        this.count = count;
        this.body = body;
    }

    public Expression getCount() {
        return this.count;
    }

    public Expression getBody() {
        return this.body;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LoopExpression)) {
            return false;
        }
        LoopExpression other = (LoopExpression)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Expression this$count = this.getCount();
        Expression other$count = other.getCount();
        if (this$count == null ? other$count != null : !this$count.equals(other$count)) {
            return false;
        }
        Expression this$body = this.getBody();
        Expression other$body = other.getBody();
        return !(this$body == null ? other$body != null : !this$body.equals(other$body));
    }

    protected boolean canEqual(Object other) {
        return other instanceof LoopExpression;
    }

    @SuppressWarnings("unused")
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Expression $count = this.getCount();
        result = result * 59 + ($count == null ? 43 : $count.hashCode());
        Expression $body = this.getBody();
        result = result * 59 + ($body == null ? 43 : $body.hashCode());
        return result;
    }

    public String toString() {
        return "LoopExpression(count=" + this.getCount() + ", body=" + this.getBody() + ")";
    }
}

