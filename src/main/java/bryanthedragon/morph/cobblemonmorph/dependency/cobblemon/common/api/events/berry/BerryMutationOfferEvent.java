/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryEvent;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\t\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000f\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0011\u001a\u00020\u0002\u0012\u0006\u0010\u0012\u001a\u00020\u0005\u0012\u0006\u0010\u0013\u001a\u00020\b\u0012\u0006\u0010\u0014\u001a\u00020\u000b\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00020\u000e\u00a2\u0006\u0004\b-\u0010.J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ\u0010\u0010\f\u001a\u00020\u000bH\u00c6\u0003\u00a2\u0006\u0004\b\f\u0010\rJ\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00020\u000eH\u00c6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010JH\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0011\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u00052\b\b\u0002\u0010\u0013\u001a\u00020\b2\b\b\u0002\u0010\u0014\u001a\u00020\u000b2\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00020\u000eH\u00c6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u001a\u0010\u001b\u001a\u00020\u001a2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0018H\u00d6\u0003\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0010\u0010\u001e\u001a\u00020\u001dH\u00d6\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0010\u0010!\u001a\u00020 H\u00d6\u0001\u00a2\u0006\u0004\b!\u0010\"R\u001a\u0010\u0011\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0011\u0010#\u001a\u0004\b$\u0010\u0004R\u001d\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010%\u001a\u0004\b&\u0010\u0010R\u0017\u0010\u0014\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010'\u001a\u0004\b(\u0010\rR\u0017\u0010\u0013\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010)\u001a\u0004\b*\u0010\nR\u0017\u0010\u0012\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010+\u001a\u0004\b,\u0010\u0007\u00a8\u0006/"}, d2={"Lcom/cobblemon/mod/common/api/events/berry/BerryMutationOfferEvent;", "Lcom/cobblemon/mod/common/api/events/berry/BerryEvent;", "Lcom/cobblemon/mod/common/api/berry/Berry;", "component1", "()Lcom/cobblemon/mod/common/api/berry/Berry;", "Lnet/minecraft/world/level/Level;", "component2", "()Lnet/minecraft/world/level/Level;", "Lnet/minecraft/world/level/block/state/BlockState;", "component3", "()Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/core/BlockPos;", "component4", "()Lnet/minecraft/core/BlockPos;", "", "component5", "()Ljava/util/Set;", "berry", "world", "state", "pos", "mutations", "copy", "(Lcom/cobblemon/mod/common/api/berry/Berry;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Ljava/util/Set;)Lcom/cobblemon/mod/common/api/events/berry/BerryMutationOfferEvent;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/berry/Berry;", "getBerry", "Ljava/util/Set;", "getMutations", "Lnet/minecraft/core/BlockPos;", "getPos", "Lnet/minecraft/world/level/block/state/BlockState;", "getState", "Lnet/minecraft/world/level/Level;", "getWorld", "<init>", "(Lcom/cobblemon/mod/common/api/berry/Berry;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Ljava/util/Set;)V", "common"})
public final class BerryMutationOfferEvent
implements BerryEvent {
    @NotNull
    private final Berry berry;
    @NotNull
    private final Level world;
    @NotNull
    private final BlockState state;
    @NotNull
    private final BlockPos pos;
    @NotNull
    private final Set<Berry> mutations;

    public BerryMutationOfferEvent(@NotNull Berry berry, @NotNull Level world, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Set<Berry> mutations) {
        Intrinsics.checkNotNullParameter((Object)berry, (String)"berry");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter(mutations, (String)"mutations");
        this.berry = berry;
        this.world = world;
        this.state = state;
        this.pos = pos;
        this.mutations = mutations;
    }

    @Override
    @NotNull
    public Berry getBerry() {
        return this.berry;
    }

    @NotNull
    public final Level getWorld() {
        return this.world;
    }

    @NotNull
    public final BlockState getState() {
        return this.state;
    }

    @NotNull
    public final BlockPos getPos() {
        return this.pos;
    }

    @NotNull
    public final Set<Berry> getMutations() {
        return this.mutations;
    }

    @NotNull
    public final Berry component1() {
        return this.berry;
    }

    @NotNull
    public final Level component2() {
        return this.world;
    }

    @NotNull
    public final BlockState component3() {
        return this.state;
    }

    @NotNull
    public final BlockPos component4() {
        return this.pos;
    }

    @NotNull
    public final Set<Berry> component5() {
        return this.mutations;
    }

    @NotNull
    public final BerryMutationOfferEvent copy(@NotNull Berry berry, @NotNull Level world, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Set<Berry> mutations) {
        Intrinsics.checkNotNullParameter((Object)berry, (String)"berry");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter(mutations, (String)"mutations");
        return new BerryMutationOfferEvent(berry, world, state, pos, mutations);
    }

    public static /* synthetic */ BerryMutationOfferEvent copy$default(BerryMutationOfferEvent berryMutationOfferEvent, Berry berry, Level level, BlockState blockState, BlockPos blockPos2, Set set2, int n, Object object) {
        if ((n & 1) != 0) {
            berry = berryMutationOfferEvent.berry;
        }
        if ((n & 2) != 0) {
            level = berryMutationOfferEvent.world;
        }
        if ((n & 4) != 0) {
            blockState = berryMutationOfferEvent.state;
        }
        if ((n & 8) != 0) {
            blockPos2 = berryMutationOfferEvent.pos;
        }
        if ((n & 0x10) != 0) {
            set2 = berryMutationOfferEvent.mutations;
        }
        return berryMutationOfferEvent.copy(berry, level, blockState, blockPos2, set2);
    }

    @NotNull
    public String toString() {
        return "BerryMutationOfferEvent(berry=" + this.berry + ", world=" + this.world + ", state=" + this.state + ", pos=" + this.pos + ", mutations=" + this.mutations + ")";
    }

    public int hashCode() {
        int result = this.berry.hashCode();
        result = result * 31 + this.world.hashCode();
        result = result * 31 + this.state.hashCode();
        result = result * 31 + this.pos.hashCode();
        result = result * 31 + ((Object)this.mutations).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BerryMutationOfferEvent)) {
            return false;
        }
        BerryMutationOfferEvent berryMutationOfferEvent = (BerryMutationOfferEvent)other;
        if (!Intrinsics.areEqual((Object)this.berry, (Object)berryMutationOfferEvent.berry)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.world, (Object)berryMutationOfferEvent.world)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.state, (Object)berryMutationOfferEvent.state)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.pos, (Object)berryMutationOfferEvent.pos)) {
            return false;
        }
        return Intrinsics.areEqual(this.mutations, berryMutationOfferEvent.mutations);
    }
}

