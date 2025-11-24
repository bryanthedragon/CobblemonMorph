/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import kotlin.Metadata;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u008f\u0001\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010\"\b\b\u0000\u0010\u0003*\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\u00042\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\tH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0016\u001a\u00020\u00138&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lnet/minecraft/world/entity/Entity;", "T", "", "invertX", "invertY", "disableX", "disableY", "", "pitchMultiplier", "yawMultiplier", "maxPitch", "minPitch", "maxYaw", "minYaw", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/SingleBoneLookAnimation;", "singleBoneLook", "(ZZZZLjava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;)Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/SingleBoneLookAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "getHead", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "head", "common"})
public interface HeadedFrame
extends ModelFrame {
    @NotNull
    public Bone getHead();

    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean var1, boolean var2, boolean var3, boolean var4, @Nullable Float var5, @Nullable Float var6, @Nullable Float var7, @Nullable Float var8, @Nullable Float var9, @Nullable Float var10);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @NotNull
        public static <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(@NotNull HeadedFrame $this, boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
            return new SingleBoneLookAnimation($this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
        }

        public static /* synthetic */ SingleBoneLookAnimation singleBoneLook$default(HeadedFrame headedFrame, boolean bl, boolean bl2, boolean bl3, boolean bl4, Float f, Float f2, Float f3, Float f4, Float f5, Float f6, int n, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: singleBoneLook");
            }
            if ((n & 1) != 0) {
                bl = false;
            }
            if ((n & 2) != 0) {
                bl2 = false;
            }
            if ((n & 4) != 0) {
                bl3 = false;
            }
            if ((n & 8) != 0) {
                bl4 = false;
            }
            if ((n & 0x10) != 0) {
                f = null;
            }
            if ((n & 0x20) != 0) {
                f2 = null;
            }
            if ((n & 0x40) != 0) {
                f3 = null;
            }
            if ((n & 0x80) != 0) {
                f4 = null;
            }
            if ((n & 0x100) != 0) {
                f5 = null;
            }
            if ((n & 0x200) != 0) {
                f6 = null;
            }
            return headedFrame.singleBoneLook(bl, bl2, bl3, bl4, f, f2, f3, f4, f5, f6);
        }
    }
}

