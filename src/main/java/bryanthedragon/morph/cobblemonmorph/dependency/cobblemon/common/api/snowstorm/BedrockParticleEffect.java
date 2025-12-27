package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.ListCodec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.UnboundedMapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import java.util.ArrayList;
import java.util.LinkedHashMap
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nBedrockParticleEffect.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockParticleEffect.kt\ncom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,74:1\n1#2:75\n*E\n"])
public class BedrockParticleEffect(id: ResourceLocation = new ResourceLocation("effect"),
   emitter: BedrockParticleEmitter = new BedrockParticleEmitter(null, null, null, null, null, null, null, null, null, null, 1023, null),
   particle: BedrockParticle = new BedrockParticle(
         null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, null, null, null, 524287, null
      ),
   curves: MutableList<MoLangCurve> = (new ArrayList()) as java.util.List,
   space: ParticleSpace = new ParticleSpace(false, false, false, 7, null),
   events: MutableMap<String, ParticleEvent> = (new LinkedHashMap()) as java.util.Map
) {
   public final var curves: MutableList<MoLangCurve>
   public final var emitter: BedrockParticleEmitter
   public final var events: MutableMap<String, ParticleEvent>
   public final var id: ResourceLocation
   public final var particle: BedrockParticle
   public final var space: ParticleSpace

   init {
      this.id = id;
      this.emitter = emitter;
      this.particle = particle;
      this.curves = curves;
      this.space = space;
      this.events = events;
   }

   public fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130085_(this.id);
      this.emitter.writeToBuffer(buffer);
      this.particle.writeToBuffer(buffer);
      buffer.m_236828_(this.curves, BedrockParticleEffect::writeToBuffer$lambda$0);
      this.space.writeToBuffer(buffer);
      buffer.m_236831_(this.events, BedrockParticleEffect::writeToBuffer$lambda$1, BedrockParticleEffect::writeToBuffer$lambda$2);
   }

   public fun readFromBuffer(buffer: FriendlyByteBuf) {
      val var10001: ResourceLocation = buffer.m_130281_();
      this.id = var10001;
      this.emitter.readFromBuffer(buffer);
      this.particle.readFromBuffer(buffer);
      val var2: java.util.List = buffer.m_236845_(BedrockParticleEffect::readFromBuffer$lambda$3);
      this.curves = var2;
      this.space.readFromBuffer(buffer);
      val var3: java.util.Map = buffer.m_236847_(BedrockParticleEffect::readFromBuffer$lambda$4, BedrockParticleEffect::readFromBuffer$lambda$6);
      this.events = var3;
   }

   @JvmStatic
   fun `writeToBuffer$lambda$0`(`$buffer`: FriendlyByteBuf, pb: FriendlyByteBuf, curve: MoLangCurve) {
      val var10000: MoLangCurve.Companion = MoLangCurve.Companion;
      var10000.writeToBuffer(`$buffer`, curve);
   }

   @JvmStatic
   fun `writeToBuffer$lambda$1`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: java.lang.String) {
      `$buffer`.m_130070_(v);
   }

   @JvmStatic
   fun `writeToBuffer$lambda$2`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, event: ParticleEvent) {
      event.encode(`$buffer`);
   }

   @JvmStatic
   fun `readFromBuffer$lambda$3`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): MoLangCurve {
      return MoLangCurve.Companion.readFromBuffer(`$buffer`);
   }

   @JvmStatic
   fun `readFromBuffer$lambda$4`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   @JvmStatic
   fun `readFromBuffer$lambda$6`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): ParticleEvent {
      val var2: ParticleEvent = new ParticleEvent(null, null, null, 7, null);
      var2.decode(`$buffer`);
      return var2;
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$7`(it: BedrockParticleEffect): ResourceLocation {
      return it.id;
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$8`(it: BedrockParticleEffect): BedrockParticleEmitter {
      return it.emitter;
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$9`(it: BedrockParticleEffect): BedrockParticle {
      return it.particle;
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$10`(it: BedrockParticleEffect): java.util.List {
      return it.curves;
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$11`(it: BedrockParticleEffect): ParticleSpace {
      return it.space;
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$12`(it: BedrockParticleEffect): java.util.Map {
      return it.events;
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$13`(
      id: ResourceLocation, emitter: BedrockParticleEmitter, particle: BedrockParticle, curves: java.util.List, space: ParticleSpace, events: java.util.Map
   ): BedrockParticleEffect {
      val var10005: java.util.List = CollectionsKt.toMutableList(curves);
      return new BedrockParticleEffect(id, emitter, particle, var10005, space, events);
   }

   @JvmStatic
   fun `CODEC$lambda$14`(instance: Instance): App {
      return instance.group(
            ResourceLocation.f_135803_.fieldOf("id").forGetter(BedrockParticleEffect::CODEC$lambda$14$lambda$7) as App,
            BedrockParticleEmitter.Companion.getCODEC().fieldOf("emitter").forGetter(BedrockParticleEffect::CODEC$lambda$14$lambda$8) as App,
            BedrockParticle.Companion.getCODEC().fieldOf("particle").forGetter(BedrockParticleEffect::CODEC$lambda$14$lambda$9) as App,
            new ListCodec(MoLangCurve.Companion.getCodec()).fieldOf("curves").forGetter(BedrockParticleEffect::CODEC$lambda$14$lambda$10) as App,
            ParticleSpace.Companion.getCODEC().fieldOf("space").forGetter(BedrockParticleEffect::CODEC$lambda$14$lambda$11) as App,
            new UnboundedMapCodec(PrimitiveCodec.STRING as Codec, ParticleEvent.Companion.getCODEC())
               .fieldOf("events")
               .forGetter(BedrockParticleEffect::CODEC$lambda$14$lambda$12) as App
         )
         .apply(instance as Applicative, BedrockParticleEffect::CODEC$lambda$14$lambda$13);
   }

   fun BedrockParticleEffect() {
      this(null, null, null, null, null, null, 63, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(BedrockParticleEffect::CODEC$lambda$14);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<BedrockParticleEffect>
   }
}
