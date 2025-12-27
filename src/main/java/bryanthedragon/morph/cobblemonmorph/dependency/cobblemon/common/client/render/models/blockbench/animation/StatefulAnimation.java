package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import net.minecraft.world.entity.Entity

public interface StatefulAnimation<T extends Entity, F extends ModelFrame> {
   public val duration: Float
   public val isTransform: Boolean

   public abstract fun run(
      entity: Any?,
      model: PoseableEntityModel<Any>,
      state: PoseableEntityState<Any>,
      limbSwing: Float,
      limbSwingAmount: Float,
      ageInTicks: Float,
      headYaw: Float,
      headPitch: Float,
      intensity: Float
   ): Boolean {
   }

   public open fun applyEffects(entity: Any, state: PoseableEntityState<Any>, previousSeconds: Float, newSeconds: Float) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T extends Entity, F extends ModelFrame> applyEffects(
         `$this`: StatefulAnimation<T, F>, entity: T, state: PoseableEntityState<T>, previousSeconds: Float, newSeconds: Float
      ) {
      }
   }
}
