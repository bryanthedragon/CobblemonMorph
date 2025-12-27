package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional

import com.google.gson.JsonElement
import kotlin.jvm.functions.Function1
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.NotNull

public open class RegistryLikeIdentifierCondition<T>(identifier: ResourceLocation) : RegistryLikeCondition<T> {
   public final val identifier: ResourceLocation

   init {
      this.identifier = identifier;
   }

   public override fun fits(t: Any, registry: Registry<Any>): Boolean {
      return registry.m_7981_(t) == this.identifier;
   }

   public companion object {
      public fun <T> resolver(constructor: (ResourceLocation) -> RegistryLikeIdentifierCondition<Any>): (JsonElement) -> RegistryLikeIdentifierCondition<Any>? {
         return (new Function1<JsonElement, RegistryLikeIdentifierCondition<T>>(constructor) {
            {
               super(1);
               this.$constructor = `$constructor`;
            }

            @NotNull
            public final RegistryLikeIdentifierCondition<T> invoke(@NotNull JsonElement it) {
               return this.$constructor.invoke(new ResourceLocation(it.getAsString())) as RegistryLikeIdentifierCondition<T>;
            }
         }) as (JsonElement?) -> RegistryLikeIdentifierCondition<T>;
      }
   }
}
