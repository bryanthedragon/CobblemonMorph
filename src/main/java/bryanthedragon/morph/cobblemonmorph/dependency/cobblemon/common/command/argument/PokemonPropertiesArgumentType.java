package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.PropertiesCompletionProvider
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.SharedSuggestionProvider

@SourceDebugExtension(["SMAP\nPokemonPropertiesArgumentType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonPropertiesArgumentType.kt\ncom/cobblemon/mod/common/command/argument/PokemonPropertiesArgumentType\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,72:1\n766#2:73\n857#2,2:74\n1549#2:76\n1620#2,3:77\n1549#2:80\n1620#2,3:81\n*S KotlinDebug\n*F\n+ 1 PokemonPropertiesArgumentType.kt\ncom/cobblemon/mod/common/command/argument/PokemonPropertiesArgumentType\n*L\n61#1:73\n61#1:74,2\n61#1:76\n61#1:77,3\n69#1:80\n69#1:81,3\n*E\n"])
public class PokemonPropertiesArgumentType : ArgumentType<PokemonProperties> {
   public open fun parse(reader: StringReader): PokemonProperties {
      val properties: java.lang.String = reader.getRemaining();
      reader.setCursor(reader.getTotalLength());
      val var10000: PokemonProperties.Companion = PokemonProperties.Companion;
      return PokemonProperties.Companion.parse$default(var10000, properties, null, null, 6, null);
   }

   public open fun <S> listSuggestions(context: CommandContext<Any>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
      val var10000: java.lang.String = builder.getRemainingLowerCase();
      val sections: java.util.List = StringsKt.split$default(var10000, new java.lang.String[]{" "}, false, 0, 6, null);
      if (sections.isEmpty()) {
         val var29: CompletableFuture = this.suggestSpeciesAndPropertyKeys(builder);
         return var29;
      } else {
         val var16: java.lang.String = CollectionsKt.last(sections) as java.lang.String;
         if (StringsKt.contains$default(var16, ASSIGNER, false, 2, null)) {
            return PropertiesCompletionProvider.INSTANCE
               .suggestValues(
                  StringsKt.substringBefore$default(var16, ASSIGNER, null, 2, null), StringsKt.substringAfter$default(var16, ASSIGNER, null, 2, null), builder
               );
         } else if (sections.size() < 2) {
            val var28: CompletableFuture = this.suggestSpeciesAndPropertyKeys(builder);
            return var28;
         } else {
            var `$this$map$iv`: java.lang.Iterable = sections;
            var `destination$iv$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : $this$filter$iv) {
               if (StringsKt.contains$default(`item$iv$iv` as java.lang.String, "=", false, 2, null)) {
                  `destination$iv$iv`.add(`item$iv$iv`);
               }
            }

            `$this$map$iv` = `destination$iv$iv` as java.util.List;
            `destination$iv$iv` = new ArrayList(CollectionsKt.collectionSizeOrDefault(`destination$iv$iv` as java.util.List, 10));

            for (Object item$iv$iv : $this$filter$iv) {
               `destination$iv$iv`.add(StringsKt.substringBefore$default(var24 as java.lang.String, "=", null, 2, null));
            }

            return PropertiesCompletionProvider.INSTANCE.suggestKeys(var16, CollectionsKt.toSet(`destination$iv$iv` as java.util.List), builder);
         }
      }
   }

   private fun suggestSpeciesAndPropertyKeys(builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
      return SharedSuggestionProvider.m_82970_(CollectionsKt.plus(this.collectSpeciesIdentifiers(), PropertiesCompletionProvider.INSTANCE.keys()), builder);
   }

   private fun collectSpeciesIdentifiers(): List<String> {
      val `$this$map$iv`: java.lang.Iterable = PokemonSpecies.INSTANCE.getSpecies();
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add(
            if ((`item$iv$iv` as Species).getResourceIdentifier().m_135827_() == "cobblemon")
               (`item$iv$iv` as Species).getResourceIdentifier().m_135815_()
               else
               (`item$iv$iv` as Species).getResourceIdentifier().toString()
         );
      }

      return `destination$iv$iv` as MutableList<java.lang.String>;
   }

   public open fun getExamples(): List<String> {
      return EXAMPLES;
   }

   public companion object {
      private final val ASSIGNER: String
      public final val EXAMPLES: List<String>

      public fun properties(): PokemonPropertiesArgumentType {
         return new PokemonPropertiesArgumentType();
      }

      public fun <S> getPokemonProperties(context: CommandContext<Any>, name: String): PokemonProperties {
         val var10000: Any = context.getArgument(name, PokemonProperties.class);
         return var10000 as PokemonProperties;
      }
   }
}
