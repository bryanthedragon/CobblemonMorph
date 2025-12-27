package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

public sealed interface DialogueFaceProvider {
   public companion object {
      public final val types: MutableMap<String, Class<out DialogueFaceProvider>> =
         MapsKt.mutableMapOf(
            new Pair[]{
               TuplesKt.to("player", PlayerDialogueFaceProvider.class),
               TuplesKt.to("standard", ArtificialDialogueFaceProvider.class),
               TuplesKt.to("expression", ExpressionLikeDialogueFaceProvider.class)
            }
         )
      }
}
