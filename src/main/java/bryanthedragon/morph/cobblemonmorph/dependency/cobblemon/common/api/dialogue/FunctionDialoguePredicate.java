package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import kotlin.jvm.functions.Function1

public class FunctionDialoguePredicate(predicate: (ActiveDialogue) -> Boolean = <unrepresentable>.INSTANCE as Function1) : DialoguePredicate {
   public final val predicate: (ActiveDialogue) -> Boolean

   init {
      this.predicate = predicate;
   }

   public override operator fun invoke(dialogue: ActiveDialogue): Boolean {
      return this.predicate.invoke(dialogue) as java.lang.Boolean;
   }

   fun FunctionDialoguePredicate() {
      this(null, 1, null);
   }
}
