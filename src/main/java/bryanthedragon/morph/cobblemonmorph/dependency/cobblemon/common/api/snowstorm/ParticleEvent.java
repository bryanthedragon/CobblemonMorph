package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import java.util.HashMap
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nParticleEvent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParticleEvent.kt\ncom/cobblemon/mod/common/api/snowstorm/ParticleEvent\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,174:1\n1#2:175\n*E\n"])
public class ParticleEvent(particleEffect: EventParticleEffect? = null, soundEffect: EventSoundEffect? = null, expression: ExpressionLike? = null) :
   Encodable,
   Decodable {
   public final var expression: ExpressionLike?
   public final var particleEffect: EventParticleEffect?
   public final var soundEffect: EventSoundEffect?

   init {
      this.particleEffect = particleEffect;
      this.soundEffect = soundEffect;
      this.expression = expression;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_236821_(this.particleEffect, ParticleEvent::encode$lambda$1);
      buffer.m_236821_(this.soundEffect, ParticleEvent::encode$lambda$2);
      buffer.m_236821_(this.expression, ParticleEvent::encode$lambda$3);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      this.particleEffect = buffer.m_236868_(ParticleEvent::decode$lambda$5) as EventParticleEffect;
      this.soundEffect = buffer.m_236868_(ParticleEvent::decode$lambda$6) as EventSoundEffect;
      this.expression = buffer.m_236868_(ParticleEvent::decode$lambda$7) as ExpressionLike;
   }

   public fun run(storm: ParticleStorm, particle: SnowstormParticle?) {
      if (this.particleEffect != null) {
         val effect: EventParticleEffect = this.particleEffect;
         val var10000: BedrockParticleEffect = BedrockParticleEffectRepository.INSTANCE.getEffect(this.particleEffect.getEffect());
         if (var10000 != null) {
            switch (ParticleEvent.WhenMappings.$EnumSwitchMapping$0[effect.getType().ordinal()]) {
               case 1:
               case 2:
               case 3:
               case 4:
                  val rootMatrix: MatrixWrapper = new MatrixWrapper()
                     .updatePosition(
                        if (particle != null)
                           new Vec3(particle.getX(), particle.getY(), particle.getZ())
                           else
                           new Vec3(storm.getX(), storm.getY(), storm.getZ())
                     );
                  var var45: Function0;
                  switch (ParticleEvent.WhenMappings.$EnumSwitchMapping$0[effect.getType().ordinal()]) {
                     case 1:
                     case 2:
                     case 3:
                        var45 = <unrepresentable>.INSTANCE;
                        break;
                     case 4:
                        val var34: Vec3 = if (particle != null)
                           new Vec3(particle.getVelocityX(), particle.getVelocityY(), particle.getVelocityZ())
                           else
                           Vec3.f_82478_;
                        var45 = (new Function0<Vec3>(var34) {
                           {
                              super(0);
                              this.$it = `$it`;
                           }

                           public final Vec3 invoke() {
                              return this.$it;
                           }
                        }) as Function0;
                        break;
                     default:
                        throw new NoWhenBranchMatchedException();
                  }

                  val var10002: ClientLevel = storm.getWorld();
                  val var10004: Function0 = storm.getSourceAlive();
                  val var10005: Function0 = storm.getSourceVisible();
                  val var10006: Function0 = <unrepresentable>.INSTANCE;
                  val position: MoLangRuntime = MoLangFunctions.INSTANCE.setup(new MoLangRuntime());
                  val var46: HashMap = position.getEnvironment().getStructs();
                  val var38: java.util.Map = var46;
                  val var47: MoLangFunctions = MoLangFunctions.INSTANCE;
                  val var10001: MoLangEnvironment = storm.getRuntime().getEnvironment();
                  var38.put("query", MoLangFunctions.getQueryStruct$default(var47, var10001, null, 1, null));
                  val newStorm: ParticleStorm = new ParticleStorm(
                     var10000, rootMatrix, var10002, var45, var10004, var10005, var10006, position, storm.getEntity()
                  );
                  val var48: ExpressionLike = effect.getExpression();
                  if (var48 != null) {
                     var48.resolve(newStorm.getRuntime());
                  }

                  newStorm.spawn();
                  break;
               default:
                  throw new NoWhenBranchMatchedException();
            }
         }
      }

      if (this.soundEffect != null) {
         val var32: EventSoundEffect = this.soundEffect;
         val var40: Vec3 = if (particle != null)
            new Vec3(particle.getX(), particle.getY(), particle.getZ())
            else
            new Vec3(storm.getX(), storm.getY(), storm.getZ());
         storm.getWorld()
            .m_7785_(var40.f_82479_, var40.f_82480_, var40.f_82481_, SoundEvent.m_262824_(var32.getSound()), SoundSource.NEUTRAL, 1.0F, 1.0F, true);
      }

      if (this.expression != null) {
         this.expression.resolve(storm.getRuntime());
      }
   }

   @JvmStatic
   fun `encode$lambda$1$lambda$0`(pb: FriendlyByteBuf, expr: ExpressionLike) {
      pb.m_130070_(expr.toString());
   }

   @JvmStatic
   fun `encode$lambda$1`(pb: FriendlyByteBuf, effect: EventParticleEffect) {
      pb.m_130085_(effect.getEffect());
      pb.m_130068_(effect.getType());
      pb.m_236821_(effect.getExpression(), ParticleEvent::encode$lambda$1$lambda$0);
   }

   @JvmStatic
   fun `encode$lambda$2`(pb: FriendlyByteBuf, effect: EventSoundEffect) {
      pb.m_130085_(effect.getSound());
   }

   @JvmStatic
   fun `encode$lambda$3`(pb: FriendlyByteBuf, expr: ExpressionLike) {
      pb.m_130070_(expr.toString());
   }

   @JvmStatic
   fun `decode$lambda$5$lambda$4`(`$pb`: FriendlyByteBuf, it: FriendlyByteBuf): ExpressionLike {
      val var10000: java.lang.String = `$pb`.m_130277_();
      return MoLangExtensionsKt.asExpressionLike(var10000);
   }

   @JvmStatic
   fun `decode$lambda$5`(pb: FriendlyByteBuf): EventParticleEffect {
      val var10002: ResourceLocation = pb.m_130281_();
      val var10003: java.lang.Enum = pb.m_130066_(EventParticleEffect.EventParticleType.class);
      return new EventParticleEffect(
         var10002, var10003 as EventParticleEffect.EventParticleType, pb.m_236868_(ParticleEvent::decode$lambda$5$lambda$4) as ExpressionLike
      );
   }

   @JvmStatic
   fun `decode$lambda$6`(pb: FriendlyByteBuf): EventSoundEffect {
      val var10002: ResourceLocation = pb.m_130281_();
      return new EventSoundEffect(var10002);
   }

   @JvmStatic
   fun `decode$lambda$7`(pb: FriendlyByteBuf): ExpressionLike {
      val var10000: java.lang.String = pb.m_130277_();
      return MoLangExtensionsKt.asExpressionLike(var10000);
   }

   @JvmStatic
   fun `CODEC$lambda$20$lambda$16`(it: ParticleEvent): EventParticleEffect {
      return it.particleEffect;
   }

   @JvmStatic
   fun `CODEC$lambda$20$lambda$17`(it: ParticleEvent): EventSoundEffect {
      return it.soundEffect;
   }

   @JvmStatic
   fun `CODEC$lambda$20$lambda$18`(it: ParticleEvent): java.lang.String {
      return if (it.expression != null) it.expression.toString() else null;
   }

   @JvmStatic
   fun `CODEC$lambda$20$lambda$19`(particleEffect: EventParticleEffect, soundEffect: EventSoundEffect, expression: java.lang.String): ParticleEvent {
      return new ParticleEvent(particleEffect, soundEffect, if (expression != null) MoLangExtensionsKt.asExpressionLike(expression) else null);
   }

   @JvmStatic
   fun `CODEC$lambda$20`(instance: Instance): App {
      return instance.group(
            EventParticleEffect.Companion.getCODEC().optionalFieldOf("particle_effect", null).forGetter(ParticleEvent::CODEC$lambda$20$lambda$16) as App,
            EventSoundEffect.Companion.getCODEC().optionalFieldOf("sound_effect", null).forGetter(ParticleEvent::CODEC$lambda$20$lambda$17) as App,
            PrimitiveCodec.STRING.optionalFieldOf("expression", null).forGetter(ParticleEvent::CODEC$lambda$20$lambda$18) as App
         )
         .apply(instance as Applicative, ParticleEvent::CODEC$lambda$20$lambda$19);
   }

   fun ParticleEvent() {
      this(null, null, null, 7, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(ParticleEvent::CODEC$lambda$20);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<ParticleEvent>
   }
}
