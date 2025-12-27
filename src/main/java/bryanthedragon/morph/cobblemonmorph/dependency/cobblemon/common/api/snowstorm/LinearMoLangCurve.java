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
import com.mojang.serialization.codecs.ListCodec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import net.minecraft.network.FriendlyByteBuf

public class LinearMoLangCurve(name: String = "variable",
      input: Expression = (new NumberExpression(0.0)) as Expression,
      horizontalRange: Expression = (new NumberExpression(1.0)) as Expression,
      nodes: List<Double> = CollectionsKt.emptyList()
   ) :
   MoLangCurve {
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
      this.type = CurveType.LINEAR;
   }

   public override fun resolve(runtime: MoLangRuntime, inputValue: Double): Double {
      val spaceBetweenNodes: Double = MoLangExtensionsKt.resolveDouble(runtime, this.horizontalRange) / (this.nodes.size() - 1);
      val rangeIndex: Int = (int)Math.floor(inputValue / spaceBetweenNodes);
      if (rangeIndex < 0) {
         return this.nodes.get(0).doubleValue();
      } else if (rangeIndex + 1 >= this.nodes.size()) {
         return (CollectionsKt.last(this.nodes) as java.lang.Number).doubleValue();
      } else {
         val leftNode: Double = this.nodes.get(rangeIndex).doubleValue();
         val rightNode: Double = this.nodes.get(rangeIndex + 1).doubleValue();
         val var15: Double = inputValue % spaceBetweenNodes;
         return leftNode
            + (rightNode - leftNode)
               * (
                  (
                        if (inputValue % spaceBetweenNodes != 0.0 && Math.signum(inputValue % spaceBetweenNodes) != Math.signum(spaceBetweenNodes))
                           inputValue % spaceBetweenNodes + spaceBetweenNodes
                           else
                           inputValue % spaceBetweenNodes
                     )
                     / spaceBetweenNodes
               );
      }
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
      val var4: java.util.List = buffer.m_236845_(LinearMoLangCurve::readFromBuffer$lambda$0);
      this.nodes = var4;
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.getName());
      buffer.m_130070_(MoLangExtensionsKt.getString(this.getInput()));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.horizontalRange));
      buffer.m_236828_(this.nodes, LinearMoLangCurve::writeToBuffer$lambda$1);
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
   fun `CODEC$lambda$8$lambda$2`(it: LinearMoLangCurve): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$3`(it: LinearMoLangCurve): java.lang.String {
      return it.getName();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$4`(it: LinearMoLangCurve): Expression {
      return it.getInput();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$5`(it: LinearMoLangCurve): Expression {
      return it.horizontalRange;
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$6`(it: LinearMoLangCurve): java.util.List {
      return it.nodes;
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$7`(var0: java.lang.String, name: java.lang.String, input: Expression, horizontalRange: Expression, nodes: java.util.List): LinearMoLangCurve {
      return new LinearMoLangCurve(name, input, horizontalRange, nodes);
   }

   @JvmStatic
   fun `CODEC$lambda$8`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(LinearMoLangCurve::CODEC$lambda$8$lambda$2) as App,
            PrimitiveCodec.STRING.fieldOf("name").forGetter(LinearMoLangCurve::CODEC$lambda$8$lambda$3) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("input").forGetter(LinearMoLangCurve::CODEC$lambda$8$lambda$4) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("horizontalRange").forGetter(LinearMoLangCurve::CODEC$lambda$8$lambda$5) as App,
            new ListCodec(PrimitiveCodec.DOUBLE as Codec).fieldOf("nodes").forGetter(LinearMoLangCurve::CODEC$lambda$8$lambda$6) as App
         )
         .apply(instance as Applicative, LinearMoLangCurve::CODEC$lambda$8$lambda$7);
   }

   fun LinearMoLangCurve() {
      this(null, null, null, null, 15, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(LinearMoLangCurve::CODEC$lambda$8);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<LinearMoLangCurve>
   }
}
