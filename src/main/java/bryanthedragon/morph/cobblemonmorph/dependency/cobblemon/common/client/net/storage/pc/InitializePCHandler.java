package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientPC
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.InitializePCPacket
import net.minecraft.client.Minecraft

public object InitializePCHandler : ClientNetworkPacketHandler<InitializePCPacket> {
   public open fun handle(packet: InitializePCPacket, client: Minecraft) {
      CobblemonClient.INSTANCE.getStorage().getPcStores().put(packet.getStoreID(), new ClientPC(packet.getStoreID(), packet.getBoxCount()));
   }

   fun handleOnNettyThread(packet: InitializePCPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
