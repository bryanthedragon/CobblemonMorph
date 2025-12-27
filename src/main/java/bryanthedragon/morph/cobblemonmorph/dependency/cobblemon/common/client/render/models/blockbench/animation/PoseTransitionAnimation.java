package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nPoseTransitionAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PoseTransitionAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/animation/PoseTransitionAnimation\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,82:1\n13579#2,2:83\n13579#2,2:85\n*S KotlinDebug\n*F\n+ 1 PoseTransitionAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/animation/PoseTransitionAnimation\n*L\n71#1:83,2\n76#1:85,2\n*E\n"])
public class PoseTransitionAnimation<T extends Entity>(beforePose: Pose<Any, *>,
      afterPose: Pose<Any, *>,
      durationTicks: Int = 20,
      curve: (Float) -> Float = WaveFunctionKt.sineFunction(0.5F, 2.0F, 0.5F, 0.5F)
   ) :
   StatefulAnimation<T, ModelFrame> {
   public final val afterPose: Pose<Any, *>
   public final val beforePose: Pose<Any, *>
   public final val curve: (Float) -> Float
   public open val duration: Float
   public final val durationTicks: Int
   public final var endTime: Float
   public final var initialized: Boolean
   public open val isTransform: Boolean
   public final var startTime: Float

   init {
      this.beforePose = beforePose;
      this.afterPose = afterPose;
      this.durationTicks = durationTicks;
      this.curve = curve;
      this.isTransform = true;
      this.duration = this.durationTicks / 20.0F;
   }

   public fun initialize(state: PoseableEntityState<Any>) {
      this.startTime = state.getAnimationSeconds();
      this.endTime = this.startTime + this.durationTicks / 20.0F;
      this.initialized = true;
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
      if (!this.initialized) {
         this.initialize(state);
      }

      val ratio: Float = java.lang.Float.min((state.getAnimationSeconds() - this.startTime) / (this.endTime - this.startTime), 1.0F);
      val newIntensity: Float = (RangesKt.coerceIn(this.curve.invoke(ratio) as java.lang.Comparable, RangesKt.rangeTo(0.0F, 1.0F)) as java.lang.Number)
         .floatValue();
      val oldIntensity: Float = 1 - newIntensity;
      model.setDefault();
      model.applyPose(this.beforePose.getPoseName(), oldIntensity);

      val `$this$forEach$iv`: Any;
      for (Object element$iv : $this$forEach$iv) {
         ((StatelessAnimation<Entity, F>)`element$iv`).apply(entity, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, oldIntensity);
      }

      model.applyPose(this.afterPose.getPoseName(), newIntensity);

      for (Object element$iv : $this$forEach$iv) {
         ((StatelessAnimation<Entity, F>)var27).apply(entity, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, newIntensity);
      }

      return ratio < 1.0F;
   }

   override fun applyEffects(entity: T, state: PoseableEntityState<T>, previousSeconds: Float, newSeconds: Float) {
      StatefulAnimation.DefaultImpls.applyEffects(this, (T)entity, state, previousSeconds, newSeconds);
   }
}
