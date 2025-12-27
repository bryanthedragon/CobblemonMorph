package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class ClosePCPacket(storeID: UUID?) : NetworkPacket<ClosePCPacket> {
   public open val id: ResourceLocation
   public final val storeID: UUID?

   init {
      this.storeID = storeID;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_236821_(this.storeID, ClosePCPacket::encode$lambda$0);
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
   fun `encode$lambda$0`(pb: FriendlyByteBuf, value: UUID) {
      pb.m_130077_(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): ClosePCPacket {
         return new ClosePCPacket(buffer.m_236868_(ClosePCPacket.Companion::decode$lambda$0) as UUID);
      }

      @JvmStatic
      fun `decode$lambda$0`(it: FriendlyByteBuf): UUID {
         return it.m_130259_();
      }
   }
}
