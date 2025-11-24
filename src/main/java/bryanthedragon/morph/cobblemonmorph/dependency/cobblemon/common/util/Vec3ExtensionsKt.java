/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u0019\u0010\u0003\u001a\u00020\u0000*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0003\u0010\u0004\u001a\u0011\u0010\u0006\u001a\u00020\u0005*\u00020\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a\u0011\u0010\b\u001a\u00020\u0001*\u00020\u0000\u00a2\u0006\u0004\b\b\u0010\t\u001a\u0011\u0010\n\u001a\u00020\u0000*\u00020\u0001\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2={"Lorg/joml/Vector3f;", "Lnet/minecraft/world/phys/Vec3;", "vec3d", "set", "(Lorg/joml/Vector3f;Lnet/minecraft/world/phys/Vec3;)Lorg/joml/Vector3f;", "Lnet/minecraft/core/BlockPos;", "toBlockPos", "(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/core/BlockPos;", "toVec3d", "(Lorg/joml/Vector3f;)Lnet/minecraft/world/phys/Vec3;", "toVec3f", "(Lnet/minecraft/world/phys/Vec3;)Lorg/joml/Vector3f;", "common"})
public final class Vec3ExtensionsKt {
    @NotNull
    public static final BlockPos toBlockPos(@NotNull Vec3 $this$toBlockPos) {
        Intrinsics.checkNotNullParameter((Object)$this$toBlockPos, (String)"<this>");
        BlockPos blockPos2 = BlockPos.m_274561_((double)$this$toBlockPos.f_82479_, (double)$this$toBlockPos.f_82480_, (double)$this$toBlockPos.f_82481_);
        Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"ofFloored(this.x, this.y, this.z)");
        return blockPos2;
    }

    @NotNull
    public static final Vector3f toVec3f(@NotNull Vec3 $this$toVec3f) {
        Intrinsics.checkNotNullParameter((Object)$this$toVec3f, (String)"<this>");
        return new Vector3f((float)$this$toVec3f.f_82479_, (float)$this$toVec3f.f_82480_, (float)$this$toVec3f.f_82481_);
    }

    @NotNull
    public static final Vec3 toVec3d(@NotNull Vector3f $this$toVec3d) {
        Intrinsics.checkNotNullParameter((Object)$this$toVec3d, (String)"<this>");
        return new Vec3((double)$this$toVec3d.x, (double)$this$toVec3d.y, (double)$this$toVec3d.z);
    }

    @NotNull
    public static final Vector3f set(@NotNull Vector3f $this$set, @NotNull Vec3 vec3d) {
        Intrinsics.checkNotNullParameter((Object)$this$set, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)vec3d, (String)"vec3d");
        $this$set.x = (float)vec3d.f_82479_;
        $this$set.y = (float)vec3d.f_82480_;
        $this$set.z = (float)vec3d.f_82481_;
        return $this$set;
    }
}

