package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.NumberExpression
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.CatmullRomCurve
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.ListCodec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import net.minecraft.network.FriendlyByteBuf

public class CatmullRomMoLangCurve(name: String = "variable",
      input: Expression = (new NumberExpression(0.5)) as Expression,
      horizontalRange: Expression = (new NumberExpression(1.0)) as Expression,
      nodes: List<Double> = CollectionsKt.emptyList()
   ) :
   MoLangCurve {
   public final var curve: CatmullRomCurve
   public final var horizontalRange: Expression
   public open var input: Expression
   public open var name: String
   public final var nodes: List<Double>
   public open val type: CurveType

   init {
      this.name = name;
      this.input = input;
      this.horizontalRange = horizontalRange;
      this.nodes = nodes;
      this.curve = new CatmullRomCurve(this.nodes);
      this.type = CurveType.CATMULL_ROM;
   }

   public override fun resolve(runtime: MoLangRuntime, inputValue: Double): Double {
      val horizontalRange: Double = MoLangExtensionsKt.resolveDouble(runtime, this.horizontalRange);
      val segments: Int = this.nodes.size() - 3;
      return this.curve.getY((inputValue / horizontalRange * (double)segments + (double)1) / (double)(segments + 2));
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
      val var4: java.util.List = buffer.m_236845_(CatmullRomMoLangCurve::readFromBuffer$lambda$0);
      this.nodes = var4;
      this.curve = new CatmullRomCurve(this.nodes);
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.getName());
      buffer.m_130070_(MoLangExtensionsKt.getString(this.getInput()));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.horizontalRange));
      buffer.m_236828_(this.nodes, CatmullRomMoLangCurve::writeToBuffer$lambda$1);
   }

   override fun apply(runtime: MoLangRuntime) {
      MoLangCurve.DefaultImpls.apply(this, runtime);
   }

   @JvmStatic
   fun `readFromBuffer$lambda$0`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.Double {
      return `$buffer`.readDouble();
   }

   @JvmStatic
   fun `writeToBuffer$lambda$1`(pb: FriendlyByteBuf, node: java.lang.Double) {
      pb.writeDouble(node);
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$2`(it: CatmullRomMoLangCurve): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$3`(it: CatmullRomMoLangCurve): java.lang.String {
      return it.getName();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$4`(it: CatmullRomMoLangCurve): Expression {
      return it.getInput();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$5`(it: CatmullRomMoLangCurve): Expression {
      return it.horizontalRange;
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$6`(it: CatmullRomMoLangCurve): java.util.List {
      return it.nodes;
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$7`(var0: java.lang.String, name: java.lang.String, input: Expression, horizontalRange: Expression, nodes: java.util.List): CatmullRomMoLangCurve {
      return new CatmullRomMoLangCurve(name, input, horizontalRange, nodes);
   }

   @JvmStatic
   fun `CODEC$lambda$8`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(CatmullRomMoLangCurve::CODEC$lambda$8$lambda$2) as App,
            PrimitiveCodec.STRING.fieldOf("name").forGetter(CatmullRomMoLangCurve::CODEC$lambda$8$lambda$3) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("input").forGetter(CatmullRomMoLangCurve::CODEC$lambda$8$lambda$4) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("horizontalRange").forGetter(CatmullRomMoLangCurve::CODEC$lambda$8$lambda$5) as App,
            new ListCodec(PrimitiveCodec.DOUBLE as Codec).fieldOf("nodes").forGetter(CatmullRomMoLangCurve::CODEC$lambda$8$lambda$6) as App
         )
         .apply(instance as Applicative, CatmullRomMoLangCurve::CODEC$lambda$8$lambda$7);
   }

   fun CatmullRomMoLangCurve() {
      this(null, null, null, null, 15, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(CatmullRomMoLangCurve::CODEC$lambda$8);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<CatmullRomMoLangCurve>
   }
}
