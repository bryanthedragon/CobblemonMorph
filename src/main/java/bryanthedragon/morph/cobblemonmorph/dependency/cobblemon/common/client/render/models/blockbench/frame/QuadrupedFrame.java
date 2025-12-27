package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame

import net.minecraft.client.model.geom.ModelPart

public interface QuadrupedFrame : ModelFrame {
   public val foreLeftLeg: ModelPart
   public val foreRightLeg: ModelPart
   public val hindLeftLeg: ModelPart
   public val hindRightLeg: ModelPart
}
