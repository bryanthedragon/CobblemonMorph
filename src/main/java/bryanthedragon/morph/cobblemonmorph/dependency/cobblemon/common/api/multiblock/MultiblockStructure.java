/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J+\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u000e\u0010\rJ1\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0010\u001a\u0004\u0018\u00010\u000fH&\u00a2\u0006\u0004\b\u0011\u0010\u0012J7\u0010\u0016\u001a\u00020\u000b2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00132\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010\u0015\u001a\u0004\u0018\u00010\u0014H&\u00a2\u0006\u0004\b\u0016\u0010\u0017J?\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001cH&\u00a2\u0006\u0004\b\u001f\u0010 J\u0017\u0010!\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b!\u0010\rJ\u0017\u0010\"\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\"\u0010\rJ\u000f\u0010$\u001a\u00020#H&\u00a2\u0006\u0004\b$\u0010%R\u0014\u0010(\u001a\u00020\u00068&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b&\u0010'\u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/api/multiblock/MultiblockStructure;", "", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "", "getComparatorOutput", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)I", "", "markDirty", "(Lnet/minecraft/world/level/Level;)V", "markRemoved", "Lnet/minecraft/world/entity/player/Player;", "player", "onBreak", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)V", "Lnet/minecraft/server/level/ServerLevel;", "Lnet/minecraft/util/RandomSource;", "random", "onTriggerEvent", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", "blockState", "blockPos", "Lnet/minecraft/world/InteractionHand;", "interactionHand", "Lnet/minecraft/world/phys/BlockHitResult;", "blockHitResult", "Lnet/minecraft/world/InteractionResult;", "onUse", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;", "syncToClient", "tick", "Lnet/minecraft/nbt/CompoundTag;", "writeToNbt", "()Lnet/minecraft/nbt/CompoundTag;", "getControllerBlockPos", "()Lnet/minecraft/core/BlockPos;", "controllerBlockPos", "common"})
public interface MultiblockStructure {
    @NotNull
    public BlockPos getControllerBlockPos();

    @NotNull
    public InteractionResult onUse(@NotNull BlockState var1, @NotNull Level var2, @NotNull BlockPos var3, @NotNull Player var4, @NotNull InteractionHand var5, @NotNull BlockHitResult var6);

    public void onBreak(@NotNull Level var1, @NotNull BlockPos var2, @NotNull BlockState var3, @Nullable Player var4);

    public void tick(@NotNull Level var1);

    public void syncToClient(@NotNull Level var1);

    public void markDirty(@NotNull Level var1);

    @NotNull
    public CompoundTag writeToNbt();

    public int getComparatorOutput(@NotNull BlockState var1, @Nullable Level var2, @Nullable BlockPos var3);

    public void markRemoved(@NotNull Level var1);

    public void onTriggerEvent(@Nullable BlockState var1, @Nullable ServerLevel var2, @Nullable BlockPos var3, @Nullable RandomSource var4);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static int getComparatorOutput(@NotNull MultiblockStructure $this, @NotNull BlockState state, @Nullable Level world, @Nullable BlockPos pos) {
            Intrinsics.checkNotNullParameter((Object)state, (String)"state");
            return 0;
        }
    }
}

