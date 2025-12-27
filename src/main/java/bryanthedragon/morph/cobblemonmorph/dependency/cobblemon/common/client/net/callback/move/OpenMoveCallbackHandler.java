package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.callback.move

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect.MoveSelectGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenMoveCallbackPacket
import net.minecraft.client.Minecraft

public object OpenMoveCallbackHandler : ClientNetworkPacketHandler<OpenMoveCallbackPacket> {
   public open fun handle(packet: OpenMoveCallbackPacket, client: Minecraft) {
      client.m_91152_(new MoveSelectGUI(packet.getTitle(), packet.getMoves(), packet.getUuid()));
   }

   fun handleOnNettyThread(packet: OpenMoveCallbackPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
