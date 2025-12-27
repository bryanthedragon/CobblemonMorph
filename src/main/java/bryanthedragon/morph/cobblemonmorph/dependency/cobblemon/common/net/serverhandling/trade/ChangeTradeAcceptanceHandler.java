package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeCancelledPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.ChangeTradeAcceptancePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.ActiveTrade
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeParticipant
import java.util.UUID
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object ChangeTradeAcceptanceHandler : ServerNetworkPacketHandler<ChangeTradeAcceptancePacket> {
   public open fun handle(packet: ChangeTradeAcceptancePacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: TradeManager = TradeManager.INSTANCE;
      var var10001: UUID = player.m_20148_();
      val var6: ActiveTrade = var10000.getActiveTrade(var10001);
      if (var6 == null) {
         CobblemonNetwork.INSTANCE.sendPacket(player, new TradeCancelledPacket());
      } else {
         var10001 = player.m_20148_();
         val tradeParticipant: TradeParticipant = var6.getTradeParticipant(var10001);
         val var7: Pokemon = var6.getOpposingOffer(tradeParticipant).getPokemon();
         if ((if (var7 != null) var7.getUuid() else null) == packet.getPokemonOfferId()) {
            var6.updateAcceptance(tradeParticipant, packet.getNewAcceptance());
         }
      }
   }

   fun handleOnNettyThread(packet: ChangeTradeAcceptancePacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
