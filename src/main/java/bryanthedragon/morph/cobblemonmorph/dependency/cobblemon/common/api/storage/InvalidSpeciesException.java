package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage

import net.minecraft.resources.ResourceLocation

public class InvalidSpeciesException(identifier: ResourceLocation) : IllegalStateException("Invalid species: $identifier") {
   public final val identifier: ResourceLocation

   init {
      this.identifier = identifier;
   }
}
