package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import net.minecraft.resources.ResourceLocation

public class EventSoundEffect(sound: ResourceLocation) {
   public final val sound: ResourceLocation

   init {
      this.sound = sound;
   }

   @JvmStatic
   fun `CODEC$lambda$1$lambda$0`(it: EventSoundEffect): ResourceLocation {
      return it.sound;
   }

   @JvmStatic
   fun `CODEC$lambda$1`(instance: Instance): App {
      return instance.group(ResourceLocation.f_135803_.fieldOf("sound").forGetter(EventSoundEffect::CODEC$lambda$1$lambda$0) as App)
         .apply(instance as Applicative, EventSoundEffect::new);
   }

   public companion object {
      public final val CODEC: Codec<EventSoundEffect>
   }
}
