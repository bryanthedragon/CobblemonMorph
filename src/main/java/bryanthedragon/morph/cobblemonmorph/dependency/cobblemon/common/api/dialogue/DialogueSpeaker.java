package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import net.minecraft.network.chat.MutableComponent

public class DialogueSpeaker(name: DialogueText? = null, face: DialogueFaceProvider? = null) {
   public final val face: DialogueFaceProvider?
   public final val name: DialogueText?

   init {
      this.name = name;
      this.face = face;
   }

   public fun of(name: MutableComponent = TextKt.text(""), face: DialogueFaceProvider? = null): DialogueSpeaker {
      return new DialogueSpeaker(new WrappedDialogueText(name), face);
   }

   fun DialogueSpeaker() {
      this(null, null, 3, null);
   }
}
