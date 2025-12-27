package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer

public object ClearPartyCommand {
   private const val NAME: String = "clearparty"
   private const val PLAYER: String = "player"

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10000: LiteralArgumentBuilder = Commands.m_82127_("clearparty");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10000 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getCLEAR_PARTY(), false, 2, null) as LiteralArgumentBuilder)
            .then(Commands.m_82129_("player", EntityArgument.m_91470_() as ArgumentType).executes(this::execute)) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>): Int {
      val target: ServerPlayer = EntityArgument.m_91474_(context, "player");
      val party: PlayerPartyStore = PlayerExtensionsKt.party(target);
      val pokemonList: java.util.List = CollectionsKt.filterNotNull(party);
      if (pokemonList.isEmpty()) {
         val var10000: CommandSourceStack = context.getSource() as CommandSourceStack;
         val var7: Array<Any> = new Object[1];
         val var10004: Component = target.m_5446_();
         var7[0] = var10004;
         var10000.m_81352_(LocalizationUtilsKt.commandLang("clearparty.nonethere", var7) as Component);
         return 0;
      } else {
         for (Pokemon pokemon : pokemonList) {
            party.remove(pokemon);
         }

         (context.getSource() as CommandSourceStack).m_288197_(ClearPartyCommand::execute$lambda$0, true);
         return 1;
      }
   }

   @JvmStatic
   fun `execute$lambda$0`(`$target`: ServerPlayer): Component {
      val var1: Array<Any> = new Object[1];
      val var10003: Component = `$target`.m_5446_();
      var1[0] = var10003;
      return LocalizationUtilsKt.commandLang("clearparty.cleared", var1) as Component;
   }
}
