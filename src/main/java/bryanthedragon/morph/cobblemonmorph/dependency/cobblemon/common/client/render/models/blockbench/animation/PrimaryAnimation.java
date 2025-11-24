/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u000e\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u0003BM\u0012\u0010\u0010 \u001a\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u0003\u0012\u0018\b\u0002\u0010&\u001a\u0012\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0$j\u0002`%\u0012\u000e\b\u0002\u00102\u001a\b\u0012\u0004\u0012\u00020100\u0012\b\b\u0002\u00106\u001a\u00020\u0007\u00a2\u0006\u0004\b=\u0010>J\u001f\u0010\b\u001a\u00020\u00072\u0010\u0010\u0006\u001a\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u0005\u00a2\u0006\u0004\b\b\u0010\tJe\u0010\u0016\u001a\u00020\u00072\b\u0010\n\u001a\u0004\u0018\u00018\u00002\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017R(\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u00188\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR!\u0010 \u001a\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u00038\u0006\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R2\u0010&\u001a\u0012\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0$j\u0002`%8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001a\u0010,\u001a\u00020\u000f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/R\u001d\u00102\u001a\b\u0012\u0004\u0012\u000201008\u0006\u00a2\u0006\f\n\u0004\b2\u00103\u001a\u0004\b4\u00105R\u001a\u00106\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b6\u00107\u001a\u0004\b6\u00108R\"\u00109\u001a\u00020\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b9\u0010-\u001a\u0004\b:\u0010/\"\u0004\b;\u0010<\u00a8\u0006?"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/PrimaryAnimation;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "idleAnimation", "", "prevents", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;)Z", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "run", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)Z", "Ljava/util/function/Consumer;", "", "afterAction", "Ljava/util/function/Consumer;", "getAfterAction", "()Ljava/util/function/Consumer;", "setAfterAction", "(Ljava/util/function/Consumer;)V", "animation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "getAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/wavefunction/WaveFunction;", "curve", "Lkotlin/jvm/functions/Function1;", "getCurve", "()Lkotlin/jvm/functions/Function1;", "setCurve", "(Lkotlin/jvm/functions/Function1;)V", "duration", "F", "getDuration", "()F", "", "", "excludedLabels", "Ljava/util/Set;", "getExcludedLabels", "()Ljava/util/Set;", "isTransform", "Z", "()Z", "started", "getStarted", "setStarted", "(F)V", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;Lkotlin/jvm/functions/Function1;Ljava/util/Set;Z)V", "common"})
public final class PrimaryAnimation<T extends Entity>
implements StatefulAnimation<T, ModelFrame> {
    @NotNull
    private final StatefulAnimation<T, ?> animation;
    @NotNull
    private Function1<? super Float, Float> curve;
    @NotNull
    private final Set<String> excludedLabels;
    private final boolean isTransform;
    private float started;
    private final float duration;
    @NotNull
    private Consumer<Unit> afterAction;

    public PrimaryAnimation(@NotNull StatefulAnimation<T, ?> animation, @NotNull Function1<? super Float, Float> curve2, @NotNull Set<String> excludedLabels, boolean isTransform) {
        Intrinsics.checkNotNullParameter(animation, (String)"animation");
        Intrinsics.checkNotNullParameter(curve2, (String)"curve");
        Intrinsics.checkNotNullParameter(excludedLabels, (String)"excludedLabels");
        this.animation = animation;
        this.curve = curve2;
        this.excludedLabels = excludedLabels;
        this.isTransform = isTransform;
        this.started = -1.0f;
        this.duration = this.animation.getDuration();
        this.afterAction = PrimaryAnimation::afterAction$lambda$0;
    }

    public /* synthetic */ PrimaryAnimation(StatefulAnimation statefulAnimation, Function1 function1, Set set2, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            function1 = 1.INSTANCE;
        }
        if ((n & 4) != 0) {
            set2 = SetsKt.emptySet();
        }
        if ((n & 8) != 0) {
            bl = false;
        }
        this(statefulAnimation, (Function1<? super Float, Float>)function1, set2, bl);
    }

    @NotNull
    public final StatefulAnimation<T, ?> getAnimation() {
        return this.animation;
    }

    @NotNull
    public final Function1<Float, Float> getCurve() {
        return this.curve;
    }

    public final void setCurve(@NotNull Function1<? super Float, Float> function1) {
        Intrinsics.checkNotNullParameter(function1, (String)"<set-?>");
        this.curve = function1;
    }

    @NotNull
    public final Set<String> getExcludedLabels() {
        return this.excludedLabels;
    }

    @Override
    public boolean isTransform() {
        return this.isTransform;
    }

    public final float getStarted() {
        return this.started;
    }

    public final void setStarted(float f) {
        this.started = f;
    }

    @Override
    public float getDuration() {
        return this.duration;
    }

    @NotNull
    public final Consumer<Unit> getAfterAction() {
        return this.afterAction;
    }

    public final void setAfterAction(@NotNull Consumer<Unit> consumer) {
        Intrinsics.checkNotNullParameter(consumer, (String)"<set-?>");
        this.afterAction = consumer;
    }

    @Override
    public boolean run(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @NotNull PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        Intrinsics.checkNotNullParameter(model, (String)"model");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        return this.animation.run(entity2, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, intensity);
    }

    public final boolean prevents(@NotNull StatelessAnimation<T, ?> idleAnimation) {
        Intrinsics.checkNotNullParameter(idleAnimation, (String)"idleAnimation");
        return CollectionsKt.intersect((Iterable)idleAnimation.getLabels(), (Iterable)this.excludedLabels).isEmpty() && !this.excludedLabels.contains("all");
    }

    @Override
    public void applyEffects(@NotNull T entity2, @NotNull PoseableEntityState<T> state, float previousSeconds, float newSeconds) {
        StatefulAnimation.DefaultImpls.applyEffects(this, entity2, state, previousSeconds, newSeconds);
    }

    private static final void afterAction$lambda$0(Unit it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
    }
}

