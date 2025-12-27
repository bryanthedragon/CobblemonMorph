package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.NumberExpression
import com.bedrockk.molang.runtime.MoLangRuntime
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

public class ExpressionEmitterLifetime(activation: Expression = (new NumberExpression(0.0)) as Expression,
      expiration: Expression = (new NumberExpression(0.0)) as Expression
   ) :
   ParticleEmitterLifetime {
   public final var activation: Expression
   public final var expiration: Expression
   public open val type: ParticleEmitterLifetimeType

   init {
      this.activation = activation;
      this.expiration = expiration;
      this.type = ParticleEmitterLifetimeType.EXPRESSION;
   }

   public override fun getAction(runtime: MoLangRuntime, started: Boolean, emitterAge: Double): ParticleEmitterAction {
      if (started) {
         return if (MoLangExtensionsKt.resolveBoolean(runtime, this.expiration)) ParticleEmitterAction.STOP else ParticleEmitterAction.GO;
      } else {
         return if (MoLangExtensionsKt.resolveBoolean(runtime, this.activation)) ParticleEmitterAction.GO else ParticleEmitterAction.NOTHING;
      }
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      var var10001: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.activation = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.expiration = var10001;
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.activation));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.expiration));
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$0`(it: ExpressionEmitterLifetime): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$1`(it: ExpressionEmitterLifetime): Expression {
      return it.activation;
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$2`(it: ExpressionEmitterLifetime): Expression {
      return it.expiration;
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$3`(var0: java.lang.String, activation: Expression, expiration: Expression): ExpressionEmitterLifetime {
      return new ExpressionEmitterLifetime(activation, expiration);
   }

   @JvmStatic
   fun `CODEC$lambda$4`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(ExpressionEmitterLifetime::CODEC$lambda$4$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("activation").forGetter(ExpressionEmitterLifetime::CODEC$lambda$4$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("expiration").forGetter(ExpressionEmitterLifetime::CODEC$lambda$4$lambda$2) as App
         )
         .apply(instance as Applicative, ExpressionEmitterLifetime::CODEC$lambda$4$lambda$3);
   }

   fun ExpressionEmitterLifetime() {
      this(null, null, 3, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(ExpressionEmitterLifetime::CODEC$lambda$4);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<ExpressionEmitterLifetime>
   }
}
