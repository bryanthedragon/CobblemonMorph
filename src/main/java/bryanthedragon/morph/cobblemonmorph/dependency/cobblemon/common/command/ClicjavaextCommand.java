package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import java.util.UUID
import kotlin.jvm.functions.Function1
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity

public object ClickTextCommand {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      dispatcher.register(
         (Commands.m_82127_("cobblemonclicktext").requires(ClickTextCommand::register$lambda$0) as LiteralArgumentBuilder)
            .then(RequiredArgumentBuilder.argument("callback", StringArgumentType.greedyString() as ArgumentType).executes(ClickTextCommand::register$lambda$1)) as LiteralArgumentBuilder
      );
   }

   @JvmStatic
   fun `register$lambda$0`(src: CommandSourceStack): Boolean {
      return src.m_81373_() is ServerPlayer;
   }

   @JvmStatic
   fun `register$lambda$1`(ctx: CommandContext): Int {
      val var10000: Entity = (ctx.getSource() as CommandSourceStack).m_81373_();
      val player: ServerPlayer = var10000 as ServerPlayer;
      val var2: Function1 = TextKt.getTextClickHandlers().get(UUID.fromString(ctx.getArgument("callback", java.lang.String.class) as java.lang.String));
      if (var2 != null) {
         var2.invoke(player);
      }

      return 1;
   }
}
