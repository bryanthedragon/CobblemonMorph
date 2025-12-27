package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.SetPartyPokemonPacket
import net.minecraft.client.Minecraft

public object SetPartyPokemonHandler : ClientNetworkPacketHandler<SetPartyPokemonPacket> {
   public open fun handle(packet: SetPartyPokemonPacket, client: Minecraft) {
      CobblemonClient.INSTANCE.getStorage().setPartyPokemon(packet.getStoreID(), packet.getStorePosition(), packet.getPokemonDTO().create());
   }

   fun handleOnNettyThread(packet: SetPartyPokemonPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
