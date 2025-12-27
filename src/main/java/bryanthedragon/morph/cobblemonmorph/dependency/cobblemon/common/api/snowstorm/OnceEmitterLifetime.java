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

public class OnceEmitterLifetime(activeTime: Expression = MoLangExtensionsKt.asExpression(1.0) as Expression) : ParticleEmitterLifetime {
   public final var activeTime: Expression
   public open val type: ParticleEmitterLifetimeType

   init {
      this.activeTime = activeTime;
      this.type = ParticleEmitterLifetimeType.ONCE;
   }

   public override fun getAction(runtime: MoLangRuntime, started: Boolean, emitterAge: Double): ParticleEmitterAction {
      val activeTime: MoValue = MoLangExtensionsKt.resolve(runtime, this.activeTime);
      runtime.getEnvironment().setSimpleVariable("emitter_lifetime", activeTime);
      return if (emitterAge > activeTime.asDouble()) ParticleEmitterAction.STOP else ParticleEmitterAction.GO;
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      val var10001: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.activeTime = var10001;
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.activeTime));
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$0`(it: OnceEmitterLifetime): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$1`(it: OnceEmitterLifetime): Expression {
      return it.activeTime;
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$2`(var0: java.lang.String, activeTime: Expression): OnceEmitterLifetime {
      return new OnceEmitterLifetime(activeTime);
   }

   @JvmStatic
   fun `CODEC$lambda$3`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(OnceEmitterLifetime::CODEC$lambda$3$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("activeTime").forGetter(OnceEmitterLifetime::CODEC$lambda$3$lambda$1) as App
         )
         .apply(instance as Applicative, OnceEmitterLifetime::CODEC$lambda$3$lambda$2);
   }

   fun OnceEmitterLifetime() {
      this(null, 1, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(OnceEmitterLifetime::CODEC$lambda$3);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<OnceEmitterLifetime>
   }
}
