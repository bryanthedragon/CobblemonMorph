/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BimanualFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u0003B%\b\u0016\u0012\u0006\u0010'\u001a\u00020&\u0012\b\b\u0002\u0010\u001f\u001a\u00020\n\u0012\b\b\u0002\u0010\u0014\u001a\u00020\n\u00a2\u0006\u0004\b(\u0010)B7\u0012\u0006\u0010'\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u001f\u001a\u00020\n\u0012\b\b\u0002\u0010\u0014\u001a\u00020\n\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u0018\u0012\b\u0010\u001d\u001a\u0004\u0018\u00010\u0018\u00a2\u0006\u0004\b(\u0010*Jg\u0010\u0012\u001a\u00020\u00112\b\u0010\u0005\u001a\u0004\u0018\u00018\u00002\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\nH\u0014\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0019\u0010\u0019\u001a\u0004\u0018\u00010\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u0019\u0010\u001d\u001a\u0004\u0018\u00010\u00188\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001a\u001a\u0004\b\u001e\u0010\u001cR\u0017\u0010\u001f\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010\u0015\u001a\u0004\b \u0010\u0017R \u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00040!8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/BimanualSwingAnimation;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "", "setAngles", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)V", "amplitudeMultiplier", "F", "getAmplitudeMultiplier", "()F", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "leftArm", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "getLeftArm", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "rightArm", "getRightArm", "swingPeriodMultiplier", "getSwingPeriodMultiplier", "Ljava/lang/Class;", "targetFrame", "Ljava/lang/Class;", "getTargetFrame", "()Ljava/lang/Class;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BimanualFrame;", "frame", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BimanualFrame;FF)V", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;FFLcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;)V", "common"})
public final class BimanualSwingAnimation<T extends Entity>
extends StatelessAnimation<T, ModelFrame> {
    private final float swingPeriodMultiplier;
    private final float amplitudeMultiplier;
    @Nullable
    private final Bone leftArm;
    @Nullable
    private final Bone rightArm;
    @NotNull
    private final Class<ModelFrame> targetFrame;

    public BimanualSwingAnimation(@NotNull ModelFrame frame, float swingPeriodMultiplier, float amplitudeMultiplier, @Nullable Bone leftArm, @Nullable Bone rightArm) {
        Intrinsics.checkNotNullParameter((Object)frame, (String)"frame");
        super(frame);
        this.swingPeriodMultiplier = swingPeriodMultiplier;
        this.amplitudeMultiplier = amplitudeMultiplier;
        this.leftArm = leftArm;
        this.rightArm = rightArm;
        this.targetFrame = ModelFrame.class;
    }

    public /* synthetic */ BimanualSwingAnimation(ModelFrame modelFrame, float f, float f2, Bone bone, Bone bone2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            f = 0.6662f;
        }
        if ((n & 4) != 0) {
            f2 = 1.0f;
        }
        this(modelFrame, f, f2, bone, bone2);
    }

    public final float getSwingPeriodMultiplier() {
        return this.swingPeriodMultiplier;
    }

    public final float getAmplitudeMultiplier() {
        return this.amplitudeMultiplier;
    }

    @Nullable
    public final Bone getLeftArm() {
        return this.leftArm;
    }

    @Nullable
    public final Bone getRightArm() {
        return this.rightArm;
    }

    public BimanualSwingAnimation(@NotNull BimanualFrame frame, float swingPeriodMultiplier, float amplitudeMultiplier) {
        Intrinsics.checkNotNullParameter((Object)frame, (String)"frame");
        this((ModelFrame)frame, swingPeriodMultiplier, amplitudeMultiplier, (Bone)frame.getLeftArm(), (Bone)frame.getRightArm());
    }

    public /* synthetic */ BimanualSwingAnimation(BimanualFrame bimanualFrame, float f, float f2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            f = 0.6662f;
        }
        if ((n & 4) != 0) {
            f2 = 1.0f;
        }
        this(bimanualFrame, f, f2);
    }

    @Override
    @NotNull
    public Class<ModelFrame> getTargetFrame() {
        return this.targetFrame;
    }

    @Override
    protected void setAngles(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @Nullable PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        block5: {
            Intrinsics.checkNotNullParameter(model, (String)"model");
            Bone bone = this.rightArm;
            if (bone != null) {
                ModelPartExtensionsKt.addRotation(bone, 1, Mth.m_14089_((float)(limbSwing * this.swingPeriodMultiplier)) * limbSwingAmount * this.amplitudeMultiplier * intensity);
            }
            Bone bone2 = this.leftArm;
            if (bone2 != null) {
                ModelPartExtensionsKt.addRotation(bone2, 1, Mth.m_14089_((float)(limbSwing * this.swingPeriodMultiplier)) * limbSwingAmount * this.amplitudeMultiplier * intensity);
            }
            Bone bone3 = this.rightArm;
            if (bone3 != null) {
                ModelPartExtensionsKt.addRotation(bone3, 2, 1.0f * (Mth.m_14089_((float)(ageInTicks * 0.09f)) * 0.05f + 0.05f) * intensity);
            }
            Bone bone4 = this.rightArm;
            if (bone4 != null) {
                ModelPartExtensionsKt.addRotation(bone4, 1, Mth.m_14031_((float)(ageInTicks * 0.067f)) * 0.05f * intensity);
            }
            Bone bone5 = this.leftArm;
            if (bone5 != null) {
                ModelPartExtensionsKt.addRotation(bone5, 2, -1.0f * (Mth.m_14089_((float)(ageInTicks * 0.09f)) * 0.05f + 0.05f) * intensity);
            }
            Bone bone6 = this.leftArm;
            if (bone6 == null) break block5;
            ModelPartExtensionsKt.addRotation(bone6, 1, -1.0f * Mth.m_14031_((float)(ageInTicks * 0.067f)) * 0.05f * intensity);
        }
    }
}

