package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike

public class ExpressionLikeDialogueFaceProvider(providerExpression: ExpressionLike) : DialogueFaceProvider {
   public final val providerExpression: ExpressionLike

   init {
      this.providerExpression = providerExpression;
   }
}
