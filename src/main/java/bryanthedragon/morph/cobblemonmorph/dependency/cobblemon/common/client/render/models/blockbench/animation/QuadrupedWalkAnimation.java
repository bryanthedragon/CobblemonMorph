package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.QuadrupedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity

public class QuadrupedWalkAnimation<T extends Entity>(frame: ModelFrame,
   legFrontLeft: Bone?,
   legFrontRight: Bone?,
   legBackLeft: Bone?,
   legBackRight: Bone?,
   periodMultiplier: Float = 0.6662F,
   amplitudeMultiplier: Float = 1.4F
) : StatelessAnimation(frame) {
   public final val amplitudeMultiplier: Float
   public final val legBackLeft: Bone?
   public final val legBackRight: Bone?
   public final val legFrontLeft: Bone?
   public final val legFrontRight: Bone?
   public final val periodMultiplier: Float
   public open val targetFrame: Class<ModelFrame>

   init {
      this.legFrontLeft = legFrontLeft;
      this.legFrontRight = legFrontRight;
      this.legBackLeft = legBackLeft;
      this.legBackRight = legBackRight;
      this.periodMultiplier = periodMultiplier;
      this.amplitudeMultiplier = amplitudeMultiplier;
      this.targetFrame = ModelFrame::class.java;
   }

   public constructor(frame: QuadrupedFrame, periodMultiplier: Float = 0.6662F, amplitudeMultiplier: Float = 1.4F) : this(
         frame,
         frame.getForeLeftLeg() as Bone,
         frame.getForeRightLeg() as Bone,
         frame.getHindLeftLeg() as Bone,
         frame.getHindRightLeg() as Bone,
         periodMultiplier,
         amplitudeMultiplier
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
      if (this.legBackRight != null) {
         if (this.legBackLeft != null) {
            val hindLeftLeg: Bone = this.legBackLeft;
            if (this.legFrontRight != null) {
               val foreRightLeg: Bone = this.legFrontRight;
               if (this.legFrontLeft != null) {
                  val foreLeftLeg: Bone = this.legFrontLeft;
                  ModelPartExtensionsKt.addRotation(
                     this.legBackRight, 0, Mth.m_14089_(limbSwing * this.periodMultiplier) * limbSwingAmount * this.amplitudeMultiplier * intensity
                  );
                  ModelPartExtensionsKt.addRotation(
                     hindLeftLeg, 0, Mth.m_14089_(limbSwing * this.periodMultiplier + (float) Math.PI) * limbSwingAmount * this.amplitudeMultiplier * intensity
                  );
                  ModelPartExtensionsKt.addRotation(
                     foreRightLeg,
                     0,
                     Mth.m_14089_(limbSwing * this.periodMultiplier + (float) Math.PI) * limbSwingAmount * this.amplitudeMultiplier * intensity
                  );
                  ModelPartExtensionsKt.addRotation(
                     foreLeftLeg, 0, Mth.m_14089_(limbSwing * this.periodMultiplier) * limbSwingAmount * this.amplitudeMultiplier * intensity
                  );
               }
            }
         }
      }
   }
}
