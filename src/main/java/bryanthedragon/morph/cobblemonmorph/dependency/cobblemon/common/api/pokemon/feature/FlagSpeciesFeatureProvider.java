package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonObject
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nFlagSpeciesFeature.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlagSpeciesFeature.kt\ncom/cobblemon/mod/common/api/pokemon/feature/FlagSpeciesFeatureProvider\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,185:1\n1#2:186\n800#3,11:187\n*S KotlinDebug\n*F\n+ 1 FlagSpeciesFeature.kt\ncom/cobblemon/mod/common/api/pokemon/feature/FlagSpeciesFeatureProvider\n*L\n179#1:187,11\n*E\n"])
public class FlagSpeciesFeatureProvider : SynchronizedSpeciesFeatureProvider<FlagSpeciesFeature>, CustomPokemonPropertyType<FlagSpeciesFeature>, AspectProvider {
   public final var default: String?
   public final var isAspect: Boolean = true
   public open var keys: List<String> = CollectionsKt.emptyList()

   public open val needsKey: Boolean
      public open get() {
         return true;
      }


   public open var visible: Boolean

   public open operator fun invoke(buffer: FriendlyByteBuf, name: String): FlagSpeciesFeature? {
      val var10000: FlagSpeciesFeature;
      if (this.getKeys().contains(name)) {
         val var3: FlagSpeciesFeature = new FlagSpeciesFeature(name);
         var3.decode(buffer);
         var10000 = var3;
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_236828_(this.getKeys(), FlagSpeciesFeatureProvider::encode$lambda$1);
      buffer.m_236821_(this.default, FlagSpeciesFeatureProvider::encode$lambda$2);
      buffer.writeBoolean(this.isAspect);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      val var10001: java.util.List = buffer.m_236845_(FlagSpeciesFeatureProvider::decode$lambda$3);
      this.setKeys(var10001);
      this.default = buffer.m_236868_(FlagSpeciesFeatureProvider::decode$lambda$4) as java.lang.String;
      this.isAspect = buffer.readBoolean();
   }

   public override fun getRenderer(pokemon: Pokemon): SummarySpeciesFeatureRenderer<FlagSpeciesFeature>? {
      return null;
   }

   public open fun examples(): Set<String> {
      return SetsKt.setOf(new java.lang.String[]{"true", "false"});
   }

   internal constructor() : this.setKeys(CollectionsKt.emptyList())
   public constructor(keys: List<String>) : this.setKeys(keys)
   public constructor(keys: List<String>, default: Boolean) : this.setKeys(keys) {
      this.default = java.lang.String.valueOf(defaultx);
   }

   public constructor(vararg keys: String) : this(ArraysKt.toList(keys))
   public open fun get(pokemon: Pokemon): FlagSpeciesFeature? {
      return pokemon.getFeature(CollectionsKt.first(this.getKeys()) as java.lang.String);
   }

   public open operator fun invoke(pokemon: Pokemon): FlagSpeciesFeature? {
      var var10000: FlagSpeciesFeature = this.get(pokemon);
      if (var10000 == null) {
         val var2: java.lang.String = this.default;
         var10000 = if (this.default == "random")
            new FlagSpeciesFeature(CollectionsKt.first(this.getKeys()) as java.lang.String, Random.Default.nextBoolean())
            else
            (
               if (CollectionsKt.contains(SetsKt.setOf(new java.lang.String[]{"true", "false"}), var2))
                  new FlagSpeciesFeature(CollectionsKt.first(this.getKeys()) as java.lang.String, java.lang.Boolean.parseBoolean(this.default))
                  else
                  null
            );
      }

      return var10000;
   }

   public open operator fun invoke(nbt: CompoundTag): FlagSpeciesFeature? {
      val var10000: FlagSpeciesFeature;
      if (nbt.m_128441_(CollectionsKt.first(this.getKeys()) as java.lang.String)) {
         val var2: FlagSpeciesFeature = new FlagSpeciesFeature(CollectionsKt.first(this.getKeys()) as java.lang.String, false);
         var2.loadFromNBT(nbt);
         var10000 = var2;
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public open operator fun invoke(json: JsonObject): FlagSpeciesFeature? {
      val var10000: FlagSpeciesFeature;
      if (json.has(CollectionsKt.first(this.getKeys()) as java.lang.String)) {
         val var2: FlagSpeciesFeature = new FlagSpeciesFeature(CollectionsKt.first(this.getKeys()) as java.lang.String, false);
         var2.loadFromJSON(json);
         var10000 = var2;
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public open fun fromString(value: String?): FlagSpeciesFeature? {
      if (value != null && !this.examples().contains(value)) {
         return null;
      } else {
         return if (value == null)
            new FlagSpeciesFeature(CollectionsKt.first(this.getKeys()) as java.lang.String, true)
            else
            new FlagSpeciesFeature(CollectionsKt.first(this.getKeys()) as java.lang.String, java.lang.Boolean.parseBoolean(value));
      }
   }

   public override fun provide(pokemon: Pokemon): Set<String> {
      if (this.isAspect) {
         val var10000: FlagSpeciesFeature = pokemon.getFeature(CollectionsKt.first(this.getKeys()) as java.lang.String);
         if (var10000 != null && var10000.getEnabled()) {
            return SetsKt.setOf(CollectionsKt.first(this.getKeys()));
         }
      }

      return SetsKt.emptySet();
   }

   public override fun provide(properties: PokemonProperties): Set<String> {
      if (this.isAspect) {
         val `$this$filterIsInstance$iv`: java.lang.Iterable = properties.getCustomProperties();
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filterIsInstance$iv) {
            if (`element$iv$iv` is FlagSpeciesFeature) {
               `destination$iv$iv`.add(`element$iv$iv`);
            }
         }

         val `$this$filterIsInstanceTo$iv$iv`: java.util.Iterator = (`destination$iv$iv` as java.util.List).iterator();

         var var10000: Any;
         while (true) {
            if (!`$this$filterIsInstanceTo$iv$iv`.hasNext()) {
               var10000 = null;
               break;
            }

            val var10: Any = `$this$filterIsInstanceTo$iv$iv`.next();
            if ((var10 as FlagSpeciesFeature).getName() == CollectionsKt.first(this.getKeys())) {
               var10000 = var10;
               break;
            }
         }

         if (var10000 as FlagSpeciesFeature != null && (var10000 as FlagSpeciesFeature).getEnabled()) {
            return SetsKt.setOf(CollectionsKt.first(this.getKeys()));
         }
      }

      return SetsKt.emptySet();
   }

   override fun register(): AspectProvider {
      return AspectProvider.DefaultImpls.register(this);
   }

   @JvmStatic
   fun `encode$lambda$1`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, value: java.lang.String) {
      `$buffer`.m_130070_(value);
   }

   @JvmStatic
   fun `encode$lambda$2`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, value: java.lang.String) {
      `$buffer`.m_130070_(value);
   }

   @JvmStatic
   fun `decode$lambda$3`(it: FriendlyByteBuf): java.lang.String {
      return it.m_130277_();
   }

   @JvmStatic
   fun `decode$lambda$4`(it: FriendlyByteBuf): java.lang.String {
      return it.m_130277_();
   }
}
