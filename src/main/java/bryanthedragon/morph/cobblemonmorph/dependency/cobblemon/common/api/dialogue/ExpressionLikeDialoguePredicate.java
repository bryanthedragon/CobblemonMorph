package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt

public class ExpressionLikeDialoguePredicate(expression: ExpressionLike = MoLangExtensionsKt.asExpressionLike("true")) : DialoguePredicate {
   public final val expression: ExpressionLike

   init {
      this.expression = expression;
   }

   public override operator fun invoke(dialogue: ActiveDialogue): Boolean {
      return MoLangExtensionsKt.resolveBoolean(dialogue.getRuntime(), this.expression);
   }

   fun ExpressionLikeDialoguePredicate() {
      this(null, 1, null);
   }
}
