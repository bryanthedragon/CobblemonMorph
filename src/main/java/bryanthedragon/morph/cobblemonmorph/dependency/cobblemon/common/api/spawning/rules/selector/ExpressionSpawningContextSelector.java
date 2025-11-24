/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawningContextSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R*\u0010\t\u001a\n \b*\u0004\u0018\u00010\u00070\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/spawning/rules/selector/ExpressionSpawningContextSelector;", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawningContextSelector;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "", "selects", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Z", "Lcom/bedrockk/molang/Expression;", "kotlin.jvm.PlatformType", "expression", "Lcom/bedrockk/molang/Expression;", "getExpression", "()Lcom/bedrockk/molang/Expression;", "setExpression", "(Lcom/bedrockk/molang/Expression;)V", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getRuntime", "()Lcom/bedrockk/molang/runtime/MoLangRuntime;", "<init>", "()V", "common"})
public final class ExpressionSpawningContextSelector
implements SpawningContextSelector {
    @NotNull
    private final transient MoLangRuntime runtime = MoLangFunctions.INSTANCE.setup(new MoLangRuntime());
    private Expression expression = MoLangExtensionsKt.asExpression("true");

    @NotNull
    public final MoLangRuntime getRuntime() {
        return this.runtime;
    }

    public final Expression getExpression() {
        return this.expression;
    }

    public final void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public boolean selects(@NotNull SpawningContext ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        this.runtime.getEnvironment().setSimpleVariable("context", ctx.getOrSetupStruct());
        Expression expression = this.expression;
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"expression");
        return MoLangExtensionsKt.resolveBoolean(this.runtime, expression);
    }
}

