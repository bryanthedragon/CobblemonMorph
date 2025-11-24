/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0015\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u0003B\u000f\u0012\u0006\u0010\"\u001a\u00020!\u00a2\u0006\u0004\b4\u00105JB\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002-\u0010\u000b\u001a)\u0012\u0013\u0012\u00118\u0000\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t\u0012\u0004\u0012\u00020\n0\u0005\u00a2\u0006\u0004\b\f\u0010\rJ5\u0010\u0012\u001a\u00020\n2\u0006\u0010\b\u001a\u00028\u00002\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013Je\u0010\u001d\u001a\u00020\u001c2\b\u0010\b\u001a\u0004\u0018\u00018\u00002\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eR.\u0010\u001f\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t\u0012\u0004\u0012\u00020\n0\u00058\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u001f\u0010 R\u0017\u0010\"\u001a\u00020!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\u000f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)R\u0014\u0010*\u001a\u00020\u001c8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b*\u0010+R\"\u0010,\u001a\u00020\u001c8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b,\u0010-\u001a\u0004\b,\u0010+\"\u0004\b.\u0010/R\"\u00100\u001a\u00020\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b0\u0010'\u001a\u0004\b1\u0010)\"\u0004\b2\u00103\u00a8\u00066"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "", "action", "andThen", "(Lkotlin/jvm/functions/Function2;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "state", "", "previousSeconds", "newSeconds", "applyEffects", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FF)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "", "run", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)Z", "afterAction", "Lkotlin/jvm/functions/Function2;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;", "animation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;", "getAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;", "duration", "F", "getDuration", "()F", "isTransform", "()Z", "isTransformAnimation", "Z", "setTransformAnimation", "(Z)V", "startedSeconds", "getStartedSeconds", "setStartedSeconds", "(F)V", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;)V", "common"})
public class BedrockStatefulAnimation<T extends Entity>
implements StatefulAnimation<T, ModelFrame> {
    @NotNull
    private final BedrockAnimation animation;
    private float startedSeconds;
    private boolean isTransformAnimation;
    private final float duration;
    @NotNull
    private Function2<? super T, ? super PoseableEntityState<T>, Unit> afterAction;

    public BedrockStatefulAnimation(@NotNull BedrockAnimation animation) {
        Intrinsics.checkNotNullParameter((Object)animation, (String)"animation");
        this.animation = animation;
        this.startedSeconds = -1.0f;
        this.duration = (float)this.animation.getAnimationLength();
        this.afterAction = afterAction.1.INSTANCE;
    }

    @NotNull
    public final BedrockAnimation getAnimation() {
        return this.animation;
    }

    public final float getStartedSeconds() {
        return this.startedSeconds;
    }

    public final void setStartedSeconds(float f) {
        this.startedSeconds = f;
    }

    public final boolean isTransformAnimation() {
        return this.isTransformAnimation;
    }

    public final void setTransformAnimation(boolean bl) {
        this.isTransformAnimation = bl;
    }

    @Override
    public float getDuration() {
        return this.duration;
    }

    @Override
    public boolean isTransform() {
        return this.isTransformAnimation;
    }

    @NotNull
    public final BedrockStatefulAnimation<T> andThen(@NotNull Function2<? super T, ? super PoseableEntityState<T>, Unit> action2) {
        BedrockStatefulAnimation bedrockStatefulAnimation;
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        BedrockStatefulAnimation it = bedrockStatefulAnimation = this;
        boolean bl = false;
        it.afterAction = action2;
        return bedrockStatefulAnimation;
    }

    @Override
    public boolean run(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @NotNull PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        boolean bl;
        Intrinsics.checkNotNullParameter(model, (String)"model");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        if (this.startedSeconds == -1.0f) {
            this.startedSeconds = state.getAnimationSeconds();
        }
        boolean it = bl = this.animation.run(model, state, state.getAnimationSeconds() - this.startedSeconds, intensity);
        boolean bl2 = false;
        if (!it && entity2 != null) {
            this.afterAction.invoke(entity2, state);
        }
        return bl;
    }

    @Override
    public void applyEffects(@NotNull T entity2, @NotNull PoseableEntityState<T> state, float previousSeconds, float newSeconds) {
        Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        float previousSecondsOffset = previousSeconds - this.startedSeconds;
        float currentSecondsOffset = newSeconds - this.startedSeconds;
        this.animation.applyEffects(entity2, state, previousSecondsOffset, currentSecondsOffset);
    }
}

