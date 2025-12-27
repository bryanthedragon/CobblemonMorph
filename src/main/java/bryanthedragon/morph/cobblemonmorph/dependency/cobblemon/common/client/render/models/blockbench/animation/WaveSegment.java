package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import net.minecraft.client.model.geom.ModelPart

public class WaveSegment(modelPart: ModelPart, length: Float) {
   public final val length: Float
   public final val modelPart: ModelPart

   init {
      this.modelPart = modelPart;
      this.length = length;
   }
}
