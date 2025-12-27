package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.BufferSerializer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.DataSerializer
import com.google.gson.JsonElement
import net.minecraft.nbt.Tag

public interface EvolutionProxy<C extends EvolutionLike, S extends EvolutionLike> : DataSerializer<Tag, JsonElement>, BufferSerializer {
   public abstract fun isClient(): Boolean {
   }

   public abstract fun current(): EvolutionController<out EvolutionLike> {
   }

   public abstract fun client(): EvolutionController<Any> {
   }

   public abstract fun server(): EvolutionController<Any> {
   }
}
