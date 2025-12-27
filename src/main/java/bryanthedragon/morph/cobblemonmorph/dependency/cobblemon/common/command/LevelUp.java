package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.CommandExperienceSource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer

public object LevelUp {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      var var10000: LiteralArgumentBuilder = Commands.m_82127_("levelup");
      var10000 = PermissionUtilsKt.permission$default(var10000 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getLEVEL_UP_SELF(), false, 2, null) as LiteralArgumentBuilder;
      val var10001: RequiredArgumentBuilder = Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType);
      dispatcher.register(
         (var10000.then(
               (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getLEVEL_UP_OTHER(), false, 2, null) as RequiredArgumentBuilder)
                  .then(Commands.m_82129_("slot", IntegerArgumentType.integer(1, 99) as ArgumentType).executes(LevelUp::register$lambda$0))
            ) as LiteralArgumentBuilder)
            .then(
               (Commands.m_82129_("slot", IntegerArgumentType.integer(1, 99) as ArgumentType).requires(LevelUp::register$lambda$1) as RequiredArgumentBuilder)
                  .executes(LevelUp::register$lambda$2)
            ) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>, player: ServerPlayer): Int {
      val slot: Int = IntegerArgumentType.getInteger(context, "slot");
      val party: PlayerPartyStore = PlayerExtensionsKt.party(player);
      if (slot > party.size()) {
         (context.getSource() as CommandSourceStack).m_81352_(TextKt.text("Your party only has ${party.size()} slots.") as Component);
         return 0;
      } else {
         val pokemon: Pokemon = party.get(slot - 1);
         if (pokemon == null) {
            (context.getSource() as CommandSourceStack).m_81352_(TextKt.text("There is no Pokémon in slot $slot") as Component);
            return 0;
         } else {
            val var10002: Any = context.getSource();
            pokemon.addExperienceWithPlayer(player, new CommandExperienceSource(var10002 as SharedSuggestionProvider), pokemon.getExperienceToNextLevel());
            return 1;
         }
      }
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandContext): Int {
      val var10000: LevelUp = INSTANCE;
      val var10002: ServerPlayer = CommandContextExtensionsKt.player$default(it, null, 1, null);
      return var10000.execute(it, var10002);
   }

   @JvmStatic
   fun `register$lambda$1`(it: CommandSourceStack): Boolean {
      return it.m_81373_() is ServerPlayer && it.m_230896_() != null;
   }

   @JvmStatic
   fun `register$lambda$2`(it: CommandContext): Int {
      val var10000: LevelUp = INSTANCE;
      val var10002: ServerPlayer = (it.getSource() as CommandSourceStack).m_81375_();
      return var10000.execute(it, var10002);
   }
}
