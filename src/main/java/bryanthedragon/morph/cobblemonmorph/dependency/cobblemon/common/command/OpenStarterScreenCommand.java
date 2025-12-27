package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerDataStoreManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter.OpenStarterUIPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player

public object OpenStarterScreenCommand {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("openstarterscreen");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getOPEN_STARTER_SCREEN(), false, 2, null) as LiteralArgumentBuilder)
            .then(Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType).executes(OpenStarterScreenCommand::register$lambda$0)) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>): Int {
      val player: ServerPlayer = EntityArgument.m_91474_(context, "player");
      val var10000: PlayerDataStoreManager = Cobblemon.INSTANCE.getPlayerData();
      val playerData: PlayerData = var10000.get(player as Player);
      if (playerData.getStarterSelected()) {
         (context.getSource() as CommandSourceStack).m_288197_(OpenStarterScreenCommand::execute$lambda$1, true);
         return 0;
      } else {
         if (playerData.getStarterLocked()) {
            playerData.setStarterLocked(false);
            playerData.sendToPlayer(player);
         }

         playerData.setStarterPrompted(true);
         Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
         CobblemonNetwork.INSTANCE.sendPacket(player, new OpenStarterUIPacket(Cobblemon.INSTANCE.getStarterHandler().getStarterList(player)));
         return 1;
      }
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandContext): Int {
      val var10000: OpenStarterScreenCommand = INSTANCE;
      return var10000.execute(it);
   }

   @JvmStatic
   fun `execute$lambda$1`(`$player`: ServerPlayer): Component {
      val var1: Array<Any> = new Object[1];
      val var10003: Component = `$player`.m_7755_();
      var1[0] = var10003;
      val var10000: MutableComponent = LocalizationUtilsKt.lang("ui.starter.hasalreadychosen", var1);
      return TextKt.red(var10000) as Component;
   }
}
