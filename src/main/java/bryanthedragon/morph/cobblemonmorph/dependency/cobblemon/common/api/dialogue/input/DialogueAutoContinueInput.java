package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input

import com.bedrockk.molang.runtime.struct.QueryStruct
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialogueAction
import java.util.HashMap

public class DialogueAutoContinueInput : DialogueInput {
   public final val action: DialogueAction = (new FunctionDialogueAction(<unrepresentable>.INSTANCE)) as DialogueAction
   public final var allowSkip: Boolean = true
   public final val delay: Float = 5.0F
   public final var showTimer: Boolean

   public open var timeout: DialogueTimeout?
      public open get() {
         return new DialogueTimeout(this.delay, this.showTimer, this.action);
      }

      public open set(<anonymous parameter 0>) {
      }


   public open fun toMoLangStruct(activeInput: ActiveInput): QueryStruct {
      return new QueryStruct(new HashMap<>());
   }

   public override fun handle(activeInput: ActiveInput, value: String) {
      if (!this.allowSkip) {
         Cobblemon.INSTANCE
            .getLOGGER()
            .warn(
               "A no-skip dialogue received input from ${activeInput.getActiveDialogue().getPlayerEntity().m_36316_().getName()}, is this person a hacker or something"
            );
      } else {
         DialogueAction.DefaultImpls.invoke$default(this.action, activeInput.getActiveDialogue(), null, 2, null);
      }
   }
}
