package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import java.util.ArrayList;
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.nbt.SnbtPrinterTagVisitor
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.item.ItemStack

public object GetNBT {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("getnbt");
      dispatcher.register(
         (PermissionUtilsKt.requiresWithPermission(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getGET_NBT(), <unrepresentable>.INSTANCE) as LiteralArgumentBuilder)
            .executes(GetNBT::register$lambda$0) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>, player: ServerPlayer): Int {
      val stack: ItemStack = player.m_21120_(InteractionHand.MAIN_HAND);

      try {
         val str: java.lang.String = new SnbtPrinterTagVisitor("", 0, new ArrayList()).m_178141_(stack.m_41783_() as Tag);
         player.m_213846_(TextKt.suggest(TextKt.text(str), str) as Component);
      } catch (var6: Exception) {
         var6.printStackTrace();
      }

      return 1;
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandContext): Int {
      val var10000: GetNBT = INSTANCE;
      val var10002: ServerPlayer = (it.getSource() as CommandSourceStack).m_81375_();
      return var10000.execute(it, var10002);
   }
}
