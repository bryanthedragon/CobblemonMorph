package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement

public class RegisteredSpawnDetail<T extends SpawnDetail>(detailClass: Class<Any>) {
   public final val detailClass: Class<Any>

   init {
      this.detailClass = detailClass;
   }

   public fun deserializeDetail(element: JsonElement, ctx: JsonDeserializationContext): Any {
      val var10000: Any = ctx.deserialize(element, this.detailClass);
      return (T)var10000;
   }
}
