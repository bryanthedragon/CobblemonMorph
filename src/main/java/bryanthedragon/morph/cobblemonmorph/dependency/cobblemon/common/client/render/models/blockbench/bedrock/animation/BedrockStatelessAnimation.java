/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockParticleKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u0003B\u0017\u0012\u0006\u0010)\u001a\u00020\u0004\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\u0004\b*\u0010+J5\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00028\u00002\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\f\u0010\rJg\u0010\u0016\u001a\u00020\u000b2\b\u0010\u0005\u001a\u0004\u0018\u00018\u00002\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00062\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\bH\u0014\u00a2\u0006\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0019\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\"\u0010 \u001a\r\u0012\t\u0012\u00070\u001e\u00a2\u0006\u0002\b\u001f0\u001d8\u0006\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R \u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040$8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatelessAnimation;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "previousSeconds", "newSeconds", "", "applyEffects", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FF)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "setAngles", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;", "animation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;", "getAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockParticleKeyframe;", "Lkotlin/internal/NoInfer;", "particleKeyFrames", "Ljava/util/List;", "getParticleKeyFrames", "()Ljava/util/List;", "Ljava/lang/Class;", "targetFrame", "Ljava/lang/Class;", "getTargetFrame", "()Ljava/lang/Class;", "frame", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;)V", "common"})
@SourceDebugExtension(value={"SMAP\nBedrockStatelessAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockStatelessAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatelessAnimation\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,44:1\n800#2,11:45\n1#3:56\n*S KotlinDebug\n*F\n+ 1 BedrockStatelessAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatelessAnimation\n*L\n29#1:45,11\n*E\n"})
public final class BedrockStatelessAnimation<T extends Entity>
extends StatelessAnimation<T, ModelFrame> {
    @NotNull
    private final BedrockAnimation animation;
    @NotNull
    private final Class<ModelFrame> targetFrame;
    @NotNull
    private final List<BedrockParticleKeyframe> particleKeyFrames;

    /*
     * WARNING - void declaration
     */
    public BedrockStatelessAnimation(@NotNull ModelFrame frame, @NotNull BedrockAnimation animation) {
        void $this$filterIsInstanceTo$iv$iv;
        void $this$filterIsInstance$iv;
        Intrinsics.checkNotNullParameter((Object)frame, (String)"frame");
        Intrinsics.checkNotNullParameter((Object)animation, (String)"animation");
        super(frame);
        this.animation = animation;
        this.targetFrame = ModelFrame.class;
        Iterable iterable = this.animation.getEffects();
        BedrockStatelessAnimation bedrockStatelessAnimation = this;
        boolean $i$f$filterIsInstance = false;
        void var5_6 = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof BedrockParticleKeyframe)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        bedrockStatelessAnimation.particleKeyFrames = (List)destination$iv$iv;
    }

    @NotNull
    public final BedrockAnimation getAnimation() {
        return this.animation;
    }

    @Override
    @NotNull
    public Class<ModelFrame> getTargetFrame() {
        return this.targetFrame;
    }

    @NotNull
    public final List<BedrockParticleKeyframe> getParticleKeyFrames() {
        return this.particleKeyFrames;
    }

    @Override
    protected void setAngles(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @Nullable PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        Intrinsics.checkNotNullParameter(model, (String)"model");
        PoseableEntityState<T> poseableEntityState = state;
        this.animation.run(model, state, poseableEntityState != null ? poseableEntityState.getAnimationSeconds() : 0.0f, intensity);
    }

    @Override
    public void applyEffects(@NotNull T entity2, @NotNull PoseableEntityState<T> state, float previousSeconds, float newSeconds) {
        Float f;
        Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Double d = this.animation.getAnimationLength();
        double it = ((Number)d).doubleValue();
        boolean bl2 = false;
        Double d2 = !(it <= 0.0) ? d : null;
        if (d2 != null) {
            f = Float.valueOf((float)d2.doubleValue());
        } else {
            Float f2;
            Iterator bl2 = ((Iterable)this.animation.getEffects()).iterator();
            if (!bl2.hasNext()) {
                f2 = null;
            } else {
                BedrockEffectKeyframe it22 = (BedrockEffectKeyframe)bl2.next();
                boolean bl3 = false;
                float it22 = it22.getSeconds();
                while (bl2.hasNext()) {
                    BedrockEffectKeyframe it3 = (BedrockEffectKeyframe)bl2.next();
                    $i$a$-maxOfOrNull-BedrockStatelessAnimation$applyEffects$effectiveAnimationLength$2 = false;
                    float f3 = it3.getSeconds();
                    it22 = Math.max(it22, f3);
                }
                f2 = Float.valueOf(it22);
            }
            if (f2 != null) {
                Float f4 = f2;
                float it4 = ((Number)f4).floatValue();
                boolean bl4 = false;
                f = !(it4 == 0.0f) ? f4 : null;
            } else {
                f = null;
            }
        }
        Float effectiveAnimationLength = f;
        Pair pair = effectiveAnimationLength != null ? TuplesKt.to((Object)Float.valueOf(previousSeconds % effectiveAnimationLength.floatValue()), (Object)Float.valueOf(newSeconds % effectiveAnimationLength.floatValue())) : TuplesKt.to((Object)Float.valueOf(previousSeconds), (Object)Float.valueOf(newSeconds));
        float loopedPreviousSeconds = ((Number)pair.component1()).floatValue();
        float loopedNewSeconds = ((Number)pair.component2()).floatValue();
        this.animation.applyEffects(entity2, state, loopedPreviousSeconds, loopedNewSeconds);
    }
}

