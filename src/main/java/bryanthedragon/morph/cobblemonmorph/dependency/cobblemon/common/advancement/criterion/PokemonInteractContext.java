package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import net.minecraft.resources.ResourceLocation

public open class PokemonInteractContext(type: ResourceLocation, item: ResourceLocation) {
   public final var item: ResourceLocation
   public final var type: ResourceLocation

   init {
      this.type = type;
      this.item = item;
   }
}
