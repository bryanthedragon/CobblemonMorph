package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import net.minecraft.network.chat.MutableComponent

public class WrappedDialogueText(text: MutableComponent = TextKt.text("")) : DialogueText {
   public final val text: MutableComponent

   init {
      this.text = text;
   }

   public override operator fun invoke(activeDialogue: ActiveDialogue): MutableComponent {
      return this.text.m_6881_();
   }

   fun WrappedDialogueText() {
      this(null, 1, null);
   }
}
