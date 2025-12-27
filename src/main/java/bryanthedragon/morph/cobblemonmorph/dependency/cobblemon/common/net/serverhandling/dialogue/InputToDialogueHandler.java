package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.ActiveInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.dialogue.InputToDialoguePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object InputToDialogueHandler : ServerNetworkPacketHandler<InputToDialoguePacket> {
   public open fun handle(packet: InputToDialoguePacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: ActiveDialogue = PlayerExtensionsKt.getActiveDialogue(player);
      if (var10000 != null) {
         val input: java.lang.String = packet.getInput();
         val activeInput: ActiveInput = var10000.getActiveInput();
         if (activeInput.getInputId() == packet.getInputId()) {
            activeInput.handle(input);
         }
      }
   }

   fun handleOnNettyThread(packet: InputToDialoguePacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
