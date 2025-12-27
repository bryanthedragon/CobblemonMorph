package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.BenchedMove
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.PermissionValidator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.LearnsetQuery
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.MoveArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PartySlotArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.Message
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nTeachCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TeachCommand.kt\ncom/cobblemon/mod/common/command/TeachCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,80:1\n1747#2,3:81\n*S KotlinDebug\n*F\n+ 1 TeachCommand.kt\ncom/cobblemon/mod/common/command/TeachCommand\n*L\n59#1:81,3\n*E\n"])
public object TeachCommand {
   private final val ALREADY_KNOWS_EXCEPTION: Dynamic2CommandExceptionType = new Dynamic2CommandExceptionType(TeachCommand::ALREADY_KNOWS_EXCEPTION$lambda$0)
   private final val CANT_LEARN_EXCEPTION: Dynamic2CommandExceptionType = new Dynamic2CommandExceptionType(TeachCommand::CANT_LEARN_EXCEPTION$lambda$1)
   private const val MOVE: String = "move"
   private const val NAME: String = "teach"
   private const val PLAYER: String = "player"
   private const val SLOT: String = "slot"

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10000: LiteralArgumentBuilder = Commands.m_82127_("teach");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10000 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getTEACH(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType)
                  .then(
                     Commands.m_82129_("slot", PartySlotArgumentType.Companion.partySlot())
                        .then(Commands.m_82129_("move", MoveArgumentType.Companion.move()).executes(TeachCommand::register$lambda$2))
                  )
            ) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>, player: ServerPlayer): Int {
      val pokemon: Pokemon = PartySlotArgumentType.Companion.getPokemonOf(context, "slot", player);
      val move: MoveTemplate = MoveArgumentType.Companion.getMove(context, "move");
      val var10000: PermissionValidator = Cobblemon.INSTANCE.getPermissionValidator();
      val var10001: Any = context.getSource();
      if (!var10000.hasPermission(var10001 as SharedSuggestionProvider, CobblemonPermissions.INSTANCE.getTEACH_BYPASS_LEARNSET())
         && !LearnsetQuery.Companion.getANY().canLearn(move, pokemon.getForm().getMoves())) {
         val var23: CommandSyntaxException = CANT_LEARN_EXCEPTION.create(pokemon.getDisplayName(), move.getDisplayName());
         throw var23 as java.lang.Throwable;
      } else {
         var pokemonLearntMessage: java.lang.Iterable = pokemon.getMoveSet().getMoves();
         var var19: Boolean;
         if (pokemonLearntMessage is java.util.Collection && (pokemonLearntMessage as java.util.Collection).isEmpty()) {
            var19 = false;
         } else {
            val var7: java.util.Iterator = pokemonLearntMessage.iterator();

            while (true) {
               if (!var7.hasNext()) {
                  var19 = false;
                  break;
               }

               if ((var7.next() as Move).getTemplate() == move) {
                  var19 = true;
                  break;
               }
            }
         }

         if (!var19) {
            pokemonLearntMessage = pokemon.getBenchedMoves();
            var var20: Boolean;
            if (pokemonLearntMessage is java.util.Collection && (pokemonLearntMessage as java.util.Collection).isEmpty()) {
               var20 = false;
            } else {
               val var15: java.util.Iterator = pokemonLearntMessage.iterator();

               while (true) {
                  if (!var15.hasNext()) {
                     var20 = false;
                     break;
                  }

                  if ((var15.next() as BenchedMove).getMoveTemplate() == move) {
                     var20 = true;
                     break;
                  }
               }
            }

            if (!var20) {
               if (pokemon.getMoveSet().hasSpace()) {
                  pokemon.getMoveSet().add(move.create());
               } else {
                  pokemon.getBenchedMoves().add(new BenchedMove(move, 0));
               }

               val var14: Array<Any> = new Object[]{pokemon.getSpecies().getTranslatedName(), null, null};
               val var10003: Component = player.m_7755_();
               var14[1] = var10003;
               var14[2] = move.getDisplayName();
               val var12: MutableComponent = LocalizationUtilsKt.commandLang("teach", var14);
               (context.getSource() as CommandSourceStack).m_288197_(TeachCommand::execute$lambda$5, true);
               val var22: ServerPlayer = (context.getSource() as CommandSourceStack).m_230896_();
               if (var22 == null || !var22.equals(player)) {
                  player.m_213846_(var12 as Component);
               }

               return 1;
            }
         }

         val var21: CommandSyntaxException = ALREADY_KNOWS_EXCEPTION.create(pokemon.getDisplayName(), move.getDisplayName());
         throw var21 as java.lang.Throwable;
      }
   }

   @JvmStatic
   fun `ALREADY_KNOWS_EXCEPTION$lambda$0`(a: Any, b: Any): Message {
      val var2: Array<Any> = new Object[2];
      var2[0] = a;
      var2[1] = b;
      val var10000: MutableComponent = LocalizationUtilsKt.commandLang("teach.already_knows", var2);
      return TextKt.red(var10000) as Message;
   }

   @JvmStatic
   fun `CANT_LEARN_EXCEPTION$lambda$1`(a: Any, b: Any): Message {
      val var2: Array<Any> = new Object[2];
      var2[0] = a;
      var2[1] = b;
      val var10000: MutableComponent = LocalizationUtilsKt.commandLang("teach.cant_learn", var2);
      return TextKt.red(var10000) as Message;
   }

   @JvmStatic
   fun `register$lambda$2`(it: CommandContext): Int {
      val var10000: TeachCommand = INSTANCE;
      val var10002: ServerPlayer = CommandContextExtensionsKt.player$default(it, null, 1, null);
      return var10000.execute(it, var10002);
   }

   @JvmStatic
   fun `execute$lambda$5`(`$pokemonLearntMessage`: MutableComponent): Component {
      return `$pokemonLearntMessage` as Component;
   }
}
