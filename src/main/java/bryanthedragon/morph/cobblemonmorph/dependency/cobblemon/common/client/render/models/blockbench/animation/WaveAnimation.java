package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nWaveAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WaveAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveAnimation\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,103:1\n1#2:104\n11335#3:105\n11670#3,3:106\n*S KotlinDebug\n*F\n+ 1 WaveAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveAnimation\n*L\n64#1:105\n64#1:106,3\n*E\n"])
public class WaveAnimation<T extends Entity>(frame: ModelFrame,
   waveFunction: (Float) -> Float,
   oscillationsScalar: Float,
   head: ModelPart,
   headLength: Float,
   moveHead: Boolean = false,
   rotationAxis: Int,
   motionAxis: Int,
   basedOnLimbSwing: Boolean = false,
   vararg segments: Any
) : StatelessAnimation(frame) {
   public final val basedOnLimbSwing: Boolean
   public final val head: ModelPart
   public final val headLength: Float
   public final val motionAxis: Int
   public final val moveHead: Boolean
   public final val oscillationsScalar: Float
   public final val rotationAxis: Int
   public final val segments: Array<WaveSegment>
   public open val targetFrame: Class<ModelFrame>
   public final val waveFunction: (Float) -> Float

   init {
      this.waveFunction = waveFunction;
      this.oscillationsScalar = oscillationsScalar;
      this.head = head;
      this.headLength = headLength;
      this.moveHead = moveHead;
      this.rotationAxis = rotationAxis;
      this.motionAxis = motionAxis;
      this.basedOnLimbSwing = basedOnLimbSwing;
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
      val t: Float = if (this.basedOnLimbSwing) limbSwing else (if (entity != null) model.getState(entity).getAnimationSeconds() else 0.0F);
      val previousSegmentLength: Array<WaveSegment> = this.segments;
      val var24: Float = this.headLength;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(this.segments.length);

      for (Object item$iv$iv : previousSegmentLength) {
         `destination$iv$iv`.add(((WaveSegment)t1).getLength());
      }

      var totalTimeDisplacement: Float = (var24 + CollectionsKt.sumOfFloat(`destination$iv$iv` as java.util.List)) / this.oscillationsScalar;
      if (this.moveHead) {
         ModelPartExtensionsKt.addPosition(
            this.head,
            this.motionAxis,
            -((this.waveFunction.invoke(t + totalTimeDisplacement - this.headLength / this.oscillationsScalar) as java.lang.Number).floatValue() * (float)16)
               * intensity
         );
      }

      totalTimeDisplacement = totalTimeDisplacement - this.headLength / this.oscillationsScalar;
      var var28: Float = this.headLength;
      var var29: Float = 0.0F;

      for (WaveSegment segment : this.segments) {
         val var35: Float = totalTimeDisplacement + var28 / 2 / this.oscillationsScalar;
         val var36: Float = totalTimeDisplacement - var34.getLength() / 2 / this.oscillationsScalar;
         val theta: Float = (float)Math.atan(
            (double)(
               ((this.waveFunction.invoke(t + var36) as java.lang.Number).floatValue() - (this.waveFunction.invoke(t + var35) as java.lang.Number).floatValue())
                  / (var35 - var36)
            )
         );
         ModelPartExtensionsKt.addRotation(var34.getModelPart() as Bone, this.rotationAxis, (theta - var29) * intensity);
         var29 = theta;
         var28 = var34.getLength();
         totalTimeDisplacement -= var34.getLength() / this.oscillationsScalar;
         if (totalTimeDisplacement < 0.0F) {
            totalTimeDisplacement = 0.0F;
         }
      }
   }
}
