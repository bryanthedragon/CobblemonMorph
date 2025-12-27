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
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nParticleEventTrigger.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParticleEventTrigger.kt\ncom/cobblemon/mod/common/api/snowstorm/LoopingTravelDistanceEventTrigger\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,118:1\n1603#2,9:119\n1855#2:128\n1856#2:130\n1612#2:131\n1855#2,2:132\n1#3:129\n*S KotlinDebug\n*F\n+ 1 ParticleEventTrigger.kt\ncom/cobblemon/mod/common/api/snowstorm/LoopingTravelDistanceEventTrigger\n*L\n115#1:119,9\n115#1:128\n115#1:130\n115#1:131\n115#1:132,2\n115#1:129\n*E\n"])
public class LoopingTravelDistanceEventTrigger(distance: Double, events: MutableList<String>) : Encodable, Decodable {
   public final var distance: Double
   public final var events: MutableList<String>

   init {
      this.distance = distance;
      this.events = events;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeDouble(this.distance);
      buffer.m_236828_(this.events, LoopingTravelDistanceEventTrigger::encode$lambda$0);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      this.distance = buffer.readDouble();
      val var10001: java.util.List = buffer.m_236845_(LoopingTravelDistanceEventTrigger::decode$lambda$1);
      this.events = var10001;
   }

   public fun check(storm: ParticleStorm, particle: SnowstormParticle?, previousDistance: Double, currentDistance: Double) {
      if (previousDistance < this.distance && currentDistance >= this.distance) {
         val `$this$forEach$iv`: java.lang.Iterable = this.events;
         val `element$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            val var10000: ParticleEvent = storm.getEffect().getEvents().get(`element$iv$iv$iv` as java.lang.String);
            if (var10000 != null) {
               `element$iv`.add(var10000);
            }
         }

         for (Object element$ivx : $this$mapNotNull$iv) {
            (`element$ivx` as ParticleEvent).run(storm, particle);
         }
      }
   }

   @JvmStatic
   fun `encode$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, s: java.lang.String) {
      `$buffer`.m_130070_(s);
   }

   @JvmStatic
   fun `decode$lambda$1`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$4`(it: LoopingTravelDistanceEventTrigger): java.lang.Double {
      return it.distance;
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$5`(it: LoopingTravelDistanceEventTrigger): java.util.List {
      return it.events;
   }

   @JvmStatic
   fun `CODEC$lambda$6`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.DOUBLE.fieldOf("distance").forGetter(LoopingTravelDistanceEventTrigger::CODEC$lambda$6$lambda$4) as App,
            PrimitiveCodec.STRING.listOf().fieldOf("events").forGetter(LoopingTravelDistanceEventTrigger::CODEC$lambda$6$lambda$5) as App
         )
         .apply(instance as Applicative, LoopingTravelDistanceEventTrigger::new);
   }

   public companion object {
      public final val CODEC: Codec<LoopingTravelDistanceEventTrigger>
   }
}
