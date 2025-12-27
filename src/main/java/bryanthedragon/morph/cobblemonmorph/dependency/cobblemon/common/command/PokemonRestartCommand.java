package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter.SetClientPlayerDataPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.InitializePartyPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.InitializePCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import java.util.UUID
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player

public object PokemonRestartCommand {
   private const val ALIAS: String = "pokerestart"
   private const val ALIAS_OTHER: String = "pokerestartother"
   private const val NAME: String = "pokemonrestart"
   private const val NAME_OTHER: String = "pokemonrestartother"
   private const val PLAYER: String = "player"
   private const val STARTERS: String = "reset_starters"

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      var var10001: LiteralArgumentBuilder = Commands.m_82127_("pokemonrestart");
      val selfCommand: LiteralCommandNode = dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getPOKEMON_EDIT_SELF(), false, 2, null) as LiteralArgumentBuilder)
            .then(Commands.m_82129_("reset_starters", BoolArgumentType.bool() as ArgumentType).executes(PokemonRestartCommand::register$lambda$0)) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(selfCommand, "pokerestart"));
      var10001 = Commands.m_82127_("pokemonrestartother");
      val otherCommand: LiteralCommandNode = dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getPOKEMON_EDIT_OTHER(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType)
                  .then(Commands.m_82129_("reset_starters", BoolArgumentType.bool() as ArgumentType).executes(PokemonRestartCommand::register$lambda$1))
            ) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(otherCommand, "pokerestartother"));
      var10001 = Commands.m_82127_("pokemonrestart");
      val selfCommandWithoutStarters: LiteralCommandNode = dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getPOKEMON_EDIT_SELF(), false, 2, null) as LiteralArgumentBuilder)
            .executes(PokemonRestartCommand::register$lambda$2) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(selfCommandWithoutStarters, "pokerestart"));
      var10001 = Commands.m_82127_("pokemonrestartother");
      val otherCommandWithoutStarters: LiteralCommandNode = dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getPOKEMON_EDIT_OTHER(), false, 2, null) as LiteralArgumentBuilder)
            .then(Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType).executes(PokemonRestartCommand::register$lambda$3)) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(otherCommandWithoutStarters, "pokerestartother"));
   }

   private fun execute(context: CommandContext<CommandSourceStack>, player: ServerPlayer, resetStarters: Boolean): Int {
      this.resetPlayerPokemonData(player, resetStarters);
      (context.getSource() as CommandSourceStack).m_288197_(PokemonRestartCommand::execute$lambda$4, true);
      return 1;
   }

   private fun resetPlayerPokemonData(player: ServerPlayer, resetStarters: Boolean) {
      PlayerExtensionsKt.party(player).clearParty();
      PlayerExtensionsKt.pc(player).clearPC();
      var var10000: CobblemonNetwork = CobblemonNetwork.INSTANCE;
      val var10005: UUID = player.m_20148_();
      var10000.sendPacket(player, new InitializePartyPacket(true, var10005, PlayerExtensionsKt.party(player).size()));
      var10000 = CobblemonNetwork.INSTANCE;
      val var10004: UUID = player.m_20148_();
      var10000.sendPacket(player, new InitializePCPacket(var10004, PlayerExtensionsKt.pc(player).getBoxes().size(), false));
      val playerData: PlayerData = Cobblemon.INSTANCE.getPlayerData().get(player as Player);
      playerData.setStarterPrompted(false);
      playerData.setStarterLocked(false);
      playerData.setStarterSelected(!resetStarters);
      CobblemonNetwork.INSTANCE.sendPacket(player, new SetClientPlayerDataPacket(playerData, resetStarters));
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandContext): Int {
      val var10000: PokemonRestartCommand = INSTANCE;
      val var10002: ServerPlayer = (it.getSource() as CommandSourceStack).m_81375_();
      return var10000.execute(it, var10002, BoolArgumentType.getBool(it, "reset_starters"));
   }

   @JvmStatic
   fun `register$lambda$1`(it: CommandContext): Int {
      val var10000: PokemonRestartCommand = INSTANCE;
      val var10002: ServerPlayer = CommandContextExtensionsKt.player$default(it, null, 1, null);
      return var10000.execute(it, var10002, BoolArgumentType.getBool(it, "reset_starters"));
   }

   @JvmStatic
   fun `register$lambda$2`(it: CommandContext): Int {
      val var10000: PokemonRestartCommand = INSTANCE;
      val var10002: ServerPlayer = (it.getSource() as CommandSourceStack).m_81375_();
      return var10000.execute(it, var10002, false);
   }

   @JvmStatic
   fun `register$lambda$3`(it: CommandContext): Int {
      val var10000: PokemonRestartCommand = INSTANCE;
      val var10002: ServerPlayer = CommandContextExtensionsKt.player$default(it, null, 1, null);
      return var10000.execute(it, var10002, false);
   }

   @JvmStatic
   fun `execute$lambda$4`(`$player`: ServerPlayer): Component {
      val var1: Array<Any> = new Object[1];
      val var10003: Component = `$player`.m_7755_();
      var1[0] = var10003;
      return LocalizationUtilsKt.commandLang("pokemonrestart", var1) as Component;
   }
}
