package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.PropertiesCompletionRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.ArrayList;
import java.util.HashSet
import java.util.Locale
import java.util.concurrent.CompletableFuture
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager

@SourceDebugExtension(["SMAP\nPropertiesCompletionProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PropertiesCompletionProvider.kt\ncom/cobblemon/mod/common/pokemon/properties/PropertiesCompletionProvider\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,171:1\n1360#2:172\n1446#2,5:173\n1855#2:178\n2624#2,3:179\n1855#2,2:182\n1856#2:184\n288#2,2:185\n1855#2,2:187\n1549#2:193\n1620#2,3:194\n1549#2:197\n1620#2,3:198\n1549#2:201\n1620#2,3:202\n1549#2:205\n1620#2,3:206\n1855#2,2:209\n1549#2:211\n1620#2,3:212\n1855#2,2:215\n11335#3:189\n11670#3,3:190\n*S KotlinDebug\n*F\n+ 1 PropertiesCompletionProvider.kt\ncom/cobblemon/mod/common/pokemon/properties/PropertiesCompletionProvider\n*L\n79#1:172\n79#1:173,5\n92#1:178\n93#1:179,3\n94#1:182,2\n92#1:184\n122#1:185,2\n123#1:187,2\n137#1:193\n137#1:194,3\n138#1:197\n138#1:198,3\n139#1:201\n139#1:202,3\n142#1:205\n142#1:206,3\n147#1:209,2\n153#1:211\n153#1:212,3\n157#1:215,2\n135#1:189\n135#1:190,3\n*E\n"])
internal object PropertiesCompletionProvider : DataRegistry {
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("properties_tab_completion")
   public open val observable: SimpleObservable<PropertiesCompletionProvider> = new SimpleObservable()
   private final val providers: HashSet<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.PropertiesCompletionProvider.SuggestionHolder> = new HashSet()
   public open val type: PackType = PackType.SERVER_DATA

   public override fun reload(manager: ResourceManager) {
      this.reload();
   }

   public override fun sync(player: ServerPlayer) {
      new PropertiesCompletionRegistrySyncPacket(providers).sendToPlayer(player);
   }

   public fun reload() {
      providers.clear();
      this.addDefaults();
      this.addCustom();
   }

   public fun inject(keys: Iterable<String>, suggestions: Collection<String>) {
      providers.add(new PropertiesCompletionProvider.SuggestionHolder(CollectionsKt.toList(keys), suggestions));
   }

   public fun keys(): List<String> {
      val `$this$flatMap$iv`: java.lang.Iterable = providers;
      val `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$flatMap$iv) {
         CollectionsKt.addAll(`destination$iv$iv`, (`element$iv$iv` as PropertiesCompletionProvider.SuggestionHolder).getKeys());
      }

      return `destination$iv$iv` as MutableList<java.lang.String>;
   }

   public fun suggestKeys(partialKey: String, excludedKeys: Collection<String>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
      var matches: Int = 0;
      var exactMatch: Boolean = false;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val provider: PropertiesCompletionProvider.SuggestionHolder = `element$iv` as PropertiesCompletionProvider.SuggestionHolder;
         val `$this$forEach$ivx`: java.lang.Iterable = (`element$iv` as PropertiesCompletionProvider.SuggestionHolder).getKeys();
         var var10000: Boolean;
         if (`$this$forEach$ivx` is java.util.Collection && (`$this$forEach$ivx` as java.util.Collection).isEmpty()) {
            var10000 = true;
         } else {
            val var14: java.util.Iterator = `$this$forEach$ivx`.iterator();

            while (true) {
               if (!var14.hasNext()) {
                  var10000 = true;
                  break;
               }

               if (excludedKeys.contains(var14.next() as java.lang.String)) {
                  var10000 = false;
                  break;
               }
            }
         }

         if (var10000) {
            for (Object element$ivx : $this$forEach$ivx) {
               val var23: java.lang.String = `element$ivx` as java.lang.String;
               if (StringsKt.startsWith$default(`element$ivx` as java.lang.String, partialKey, false, 2, null)) {
                  val substring: java.lang.String = StringsKt.substringAfter$default(var23, partialKey, null, 2, null);
                  builder.suggest("${builder.getRemaining()}$substring");
                  matches++;
                  if (substring.length() == 0) {
                     exactMatch = true;
                  }
               }
            }
         }
      }

      if (matches == 1 && exactMatch) {
         builder.suggest("${builder.getRemaining()}=");
      }

      val var25: CompletableFuture = builder.buildFuture();
      return var25;
   }

   public fun suggestValues(possibleKey: String, currentValue: String, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
      val `element$iv`: java.util.Iterator = providers.iterator();

      var var10000: Any;
      while (true) {
         if (`element$iv`.hasNext()) {
            val suggestion: Any = `element$iv`.next();
            if (!(suggestion as PropertiesCompletionProvider.SuggestionHolder).getKeys().contains(possibleKey)) {
               continue;
            }

            var10000 = (CompletableFuture)suggestion;
            break;
         }

         var10000 = null;
         break;
      }

      if (var10000 as PropertiesCompletionProvider.SuggestionHolder == null) {
         var10000 = Suggestions.empty();
         return var10000;
      } else {
         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$ivx : $this$forEach$iv) {
            val var15: java.lang.String = `element$ivx` as java.lang.String;
            if (StringsKt.startsWith$default(`element$ivx` as java.lang.String, currentValue, false, 2, null)) {
               builder.suggest("${builder.getRemaining()}${StringsKt.substringAfter$default(var15, currentValue, null, 2, null)}");
            }
         }

         var10000 = builder.buildFuture();
         return var10000;
      }
   }

   private fun addDefaults() {
      this.inject(
         SetsKt.setOf(new java.lang.String[]{"level", "lvl", "l"}),
         SetsKt.setOf(new java.lang.String[]{"1", java.lang.String.valueOf(Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel())})
      );
      this.inject(SetsKt.setOf(new java.lang.String[]{"shiny", "s"}), SetsKt.setOf(new java.lang.String[]{"yes", "no"}));
      var var85: java.lang.Iterable = SetsKt.setOf("gender");
      val var17: Array<Gender> = Gender.values();
      var `destination$iv$iv`: java.util.Collection = new ArrayList(var17.length);

      for (Object item$iv$iv : var17) {
         val var10000: java.lang.String = ((Gender)it).name().toLowerCase(Locale.ROOT);
         `destination$iv$iv`.add(var10000);
      }

      this.inject(var85, `destination$iv$iv`);
      this.inject(
         SetsKt.setOf("friendship"),
         SetsKt.setOf(new java.lang.String[]{"0", java.lang.String.valueOf(Cobblemon.INSTANCE.getConfig().getMaxPokemonFriendship())})
      );
      var85 = SetsKt.setOf("pokeball");
      val var19: java.lang.Iterable = PokeBalls.INSTANCE.all();
      `destination$iv$iv` = new ArrayList(CollectionsKt.collectionSizeOrDefault(var19, 10));

      for (Object item$iv$iv : var19) {
         `destination$iv$iv`.add(
            if ((var56 as PokeBall).getName().m_135827_() == "cobblemon")
               (var56 as PokeBall).getName().m_135815_()
               else
               (var56 as PokeBall).getName().toString()
         );
      }

      this.inject(var85, `destination$iv$iv`);
      var85 = SetsKt.setOf("nature");
      val var20: java.lang.Iterable = Natures.INSTANCE.all();
      `destination$iv$iv` = new ArrayList(CollectionsKt.collectionSizeOrDefault(var20, 10));

      for (Object item$iv$iv : var20) {
         `destination$iv$iv`.add(
            if ((var57 as Nature).getName().m_135827_() == "cobblemon") (var57 as Nature).getName().m_135815_() else (var57 as Nature).getName().toString()
         );
      }

      this.inject(var85, `destination$iv$iv`);
      var85 = SetsKt.setOf("ability");
      val var21: java.lang.Iterable = Abilities.INSTANCE.all();
      `destination$iv$iv` = new ArrayList(CollectionsKt.collectionSizeOrDefault(var21, 10));

      for (Object item$iv$iv : var21) {
         `destination$iv$iv`.add(
            if (ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default((var58 as AbilityTemplate).getName(), null, 1, null).m_135827_()
                  == "cobblemon")
               ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default((var58 as AbilityTemplate).getName(), null, 1, null).m_135815_()
               else
               (var58 as AbilityTemplate).getName()
         );
      }

      this.inject(var85, `destination$iv$iv`);
      this.inject(
         SetsKt.setOf("dmax"), SetsKt.setOf(new java.lang.String[]{"0", java.lang.String.valueOf(Cobblemon.INSTANCE.getConfig().getMaxDynamaxLevel())})
      );
      this.inject(SetsKt.setOf("gmax"), SetsKt.setOf(new java.lang.String[]{"yes", "no"}));
      var85 = SetsKt.setOf("tera");
      val var24: java.lang.Iterable = ElementalTypes.INSTANCE.all();
      `destination$iv$iv` = new ArrayList(CollectionsKt.collectionSizeOrDefault(var24, 10));

      for (Object item$iv$iv : var24) {
         `destination$iv$iv`.add((var59 as ElementalType).getName());
      }

      this.inject(var85, `destination$iv$iv`);
      this.inject(SetsKt.setOf("tradeable"), SetsKt.setOf(new java.lang.String[]{"yes", "no"}));
      this.inject(SetsKt.setOf(new java.lang.String[]{"originaltrainer", "ot"}), SetsKt.setOf(""));
      this.inject(SetsKt.setOf(new java.lang.String[]{"originaltrainertype", "ottype"}), SetsKt.setOf(new java.lang.String[]{"None", "Player", "NPC"}));

      for (Object element$iv : var29) {
         val var83: java.lang.String = (var42 as Stat).toString().toLowerCase(Locale.ROOT);
         this.inject(SetsKt.setOf("$var83_iv"), SetsKt.setOf(new java.lang.String[]{"0", "31"}));
         this.inject(SetsKt.setOf("$var83_ev"), SetsKt.setOf(new java.lang.String[]{"0", "252"}));
      }

      var85 = SetsKt.setOf("status");
      val var30: java.lang.Iterable = Statuses.INSTANCE.getPersistentStatuses();
      `destination$iv$iv` = new ArrayList(CollectionsKt.collectionSizeOrDefault(var30, 10));

      for (Object item$iv$iv : var30) {
         `destination$iv$iv`.add(
            if ((var61 as Status).getName().m_135827_() == "cobblemon") (var61 as Status).getName().m_135815_() else (var61 as Status).getName().toString()
         );
      }

      this.inject(var85, `destination$iv$iv`);
   }

   private fun addCustom() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val property: CustomPokemonPropertyType = `element$iv` as CustomPokemonPropertyType;
         if ((`element$iv` as CustomPokemonPropertyType).getNeedsKey()) {
            this.inject(property.getKeys(), property.examples());
         }
      }
   }

   internal data class SuggestionHolder(keys: Collection<String>, suggestions: Collection<String>) {
      public final val keys: Collection<String>
      public final val suggestions: Collection<String>

      init {
         this.keys = keys;
         this.suggestions = suggestions;
      }

      public operator fun component1(): Collection<String> {
         return this.keys;
      }

      public operator fun component2(): Collection<String> {
         return this.suggestions;
      }

      public fun copy(keys: Collection<String> = this.keys, suggestions: Collection<String> = this.suggestions): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.PropertiesCompletionProvider.SuggestionHolder {
         return new PropertiesCompletionProvider.SuggestionHolder(keys, suggestions);
      }

      public override fun toString(): String {
         return "SuggestionHolder(keys=${this.keys}, suggestions=${this.suggestions})";
      }

      public override fun hashCode(): Int {
         return this.keys.hashCode() * 31 + this.suggestions.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is PropertiesCompletionProvider.SuggestionHolder) {
            return false;
         } else {
            val var2: PropertiesCompletionProvider.SuggestionHolder = other as PropertiesCompletionProvider.SuggestionHolder;
            if (!(this.keys == (other as PropertiesCompletionProvider.SuggestionHolder).keys)) {
               return false;
            } else {
               return this.suggestions == var2.suggestions;
            }
         }
      }
   }
}
