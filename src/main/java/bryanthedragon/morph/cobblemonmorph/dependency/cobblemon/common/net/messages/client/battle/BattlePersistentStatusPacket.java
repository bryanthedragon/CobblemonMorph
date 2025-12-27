package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class BattlePersistentStatusPacket(pnx: String, status: PersistentStatus?) : NetworkPacket<BattlePersistentStatusPacket> {
   public open val id: ResourceLocation
   public final val pnx: String
   public final val status: PersistentStatus?

   init {
      this.pnx = pnx;
      this.status = status;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.pnx);
      buffer.m_236821_(this.status, BattlePersistentStatusPacket::encode$lambda$0);
   }

   override fun sendToPlayer(player: ServerPlayer) {
      NetworkPacket.DefaultImpls.sendToPlayer(this, player);
   }

   override fun sendToPlayers(players: MutableIterable<ServerPlayer>) {
      NetworkPacket.DefaultImpls.sendToPlayers(this, players);
   }

   override fun sendToAllPlayers() {
      NetworkPacket.DefaultImpls.sendToAllPlayers(this);
   }

   override fun sendToServer() {
      NetworkPacket.DefaultImpls.sendToServer(this);
   }

   override fun sendToPlayersAround(
      x: Double, y: Double, z: Double, distance: Double, worldKey: ResourceKey<Level>, exclusionCondition: (ServerPlayer?) -> java.lang.Boolean
   ) {
      NetworkPacket.DefaultImpls.sendToPlayersAround(this, x, y, z, distance, worldKey, exclusionCondition);
   }

   override fun toBuffer(): FriendlyByteBuf {
      return NetworkPacket.DefaultImpls.toBuffer(this);
   }

   @JvmStatic
   fun `encode$lambda$0`(buf: FriendlyByteBuf, value: PersistentStatus) {
      buf.m_130085_(value.getName());
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): BattlePersistentStatusPacket {
         val pnx: java.lang.String = buffer.m_130277_();
         val var10000: ResourceLocation = buffer.m_236868_(BattlePersistentStatusPacket.Companion::decode$lambda$0) as ResourceLocation;
         if (var10000 == null) {
            return new BattlePersistentStatusPacket(pnx, null);
         } else {
            val var5: Status = Statuses.INSTANCE.getStatus(var10000);
            val status: PersistentStatus = var5 as? PersistentStatus;
            return new BattlePersistentStatusPacket(pnx, status);
         }
      }

      @JvmStatic
      fun `decode$lambda$0`(it: FriendlyByteBuf): ResourceLocation {
         return it.m_130281_();
      }
   }
}
