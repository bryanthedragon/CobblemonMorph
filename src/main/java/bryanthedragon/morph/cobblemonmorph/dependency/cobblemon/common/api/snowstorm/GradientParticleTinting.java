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
import java.util.ArrayList;
import java.util.Arrays
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.util.Mth
import org.joml.Vector4f

@SourceDebugExtension(["SMAP\nParticleTinting.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParticleTinting.kt\ncom/cobblemon/mod/common/api/snowstorm/GradientParticleTinting\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,178:1\n766#2:179\n857#2,2:180\n2333#2,14:182\n766#2:196\n857#2,2:197\n2333#2,14:199\n1549#2:213\n1620#2,3:214\n1549#2:217\n1620#2,3:218\n37#3,2:221\n*S KotlinDebug\n*F\n+ 1 ParticleTinting.kt\ncom/cobblemon/mod/common/api/snowstorm/GradientParticleTinting\n*L\n133#1:179\n133#1:180,2\n134#1:182,14\n136#1:196\n136#1:197,2\n137#1:199,14\n119#1:213\n119#1:214,3\n123#1:217\n123#1:218,3\n123#1:221,2\n*E\n"])
public class GradientParticleTinting(interpolant: Expression = (new NumberExpression(0.0)) as Expression, gradient: Map<Double, Vector4f> = MapsKt.emptyMap()) :
   ParticleTinting {
   public final var gradient: Map<Double, Vector4f>
   public final var interpolant: Expression
   public open val type: ParticleTintingType

   init {
      this.interpolant = interpolant;
      this.gradient = gradient;
      this.type = ParticleTintingType.GRADIENT;
   }

   public override fun getTint(runtime: MoLangRuntime): Vector4f {
      val interpolant: Double = MoLangExtensionsKt.resolveDouble(runtime, this.interpolant);
      val closestAboveNode: java.lang.Iterable = this.gradient.entrySet();
      val `iterator$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : closestAboveNode) {
         if (((`e$iv` as Entry).getKey() as java.lang.Number).doubleValue() <= interpolant) {
            `iterator$iv`.add(`e$iv`);
         }
      }

      val `$i$f$minByOrNull`: java.util.Iterator = (`iterator$iv` as java.util.List).iterator();
      var var10000: Any;
      if (!`$i$f$minByOrNull`.hasNext()) {
         var10000 = null;
      } else {
         var var27: Any = `$i$f$minByOrNull`.next();
         if (!`$i$f$minByOrNull`.hasNext()) {
            var10000 = var27;
         } else {
            var var30: Double = Math.abs(((var27 as Entry).getKey() as java.lang.Number).doubleValue() - interpolant);

            do {
               val var34: Any = `$i$f$minByOrNull`.next();
               val var39: Double = Math.abs(((var34 as Entry).getKey() as java.lang.Number).doubleValue() - interpolant);
               if (java.lang.Double.compare(var30, var39) > 0) {
                  var27 = var34;
                  var30 = var39;
               }
            } while (iterator$iv.hasNext());

            var10000 = var27;
         }
      }

      val closestBelowNode: Entry = var10000 as Entry;
      val var22: java.lang.Iterable = this.gradient.entrySet();
      val `destination$iv$ivx`: java.util.Collection = new ArrayList();

      for (Object element$iv$ivx : var22) {
         if (((`element$iv$ivx` as Entry).getKey() as java.lang.Number).doubleValue() >= interpolant) {
            `destination$iv$ivx`.add(`element$iv$ivx`);
         }
      }

      val `iterator$ivx`: java.util.Iterator = (`destination$iv$ivx` as java.util.List).iterator();
      if (!`iterator$ivx`.hasNext()) {
         var10000 = null;
      } else {
         var `minElem$ivx`: Any = `iterator$ivx`.next();
         if (!`iterator$ivx`.hasNext()) {
            var10000 = `minElem$ivx`;
         } else {
            var var37: Double = Math.abs(((`minElem$ivx` as Entry).getKey() as java.lang.Number).doubleValue() - interpolant);

            do {
               val var42: Any = `iterator$ivx`.next();
               val var46: Double = Math.abs(((var42 as Entry).getKey() as java.lang.Number).doubleValue() - interpolant);
               if (java.lang.Double.compare(var37, var46) > 0) {
                  `minElem$ivx` = var42;
                  var37 = var46;
               }
            } while (iterator$ivx.hasNext());

            var10000 = `minElem$ivx`;
         }
      }

      val var20: Entry = var10000 as Entry;
      if (closestBelowNode == null && var10000 as Entry == null) {
         throw new IllegalStateException(
            "A gradient particle tinting had no below node and no above node, which is probably only possible if the gradient has no points."
         );
      } else if (closestBelowNode == null) {
         return var20.getValue() as Vector4f;
      } else if (var20 == null) {
         return closestBelowNode.getValue() as Vector4f;
      } else {
         val var24: Float = (float)(
            (interpolant - (closestBelowNode.getKey() as java.lang.Number).doubleValue())
               / ((var20.getKey() as java.lang.Number).doubleValue() - (closestBelowNode.getKey() as java.lang.Number).doubleValue())
         );
         return new Vector4f(
            Mth.m_14179_(var24, (closestBelowNode.getValue() as Vector4f).x, (var20.getValue() as Vector4f).x),
            Mth.m_14179_(var24, (closestBelowNode.getValue() as Vector4f).y, (var20.getValue() as Vector4f).y),
            Mth.m_14179_(var24, (closestBelowNode.getValue() as Vector4f).z, (var20.getValue() as Vector4f).z),
            Mth.m_14179_(var24, (closestBelowNode.getValue() as Vector4f).w, (var20.getValue() as Vector4f).w)
         );
      }
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      val var10001: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.interpolant = var10001;
      val var2: java.util.List = buffer.m_236845_(GradientParticleTinting::readFromBuffer$lambda$4);
      this.gradient = MapsKt.toMap(var2);
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.interpolant));
      buffer.m_236828_(this.gradient.entrySet(), GradientParticleTinting::writeToBuffer$lambda$5);
   }

   @JvmStatic
   fun `readFromBuffer$lambda$4`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): Pair {
      return TuplesKt.to(`$buffer`.readDouble(), new Vector4f(`$buffer`.readFloat(), `$buffer`.readFloat(), `$buffer`.readFloat(), `$buffer`.readFloat()));
   }

   @JvmStatic
   fun `writeToBuffer$lambda$5`(`$buffer`: FriendlyByteBuf, pb: FriendlyByteBuf, var2: Entry) {
      val key: Double = (var2.getKey() as java.lang.Number).doubleValue();
      val colour: Vector4f = var2.getValue() as Vector4f;
      `$buffer`.writeDouble(key);
      `$buffer`.writeFloat(colour.x);
      `$buffer`.writeFloat(colour.y);
      `$buffer`.writeFloat(colour.z);
      `$buffer`.writeFloat(colour.w);
   }

   @JvmStatic
   fun `CODEC$lambda$12$lambda$6`(it: GradientParticleTinting): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$12$lambda$7`(it: GradientParticleTinting): Expression {
      return it.interpolant;
   }

   @JvmStatic
   fun `CODEC$lambda$12$lambda$9`(it: GradientParticleTinting): java.util.List {
      val `$this$map$iv`: java.lang.Iterable = it.gradient.entrySet();
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add(
            new GradientParticleTinting.GradientEntry(
               ((`item$iv$iv` as Entry).getKey() as java.lang.Number).doubleValue(), (`item$iv$iv` as Entry).getValue() as Vector4f
            )
         );
      }

      return `destination$iv$iv` as java.util.List;
   }

   @JvmStatic
   fun `CODEC$lambda$12$lambda$11`(var0: java.lang.String, interpolant: Expression, gradient: java.util.List): GradientParticleTinting {
      val `$this$toTypedArray$iv`: java.lang.Iterable = gradient;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(gradient, 10));

      for (Object item$iv$iv : $this$toTypedArray$iv) {
         `destination$iv$iv`.add((`item$iv$iv` as GradientParticleTinting.GradientEntry).toEntry());
      }

      val var3: Array<Pair> = (`destination$iv$iv` as java.util.List).toArray(new Pair[0]);
      return new GradientParticleTinting(interpolant, MapsKt.mapOf(Arrays.copyOf(var3, var3.length)));
   }

   @JvmStatic
   fun `CODEC$lambda$12`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(GradientParticleTinting::CODEC$lambda$12$lambda$6) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("interpolant").forGetter(GradientParticleTinting::CODEC$lambda$12$lambda$7) as App,
            new ListCodec(GradientParticleTinting.GradientEntry.Companion.getCODEC())
               .fieldOf("gradient")
               .forGetter(GradientParticleTinting::CODEC$lambda$12$lambda$9) as App
         )
         .apply(instance as Applicative, GradientParticleTinting::CODEC$lambda$12$lambda$11);
   }

   fun GradientParticleTinting() {
      this(null, null, 3, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(GradientParticleTinting::CODEC$lambda$12);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<GradientParticleTinting>
   }

   public class GradientEntry(key: Double, colour: Vector4f) {
      public final val colour: Vector4f
      public final val key: Double

      init {
         this.key = key;
         this.colour = colour;
      }

      public fun toEntry(): Pair<Double, Vector4f> {
         return TuplesKt.to(this.key, this.colour);
      }

      @JvmStatic
      fun `CODEC$lambda$6$lambda$0`(it: GradientParticleTinting.GradientEntry): java.lang.Double {
         return it.key;
      }

      @JvmStatic
      fun `CODEC$lambda$6$lambda$1`(it: GradientParticleTinting.GradientEntry): java.lang.Float {
         return it.colour.x;
      }

      @JvmStatic
      fun `CODEC$lambda$6$lambda$2`(it: GradientParticleTinting.GradientEntry): java.lang.Float {
         return it.colour.y;
      }

      @JvmStatic
      fun `CODEC$lambda$6$lambda$3`(it: GradientParticleTinting.GradientEntry): java.lang.Float {
         return it.colour.z;
      }

      @JvmStatic
      fun `CODEC$lambda$6$lambda$4`(it: GradientParticleTinting.GradientEntry): java.lang.Float {
         return it.colour.w;
      }

      @JvmStatic
      fun `CODEC$lambda$6$lambda$5`(key: java.lang.Double, red: java.lang.Float, green: java.lang.Float, blue: java.lang.Float, alpha: java.lang.Float): GradientParticleTinting.GradientEntry {
         val var10002: Double = key;
         val var10005: Float = red;
         val var10006: Float = green;
         val var10007: Float = blue;
         return new GradientParticleTinting.GradientEntry(var10002, new Vector4f(var10005, var10006, var10007, alpha));
      }

      @JvmStatic
      fun `CODEC$lambda$6`(instance: Instance): App {
         return instance.group(
               PrimitiveCodec.DOUBLE.fieldOf("key").forGetter(GradientParticleTinting.GradientEntry::CODEC$lambda$6$lambda$0) as App,
               PrimitiveCodec.FLOAT.fieldOf("red").forGetter(GradientParticleTinting.GradientEntry::CODEC$lambda$6$lambda$1) as App,
               PrimitiveCodec.FLOAT.fieldOf("green").forGetter(GradientParticleTinting.GradientEntry::CODEC$lambda$6$lambda$2) as App,
               PrimitiveCodec.FLOAT.fieldOf("blue").forGetter(GradientParticleTinting.GradientEntry::CODEC$lambda$6$lambda$3) as App,
               PrimitiveCodec.FLOAT.fieldOf("alpha").forGetter(GradientParticleTinting.GradientEntry::CODEC$lambda$6$lambda$4) as App
            )
            .apply(instance as Applicative, GradientParticleTinting.GradientEntry::CODEC$lambda$6$lambda$5);
      }

      @JvmStatic
      fun {
         val var10000: Codec = RecordCodecBuilder.create(GradientParticleTinting.GradientEntry::CODEC$lambda$6);
         CODEC = var10000;
      }

      public companion object {
         public final val CODEC: Codec<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.GradientParticleTinting.GradientEntry>
      }
   }
}
