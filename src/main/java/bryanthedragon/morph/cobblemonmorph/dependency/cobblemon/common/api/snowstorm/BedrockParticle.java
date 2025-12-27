package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.BooleanExpression
import com.bedrockk.molang.ast.NumberExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.ListCodec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import java.util.ArrayList;
import java.util.LinkedHashMap
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nBedrockParticle.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockParticle.kt\ncom/cobblemon/mod/common/api/snowstorm/BedrockParticle\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,194:1\n1#2:195\n*E\n"])
public class BedrockParticle(texture: ResourceLocation = new ResourceLocation("minecraft:textures/particles/bubble.png"),
   material: ParticleMaterial = ParticleMaterial.ALPHA,
   uvMode: ParticleUVMode = (new StaticParticleUVMode(null, null, 0, 0, null, null, 63, null)) as ParticleUVMode,
   sizeX: Expression = (new NumberExpression(0.15)) as Expression,
   sizeY: Expression = (new NumberExpression(0.15)) as Expression,
   maxAge: Expression = (new NumberExpression(1.0)) as Expression,
   killExpression: Expression = (new BooleanExpression(false)) as Expression,
   updateExpressions: MutableList<Expression> = (new ArrayList()) as java.util.List,
   renderExpressions: MutableList<Expression> = (new ArrayList()) as java.util.List,
   motion: ParticleMotion = (new StaticParticleMotion()) as ParticleMotion,
   rotation: ParticleRotation = (new DynamicParticleRotation(null, null, null, null, 15, null)) as ParticleRotation,
   viewDirection: ParticleViewDirection = (new FromMotionViewDirection(0.0, 1, null)) as ParticleViewDirection,
   cameraMode: ParticleCameraMode = (new RotateXYZCameraMode()) as ParticleCameraMode,
   tinting: ParticleTinting = (new ExpressionParticleTinting(null, null, null, null, 15, null)) as ParticleTinting,
   collision: ParticleCollision = new ParticleCollision(null, null, null, null, false, 31, null),
   environmentLighting: Boolean = false,
   creationEvents: MutableList<SimpleEventTrigger> = (new ArrayList()) as java.util.List,
   expirationEvents: MutableList<SimpleEventTrigger> = (new ArrayList()) as java.util.List,
   timeline: EventTriggerTimeline = new EventTriggerTimeline(new LinkedHashMap<>())
) {
   public final var cameraMode: ParticleCameraMode
   public final var collision: ParticleCollision
   public final var creationEvents: MutableList<SimpleEventTrigger>
   public final var environmentLighting: Boolean
   public final var expirationEvents: MutableList<SimpleEventTrigger>
   public final var killExpression: Expression
   public final var material: ParticleMaterial
   public final var maxAge: Expression
   public final var motion: ParticleMotion
   public final var renderExpressions: MutableList<Expression>
   public final var rotation: ParticleRotation
   public final var sizeX: Expression
   public final var sizeY: Expression
   public final var texture: ResourceLocation
   public final var timeline: EventTriggerTimeline
   public final var tinting: ParticleTinting
   public final var updateExpressions: MutableList<Expression>
   public final var uvMode: ParticleUVMode
   public final var viewDirection: ParticleViewDirection

   init {
      this.texture = texture;
      this.material = material;
      this.uvMode = uvMode;
      this.sizeX = sizeX;
      this.sizeY = sizeY;
      this.maxAge = maxAge;
      this.killExpression = killExpression;
      this.updateExpressions = updateExpressions;
      this.renderExpressions = renderExpressions;
      this.motion = motion;
      this.rotation = rotation;
      this.viewDirection = viewDirection;
      this.cameraMode = cameraMode;
      this.tinting = tinting;
      this.collision = collision;
      this.environmentLighting = environmentLighting;
      this.creationEvents = creationEvents;
      this.expirationEvents = expirationEvents;
      this.timeline = timeline;
   }

   public fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130085_(this.texture);
      buffer.m_130070_(this.material.name());
      ParticleUVMode.Companion.writeToBuffer(buffer, this.uvMode);
      buffer.m_130070_(MoLangExtensionsKt.getString(this.sizeX));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.sizeY));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.maxAge));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.killExpression));
      buffer.m_236828_(this.updateExpressions, BedrockParticle::writeToBuffer$lambda$0);
      buffer.m_236828_(this.renderExpressions, BedrockParticle::writeToBuffer$lambda$1);
      ParticleMotion.Companion.writeToBuffer(buffer, this.motion);
      ParticleRotation.Companion.writeToBuffer(buffer, this.rotation);
      ParticleViewDirection.Companion.writeToBuffer(buffer, this.viewDirection);
      ParticleCameraMode.Companion.writeToBuffer(buffer, this.cameraMode);
      ParticleTinting.Companion.writeToBuffer(buffer, this.tinting);
      this.collision.writeToBuffer(buffer);
      buffer.writeBoolean(this.environmentLighting);
      buffer.m_236828_(this.creationEvents, BedrockParticle::writeToBuffer$lambda$2);
      buffer.m_236828_(this.expirationEvents, BedrockParticle::writeToBuffer$lambda$3);
      this.timeline.encode(buffer);
   }

   public fun readFromBuffer(buffer: FriendlyByteBuf) {
      val var10001: ResourceLocation = buffer.m_130281_();
      this.texture = var10001;
      val var2: java.lang.String = buffer.m_130277_();
      this.material = ParticleMaterial.valueOf(var2);
      this.uvMode = ParticleUVMode.Companion.readFromBuffer(buffer);
      val var3: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.sizeX = var3;
      val var4: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.sizeY = var4;
      val var5: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.maxAge = var5;
      val var6: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.killExpression = var6;
      val var7: java.util.List = buffer.m_236845_(BedrockParticle::readFromBuffer$lambda$4);
      this.updateExpressions = var7;
      val var8: java.util.List = buffer.m_236845_(BedrockParticle::readFromBuffer$lambda$5);
      this.renderExpressions = var8;
      this.motion = ParticleMotion.Companion.readFromBuffer(buffer);
      this.rotation = ParticleRotation.Companion.readFromBuffer(buffer);
      this.viewDirection = ParticleViewDirection.Companion.readFromBuffer(buffer);
      this.cameraMode = ParticleCameraMode.Companion.readFromBuffer(buffer);
      this.tinting = ParticleTinting.Companion.readFromBuffer(buffer);
      this.collision.readFromBuffer(buffer);
      this.environmentLighting = buffer.readBoolean();
      val var9: java.util.List = buffer.m_236845_(BedrockParticle::readFromBuffer$lambda$7);
      this.creationEvents = var9;
      val var10: java.util.List = buffer.m_236845_(BedrockParticle::readFromBuffer$lambda$9);
      this.expirationEvents = var10;
      this.timeline.decode(buffer);
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
   fun `readFromBuffer$lambda$4`(it: FriendlyByteBuf): Expression {
      return MoLang.createParser(it.m_130277_()).parseExpression();
   }

   @JvmStatic
   fun `readFromBuffer$lambda$5`(it: FriendlyByteBuf): Expression {
      return MoLang.createParser(it.m_130277_()).parseExpression();
   }

   @JvmStatic
   fun `readFromBuffer$lambda$7`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): SimpleEventTrigger {
      val var2: SimpleEventTrigger = new SimpleEventTrigger("");
      var2.decode(`$buffer`);
      return var2;
   }

   @JvmStatic
   fun `readFromBuffer$lambda$9`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): SimpleEventTrigger {
      val var2: SimpleEventTrigger = new SimpleEventTrigger("");
      var2.decode(`$buffer`);
      return var2;
   }

   @JvmStatic
   fun `EXPRESSION_SET_CODEC$lambda$16$lambda$10`(it: BedrockParticle.ExpressionSet): Expression {
      return it.getSizeX();
   }

   @JvmStatic
   fun `EXPRESSION_SET_CODEC$lambda$16$lambda$11`(it: BedrockParticle.ExpressionSet): Expression {
      return it.getSizeY();
   }

   @JvmStatic
   fun `EXPRESSION_SET_CODEC$lambda$16$lambda$12`(it: BedrockParticle.ExpressionSet): Expression {
      return it.getMaxAge();
   }

   @JvmStatic
   fun `EXPRESSION_SET_CODEC$lambda$16$lambda$13`(it: BedrockParticle.ExpressionSet): Expression {
      return it.getKillExpression();
   }

   @JvmStatic
   fun `EXPRESSION_SET_CODEC$lambda$16$lambda$14`(it: BedrockParticle.ExpressionSet): java.util.List {
      return it.getUpdateExpressions();
   }

   @JvmStatic
   fun `EXPRESSION_SET_CODEC$lambda$16$lambda$15`(it: BedrockParticle.ExpressionSet): java.util.List {
      return it.getRenderExpressions();
   }

   @JvmStatic
   fun `EXPRESSION_SET_CODEC$lambda$16`(instance: Instance): App {
      return instance.group(
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("sizeX").forGetter(BedrockParticle::EXPRESSION_SET_CODEC$lambda$16$lambda$10) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("sizeY").forGetter(BedrockParticle::EXPRESSION_SET_CODEC$lambda$16$lambda$11) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("maxAge").forGetter(BedrockParticle::EXPRESSION_SET_CODEC$lambda$16$lambda$12) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("killExpression").forGetter(BedrockParticle::EXPRESSION_SET_CODEC$lambda$16$lambda$13) as App,
            new ListCodec(ExpressionCodecKt.getEXPRESSION_CODEC() as Codec)
               .fieldOf("updateExpressions")
               .forGetter(BedrockParticle::EXPRESSION_SET_CODEC$lambda$16$lambda$14) as App,
            new ListCodec(ExpressionCodecKt.getEXPRESSION_CODEC() as Codec)
               .fieldOf("renderExpressions")
               .forGetter(BedrockParticle::EXPRESSION_SET_CODEC$lambda$16$lambda$15) as App
         )
         .apply(instance as Applicative, BedrockParticle.ExpressionSet::new);
   }

   @JvmStatic
   fun `EVENT_SET_CODEC$lambda$20$lambda$17`(it: BedrockParticle.EventSet): java.util.List {
      return it.getCreationEvents();
   }

   @JvmStatic
   fun `EVENT_SET_CODEC$lambda$20$lambda$18`(it: BedrockParticle.EventSet): java.util.List {
      return it.getExpirationEvents();
   }

   @JvmStatic
   fun `EVENT_SET_CODEC$lambda$20$lambda$19`(it: BedrockParticle.EventSet): EventTriggerTimeline {
      return it.getTimeline();
   }

   @JvmStatic
   fun `EVENT_SET_CODEC$lambda$20`(instance: Instance): App {
      return instance.group(
            new ListCodec(SimpleEventTrigger.Companion.getCODEC()).fieldOf("creationEvents").forGetter(BedrockParticle::EVENT_SET_CODEC$lambda$20$lambda$17) as App,
            new ListCodec(SimpleEventTrigger.Companion.getCODEC()).fieldOf("expirationEvents").forGetter(BedrockParticle::EVENT_SET_CODEC$lambda$20$lambda$18) as App,
            EventTriggerTimeline.Companion.getCODEC().fieldOf("timeline").forGetter(BedrockParticle::EVENT_SET_CODEC$lambda$20$lambda$19) as App
         )
         .apply(instance as Applicative, BedrockParticle.EventSet::new);
   }

   @JvmStatic
   fun `CODEC$lambda$34$lambda$21`(it: BedrockParticle): ResourceLocation {
      return it.texture;
   }

   @JvmStatic
   fun `CODEC$lambda$34$lambda$22`(it: BedrockParticle): java.lang.String {
      return it.material.name();
   }

   @JvmStatic
   fun `CODEC$lambda$34$lambda$23`(it: BedrockParticle): ParticleUVMode {
      return it.uvMode;
   }

   @JvmStatic
   fun `CODEC$lambda$34$lambda$24`(it: BedrockParticle): BedrockParticle.ExpressionSet {
      return new BedrockParticle.ExpressionSet(it.sizeX, it.sizeY, it.maxAge, it.killExpression, it.updateExpressions, it.renderExpressions);
   }

   @JvmStatic
   fun `CODEC$lambda$34$lambda$25`(it: BedrockParticle): ParticleMotion {
      return it.motion;
   }

   @JvmStatic
   fun `CODEC$lambda$34$lambda$26`(it: BedrockParticle): ParticleRotation {
      return it.rotation;
   }

   @JvmStatic
   fun `CODEC$lambda$34$lambda$27`(it: BedrockParticle): ParticleViewDirection {
      return it.viewDirection;
   }

   @JvmStatic
   fun `CODEC$lambda$34$lambda$28`(it: BedrockParticle): ParticleCameraMode {
      return it.cameraMode;
   }

   @JvmStatic
   fun `CODEC$lambda$34$lambda$29`(it: BedrockParticle): ParticleTinting {
      return it.tinting;
   }

   @JvmStatic
   fun `CODEC$lambda$34$lambda$30`(it: BedrockParticle): ParticleCollision {
      return it.collision;
   }

   @JvmStatic
   fun `CODEC$lambda$34$lambda$31`(it: BedrockParticle): java.lang.Boolean {
      return it.environmentLighting;
   }

   @JvmStatic
   fun `CODEC$lambda$34$lambda$32`(it: BedrockParticle): BedrockParticle.EventSet {
      return new BedrockParticle.EventSet(it.creationEvents, it.expirationEvents, it.timeline);
   }

   @JvmStatic
   fun `CODEC$lambda$34$lambda$33`(
      texture: ResourceLocation,
      materialStr: java.lang.String,
      uvMode: ParticleUVMode,
      expressionSet: BedrockParticle.ExpressionSet,
      motion: ParticleMotion,
      rotation: ParticleRotation,
      viewDirection: ParticleViewDirection,
      cameraMode: ParticleCameraMode,
      tinting: ParticleTinting,
      collision: ParticleCollision,
      environmentLighting: java.lang.Boolean,
      eventSet: BedrockParticle.EventSet
   ): BedrockParticle {
      val var10003: ParticleMaterial = ParticleMaterial.valueOf(materialStr);
      val var10005: Expression = expressionSet.getSizeX();
      val var10006: Expression = expressionSet.getSizeY();
      val var10007: Expression = expressionSet.getMaxAge();
      val var10008: Expression = expressionSet.getKillExpression();
      val var10009: java.util.List = expressionSet.getUpdateExpressions();
      val var10010: java.util.List = expressionSet.getRenderExpressions();
      return new BedrockParticle(
         texture,
         var10003,
         uvMode,
         var10005,
         var10006,
         var10007,
         var10008,
         var10009,
         var10010,
         motion,
         rotation,
         viewDirection,
         cameraMode,
         tinting,
         collision,
         environmentLighting,
         eventSet.getCreationEvents(),
         eventSet.getExpirationEvents(),
         eventSet.getTimeline()
      );
   }

   @JvmStatic
   fun `CODEC$lambda$34`(instance: Instance): App {
      return instance.group(
            ResourceLocation.f_135803_.fieldOf("texture").forGetter(BedrockParticle::CODEC$lambda$34$lambda$21) as App,
            PrimitiveCodec.STRING.fieldOf("material").forGetter(BedrockParticle::CODEC$lambda$34$lambda$22) as App,
            ParticleUVMode.Companion.getCodec().fieldOf("uvMode").forGetter(BedrockParticle::CODEC$lambda$34$lambda$23) as App,
            EXPRESSION_SET_CODEC.fieldOf("expressionSet").forGetter(BedrockParticle::CODEC$lambda$34$lambda$24) as App,
            ParticleMotion.Companion.getCodec().fieldOf("motion").forGetter(BedrockParticle::CODEC$lambda$34$lambda$25) as App,
            ParticleRotation.Companion.getCodec().fieldOf("rotation").forGetter(BedrockParticle::CODEC$lambda$34$lambda$26) as App,
            ParticleViewDirection.Companion.getCodec().fieldOf("viewDirection").forGetter(BedrockParticle::CODEC$lambda$34$lambda$27) as App,
            ParticleCameraMode.Companion.getCodec().fieldOf("cameraMode").forGetter(BedrockParticle::CODEC$lambda$34$lambda$28) as App,
            ParticleTinting.Companion.getCodec().fieldOf("tinting").forGetter(BedrockParticle::CODEC$lambda$34$lambda$29) as App,
            ParticleCollision.Companion.getCODEC().fieldOf("collision").forGetter(BedrockParticle::CODEC$lambda$34$lambda$30) as App,
            PrimitiveCodec.BOOL.fieldOf("environmentLighting").forGetter(BedrockParticle::CODEC$lambda$34$lambda$31) as App,
            EVENT_SET_CODEC.fieldOf("eventSet").forGetter(BedrockParticle::CODEC$lambda$34$lambda$32) as App
         )
         .apply(instance as Applicative, BedrockParticle::CODEC$lambda$34$lambda$33);
   }

   fun BedrockParticle() {
      this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, null, null, null, 524287, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(BedrockParticle::CODEC$lambda$34);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<BedrockParticle>
      public final val EVENT_SET_CODEC: Codec<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticle.EventSet>
      public final val EXPRESSION_SET_CODEC: Codec<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticle.ExpressionSet>
   }

   public class EventSet(creationEvents: MutableList<SimpleEventTrigger>, expirationEvents: MutableList<SimpleEventTrigger>, timeline: EventTriggerTimeline) {
      public final val creationEvents: MutableList<SimpleEventTrigger>
      public final val expirationEvents: MutableList<SimpleEventTrigger>
      public final val timeline: EventTriggerTimeline

      init {
         this.creationEvents = creationEvents;
         this.expirationEvents = expirationEvents;
         this.timeline = timeline;
      }
   }

   public class ExpressionSet(sizeX: Expression,
      sizeY: Expression,
      maxAge: Expression,
      killExpression: Expression,
      updateExpressions: MutableList<Expression>,
      renderExpressions: MutableList<Expression>
   ) {
      public final val killExpression: Expression
      public final val maxAge: Expression
      public final val renderExpressions: MutableList<Expression>
      public final val sizeX: Expression
      public final val sizeY: Expression
      public final val updateExpressions: MutableList<Expression>

      init {
         this.sizeX = sizeX;
         this.sizeY = sizeY;
         this.maxAge = maxAge;
         this.killExpression = killExpression;
         this.updateExpressions = updateExpressions;
         this.renderExpressions = renderExpressions;
      }
   }
}
