package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonObject
import net.minecraft.nbt.CompoundTag

public interface SpeciesFeatureProvider<T extends SpeciesFeature> {
   public abstract operator fun invoke(pokemon: Pokemon): Any? {
   }

   public abstract operator fun invoke(nbt: CompoundTag): Any? {
   }

   public abstract operator fun invoke(json: JsonObject): Any? {
   }
}
