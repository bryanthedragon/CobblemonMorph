package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonPropertiesArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nTestPartySlotCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TestPartySlotCommand.kt\ncom/cobblemon/mod/common/command/TestPartySlotCommand\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,51:1\n1#2:52\n*E\n"])
public object TestPartySlotCommand {
   private const val NAME: String = "testpartyslot"
   private const val NO_SUCCESS: Int = 0
   private const val PLAYER: String = "player"
   private const val PROPERTIES: String = "properties"
   private const val SLOT: String = "slot"

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("testpartyslot");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getTEST_PARTY_SLOT(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType)
                  .then(
                     Commands.m_82129_("slot", IntegerArgumentType.integer(1, 6) as ArgumentType)
                        .then(Commands.m_82129_("properties", PokemonPropertiesArgumentType.Companion.properties()).executes(this::execute))
                  )
            ) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>): Int {
      val player: ServerPlayer = CommandContextExtensionsKt.player(context, "player");
      val slot: Int = IntegerArgumentType.getInteger(context, "slot");
      val properties: PokemonProperties = PokemonPropertiesArgumentType.Companion.getPokemonProperties(context, "properties");
      val var5: Pokemon = PlayerExtensionsKt.party(player).get(slot - 1);
      if (var5 != null) {
         val var6: PokemonProperties = var5.createPokemonProperties(PokemonPropertyExtractor.ALL);
         if (var6 != null) {
            return if (properties.isSubSetOf(var6)) 1 else 0;
         }
      }

      return if (false) 1 else 0;
   }
}
