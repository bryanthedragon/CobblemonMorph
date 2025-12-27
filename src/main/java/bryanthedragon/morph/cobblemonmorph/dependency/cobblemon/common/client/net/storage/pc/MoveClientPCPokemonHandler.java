package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.MoveClientPCPokemonPacket
import net.minecraft.client.Minecraft

public object MoveClientPCPokemonHandler : ClientNetworkPacketHandler<MoveClientPCPokemonPacket> {
   public open fun handle(packet: MoveClientPCPokemonPacket, client: Minecraft) {
      CobblemonClient.INSTANCE.getStorage().moveInPC(packet.getStoreID(), packet.getPokemonID(), packet.getNewPosition());
   }

   fun handleOnNettyThread(packet: MoveClientPCPokemonPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
