package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTrade
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeCancelledPacket
import net.minecraft.client.Minecraft

public object TradeCancelledHandler : ClientNetworkPacketHandler<TradeCancelledPacket> {
   public open fun handle(packet: TradeCancelledPacket, client: Minecraft) {
      val var10000: ClientTrade = CobblemonClient.INSTANCE.getTrade();
      if (var10000 != null) {
         var10000.getCancelEmitter().emit(Unit.INSTANCE);
         CobblemonClient.INSTANCE.setTrade(null);
      }
   }

   fun handleOnNettyThread(packet: TradeCancelledPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
