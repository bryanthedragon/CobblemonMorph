package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import com.mojang.brigadier.ImmutableStringReader
import com.mojang.brigadier.Message
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.concurrent.CompletableFuture
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class MoveArgumentType : ArgumentType<MoveTemplate> {
   public open fun parse(reader: StringReader): MoveTemplate {
      val var10000: Moves = Moves.INSTANCE;
      val var10001: java.lang.String = reader.readString();
      val var2: MoveTemplate = var10000.getByName(var10001);
      if (var2 == null) {
         val var3: CommandSyntaxException = new SimpleCommandExceptionType(INVALID_MOVE as Message).createWithContext(reader as ImmutableStringReader);
         throw var3 as java.lang.Throwable;
      } else {
         return var2;
      }
   }

   public open fun <S : Any> listSuggestions(context: CommandContext<Any>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
      val var10000: CompletableFuture = SharedSuggestionProvider.m_82970_(Moves.INSTANCE.names(), builder);
      return var10000;
   }

   public open fun getExamples(): List<String> {
      return EXAMPLES;
   }

   @JvmStatic
   fun {
      val var10000: MutableComponent = Component.m_237115_("cobblemon.command.pokespawn.invalid-move");
      INVALID_MOVE = var10000;
   }

   public companion object {
      public final val EXAMPLES: List<String>
      public final val INVALID_MOVE: MutableComponent

      public fun move(): MoveArgumentType {
         return new MoveArgumentType();
      }

      public fun <S> getMove(context: CommandContext<Any>, name: String): MoveTemplate {
         val var10000: Any = context.getArgument(name, MoveTemplate.class);
         return var10000 as MoveTemplate;
      }
   }
}
