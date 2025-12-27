package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.SwapClientPokemonPacket
import net.minecraft.client.Minecraft

public object SwapClientPokemonHandler : ClientNetworkPacketHandler<SwapClientPokemonPacket> {
   public open fun handle(packet: SwapClientPokemonPacket, client: Minecraft) {
      if (packet.getStoreIsParty()) {
         CobblemonClient.INSTANCE.getStorage().swapInParty(packet.getStoreID(), packet.getPokemonID1(), packet.getPokemonID2());
      } else {
         CobblemonClient.INSTANCE.getStorage().swapInPC(packet.getStoreID(), packet.getPokemonID1(), packet.getPokemonID2());
      }
   }

   fun handleOnNettyThread(packet: SwapClientPokemonPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
