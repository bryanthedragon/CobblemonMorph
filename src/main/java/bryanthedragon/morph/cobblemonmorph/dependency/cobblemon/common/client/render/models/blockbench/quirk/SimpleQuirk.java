/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.PrimaryAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirkData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00040\u0003B\u0089\u0001\u0012\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020 0\u001f\u0012)\b\u0002\u0010\u0018\u001a#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0005\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00170\u000e\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u001a\u00127\u0010\u0013\u001a3\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0005\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0006\u0012\u0014\u0012\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u00120\u00110\u000e\u00a2\u0006\u0004\b#\u0010$J+\u0010\t\u001a\u00020\b2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u0015\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ+\u0010\r\u001a\u00020\b2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H\u0014\u00a2\u0006\u0004\b\r\u0010\nRH\u0010\u0013\u001a3\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0005\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0006\u0012\u0014\u0012\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u00120\u00110\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R8\u0010\u0018\u001a#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0005\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00170\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0014\u001a\u0004\b\u0019\u0010\u0016R\u0017\u0010\u001b\u001a\u00020\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR \u0010!\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020 0\u001f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\"\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/SimpleQuirk;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/SimpleQuirkData;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "data", "", "applyAnimations", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/SimpleQuirkData;)V", "createData", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/SimpleQuirkData;", "tick", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "animations", "Lkotlin/jvm/functions/Function1;", "getAnimations", "()Lkotlin/jvm/functions/Function1;", "", "condition", "getCondition", "Lkotlin/ranges/IntRange;", "loopTimes", "Lkotlin/ranges/IntRange;", "getLoopTimes", "()Lkotlin/ranges/IntRange;", "Lkotlin/Pair;", "", "secondsBetweenOccurrences", "Lkotlin/Pair;", "<init>", "(Lkotlin/Pair;Lkotlin/jvm/functions/Function1;Lkotlin/ranges/IntRange;Lkotlin/jvm/functions/Function1;)V", "common"})
@SourceDebugExtension(value={"SMAP\nSimpleQuirk.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SimpleQuirk.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/quirk/SimpleQuirk\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,62:1\n3190#2,10:63\n*S KotlinDebug\n*F\n+ 1 SimpleQuirk.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/quirk/SimpleQuirk\n*L\n54#1:63,10\n*E\n"})
public final class SimpleQuirk<T extends Entity>
extends ModelQuirk<T, SimpleQuirkData<T>> {
    @NotNull
    private final Pair<Float, Float> secondsBetweenOccurrences;
    @NotNull
    private final Function1<PoseableEntityState<T>, Boolean> condition;
    @NotNull
    private final IntRange loopTimes;
    @NotNull
    private final Function1<PoseableEntityState<T>, Iterable<StatefulAnimation<T, ?>>> animations;

    public SimpleQuirk(@NotNull Pair<Float, Float> secondsBetweenOccurrences, @NotNull Function1<? super PoseableEntityState<T>, Boolean> condition2, @NotNull IntRange loopTimes, @NotNull Function1<? super PoseableEntityState<T>, ? extends Iterable<? extends StatefulAnimation<T, ?>>> animations2) {
        Intrinsics.checkNotNullParameter(secondsBetweenOccurrences, (String)"secondsBetweenOccurrences");
        Intrinsics.checkNotNullParameter(condition2, (String)"condition");
        Intrinsics.checkNotNullParameter((Object)loopTimes, (String)"loopTimes");
        Intrinsics.checkNotNullParameter(animations2, (String)"animations");
        this.secondsBetweenOccurrences = secondsBetweenOccurrences;
        this.condition = condition2;
        this.loopTimes = loopTimes;
        this.animations = animations2;
    }

    public /* synthetic */ SimpleQuirk(Pair pair, Function1 function1, IntRange intRange, Function1 function12, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            function1 = 1.INSTANCE;
        }
        if ((n & 4) != 0) {
            intRange = new IntRange(1, 1);
        }
        this((Pair<Float, Float>)pair, function1, intRange, function12);
    }

    @NotNull
    public final Function1<PoseableEntityState<T>, Boolean> getCondition() {
        return this.condition;
    }

    @NotNull
    public final IntRange getLoopTimes() {
        return this.loopTimes;
    }

    @NotNull
    public final Function1<PoseableEntityState<T>, Iterable<StatefulAnimation<T, ?>>> getAnimations() {
        return this.animations;
    }

    @Override
    @NotNull
    public SimpleQuirkData<T> createData() {
        return new SimpleQuirkData();
    }

    @Override
    protected void tick(@NotNull PoseableEntityState<T> state, @NotNull SimpleQuirkData<T> data) {
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Intrinsics.checkNotNullParameter(data, (String)"data");
        if (!((Collection)data.getAnimations()).isEmpty() || data.getPrimaryAnimation() != null) {
            return;
        }
        if (!((Boolean)this.condition.invoke(state)).booleanValue()) {
            return;
        }
        if (data.getRemainingLoops() > 0) {
            this.applyAnimations(state, data);
            int n = data.getRemainingLoops();
            data.setRemainingLoops(n + -1);
        }
        if (data.getRemainingLoops() == 0) {
            if (data.getNextOccurrenceSeconds() > 0.0f) {
                if (data.getNextOccurrenceSeconds() <= state.getAnimationSeconds()) {
                    data.setRemainingLoops(RangesKt.random((IntRange)this.loopTimes, (Random)((Random)Random.Default)) - 1);
                    this.applyAnimations(state, data);
                    data.setNextOccurrenceSeconds(-1.0f);
                }
            } else {
                data.setNextOccurrenceSeconds(state.getAnimationSeconds() + SimpleMathExtensionsKt.random(this.secondsBetweenOccurrences));
            }
        }
    }

    private final void applyAnimations(PoseableEntityState<T> state, SimpleQuirkData<T> data) {
        Iterable $this$partition$iv = (Iterable)this.animations.invoke(state);
        boolean $i$f$partition = false;
        ArrayList first$iv = new ArrayList();
        ArrayList second$iv = new ArrayList();
        for (Object element$iv : $this$partition$iv) {
            StatefulAnimation it = (StatefulAnimation)element$iv;
            boolean bl = false;
            if (it instanceof PrimaryAnimation) {
                first$iv.add(element$iv);
                continue;
            }
            second$iv.add(element$iv);
        }
        Pair pair = new Pair(first$iv, second$iv);
        List primary = (List)pair.component1();
        List stateful = (List)pair.component2();
        data.getAnimations().addAll(stateful);
        if (!((Collection)primary).isEmpty()) {
            Object object = CollectionsKt.first((List)primary);
            Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.PrimaryAnimation<T of bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk>");
            PrimaryAnimation primaryAnimation2 = (PrimaryAnimation)object;
            data.setPrimaryAnimation(primaryAnimation2);
            state.addPrimaryAnimation(primaryAnimation2);
        }
    }
}

