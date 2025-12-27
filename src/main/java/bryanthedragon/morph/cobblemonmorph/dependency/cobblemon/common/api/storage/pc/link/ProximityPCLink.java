package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PCBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt
import java.util.UUID
import net.minecraft.core.BlockPos
import net.minecraft.core.Position
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

public class ProximityPCLink(pc: PCStore, playerID: UUID, pcBlockEntity: PCBlockEntity, maxDistance: Double = 10.0) : PCLink(pc, playerID) {
   public final val maxDistance: Double
   public final val pos: BlockPos
   public final val world: Level?

   init {
      this.maxDistance = maxDistance;
      this.world = pcBlockEntity.m_58904_();
      this.pos = pcBlockEntity.m_58899_();
   }

   public override fun isPermitted(player: ServerPlayer): Boolean {
      var var4: Boolean;
      label29: {
         if (player.m_9236_() == this.world) {
            val var10000: Vec3 = player.m_20182_();
            val var10001: BlockPos = this.pos;
            if (var10000.m_82509_(BlockPosExtensionsKt.toVec3d(var10001) as Position, this.maxDistance)) {
               var4 = true;
               break label29;
            }
         }

         var4 = false;
      }

      val pcStillStanding: Boolean = player.m_9236_().m_7702_(this.pos) is PCBlockEntity;
      if (!var4 || !pcStillStanding) {
         PCLinkManager.INSTANCE.removeLink(this.getPlayerID());
      }

      return var4 && pcStillStanding;
   }
}
