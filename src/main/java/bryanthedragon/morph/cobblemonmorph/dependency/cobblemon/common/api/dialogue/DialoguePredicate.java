package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import java.util.LinkedHashMap

public interface DialoguePredicate {
   public abstract operator fun invoke(dialogue: ActiveDialogue): Boolean {
   }

   public companion object {
      public final val types: MutableMap<String, Class<out DialoguePredicate>> = (new LinkedHashMap()) as java.util.Map
   }
}
