package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePredicate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialogueAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialoguePredicate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.WrappedDialogueText

public class DialogueOption(text: DialogueText = (new WrappedDialogueText(null, 1, null)) as DialogueText,
   value: String = "",
   action: DialogueAction = (new FunctionDialogueAction(<unrepresentable>.INSTANCE)) as DialogueAction,
   isVisible: DialoguePredicate = (new FunctionDialoguePredicate(null, 1, null)) as DialoguePredicate,
   isSelectable: DialoguePredicate = (new FunctionDialoguePredicate(null, 1, null)) as DialoguePredicate
) {
   public final var action: DialogueAction
   public final val isSelectable: DialoguePredicate
   public final val isVisible: DialoguePredicate
   public final var text: DialogueText
   public final var value: String

   init {
      this.text = text;
      this.value = value;
      this.action = action;
      this.isVisible = isVisible;
      this.isSelectable = isSelectable;
   }

   fun DialogueOption() {
      this(null, null, null, null, null, 31, null);
   }
}
