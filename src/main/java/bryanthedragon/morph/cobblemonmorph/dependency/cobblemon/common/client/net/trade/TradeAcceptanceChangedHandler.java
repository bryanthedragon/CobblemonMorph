package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTrade
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeAcceptanceChangedPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID
import net.minecraft.client.Minecraft

public object TradeAcceptanceChangedHandler : ClientNetworkPacketHandler<TradeAcceptanceChangedPacket> {
   public open fun handle(packet: TradeAcceptanceChangedPacket, client: Minecraft) {
      var var5: UUID;
      label32: {
         val var10000: ClientTrade = CobblemonClient.INSTANCE.getTrade();
         if (var10000 != null) {
            val var3: SettableObservable = var10000.getMyOffer();
            if (var3 != null) {
               val var4: Pokemon = var3.get() as Pokemon;
               if (var4 != null) {
                  var5 = var4.getUuid();
                  break label32;
               }
            }
         }

         var5 = null;
      }

      if (var5 == packet.getPokemonId()) {
         val var6: ClientTrade = CobblemonClient.INSTANCE.getTrade();
         var6.getOppositeAcceptedMyOffer().set(packet.getAccepted());
      } else {
         label24: {
            val var7: ClientTrade = CobblemonClient.INSTANCE.getTrade();
            if (var7 != null) {
               val var8: SettableObservable = var7.getOppositeOffer();
               if (var8 != null) {
                  val var9: Pokemon = var8.get() as Pokemon;
                  if (var9 != null) {
                     var5 = var9.getUuid();
                     break label24;
                  }
               }
            }

            var5 = null;
         }

         if (var5 == packet.getPokemonId()) {
            val var11: ClientTrade = CobblemonClient.INSTANCE.getTrade();
            var11.setAcceptedOppositeOffer(packet.getAccepted());
         }
      }
   }

   fun handleOnNettyThread(packet: TradeAcceptanceChangedPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
