package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.NumberExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.ListCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import java.util.ArrayList;
import java.util.LinkedHashMap
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nBedrockParticleEmitter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockParticleEmitter.kt\ncom/cobblemon/mod/common/api/snowstorm/BedrockParticleEmitter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,94:1\n1#2:95\n*E\n"])
public class BedrockParticleEmitter(startExpressions: MutableList<Expression> = (new ArrayList()) as java.util.List,
   updateExpressions: MutableList<Expression> = (new ArrayList()) as java.util.List,
   rate: ParticleEmitterRate = (new InstantParticleEmitterRate(null, 1, null)) as ParticleEmitterRate,
   shape: ParticleEmitterShape = (new SphereParticleEmitterShape(null, null, false, 7, null)) as ParticleEmitterShape,
   lifetime: ParticleEmitterLifetime = (new OnceEmitterLifetime(new NumberExpression(1.0))) as ParticleEmitterLifetime,
   eventTimeline: EventTriggerTimeline = new EventTriggerTimeline(new LinkedHashMap<>()),
   creationEvents: MutableList<SimpleEventTrigger> = (new ArrayList()) as java.util.List,
   expirationEvents: MutableList<SimpleEventTrigger> = (new ArrayList()) as java.util.List,
   travelDistanceEvents: EventTriggerTimeline = new EventTriggerTimeline(new LinkedHashMap<>()),
   loopingTravelDistanceEvents: MutableList<LoopingTravelDistanceEventTrigger> = (new ArrayList()) as java.util.List
) {
   public final var creationEvents: MutableList<SimpleEventTrigger>
   public final var eventTimeline: EventTriggerTimeline
   public final var expirationEvents: MutableList<SimpleEventTrigger>
   public final var lifetime: ParticleEmitterLifetime
   public final var loopingTravelDistanceEvents: MutableList<LoopingTravelDistanceEventTrigger>
   public final var rate: ParticleEmitterRate
   public final var shape: ParticleEmitterShape
   public final var startExpressions: MutableList<Expression>
   public final var travelDistanceEvents: EventTriggerTimeline
   public final var updateExpressions: MutableList<Expression>

   init {
      this.startExpressions = startExpressions;
      this.updateExpressions = updateExpressions;
      this.rate = rate;
      this.shape = shape;
      this.lifetime = lifetime;
      this.eventTimeline = eventTimeline;
      this.creationEvents = creationEvents;
      this.expirationEvents = expirationEvents;
      this.travelDistanceEvents = travelDistanceEvents;
      this.loopingTravelDistanceEvents = loopingTravelDistanceEvents;
   }

   public fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_236828_(this.startExpressions, BedrockParticleEmitter::writeToBuffer$lambda$0);
      buffer.m_236828_(this.updateExpressions, BedrockParticleEmitter::writeToBuffer$lambda$1);
      ParticleEmitterRate.Companion.writeToBuffer(buffer, this.rate);
      ParticleEmitterShape.Companion.writeToBuffer(buffer, this.shape);
      ParticleEmitterLifetime.Companion.writeToBuffer(buffer, this.lifetime);
      this.eventTimeline.encode(buffer);
      buffer.m_236828_(this.creationEvents, BedrockParticleEmitter::writeToBuffer$lambda$2);
      buffer.m_236828_(this.expirationEvents, BedrockParticleEmitter::writeToBuffer$lambda$3);
      this.travelDistanceEvents.encode(buffer);
      buffer.m_236828_(this.loopingTravelDistanceEvents, BedrockParticleEmitter::writeToBuffer$lambda$4);
   }

   public fun readFromBuffer(buffer: FriendlyByteBuf) {
      var var10001: java.util.List = buffer.m_236845_(BedrockParticleEmitter::readFromBuffer$lambda$5);
      this.startExpressions = var10001;
      var10001 = buffer.m_236845_(BedrockParticleEmitter::readFromBuffer$lambda$6);
      this.updateExpressions = var10001;
      this.rate = ParticleEmitterRate.Companion.readFromBuffer(buffer);
      this.shape = ParticleEmitterShape.Companion.readFromBuffer(buffer);
      this.lifetime = ParticleEmitterLifetime.Companion.readFromBuffer(buffer);
      this.eventTimeline.decode(buffer);
      var10001 = buffer.m_236845_(BedrockParticleEmitter::readFromBuffer$lambda$8);
      this.creationEvents = var10001;
      var10001 = buffer.m_236845_(BedrockParticleEmitter::readFromBuffer$lambda$10);
      this.expirationEvents = var10001;
      this.travelDistanceEvents.decode(buffer);
      var10001 = buffer.m_236845_(BedrockParticleEmitter::readFromBuffer$lambda$12);
      this.loopingTravelDistanceEvents = var10001;
   }

   @JvmStatic
   fun `writeToBuffer$lambda$0`(pb: FriendlyByteBuf, expression: Expression) {
      pb.m_130070_(MoLangExtensionsKt.getString(expression));
   }

   @JvmStatic
   fun `writeToBuffer$lambda$1`(pb: FriendlyByteBuf, expression: Expression) {
      pb.m_130070_(MoLangExtensionsKt.getString(expression));
   }

   @JvmStatic
   fun `writeToBuffer$lambda$2`(pb: FriendlyByteBuf, event: SimpleEventTrigger) {
      event.encode(pb);
   }

   @JvmStatic
   fun `writeToBuffer$lambda$3`(pb: FriendlyByteBuf, event: SimpleEventTrigger) {
      event.encode(pb);
   }

   @JvmStatic
   fun `writeToBuffer$lambda$4`(pb: FriendlyByteBuf, event: LoopingTravelDistanceEventTrigger) {
      event.encode(pb);
   }

   @JvmStatic
   fun `readFromBuffer$lambda$5`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): Expression {
      return MoLang.createParser(`$buffer`.m_130277_()).parseExpression();
   }

   @JvmStatic
   fun `readFromBuffer$lambda$6`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): Expression {
      return MoLang.createParser(`$buffer`.m_130277_()).parseExpression();
   }

   @JvmStatic
   fun `readFromBuffer$lambda$8`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): SimpleEventTrigger {
      val var2: SimpleEventTrigger = new SimpleEventTrigger("");
      var2.decode(`$buffer`);
      return var2;
   }

   @JvmStatic
   fun `readFromBuffer$lambda$10`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): SimpleEventTrigger {
      val var2: SimpleEventTrigger = new SimpleEventTrigger("");
      var2.decode(`$buffer`);
      return var2;
   }

   @JvmStatic
   fun `readFromBuffer$lambda$12`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): LoopingTravelDistanceEventTrigger {
      val var2: LoopingTravelDistanceEventTrigger = new LoopingTravelDistanceEventTrigger(0.0, new ArrayList<>());
      var2.decode(`$buffer`);
      return var2;
   }

   @JvmStatic
   fun `CODEC$lambda$24$lambda$13`(it: BedrockParticleEmitter): java.util.List {
      return it.startExpressions;
   }

   @JvmStatic
   fun `CODEC$lambda$24$lambda$14`(it: BedrockParticleEmitter): java.util.List {
      return it.updateExpressions;
   }

   @JvmStatic
   fun `CODEC$lambda$24$lambda$15`(it: BedrockParticleEmitter): ParticleEmitterRate {
      return it.rate;
   }

   @JvmStatic
   fun `CODEC$lambda$24$lambda$16`(it: BedrockParticleEmitter): ParticleEmitterShape {
      return it.shape;
   }

   @JvmStatic
   fun `CODEC$lambda$24$lambda$17`(it: BedrockParticleEmitter): ParticleEmitterLifetime {
      return it.lifetime;
   }

   @JvmStatic
   fun `CODEC$lambda$24$lambda$18`(it: BedrockParticleEmitter): EventTriggerTimeline {
      return it.eventTimeline;
   }

   @JvmStatic
   fun `CODEC$lambda$24$lambda$19`(it: BedrockParticleEmitter): java.util.List {
      return it.creationEvents;
   }

   @JvmStatic
   fun `CODEC$lambda$24$lambda$20`(it: BedrockParticleEmitter): java.util.List {
      return it.expirationEvents;
   }

   @JvmStatic
   fun `CODEC$lambda$24$lambda$21`(it: BedrockParticleEmitter): EventTriggerTimeline {
      return it.travelDistanceEvents;
   }

   @JvmStatic
   fun `CODEC$lambda$24$lambda$22`(it: BedrockParticleEmitter): java.util.List {
      return it.loopingTravelDistanceEvents;
   }

   @JvmStatic
   fun `CODEC$lambda$24$lambda$23`(
      startExpressions: java.util.List,
      updateExpressions: java.util.List,
      rate: ParticleEmitterRate,
      shape: ParticleEmitterShape,
      lifetime: ParticleEmitterLifetime,
      eventTimeline: EventTriggerTimeline,
      creationEvents: java.util.List,
      expirationEvents: java.util.List,
      travelDistanceEvents: EventTriggerTimeline,
      loopingTravelDistanceEvents: java.util.List
   ): BedrockParticleEmitter {
      return new BedrockParticleEmitter(
         startExpressions,
         updateExpressions,
         rate,
         shape,
         lifetime,
         eventTimeline,
         creationEvents,
         expirationEvents,
         travelDistanceEvents,
         loopingTravelDistanceEvents
      );
   }

   @JvmStatic
   fun `CODEC$lambda$24`(instance: Instance): App {
      return instance.group(
            new ListCodec(ExpressionCodecKt.getEXPRESSION_CODEC() as Codec)
               .fieldOf("startExpressions")
               .forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$13) as App,
            new ListCodec(ExpressionCodecKt.getEXPRESSION_CODEC() as Codec)
               .fieldOf("updateExpressions")
               .forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$14) as App,
            ParticleEmitterRate.Companion.getCodec().fieldOf("rate").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$15) as App,
            ParticleEmitterShape.Companion.getCodec().fieldOf("shape").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$16) as App,
            ParticleEmitterLifetime.Companion.getCodec().fieldOf("lifetime").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$17) as App,
            EventTriggerTimeline.Companion.getCODEC().fieldOf("eventTimeline").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$18) as App,
            new ListCodec(SimpleEventTrigger.Companion.getCODEC()).fieldOf("creationEvents").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$19) as App,
            new ListCodec(SimpleEventTrigger.Companion.getCODEC()).fieldOf("expirationEvents").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$20) as App,
            EventTriggerTimeline.Companion.getCODEC().fieldOf("travelDistanceEvents").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$21) as App,
            new ListCodec(LoopingTravelDistanceEventTrigger.Companion.getCODEC())
               .fieldOf("loopingTravelDistanceEvents")
               .forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$22) as App
         )
         .apply(instance as Applicative, BedrockParticleEmitter::CODEC$lambda$24$lambda$23);
   }

   fun BedrockParticleEmitter() {
      this(null, null, null, null, null, null, null, null, null, null, 1023, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(BedrockParticleEmitter::CODEC$lambda$24);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<BedrockParticleEmitter>
   }
}
