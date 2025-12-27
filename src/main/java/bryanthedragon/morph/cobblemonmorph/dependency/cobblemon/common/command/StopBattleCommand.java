package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
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
import net.minecraft.world.entity.Entity

public object StopBattleCommand {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("stopbattle");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getSTOP_BATTLE(), false, 2, null) as LiteralArgumentBuilder)
            .then(Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType).executes(this::execute)) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>): Int {
      val entity: Entity = (context.getSource() as CommandSourceStack).m_81373_();
      var var10000: ServerPlayer = CommandContextExtensionsKt.player(context, "player");
      if (var10000 == null) {
         if (entity !is ServerPlayer) {
            return 0;
         }

         var10000 = entity as ServerPlayer;
      }

      if (!var10000.m_9236_().f_46443_) {
         val var5: PokemonBattle = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(var10000);
         if (var5 == null) {
            return 0;
         }

         var5.stop();
      }

      return 1;
   }
}
