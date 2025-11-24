/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\t\u0018\u00002\u00020\u0001B1\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\r\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\r\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0017\u0010\t\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\r8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u000f\u001a\u0004\b\u0013\u0010\u0011\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ForkActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Ljava/util/concurrent/CompletableFuture;", "", "play", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "condition", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getCondition", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "", "ifFalse", "Ljava/util/List;", "getIfFalse", "()Ljava/util/List;", "ifTrue", "getIfTrue", "<init>", "(Lcom/cobblemon/mod/common/api/molang/ExpressionLike;Ljava/util/List;Ljava/util/List;)V", "common"})
public final class ForkActionEffectKeyframe
implements ActionEffectKeyframe {
    @NotNull
    private final ExpressionLike condition;
    @NotNull
    private final List<ActionEffectKeyframe> ifTrue;
    @NotNull
    private final List<ActionEffectKeyframe> ifFalse;

    public ForkActionEffectKeyframe(@NotNull ExpressionLike condition2, @NotNull List<? extends ActionEffectKeyframe> ifTrue, @NotNull List<? extends ActionEffectKeyframe> ifFalse) {
        Intrinsics.checkNotNullParameter((Object)condition2, (String)"condition");
        Intrinsics.checkNotNullParameter(ifTrue, (String)"ifTrue");
        Intrinsics.checkNotNullParameter(ifFalse, (String)"ifFalse");
        this.condition = condition2;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    public /* synthetic */ ForkActionEffectKeyframe(ExpressionLike expressionLike, List list, List list2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            expressionLike = MoLangExtensionsKt.asExpressionLike("true");
        }
        if ((n & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        if ((n & 4) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        this(expressionLike, list, list2);
    }

    @NotNull
    public final ExpressionLike getCondition() {
        return this.condition;
    }

    @NotNull
    public final List<ActionEffectKeyframe> getIfTrue() {
        return this.ifTrue;
    }

    @NotNull
    public final List<ActionEffectKeyframe> getIfFalse() {
        return this.ifFalse;
    }

    @Override
    @NotNull
    public CompletableFuture<Unit> play(@NotNull ActionEffectContext context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        CompletableFuture<Unit> future2 = new CompletableFuture<Unit>();
        if (!this.condition.resolveBoolean(context.getRuntime())) {
            context.getActionEffect().chainKeyframes(context, CollectionsKt.toList((Iterable)this.ifFalse).iterator(), future2);
        } else {
            context.getActionEffect().chainKeyframes(context, CollectionsKt.toList((Iterable)this.ifTrue).iterator(), future2);
        }
        return future2;
    }

    @Override
    public void interrupt(@NotNull ActionEffectContext context) {
        ActionEffectKeyframe.DefaultImpls.interrupt(this, context);
    }

    @Override
    @NotNull
    public CompletableFuture<Unit> skip() {
        return ActionEffectKeyframe.DefaultImpls.skip(this);
    }

    public ForkActionEffectKeyframe() {
        this(null, null, null, 7, null);
    }
}

