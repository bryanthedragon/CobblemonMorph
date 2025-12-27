package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

public class FunctionDialogueAction(consumer: (ActiveDialogue, String?) -> Unit) : DialogueAction {
   public final val consumer: (ActiveDialogue, String?) -> Unit

   init {
      this.consumer = consumer;
   }

   public override operator fun invoke(dialogue: ActiveDialogue, input: String?) {
      this.consumer.invoke(dialogue, input);
   }
}
