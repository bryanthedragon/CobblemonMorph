package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.DataSerializer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonObject
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation

public interface EvolutionProgress<T> : DataSerializer<CompoundTag, JsonObject> {
   public abstract fun id(): ResourceLocation {
   }

   public abstract fun currentProgress(): Any {
   }

   public abstract fun updateProgress(progress: Any) {
   }

   public abstract fun reset() {
   }

   public abstract fun shouldKeep(pokemon: Pokemon): Boolean {
   }
}
