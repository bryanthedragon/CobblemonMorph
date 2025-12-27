package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.AngleExtensionsKt
import net.minecraft.world.entity.Entity

public class SingleBoneLookAnimation<T extends Entity>(frame: ModelFrame,
   bone: Bone?,
   pitchMultiplier: Float = 1.0F,
   yawMultiplier: Float = 1.0F,
   maxPitch: Float = 70.0F,
   minPitch: Float = -45.0F,
   maxYaw: Float = 45.0F,
   minYaw: Float = -45.0F
) : StatelessAnimation(frame) {
   public final val bone: Bone?
   public open var labels: Set<String>
   public final val maxPitch: Float
   public final val maxYaw: Float
   public final val minPitch: Float
   public final val minYaw: Float
   public final val pitchMultiplier: Float
   public open val targetFrame: Class<ModelFrame>
   public final val yawMultiplier: Float

   init {
      this.bone = bone;
      this.pitchMultiplier = pitchMultiplier;
      this.yawMultiplier = yawMultiplier;
      this.maxPitch = maxPitch;
      this.minPitch = minPitch;
      this.maxYaw = maxYaw;
      this.minYaw = minYaw;
      this.targetFrame = ModelFrame::class.java;
      this.labels = SetsKt.setOf("look");
   }

   public constructor(frame: HeadedFrame,
      invertX: Boolean,
      invertY: Boolean,
      disableX: Boolean,
      disableY: Boolean,
      pitchMultiplier: Float? = null,
      yawMultiplier: Float? = null,
      maxPitch: Float? = null,
      minPitch: Float? = null,
      maxYaw: Float? = null,
      minYaw: Float? = null
   ) : this(
         frame,
         frame.getHead(),
         pitchMultiplier ?: (if (disableX) 0.0F else (if (invertX) -1.0F else 1.0F)),
         yawMultiplier ?: (if (disableY) 0.0F else (if (invertY) -1.0F else 1.0F)),
         maxPitch ?: 70.0F,
         minPitch ?: -45.0F,
         maxYaw ?: 45.0F,
         minYaw ?: -45.0F
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
      if (this.bone != null) {
         val head: Bone = this.bone;
         val pitch: Float = this.pitchMultiplier * RangesKt.coerceIn(headPitch, this.minPitch, this.maxPitch);
         val yaw: Float = this.yawMultiplier * RangesKt.coerceIn(headYaw, this.minYaw, this.maxYaw);
         ModelPartExtensionsKt.addRotation(head, 0, AngleExtensionsKt.toRadians(pitch) * intensity);
         ModelPartExtensionsKt.addRotation(head, 1, AngleExtensionsKt.toRadians(yaw) * intensity);
      }
   }
}
