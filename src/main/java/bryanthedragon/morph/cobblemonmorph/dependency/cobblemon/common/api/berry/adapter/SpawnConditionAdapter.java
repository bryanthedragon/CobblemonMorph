package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.adapter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.BerrySpawnCondition
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import kotlin.reflect.KClass
import net.minecraft.resources.ResourceLocation

public interface SpawnConditionAdapter : JsonDeserializer<BerrySpawnCondition>, JsonSerializer<BerrySpawnCondition> {
   public abstract fun register(type: KClass<out BerrySpawnCondition>, identifier: ResourceLocation) {
   }
}
