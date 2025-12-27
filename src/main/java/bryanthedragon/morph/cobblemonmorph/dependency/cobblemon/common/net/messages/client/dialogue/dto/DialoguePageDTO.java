package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nDialoguePageDTO.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialoguePageDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialoguePageDTO\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,47:1\n1549#2:48\n1620#2,3:49\n1549#2:52\n1620#2,3:53\n1855#2,2:56\n*S KotlinDebug\n*F\n+ 1 DialoguePageDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialoguePageDTO\n*L\n28#1:48\n28#1:49,3\n29#1:52\n29#1:53,3\n36#1:56,2\n*E\n"])
public class DialoguePageDTO : Encodable, Decodable {
   public final var clientActions: MutableList<String> = (new ArrayList()) as java.util.List
   public final var lines: MutableList<MutableComponent> = (new ArrayList()) as java.util.List
   public final var speaker: String?


   public constructor(dialoguePage: DialoguePage, activeDialogue: ActiveDialogue)  {
      this.speaker = dialoguePage.getSpeaker();
      var `$this$map$iv`: java.lang.Iterable = dialoguePage.getLines();
      var `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add((`item$iv$iv` as DialogueText).invoke(activeDialogue));
      }

      this.lines = CollectionsKt.toMutableList(`destination$iv$iv` as java.util.List);
      `$this$map$iv` = dialoguePage.getClientActions();
      `destination$iv$iv` = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add((var19 as Expression).getOriginalString());
      }

      this.clientActions = CollectionsKt.toMutableList(`destination$iv$iv` as java.util.List);
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_236821_(this.speaker, DialoguePageDTO::encode$lambda$2);
      buffer.m_236828_(this.lines, DialoguePageDTO::encode$lambda$3);
      buffer.writeInt(this.clientActions.size());

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         buffer.m_130070_(`element$iv` as java.lang.String);
      }
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      this.speaker = buffer.m_236868_(DialoguePageDTO::decode$lambda$5) as java.lang.String;
      val var10001: java.util.List = buffer.m_236845_(DialoguePageDTO::decode$lambda$6);
      this.lines = CollectionsKt.toMutableList(var10001);
      val clientActionsSize: Int = buffer.readInt();

      for (int i = 0; i < clientActionsSize; i++) {
         val var10000: java.util.List = this.clientActions;
         val var4: java.lang.String = buffer.m_130277_();
         var10000.add(var4);
      }
   }

   @JvmStatic
   fun `encode$lambda$2`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, value: java.lang.String) {
      `$buffer`.m_130070_(value);
   }

   @JvmStatic
   fun `encode$lambda$3`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, value: MutableComponent) {
      `$buffer`.m_130083_(value as Component);
   }

   @JvmStatic
   fun `decode$lambda$5`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   @JvmStatic
   fun `decode$lambda$6`(it: FriendlyByteBuf): MutableComponent {
      return it.m_130238_().m_6881_();
   }
}
