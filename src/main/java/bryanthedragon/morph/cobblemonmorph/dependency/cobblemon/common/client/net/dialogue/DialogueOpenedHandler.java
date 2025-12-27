package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.DialogueScreen
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueOpenedPacket
import net.minecraft.client.Minecraft

public object DialogueOpenedHandler : ClientNetworkPacketHandler<DialogueOpenedPacket> {
   public open fun handle(packet: DialogueOpenedPacket, client: Minecraft) {
      val currentScreen: DialogueScreen = client.f_91080_ as? DialogueScreen;
      if ((client.f_91080_ as? DialogueScreen) != null && (client.f_91080_ as? DialogueScreen).getDialogueId() == packet.getDialogueDTO().getDialogueId()) {
         currentScreen.update(packet.getDialogueDTO());
      } else {
         client.m_91152_(new DialogueScreen(packet.getDialogueDTO()));
      }
   }

   fun handleOnNettyThread(packet: DialogueOpenedPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
