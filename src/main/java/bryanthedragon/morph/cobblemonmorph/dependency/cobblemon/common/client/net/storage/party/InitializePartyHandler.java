package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.InitializePartyPacket
import net.minecraft.client.Minecraft

public object InitializePartyHandler : ClientNetworkPacketHandler<InitializePartyPacket> {
   public open fun handle(packet: InitializePartyPacket, client: Minecraft) {
      CobblemonClient.INSTANCE.getStorage().createParty(packet.isThisPlayerParty(), packet.getUuid(), packet.getSlots());
   }

   fun handleOnNettyThread(packet: InitializePartyPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
