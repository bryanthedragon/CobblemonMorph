package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nBedrockStatelessAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockStatelessAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatelessAnimation\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,44:1\n800#2,11:45\n1#3:56\n*S KotlinDebug\n*F\n+ 1 BedrockStatelessAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatelessAnimation\n*L\n29#1:45,11\n*E\n"])
public class BedrockStatelessAnimation<T extends Entity>(frame: ModelFrame, animation: BedrockAnimation) : StatelessAnimation(frame) {
   public final val animation: BedrockAnimation
   public final val particleKeyFrames: List<BedrockParticleKeyframe>
   public open val targetFrame: Class<ModelFrame>

   init {
      this.animation = animation;
      this.targetFrame = ModelFrame::class.java;
      val `$this$filterIsInstance$iv`: java.lang.Iterable = this.animation.getEffects();
      val `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filterIsInstance$iv) {
         if (`element$iv$iv` is BedrockParticleKeyframe) {
            `destination$iv$iv`.add(`element$iv$iv`);
         }
      }

      this.particleKeyFrames = `destination$iv$iv` as MutableList<BedrockParticleKeyframe>;
   }

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
      this.animation.run(model, state, if (state != null) state.getAnimationSeconds() else 0.0F, intensity);
   }

   public override fun applyEffects(entity: Any, state: PoseableEntityState<Any>, previousSeconds: Float, newSeconds: Float) {
      val loopedPreviousSeconds: java.lang.Double = this.animation.getAnimationLength();
      val loopedNewSeconds: Double = loopedPreviousSeconds.doubleValue();
      val var10000: java.lang.Double = if (!(loopedNewSeconds <= 0.0)) loopedPreviousSeconds else null;
      val var22: java.lang.Float;
      if ((if (!(loopedNewSeconds <= 0.0)) loopedPreviousSeconds else null) != null) {
         var22 = (float)var10000.doubleValue();
      } else {
         val var16: java.util.Iterator = this.animation.getEffects().iterator();
         val var23: java.lang.Float;
         if (!var16.hasNext()) {
            var23 = null;
         } else {
            var var18: Float = (var16.next() as BedrockEffectKeyframe).getSeconds();

            while (var16.hasNext()) {
               var18 = Math.max(var18, (var16.next() as BedrockEffectKeyframe).getSeconds());
            }

            var23 = var18;
         }

         var22 = if (var23 != null) (if (var23.floatValue() != 0.0F) var23 else null) else null;
      }

      val var6: Pair = if (var22 != null) TuplesKt.to(previousSeconds % var22, newSeconds % var22) else TuplesKt.to(previousSeconds, newSeconds);
      this.animation.applyEffects(entity, state, (var6.component1() as java.lang.Number).floatValue(), (var6.component2() as java.lang.Number).floatValue());
   }
}
