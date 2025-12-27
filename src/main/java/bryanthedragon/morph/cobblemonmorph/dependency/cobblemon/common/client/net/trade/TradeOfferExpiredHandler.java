package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeOfferExpiredPacket
import kotlin.jvm.functions.Function1
import net.minecraft.client.Minecraft

public object TradeOfferExpiredHandler : ClientNetworkPacketHandler<TradeOfferExpiredPacket> {
   public open fun handle(packet: TradeOfferExpiredPacket, client: Minecraft) {
      CobblemonClient.INSTANCE.getRequests().getTradeOffers().removeIf(TradeOfferExpiredHandler::handle$lambda$0);
   }

   fun handleOnNettyThread(packet: TradeOfferExpiredPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }

   @JvmStatic
   fun `handle$lambda$0`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}
