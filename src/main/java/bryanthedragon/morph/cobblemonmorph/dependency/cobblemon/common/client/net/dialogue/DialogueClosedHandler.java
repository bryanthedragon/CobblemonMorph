package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.DialogueScreen
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueClosedPacket
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen

public object DialogueClosedHandler : ClientNetworkPacketHandler<DialogueClosedPacket> {
   public open fun handle(packet: DialogueClosedPacket, client: Minecraft) {
      val var4: Screen = client.f_91080_;
      val var10000: DialogueScreen = client.f_91080_ as? DialogueScreen;
      if ((client.f_91080_ as? DialogueScreen) != null) {
         if (packet.getDialogueId() == null || var10000.getDialogueId() == packet.getDialogueId()) {
            client.m_91152_(null);
         }
      }
   }

   fun handleOnNettyThread(packet: DialogueClosedPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
