package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.server.level.ServerPlayer

public fun CommandContext<CommandSourceStack>.player(argumentName: String = "player"): ServerPlayer {
   return EntityArgument.m_91474_(`$this$player`, argumentName);
}

@JvmSynthetic
fun `player$default`(var0: CommandContext, var1: java.lang.String, var2: Int, var3: Any): ServerPlayer {
   if ((var2 and 1) != 0) {
      var1 = "player";
   }

   return player(var0, var1);
}
