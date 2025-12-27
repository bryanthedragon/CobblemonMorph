package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render

import net.minecraft.resources.ResourceLocation

public fun interface ModelTextureSupplier {
   public abstract operator fun invoke(animationSeconds: Float): ResourceLocation {
   }
}
