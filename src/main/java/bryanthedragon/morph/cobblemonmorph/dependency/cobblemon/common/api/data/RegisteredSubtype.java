package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data

import com.mojang.serialization.Codec

public class RegisteredSubtype<T>(clazz: Class<out Any>, codec: Codec<out Any>) {
   public final val clazz: Class<out Any>
   public final val codec: Codec<out Any>

   init {
      this.clazz = clazz;
      this.codec = codec;
   }
}
