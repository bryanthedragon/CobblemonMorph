package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.RemoveClientPokemonPacket
import net.minecraft.client.Minecraft

public object RemoveClientPokemonHandler : ClientNetworkPacketHandler<RemoveClientPokemonPacket> {
   public open fun handle(packet: RemoveClientPokemonPacket, client: Minecraft) {
      if (packet.getStoreIsParty()) {
         CobblemonClient.INSTANCE.getStorage().removeFromParty(packet.getStoreID(), packet.getPokemonID());
      } else {
         CobblemonClient.INSTANCE.getStorage().removeFromPC(packet.getStoreID(), packet.getPokemonID());
      }
   }

   fun handleOnNettyThread(packet: RemoveClientPokemonPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
