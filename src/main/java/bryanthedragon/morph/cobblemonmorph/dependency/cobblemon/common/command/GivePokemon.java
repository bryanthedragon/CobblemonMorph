package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonPropertiesArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

public object GivePokemon {
   private const val ALIAS: String = "pokegive"
   private const val ALIAS_OTHER: String = "pokegiveother"
   private const val NAME: String = "givepokemon"
   private const val NAME_OTHER: String = "givepokemonother"
   private const val PLAYER: String = "player"
   private const val PROPERTIES: String = "properties"

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      var var10001: LiteralArgumentBuilder = Commands.m_82127_("givepokemon");
      val selfCommand: LiteralCommandNode = dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getGIVE_POKEMON_SELF(), false, 2, null) as LiteralArgumentBuilder)
            .then(Commands.m_82129_("properties", PokemonPropertiesArgumentType.Companion.properties()).executes(GivePokemon::register$lambda$0)) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(selfCommand, "pokegive"));
      var10001 = Commands.m_82127_("givepokemonother");
      val otherCommand: LiteralCommandNode = dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getGIVE_POKEMON_OTHER(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType)
                  .then(Commands.m_82129_("properties", PokemonPropertiesArgumentType.Companion.properties()).executes(GivePokemon::register$lambda$1))
            ) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(otherCommand, "pokegiveother"));
   }

   private fun execute(context: CommandContext<CommandSourceStack>, player: ServerPlayer): Int {
      try {
         val e: PokemonProperties = PokemonPropertiesArgumentType.Companion.getPokemonProperties(context, "properties");
         if (e.getSpecies() == null) {
            val var10001: MutableComponent = LocalizationUtilsKt.commandLang("givepokemon.nospecies");
            player.m_213846_(TextKt.red(var10001) as Component);
            return 1;
         }

         val pokemon: Pokemon = e.create();
         Cobblemon.INSTANCE.getStorage().getParty(player).add(pokemon);
         (context.getSource() as CommandSourceStack).m_288197_(GivePokemon::execute$lambda$2, true);
      } catch (var6: Exception) {
         var6.printStackTrace();
      }

      return 1;
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandContext): Int {
      val var10000: GivePokemon = INSTANCE;
      val var10002: ServerPlayer = (it.getSource() as CommandSourceStack).m_81375_();
      return var10000.execute(it, var10002);
   }

   @JvmStatic
   fun `register$lambda$1`(it: CommandContext): Int {
      val var10000: GivePokemon = INSTANCE;
      val var10002: ServerPlayer = CommandContextExtensionsKt.player$default(it, null, 1, null);
      return var10000.execute(it, var10002);
   }

   @JvmStatic
   fun `execute$lambda$2`(`$pokemon`: Pokemon, `$player`: ServerPlayer): Component {
      val var2: Array<Any> = new Object[]{`$pokemon`.getSpecies().getTranslatedName(), null};
      val var10003: Component = `$player`.m_7755_();
      var2[1] = var10003;
      return LocalizationUtilsKt.commandLang("givepokemon.give", var2) as Component;
   }
}
