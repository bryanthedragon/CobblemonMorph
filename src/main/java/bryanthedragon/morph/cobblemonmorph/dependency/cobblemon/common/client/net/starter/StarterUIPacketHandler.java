package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.starter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection.StarterSelectionScreen
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter.OpenStarterUIPacket
import net.minecraft.client.Minecraft

public object StarterUIPacketHandler : ClientNetworkPacketHandler<OpenStarterUIPacket> {
   public open fun handle(packet: OpenStarterUIPacket, client: Minecraft) {
      CobblemonClient.INSTANCE.setCheckedStarterScreen(true);
      client.m_91152_(new StarterSelectionScreen(packet.getCategories()));
   }

   fun handleOnNettyThread(packet: OpenStarterUIPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
