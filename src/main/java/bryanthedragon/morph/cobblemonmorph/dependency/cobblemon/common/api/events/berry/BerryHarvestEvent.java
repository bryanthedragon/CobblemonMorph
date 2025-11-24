/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0013\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0018\u001a\u00020\u0002\u0012\u0006\u0010\u0019\u001a\u00020\u0005\u0012\u0006\u0010\u001a\u001a\u00020\b\u0012\u0006\u0010\u001b\u001a\u00020\u000b\u0012\u0006\u0010\u001c\u001a\u00020\u000e\u0012\u0006\u0010\u001d\u001a\u00020\u0011\u0012\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014\u00a2\u0006\u0004\b:\u0010;J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ\u0010\u0010\f\u001a\u00020\u000bH\u00c6\u0003\u00a2\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000f\u001a\u00020\u000eH\u00c6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00c6\u0003\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u00c6\u0003\u00a2\u0006\u0004\b\u0016\u0010\u0017J\\\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0018\u001a\u00020\u00022\b\b\u0002\u0010\u0019\u001a\u00020\u00052\b\b\u0002\u0010\u001a\u001a\u00020\b2\b\b\u0002\u0010\u001b\u001a\u00020\u000b2\b\b\u0002\u0010\u001c\u001a\u00020\u000e2\b\b\u0002\u0010\u001d\u001a\u00020\u00112\u000e\b\u0002\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u00c6\u0001\u00a2\u0006\u0004\b\u001f\u0010 J\u001a\u0010$\u001a\u00020#2\b\u0010\"\u001a\u0004\u0018\u00010!H\u00d6\u0003\u00a2\u0006\u0004\b$\u0010%J\u0010\u0010'\u001a\u00020&H\u00d6\u0001\u00a2\u0006\u0004\b'\u0010(J\u0010\u0010*\u001a\u00020)H\u00d6\u0001\u00a2\u0006\u0004\b*\u0010+R\u001a\u0010\u0018\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010,\u001a\u0004\b-\u0010\u0004R\u0017\u0010\u001d\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010.\u001a\u0004\b/\u0010\u0013R\u001d\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b\u001e\u00100\u001a\u0004\b1\u0010\u0017R\u0017\u0010\u0019\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0019\u00102\u001a\u0004\b3\u0010\u0007R\u0017\u0010\u001b\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u00104\u001a\u0004\b5\u0010\rR\u0017\u0010\u001c\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u00106\u001a\u0004\b7\u0010\u0010R\u0017\u0010\u001a\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u001a\u00108\u001a\u0004\b9\u0010\n\u00a8\u0006<"}, d2={"Lcom/cobblemon/mod/common/api/events/berry/BerryHarvestEvent;", "Lcom/cobblemon/mod/common/api/events/berry/BerryEvent;", "Lcom/cobblemon/mod/common/api/berry/Berry;", "component1", "()Lcom/cobblemon/mod/common/api/berry/Berry;", "Lnet/minecraft/server/level/ServerPlayer;", "component2", "()Lnet/minecraft/server/level/ServerPlayer;", "Lnet/minecraft/world/level/Level;", "component3", "()Lnet/minecraft/world/level/Level;", "Lnet/minecraft/core/BlockPos;", "component4", "()Lnet/minecraft/core/BlockPos;", "Lnet/minecraft/world/level/block/state/BlockState;", "component5", "()Lnet/minecraft/world/level/block/state/BlockState;", "Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;", "component6", "()Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;", "", "Lnet/minecraft/world/item/ItemStack;", "component7", "()Ljava/util/List;", "berry", "player", "world", "pos", "state", "blockEntity", "drops", "copy", "(Lcom/cobblemon/mod/common/api/berry/Berry;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;Ljava/util/List;)Lcom/cobblemon/mod/common/api/events/berry/BerryHarvestEvent;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/berry/Berry;", "getBerry", "Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;", "getBlockEntity", "Ljava/util/List;", "getDrops", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "Lnet/minecraft/core/BlockPos;", "getPos", "Lnet/minecraft/world/level/block/state/BlockState;", "getState", "Lnet/minecraft/world/level/Level;", "getWorld", "<init>", "(Lcom/cobblemon/mod/common/api/berry/Berry;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;Ljava/util/List;)V", "common"})
public final class BerryHarvestEvent
implements BerryEvent {
    @NotNull
    private final Berry berry;
    @NotNull
    private final ServerPlayer player;
    @NotNull
    private final Level world;
    @NotNull
    private final BlockPos pos;
    @NotNull
    private final BlockState state;
    @NotNull
    private final BerryBlockEntity blockEntity;
    @NotNull
    private final List<ItemStack> drops;

    public BerryHarvestEvent(@NotNull Berry berry, @NotNull ServerPlayer player, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull BerryBlockEntity blockEntity, @NotNull List<ItemStack> drops) {
        Intrinsics.checkNotNullParameter((Object)berry, (String)"berry");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)((Object)blockEntity), (String)"blockEntity");
        Intrinsics.checkNotNullParameter(drops, (String)"drops");
        this.berry = berry;
        this.player = player;
        this.world = world;
        this.pos = pos;
        this.state = state;
        this.blockEntity = blockEntity;
        this.drops = drops;
    }

    @Override
    @NotNull
    public Berry getBerry() {
        return this.berry;
    }

    @NotNull
    public final ServerPlayer getPlayer() {
        return this.player;
    }

    @NotNull
    public final Level getWorld() {
        return this.world;
    }

    @NotNull
    public final BlockPos getPos() {
        return this.pos;
    }

    @NotNull
    public final BlockState getState() {
        return this.state;
    }

    @NotNull
    public final BerryBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    @NotNull
    public final List<ItemStack> getDrops() {
        return this.drops;
    }

    @NotNull
    public final Berry component1() {
        return this.berry;
    }

    @NotNull
    public final ServerPlayer component2() {
        return this.player;
    }

    @NotNull
    public final Level component3() {
        return this.world;
    }

    @NotNull
    public final BlockPos component4() {
        return this.pos;
    }

    @NotNull
    public final BlockState component5() {
        return this.state;
    }

    @NotNull
    public final BerryBlockEntity component6() {
        return this.blockEntity;
    }

    @NotNull
    public final List<ItemStack> component7() {
        return this.drops;
    }

    @NotNull
    public final BerryHarvestEvent copy(@NotNull Berry berry, @NotNull ServerPlayer player, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull BerryBlockEntity blockEntity, @NotNull List<ItemStack> drops) {
        Intrinsics.checkNotNullParameter((Object)berry, (String)"berry");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)((Object)blockEntity), (String)"blockEntity");
        Intrinsics.checkNotNullParameter(drops, (String)"drops");
        return new BerryHarvestEvent(berry, player, world, pos, state, blockEntity, drops);
    }

    public static /* synthetic */ BerryHarvestEvent copy$default(BerryHarvestEvent berryHarvestEvent, Berry berry, ServerPlayer serverPlayer, Level level, BlockPos blockPos2, BlockState blockState, BerryBlockEntity berryBlockEntity, List list, int n, Object object) {
        if ((n & 1) != 0) {
            berry = berryHarvestEvent.berry;
        }
        if ((n & 2) != 0) {
            serverPlayer = berryHarvestEvent.player;
        }
        if ((n & 4) != 0) {
            level = berryHarvestEvent.world;
        }
        if ((n & 8) != 0) {
            blockPos2 = berryHarvestEvent.pos;
        }
        if ((n & 0x10) != 0) {
            blockState = berryHarvestEvent.state;
        }
        if ((n & 0x20) != 0) {
            berryBlockEntity = berryHarvestEvent.blockEntity;
        }
        if ((n & 0x40) != 0) {
            list = berryHarvestEvent.drops;
        }
        return berryHarvestEvent.copy(berry, serverPlayer, level, blockPos2, blockState, berryBlockEntity, list);
    }

    @NotNull
    public String toString() {
        return "BerryHarvestEvent(berry=" + this.berry + ", player=" + this.player + ", world=" + this.world + ", pos=" + this.pos + ", state=" + this.state + ", blockEntity=" + this.blockEntity + ", drops=" + this.drops + ")";
    }

    public int hashCode() {
        int result = this.berry.hashCode();
        result = result * 31 + this.player.hashCode();
        result = result * 31 + this.world.hashCode();
        result = result * 31 + this.pos.hashCode();
        result = result * 31 + this.state.hashCode();
        result = result * 31 + this.blockEntity.hashCode();
        result = result * 31 + ((Object)this.drops).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BerryHarvestEvent)) {
            return false;
        }
        BerryHarvestEvent berryHarvestEvent = (BerryHarvestEvent)other;
        if (!Intrinsics.areEqual((Object)this.berry, (Object)berryHarvestEvent.berry)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.player, (Object)berryHarvestEvent.player)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.world, (Object)berryHarvestEvent.world)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.pos, (Object)berryHarvestEvent.pos)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.state, (Object)berryHarvestEvent.state)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)((Object)this.blockEntity), (Object)((Object)berryHarvestEvent.blockEntity))) {
            return false;
        }
        return Intrinsics.areEqual(this.drops, berryHarvestEvent.drops);
    }
}

