package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.JsonObject
import java.util.ArrayList;
import java.util.Locale
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nChoiceSpeciesFeatureProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChoiceSpeciesFeatureProvider.kt\ncom/cobblemon/mod/common/api/pokemon/feature/ChoiceSpeciesFeatureProvider\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,142:1\n1#2:143\n1855#3,2:144\n800#3,11:146\n*S KotlinDebug\n*F\n+ 1 ChoiceSpeciesFeatureProvider.kt\ncom/cobblemon/mod/common/api/pokemon/feature/ChoiceSpeciesFeatureProvider\n*L\n71#1:144,2\n132#1:146,11\n*E\n"])
public open class ChoiceSpeciesFeatureProvider(keys: List<String>,
      default: String? = null,
      choices: List<String> = CollectionsKt.emptyList(),
      isAspect: Boolean = true,
      aspectFormat: String = "{{choice}}"
   ) :
   SynchronizedSpeciesFeatureProvider<StringSpeciesFeature>,
   CustomPokemonPropertyType<StringSpeciesFeature>,
   AspectProvider {
   public final var aspectFormat: String
   public final var choices: List<String>
   public final var default: String?
   public final var isAspect: Boolean
   public open var keys: List<String>
   public open var needsKey: Boolean
   public open var visible: Boolean

   init {
      this.keys = keys;
      this.default = defaultx;
      this.choices = choices;
      this.isAspect = isAspect;
      this.aspectFormat = aspectFormat;
      this.needsKey = true;
   }

   public fun getAspect(feature: StringSpeciesFeature): String {
      return MiscUtilsKt.substitute(this.aspectFormat, "choice", feature.getValue());
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_236828_(this.getKeys(), ChoiceSpeciesFeatureProvider::encode$lambda$0);
      buffer.m_236821_(this.default, ChoiceSpeciesFeatureProvider::encode$lambda$1);
      buffer.m_236828_(this.choices, ChoiceSpeciesFeatureProvider::encode$lambda$2);
      buffer.writeBoolean(this.isAspect);
      buffer.m_130070_(this.aspectFormat);
      buffer.writeBoolean(this.getNeedsKey());
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      var var10001: java.util.List = buffer.m_236845_(ChoiceSpeciesFeatureProvider::decode$lambda$3);
      this.setKeys(var10001);
      this.default = buffer.m_236868_(ChoiceSpeciesFeatureProvider::decode$lambda$4) as java.lang.String;
      var10001 = buffer.m_236845_(ChoiceSpeciesFeatureProvider::decode$lambda$5);
      this.choices = var10001;
      this.isAspect = buffer.readBoolean();
      val var3: java.lang.String = buffer.m_130277_();
      this.aspectFormat = var3;
      this.setNeedsKey(buffer.readBoolean());
   }

   public override fun getRenderer(pokemon: Pokemon): SummarySpeciesFeatureRenderer<StringSpeciesFeature>? {
      return null;
   }

   public open operator fun invoke(buffer: FriendlyByteBuf, name: String): StringSpeciesFeature? {
      val var10000: StringSpeciesFeature;
      if (this.getKeys().contains(name)) {
         val var3: StringSpeciesFeature = new StringSpeciesFeature(name, "");
         var3.decode(buffer);
         var10000 = var3;
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public fun getAllAspects(): MutableList<String> {
      val aspects: java.util.List = CollectionsKt.toMutableList(this.choices);

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         aspects.set(
            this.choices.indexOf(`element$iv` as java.lang.String), MiscUtilsKt.substitute(this.aspectFormat, "choice", `element$iv` as java.lang.String)
         );
      }

      return aspects;
   }

   public open fun examples(): List<String> {
      return this.choices;
   }

   internal constructor() : this(CollectionsKt.emptyList(), null, null, false, null, 30, null)
   public open fun get(pokemon: Pokemon): StringSpeciesFeature? {
      return pokemon.getFeature(CollectionsKt.first(this.getKeys()) as java.lang.String);
   }

   public open operator fun invoke(pokemon: Pokemon): StringSpeciesFeature? {
      val existing: StringSpeciesFeature = this.get(pokemon);
      val var4: StringSpeciesFeature;
      if (existing != null && this.choices.contains(existing.getValue())) {
         var4 = existing;
      } else {
         val var10000: java.lang.String;
         if (CollectionsKt.contains(this.choices, this.default)) {
            var10000 = this.default;
         } else {
            if (!(this.default == "random")) {
               return null;
            }

            var10000 = CollectionsKt.randomOrNull(this.choices, Random.Default as Random) as java.lang.String;
            if (var10000 == null) {
               throw new IllegalStateException(
                  "The 'choices' list is empty for species feature provider: ${CollectionsKt.joinToString$default(
                     this.getKeys(), null, null, null, 0, null, null, 63, null
                  )}"
               );
            }
         }

         var4 = this.fromString(var10000);
      }

      return var4;
   }

   public open operator fun invoke(nbt: CompoundTag): StringSpeciesFeature? {
      val var10000: StringSpeciesFeature;
      if (nbt.m_128441_(CollectionsKt.first(this.getKeys()) as java.lang.String)) {
         val var2: StringSpeciesFeature = new StringSpeciesFeature(CollectionsKt.first(this.getKeys()) as java.lang.String, "");
         var2.loadFromNBT(nbt);
         var10000 = var2;
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public open operator fun invoke(json: JsonObject): StringSpeciesFeature? {
      val var10000: StringSpeciesFeature;
      if (json.has(CollectionsKt.first(this.getKeys()) as java.lang.String)) {
         val var2: StringSpeciesFeature = new StringSpeciesFeature(CollectionsKt.first(this.getKeys()) as java.lang.String, "");
         var2.loadFromJSON(json);
         var10000 = var2;
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public open fun fromString(value: String?): StringSpeciesFeature? {
      val var10000: java.lang.String;
      if (value != null) {
         var10000 = value.toLowerCase(Locale.ROOT);
      } else {
         var10000 = null;
      }

      return if (var10000 != null && this.choices.contains(var10000))
         new StringSpeciesFeature(CollectionsKt.first(this.getKeys()) as java.lang.String, var10000)
         else
         null;
   }

   public override fun provide(pokemon: Pokemon): Set<String> {
      var var5: java.util.Set;
      if (this.isAspect) {
         val var10000: StringSpeciesFeature = this.get(pokemon);
         if (var10000 != null) {
            var5 = SetsKt.setOf(this.getAspect(var10000));
            if (var5 != null) {
               return var5;
            }
         }

         var5 = SetsKt.emptySet();
      } else {
         var5 = SetsKt.emptySet();
      }

      return var5;
   }

   public override fun provide(properties: PokemonProperties): Set<String> {
      val var14: java.util.Set;
      if (this.isAspect) {
         val `$this$filterIsInstance$iv`: java.lang.Iterable = properties.getCustomProperties();
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filterIsInstance$iv) {
            if (`element$iv$iv` is StringSpeciesFeature) {
               `destination$iv$iv`.add(`element$iv$iv`);
            }
         }

         val `$this$filterIsInstanceTo$iv$iv`: java.util.Iterator = (`destination$iv$iv` as java.util.List).iterator();

         while (true) {
            if (!`$this$filterIsInstanceTo$iv$iv`.hasNext()) {
               var10000 = null;
               break;
            }

            val var11: Any = `$this$filterIsInstanceTo$iv$iv`.next();
            if ((var11 as StringSpeciesFeature).getName() == CollectionsKt.first(this.getKeys())) {
               var10000 = var11;
               break;
            }
         }

         var14 = if (var10000 as StringSpeciesFeature != null) SetsKt.setOf(this.getAspect(var10000 as StringSpeciesFeature)) else SetsKt.emptySet();
      } else {
         var14 = SetsKt.emptySet();
      }

      return var14;
   }

   override fun register(): AspectProvider {
      return AspectProvider.DefaultImpls.register(this);
   }

   @JvmStatic
   fun `encode$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, value: java.lang.String) {
      `$buffer`.m_130070_(value);
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
   fun `decode$lambda$3`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   @JvmStatic
   fun `decode$lambda$4`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   @JvmStatic
   fun `decode$lambda$5`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }
}
