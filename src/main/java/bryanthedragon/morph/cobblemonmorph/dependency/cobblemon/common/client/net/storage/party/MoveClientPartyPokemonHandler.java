package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.MoveClientPartyPokemonPacket
import net.minecraft.client.Minecraft

public object MoveClientPartyPokemonHandler : ClientNetworkPacketHandler<MoveClientPartyPokemonPacket> {
   public open fun handle(packet: MoveClientPartyPokemonPacket, client: Minecraft) {
      CobblemonClient.INSTANCE.getStorage().moveInParty(packet.getStoreID(), packet.getPokemonID(), packet.getNewPosition());
   }

   fun handleOnNettyThread(packet: MoveClientPartyPokemonPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
