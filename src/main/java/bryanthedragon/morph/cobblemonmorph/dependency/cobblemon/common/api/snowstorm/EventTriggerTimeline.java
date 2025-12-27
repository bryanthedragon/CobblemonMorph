package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.UnboundedMapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import java.util.ArrayList;
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nParticleEventTrigger.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParticleEventTrigger.kt\ncom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,118:1\n766#2:119\n857#2,2:120\n1360#2:122\n1446#2,5:123\n1855#2,2:128\n*S KotlinDebug\n*F\n+ 1 ParticleEventTrigger.kt\ncom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline\n*L\n78#1:119\n78#1:120,2\n78#1:122\n78#1:123,5\n79#1:128,2\n*E\n"])
public class EventTriggerTimeline(map: MutableMap<Double, MutableList<String>>) : Encodable, Decodable {
   public final var map: MutableMap<Double, MutableList<String>>

   init {
      this.map = map;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_236831_(this.map, EventTriggerTimeline::encode$lambda$0, EventTriggerTimeline::encode$lambda$2);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      val var10001: java.util.Map = buffer.m_236847_(EventTriggerTimeline::decode$lambda$3, EventTriggerTimeline::decode$lambda$5);
      this.map = var10001;
   }

   public fun check(storm: ParticleStorm, particle: SnowstormParticle?, previousTime: Double, newTime: Double) {
      var `$this$forEach$iv`: java.lang.Iterable = this.map.entrySet();
      var `element$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         val var17: Double = ((event as Entry).getKey() as java.lang.Number).doubleValue();
         if (previousTime <= var17 && var17 <= newTime) {
            `element$iv`.add(event);
         }
      }

      `$this$forEach$iv` = `element$iv` as java.util.List;
      `element$iv` = new ArrayList();

      for (Object element$iv$ivx : $this$filter$iv) {
         CollectionsKt.addAll(`element$iv`, (`element$iv$ivx` as Entry).getValue() as java.util.List);
      }

      for (Object element$ivx : $this$filter$iv) {
         val var10000: ParticleEvent = storm.getEffect().getEvents().get(`element$ivx` as java.lang.String);
         if (var10000 == null) {
            return;
         }

         var10000.run(storm, particle);
      }
   }

   @JvmStatic
   fun `encode$lambda$0`(pb: FriendlyByteBuf, k: java.lang.Double) {
      pb.writeDouble(k);
   }

   @JvmStatic
   fun `encode$lambda$2$lambda$1`(`$pb`: FriendlyByteBuf, var1: FriendlyByteBuf, s: java.lang.String) {
      `$pb`.m_130070_(s);
   }

   @JvmStatic
   fun `encode$lambda$2`(pb: FriendlyByteBuf, v: java.util.List) {
      pb.m_236828_(v, EventTriggerTimeline::encode$lambda$2$lambda$1);
   }

   @JvmStatic
   fun `decode$lambda$3`(pb: FriendlyByteBuf): java.lang.Double {
      return pb.readDouble();
   }

   @JvmStatic
   fun `decode$lambda$5$lambda$4`(`$pb`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$pb`.m_130277_();
   }

   @JvmStatic
   fun `decode$lambda$5`(pb: FriendlyByteBuf): java.util.List {
      return pb.m_236845_(EventTriggerTimeline::decode$lambda$5$lambda$4);
   }

   @JvmStatic
   fun `CODEC$lambda$10$lambda$9`(it: EventTriggerTimeline): java.util.Map {
      return it.map;
   }

   @JvmStatic
   fun `CODEC$lambda$10`(instance: Instance): App {
      return instance.group(
            new UnboundedMapCodec(PrimitiveCodec.DOUBLE as Codec, PrimitiveCodec.STRING.listOf())
               .fieldOf("map")
               .forGetter(EventTriggerTimeline::CODEC$lambda$10$lambda$9) as App
         )
         .apply(instance as Applicative, EventTriggerTimeline::new);
   }

   public companion object {
      public final val CODEC: Codec<EventTriggerTimeline>
   }
}
