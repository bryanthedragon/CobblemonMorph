package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CurrentKeyAccessorKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.PartySendBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTradeOffer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeOfferNotificationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.chat.Component

public object TradeOfferNotificationHandler : ClientNetworkPacketHandler<TradeOfferNotificationPacket> {
   public open fun handle(packet: TradeOfferNotificationPacket, client: Minecraft) {
      CobblemonClient.INSTANCE.getRequests().getTradeOffers().add(new ClientTradeOffer(packet.getTradeOfferId(), packet.getTraderId()));
      val var10000: LocalPlayer = client.f_91074_;
      if (client.f_91074_ != null) {
         val var3: Array<Any> = new Object[]{packet.getTraderName(), null};
         val var10004: Component = CurrentKeyAccessorKt.boundKey(PartySendBinding.INSTANCE).m_84875_();
         var3[1] = var10004;
         var10000.m_5661_(LocalizationUtilsKt.lang("trade.offer", var3) as Component, true);
      }
   }

   fun handleOnNettyThread(packet: TradeOfferNotificationPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
