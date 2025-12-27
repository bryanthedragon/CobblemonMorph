package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input

import com.bedrockk.molang.runtime.struct.VariableStruct
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialogueAction

public class DialogueNoInput(action: DialogueAction = (new FunctionDialogueAction(<unrepresentable>.INSTANCE)) as DialogueAction) : DialogueInput {
   public final var action: DialogueAction

   public open var timeout: DialogueTimeout?
      public open get() {
         return null;
      }

      public open set(<anonymous parameter 0>) {
      }


   init {
      this.action = action;
   }

   public open fun toMoLangStruct(activeInput: ActiveInput): VariableStruct {
      return new VariableStruct();
   }

   public override fun handle(activeInput: ActiveInput, value: String) {
      DialogueAction.DefaultImpls.invoke$default(this.action, activeInput.getActiveDialogue(), null, 2, null);
   }

   fun DialogueNoInput() {
      this(null, 1, null);
   }
}
