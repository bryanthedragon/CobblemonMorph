package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types

import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

public class ElementalType(name: String,
   displayName: MutableComponent,
   hue: Int,
   textureXMultiplier: Int,
   resourceLocation: ResourceLocation = new ResourceLocation("cobblemon", "ui/types.png")
) {
   public final val displayName: MutableComponent
   public final val hue: Int
   public final val name: String
   public final val resourceLocation: ResourceLocation
   public final val textureXMultiplier: Int

   init {
      this.name = name;
      this.displayName = displayName;
      this.hue = hue;
      this.textureXMultiplier = textureXMultiplier;
      this.resourceLocation = resourceLocation;
   }
}
