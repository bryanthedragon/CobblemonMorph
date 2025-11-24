/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u001a\u0010\t\u001a\u00020\b8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/RemoveHoldsActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Ljava/util/concurrent/CompletableFuture;", "", "play", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "", "delay", "F", "getDelay", "()F", "", "", "holds", "Ljava/util/Set;", "getHolds", "()Ljava/util/Set;", "<init>", "()V", "common"})
public final class RemoveHoldsActionEffectKeyframe
implements ActionEffectKeyframe {
    private final float delay;
    @NotNull
    private final Set<String> holds = new LinkedHashSet();

    public final float getDelay() {
        return this.delay;
    }

    @NotNull
    public final Set<String> getHolds() {
        return this.holds;
    }

    @Override
    @NotNull
    public CompletableFuture<Unit> play(@NotNull ActionEffectContext context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        CompletionStage completionStage = SchedulingFunctionsKt.delayedFuture$default(0, this.delay, true, 1, null).thenApply(arg_0 -> RemoveHoldsActionEffectKeyframe.play$lambda$0((Function1)new Function1<Unit, Unit>(this, context){
            final /* synthetic */ RemoveHoldsActionEffectKeyframe this$0;
            final /* synthetic */ ActionEffectContext $context;
            {
                this.this$0 = $receiver;
                this.$context = $context;
                super(1);
            }

            public final void invoke(Unit it) {
                if (this.this$0.getHolds().isEmpty()) {
                    this.$context.getHolds().clear();
                } else {
                    this.$context.getHolds().removeAll((Collection)this.this$0.getHolds());
                }
            }
        }, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)completionStage, (String)"override fun play(contex\u2026        }\n        }\n    }");
        return completionStage;
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

    private static final Unit play$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Unit)$tmp0.invoke(p0);
    }
}

