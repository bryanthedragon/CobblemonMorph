package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.BarSummarySpeciesFeatureRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.JsonObject
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nIntSpeciesFeature.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IntSpeciesFeature.kt\ncom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeatureProvider\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,181:1\n1#2:182\n800#3,11:183\n*S KotlinDebug\n*F\n+ 1 IntSpeciesFeature.kt\ncom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeatureProvider\n*L\n149#1:183,11\n*E\n"])
public class IntSpeciesFeatureProvider : SynchronizedSpeciesFeatureProvider<IntSpeciesFeature>, CustomPokemonPropertyType<IntSpeciesFeature> {
   public final var default: Int?
   public final var display: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.IntSpeciesFeatureProvider.DisplayData?
   public open var keys: List<String> = CollectionsKt.emptyList()
   public final var max: Int = 100
   public final var min: Int

   public open val needsKey: Boolean
      public open get() {
         return true;
      }


   public open var visible: Boolean

   public open fun fromString(value: String?): IntSpeciesFeature? {
      if (value != null) {
         var var10000: Int = StringsKt.toIntOrNull(value);
         if (var10000 != null) {
            val it: Int = var10000.intValue();
            val var5: Int = this.min;
            var10000 = if (it <= this.max && this.min <= it) var10000 else null;
            if ((if (it <= this.max && this.min <= it) var10000 else null) != null) {
               return new IntSpeciesFeature(CollectionsKt.first(this.getKeys()) as java.lang.String, var10000.intValue());
            }
         }
      }

      return null;
   }

   public open fun examples(): List<String> {
      return CollectionsKt.emptyList();
   }

   public open operator fun invoke(buffer: FriendlyByteBuf, name: String): IntSpeciesFeature? {
      return if (this.getKeys().contains(name)) new IntSpeciesFeature(name, buffer.readInt()) else null;
   }

   public open operator fun invoke(pokemon: Pokemon): IntSpeciesFeature? {
      var var10000: IntSpeciesFeature = this.get(pokemon);
      if (var10000 == null) {
         var10000 = if (this.default != null) new IntSpeciesFeature(CollectionsKt.first(this.getKeys()) as java.lang.String, this.default.intValue()) else null;
      }

      return var10000;
   }

   public open operator fun invoke(nbt: CompoundTag): IntSpeciesFeature? {
      return if (nbt.m_128441_(CollectionsKt.first(this.getKeys()) as java.lang.String))
         new IntSpeciesFeature(CollectionsKt.first(this.getKeys()) as java.lang.String, nbt.m_128451_(CollectionsKt.first(this.getKeys()) as java.lang.String))
         else
         null;
   }

   public open operator fun invoke(json: JsonObject): IntSpeciesFeature? {
      return if (json.has(CollectionsKt.first(this.getKeys()) as java.lang.String))
         new IntSpeciesFeature(
            CollectionsKt.first(this.getKeys()) as java.lang.String, json.get(CollectionsKt.first(this.getKeys()) as java.lang.String).getAsInt()
         )
         else
         null;
   }

   public open fun get(pokemon: Pokemon): IntSpeciesFeature? {
      val `$this$filterIsInstance$iv`: java.lang.Iterable = pokemon.getFeatures();
      val `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filterIsInstance$iv) {
         if (`element$iv$iv` is IntSpeciesFeature) {
            `destination$iv$iv`.add(`element$iv$iv`);
         }
      }

      val `$this$filterIsInstanceTo$iv$iv`: java.util.Iterator = (`destination$iv$iv` as java.util.List).iterator();

      var var10000: Any;
      while (true) {
         if (`$this$filterIsInstanceTo$iv$iv`.hasNext()) {
            val var10: Any = `$this$filterIsInstanceTo$iv$iv`.next();
            if (!this.getKeys().contains((var10 as IntSpeciesFeature).getName())) {
               continue;
            }

            var10000 = var10;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as IntSpeciesFeature;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_236828_(this.getKeys(), IntSpeciesFeatureProvider::encode$lambda$4);
      buffer.m_236821_(this.default, IntSpeciesFeatureProvider::encode$lambda$5);
      buffer.writeInt(this.min);
      buffer.writeInt(this.max);
      buffer.m_236821_(this.display, IntSpeciesFeatureProvider::encode$lambda$6);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      val var10001: java.util.List = buffer.m_236845_(IntSpeciesFeatureProvider::decode$lambda$7);
      this.setKeys(var10001);
      this.default = buffer.m_236868_(IntSpeciesFeatureProvider::decode$lambda$8) as Int;
      this.min = buffer.readInt();
      this.max = buffer.readInt();
      this.display = buffer.m_236868_(IntSpeciesFeatureProvider::decode$lambda$10) as IntSpeciesFeatureProvider.DisplayData;
   }

   public override fun getRenderer(pokemon: Pokemon): SummarySpeciesFeatureRenderer<IntSpeciesFeature>? {
      val var10000: BarSummarySpeciesFeatureRenderer;
      if (this.display != null) {
         val it: IntSpeciesFeatureProvider.DisplayData = this.display;
         var10000 = new BarSummarySpeciesFeatureRenderer;
         val var10002: java.lang.String = CollectionsKt.first(this.getKeys()) as java.lang.String;
         val var10003: MutableComponent = MiscUtilsKt.asTranslated(it.getName());
         val var10004: Int = this.min;
         val var10005: Int = this.max;
         val var10006: Vec3 = it.getColour();
         var var10007: ResourceLocation = it.getUnderlay();
         if (var10007 == null) {
            var10007 = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_stats_other_bar.png");
         }

         var var10008: ResourceLocation = it.getOverlay();
         if (var10008 == null) {
            var10008 = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_stats_generic_overlay.png");
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, var10003, var10004, var10005, var10006, var10007, var10008, pokemon);
      } else {
         var10000 = null;
      }

      return var10000;
   }

   @JvmStatic
   fun `encode$lambda$4`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, value: java.lang.String) {
      `$buffer`.m_130070_(value);
   }

   @JvmStatic
   fun `encode$lambda$5`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, value: Int) {
      `$buffer`.writeInt(value);
   }

   @JvmStatic
   fun `encode$lambda$6`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, value: IntSpeciesFeatureProvider.DisplayData) {
      value.encode(`$buffer`);
   }

   @JvmStatic
   fun `decode$lambda$7`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   @JvmStatic
   fun `decode$lambda$8`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): Int {
      return `$buffer`.readInt();
   }

   @JvmStatic
   fun `decode$lambda$10`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): IntSpeciesFeatureProvider.DisplayData {
      val var2: IntSpeciesFeatureProvider.DisplayData = new IntSpeciesFeatureProvider.DisplayData();
      var2.decode(`$buffer`);
      return var2;
   }

   public class DisplayData : Encodable, Decodable {
      public final var colour: Vec3 = new Vec3(255.0, 255.0, 255.0)
      public final var name: String = ""
      public final var overlay: ResourceLocation?
      public final var underlay: ResourceLocation?

      public override fun decode(buffer: FriendlyByteBuf) {
         val var10001: java.lang.String = buffer.m_130277_();
         this.name = var10001;
         this.colour = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
         this.underlay = buffer.m_236868_(IntSpeciesFeatureProvider.DisplayData::decode$lambda$0) as ResourceLocation;
         this.overlay = buffer.m_236868_(IntSpeciesFeatureProvider.DisplayData::decode$lambda$1) as ResourceLocation;
      }

      public override fun encode(buffer: FriendlyByteBuf) {
         buffer.m_130070_(this.name);
         buffer.writeDouble(this.colour.f_82479_);
         buffer.writeDouble(this.colour.f_82480_);
         buffer.writeDouble(this.colour.f_82481_);
         buffer.m_236821_(this.underlay, IntSpeciesFeatureProvider.DisplayData::encode$lambda$2);
         buffer.m_236821_(this.overlay, IntSpeciesFeatureProvider.DisplayData::encode$lambda$3);
      }

      @JvmStatic
      fun `decode$lambda$0`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): ResourceLocation {
         return `$buffer`.m_130281_();
      }

      @JvmStatic
      fun `decode$lambda$1`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): ResourceLocation {
         return `$buffer`.m_130281_();
      }

      @JvmStatic
      fun `encode$lambda$2`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, value: ResourceLocation) {
         `$buffer`.m_130085_(value);
      }

      @JvmStatic
      fun `encode$lambda$3`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, value: ResourceLocation) {
         `$buffer`.m_130085_(value);
      }
   }
}
