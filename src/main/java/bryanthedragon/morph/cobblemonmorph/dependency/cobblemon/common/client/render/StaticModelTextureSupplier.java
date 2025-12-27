package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render

import net.minecraft.resources.ResourceLocation

public class StaticModelTextureSupplier(texture: ResourceLocation) : ModelTextureSupplier {
   public final val texture: ResourceLocation

   init {
      this.texture = texture;
   }

   public override operator fun invoke(animationSeconds: Float): ResourceLocation {
      return this.texture;
   }
}
