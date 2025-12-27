package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt

public class ExpressionLikeDialogueAction(expression: ExpressionLike) : DialogueAction {
   public final val expression: ExpressionLike

   init {
      this.expression = expression;
   }

   public override operator fun invoke(dialogue: ActiveDialogue, input: String?) {
      if (input != null) {
         dialogue.getRuntime().getEnvironment().setSimpleVariable("selected_option", new StringValue(input));
      }

      MoLangExtensionsKt.resolve(dialogue.getRuntime(), this.expression);
   }
}
