package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
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

public class InstantParticleEmitterRate(amount: Expression = MoLangExtensionsKt.asExpression("1")) : ParticleEmitterRate {
   public final var amount: Expression
   public open val type: ParticleEmitterRateType

   init {
      this.amount = amount;
      this.type = ParticleEmitterRateType.INSTANT;
   }

   public override fun getEmitCount(runtime: MoLangRuntime, started: Boolean, currentlyActive: Int): Int {
      return if (started) 0 else MoLangExtensionsKt.resolveInt(runtime, this.amount);
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      val var10001: java.lang.String = buffer.m_130277_();
      val var2: Expression = MoLangExtensionsKt.asExpression(var10001);
      this.amount = var2;
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.amount));
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$0`(it: InstantParticleEmitterRate): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$1`(it: InstantParticleEmitterRate): Expression {
      return it.amount;
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$2`(var0: java.lang.String, amount: Expression): InstantParticleEmitterRate {
      return new InstantParticleEmitterRate(amount);
   }

   @JvmStatic
   fun `CODEC$lambda$3`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(InstantParticleEmitterRate::CODEC$lambda$3$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("amount").forGetter(InstantParticleEmitterRate::CODEC$lambda$3$lambda$1) as App
         )
         .apply(instance as Applicative, InstantParticleEmitterRate::CODEC$lambda$3$lambda$2);
   }

   fun InstantParticleEmitterRate() {
      this(null, 1, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(InstantParticleEmitterRate::CODEC$lambda$3);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<InstantParticleEmitterRate>
   }
}
