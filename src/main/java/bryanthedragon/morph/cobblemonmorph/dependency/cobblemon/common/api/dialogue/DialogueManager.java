package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueClosedPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueOpenedPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.LinkedHashMap
import java.util.UUID
import net.minecraft.server.level.ServerPlayer

public object DialogueManager {
   public final val activeDialogues: MutableMap<UUID, ActiveDialogue> = (new LinkedHashMap()) as java.util.Map

   public fun startDialogue(playerEntity: ServerPlayer, dialogue: Dialogue) {
      val activeDialogue: ActiveDialogue = new ActiveDialogue(playerEntity, dialogue);
      val packet: java.util.Map = activeDialogues;
      val var10000: UUID = playerEntity.m_20148_();
      packet.put(var10000, activeDialogue);
      CobblemonNetwork.INSTANCE.sendPacket(playerEntity, new DialogueOpenedPacket(activeDialogue, true));
   }

   public fun stopDialogue(playerEntity: ServerPlayer) {
      val var10000: ActiveDialogue = PlayerExtensionsKt.getActiveDialogue(playerEntity);
      if (var10000 != null) {
         new DialogueClosedPacket(var10000.getDialogueId()).sendToPlayer(playerEntity);
         activeDialogues.remove(var10000.getDialogueId());
      }
   }
}
