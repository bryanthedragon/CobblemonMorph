package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity

public class BipedWalkAnimation<T extends Entity>(frame: ModelFrame,
   periodMultiplier: Float = 0.6662F,
   amplitudeMultiplier: Float = 1.4F,
   leftLeg: Bone?,
   rightLeg: Bone?
) : StatelessAnimation(frame) {
   public final val amplitudeMultiplier: Float
   public final val leftLeg: Bone?
   public final val periodMultiplier: Float
   public final val rightLeg: Bone?
   public open val targetFrame: Class<ModelFrame>

   init {
      this.periodMultiplier = periodMultiplier;
      this.amplitudeMultiplier = amplitudeMultiplier;
      this.leftLeg = leftLeg;
      this.rightLeg = rightLeg;
      this.targetFrame = ModelFrame::class.java;
   }

   public constructor(frame: BipedFrame, periodMultiplier: Float = 0.6662F, amplitudeMultiplier: Float = 1.4F) : this(
         frame, periodMultiplier, amplitudeMultiplier, frame.getLeftLeg() as Bone, frame.getRightLeg() as Bone
      )
   protected override fun setAngles(
      entity: Any?,
      model: PoseableEntityModel<Any>,
      state: PoseableEntityState<Any>?,
      limbSwing: Float,
      limbSwingAmount: Float,
      ageInTicks: Float,
      headYaw: Float,
      headPitch: Float,
      intensity: Float
   ) {
      if (this.rightLeg != null) {
         ModelPartExtensionsKt.addRotation(
            this.rightLeg, 0, Mth.m_14089_(limbSwing * this.periodMultiplier + (float) Math.PI) * limbSwingAmount * this.amplitudeMultiplier * intensity
         );
      }

      if (this.leftLeg != null) {
         ModelPartExtensionsKt.addRotation(
            this.leftLeg, 0, Mth.m_14089_(limbSwing * this.periodMultiplier) * limbSwingAmount * this.amplitudeMultiplier * intensity
         );
      }
   }
}
