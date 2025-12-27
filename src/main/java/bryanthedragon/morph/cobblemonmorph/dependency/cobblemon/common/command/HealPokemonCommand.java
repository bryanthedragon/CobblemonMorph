package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.Message
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.tree.LiteralCommandNode
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

public object HealPokemonCommand {
   private final val IN_BATTLE_EXCEPTION: SimpleCommandExceptionType

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      var var10001: LiteralArgumentBuilder = Commands.m_82127_("healpokemon");
      var10001 = (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getHEAL_POKEMON_SELF(), false, 2, null) as LiteralArgumentBuilder)
         .executes(HealPokemonCommand::register$lambda$0) as LiteralArgumentBuilder;
      val var10002: RequiredArgumentBuilder = Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType);
      val command: LiteralCommandNode = dispatcher.register(
         var10001.then(
            (PermissionUtilsKt.permission$default(var10002 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getHEAL_POKEMON_OTHER(), false, 2, null) as RequiredArgumentBuilder)
               .executes(HealPokemonCommand::register$lambda$1)
         ) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(command, "pokeheal"));
   }

   private fun execute(source: CommandSourceStack, target: ServerPlayer): Int {
      if (PlayerExtensionsKt.isInBattle(target)) {
         val var10000: CommandSyntaxException = IN_BATTLE_EXCEPTION.create();
         throw var10000 as java.lang.Throwable;
      } else {
         if (!target.m_9236_().f_46443_) {
            PlayerExtensionsKt.party(target).heal();
            source.m_288197_(HealPokemonCommand::execute$lambda$2, true);
         }

         return 1;
      }
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandContext): Int {
      val var10000: HealPokemonCommand = INSTANCE;
      var var10001: Any = it.getSource();
      var10001 = var10001 as CommandSourceStack;
      val var10002: ServerPlayer = (it.getSource() as CommandSourceStack).m_81375_();
      return var10000.execute((CommandSourceStack)var10001, var10002);
   }

   @JvmStatic
   fun `register$lambda$1`(it: CommandContext): Int {
      val var10000: HealPokemonCommand = INSTANCE;
      var var10001: Any = it.getSource();
      var10001 = var10001 as CommandSourceStack;
      val var10002: ServerPlayer = CommandContextExtensionsKt.player(it, "player");
      return var10000.execute((CommandSourceStack)var10001, var10002);
   }

   @JvmStatic
   fun `execute$lambda$2`(`$target`: ServerPlayer): Component {
      val var1: Array<Any> = new Object[1];
      val var10003: Component = `$target`.m_7755_();
      var1[0] = var10003;
      return LocalizationUtilsKt.commandLang("healpokemon.heal", var1) as Component;
   }

   @JvmStatic
   fun {
      val var10002: MutableComponent = LocalizationUtilsKt.commandLang("pokeheal.in_battle");
      IN_BATTLE_EXCEPTION = new SimpleCommandExceptionType(TextKt.red(var10002) as Message);
   }
}
