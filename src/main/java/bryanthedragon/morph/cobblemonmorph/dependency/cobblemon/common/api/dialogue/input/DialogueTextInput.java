package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input

import com.bedrockk.molang.runtime.struct.QueryStruct
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialogueAction
import java.util.HashMap

public class DialogueTextInput : DialogueInput {
   public final val action: DialogueAction = (new FunctionDialogueAction(<unrepresentable>.INSTANCE)) as DialogueAction
   public open var timeout: DialogueTimeout?

   public open fun toMoLangStruct(activeInput: ActiveInput): QueryStruct {
      return new QueryStruct(new HashMap<>());
   }

   public override fun handle(activeInput: ActiveInput, value: String) {
      this.action.invoke(activeInput.getActiveDialogue(), value);
   }
}
