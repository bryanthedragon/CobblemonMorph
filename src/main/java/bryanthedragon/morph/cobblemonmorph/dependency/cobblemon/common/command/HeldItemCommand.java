package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PartySlotArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.commands.arguments.item.ItemArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

public object HeldItemCommand {
   private const val ITEM: String = "item"
   private const val NAME: String = "held_item"
   private const val SLOT: String = "slot"
   private const val TARGET: String = "target"

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>, commandRegistryAccess: CommandBuildContext) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("held_item");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getHELD_ITEM(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               Commands.m_82129_("target", EntityArgument.m_91466_() as ArgumentType)
                  .then(
                     Commands.m_82129_("slot", PartySlotArgumentType.Companion.partySlot())
                        .then(Commands.m_82129_("item", ItemArgument.m_235279_(commandRegistryAccess) as ArgumentType).executes(this::execute))
                  )
            ) as LiteralArgumentBuilder
      );
   }

   private fun execute(ctx: CommandContext<CommandSourceStack>): Int {
      val player: ServerPlayer = EntityArgument.m_91474_(ctx, "target");
      val var10000: PartySlotArgumentType.Companion = PartySlotArgumentType.Companion;
      val pokemon: Pokemon = var10000.getPokemonOf(ctx, "slot", player);
      val stack: ItemStack = ItemArgument.m_120963_(ctx, "item").m_120980_(1, false);
      Pokemon.swapHeldItem$default(pokemon, stack, false, 2, null);
      (ctx.getSource() as CommandSourceStack).m_288197_(HeldItemCommand::execute$lambda$0, true);
      return 1;
   }

   @JvmStatic
   fun `execute$lambda$0`(`$player`: ServerPlayer, `$pokemon`: Pokemon, `$stack`: ItemStack): Component {
      val var3: Array<Any> = new Object[3];
      var var10003: Component = `$player`.m_7755_();
      var3[0] = var10003;
      var3[1] = `$pokemon`.getSpecies().getTranslatedName();
      var10003 = `$stack`.m_41786_();
      var3[2] = var10003;
      return LocalizationUtilsKt.commandLang("held_item", var3) as Component;
   }
}
