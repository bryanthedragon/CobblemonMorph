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

public class DynamicParticleRotation(startRotation: Expression = (new NumberExpression(0.0)) as Expression,
      speed: Expression = (new NumberExpression(0.0)) as Expression,
      acceleration: Expression = (new NumberExpression(0.0)) as Expression,
      drag: Expression = (new NumberExpression(0.0)) as Expression
   ) :
   ParticleRotation {
   public final var acceleration: Expression
   public final var drag: Expression
   public final var speed: Expression
   public final var startRotation: Expression
   public open val type: ParticleRotationType

   init {
      this.startRotation = startRotation;
      this.speed = speed;
      this.acceleration = acceleration;
      this.drag = drag;
      this.type = ParticleRotationType.DYNAMIC;
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun getInitialRotation(runtime: MoLangRuntime): Double {
      return MoLangExtensionsKt.resolveDouble(runtime, this.startRotation);
   }

   public override fun getInitialAngularVelocity(runtime: MoLangRuntime): Double {
      return MoLangExtensionsKt.resolveDouble(runtime, this.speed) / 20;
   }

   public override fun getAngularVelocity(runtime: MoLangRuntime, angle: Double, angularVelocity: Double): Double {
      val nextVelocity: Double = angularVelocity * 20 + MoLangExtensionsKt.resolveDouble(runtime, this.acceleration);
      val drag: Double = nextVelocity * MoLangExtensionsKt.resolveDouble(runtime, this.drag);
      return angularVelocity + (if (Math.abs(drag) > Math.abs(nextVelocity)) 0.0 else nextVelocity - drag - angularVelocity * 20);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      var var10001: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.startRotation = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.speed = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.acceleration = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.drag = var10001;
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.startRotation));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.speed));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.acceleration));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.drag));
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$0`(it: DynamicParticleRotation): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$1`(it: DynamicParticleRotation): Expression {
      return it.startRotation;
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$2`(it: DynamicParticleRotation): Expression {
      return it.speed;
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$3`(it: DynamicParticleRotation): Expression {
      return it.acceleration;
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$4`(it: DynamicParticleRotation): Expression {
      return it.drag;
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$5`(var0: java.lang.String, startRotation: Expression, speed: Expression, acceleration: Expression, drag: Expression): DynamicParticleRotation {
      return new DynamicParticleRotation(startRotation, speed, acceleration, drag);
   }

   @JvmStatic
   fun `CODEC$lambda$6`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(DynamicParticleRotation::CODEC$lambda$6$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("startRotation").forGetter(DynamicParticleRotation::CODEC$lambda$6$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("speed").forGetter(DynamicParticleRotation::CODEC$lambda$6$lambda$2) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("acceleration").forGetter(DynamicParticleRotation::CODEC$lambda$6$lambda$3) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("drag").forGetter(DynamicParticleRotation::CODEC$lambda$6$lambda$4) as App
         )
         .apply(instance as Applicative, DynamicParticleRotation::CODEC$lambda$6$lambda$5);
   }

   fun DynamicParticleRotation() {
      this(null, null, null, null, 15, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(DynamicParticleRotation::CODEC$lambda$6);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<DynamicParticleRotation>
   }
}
