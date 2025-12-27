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

public class ParametricParticleRotation(expression: Expression = (new NumberExpression(0.0)) as Expression) : ParticleRotation {
   public final var expression: Expression
   public open val type: ParticleRotationType

   init {
      this.expression = expression;
      this.type = ParticleRotationType.PARAMETRIC;
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun getInitialRotation(runtime: MoLangRuntime): Double {
      return MoLangExtensionsKt.resolveDouble(runtime, this.expression);
   }

   public override fun getInitialAngularVelocity(runtime: MoLangRuntime): Double {
      return 0.0;
   }

   public override fun getAngularVelocity(runtime: MoLangRuntime, angle: Double, angularVelocity: Double): Double {
      return MoLangExtensionsKt.resolveDouble(runtime, this.expression) - angle;
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      val var10001: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.expression = var10001;
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.expression));
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$0`(it: ParametricParticleRotation): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$1`(it: ParametricParticleRotation): Expression {
      return it.expression;
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$2`(var0: java.lang.String, expression: Expression): ParametricParticleRotation {
      return new ParametricParticleRotation(expression);
   }

   @JvmStatic
   fun `CODEC$lambda$3`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(ParametricParticleRotation::CODEC$lambda$3$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("expression").forGetter(ParametricParticleRotation::CODEC$lambda$3$lambda$1) as App
         )
         .apply(instance as Applicative, ParametricParticleRotation::CODEC$lambda$3$lambda$2);
   }

   fun ParametricParticleRotation() {
      this(null, 1, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(ParametricParticleRotation::CODEC$lambda$3);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<ParametricParticleRotation>
   }
}
