package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager

public interface JsonDataRegistry<T> : DataRegistry {
   public val gson: Gson
   public val resourcePath: String
   public val typeToken: TypeToken<Any>

   public override fun reload(manager: ResourceManager) {
   }

   public abstract fun reload(data: Map<ResourceLocation, Any>) {
   }

   public companion object {
      public const val JSON_EXTENSION: String = ".json"
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T> reload(`$this`: JsonDataRegistry<T>, manager: ResourceManager) {
         `$this`.reload(Cobblemon.INSTANCE.getImplementation().reloadJsonRegistry(`$this`, manager));
      }
   }
}
