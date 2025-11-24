/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.BaseEntityBlock
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockStructure;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilMultiblockEntity;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010'\u001a\u00020&\u00a2\u0006\u0004\b(\u0010)J!\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0019\u0010\u0012\u001a\u00020\u00112\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0017\u00a2\u0006\u0004\b\u0012\u0010\u0013J1\u0010\u0018\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u00142\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\b\u0010\u0016\u001a\u0004\u0018\u00010\u0015H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J;\u0010\u001d\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u00142\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\b\u0010\u001b\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001c\u001a\u0004\u0018\u00010\u000eH\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJ?\u0010$\u001a\u00020#2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00142\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020!H\u0016\u00a2\u0006\u0004\b$\u0010%\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/api/multiblock/MultiblockBlock;", "Lnet/minecraft/world/level/block/BaseEntityBlock;", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "createBlockEntity", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/entity/BlockEntity;", "Lcom/cobblemon/mod/common/block/entity/FossilMultiblockEntity;", "createMultiBlockEntity", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lcom/cobblemon/mod/common/block/entity/FossilMultiblockEntity;", "Lnet/minecraft/world/level/BlockGetter;", "world", "Lnet/minecraft/world/item/ItemStack;", "getPickStack", "(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/item/ItemStack;", "Lnet/minecraft/world/level/block/RenderShape;", "getRenderType", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/RenderShape;", "Lnet/minecraft/world/level/Level;", "Lnet/minecraft/world/entity/player/Player;", "player", "", "onBreak", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)V", "Lnet/minecraft/world/entity/LivingEntity;", "placer", "itemStack", "onPlaced", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/phys/BlockHitResult;", "hit", "Lnet/minecraft/world/InteractionResult;", "onUse", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;", "Lnet/minecraft/block/AbstractBlock$Settings;", "properties", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "common"})
public abstract class MultiblockBlock
extends BaseEntityBlock {
    public MultiblockBlock(@NotNull BlockBehaviour.Properties properties2) {
        Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
        super(properties2);
    }

    public void m_6402_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @Nullable ItemStack itemStack) {
        BlockEntity blockEntity;
        MultiblockEntity multiblockEntity;
        Object object;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        if (world instanceof ServerLevel && (object = (multiblockEntity = (blockEntity = world.m_7702_(pos)) instanceof MultiblockEntity ? (MultiblockEntity)blockEntity : null)) != null && (object = ((MultiblockEntity)((Object)object)).getMultiblockBuilder()) != null) {
            object.validate((ServerLevel)world);
        }
        super.m_6402_(world, pos, state, placer, itemStack);
    }

    @NotNull
    public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        MultiblockEntity entity2;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        Intrinsics.checkNotNullParameter((Object)hit, (String)"hit");
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResult.SUCCESS;
        }
        MultiblockEntity multiblockEntity = entity2 = (MultiblockEntity)world.m_7702_(pos);
        if ((multiblockEntity != null ? multiblockEntity.getMultiblockStructure() : null) != null) {
            MultiblockStructure multiblockStructure = entity2.getMultiblockStructure();
            Intrinsics.checkNotNull((Object)multiblockStructure);
            return multiblockStructure.onUse(state, world, pos, player, hand, hit);
        }
        InteractionResult interactionResult = super.m_6227_(state, world, pos, player, hand, hit);
        Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"super.onUse(state, world, pos, player, hand, hit)");
        return interactionResult;
    }

    public void m_5707_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable Player player) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        if (!world.f_46443_) {
            BlockEntity entity2 = world.m_7702_(pos);
            if (entity2 instanceof MultiblockEntity && ((MultiblockEntity)entity2).getMultiblockStructure() != null) {
                MultiblockStructure multiblockStructure = ((MultiblockEntity)entity2).getMultiblockStructure();
                Intrinsics.checkNotNull((Object)multiblockStructure);
                multiblockStructure.onBreak(world, pos, state, player);
            }
            BlockEntity blockEntity = entity2;
            if (blockEntity != null) {
                blockEntity.m_7651_();
            }
        }
        super.m_5707_(world, pos, state, player);
    }

    @Nullable
    public BlockEntity m_142194_(@NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return this.createMultiBlockEntity(pos, state);
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public RenderShape m_7514_(@Nullable BlockState state) {
        return RenderShape.MODEL;
    }

    @NotNull
    public ItemStack m_7397_(@NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull BlockState state) {
        ItemStack itemStack;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        BlockEntity blockEntity = world.m_7702_(pos);
        MultiblockEntity multiblockEntity = blockEntity instanceof MultiblockEntity ? (MultiblockEntity)blockEntity : null;
        if (multiblockEntity == null) {
            ItemStack itemStack2 = ItemStack.f_41583_;
            Intrinsics.checkNotNullExpressionValue((Object)itemStack2, (String)"EMPTY");
            return itemStack2;
        }
        MultiblockEntity blockEntity2 = multiblockEntity;
        if (blockEntity2.getMultiblockStructure() == null) {
            ItemStack itemStack3 = super.m_7397_(world, pos, state);
            itemStack = itemStack3;
            Intrinsics.checkNotNullExpressionValue((Object)itemStack3, (String)"super.getPickStack(world, pos, state)");
        } else {
            ItemStack itemStack4 = ItemStack.f_41583_;
            itemStack = itemStack4;
            Intrinsics.checkNotNullExpressionValue((Object)itemStack4, (String)"EMPTY");
        }
        return itemStack;
    }

    @NotNull
    public abstract FossilMultiblockEntity createMultiBlockEntity(@NotNull BlockPos var1, @NotNull BlockState var2);
}

