package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PartySlotArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer

public object FriendshipCommand {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("friendship");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getFRIENDSHIP(), false, 2, null) as LiteralArgumentBuilder)
            .then(Commands.m_82129_("slot", PartySlotArgumentType.Companion.partySlot()).executes(FriendshipCommand::register$lambda$0)) as LiteralArgumentBuilder
      );
   }

   private fun execute(source: CommandSourceStack, target: ServerPlayer, pokemon: Pokemon): Int {
      source.m_288197_(FriendshipCommand::execute$lambda$1, true);
      return 1;
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandContext): Int {
      val var10000: FriendshipCommand = INSTANCE;
      var var10001: Any = it.getSource();
      var10001 = var10001 as CommandSourceStack;
      val var10002: ServerPlayer = (it.getSource() as CommandSourceStack).m_81375_();
      val var10003: PartySlotArgumentType.Companion = PartySlotArgumentType.Companion;
      return var10000.execute((CommandSourceStack)var10001, var10002, var10003.getPokemon(it, "slot"));
   }

   @JvmStatic
   fun `execute$lambda$1`(`$pokemon`: Pokemon): Component {
      return LocalizationUtilsKt.commandLang("friendship", `$pokemon`.getDisplayName(), `$pokemon`.getFriendship()) as Component;
   }
}
