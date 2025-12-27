package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueAutoContinueInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueOption
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueOptionSetInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTextInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTimeout
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nDialogueInputDTO.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueInputDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,119:1\n1549#2:120\n1620#2,3:121\n1855#2,2:124\n*S KotlinDebug\n*F\n+ 1 DialogueInputDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueInputDTO\n*L\n46#1:120\n46#1:121,3\n87#1:124,2\n*E\n"])
public class DialogueInputDTO : Encodable, Decodable {
   public final var allowSkip: Boolean
   public final var deadline: Float
   public final var inputId: UUID
   public final var inputType: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueInputDTO.InputType
   public final var options: MutableList<DialogueOptionDTO>
   public final var showTimer: Boolean
   public final var vertical: Boolean

   init {
      val var10001: UUID = UUID.randomUUID();
      this.inputId = var10001;
      this.inputType = DialogueInputDTO.InputType.NONE;
      this.deadline = -1.0F;
      this.showTimer = true;
      this.options = new ArrayList<>();
      this.allowSkip = true;
   }

   public constructor(optionSet: DialogueOptionSetInput, activeDialogue: ActiveDialogue) : this() {
      val var10001: UUID = activeDialogue.getActiveInput().getInputId();
      this.inputId = var10001;
      val `$this$map$iv`: java.lang.Iterable = optionSet.getVisibleOptions(activeDialogue);
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add(
            new DialogueOptionDTO(
               (`item$iv$iv` as DialogueOption).getText().invoke(activeDialogue),
               (`item$iv$iv` as DialogueOption).getValue(),
               (`item$iv$iv` as DialogueOption).isSelectable().invoke(activeDialogue)
            )
         );
      }

      this.options = CollectionsKt.toMutableList(`destination$iv$iv` as java.util.List);
      val var14: DialogueTimeout = optionSet.getTimeout();
      this.deadline = if (var14 != null) var14.getDuration() else -1.0F;
      val var15: DialogueTimeout = optionSet.getTimeout();
      this.showTimer = var15 == null || var15.getShowTimer();
      this.inputType = DialogueInputDTO.InputType.OPTION;
      this.vertical = optionSet.getVertical();
   }

   public constructor(autoContinue: DialogueAutoContinueInput, activeDialogue: ActiveDialogue) : this() {
      val var10001: UUID = activeDialogue.getActiveInput().getInputId();
      this.inputId = var10001;
      val var3: DialogueTimeout = autoContinue.getTimeout();
      this.deadline = if (var3 != null) var3.getDuration() else -1.0F;
      this.inputType = DialogueInputDTO.InputType.AUTO_CONTINUE;
      this.allowSkip = autoContinue.getAllowSkip();
      this.showTimer = autoContinue.getShowTimer();
   }

   public constructor(text: DialogueTextInput, activeDialogue: ActiveDialogue) : this() {
      val var10001: UUID = activeDialogue.getActiveInput().getInputId();
      this.inputId = var10001;
      val var3: DialogueTimeout = text.getTimeout();
      this.deadline = if (var3 != null) var3.getDuration() else -1.0F;
      this.inputType = DialogueInputDTO.InputType.TEXT;
      val var4: DialogueTimeout = text.getTimeout();
      this.showTimer = var4 == null || var4.getShowTimer();
   }

   public constructor(activeDialogue: ActiveDialogue) : this() {
      val var10001: UUID = activeDialogue.getActiveInput().getInputId();
      this.inputId = var10001;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.inputId);
      buffer.m_130068_(this.inputType);
      buffer.writeFloat(this.deadline);
      buffer.writeBoolean(this.showTimer);
      switch (DialogueInputDTO.WhenMappings.$EnumSwitchMapping$0[this.inputType.ordinal()]) {
         case 1:
            buffer.writeBoolean(this.vertical);
            buffer.writeInt(this.options.size());

            val `$this$forEach$iv`: java.lang.Iterable;
            for (Object element$iv : $this$forEach$iv) {
               (`element$iv` as DialogueOptionDTO).encode(buffer);
            }
            break;
         case 2:
            buffer.writeBoolean(this.allowSkip);
         default:
      }
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      val var10001: UUID = buffer.m_130259_();
      this.inputId = var10001;
      val var5: java.lang.Enum = buffer.m_130066_(DialogueInputDTO.InputType.class);
      this.inputType = var5 as DialogueInputDTO.InputType;
      this.deadline = buffer.readFloat();
      this.showTimer = buffer.readBoolean();
      switch (DialogueInputDTO.WhenMappings.$EnumSwitchMapping$0[this.inputType.ordinal()]) {
         case 1:
            this.vertical = buffer.readBoolean();
            val size: Int = buffer.readInt();

            for (int i = 0; i < size; i++) {
               val option: DialogueOptionDTO = new DialogueOptionDTO(null, null, false, 7, null);
               option.decode(buffer);
               this.options.add(option);
            }
            break;
         case 2:
            this.allowSkip = buffer.readBoolean();
         default:
      }
   }

   public enum InputType {
      OPTION,
      TEXT,
      AUTO_CONTINUE,
      NONE   }
}
