package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.adapter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthFactor
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import kotlin.reflect.KClass
import net.minecraft.resources.ResourceLocation

public interface GrowthFactorAdapter : JsonDeserializer<GrowthFactor>, JsonSerializer<GrowthFactor> {
   public abstract fun register(type: KClass<out GrowthFactor>, identifier: ResourceLocation) {
   }
}
