package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WingFlapIdleAnimation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity

public interface BiWingedFrame : ModelFrame {
   public val leftWing: ModelPart
   public val rightWing: ModelPart

   public open fun <T : Entity> wingFlap(flapFunction: (Float) -> Float, timeVariable: (PoseableEntityState<Any>?, Float, Float) -> Float?, axis: Int): WingFlapIdleAnimation<
         Any
      > {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T extends Entity> wingFlap(
         `$this`: BiWingedFrame,
         flapFunction: (java.lang.Float?) -> java.lang.Float,
         timeVariable: (PoseableEntityState<T>?, java.lang.Float?, java.lang.Float?) -> java.lang.Float,
         axis: Int
      ): WingFlapIdleAnimation<T> {
         return new WingFlapIdleAnimation<>(`$this`, flapFunction, timeVariable, axis);
      }
   }
}
