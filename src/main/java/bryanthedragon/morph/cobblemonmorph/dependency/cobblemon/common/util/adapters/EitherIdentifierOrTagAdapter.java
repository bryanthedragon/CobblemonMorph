package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.mojang.datafixers.util.Either
import java.lang.reflect.Type
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey

public class EitherIdentifierOrTagAdapter<E, T extends Registry<E>>(registryKey: ResourceKey<Any>) : JsonDeserializer<Either<ResourceLocation, TagKey<E>>> {
   public final val registryKey: ResourceKey<Any>

   init {
      this.registryKey = registryKey;
   }

   public open fun deserialize(element: JsonElement, type: Type, ctx: JsonDeserializationContext): Either<ResourceLocation, TagKey<Any>> {
      val string: java.lang.String = element.getAsString();
      val var6: Either;
      if (StringsKt.startsWith$default(string, "#", false, 2, null)) {
         val var10000: ResourceKey = this.registryKey;
         val var10003: java.lang.String = string.substring(1);
         val var5: Either = Either.right(TagKey.m_203882_(var10000, new ResourceLocation(var10003)));
         var6 = var5;
      } else {
         var6 = Either.left(new ResourceLocation(string));
      }

      return var6;
   }
}
