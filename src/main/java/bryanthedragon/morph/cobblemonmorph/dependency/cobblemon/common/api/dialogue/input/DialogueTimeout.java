package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialogueAction

public class DialogueTimeout(duration: Float = 10.0F,
   showTimer: Boolean = true,
   action: DialogueAction = (new FunctionDialogueAction(<unrepresentable>.INSTANCE)) as DialogueAction
) {
   public final var action: DialogueAction
   public final var duration: Float
   public final var showTimer: Boolean

   init {
      this.duration = duration;
      this.showTimer = showTimer;
      this.action = action;
   }

   fun DialogueTimeout() {
      this(0.0F, false, null, 7, null);
   }
}
