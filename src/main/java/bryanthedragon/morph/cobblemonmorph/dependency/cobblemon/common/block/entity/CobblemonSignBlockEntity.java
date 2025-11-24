/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.entity.SignBlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\t\u0010\nJ\u0013\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/block/entity/CobblemonSignBlockEntity;", "Lnet/minecraft/world/level/block/entity/SignBlockEntity;", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", "getType", "()Lnet/minecraft/world/level/block/entity/BlockEntityType;", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "<init>", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "common"})
public final class CobblemonSignBlockEntity
extends SignBlockEntity {
    public CobblemonSignBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        super(pos, state);
    }

    @NotNull
    public BlockEntityType<?> m_58903_() {
        return CobblemonBlockEntities.SIGN;
    }
}

