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
import org.joml.Vector4f

public class ExpressionParticleTinting(red: Expression = (new NumberExpression(1.0)) as Expression,
      green: Expression = (new NumberExpression(1.0)) as Expression,
      blue: Expression = (new NumberExpression(1.0)) as Expression,
      alpha: Expression = (new NumberExpression(1.0)) as Expression
   ) :
   ParticleTinting {
   public final var alpha: Expression
   public final var blue: Expression
   public final var green: Expression
   public final var red: Expression
   public open val type: ParticleTintingType

   init {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.alpha = alpha;
      this.type = ParticleTintingType.EXPRESSION;
   }

   public override fun getTint(runtime: MoLangRuntime): Vector4f {
      return new Vector4f(
         (float)MoLangExtensionsKt.resolveDouble(runtime, this.red),
         (float)MoLangExtensionsKt.resolveDouble(runtime, this.green),
         (float)MoLangExtensionsKt.resolveDouble(runtime, this.blue),
         (float)MoLangExtensionsKt.resolveDouble(runtime, this.alpha)
      );
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      var var10001: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.red = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.green = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.blue = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.alpha = var10001;
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.red));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.green));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.blue));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.alpha));
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$0`(it: ExpressionParticleTinting): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$1`(it: ExpressionParticleTinting): Expression {
      return it.red;
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$2`(it: ExpressionParticleTinting): Expression {
      return it.green;
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$3`(it: ExpressionParticleTinting): Expression {
      return it.blue;
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$4`(it: ExpressionParticleTinting): Expression {
      return it.alpha;
   }

   @JvmStatic
   fun `CODEC$lambda$6$lambda$5`(var0: java.lang.String, red: Expression, green: Expression, blue: Expression, alpha: Expression): ExpressionParticleTinting {
      return new ExpressionParticleTinting(red, green, blue, alpha);
   }

   @JvmStatic
   fun `CODEC$lambda$6`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(ExpressionParticleTinting::CODEC$lambda$6$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("red").forGetter(ExpressionParticleTinting::CODEC$lambda$6$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("green").forGetter(ExpressionParticleTinting::CODEC$lambda$6$lambda$2) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("blue").forGetter(ExpressionParticleTinting::CODEC$lambda$6$lambda$3) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("alpha").forGetter(ExpressionParticleTinting::CODEC$lambda$6$lambda$4) as App
         )
         .apply(instance as Applicative, ExpressionParticleTinting::CODEC$lambda$6$lambda$5);
   }

   fun ExpressionParticleTinting() {
      this(null, null, null, null, 15, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(ExpressionParticleTinting::CODEC$lambda$6);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<ExpressionParticleTinting>
   }
}
