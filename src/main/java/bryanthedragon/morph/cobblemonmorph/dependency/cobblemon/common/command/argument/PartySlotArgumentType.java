package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import com.mojang.brigadier.ImmutableStringReader
import com.mojang.brigadier.Message
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nPartySlotArgumentType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartySlotArgumentType.kt\ncom/cobblemon/mod/common/command/argument/PartySlotArgumentType\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,66:1\n1549#2:67\n1620#2,3:68\n*S KotlinDebug\n*F\n+ 1 PartySlotArgumentType.kt\ncom/cobblemon/mod/common/command/argument/PartySlotArgumentType\n*L\n46#1:67\n46#1:68,3\n*E\n"])
public class PartySlotArgumentType : ArgumentType<Integer> {
   public open fun parse(reader: StringReader): Int {
      val slot: Int = reader.readInt();
      if (slot < 1) {
         val var3: CommandSyntaxException = CommandSyntaxException.BUILT_IN_EXCEPTIONS
            .integerTooLow()
            .createWithContext(reader as ImmutableStringReader, slot, 1);
         throw var3 as java.lang.Throwable;
      } else if (slot > 6) {
         val var10000: CommandSyntaxException = CommandSyntaxException.BUILT_IN_EXCEPTIONS
            .integerTooHigh()
            .createWithContext(reader as ImmutableStringReader, slot, 6);
         throw var10000 as java.lang.Throwable;
      } else {
         return slot;
      }
   }

   public open fun <S : Any> listSuggestions(context: CommandContext<Any>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
      val var10000: CompletableFuture = SharedSuggestionProvider.m_82970_(EXAMPLES, builder);
      return var10000;
   }

   public open fun getExamples(): List<String> {
      return EXAMPLES;
   }

   @JvmStatic
   fun `INVALID_SLOT$lambda$1`(slot: Any): Message {
      val var1: Array<Any> = new Object[1];
      var1[0] = slot;
      val var10000: MutableComponent = LocalizationUtilsKt.commandLang("general.invalid-party-slot", var1);
      return TextKt.red(var10000) as Message;
   }

   @JvmStatic
   fun {
      val `$this$map$iv`: java.lang.Iterable = (new IntRange(1, 6)) as java.lang.Iterable;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));
      val var5: java.util.Iterator = `$this$map$iv`.iterator();

      while (var5.hasNext()) {
         `destination$iv$iv`.add(java.lang.String.valueOf((var5 as IntIterator).nextInt()));
      }

      EXAMPLES = `destination$iv$iv` as MutableList<java.lang.String>;
      INVALID_SLOT = new DynamicCommandExceptionType(PartySlotArgumentType::INVALID_SLOT$lambda$1);
   }

   public companion object {
      private final val EXAMPLES: List<String>
      private final val INVALID_SLOT: DynamicCommandExceptionType
      private const val MAX: Int
      private const val MIN: Int

      public fun partySlot(): PartySlotArgumentType {
         return new PartySlotArgumentType();
      }

      public fun <S> getPokemon(context: CommandContext<Any>, name: String): Pokemon {
         val slot: Int = context.getArgument(name, int.class) as Int;
         val party: Any = context.getSource();
         val var10000: CommandSourceStack = party as? CommandSourceStack;
         if ((party as? CommandSourceStack) == null) {
            val var13: CommandSyntaxException = CommandSourceStack.f_81286_.create();
            throw var13 as java.lang.Throwable;
         } else {
            val var7: Entity = var10000.m_81373_();
            val var9: ServerPlayer = var7 as? ServerPlayer;
            if ((var7 as? ServerPlayer) == null) {
               val var12: CommandSyntaxException = CommandSourceStack.f_81286_.create();
               throw var12 as java.lang.Throwable;
            } else {
               val var10: Pokemon = Cobblemon.INSTANCE.getStorage().getParty(var9).get(slot - 1);
               if (var10 == null) {
                  val var11: CommandSyntaxException = PartySlotArgumentType.access$getINVALID_SLOT$cp().create(slot);
                  throw var11 as java.lang.Throwable;
               } else {
                  return var10;
               }
            }
         }
      }

      public fun <S> getPokemonOf(context: CommandContext<Any>, name: String, player: ServerPlayer): Pokemon {
         val slot: Int = context.getArgument(name, int.class) as Int;
         val var10000: Pokemon = Cobblemon.INSTANCE.getStorage().getParty(player).get(slot - 1);
         if (var10000 == null) {
            val var6: CommandSyntaxException = PartySlotArgumentType.access$getINVALID_SLOT$cp().create(slot);
            throw var6 as java.lang.Throwable;
         } else {
            return var10000;
         }
      }
   }
}
