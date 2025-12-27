package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.CancelTradePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.UpdateTradeOfferPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.ActiveTrade
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeParticipant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.UUID
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object UpdateTradeOfferHandler : ServerNetworkPacketHandler<UpdateTradeOfferPacket> {
   public open fun handle(packet: UpdateTradeOfferPacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: TradeManager = TradeManager.INSTANCE;
      var var10001: UUID = player.m_20148_();
      val var11: ActiveTrade = var10000.getActiveTrade(var10001);
      if (var11 == null) {
         CobblemonNetwork.INSTANCE.sendPacket(player, new CancelTradePacket());
      } else {
         var10001 = player.m_20148_();
         val tradeParticipant: TradeParticipant = var11.getTradeParticipant(var10001);
         val newOffer: Pair = packet.getNewOffer();
         if (newOffer == null) {
            var11.updateOffer(tradeParticipant, null);
         } else {
            val pokemonId: UUID = newOffer.component1() as UUID;
            val pokemon: Pokemon = PlayerExtensionsKt.party(player).get(newOffer.component2() as PartyPosition);
            if (pokemon == null || !(pokemon.getUuid() == pokemonId)) {
               return;
            }

            if (!pokemon.getTradeable()) {
               return;
            }

            var11.updateOffer(tradeParticipant, pokemon);
         }
      }
   }

   fun handleOnNettyThread(packet: UpdateTradeOfferPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
