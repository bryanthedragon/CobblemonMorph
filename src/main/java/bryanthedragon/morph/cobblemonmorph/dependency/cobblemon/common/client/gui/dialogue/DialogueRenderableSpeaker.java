package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue

import net.minecraft.network.chat.MutableComponent

public class DialogueRenderableSpeaker(name: MutableComponent?, face: RenderableFace?) {
   public final val face: RenderableFace?
   public final val name: MutableComponent?

   init {
      this.name = name;
      this.face = face;
   }
}
