/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.ClosedFloatingPointRange
 *  kotlin.ranges.RangesKt
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0015\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u0003BO\u0012\u0010\u0010\u001c\u001a\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u0017\u0012\u0010\u0010\u0018\u001a\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u0017\u0012\b\b\u0002\u0010)\u001a\u00020(\u0012\u0018\b\u0002\u0010 \u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\u001ej\u0002`\u001f\u00a2\u0006\u0004\b;\u0010<J\u001b\u0010\b\u001a\u00020\u00072\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u00a2\u0006\u0004\b\b\u0010\tJe\u0010\u0015\u001a\u00020\u00142\b\u0010\n\u001a\u0004\u0018\u00018\u00002\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016R!\u0010\u0018\u001a\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u00178\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR!\u0010\u001c\u001a\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u00178\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0019\u001a\u0004\b\u001d\u0010\u001bR'\u0010 \u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\u001ej\u0002`\u001f8\u0006\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'R\u0017\u0010)\u001a\u00020(8\u0006\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R\"\u0010-\u001a\u00020\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b-\u0010%\u001a\u0004\b.\u0010'\"\u0004\b/\u00100R\"\u00101\u001a\u00020\u00148\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b1\u00102\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u001a\u00107\u001a\u00020\u00148\u0016X\u0096D\u00a2\u0006\f\n\u0004\b7\u00102\u001a\u0004\b7\u00104R\"\u00108\u001a\u00020\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b8\u0010%\u001a\u0004\b9\u0010'\"\u0004\b:\u00100\u00a8\u0006="}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/PoseTransitionAnimation;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "initialize", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)V", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "", "run", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)Z", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "afterPose", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getAfterPose", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "beforePose", "getBeforePose", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/wavefunction/WaveFunction;", "curve", "Lkotlin/jvm/functions/Function1;", "getCurve", "()Lkotlin/jvm/functions/Function1;", "duration", "F", "getDuration", "()F", "", "durationTicks", "I", "getDurationTicks", "()I", "endTime", "getEndTime", "setEndTime", "(F)V", "initialized", "Z", "getInitialized", "()Z", "setInitialized", "(Z)V", "isTransform", "startTime", "getStartTime", "setStartTime", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;ILkotlin/jvm/functions/Function1;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPoseTransitionAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PoseTransitionAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/animation/PoseTransitionAnimation\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,82:1\n13579#2,2:83\n13579#2,2:85\n*S KotlinDebug\n*F\n+ 1 PoseTransitionAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/animation/PoseTransitionAnimation\n*L\n71#1:83,2\n76#1:85,2\n*E\n"})
public final class PoseTransitionAnimation<T extends Entity>
implements StatefulAnimation<T, ModelFrame> {
    @NotNull
    private final Pose<T, ?> beforePose;
    @NotNull
    private final Pose<T, ?> afterPose;
    private final int durationTicks;
    @NotNull
    private final Function1<Float, Float> curve;
    private final boolean isTransform;
    private final float duration;
    private boolean initialized;
    private float startTime;
    private float endTime;

    public PoseTransitionAnimation(@NotNull Pose<T, ?> beforePose, @NotNull Pose<T, ?> afterPose, int durationTicks, @NotNull Function1<? super Float, Float> curve2) {
        Intrinsics.checkNotNullParameter(beforePose, (String)"beforePose");
        Intrinsics.checkNotNullParameter(afterPose, (String)"afterPose");
        Intrinsics.checkNotNullParameter(curve2, (String)"curve");
        this.beforePose = beforePose;
        this.afterPose = afterPose;
        this.durationTicks = durationTicks;
        this.curve = curve2;
        this.isTransform = true;
        this.duration = (float)this.durationTicks / 20.0f;
    }

    public /* synthetic */ PoseTransitionAnimation(Pose pose, Pose pose2, int n, Function1 function1, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 4) != 0) {
            n = 20;
        }
        if ((n2 & 8) != 0) {
            function1 = WaveFunctionKt.sineFunction(0.5f, 2.0f, 0.5f, 0.5f);
        }
        this(pose, pose2, n, function1);
    }

    @NotNull
    public final Pose<T, ?> getBeforePose() {
        return this.beforePose;
    }

    @NotNull
    public final Pose<T, ?> getAfterPose() {
        return this.afterPose;
    }

    public final int getDurationTicks() {
        return this.durationTicks;
    }

    @NotNull
    public final Function1<Float, Float> getCurve() {
        return this.curve;
    }

    @Override
    public boolean isTransform() {
        return this.isTransform;
    }

    @Override
    public float getDuration() {
        return this.duration;
    }

    public final boolean getInitialized() {
        return this.initialized;
    }

    public final void setInitialized(boolean bl) {
        this.initialized = bl;
    }

    public final float getStartTime() {
        return this.startTime;
    }

    public final void setStartTime(float f) {
        this.startTime = f;
    }

    public final float getEndTime() {
        return this.endTime;
    }

    public final void setEndTime(float f) {
        this.endTime = f;
    }

    public final void initialize(@NotNull PoseableEntityState<T> state) {
        Intrinsics.checkNotNullParameter(state, (String)"state");
        this.startTime = state.getAnimationSeconds();
        this.endTime = this.startTime + (float)this.durationTicks / 20.0f;
        this.initialized = true;
    }

    @Override
    public boolean run(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @NotNull PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        StatelessAnimation<T, ?> it;
        StatelessAnimation<T, ?> element$iv;
        int n;
        Intrinsics.checkNotNullParameter(model, (String)"model");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        if (!this.initialized) {
            this.initialize(state);
        }
        float now = state.getAnimationSeconds();
        float durationSeconds = this.endTime - this.startTime;
        float passedSeconds = now - this.startTime;
        float ratio = Float.min(passedSeconds / durationSeconds, 1.0f);
        float newIntensity = ((Number)((Object)RangesKt.coerceIn((Comparable)((Comparable)this.curve.invoke((Object)Float.valueOf(ratio))), (ClosedFloatingPointRange)RangesKt.rangeTo((float)0.0f, (float)1.0f)))).floatValue();
        float oldIntensity = 1.0f - newIntensity;
        model.setDefault();
        model.applyPose(this.beforePose.getPoseName(), oldIntensity);
        StatelessAnimation<T, ?>[] $this$forEach$iv = this.beforePose.getIdleAnimations();
        boolean $i$f$forEach = false;
        int n2 = $this$forEach$iv.length;
        for (n = 0; n < n2; ++n) {
            it = element$iv = $this$forEach$iv[n];
            boolean bl = false;
            it.apply(entity2, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, oldIntensity);
        }
        model.applyPose(this.afterPose.getPoseName(), newIntensity);
        $this$forEach$iv = this.afterPose.getIdleAnimations();
        $i$f$forEach = false;
        n2 = $this$forEach$iv.length;
        for (n = 0; n < n2; ++n) {
            it = element$iv = $this$forEach$iv[n];
            boolean bl = false;
            it.apply(entity2, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, newIntensity);
        }
        return ratio < 1.0f;
    }

    @Override
    public void applyEffects(@NotNull T entity2, @NotNull PoseableEntityState<T> state, float previousSeconds, float newSeconds) {
        StatefulAnimation.DefaultImpls.applyEffects(this, entity2, state, previousSeconds, newSeconds);
    }
}

