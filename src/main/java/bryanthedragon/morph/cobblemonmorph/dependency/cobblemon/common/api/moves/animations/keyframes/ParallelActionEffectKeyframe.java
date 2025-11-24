/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ConditionalActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ParallelActionEffectKeyframe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R(\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ParallelActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ConditionalActionEffectKeyframe;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;", "context", "Ljava/util/concurrent/CompletableFuture;", "", "playWhenTrue", "(Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectContext;)Ljava/util/concurrent/CompletableFuture;", "", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe;", "keyframes", "Ljava/util/List;", "getKeyframes", "()Ljava/util/List;", "setKeyframes", "(Ljava/util/List;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nParallelActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParallelActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/ParallelActionEffectKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,25:1\n1549#2:26\n1620#2,3:27\n37#3,2:30\n*S KotlinDebug\n*F\n+ 1 ParallelActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/ParallelActionEffectKeyframe\n*L\n19#1:26\n19#1:27,3\n22#1:30,2\n*E\n"})
public final class ParallelActionEffectKeyframe
extends ConditionalActionEffectKeyframe {
    @NotNull
    private List<ActionEffectKeyframe> keyframes = new ArrayList();

    @NotNull
    public final List<ActionEffectKeyframe> getKeyframes() {
        return this.keyframes;
    }

    public final void setKeyframes(@NotNull List<ActionEffectKeyframe> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.keyframes = list;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public CompletableFuture<Unit> playWhenTrue(@NotNull ActionEffectContext context) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Iterable $this$map$iv = this.keyframes;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ActionEffectKeyframe actionEffectKeyframe = (ActionEffectKeyframe)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            context.getCurrentKeyframes().add((ActionEffectKeyframe)it);
            collection.add(it.play(context).thenRun(() -> ParallelActionEffectKeyframe.playWhenTrue$lambda$1$lambda$0(context, (ActionEffectKeyframe)it)));
        }
        Collection $this$toTypedArray$iv = (List)destination$iv$iv;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        CompletableFuture[] completableFutureArray = thisCollection$iv.toArray(new CompletableFuture[0]);
        CompletionStage completionStage = CompletableFuture.allOf(Arrays.copyOf(completableFutureArray, completableFutureArray.length)).thenApply(arg_0 -> ParallelActionEffectKeyframe.playWhenTrue$lambda$2(playWhenTrue.2.INSTANCE, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)completionStage, (String)"allOf(\n            *keyf\u2026()\n        ).thenApply {}");
        return completionStage;
    }

    private static final void playWhenTrue$lambda$1$lambda$0(ActionEffectContext $context, ActionEffectKeyframe $it) {
        Intrinsics.checkNotNullParameter((Object)$context, (String)"$context");
        Intrinsics.checkNotNullParameter((Object)$it, (String)"$it");
        $context.getCurrentKeyframes().remove($it);
    }

    private static final Unit playWhenTrue$lambda$2(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Unit)$tmp0.invoke(p0);
    }
}

