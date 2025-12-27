package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import net.minecraft.network.FriendlyByteBuf

public class LoopingEmitterLifetime(activeTime: Expression = MoLangExtensionsKt.asExpression(1.0) as Expression,
      sleepTime: Expression = MoLangExtensionsKt.asExpression(1.0) as Expression
   ) :
   ParticleEmitterLifetime {
   public final var activeTime: Expression
   public final var sleepTime: Expression
   public open val type: ParticleEmitterLifetimeType

   init {
      this.activeTime = activeTime;
      this.sleepTime = sleepTime;
      this.type = ParticleEmitterLifetimeType.LOOPING;
   }

   public override fun getAction(runtime: MoLangRuntime, started: Boolean, emitterAge: Double): ParticleEmitterAction {
      val activeTime: MoValue = MoLangExtensionsKt.resolve(runtime, this.activeTime);
      val activeTimeValue: Double = activeTime.asDouble();
      val sleepTime: Double = MoLangExtensionsKt.resolveDouble(runtime, this.sleepTime);
      val displacement: Double = emitterAge % (activeTimeValue + sleepTime);
      runtime.getEnvironment().setSimpleVariable("emitter_lifetime", activeTime);
      if (emitterAge > activeTimeValue && sleepTime == 0.0) {
         return ParticleEmitterAction.STOP;
      } else {
         return if (displacement < activeTimeValue) ParticleEmitterAction.GO else ParticleEmitterAction.RESET;
      }
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      var var10001: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.activeTime = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.sleepTime = var10001;
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.activeTime));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.sleepTime));
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$0`(it: LoopingEmitterLifetime): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$1`(it: LoopingEmitterLifetime): Expression {
      return it.activeTime;
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$2`(it: LoopingEmitterLifetime): Expression {
      return it.sleepTime;
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$3`(var0: java.lang.String, activeTime: Expression, sleepTime: Expression): LoopingEmitterLifetime {
      return new LoopingEmitterLifetime(activeTime, sleepTime);
   }

   @JvmStatic
   fun `CODEC$lambda$4`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(LoopingEmitterLifetime::CODEC$lambda$4$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("activeTime").forGetter(LoopingEmitterLifetime::CODEC$lambda$4$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("sleepTime").forGetter(LoopingEmitterLifetime::CODEC$lambda$4$lambda$2) as App
         )
         .apply(instance as Applicative, LoopingEmitterLifetime::CODEC$lambda$4$lambda$3);
   }

   fun LoopingEmitterLifetime() {
      this(null, null, 3, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(LoopingEmitterLifetime::CODEC$lambda$4);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<LoopingEmitterLifetime>
   }
}
