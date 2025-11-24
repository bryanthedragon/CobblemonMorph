/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/util/TraceResult;", "", "Lnet/minecraft/core/BlockPos;", "blockPos", "Lnet/minecraft/core/BlockPos;", "getBlockPos", "()Lnet/minecraft/core/BlockPos;", "Lnet/minecraft/core/Direction;", "direction", "Lnet/minecraft/core/Direction;", "getDirection", "()Lnet/minecraft/core/Direction;", "Lnet/minecraft/world/phys/Vec3;", "location", "Lnet/minecraft/world/phys/Vec3;", "getLocation", "()Lnet/minecraft/world/phys/Vec3;", "<init>", "(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)V", "common"})
public final class TraceResult {
    @NotNull
    private final Vec3 location;
    @NotNull
    private final BlockPos blockPos;
    @NotNull
    private final Direction direction;

    public TraceResult(@NotNull Vec3 location, @NotNull BlockPos blockPos2, @NotNull Direction direction) {
        Intrinsics.checkNotNullParameter((Object)location, (String)"location");
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)direction, (String)"direction");
        this.location = location;
        this.blockPos = blockPos2;
        this.direction = direction;
    }

    @NotNull
    public final Vec3 getLocation() {
        return this.location;
    }

    @NotNull
    public final BlockPos getBlockPos() {
        return this.blockPos;
    }

    @NotNull
    public final Direction getDirection() {
        return this.direction;
    }
}

