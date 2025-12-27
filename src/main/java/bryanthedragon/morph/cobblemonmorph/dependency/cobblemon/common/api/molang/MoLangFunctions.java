package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.struct.ArrayStruct
import com.bedrockk.molang.runtime.struct.QueryStruct
import com.bedrockk.molang.runtime.struct.VariableStruct
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.MoValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import java.util.HashMap
import java.util.Map.Entry
import java.util.function.Function
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.DoubleTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.TagKey
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.dimension.DimensionType

@SourceDebugExtension(["SMAP\nMoLangFunctions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoLangFunctions.kt\ncom/cobblemon/mod/common/api/molang/MoLangFunctions\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,196:1\n1360#2:197\n1446#2,2:198\n1549#2:200\n1620#2,3:201\n1448#2,3:204\n1603#2,9:214\n1855#2:223\n1856#2:225\n1612#2:226\n1855#2,2:227\n1855#2,2:231\n1855#2,2:233\n1864#2,3:235\n361#3,7:207\n1#4:224\n215#5,2:229\n*S KotlinDebug\n*F\n+ 1 MoLangFunctions.kt\ncom/cobblemon/mod/common/api/molang/MoLangFunctions\n*L\n107#1:197\n107#1:198,2\n107#1:200\n107#1:201,3\n107#1:204,3\n156#1:214,9\n156#1:223\n156#1:225\n156#1:226\n156#1:227,2\n178#1:231,2\n187#1:233,2\n76#1:235,3\n142#1:207,7\n156#1:224\n161#1:229,2\n*E\n"])
public object MoLangFunctions {
   public final val biomeFunctions: HashMap<String, Function<MoParams, Any>> = new HashMap()
   public final val blockFunctions: HashMap<String, Function<MoParams, Any>> = new HashMap()
   public final val dimensionTypeFunctions: HashMap<String, Function<MoParams, Any>> = new HashMap()
   public final val generalFunctions: HashMap<String, Function<MoParams, Any>> =
      MapsKt.hashMapOf(
         new Pair[]{
            TuplesKt.to("is_int", MoLangFunctions::generalFunctions$lambda$0),
            TuplesKt.to("is_number", MoLangFunctions::generalFunctions$lambda$1),
            TuplesKt.to("to_number", MoLangFunctions::generalFunctions$lambda$2),
            TuplesKt.to("to_int", MoLangFunctions::generalFunctions$lambda$3),
            TuplesKt.to("to_string", MoLangFunctions::generalFunctions$lambda$4),
            TuplesKt.to("do_effect_walks", MoLangFunctions::generalFunctions$lambda$5),
            TuplesKt.to("random", MoLangFunctions::generalFunctions$lambda$6),
            TuplesKt.to("curve", MoLangFunctions::generalFunctions$lambda$7),
            TuplesKt.to("array", MoLangFunctions::generalFunctions$lambda$9)
         }
      )
      public final val playerFunctions: MutableList<(ServerPlayer) -> HashMap<String, Function<MoParams, Any>>> =
      CollectionsKt.mutableListOf(new Function1[]{<unrepresentable>.INSTANCE})
      public final val worldFunctions: HashMap<String, Function<MoParams, Any>> = new HashMap()

   public fun Holder<Biome>.asBiomeMoLangValue(): ObjectValue<Holder<Biome>> {
      val var10003: ResourceKey = Registries.f_256952_;
      return this.addFunctions(this.asMoLangValue(`$this$asBiomeMoLangValue`, var10003), biomeFunctions);
   }

   public fun Holder<Level>.asWorldMoLangValue(): ObjectValue<Holder<Level>> {
      val var10003: ResourceKey = Registries.f_256858_;
      return this.addFunctions(this.asMoLangValue(`$this$asWorldMoLangValue`, var10003), worldFunctions);
   }

   public fun Holder<Block>.asBlockMoLangValue(): ObjectValue<Holder<Block>> {
      val var10003: ResourceKey = Registries.f_256747_;
      return this.addFunctions(this.asMoLangValue(`$this$asBlockMoLangValue`, var10003), blockFunctions);
   }

   public fun Holder<DimensionType>.asDimensionTypeMoLangValue(): ObjectValue<Holder<DimensionType>> {
      val var10003: ResourceKey = Registries.f_256787_;
      return this.addFunctions(this.asMoLangValue(`$this$asDimensionTypeMoLangValue`, var10003), dimensionTypeFunctions);
   }

   public fun ServerPlayer.asMoLangValue(): ObjectValue<ServerPlayer> {
      val value: ObjectValue = new ObjectValue(`$this$asMoLangValue`, <unrepresentable>.INSTANCE, null, 4, null);
      val var10001: QueryStruct = value;
      val `$this$flatMap$iv`: java.lang.Iterable = playerFunctions;
      val `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$flatMap$iv) {
         val var10000: java.util.Set = ((`element$iv$iv` as Function1).invoke(`$this$asMoLangValue`) as HashMap).entrySet();
         val `$this$map$iv`: java.lang.Iterable = var10000;
         val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10000, 10));

         for (Object item$iv$iv : $this$map$iv) {
            `destination$iv$ivx`.add(TuplesKt.to((`item$iv$iv` as Entry).getKey(), (`item$iv$iv` as Entry).getValue()));
         }

         CollectionsKt.addAll(`destination$iv$iv`, `destination$iv$ivx` as java.util.List);
      }

      this.addFunctions(var10001, MapsKt.toMap(`destination$iv$iv` as java.util.List));
      return value;
   }

   public fun <T> Holder<Any>.asMoLangValue(key: ResourceKey<Registry<Any>>): ObjectValue<Holder<Any>> {
      val value: ObjectValue = new ObjectValue(`$this$asMoLangValue`, <unrepresentable>.INSTANCE, null, 4, null);
      value.functions.put("is_in", MoLangFunctions::asMoLangValue$lambda$12);
      value.functions.put("is_of", MoLangFunctions::asMoLangValue$lambda$13);
      return value;
   }

   public fun QueryStruct.addStandardFunctions(): QueryStruct {
      `$this$addStandardFunctions`.functions.putAll(generalFunctions);
      return `$this$addStandardFunctions`;
   }

   public fun <T : QueryStruct> Any.addFunctions(functions: Map<String, Function<MoParams, Any>>): Any {
      `$this$addFunctions`.functions.putAll(functions);
      return (T)`$this$addFunctions`;
   }

   public fun <T : QueryStruct> Any.addFunction(name: String, function: Function<MoParams, Any>): Any {
      val var10000: HashMap = `$this$addFunction`.functions;
      var10000.put(name, function);
      return (T)`$this$addFunction`;
   }

   public fun MoLangEnvironment.getQueryStruct(name: String = "query"): QueryStruct {
      var var10000: HashMap = `$this$getQueryStruct`.getStructs();
      val `$this$getOrPut$iv`: java.util.Map = var10000;
      val `value$iv`: Any = var10000.get(name);
      if (`value$iv` == null) {
         val var7: Any = new QueryStruct(new HashMap<>());
         `$this$getOrPut$iv`.put(name, var7);
         var10000 = (HashMap)var7;
      } else {
         var10000 = (HashMap)`value$iv`;
      }

      return var10000 as QueryStruct;
   }

   public fun MoLangRuntime.setup(): MoLangRuntime {
      val var10002: MoLangEnvironment = `$this$setup`.getEnvironment();
      this.addStandardFunctions(getQueryStruct$default(this, var10002, null, 1, null));
      return `$this$setup`;
   }

   public fun writeMoValueToNBT(value: MoValue): Tag? {
      var var10000: Tag;
      if (value is DoubleValue) {
         var10000 = DoubleTag.m_128500_((value as DoubleValue).value) as Tag;
      } else if (value is StringValue) {
         var10000 = StringTag.m_129297_((value as StringValue).value) as Tag;
      } else if (value is ArrayStruct) {
         val nbt: java.util.Collection = (value as ArrayStruct).getMap().values();
         val `$this$forEach$iv`: ListTag = new ListTag();
         val `$i$f$forEach`: java.lang.Iterable = nbt;
         val `element$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            var10000 = this.writeMoValueToNBT(`element$iv$iv$iv` as MoValue);
            if (var10000 != null) {
               `element$iv`.add(var10000);
            }
         }

         for (Object element$ivx : $this$mapNotNull$iv) {
            `$this$forEach$iv`.add(`element$ivx` as Tag);
         }

         var10000 = `$this$forEach$iv` as Tag;
      } else if (value is VariableStruct) {
         val var20: CompoundTag = new CompoundTag();
         val var34: java.util.Map = (value as VariableStruct).getMap();

         for (Entry element$iv : var34.entrySet()) {
            val var30: java.lang.String = var26.getKey() as java.lang.String;
            val valuex: MoValue = var26.getValue() as MoValue;
            val var35: MoLangFunctions = INSTANCE;
            var10000 = var35.writeMoValueToNBT(valuex);
            if (var10000 != null) {
               var20.m_128365_(var30, var10000);
            }
         }

         var10000 = var20 as Tag;
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public fun readMoValueFromNBT(nbt: Tag): MoValue {
      val var10000: MoValue;
      if (nbt is DoubleTag) {
         var10000 = new DoubleValue((nbt as DoubleTag).m_7061_());
      } else if (nbt is StringTag) {
         var10000 = new StringValue(nbt.m_7916_());
      } else if (nbt is ListTag) {
         val variable: ArrayStruct = new ArrayStruct(new HashMap<>());
         var `$this$forEach$iv`: Int = 0;

         val `$i$f$forEach`: java.lang.Iterable;
         for (Object element$iv : $i$f$forEach) {
            val var9: Tag = key as Tag;
            val var20: MoLangFunctions = INSTANCE;
            variable.setDirectly(java.lang.String.valueOf(`$this$forEach$iv`), var20.readMoValueFromNBT(var9));
            `$this$forEach$iv`++;
         }

         var10000 = variable;
      } else if (nbt is CompoundTag) {
         val var12: VariableStruct = new VariableStruct(new HashMap<>());

         val var13: java.lang.Iterable;
         for (Object element$iv : var13) {
            val var17: java.lang.String = var16 as java.lang.String;
            val var22: MoLangFunctions = INSTANCE;
            val var10001: Tag = (nbt as CompoundTag).m_128423_(var17);
            val var19: MoValue = var22.readMoValueFromNBT(var10001);
            val var23: java.util.Map = var12.getMap();
            var23.put(var17, var19);
         }

         var10000 = var12;
      } else {
         var10000 = null;
      }

      if (var10000 == null) {
         throw new IllegalArgumentException("Invalid NBT element type: ${nbt.m_7060_()}");
      } else {
         return var10000;
      }
   }

   @JvmStatic
   fun `generalFunctions$lambda$0`(params: MoParams): Any {
      val var10002: java.lang.String = params.<MoValue>get(0).asString();
      return new DoubleValue(MiscUtilsKt.isInt(var10002));
   }

   @JvmStatic
   fun `generalFunctions$lambda$1`(params: MoParams): Any {
      val var10002: java.lang.String = params.<MoValue>get(0).asString();
      return new DoubleValue(StringsKt.toDoubleOrNull(var10002) != null);
   }

   @JvmStatic
   fun `generalFunctions$lambda$2`(params: MoParams): Any {
      val var10002: java.lang.String = params.<MoValue>get(0).asString();
      val var1: java.lang.Double = StringsKt.toDoubleOrNull(var10002);
      return new DoubleValue(var1 ?: 0.0);
   }

   @JvmStatic
   fun `generalFunctions$lambda$3`(params: MoParams): Any {
      val var10002: java.lang.String = params.<MoValue>get(0).asString();
      val var1: Int = StringsKt.toIntOrNull(var10002);
      return new DoubleValue(Integer.valueOf((int)(var1 ?: 0)));
   }

   @JvmStatic
   fun `generalFunctions$lambda$4`(params: MoParams): Any {
      return new StringValue(params.<MoValue>get(0).asString());
   }

   @JvmStatic
   fun `generalFunctions$lambda$5`(var0: MoParams): Any {
      return new DoubleValue(Cobblemon.INSTANCE.getConfig().getWalkingInBattleAnimations());
   }

   @JvmStatic
   fun `generalFunctions$lambda$6`(params: MoParams): Any {
      val options: java.util.List = new ArrayList();

      for (int index = 0; params.contains(index); index++) {
         val var10001: MoValue = params.get(index);
         options.add(var10001);
      }

      return CollectionsKt.random(options, Random.Default as Random);
   }

   @JvmStatic
   fun `generalFunctions$lambda$7`(params: MoParams): Any {
      val curveName: java.lang.String = params.getString(0);
      val var10000: Function1 = WaveFunctions.INSTANCE.getFunctions().get(curveName);
      if (var10000 == null) {
         throw new IllegalArgumentException("Unknown curve: $curveName");
      } else {
         return new ObjectValue(var10000, null, null, 6, null);
      }
   }

   @JvmStatic
   fun `generalFunctions$lambda$9`(params: MoParams): Any {
      val values: java.util.List = params.getParams();
      val array: ArrayStruct = new ArrayStruct(new HashMap<>());
      val `$this$forEachIndexed$iv`: java.lang.Iterable = values;
      var `index$iv`: Int = 0;

      for (Object item$iv : $this$forEachIndexed$iv) {
         val var8: Int = `index$iv`++;
         if (var8 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         array.setDirectly(java.lang.String.valueOf(var8), `item$iv` as MoValue);
      }

      return array;
   }

   @JvmStatic
   fun `asMoLangValue$lambda$12`(`$key`: ResourceKey, `$value`: ObjectValue, it: MoParams): Any {
      val var10003: java.lang.String = it.getString(0);
      return new DoubleValue(
         if ((`$value`.getObj() as Holder)
               .m_203656_(TagKey.m_203882_(`$key`, new ResourceLocation(StringsKt.replace$default(var10003, "#", "", false, 4, null)))))
            1.0
            else
            0.0
      );
   }

   @JvmStatic
   fun `asMoLangValue$lambda$13`(`$value`: ObjectValue, it: MoParams): Any {
      return new DoubleValue(if ((`$value`.getObj() as Holder).m_203373_(new ResourceLocation(it.getString(0)))) 1.0 else 0.0);
   }
}
