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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ConditionalActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0017\u0010\t\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/PauseActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ConditionalActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Ljava/util/concurrent/CompletableFuture;", "", "playWhenTrue", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "pause", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getPause", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "<init>", "()V", "common"})
public final class PauseActionEffectKeyframe
extends ConditionalActionEffectKeyframe {
    @NotNull
    private final ExpressionLike pause = MoLangExtensionsKt.asExpressionLike("1");

    @NotNull
    public final ExpressionLike getPause() {
        return this.pause;
    }

    @Override
    @NotNull
    public CompletableFuture<Unit> playWhenTrue(@NotNull ActionEffectContext context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        return SchedulingFunctionsKt.delayedFuture$default(0, this.pause.resolveFloat(context.getRuntime()), true, 1, null);
    }
}

