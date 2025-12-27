package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import com.google.gson.JsonObject
import net.minecraft.nbt.CompoundTag

public interface SpeciesFeature {
   public val name: String

   public abstract fun saveToNBT(pokemonNBT: CompoundTag): CompoundTag {
   }

   public abstract fun loadFromNBT(pokemonNBT: CompoundTag): SpeciesFeature {
   }

   public abstract fun saveToJSON(pokemonJSON: JsonObject): JsonObject {
   }

   public abstract fun loadFromJSON(pokemonJSON: JsonObject): SpeciesFeature {
   }
}
