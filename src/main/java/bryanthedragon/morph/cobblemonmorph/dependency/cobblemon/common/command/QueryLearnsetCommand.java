package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.LearnsetQuery
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.MoveArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PartySlotArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.server.level.ServerPlayer

public object QueryLearnsetCommand {
   private const val MOVE: String = "move"
   private const val NAME: String = "querylearnset"
   private const val NO_SUCCESS: Int = 0
   private const val PLAYER: String = "player"
   private const val SLOT: String = "slot"

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("querylearnset");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getQUERY_LEARNSET(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType)
                  .then(
                     Commands.m_82129_("slot", PartySlotArgumentType.Companion.partySlot())
                        .then(Commands.m_82129_("move", MoveArgumentType.Companion.move()).executes(this::execute))
                  )
            ) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>): Int {
      val player: ServerPlayer = CommandContextExtensionsKt.player(context, "player");
      val var10000: PartySlotArgumentType.Companion = PartySlotArgumentType.Companion;
      return if (LearnsetQuery.Companion
            .getANY()
            .canLearn(MoveArgumentType.Companion.getMove(context, "move"), var10000.getPokemonOf(context, "slot", player).getForm().getMoves()))
         1
         else
         0;
   }
}
