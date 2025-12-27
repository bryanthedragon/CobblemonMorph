package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen

public object ClosePCHandler : ClientNetworkPacketHandler<ClosePCPacket> {
   public open fun handle(packet: ClosePCPacket, client: Minecraft) {
      if (client.f_91080_ is PCGUI) {
         val var10000: Screen = client.f_91080_;
         val pc: PCGUI = var10000 as PCGUI;
         if ((var10000 as PCGUI).getPc().getUuid() == packet.getStoreID()) {
            pc.getConfiguration().getExitFunction().invoke(pc);
         }
      }
   }

   fun handleOnNettyThread(packet: ClosePCPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
