/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.entity.EntityTypeTest
 *  net.minecraft.world.phys.AABB
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLink;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.ProximityPCLink;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.PCBlock;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0017\u0012\u0006\u0010\u0014\u001a\u00020\u0004\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u00a2\u0006\u0004\b\u0017\u0010\u0018J)\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\u000bH\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\rH\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/block/entity/PCBlockEntity;", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "", "range", "", "getInRangeViewerCount", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;D)I", "Lnet/minecraft/world/entity/player/Player;", "player", "", "isPlayerViewing", "(Lnet/minecraft/world/entity/player/Player;)Z", "on", "", "togglePCOn", "(Z)V", "blockPos", "Lnet/minecraft/world/level/block/state/BlockState;", "blockState", "<init>", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "Companion", "common"})
public final class PCBlockEntity
extends BlockEntity {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final BlockEntityTicker<PCBlockEntity> TICKER = PCBlockEntity::TICKER$lambda$1;

    public PCBlockEntity(@NotNull BlockPos blockPos2, @NotNull BlockState blockState) {
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        super(CobblemonBlockEntities.PC, blockPos2, blockState);
    }

    private final void togglePCOn(boolean on) {
        Block block = this.m_58900_().m_60734_();
        Intrinsics.checkNotNull((Object)block, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.PCBlock");
        PCBlock pcBlock = (PCBlock)block;
        if (this.f_58857_ != null) {
            Level level = this.f_58857_;
            Intrinsics.checkNotNull((Object)level);
            if (!level.f_46443_) {
                Level level2 = this.f_58857_;
                Intrinsics.checkNotNull((Object)level2);
                Level world = level2;
                BlockState blockState = this.m_58900_();
                Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"cachedState");
                BlockPos blockPos2 = this.f_58858_;
                Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"pos");
                BlockPos posBottom = pcBlock.getBasePosition(blockState, blockPos2);
                BlockState stateBottom = world.m_8055_(posBottom);
                Intrinsics.checkNotNullExpressionValue((Object)stateBottom, (String)"stateBottom");
                BlockPos posTop = pcBlock.getPositionOfOtherPart(stateBottom, posBottom);
                BlockState stateTop = world.m_8055_(posTop);
                try {
                    if (!Intrinsics.areEqual((Object)stateBottom.m_61143_((Property)PCBlock.Companion.getON()), (Object)on)) {
                        world.m_46597_(posTop, (BlockState)stateTop.m_61124_((Property)PCBlock.Companion.getON(), (Comparable)Boolean.valueOf(on)));
                        world.m_46597_(posBottom, (BlockState)stateBottom.m_61124_((Property)PCBlock.Companion.getON(), (Comparable)Boolean.valueOf(on)));
                    }
                }
                catch (IllegalArgumentException exception) {
                    if (world.m_8055_(this.f_58858_.m_7494_()).m_60734_() instanceof PCBlock) {
                        world.m_46597_(this.f_58858_.m_7494_(), Blocks.f_50016_.m_49966_());
                    } else {
                        world.m_46597_(this.f_58858_.m_7495_(), Blocks.f_50016_.m_49966_());
                    }
                    world.m_46597_(this.f_58858_, Blocks.f_50016_.m_49966_());
                    world.m_7967_((Entity)new ItemEntity(world, (double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 1.0, (double)this.f_58858_.m_123343_() + 0.5, new ItemStack((ItemLike)CobblemonBlocks.PC)));
                }
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean isPlayerViewing(Player player) {
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        PCLink pcLink = PCLinkManager.INSTANCE.getLink(uUID);
        if (pcLink == null) return false;
        if (!(pcLink instanceof ProximityPCLink)) return false;
        if (!Intrinsics.areEqual((Object)((ProximityPCLink)pcLink).getPos(), (Object)this.f_58858_)) return false;
        Level level = ((ProximityPCLink)pcLink).getWorld();
        Intrinsics.checkNotNull((Object)level);
        if (!Intrinsics.areEqual((Object)level.m_6042_(), (Object)player.m_9236_().m_6042_())) return false;
        return true;
    }

    private final int getInRangeViewerCount(Level world, BlockPos pos, double range) {
        AABB box = new AABB((double)pos.m_123341_() - range, (double)pos.m_123342_() - range, (double)pos.m_123343_() - range, (double)(pos.m_123341_() + 1) + range, (double)(pos.m_123342_() + 1) + range, (double)(pos.m_123343_() + 1) + range);
        return world.m_142425_(EntityTypeTest.m_156916_(Player.class), box, arg_0 -> PCBlockEntity.getInRangeViewerCount$lambda$0((Function1)new Function1<Player, Boolean>(this){
            final /* synthetic */ PCBlockEntity this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@Nullable Player player) {
                Player player2 = player;
                Intrinsics.checkNotNull((Object)player2);
                return PCBlockEntity.access$isPlayerViewing(this.this$0, player2);
            }
        }, arg_0)).size();
    }

    static /* synthetic */ int getInRangeViewerCount$default(PCBlockEntity pCBlockEntity, Level level, BlockPos blockPos2, double d, int n, Object object) {
        if ((n & 4) != 0) {
            d = 5.0;
        }
        return pCBlockEntity.getInRangeViewerCount(level, blockPos2, d);
    }

    private static final boolean getInRangeViewerCount$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    private static final void TICKER$lambda$1(Level world, BlockPos blockPos2, BlockState blockState, PCBlockEntity blockEntity) {
        if (world.f_46443_) {
            return;
        }
        Intrinsics.checkNotNullExpressionValue((Object)((Object)blockEntity), (String)"blockEntity");
        Intrinsics.checkNotNullExpressionValue((Object)world, (String)"world");
        BlockPos blockPos3 = blockEntity.f_58858_;
        Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"blockEntity.pos");
        blockEntity.togglePCOn(PCBlockEntity.getInRangeViewerCount$default(blockEntity, world, blockPos3, 0.0, 4, null) > 0);
    }

    public static final /* synthetic */ boolean access$isPlayerViewing(PCBlockEntity $this, Player player) {
        return $this.isPlayerViewing(player);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0000X\u0080\u0004\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/block/entity/PCBlockEntity$Companion;", "", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "Lcom/cobblemon/mod/common/block/entity/PCBlockEntity;", "TICKER", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "getTICKER$common", "()Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final BlockEntityTicker<PCBlockEntity> getTICKER$common() {
            return TICKER;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

