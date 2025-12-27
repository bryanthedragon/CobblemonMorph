package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueDTO
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class DialogueOpenedPacket(dialogueDTO: DialogueDTO) : NetworkPacket<DialogueOpenedPacket> {
   public final val dialogueDTO: DialogueDTO
   public open val id: ResourceLocation

   init {
      this.dialogueDTO = dialogueDTO;
      this.id = ID;
   }

   public constructor(activeDialogue: ActiveDialogue, includeFaces: Boolean) : this(new DialogueDTO(activeDialogue, includeFaces))
   public override fun encode(buffer: FriendlyByteBuf) {
      this.dialogueDTO.encode(buffer);
   }

   override fun sendToPlayer(player: ServerPlayer) {
      NetworkPacket.DefaultImpls.sendToPlayer(this, player);
   }

   override fun sendToPlayers(players: MutableIterable<ServerPlayer>) {
      NetworkPacket.DefaultImpls.sendToPlayers(this, players);
   }

   override fun sendToAllPlayers() {
      NetworkPacket.DefaultImpls.sendToAllPlayers(this);
   }

   override fun sendToServer() {
      NetworkPacket.DefaultImpls.sendToServer(this);
   }

   override fun sendToPlayersAround(
      x: Double, y: Double, z: Double, distance: Double, worldKey: ResourceKey<Level>, exclusionCondition: (ServerPlayer?) -> java.lang.Boolean
   ) {
      NetworkPacket.DefaultImpls.sendToPlayersAround(this, x, y, z, distance, worldKey, exclusionCondition);
   }

   override fun toBuffer(): FriendlyByteBuf {
      return NetworkPacket.DefaultImpls.toBuffer(this);
   }

   @SourceDebugExtension(["SMAP\nDialogueOpenedPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueOpenedPacket.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/DialogueOpenedPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,29:1\n1#2:30\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): DialogueOpenedPacket {
         val var2: DialogueDTO = new DialogueDTO();
         var2.decode(buffer);
         return new DialogueOpenedPacket(var2);
      }
   }
}
