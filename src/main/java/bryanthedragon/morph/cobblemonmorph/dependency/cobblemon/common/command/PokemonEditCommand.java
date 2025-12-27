package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PartySlotArgumentType
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

public object PokemonEditCommand {
   private const val ALIAS: String = "pokeedit"
   private const val ALIAS_OTHER: String = "pokeeditother"
   private const val NAME: String = "pokemonedit"
   private const val NAME_OTHER: String = "pokemoneditother"
   private const val PLAYER: String = "player"
   private const val PROPERTIES: String = "properties"
   private const val SLOT: String = "slot"

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      var var10001: LiteralArgumentBuilder = Commands.m_82127_("pokemonedit");
      val selfCommand: LiteralCommandNode = dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getPOKEMON_EDIT_SELF(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               Commands.m_82129_("slot", PartySlotArgumentType.Companion.partySlot())
                  .then(Commands.m_82129_("properties", PokemonPropertiesArgumentType.Companion.properties()).executes(PokemonEditCommand::register$lambda$0))
            ) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(selfCommand, "pokeedit"));
      var10001 = Commands.m_82127_("pokemoneditother");
      val otherCommand: LiteralCommandNode = dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getPOKEMON_EDIT_OTHER(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType)
                  .then(
                     Commands.m_82129_("slot", PartySlotArgumentType.Companion.partySlot())
                        .then(
                           Commands.m_82129_("properties", PokemonPropertiesArgumentType.Companion.properties())
                              .executes(PokemonEditCommand::register$lambda$1)
                        )
                  )
            ) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(otherCommand, "pokeeditother"));
   }

   private fun execute(context: CommandContext<CommandSourceStack>, player: ServerPlayer): Int {
      val pokemon: Pokemon = PartySlotArgumentType.Companion.getPokemonOf(context, "slot", player);
      val oldName: MutableComponent = pokemon.getSpecies().getTranslatedName();
      PokemonPropertiesArgumentType.Companion.getPokemonProperties(context, "properties").apply(pokemon);
      (context.getSource() as CommandSourceStack).m_288197_(PokemonEditCommand::execute$lambda$2, true);
      return 1;
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandContext): Int {
      val var10000: PokemonEditCommand = INSTANCE;
      val var10002: ServerPlayer = (it.getSource() as CommandSourceStack).m_81375_();
      return var10000.execute(it, var10002);
   }

   @JvmStatic
   fun `register$lambda$1`(it: CommandContext): Int {
      val var10000: PokemonEditCommand = INSTANCE;
      val var10002: ServerPlayer = CommandContextExtensionsKt.player$default(it, null, 1, null);
      return var10000.execute(it, var10002);
   }

   @JvmStatic
   fun `execute$lambda$2`(`$oldName`: MutableComponent, `$player`: ServerPlayer): Component {
      val var2: Array<Any> = new Object[]{`$oldName`, null};
      val var10003: Component = `$player`.m_7755_();
      var2[1] = var10003;
      return LocalizationUtilsKt.commandLang("pokemonedit", var2) as Component;
   }
}
