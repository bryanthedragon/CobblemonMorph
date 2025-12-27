package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.dialogue.EscapeDialoguePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object EscapeDialogueHandler : ServerNetworkPacketHandler<EscapeDialoguePacket> {
   public open fun handle(packet: EscapeDialoguePacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: ActiveDialogue = PlayerExtensionsKt.getActiveDialogue(player);
      if (var10000 != null) {
         var10000.escape();
      }
   }

   fun handleOnNettyThread(packet: EscapeDialoguePacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
