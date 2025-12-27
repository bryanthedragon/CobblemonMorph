package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer

public object TakePokemon {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10000: LiteralArgumentBuilder = Commands.m_82127_("takepokemon");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10000 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getTAKE_POKEMON(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType)
                  .then(Commands.m_82129_("slot", IntegerArgumentType.integer(1, 99) as ArgumentType).executes(this::execute))
            ) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>): Int {
      try {
         val e: ServerPlayer = EntityArgument.m_91474_(context, "player");
         val slot: Int = IntegerArgumentType.getInteger(context, "slot");
         val party: PlayerPartyStore = PlayerExtensionsKt.party(e);
         if (slot > party.size()) {
            (context.getSource() as CommandSourceStack).m_81352_(TextKt.text("Your party only has ${party.size()} slots.") as Component);
            return 0;
         } else {
            val pokemon: Pokemon = party.get(slot - 1);
            if (pokemon == null) {
               (context.getSource() as CommandSourceStack).m_81352_(TextKt.text("There is no Pokémon in slot $slot") as Component);
               return 0;
            } else {
               party.remove(pokemon);
               if (!((context.getSource() as CommandSourceStack).m_81373_() == e) && (context.getSource() as CommandSourceStack).m_81373_() is ServerPlayer) {
                  val var10000: ServerPlayer = (context.getSource() as CommandSourceStack).m_230896_();
                  if (var10000 == null) {
                     return 1;
                  } else {
                     PlayerExtensionsKt.party(var10000).add(pokemon);
                     (context.getSource() as CommandSourceStack).m_288197_(TakePokemon::execute$lambda$0, true);
                     return 1;
                  }
               } else {
                  (context.getSource() as CommandSourceStack).m_288197_(TakePokemon::execute$lambda$1, true);
                  return 1;
               }
            }
         }
      } catch (var8: Exception) {
         var8.printStackTrace();
         return 1;
      }
   }

   @JvmStatic
   fun `execute$lambda$0`(`$pokemon`: Pokemon): Component {
      return TextKt.text("You took ${`$pokemon`.getSpecies().getName()}") as Component;
   }

   @JvmStatic
   fun `execute$lambda$1`(`$pokemon`: Pokemon): Component {
      return TextKt.text("${`$pokemon`.getSpecies().getName()} was removed.") as Component;
   }
}
