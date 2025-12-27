package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.NumberExpression
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.CubedBezierCurve
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import net.minecraft.network.FriendlyByteBuf

public class BezierMoLangCurve(name: String = "variable",
      input: Expression = (new NumberExpression(0.5)) as Expression,
      horizontalRange: Expression = (new NumberExpression(1.0)) as Expression,
      v0: Double = 0.0,
      v1: Double = 0.0,
      v2: Double = 0.0,
      v3: Double = 0.0
   ) :
   MoLangCurve {
   public final var curve: CubedBezierCurve
   public final var horizontalRange: Expression
   public open var input: Expression
   public open var name: String
   public open val type: CurveType
   public final var v0: Double
   public final var v1: Double
   public final var v2: Double
   public final var v3: Double

   init {
      this.name = name;
      this.input = input;
      this.horizontalRange = horizontalRange;
      this.v0 = v0;
      this.v1 = v1;
      this.v2 = v2;
      this.v3 = v3;
      this.curve = new CubedBezierCurve(this.v0, this.v1, this.v2, this.v3);
      this.type = CurveType.BEZIER;
   }

   public override fun resolve(runtime: MoLangRuntime, inputValue: Double): Double {
      return this.curve.getY(inputValue / MoLangExtensionsKt.resolveDouble(runtime, this.horizontalRange));
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      val var10001: java.lang.String = buffer.m_130277_();
      this.setName(var10001);
      val var2: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.setInput(var2);
      val var3: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.horizontalRange = var3;
      this.v0 = buffer.readDouble();
      this.v1 = buffer.readDouble();
      this.v2 = buffer.readDouble();
      this.v3 = buffer.readDouble();
      this.curve = new CubedBezierCurve(this.v0, this.v1, this.v2, this.v3);
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.getName());
      buffer.m_130070_(MoLangExtensionsKt.getString(this.getInput()));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.horizontalRange));
      buffer.writeDouble(this.v0);
      buffer.writeDouble(this.v1);
      buffer.writeDouble(this.v2);
      buffer.writeDouble(this.v3);
   }

   override fun apply(runtime: MoLangRuntime) {
      MoLangCurve.DefaultImpls.apply(this, runtime);
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$0`(it: BezierMoLangCurve): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$1`(it: BezierMoLangCurve): java.lang.String {
      return it.getName();
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$2`(it: BezierMoLangCurve): Expression {
      return it.getInput();
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$3`(it: BezierMoLangCurve): Expression {
      return it.horizontalRange;
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$4`(it: BezierMoLangCurve): java.lang.Double {
      return it.v0;
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$5`(it: BezierMoLangCurve): java.lang.Double {
      return it.v1;
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$6`(it: BezierMoLangCurve): java.lang.Double {
      return it.v2;
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$7`(it: BezierMoLangCurve): java.lang.Double {
      return it.v3;
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$8`(
      var0: java.lang.String,
      name: java.lang.String,
      input: Expression,
      horizontalRange: Expression,
      v0: java.lang.Double,
      v1: java.lang.Double,
      v2: java.lang.Double,
      v3: java.lang.Double
   ): BezierMoLangCurve {
      val var10005: Double = v0;
      val var10006: Double = v1;
      val var10007: Double = v2;
      return new BezierMoLangCurve(name, input, horizontalRange, var10005, var10006, var10007, v3);
   }

   @JvmStatic
   fun `CODEC$lambda$9`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$0) as App,
            PrimitiveCodec.STRING.fieldOf("name").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("input").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$2) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("horizontalRange").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$3) as App,
            PrimitiveCodec.DOUBLE.fieldOf("v0").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$4) as App,
            PrimitiveCodec.DOUBLE.fieldOf("v1").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$5) as App,
            PrimitiveCodec.DOUBLE.fieldOf("v2").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$6) as App,
            PrimitiveCodec.DOUBLE.fieldOf("v3").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$7) as App
         )
         .apply(instance as Applicative, BezierMoLangCurve::CODEC$lambda$9$lambda$8);
   }

   fun BezierMoLangCurve() {
      this(null, null, null, 0.0, 0.0, 0.0, 0.0, 127, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(BezierMoLangCurve::CODEC$lambda$9);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<BezierMoLangCurve>
   }
}
