package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nCascadeAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CascadeAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/animation/CascadeAnimation\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,55:1\n13644#2,3:56\n*S KotlinDebug\n*F\n+ 1 CascadeAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/animation/CascadeAnimation\n*L\n35#1:56,3\n*E\n"])
public class CascadeAnimation<T extends Entity>(frame: ModelFrame, rootFunction: (Float) -> Float, amplitudeFunction: (Int) -> Float, vararg segments: Any) : StatelessAnimation(
      frame
   ) {
   public final val amplitudeFunction: (Int) -> Float
   public final val rootFunction: (Float) -> Float
   public final val segments: Array<ModelPart>
   public open val targetFrame: Class<ModelFrame>

   init {
      this.rootFunction = rootFunction;
      this.amplitudeFunction = amplitudeFunction;
      this.segments = segments;
      this.targetFrame = ModelFrame::class.java;
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
      var `index$iv`: Int = 0;

      val `$this$forEachIndexed$iv`: Any;
      for (Object item$iv : $this$forEachIndexed$iv) {
         ((ModelPart)`item$iv`).f_104204_ = ((ModelPart)`item$iv`).f_104204_
            + (this.rootFunction.invoke(ageInTicks) as java.lang.Number).floatValue()
               * (this.amplitudeFunction.invoke(`index$iv`++ + 1) as java.lang.Number).floatValue()
               * intensity;
      }
   }
}
