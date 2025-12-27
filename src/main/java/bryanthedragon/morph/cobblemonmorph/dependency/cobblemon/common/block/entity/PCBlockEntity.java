package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLink
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.ProximityPCLink
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.PCBlock
import java.util.UUID
import kotlin.jvm.functions.Function1
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.level.entity.EntityTypeTest
import net.minecraft.world.phys.AABB

public class PCBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(CobblemonBlockEntities.PC, blockPos, blockState) {
   private fun togglePCOn(on: Boolean) {
      val var10000: Block = this.m_58900_().m_60734_();
      val pcBlock: PCBlock = var10000 as PCBlock;
      if (this.f_58857_ != null) {
         val var10: Level = this.f_58857_;
         if (!var10.f_46443_) {
            val var11: Level = this.f_58857_;
            val world: Level = var11;
            val var10001: BlockState = this.m_58900_();
            val var10002: BlockPos = this.f_58858_;
            val posBottom: BlockPos = pcBlock.getBasePosition(var10001, var10002);
            val stateBottom: BlockState = var11.m_8055_(posBottom);
            val posTop: BlockPos = pcBlock.getPositionOfOtherPart(stateBottom, posBottom);
            val stateTop: BlockState = var11.m_8055_(posTop);

            try {
               if (!(stateBottom.m_61143_(PCBlock.Companion.getON() as Property) == on)) {
                  world.m_46597_(posTop, stateTop.m_61124_(PCBlock.Companion.getON() as Property, on) as BlockState);
                  world.m_46597_(posBottom, stateBottom.m_61124_(PCBlock.Companion.getON() as Property, on) as BlockState);
               }
            } catch (var9: IllegalArgumentException) {
               if (var11.m_8055_(this.f_58858_.m_7494_()).m_60734_() is PCBlock) {
                  var11.m_46597_(this.f_58858_.m_7494_(), Blocks.f_50016_.m_49966_());
               } else {
                  var11.m_46597_(this.f_58858_.m_7495_(), Blocks.f_50016_.m_49966_());
               }

               var11.m_46597_(this.f_58858_, Blocks.f_50016_.m_49966_());
               var11.m_7967_(
                  (
                     new ItemEntity(
                        var11,
                        (double)this.f_58858_.m_123341_() + 0.5,
                        (double)this.f_58858_.m_123342_() + 1.0,
                        (double)this.f_58858_.m_123343_() + 0.5,
                        new ItemStack(CobblemonBlocks.PC as ItemLike)
                     )
                  ) as Entity
               );
            }
         }
      }
   }

   private fun isPlayerViewing(player: Player): Boolean {
      val var10000: PCLinkManager = PCLinkManager.INSTANCE;
      val var10001: UUID = player.m_20148_();
      val pcLink: PCLink = var10000.getLink(var10001);
      if (pcLink != null && pcLink is ProximityPCLink && (pcLink as ProximityPCLink).getPos() == this.f_58858_) {
         val var3: Level = (pcLink as ProximityPCLink).getWorld();
         if (var3.m_6042_() == player.m_9236_().m_6042_()) {
            return true;
         }
      }

      return false;
   }

   private fun getInRangeViewerCount(world: Level, pos: BlockPos, range: Double = 5.0): Int {
      return world.m_142425_(
            EntityTypeTest.m_156916_(Player.class),
            new AABB(
               (double)pos.m_123341_() - range,
               (double)pos.m_123342_() - range,
               (double)pos.m_123343_() - range,
               (double)(pos.m_123341_() + 1) + range,
               (double)(pos.m_123342_() + 1) + range,
               (double)(pos.m_123343_() + 1) + range
            ),
            PCBlockEntity::getInRangeViewerCount$lambda$0
         )
         .size();
   }

   @JvmStatic
   fun `getInRangeViewerCount$lambda$0`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }

   @JvmStatic
   fun `TICKER$lambda$1`(world: Level, var1: BlockPos, var2: BlockState, blockEntity: PCBlockEntity) {
      if (!world.f_46443_) {
         val var10003: BlockPos = blockEntity.f_58858_;
         blockEntity.togglePCOn(getInRangeViewerCount$default(blockEntity, world, var10003, 0.0, 4, null) > 0);
      }
   }

   public companion object {
      internal final val TICKER: BlockEntityTicker<PCBlockEntity>
   }
}
