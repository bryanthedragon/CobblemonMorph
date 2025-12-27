package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class UpdateTradeOfferPacket(newOffer: Pair<UUID, PartyPosition>?) : NetworkPacket<UpdateTradeOfferPacket> {
   public open val id: ResourceLocation
   public final val newOffer: Pair<UUID, PartyPosition>?

   init {
      this.newOffer = newOffer;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_236821_(this.newOffer, UpdateTradeOfferPacket::encode$lambda$0);
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
   fun `encode$lambda$0`(buffer: FriendlyByteBuf, var1: Pair) {
      val pokemonId: UUID = var1.component1() as UUID;
      val partyPosition: PartyPosition = var1.component2() as PartyPosition;
      buffer.m_130077_(pokemonId);
      val var10000: PartyPosition.Companion = PartyPosition.Companion;
      var10000.writePartyPosition(buffer, partyPosition);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): UpdateTradeOfferPacket {
         return new UpdateTradeOfferPacket(buffer.m_236868_(UpdateTradeOfferPacket.Companion::decode$lambda$0) as Pair<UUID, PartyPosition>);
      }

      @JvmStatic
      fun `decode$lambda$0`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): Pair {
         return TuplesKt.to(`$buffer`.m_130259_(), PartyPosition.Companion.readPartyPosition(`$buffer`));
      }
   }
}
