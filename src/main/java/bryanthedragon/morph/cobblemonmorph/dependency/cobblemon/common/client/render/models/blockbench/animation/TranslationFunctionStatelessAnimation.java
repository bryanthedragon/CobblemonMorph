package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import kotlin.jvm.functions.Function1
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity

public class TranslationFunctionStatelessAnimation<T extends Entity>(part: ModelPart,
   function: (Float) -> Float,
   axis: Int,
   timeVariable: (PoseableEntityState<Any>?, Float, Float) -> Float?,
   frame: ModelFrame
) : StatelessAnimation(frame) {
   public final val axis: Int
   public final val function: (Float) -> Float
   public final val part: ModelPart
   public open val targetFrame: Class<ModelFrame>
   public final val timeVariable: (PoseableEntityState<Any>?, Float, Float) -> Float?

   init {
      this.part = part;
      this.function = function;
      this.axis = axis;
      this.timeVariable = timeVariable;
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
      val var10000: ModelPart = this.part;
      val var10001: Int = this.axis;
      val var10002: Function1 = this.function;
      val var10003: java.lang.Float = this.timeVariable.invoke(state, limbSwing, ageInTicks) as java.lang.Float;
      ModelPartExtensionsKt.addPosition(var10000, var10001, (var10002.invoke(var10003 ?: 0.0F) as java.lang.Number).floatValue() * intensity);
   }
}
