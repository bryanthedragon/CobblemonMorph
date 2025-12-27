package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import net.minecraft.resources.ResourceLocation

public open class EvolvePokemonContext(species: ResourceLocation, evolution: ResourceLocation, times: Int) : CountableContext(times) {
   public final val evolution: ResourceLocation
   public final val species: ResourceLocation

   init {
      this.species = species;
      this.evolution = evolution;
   }
}
