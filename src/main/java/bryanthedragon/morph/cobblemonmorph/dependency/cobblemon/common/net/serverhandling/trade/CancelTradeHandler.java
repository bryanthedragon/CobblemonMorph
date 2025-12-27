package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeCancelledPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.CancelTradePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.ActiveTrade
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager
import java.util.UUID
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object CancelTradeHandler : ServerNetworkPacketHandler<CancelTradePacket> {
   public open fun handle(packet: CancelTradePacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: TradeManager = TradeManager.INSTANCE;
      val var10001: UUID = player.m_20148_();
      val var5: ActiveTrade = var10000.getActiveTrade(var10001);
      if (var5 == null) {
         CobblemonNetwork.INSTANCE.sendPacket(player, new TradeCancelledPacket());
      } else {
         var5.cancelTrade();
      }
   }

   fun handleOnNettyThread(packet: CancelTradePacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
