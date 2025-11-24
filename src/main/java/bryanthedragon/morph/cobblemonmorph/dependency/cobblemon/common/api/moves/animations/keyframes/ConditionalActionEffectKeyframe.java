/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001d\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\b\u0010\u0007R\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ConditionalActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Ljava/util/concurrent/CompletableFuture;", "", "play", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "playWhenTrue", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "condition", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getCondition", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "setCondition", "(Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)V", "<init>", "()V", "common"})
public abstract class ConditionalActionEffectKeyframe
implements ActionEffectKeyframe {
    @NotNull
    private ExpressionLike condition = MoLangExtensionsKt.asExpressionLike("true");

    @NotNull
    public final ExpressionLike getCondition() {
        return this.condition;
    }

    public final void setCondition(@NotNull ExpressionLike expressionLike) {
        Intrinsics.checkNotNullParameter((Object)expressionLike, (String)"<set-?>");
        this.condition = expressionLike;
    }

    @Override
    @NotNull
    public CompletableFuture<Unit> play(@NotNull ActionEffectContext context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        return this.condition.resolveBoolean(context.getRuntime()) ? this.playWhenTrue(context) : this.skip();
    }

    @NotNull
    public abstract CompletableFuture<Unit> playWhenTrue(@NotNull ActionEffectContext var1);

    @Override
    public void interrupt(@NotNull ActionEffectContext context) {
        ActionEffectKeyframe.DefaultImpls.interrupt(this, context);
    }

    @Override
    @NotNull
    public CompletableFuture<Unit> skip() {
        return ActionEffectKeyframe.DefaultImpls.skip(this);
    }
}

