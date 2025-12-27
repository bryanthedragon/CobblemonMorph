package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

public class DamageCategory(name: String,
   displayName: Component,
   textureXMultiplier: Int,
   resourceLocation: ResourceLocation = MiscUtilsKt.cobblemonResource("textures/gui/categories.png")
) {
   public final val displayName: Component
   public final val name: String
   public final val resourceLocation: ResourceLocation
   public final val textureXMultiplier: Int

   init {
      this.name = name;
      this.displayName = displayName;
      this.textureXMultiplier = textureXMultiplier;
      this.resourceLocation = resourceLocation;
   }
}
