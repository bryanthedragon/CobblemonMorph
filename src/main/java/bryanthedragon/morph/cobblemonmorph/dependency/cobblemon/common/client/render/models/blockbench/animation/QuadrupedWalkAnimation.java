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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.QuadrupedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u0003B%\b\u0016\u0012\u0006\u0010+\u001a\u00020*\u0012\b\b\u0002\u0010#\u001a\u00020\n\u0012\b\b\u0002\u0010\u0014\u001a\u00020\n\u00a2\u0006\u0004\b,\u0010-BK\u0012\u0006\u0010+\u001a\u00020\u0004\u0012\b\u0010\u001f\u001a\u0004\u0018\u00010\u0018\u0012\b\u0010!\u001a\u0004\u0018\u00010\u0018\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u0018\u0012\b\u0010\u001d\u001a\u0004\u0018\u00010\u0018\u0012\b\b\u0002\u0010#\u001a\u00020\n\u0012\b\b\u0002\u0010\u0014\u001a\u00020\n\u00a2\u0006\u0004\b,\u0010.Jg\u0010\u0012\u001a\u00020\u00112\b\u0010\u0005\u001a\u0004\u0018\u00018\u00002\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\nH\u0014\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0019\u0010\u0019\u001a\u0004\u0018\u00010\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u0019\u0010\u001d\u001a\u0004\u0018\u00010\u00188\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001a\u001a\u0004\b\u001e\u0010\u001cR\u0019\u0010\u001f\u001a\u0004\u0018\u00010\u00188\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010\u001a\u001a\u0004\b \u0010\u001cR\u0019\u0010!\u001a\u0004\u0018\u00010\u00188\u0006\u00a2\u0006\f\n\u0004\b!\u0010\u001a\u001a\u0004\b\"\u0010\u001cR\u0017\u0010#\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b#\u0010\u0015\u001a\u0004\b$\u0010\u0017R \u0010&\u001a\b\u0012\u0004\u0012\u00020\u00040%8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\u00a8\u0006/"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/QuadrupedWalkAnimation;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "", "setAngles", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)V", "amplitudeMultiplier", "F", "getAmplitudeMultiplier", "()F", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "legBackLeft", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "getLegBackLeft", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "legBackRight", "getLegBackRight", "legFrontLeft", "getLegFrontLeft", "legFrontRight", "getLegFrontRight", "periodMultiplier", "getPeriodMultiplier", "Ljava/lang/Class;", "targetFrame", "Ljava/lang/Class;", "getTargetFrame", "()Ljava/lang/Class;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/QuadrupedFrame;", "frame", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/QuadrupedFrame;FF)V", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;FF)V", "common"})
public final class QuadrupedWalkAnimation<T extends Entity>
extends StatelessAnimation<T, ModelFrame> {
    @Nullable
    private final Bone legFrontLeft;
    @Nullable
    private final Bone legFrontRight;
    @Nullable
    private final Bone legBackLeft;
    @Nullable
    private final Bone legBackRight;
    private final float periodMultiplier;
    private final float amplitudeMultiplier;
    @NotNull
    private final Class<ModelFrame> targetFrame;

    public QuadrupedWalkAnimation(@NotNull ModelFrame frame, @Nullable Bone legFrontLeft, @Nullable Bone legFrontRight, @Nullable Bone legBackLeft, @Nullable Bone legBackRight, float periodMultiplier, float amplitudeMultiplier) {
        Intrinsics.checkNotNullParameter((Object)frame, (String)"frame");
        super(frame);
        this.legFrontLeft = legFrontLeft;
        this.legFrontRight = legFrontRight;
        this.legBackLeft = legBackLeft;
        this.legBackRight = legBackRight;
        this.periodMultiplier = periodMultiplier;
        this.amplitudeMultiplier = amplitudeMultiplier;
        this.targetFrame = ModelFrame.class;
    }

    public /* synthetic */ QuadrupedWalkAnimation(ModelFrame modelFrame, Bone bone, Bone bone2, Bone bone3, Bone bone4, float f, float f2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x20) != 0) {
            f = 0.6662f;
        }
        if ((n & 0x40) != 0) {
            f2 = 1.4f;
        }
        this(modelFrame, bone, bone2, bone3, bone4, f, f2);
    }

    @Nullable
    public final Bone getLegFrontLeft() {
        return this.legFrontLeft;
    }

    @Nullable
    public final Bone getLegFrontRight() {
        return this.legFrontRight;
    }

    @Nullable
    public final Bone getLegBackLeft() {
        return this.legBackLeft;
    }

    @Nullable
    public final Bone getLegBackRight() {
        return this.legBackRight;
    }

    public final float getPeriodMultiplier() {
        return this.periodMultiplier;
    }

    public final float getAmplitudeMultiplier() {
        return this.amplitudeMultiplier;
    }

    public QuadrupedWalkAnimation(@NotNull QuadrupedFrame frame, float periodMultiplier, float amplitudeMultiplier) {
        Intrinsics.checkNotNullParameter((Object)frame, (String)"frame");
        this(frame, (Bone)frame.getForeLeftLeg(), (Bone)frame.getForeRightLeg(), (Bone)frame.getHindLeftLeg(), (Bone)frame.getHindRightLeg(), periodMultiplier, amplitudeMultiplier);
    }

    public /* synthetic */ QuadrupedWalkAnimation(QuadrupedFrame quadrupedFrame, float f, float f2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            f = 0.6662f;
        }
        if ((n & 4) != 0) {
            f2 = 1.4f;
        }
        this(quadrupedFrame, f, f2);
    }

    @Override
    @NotNull
    public Class<ModelFrame> getTargetFrame() {
        return this.targetFrame;
    }

    @Override
    protected void setAngles(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @Nullable PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        Intrinsics.checkNotNullParameter(model, (String)"model");
        Bone bone = this.legBackRight;
        if (bone == null) {
            return;
        }
        Bone hindRightLeg = bone;
        Bone bone2 = this.legBackLeft;
        if (bone2 == null) {
            return;
        }
        Bone hindLeftLeg = bone2;
        Bone bone3 = this.legFrontRight;
        if (bone3 == null) {
            return;
        }
        Bone foreRightLeg = bone3;
        Bone bone4 = this.legFrontLeft;
        if (bone4 == null) {
            return;
        }
        Bone foreLeftLeg = bone4;
        ModelPartExtensionsKt.addRotation(hindRightLeg, 0, Mth.m_14089_((float)(limbSwing * this.periodMultiplier)) * limbSwingAmount * this.amplitudeMultiplier * intensity);
        ModelPartExtensionsKt.addRotation(hindLeftLeg, 0, Mth.m_14089_((float)(limbSwing * this.periodMultiplier + (float)Math.PI)) * limbSwingAmount * this.amplitudeMultiplier * intensity);
        ModelPartExtensionsKt.addRotation(foreRightLeg, 0, Mth.m_14089_((float)(limbSwing * this.periodMultiplier + (float)Math.PI)) * limbSwingAmount * this.amplitudeMultiplier * intensity);
        ModelPartExtensionsKt.addRotation(foreLeftLeg, 0, Mth.m_14089_((float)(limbSwing * this.periodMultiplier)) * limbSwingAmount * this.amplitudeMultiplier * intensity);
    }
}

