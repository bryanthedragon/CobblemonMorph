package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component

public object ReloadShowdownCommand {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      dispatcher.register(
         (Commands.m_82127_("reloadshowdown").requires(ReloadShowdownCommand::register$lambda$0) as LiteralArgumentBuilder).executes(this::execute) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>): Int {
      try {
         ShowdownService.Companion.getService().closeConnection();
         ShowdownService.Companion.getService().openConnection();
         ShowdownService.Companion.getService().registerSpecies();
         ShowdownService.Companion.getService().registerBagItems();
         (context.getSource() as CommandSourceStack).m_243053_(Component.m_130674_("Reloaded showdown"));
      } catch (var3: Exception) {
         var3.printStackTrace();
      }

      return 1;
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandSourceStack): Boolean {
      return it.m_6761_(4);
   }
}
