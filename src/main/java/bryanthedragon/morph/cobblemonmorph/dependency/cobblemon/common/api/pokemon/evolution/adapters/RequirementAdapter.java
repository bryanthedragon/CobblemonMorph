package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import kotlin.reflect.KClass

public interface RequirementAdapter : JsonDeserializer<EvolutionRequirement>, JsonSerializer<EvolutionRequirement> {
   public abstract fun <T : EvolutionRequirement> registerType(id: String, type: KClass<Any>) {
   }
}
