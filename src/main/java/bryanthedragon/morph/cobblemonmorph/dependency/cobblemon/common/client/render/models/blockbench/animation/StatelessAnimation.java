package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import net.minecraft.world.entity.Entity

public abstract class StatelessAnimation<T extends Entity, F extends ModelFrame> {
   public final val frame: Any
   public open var labels: Set<String>
   public abstract val targetFrame: Class<Any>

   open fun StatelessAnimation(frame: F) {
      this.frame = (F)frame;
      this.labels = SetsKt.emptySet();
   }

   protected abstract fun setAngles(
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
   }

   public fun apply(
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
      this.setAngles((T)entity, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, intensity);
   }

   public open fun applyEffects(entity: Any, state: PoseableEntityState<Any>, previousSeconds: Float, newSeconds: Float) {
   }
}
