package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench

import net.minecraft.client.model.geom.ModelPart

public class EarJoint(modelPart: ModelPart, axis: Int, rangeOfMotion: RangeOfMotion) {
   public final val axis: Int
   public final val modelPart: ModelPart
   public final val rangeOfMotion: RangeOfMotion

   init {
      this.modelPart = modelPart;
      this.axis = axis;
      this.rangeOfMotion = rangeOfMotion;
   }
}
