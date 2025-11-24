/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.RangesKt
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.AngleExtensionsKt;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u0003By\b\u0016\u0012\u0006\u00105\u001a\u000204\u0012\u0006\u00107\u001a\u000206\u0012\u0006\u00108\u001a\u000206\u0012\u0006\u00109\u001a\u000206\u0012\u0006\u0010:\u001a\u000206\u0012\n\b\u0002\u0010+\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u00102\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010)\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0004\b;\u0010<BU\u0012\u0006\u00105\u001a\u00020\u0004\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0014\u0012\b\b\u0002\u0010+\u001a\u00020\n\u0012\b\b\u0002\u00102\u001a\u00020\n\u0012\b\b\u0002\u0010!\u001a\u00020\n\u0012\b\b\u0002\u0010'\u001a\u00020\n\u0012\b\b\u0002\u0010%\u001a\u00020\n\u0012\b\b\u0002\u0010)\u001a\u00020\n\u00a2\u0006\u0004\b;\u0010=Jg\u0010\u0012\u001a\u00020\u00112\b\u0010\u0005\u001a\u0004\u0018\u00018\u00002\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\nH\u0014\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0019\u0010\u0015\u001a\u0004\u0018\u00010\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R(\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00198\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0017\u0010!\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R\u0017\u0010%\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b%\u0010\"\u001a\u0004\b&\u0010$R\u0017\u0010'\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b'\u0010\"\u001a\u0004\b(\u0010$R\u0017\u0010)\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b)\u0010\"\u001a\u0004\b*\u0010$R\u0017\u0010+\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b+\u0010\"\u001a\u0004\b,\u0010$R \u0010.\u001a\b\u0012\u0004\u0012\u00020\u00040-8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b.\u0010/\u001a\u0004\b0\u00101R\u0017\u00102\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b2\u0010\"\u001a\u0004\b3\u0010$\u00a8\u0006>"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/SingleBoneLookAnimation;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "", "setAngles", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "bone", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "getBone", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "", "", "labels", "Ljava/util/Set;", "getLabels", "()Ljava/util/Set;", "setLabels", "(Ljava/util/Set;)V", "maxPitch", "F", "getMaxPitch", "()F", "maxYaw", "getMaxYaw", "minPitch", "getMinPitch", "minYaw", "getMinYaw", "pitchMultiplier", "getPitchMultiplier", "Ljava/lang/Class;", "targetFrame", "Ljava/lang/Class;", "getTargetFrame", "()Ljava/lang/Class;", "yawMultiplier", "getYawMultiplier", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "frame", "", "invertX", "invertY", "disableX", "disableY", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;ZZZZLjava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;)V", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;FFFFFF)V", "common"})
public final class SingleBoneLookAnimation<T extends Entity>
extends StatelessAnimation<T, ModelFrame> {
    @Nullable
    private final Bone bone;
    private final float pitchMultiplier;
    private final float yawMultiplier;
    private final float maxPitch;
    private final float minPitch;
    private final float maxYaw;
    private final float minYaw;
    @NotNull
    private final Class<ModelFrame> targetFrame;
    @NotNull
    private Set<String> labels;

    public SingleBoneLookAnimation(@NotNull ModelFrame frame, @Nullable Bone bone, float pitchMultiplier, float yawMultiplier, float maxPitch, float minPitch, float maxYaw, float minYaw) {
        Intrinsics.checkNotNullParameter((Object)frame, (String)"frame");
        super(frame);
        this.bone = bone;
        this.pitchMultiplier = pitchMultiplier;
        this.yawMultiplier = yawMultiplier;
        this.maxPitch = maxPitch;
        this.minPitch = minPitch;
        this.maxYaw = maxYaw;
        this.minYaw = minYaw;
        this.targetFrame = ModelFrame.class;
        this.labels = SetsKt.setOf((Object)"look");
    }

    public /* synthetic */ SingleBoneLookAnimation(ModelFrame modelFrame, Bone bone, float f, float f2, float f3, float f4, float f5, float f6, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            f = 1.0f;
        }
        if ((n & 8) != 0) {
            f2 = 1.0f;
        }
        if ((n & 0x10) != 0) {
            f3 = 70.0f;
        }
        if ((n & 0x20) != 0) {
            f4 = -45.0f;
        }
        if ((n & 0x40) != 0) {
            f5 = 45.0f;
        }
        if ((n & 0x80) != 0) {
            f6 = -45.0f;
        }
        this(modelFrame, bone, f, f2, f3, f4, f5, f6);
    }

    @Nullable
    public final Bone getBone() {
        return this.bone;
    }

    public final float getPitchMultiplier() {
        return this.pitchMultiplier;
    }

    public final float getYawMultiplier() {
        return this.yawMultiplier;
    }

    public final float getMaxPitch() {
        return this.maxPitch;
    }

    public final float getMinPitch() {
        return this.minPitch;
    }

    public final float getMaxYaw() {
        return this.maxYaw;
    }

    public final float getMinYaw() {
        return this.minYaw;
    }

    public SingleBoneLookAnimation(@NotNull HeadedFrame frame, boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        Intrinsics.checkNotNullParameter((Object)frame, (String)"frame");
        Float f = pitchMultiplier;
        Float f2 = yawMultiplier;
        Float f3 = maxPitch;
        Float f4 = minPitch;
        Float f5 = maxYaw;
        Float f6 = minYaw;
        this(frame, frame.getHead(), f != null ? f.floatValue() : (disableX ? 0.0f : (invertX ? -1.0f : 1.0f)), f2 != null ? f2.floatValue() : (disableY ? 0.0f : (invertY ? -1.0f : 1.0f)), f3 != null ? f3.floatValue() : 70.0f, f4 != null ? f4.floatValue() : -45.0f, f5 != null ? f5.floatValue() : 45.0f, f6 != null ? f6.floatValue() : -45.0f);
    }

    public /* synthetic */ SingleBoneLookAnimation(HeadedFrame headedFrame, boolean bl, boolean bl2, boolean bl3, boolean bl4, Float f, Float f2, Float f3, Float f4, Float f5, Float f6, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x20) != 0) {
            f = null;
        }
        if ((n & 0x40) != 0) {
            f2 = null;
        }
        if ((n & 0x80) != 0) {
            f3 = null;
        }
        if ((n & 0x100) != 0) {
            f4 = null;
        }
        if ((n & 0x200) != 0) {
            f5 = null;
        }
        if ((n & 0x400) != 0) {
            f6 = null;
        }
        this(headedFrame, bl, bl2, bl3, bl4, f, f2, f3, f4, f5, f6);
    }

    @Override
    @NotNull
    public Class<ModelFrame> getTargetFrame() {
        return this.targetFrame;
    }

    @Override
    @NotNull
    public Set<String> getLabels() {
        return this.labels;
    }

    @Override
    public void setLabels(@NotNull Set<String> set2) {
        Intrinsics.checkNotNullParameter(set2, (String)"<set-?>");
        this.labels = set2;
    }

    @Override
    protected void setAngles(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @Nullable PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        Intrinsics.checkNotNullParameter(model, (String)"model");
        Bone bone = this.bone;
        if (bone == null) {
            return;
        }
        Bone head5 = bone;
        float pitch = this.pitchMultiplier * RangesKt.coerceIn((float)headPitch, (float)this.minPitch, (float)this.maxPitch);
        float yaw = this.yawMultiplier * RangesKt.coerceIn((float)headYaw, (float)this.minYaw, (float)this.maxYaw);
        ModelPartExtensionsKt.addRotation(head5, 0, AngleExtensionsKt.toRadians(Float.valueOf(pitch)) * intensity);
        ModelPartExtensionsKt.addRotation(head5, 1, AngleExtensionsKt.toRadians(Float.valueOf(yaw)) * intensity);
    }
}

