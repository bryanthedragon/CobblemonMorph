/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\b\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB!\u0012\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u0013\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0018\u0010\u0019J1\u0010\n\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe;", "iterator", "Ljava/util/concurrent/CompletableFuture;", "", "finalFuture", "chainKeyframes", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;Ljava/util/Iterator;Ljava/util/concurrent/CompletableFuture;)V", "run", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "Lcom/bedrockk/molang/Expression;", "condition", "Lcom/bedrockk/molang/Expression;", "getCondition", "()Lcom/bedrockk/molang/Expression;", "", "timeline", "Ljava/util/List;", "getTimeline", "()Ljava/util/List;", "<init>", "(Ljava/util/List;Lcom/bedrockk/molang/Expression;)V", "Companion", "common"})
public final class ActionEffectTimeline {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final List<ActionEffectKeyframe> timeline;
    @NotNull
    private final Expression condition;
    @NotNull
    private static final ActionEffectTimeline NONE = new ActionEffectTimeline(null, null, 3, null);

    public ActionEffectTimeline(@NotNull List<? extends ActionEffectKeyframe> timeline, @NotNull Expression condition2) {
        Intrinsics.checkNotNullParameter(timeline, (String)"timeline");
        Intrinsics.checkNotNullParameter((Object)condition2, (String)"condition");
        this.timeline = timeline;
        this.condition = condition2;
    }

    public /* synthetic */ ActionEffectTimeline(List list, Expression expression, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            list = new ArrayList();
        }
        if ((n & 2) != 0) {
            Expression expression2 = MoLangExtensionsKt.asExpression("true");
            Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"true\".asExpression()");
            expression = expression2;
        }
        this(list, expression);
    }

    @NotNull
    public final List<ActionEffectKeyframe> getTimeline() {
        return this.timeline;
    }

    @NotNull
    public final Expression getCondition() {
        return this.condition;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final CompletableFuture<Unit> run(@NotNull ActionEffectContext context) {
        CompletableFuture<Unit> completableFuture;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (this.timeline.isEmpty() || !MoLangExtensionsKt.resolveBoolean(context.getRuntime(), this.condition)) {
            completableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
        } else {
            void var2_2;
            CompletableFuture<Unit> finalFuture = new CompletableFuture<Unit>();
            this.chainKeyframes(context, CollectionsKt.toList((Iterable)this.timeline).iterator(), finalFuture);
            completableFuture = var2_2;
        }
        CompletableFuture completableFuture2 = completableFuture.exceptionallyCompose(ActionEffectTimeline::run$lambda$0);
        Intrinsics.checkNotNullExpressionValue((Object)completableFuture2, (String)"if (timeline.isEmpty() |\u2026tedFuture(Unit)\n        }");
        return completableFuture2;
    }

    public final void chainKeyframes(@NotNull ActionEffectContext context, @NotNull Iterator<? extends ActionEffectKeyframe> iterator, @NotNull CompletableFuture<Unit> finalFuture) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter(iterator, (String)"iterator");
        Intrinsics.checkNotNullParameter(finalFuture, (String)"finalFuture");
        if (!iterator.hasNext()) {
            finalFuture.complete(Unit.INSTANCE);
        } else {
            ActionEffectKeyframe keyframe = iterator.next();
            context.getCurrentKeyframes().add(keyframe);
            ((CompletableFuture)((CompletableFuture)keyframe.play(context).thenRun(() -> ActionEffectTimeline.chainKeyframes$lambda$1(context, keyframe))).thenApply(arg_0 -> ActionEffectTimeline.chainKeyframes$lambda$2((Function1)new Function1<Void, Unit>(this, context, iterator, finalFuture){
                final /* synthetic */ ActionEffectTimeline this$0;
                final /* synthetic */ ActionEffectContext $context;
                final /* synthetic */ Iterator<ActionEffectKeyframe> $iterator;
                final /* synthetic */ CompletableFuture<Unit> $finalFuture;
                {
                    this.this$0 = $receiver;
                    this.$context = $context;
                    this.$iterator = $iterator;
                    this.$finalFuture = $finalFuture;
                    super(1);
                }

                public final void invoke(Void it) {
                    this.this$0.chainKeyframes(this.$context, this.$iterator, this.$finalFuture);
                }
            }, arg_0))).exceptionally(arg_0 -> ActionEffectTimeline.chainKeyframes$lambda$3(finalFuture, arg_0));
        }
    }

    private static final CompletionStage run$lambda$0(Throwable it) {
        it.printStackTrace();
        return CompletableFuture.completedFuture(Unit.INSTANCE);
    }

    private static final void chainKeyframes$lambda$1(ActionEffectContext $context, ActionEffectKeyframe $keyframe) {
        Intrinsics.checkNotNullParameter((Object)$context, (String)"$context");
        Intrinsics.checkNotNullParameter((Object)$keyframe, (String)"$keyframe");
        $context.getCurrentKeyframes().remove($keyframe);
    }

    private static final Unit chainKeyframes$lambda$2(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Unit)$tmp0.invoke(p0);
    }

    private static final Unit chainKeyframes$lambda$3(CompletableFuture $finalFuture, Throwable it) {
        Intrinsics.checkNotNullParameter((Object)$finalFuture, (String)"$finalFuture");
        $finalFuture.completeExceptionally(it);
        return Unit.INSTANCE;
    }

    public ActionEffectTimeline() {
        this(null, null, 3, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline$Companion;", "", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "NONE", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "getNONE", "()Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ActionEffectTimeline getNONE() {
            return NONE;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

