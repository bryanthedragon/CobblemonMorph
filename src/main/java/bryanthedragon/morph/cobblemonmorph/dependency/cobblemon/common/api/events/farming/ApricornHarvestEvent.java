/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.farming;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u0017\u0010\u0006\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/api/events/farming/ApricornHarvestEvent;", "", "Lnet/minecraft/world/level/block/state/BlockState;", "getBlock", "()Lnet/minecraft/world/level/block/state/BlockState;", "Lcom/cobblemon/mod/common/api/apricorn/Apricorn;", "apricorn", "Lcom/cobblemon/mod/common/api/apricorn/Apricorn;", "getApricorn", "()Lcom/cobblemon/mod/common/api/apricorn/Apricorn;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/core/BlockPos;", "getPos", "()Lnet/minecraft/core/BlockPos;", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/server/level/ServerLevel;", "getWorld", "()Lnet/minecraft/server/level/ServerLevel;", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/apricorn/Apricorn;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)V", "common"})
public final class ApricornHarvestEvent {
    @NotNull
    private final ServerPlayer player;
    @NotNull
    private final Apricorn apricorn;
    @NotNull
    private final ServerLevel world;
    @NotNull
    private final BlockPos pos;

    public ApricornHarvestEvent(@NotNull ServerPlayer player, @NotNull Apricorn apricorn, @NotNull ServerLevel world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)((Object)apricorn), (String)"apricorn");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        this.player = player;
        this.apricorn = apricorn;
        this.world = world;
        this.pos = pos;
    }

    @NotNull
    public final ServerPlayer getPlayer() {
        return this.player;
    }

    @NotNull
    public final Apricorn getApricorn() {
        return this.apricorn;
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
    public final BlockState getBlock() {
        BlockState blockState = this.world.m_8055_(this.pos);
        Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"world.getBlockState(pos)");
        return blockState;
    }
}

