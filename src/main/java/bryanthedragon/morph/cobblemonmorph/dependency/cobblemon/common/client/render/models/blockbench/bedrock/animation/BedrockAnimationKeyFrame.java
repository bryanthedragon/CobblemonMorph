package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

public abstract class BedrockAnimationKeyFrame {
   public final val interpolationType: InterpolationType
   public abstract val post: MolangBoneValue
   public abstract val pre: MolangBoneValue
   public final val time: Double
   public final val transformation: Transformation

   open fun BedrockAnimationKeyFrame(time: Double, transformation: Transformation, interpolationType: InterpolationType) {
      this.time = time;
      this.transformation = transformation;
      this.interpolationType = interpolationType;
   }
}
