package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import net.minecraft.network.chat.MutableComponent

public interface DialogueText {
   public abstract operator fun invoke(activeDialogue: ActiveDialogue): MutableComponent {
   }

   public companion object {
      public final val types: MutableMap<String, Class<out DialogueText>> =
         MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("expression", ExpressionLikeDialogueText.class)})
      }
}
