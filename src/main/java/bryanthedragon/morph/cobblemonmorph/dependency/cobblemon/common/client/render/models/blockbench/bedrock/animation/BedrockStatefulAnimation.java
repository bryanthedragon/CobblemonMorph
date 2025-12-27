package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import net.minecraft.world.entity.Entity

public open class BedrockStatefulAnimation<T extends Entity>(animation: BedrockAnimation) : StatefulAnimation<T, ModelFrame> {
   private final var afterAction: (Any, PoseableEntityState<Any>) -> Unit
   public final val animation: BedrockAnimation
   public open val duration: Float

   public open val isTransform: Boolean
      public open get() {
         return this.isTransformAnimation;
      }


   public final var isTransformAnimation: Boolean
   public final var startedSeconds: Float

   init {
      this.animation = animation;
      this.startedSeconds = -1.0F;
      this.duration = (float)this.animation.getAnimationLength();
      this.afterAction = <unrepresentable>.INSTANCE;
   }

   public fun andThen(action: (Any, PoseableEntityState<Any>) -> Unit): BedrockStatefulAnimation<Any> {
      this.afterAction = action;
      return this;
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
      if (this.startedSeconds == -1.0F) {
         this.startedSeconds = state.getAnimationSeconds();
      }

      val var10: Boolean = this.animation.run(model, state, state.getAnimationSeconds() - this.startedSeconds, intensity);
      if (!var10 && entity != null) {
         this.afterAction.invoke(entity, state);
      }

      return var10;
   }

   public override fun applyEffects(entity: Any, state: PoseableEntityState<Any>, previousSeconds: Float, newSeconds: Float) {
      this.animation.applyEffects(entity, state, previousSeconds - this.startedSeconds, newSeconds - this.startedSeconds);
   }
}
