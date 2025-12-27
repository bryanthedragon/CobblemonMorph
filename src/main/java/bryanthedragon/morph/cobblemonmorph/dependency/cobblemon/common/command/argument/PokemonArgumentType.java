package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.mojang.brigadier.ImmutableStringReader
import com.mojang.brigadier.Message
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nPokemonArgumentType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonArgumentType.kt\ncom/cobblemon/mod/common/command/argument/PokemonArgumentType\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,53:1\n1549#2:54\n1620#2,3:55\n*S KotlinDebug\n*F\n+ 1 PokemonArgumentType.kt\ncom/cobblemon/mod/common/command/argument/PokemonArgumentType\n*L\n49#1:54\n49#1:55,3\n*E\n"])
public class PokemonArgumentType : ArgumentType<Species> {
   public open fun parse(reader: StringReader): Species {
      val var10000: Species = PokemonSpecies.INSTANCE
         .getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(reader, null, 1, null));
      if (var10000 == null) {
         val var2: CommandSyntaxException = new SimpleCommandExceptionType(INVALID_POKEMON as Message).createWithContext(reader as ImmutableStringReader);
         throw var2 as java.lang.Throwable;
      } else {
         return var10000;
      }
   }

   public open fun <S : Any> listSuggestions(context: CommandContext<Any>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
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

      val var10000: CompletableFuture = SharedSuggestionProvider.m_82970_(`destination$iv$iv` as java.util.List, builder);
      return var10000;
   }

   public open fun getExamples(): List<String> {
      return EXAMPLES;
   }

   public companion object {
      public final val EXAMPLES: List<String>
      public final val INVALID_POKEMON: MutableComponent

      public fun pokemon(): PokemonArgumentType {
         return new PokemonArgumentType();
      }

      public fun <S> getPokemon(context: CommandContext<Any>, name: String): Species {
         val var10000: Any = context.getArgument(name, Species.class);
         return var10000 as Species;
      }
   }
}
