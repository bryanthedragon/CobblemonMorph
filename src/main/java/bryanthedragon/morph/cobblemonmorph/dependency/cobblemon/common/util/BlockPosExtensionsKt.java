/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0011\u0010\u0002\u001a\u00020\u0001*\u00020\u0000\u00a2\u0006\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2={"Lnet/minecraft/core/BlockPos;", "Lnet/minecraft/world/phys/Vec3;", "toVec3d", "(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/Vec3;", "common"})
public final class BlockPosExtensionsKt {
    @NotNull
    public static final Vec3 toVec3d(@NotNull BlockPos $this$toVec3d) {
        Intrinsics.checkNotNullParameter((Object)$this$toVec3d, (String)"<this>");
        return new Vec3((double)$this$toVec3d.m_123341_(), (double)$this$toVec3d.m_123342_(), (double)$this$toVec3d.m_123343_());
    }
}

