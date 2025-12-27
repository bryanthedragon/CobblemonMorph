package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import kotlin.reflect.KClass

public interface EvolutionAdapter : JsonDeserializer<Evolution>, JsonSerializer<Evolution> {
   public abstract fun <T : Evolution> registerType(id: String, type: KClass<Any>) {
   }
}
