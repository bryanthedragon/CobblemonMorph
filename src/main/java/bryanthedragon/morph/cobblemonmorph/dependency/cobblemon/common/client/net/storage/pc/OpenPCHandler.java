package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUIConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientPC
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.OpenPCPacket
import net.minecraft.client.Minecraft

public object OpenPCHandler : ClientNetworkPacketHandler<OpenPCPacket> {
   public open fun handle(packet: OpenPCPacket, client: Minecraft) {
      val var10000: ClientPC = CobblemonClient.INSTANCE.getStorage().getPcStores().get(packet.getStoreID());
      if (var10000 != null) {
         Minecraft.m_91087_()
            .m_91152_(new PCGUI(var10000, CobblemonClient.INSTANCE.getStorage().getMyParty(), new PCGUIConfiguration(null, null, false, null, 15, null)));
      }
   }

   fun handleOnNettyThread(packet: OpenPCPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
