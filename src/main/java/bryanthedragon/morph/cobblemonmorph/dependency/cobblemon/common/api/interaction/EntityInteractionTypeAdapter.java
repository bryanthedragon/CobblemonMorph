package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction

import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import kotlin.reflect.KClass
import net.minecraft.resources.ResourceLocation

public interface EntityInteractionTypeAdapter<T extends EntityInteraction<?>> : JsonDeserializer<T>, JsonSerializer<T> {
   public abstract fun registerInteraction(identifier: ResourceLocation, type: KClass<out Any>) {
   }
}
