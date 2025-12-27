package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTrade
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeUpdatedPacket
import java.util.UUID
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer

public object TradeUpdatedHandler : ClientNetworkPacketHandler<TradeUpdatedPacket> {
   public open fun handle(packet: TradeUpdatedPacket, client: Minecraft) {
      val var10000: ClientTrade = CobblemonClient.INSTANCE.getTrade();
      if (var10000 != null) {
         val var4: UUID = packet.getPlayerId();
         val var10001: LocalPlayer = Minecraft.m_91087_().f_91074_;
         if (var4 == (if (var10001 != null) var10001.m_20148_() else null)) {
            var10000.getMyOffer().set(packet.getPokemon());
         } else {
            var10000.getOppositeOffer().set(packet.getPokemon());
         }

         var10000.getOppositeAcceptedMyOffer().set(false);
         var10000.setAcceptedOppositeOffer(false);
      }
   }

   fun handleOnNettyThread(packet: TradeUpdatedPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
