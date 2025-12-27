package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BimanualFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity

public class BimanualSwingAnimation<T extends Entity>(frame: ModelFrame,
   swingPeriodMultiplier: Float = 0.6662F,
   amplitudeMultiplier: Float = 1.0F,
   leftArm: Bone?,
   rightArm: Bone?
) : StatelessAnimation(frame) {
   public final val amplitudeMultiplier: Float
   public final val leftArm: Bone?
   public final val rightArm: Bone?
   public final val swingPeriodMultiplier: Float
   public open val targetFrame: Class<ModelFrame>

   init {
      this.swingPeriodMultiplier = swingPeriodMultiplier;
      this.amplitudeMultiplier = amplitudeMultiplier;
      this.leftArm = leftArm;
      this.rightArm = rightArm;
      this.targetFrame = ModelFrame::class.java;
   }

   public constructor(frame: BimanualFrame, swingPeriodMultiplier: Float = 0.6662F, amplitudeMultiplier: Float = 1.0F) : this(
         frame, swingPeriodMultiplier, amplitudeMultiplier, frame.getLeftArm() as Bone, frame.getRightArm() as Bone
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
      if (this.rightArm != null) {
         ModelPartExtensionsKt.addRotation(
            this.rightArm, 1, Mth.m_14089_(limbSwing * this.swingPeriodMultiplier) * limbSwingAmount * this.amplitudeMultiplier * intensity
         );
      }

      if (this.leftArm != null) {
         ModelPartExtensionsKt.addRotation(
            this.leftArm, 1, Mth.m_14089_(limbSwing * this.swingPeriodMultiplier) * limbSwingAmount * this.amplitudeMultiplier * intensity
         );
      }

      if (this.rightArm != null) {
         ModelPartExtensionsKt.addRotation(this.rightArm, 2, 1.0F * (Mth.m_14089_(ageInTicks * 0.09F) * 0.05F + 0.05F) * intensity);
      }

      if (this.rightArm != null) {
         ModelPartExtensionsKt.addRotation(this.rightArm, 1, Mth.m_14031_(ageInTicks * 0.067F) * 0.05F * intensity);
      }

      if (this.leftArm != null) {
         ModelPartExtensionsKt.addRotation(this.leftArm, 2, -1.0F * (Mth.m_14089_(ageInTicks * 0.09F) * 0.05F + 0.05F) * intensity);
      }

      if (this.leftArm != null) {
         ModelPartExtensionsKt.addRotation(this.leftArm, 1, -1.0F * Mth.m_14031_(ageInTicks * 0.067F) * 0.05F * intensity);
      }
   }
}
