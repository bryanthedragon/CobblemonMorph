package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

public class JumpBedrockAnimationKeyFrame(time: Double,
   transformation: Transformation,
   interpolationType: InterpolationType,
   pre: MolangBoneValue,
   post: MolangBoneValue
) : BedrockAnimationKeyFrame(time, transformation, interpolationType) {
   public open val post: MolangBoneValue
   public open val pre: MolangBoneValue

   init {
      this.pre = pre;
      this.post = post;
   }
}
