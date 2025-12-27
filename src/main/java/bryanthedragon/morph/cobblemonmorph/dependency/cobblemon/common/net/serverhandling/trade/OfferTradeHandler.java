package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.OfferTradePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object OfferTradeHandler : ServerNetworkPacketHandler<OfferTradePacket> {
   public open fun handle(packet: OfferTradePacket, server: MinecraftServer, player: ServerPlayer) {
      if (!player.m_5833_()) {
         val var10000: TradeManager = TradeManager.INSTANCE;
         val var10002: ServerPlayer = PlayerExtensionsKt.getPlayer(packet.getOfferedPlayerId());
         if (var10002 != null) {
            var10000.offerTrade(player, var10002);
         }
      }
   }

   fun handleOnNettyThread(packet: OfferTradePacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
