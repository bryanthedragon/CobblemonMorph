package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.AcceptTradeRequestPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object AcceptTradeRequestHandler : ServerNetworkPacketHandler<AcceptTradeRequestPacket> {
   public open fun handle(packet: AcceptTradeRequestPacket, server: MinecraftServer, player: ServerPlayer) {
      if (!player.m_5833_()) {
         TradeManager.INSTANCE.acceptTradeRequest(player, packet.getTradeOfferId());
      }
   }

   fun handleOnNettyThread(packet: AcceptTradeRequestPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
