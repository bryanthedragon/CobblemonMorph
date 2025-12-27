package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.SetPartyReferencePacket
import net.minecraft.client.Minecraft

public object SetPartyReferenceHandler : ClientNetworkPacketHandler<SetPartyReferencePacket> {
   public open fun handle(packet: SetPartyReferencePacket, client: Minecraft) {
      CobblemonClient.INSTANCE.getStorage().setPartyStore(packet.getStoreID());
   }

   fun handleOnNettyThread(packet: SetPartyReferencePacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
