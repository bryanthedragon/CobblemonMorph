package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider
import net.minecraft.network.chat.MutableComponent

public class DialogueSpeakerDTO(name: MutableComponent? = null, face: DialogueFaceProvider?) {
   public final val face: DialogueFaceProvider?
   public final val name: MutableComponent?

   init {
      this.name = name;
      this.face = face;
   }
}
