package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import java.util.function.Consumer
import kotlin.jvm.functions.Function1
import net.minecraft.world.entity.Entity

public class PrimaryAnimation<T extends Entity>(animation: StatefulAnimation<Any, *>,
      curve: (Float) -> Float = <unrepresentable>.INSTANCE as Function1,
      excludedLabels: Set<String> = SetsKt.emptySet(),
      isTransform: Boolean = false
   ) :
   StatefulAnimation<T, ModelFrame> {
   public final var afterAction: Consumer<Unit>
   public final val animation: StatefulAnimation<Any, *>
   public final var curve: (Float) -> Float
   public open val duration: Float
   public final val excludedLabels: Set<String>
   public open val isTransform: Boolean
   public final var started: Float

   init {
      this.animation = animation;
      this.curve = curve;
      this.excludedLabels = excludedLabels;
      this.isTransform = isTransform;
      this.started = -1.0F;
      this.duration = this.animation.getDuration();
      this.afterAction = PrimaryAnimation::afterAction$lambda$0;
   }

   public override fun run(
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
      return this.animation.run((T)entity, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, intensity);
   }

   public fun prevents(idleAnimation: StatelessAnimation<Any, *>): Boolean {
      return CollectionsKt.intersect(idleAnimation.getLabels(), this.excludedLabels).isEmpty() && !this.excludedLabels.contains("all");
   }

   override fun applyEffects(entity: T, state: PoseableEntityState<T>, previousSeconds: Float, newSeconds: Float) {
      StatefulAnimation.DefaultImpls.applyEffects(this, (T)entity, state, previousSeconds, newSeconds);
   }

   @JvmStatic
   fun `afterAction$lambda$0`(it: Unit) {
   }
}
