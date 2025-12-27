package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.BufferSerializer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.DataSerializer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonElement
import kotlin.jvm.internal.markers.KMutableSet
import net.minecraft.nbt.Tag

public interface EvolutionController<T extends EvolutionLike> : java.util.Set<T>, DataSerializer<Tag, JsonElement>, BufferSerializer, KMutableSet {
   public val pokemon: Pokemon

   public abstract fun start(evolution: Any) {
   }

   public abstract fun progress(): Collection<EvolutionProgress<*>> {
   }

   public abstract fun <P : EvolutionProgress<*>> trackProgress(progress: Any): Any {
   }

   public abstract fun <P : EvolutionProgress<*>> progressFirstOrCreate(predicate: (EvolutionProgress<*>) -> Boolean, progressFactory: () -> Any): Any {
   }
}
