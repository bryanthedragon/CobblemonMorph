package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import net.minecraft.world.entity.Entity

public interface HeadedFrame : ModelFrame {
   public val head: Bone

   public open fun <T : Entity> singleBoneLook(
      invertX: Boolean = ...,
      invertY: Boolean = ...,
      disableX: Boolean = ...,
      disableY: Boolean = ...,
      pitchMultiplier: Float? = ...,
      yawMultiplier: Float? = ...,
      maxPitch: Float? = ...,
      minPitch: Float? = ...,
      maxYaw: Float? = ...,
      minYaw: Float? = ...
   ): SingleBoneLookAnimation<Any> {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T extends Entity> singleBoneLook(
         `$this`: HeadedFrame,
         invertX: Boolean,
         invertY: Boolean,
         disableX: Boolean,
         disableY: Boolean,
         pitchMultiplier: java.lang.Float?,
         yawMultiplier: java.lang.Float?,
         maxPitch: java.lang.Float?,
         minPitch: java.lang.Float?,
         maxYaw: java.lang.Float?,
         minYaw: java.lang.Float?
      ): SingleBoneLookAnimation<T> {
         return new SingleBoneLookAnimation<>(`$this`, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
      }
   }
}
