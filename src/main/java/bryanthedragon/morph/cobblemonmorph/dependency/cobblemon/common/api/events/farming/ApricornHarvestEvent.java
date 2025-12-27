package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.farming

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.block.state.BlockState

public class ApricornHarvestEvent(player: ServerPlayer, apricorn: Apricorn, world: ServerLevel, pos: BlockPos) {
   public final val apricorn: Apricorn
   public final val player: ServerPlayer
   public final val pos: BlockPos
   public final val world: ServerLevel

   init {
      this.player = player;
      this.apricorn = apricorn;
      this.world = world;
      this.pos = pos;
   }

   public fun getBlock(): BlockState {
      val var10000: BlockState = this.world.m_8055_(this.pos);
      return var10000;
   }
}
