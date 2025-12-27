package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import net.minecraft.network.chat.MutableComponent

public class ExpressionLikeDialogueText(expression: ExpressionLike = MoLangExtensionsKt.asExpressionLike("''")) : DialogueText {
   public final val expression: ExpressionLike

   init {
      this.expression = expression;
   }

   public override operator fun invoke(activeDialogue: ActiveDialogue): MutableComponent {
      return TextKt.text(MoLangExtensionsKt.resolveString(activeDialogue.getRuntime(), this.expression));
   }

   fun ExpressionLikeDialogueText() {
      this(null, 1, null);
   }
}
