package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.ast.NumberExpression
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.struct.VariableStruct
import com.bedrockk.molang.runtime.value.DoubleValue
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

public class SteadyParticleEmitterRate(rate: Expression = (new NumberExpression(0.0)) as Expression,
      maximum: Expression = (new NumberExpression(0.0)) as Expression
   ) :
   ParticleEmitterRate {
   public final var maximum: Expression
   public final var rate: Expression
   public final var time: Long
   public open val type: ParticleEmitterRateType

   init {
      this.rate = rate;
      this.maximum = maximum;
      this.time = System.currentTimeMillis();
      this.type = ParticleEmitterRateType.STEADY;
   }

   public override fun getEmitCount(runtime: MoLangRuntime, started: Boolean, currentlyActive: Int): Int {
      val max: Int = (int)MoLangExtensionsKt.resolveDouble(runtime, this.maximum);
      var var10000: MoValue = runtime.getEnvironment().getStructs().get("variable");
      val variables: VariableStruct = var10000 as VariableStruct;
      var10000 = (var10000 as VariableStruct).getMap().get("emitter_overflow");
      val currentOverflow: Double = if (var10000 != null) var10000.asDouble() else 0.0;
      if (currentlyActive >= max) {
         return 0;
      } else {
         val trySpawn: Double = MoLangExtensionsKt.resolveDouble(runtime, this.rate) / 20.0 + currentOverflow;
         val intComponent: Int = (int)trySpawn;
         val doubleComponent: Double = trySpawn - (int)trySpawn;
         val var19: java.util.Map = variables.getMap();
         var19.put("emitter_overflow", new DoubleValue(doubleComponent));
         return Integer.min(intComponent, max - currentlyActive);
      }
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      var var10001: java.lang.String = buffer.m_130277_();
      val var2: Expression = MoLangExtensionsKt.asExpression(var10001);
      this.rate = var2;
      var10001 = buffer.m_130277_();
      val var4: Expression = MoLangExtensionsKt.asExpression(var10001);
      this.maximum = var4;
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.rate));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.maximum));
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$0`(it: SteadyParticleEmitterRate): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$1`(it: SteadyParticleEmitterRate): Expression {
      return it.rate;
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$2`(it: SteadyParticleEmitterRate): Expression {
      return it.maximum;
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$3`(var0: java.lang.String, rate: Expression, maximum: Expression): SteadyParticleEmitterRate {
      return new SteadyParticleEmitterRate(rate, maximum);
   }

   @JvmStatic
   fun `CODEC$lambda$4`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(SteadyParticleEmitterRate::CODEC$lambda$4$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("rate").forGetter(SteadyParticleEmitterRate::CODEC$lambda$4$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("maximum").forGetter(SteadyParticleEmitterRate::CODEC$lambda$4$lambda$2) as App
         )
         .apply(instance as Applicative, SteadyParticleEmitterRate::CODEC$lambda$4$lambda$3);
   }

   fun SteadyParticleEmitterRate() {
      this(null, null, 3, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(SteadyParticleEmitterRate::CODEC$lambda$4);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<SteadyParticleEmitterRate>
   }
}
