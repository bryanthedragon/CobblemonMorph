/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.world;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0013\u001a\u00020\u0012\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0017\u0010\u0018R\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006R\"\u0010\f\u001a\u00020\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0013\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/events/world/BigRootPropagatedEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lnet/minecraft/core/BlockPos;", "newRootPosition", "Lnet/minecraft/core/BlockPos;", "getNewRootPosition", "()Lnet/minecraft/core/BlockPos;", "setNewRootPosition", "(Lnet/minecraft/core/BlockPos;)V", "pos", "getPos", "Lnet/minecraft/world/level/block/state/BlockState;", "resultingSpread", "Lnet/minecraft/world/level/block/state/BlockState;", "getResultingSpread", "()Lnet/minecraft/world/level/block/state/BlockState;", "setResultingSpread", "(Lnet/minecraft/world/level/block/state/BlockState;)V", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/server/level/ServerLevel;", "getWorld", "()Lnet/minecraft/server/level/ServerLevel;", "<init>", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "common"})
public final class BigRootPropagatedEvent
extends Cancelable {
    @NotNull
    private final ServerLevel world;
    @NotNull
    private final BlockPos pos;
    @NotNull
    private BlockPos newRootPosition;
    @NotNull
    private BlockState resultingSpread;

    public BigRootPropagatedEvent(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull BlockPos newRootPosition, @NotNull BlockState resultingSpread) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)newRootPosition, (String)"newRootPosition");
        Intrinsics.checkNotNullParameter((Object)resultingSpread, (String)"resultingSpread");
        this.world = world;
        this.pos = pos;
        this.newRootPosition = newRootPosition;
        this.resultingSpread = resultingSpread;
    }

    @NotNull
    public final ServerLevel getWorld() {
        return this.world;
    }

    @NotNull
    public final BlockPos getPos() {
        return this.pos;
    }

    @NotNull
    public final BlockPos getNewRootPosition() {
        return this.newRootPosition;
    }

    public final void setNewRootPosition(@NotNull BlockPos blockPos2) {
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"<set-?>");
        this.newRootPosition = blockPos2;
    }

    @NotNull
    public final BlockState getResultingSpread() {
        return this.resultingSpread;
    }

    public final void setResultingSpread(@NotNull BlockState blockState) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"<set-?>");
        this.resultingSpread = blockState;
    }
}

