package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.SetPCPokemonPacket
import net.minecraft.client.Minecraft

public object SetPCPokemonHandler : ClientNetworkPacketHandler<SetPCPokemonPacket> {
   public open fun handle(packet: SetPCPokemonPacket, client: Minecraft) {
      CobblemonClient.INSTANCE.getStorage().setPCPokemon(packet.getStoreID(), packet.getStorePosition(), packet.getPokemonDTO().create());
   }

   fun handleOnNettyThread(packet: SetPCPokemonPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
