package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import java.util.LinkedHashMap

public interface DialogueAction {
   public abstract operator fun invoke(dialogue: ActiveDialogue, input: String? = ...) {
   }

   public companion object {
      @JvmStatic
      public final val types: MutableMap<String, Class<out DialogueAction>> = (new LinkedHashMap()) as java.util.Map
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls
}
