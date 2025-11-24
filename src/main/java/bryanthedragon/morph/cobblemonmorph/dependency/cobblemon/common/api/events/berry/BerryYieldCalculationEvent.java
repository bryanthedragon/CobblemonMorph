/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthFactor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryEvent;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\n\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u0012\u0006\u0010\u0013\u001a\u00020\u0012\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010#\u001a\u00020!\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0004\b)\u0010*R\u001a\u0010\u0003\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0019\u0010\u000e\u001a\u0004\u0018\u00010\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0013\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0018\u001a\u00020\u00178\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001d\u001a\u00020\u001c8\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R*\u0010#\u001a\u00020!2\u0006\u0010\"\u001a\u00020!8\u0006@FX\u0086\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/api/events/berry/BerryYieldCalculationEvent;", "Lcom/cobblemon/mod/common/api/events/berry/BerryEvent;", "Lcom/cobblemon/mod/common/api/berry/Berry;", "berry", "Lcom/cobblemon/mod/common/api/berry/Berry;", "getBerry", "()Lcom/cobblemon/mod/common/api/berry/Berry;", "", "Lcom/cobblemon/mod/common/api/berry/GrowthFactor;", "passedGrowthFactors", "Ljava/util/Collection;", "getPassedGrowthFactors", "()Ljava/util/Collection;", "Lnet/minecraft/world/entity/LivingEntity;", "placer", "Lnet/minecraft/world/entity/LivingEntity;", "getPlacer", "()Lnet/minecraft/world/entity/LivingEntity;", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/core/BlockPos;", "getPos", "()Lnet/minecraft/core/BlockPos;", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lnet/minecraft/world/level/block/state/BlockState;", "getState", "()Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/level/Level;", "getWorld", "()Lnet/minecraft/world/level/Level;", "", "value", "yield", "I", "getYield", "()I", "setYield", "(I)V", "<init>", "(Lcom/cobblemon/mod/common/api/berry/Berry;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/LivingEntity;ILjava/util/Collection;)V", "common"})
public final class BerryYieldCalculationEvent
implements BerryEvent {
    @NotNull
    private final Berry berry;
    @NotNull
    private final Level world;
    @NotNull
    private final BlockState state;
    @NotNull
    private final BlockPos pos;
    @Nullable
    private final LivingEntity placer;
    @NotNull
    private final Collection<GrowthFactor> passedGrowthFactors;
    private int yield;

    public BerryYieldCalculationEvent(@NotNull Berry berry, @NotNull Level world, @NotNull BlockState state, @NotNull BlockPos pos, @Nullable LivingEntity placer, int yield, @NotNull Collection<? extends GrowthFactor> passedGrowthFactors) {
        Intrinsics.checkNotNullParameter((Object)berry, (String)"berry");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter(passedGrowthFactors, (String)"passedGrowthFactors");
        this.berry = berry;
        this.world = world;
        this.state = state;
        this.pos = pos;
        this.placer = placer;
        this.passedGrowthFactors = passedGrowthFactors;
        this.yield = yield;
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

    @Nullable
    public final LivingEntity getPlacer() {
        return this.placer;
    }

    @NotNull
    public final Collection<GrowthFactor> getPassedGrowthFactors() {
        return this.passedGrowthFactors;
    }

    public final int getYield() {
        return this.yield;
    }

    public final void setYield(int value2) {
        int max2 = this.getBerry().maxYield();
        if (value2 > max2) {
            throw new IllegalArgumentException("Cannot set the berry yield for " + this.getBerry().getIdentifier() + " above " + max2);
        }
        if (value2 < 0) {
            throw new IllegalArgumentException("A berry tree cannot yield a negative amount of berries");
        }
        this.yield = value2;
    }
}

