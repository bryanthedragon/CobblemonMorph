package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

public class SimpleBedrockAnimationKeyFrame(time: Double, transformation: Transformation, interpolationType: InterpolationType, data: MolangBoneValue) : BedrockAnimationKeyFrame(
      time, transformation, interpolationType
   ) {
   public final val data: MolangBoneValue
   public open val post: MolangBoneValue
   public open val pre: MolangBoneValue

   init {
      this.data = data;
      this.pre = this.data;
      this.post = this.data;
   }
}
