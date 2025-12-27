package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BiWingedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import kotlin.jvm.functions.Function3
import net.minecraft.world.entity.Entity

public class WingFlapIdleAnimation<T extends Entity>(frame: ModelFrame,
   leftWing: Bone?,
   rightWing: Bone?,
   rotation: (Float) -> Float,
   timeVariable: (PoseableEntityState<Any>?, Float, Float) -> Float? = <unrepresentable>.INSTANCE as Function3,
   axis: Int
) : StatelessAnimation(frame) {
   public final val axis: Int
   public final val leftWing: Bone?
   public final val rightWing: Bone?
   public final val rotation: (Float) -> Float
   public open val targetFrame: Class<ModelFrame>
   public final val timeVariable: (PoseableEntityState<Any>?, Float, Float) -> Float?

   init {
      this.leftWing = leftWing;
      this.rightWing = rightWing;
      this.rotation = rotation;
      this.timeVariable = timeVariable;
      this.axis = axis;
      this.targetFrame = ModelFrame::class.java;
   }

   public constructor(frame: BiWingedFrame, flapFunction: (Float) -> Float, timeVariable: (PoseableEntityState<Any>?, Float, Float) -> Float?, axis: Int) : this(
         frame, frame.getLeftWing() as Bone, frame.getRightWing() as Bone, flapFunction, timeVariable, axis
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
      val var10000: java.lang.Float = this.timeVariable.invoke(state, limbSwing, ageInTicks) as java.lang.Float;
      val angle: Float = (this.rotation.invoke(var10000 ?: 0.0F) as java.lang.Number).floatValue();
      if (this.leftWing != null) {
         ModelPartExtensionsKt.addRotation(this.leftWing, this.axis, angle);
      }

      if (this.rightWing != null) {
         ModelPartExtensionsKt.addRotation(this.rightWing, this.axis, -angle);
      }
   }
}
