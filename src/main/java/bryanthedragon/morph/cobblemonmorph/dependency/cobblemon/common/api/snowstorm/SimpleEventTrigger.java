package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import net.minecraft.network.FriendlyByteBuf

public class SimpleEventTrigger(event: String) : Encodable, Decodable {
   public final var event: String

   init {
      this.event = event;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.event);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      val var10001: java.lang.String = buffer.m_130277_();
      this.event = var10001;
   }

   public fun trigger(storm: ParticleStorm, particle: SnowstormParticle?) {
      val var10000: ParticleEvent = storm.getEffect().getEvents().get(this.event);
      if (var10000 != null) {
         var10000.run(storm, particle);
      }
   }

   @JvmStatic
   fun `CODEC$lambda$1$lambda$0`(it: SimpleEventTrigger): java.lang.String {
      return it.event;
   }

   @JvmStatic
   fun `CODEC$lambda$1`(instance: Instance): App {
      return instance.group(PrimitiveCodec.STRING.fieldOf("event").forGetter(SimpleEventTrigger::CODEC$lambda$1$lambda$0) as App)
         .apply(instance as Applicative, SimpleEventTrigger::new);
   }

   public companion object {
      public final val CODEC: Codec<SimpleEventTrigger>
   }
}
