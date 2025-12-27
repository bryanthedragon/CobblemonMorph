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
import com.mojang.serialization.codecs.UnboundedMapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import java.util.ArrayList;
import java.util.NoSuchElementException
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nMoLangCurve.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoLangCurve.kt\ncom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,324:1\n1045#2:325\n451#2,6:326\n1#3:332\n*S KotlinDebug\n*F\n+ 1 MoLangCurve.kt\ncom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve\n*L\n178#1:325\n245#1:326,6\n*E\n"])
public class BezierChainMoLangCurve(name: String = "variable",
      input: Expression = (new NumberExpression(0.5)) as Expression,
      nodes: Map<Double, bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BezierChainMoLangCurve.BezierChainNode> = MapsKt.emptyMap()
   ) :
   MoLangCurve {
   public open var input: Expression
   public open var name: String
   public final var nodePairs: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BezierChainMoLangCurve.BezierChainNodePair>
   public final var nodes: Map<Double, bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BezierChainMoLangCurve.BezierChainNode>
   public open val type: CurveType

   init {
      this.name = name;
      this.input = input;
      this.nodes = nodes;
      this.nodePairs = CollectionsKt.emptyList();
      this.deriveNodePairs();
      this.type = CurveType.BEZIER_CHAIN;
   }

   public fun deriveNodePairs() {
      val nodePairs: java.util.List = new ArrayList();
      val sortedNodes: java.util.List = CollectionsKt.sortedWith(this.nodes.entrySet(), new BezierChainMoLangCurve$deriveNodePairs$$inlined$sortedBy$1());
      var var7: Int = 1;

      for (int var8 = sortedNodes.size(); i < var8; i++) {
         val node1: Entry = sortedNodes.get(var7 - 1) as Entry;
         val node2: Entry = sortedNodes.get(var7) as Entry;
         nodePairs.add(
            new BezierChainMoLangCurve.BezierChainNodePair(
               (node1.getKey() as java.lang.Number).doubleValue(),
               (node2.getKey() as java.lang.Number).doubleValue(),
               node1.getValue() as BezierChainMoLangCurve.BezierChainNode,
               node2.getValue() as BezierChainMoLangCurve.BezierChainNode
            )
         );
      }

      this.nodePairs = nodePairs;
   }

   public override fun resolve(runtime: MoLangRuntime, inputValue: Double): Double {
      val position: Double = RangesKt.coerceIn(inputValue, 0.0, 1.0);
      val `iterator$iv`: java.util.ListIterator = this.nodePairs.listIterator(this.nodePairs.size());

      while (iterator$iv.hasPrevious()) {
         val `element$iv`: Any = `iterator$iv`.previous();
         if (position >= (`element$iv` as BezierChainMoLangCurve.BezierChainNodePair).getStartTime()) {
            return (`element$iv` as BezierChainMoLangCurve.BezierChainNodePair)
               .getCurve()
               .getY(
                  (position - (`element$iv` as BezierChainMoLangCurve.BezierChainNodePair).getStartTime())
                     / (
                        (`element$iv` as BezierChainMoLangCurve.BezierChainNodePair).getEndTime()
                           - (`element$iv` as BezierChainMoLangCurve.BezierChainNodePair).getStartTime()
                     )
               );
         }
      }

      throw new NoSuchElementException("List contains no element matching the predicate.");
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      val var10001: java.lang.String = buffer.m_130277_();
      this.setName(var10001);
      val var2: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.setInput(var2);
      val var3: java.util.Map = buffer.m_236847_(BezierChainMoLangCurve::readFromBuffer$lambda$2, BezierChainMoLangCurve::readFromBuffer$lambda$4);
      this.nodes = var3;
      this.deriveNodePairs();
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.getName());
      buffer.m_130070_(MoLangExtensionsKt.getString(this.getInput()));
      buffer.m_236831_(this.nodes, BezierChainMoLangCurve::writeToBuffer$lambda$5, BezierChainMoLangCurve::writeToBuffer$lambda$6);
   }

   override fun apply(runtime: MoLangRuntime) {
      MoLangCurve.DefaultImpls.apply(this, runtime);
   }

   @JvmStatic
   fun `readFromBuffer$lambda$2`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.Double {
      return `$buffer`.readDouble();
   }

   @JvmStatic
   fun `readFromBuffer$lambda$4`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): BezierChainMoLangCurve.BezierChainNode {
      val var2: BezierChainMoLangCurve.BezierChainNode = new BezierChainMoLangCurve.BezierChainNode(0.0, 0.0);
      var2.readFromBuffer(`$buffer`);
      return var2;
   }

   @JvmStatic
   fun `writeToBuffer$lambda$5`(pb: FriendlyByteBuf, key: java.lang.Double) {
      pb.writeDouble(key);
   }

   @JvmStatic
   fun `writeToBuffer$lambda$6`(pb: FriendlyByteBuf, value: BezierChainMoLangCurve.BezierChainNode) {
      value.writeToBuffer(pb);
   }

   @JvmStatic
   fun `CODEC$lambda$12$lambda$7`(it: BezierChainMoLangCurve): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$12$lambda$8`(it: BezierChainMoLangCurve): java.lang.String {
      return it.getName();
   }

   @JvmStatic
   fun `CODEC$lambda$12$lambda$9`(it: BezierChainMoLangCurve): Expression {
      return it.getInput();
   }

   @JvmStatic
   fun `CODEC$lambda$12$lambda$10`(it: BezierChainMoLangCurve): java.util.Map {
      return it.nodes;
   }

   @JvmStatic
   fun `CODEC$lambda$12$lambda$11`(var0: java.lang.String, name: java.lang.String, input: Expression, nodes: java.util.Map): BezierChainMoLangCurve {
      return new BezierChainMoLangCurve(name, input, nodes);
   }

   @JvmStatic
   fun `CODEC$lambda$12`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(BezierChainMoLangCurve::CODEC$lambda$12$lambda$7) as App,
            PrimitiveCodec.STRING.fieldOf("name").forGetter(BezierChainMoLangCurve::CODEC$lambda$12$lambda$8) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("input").forGetter(BezierChainMoLangCurve::CODEC$lambda$12$lambda$9) as App,
            new UnboundedMapCodec(PrimitiveCodec.DOUBLE as Codec, BezierChainMoLangCurve.BezierChainNode.Companion.getCODEC())
               .fieldOf("nodes")
               .forGetter(BezierChainMoLangCurve::CODEC$lambda$12$lambda$10) as App
         )
         .apply(instance as Applicative, BezierChainMoLangCurve::CODEC$lambda$12$lambda$11);
   }

   fun BezierChainMoLangCurve() {
      this(null, null, null, 7, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(BezierChainMoLangCurve::CODEC$lambda$12);
      CODEC = var10000;
   }

   public class BezierChainNode(value: Double, slope: Double) {
      public final var slope: Double
      public final var value: Double

      init {
         this.value = value;
         this.slope = slope;
      }

      public fun writeToBuffer(buffer: FriendlyByteBuf) {
         buffer.writeDouble(this.value);
         buffer.writeDouble(this.slope);
      }

      public fun readFromBuffer(buffer: FriendlyByteBuf) {
         this.value = buffer.readDouble();
         this.slope = buffer.readDouble();
      }

      @JvmStatic
      fun `CODEC$lambda$2$lambda$0`(it: BezierChainMoLangCurve.BezierChainNode): java.lang.Double {
         return it.value;
      }

      @JvmStatic
      fun `CODEC$lambda$2$lambda$1`(it: BezierChainMoLangCurve.BezierChainNode): java.lang.Double {
         return it.slope;
      }

      @JvmStatic
      fun `CODEC$lambda$2`(instance: Instance): App {
         return instance.group(
               PrimitiveCodec.DOUBLE.fieldOf("value").forGetter(BezierChainMoLangCurve.BezierChainNode::CODEC$lambda$2$lambda$0) as App,
               PrimitiveCodec.DOUBLE.fieldOf("slope").forGetter(BezierChainMoLangCurve.BezierChainNode::CODEC$lambda$2$lambda$1) as App
            )
            .apply(instance as Applicative, BezierChainMoLangCurve.BezierChainNode::new);
      }

      public companion object {
         public final val CODEC: Codec<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BezierChainMoLangCurve.BezierChainNode>
      }
   }

   public class BezierChainNodePair(startTime: Double,
      endTime: Double,
      node1: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BezierChainMoLangCurve.BezierChainNode,
      node2: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BezierChainMoLangCurve.BezierChainNode
   ) {
      public final val curve: CubedBezierCurve
      public final val endTime: Double
      public final val startTime: Double

      init {
         this.startTime = startTime;
         this.endTime = endTime;
         this.curve = new CubedBezierCurve(node1.getValue(), node1.getValue() + node1.getSlope() / 3, node2.getValue() - node2.getSlope() / 3, node2.getValue());
      }
   }

   public class BezierChainPointData(time: Double, value: Double, slope: Double) {
      public final val slope: Double
      public final val time: Double
      public final val value: Double

      init {
         this.time = time;
         this.value = value;
         this.slope = slope;
      }
   }

   public companion object {
      public final val CODEC: Codec<BezierChainMoLangCurve>
   }
}
