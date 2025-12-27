package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class TradeOfferNotificationPacket(tradeOfferId: UUID, traderId: UUID, traderName: MutableComponent) : NetworkPacket<TradeOfferNotificationPacket> {
   public open val id: ResourceLocation
   public final val tradeOfferId: UUID
   public final val traderId: UUID
   public final val traderName: MutableComponent

   init {
      this.tradeOfferId = tradeOfferId;
      this.traderId = traderId;
      this.traderName = traderName;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.tradeOfferId);
      buffer.m_130077_(this.traderId);
      buffer.m_130083_(this.traderName as Component);
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

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): TradeOfferNotificationPacket {
         val var10002: UUID = buffer.m_130259_();
         val var10003: UUID = buffer.m_130259_();
         val var10004: MutableComponent = buffer.m_130238_().m_6881_();
         return new TradeOfferNotificationPacket(var10002, var10003, var10004);
      }
   }
}
