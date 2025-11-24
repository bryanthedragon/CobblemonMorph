/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant;
import kotlin.Metadata;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J7\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH&\u00a2\u0006\u0004\b\r\u0010\u000eJ/\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH&\u00a2\u0006\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/mulch/Mulchable;", "", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/util/RandomSource;", "random", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lcom/cobblemon/mod/common/api/mulch/MulchVariant;", "variant", "", "applyMulch", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/api/mulch/MulchVariant;)V", "", "canHaveMulchApplied", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/api/mulch/MulchVariant;)Z", "common"})
public interface Mulchable {
    public boolean canHaveMulchApplied(@NotNull ServerLevel var1, @NotNull BlockPos var2, @NotNull BlockState var3, @NotNull MulchVariant var4);

    public void applyMulch(@NotNull ServerLevel var1, @NotNull RandomSource var2, @NotNull BlockPos var3, @NotNull BlockState var4, @NotNull MulchVariant var5);
}

