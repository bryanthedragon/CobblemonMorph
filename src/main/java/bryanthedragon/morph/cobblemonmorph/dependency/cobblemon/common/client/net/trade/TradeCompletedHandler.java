package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTrade
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeCompletedPacket
import net.minecraft.client.Minecraft

public object TradeCompletedHandler : ClientNetworkPacketHandler<TradeCompletedPacket> {
   public open fun handle(packet: TradeCompletedPacket, client: Minecraft) {
      val var10000: ClientTrade = CobblemonClient.INSTANCE.getTrade();
      if (var10000 != null) {
         var10000.getCompletedEmitter().emit(TuplesKt.to(packet.getPokemonId1(), packet.getPokemonId2()));
      }
   }

   fun handleOnNettyThread(packet: TradeCompletedPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
