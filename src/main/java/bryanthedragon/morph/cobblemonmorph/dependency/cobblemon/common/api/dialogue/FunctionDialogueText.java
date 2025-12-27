package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import kotlin.jvm.functions.Function1
import net.minecraft.network.chat.MutableComponent

public class FunctionDialogueText(function: (ActiveDialogue) -> MutableComponent = <unrepresentable>.INSTANCE as Function1) : DialogueText {
   public final val function: (ActiveDialogue) -> MutableComponent

   init {
      this.function = function;
   }

   public override operator fun invoke(activeDialogue: ActiveDialogue): MutableComponent {
      return this.function.invoke(activeDialogue) as MutableComponent;
   }

   fun FunctionDialogueText() {
      this(null, 1, null);
   }
}
