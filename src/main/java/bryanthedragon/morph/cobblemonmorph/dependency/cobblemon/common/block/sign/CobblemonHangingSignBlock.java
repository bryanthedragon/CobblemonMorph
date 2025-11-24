/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.CeilingHangingSignBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.WoodType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.sign;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.CobblemonHangingSignBlockEntity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/block/sign/CobblemonHangingSignBlock;", "Lnet/minecraft/world/level/block/CeilingHangingSignBlock;", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "createBlockEntity", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/entity/BlockEntity;", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "Lnet/minecraft/world/level/block/state/properties/WoodType;", "woodType", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;Lnet/minecraft/world/level/block/state/properties/WoodType;)V", "common"})
public final class CobblemonHangingSignBlock
extends CeilingHangingSignBlock {
    public CobblemonHangingSignBlock(@NotNull BlockBehaviour.Properties settings, @NotNull WoodType woodType) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        Intrinsics.checkNotNullParameter((Object)woodType, (String)"woodType");
        super(settings, woodType);
    }

    @NotNull
    public BlockEntity m_142194_(@NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return (BlockEntity)new CobblemonHangingSignBlockEntity(pos, state);
    }
}

