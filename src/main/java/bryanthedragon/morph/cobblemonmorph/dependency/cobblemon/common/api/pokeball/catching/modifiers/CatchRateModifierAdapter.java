package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import kotlin.reflect.KClass

public interface CatchRateModifierAdapter : JsonDeserializer<CatchRateModifier>, JsonSerializer<CatchRateModifier> {
   public abstract fun <T : CatchRateModifier> registerType(id: String, type: KClass<Any>) {
   }
}
