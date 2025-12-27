package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.InteractWheelGuiFactoryKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PlayerInteractOptionsPacket
import net.minecraft.client.Minecraft

public object PlayerInteractOptionsHandler : ClientNetworkPacketHandler<PlayerInteractOptionsPacket> {
   public open fun handle(packet: PlayerInteractOptionsPacket, client: Minecraft) {
      Minecraft.m_91087_().m_91152_(InteractWheelGuiFactoryKt.createPlayerInteractGui(packet));
   }

   fun handleOnNettyThread(packet: PlayerInteractOptionsPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
