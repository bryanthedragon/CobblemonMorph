package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings.ServerSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PartySlotArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.RemoveClientPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.Message
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import java.util.UUID
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nPokeboxCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokeboxCommand.kt\ncom/cobblemon/mod/common/command/PokeboxCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,134:1\n1855#2,2:135\n*S KotlinDebug\n*F\n+ 1 PokeboxCommand.kt\ncom/cobblemon/mod/common/command/PokeboxCommand\n*L\n103#1:135,2\n*E\n"])
public object PokeboxCommand {
   private final val BOX_DOES_NOT_EXIST: (Int) -> MutableComponent = <unrepresentable>.INSTANCE as Function1
   private final val BOX_IS_FULL_EXCEPTION: (Int) -> MutableComponent = <unrepresentable>.INSTANCE as Function1
   private final val LAST_POKE_MESSAGE: MutableComponent = LocalizationUtilsKt.commandLang("pokebox.last_pokemon")
   private final val STORAGE_IS_FULL_EXCEPTION: MutableComponent = LocalizationUtilsKt.commandLang("pokebox.storage_is_full")

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      var var10001: LiteralArgumentBuilder = Commands.m_82127_("pokebox");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getPOKEBOX(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType)
                  .then(
                     (Commands.m_82129_("slot", PartySlotArgumentType.Companion.partySlot())
                           .then(Commands.m_82129_("box", IntegerArgumentType.integer(1) as ArgumentType).executes(PokeboxCommand::register$lambda$0)) as RequiredArgumentBuilder)
                        .executes(PokeboxCommand::register$lambda$1)
                  )
            ) as LiteralArgumentBuilder
      );
      var10001 = Commands.m_82127_("pokeboxall");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getPOKEBOX(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               (Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType)
                     .then(Commands.m_82129_("box", IntegerArgumentType.integer(1) as ArgumentType).executes(PokeboxCommand::register$lambda$2)) as RequiredArgumentBuilder)
                  .executes(PokeboxCommand::register$lambda$3)
            ) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>, player: ServerPlayer, pokemons: Collection<Pokemon>, box: Int? = null): Int {
      val playerPc: PCStore = PlayerExtensionsKt.pc(player);
      val playerParty: PlayerPartyStore = PlayerExtensionsKt.party(player);
      if (box != null) {
         if (playerPc.getBoxes().size() < box) {
            val var22: Any = BOX_DOES_NOT_EXIST.invoke(box);
            val var19: CommandSyntaxException = new SimpleCommandExceptionType(TextKt.red(var22 as MutableComponent) as Message).create();
            throw var19 as java.lang.Throwable;
         }

         if (playerPc.getBoxes().get(box - 1).getUnoccupiedSlots() < pokemons.size()) {
            val var21: Any = BOX_IS_FULL_EXCEPTION.invoke(box);
            val var18: CommandSyntaxException = new SimpleCommandExceptionType(TextKt.red(var21 as MutableComponent) as Message).create();
            throw var18 as java.lang.Throwable;
         }
      }

      val var15: java.lang.Iterable;
      for (Object element$iv : var15) {
         val pokemon: Pokemon = `element$iv` as Pokemon;
         if (ServerSettings.INSTANCE.getPreventCompletePartyDeposit() && playerParty.occupied() == 1) {
            (context.getSource() as CommandSourceStack).m_288197_(PokeboxCommand::execute$lambda$5$lambda$4, false);
            return pokemons.size() - 1;
         }

         val var10000: PCPosition;
         if (box == null) {
            var10000 = playerPc.getFirstAvailablePosition();
            if (var10000 == null) {
               val var10002: MutableComponent = STORAGE_IS_FULL_EXCEPTION;
               val var16: CommandSyntaxException = new SimpleCommandExceptionType(TextKt.red(var10002) as Message).create();
               throw var16 as java.lang.Throwable;
            }
         } else {
            var10000 = playerPc.getBoxes().get(box - 1).getFirstAvailablePosition();
            if (var10000 == null) {
               val var20: Any = BOX_IS_FULL_EXCEPTION.invoke(box);
               val var17: CommandSyntaxException = new SimpleCommandExceptionType(TextKt.red(var20 as MutableComponent) as Message).create();
               throw var17 as java.lang.Throwable;
            }
         }

         playerParty.remove(pokemon);
         playerPc.set(var10000, pokemon);
         val var10003: PokemonStore = PlayerExtensionsKt.party(player);
         val var10004: UUID = pokemon.getUuid();
         playerParty.sendPacketToObservers(new RemoveClientPokemonPacket(var10003, var10004));
      }

      return pokemons.size();
   }

   @JvmStatic
   fun `register$lambda$0`(context: CommandContext): Int {
      val player: ServerPlayer = CommandContextExtensionsKt.player$default(context, null, 1, null);
      val var10000: PartySlotArgumentType.Companion = PartySlotArgumentType.Companion;
      return INSTANCE.execute(
         context, player, CollectionsKt.listOf(var10000.getPokemonOf(context, "slot", player)), IntegerArgumentType.getInteger(context, "box")
      );
   }

   @JvmStatic
   fun `register$lambda$1`(context: CommandContext): Int {
      val player: ServerPlayer = CommandContextExtensionsKt.player$default(context, null, 1, null);
      val var10000: PartySlotArgumentType.Companion = PartySlotArgumentType.Companion;
      return execute$default(INSTANCE, context, player, CollectionsKt.listOf(var10000.getPokemonOf(context, "slot", player)), null, 8, null);
   }

   @JvmStatic
   fun `register$lambda$2`(context: CommandContext): Int {
      val player: ServerPlayer = CommandContextExtensionsKt.player$default(context, null, 1, null);
      val box: Int = IntegerArgumentType.getInteger(context, "box");
      val var10000: PokeboxCommand = INSTANCE;
      return var10000.execute(context, player, CollectionsKt.toList(PlayerExtensionsKt.party(player)), box);
   }

   @JvmStatic
   fun `register$lambda$3`(context: CommandContext): Int {
      val player: ServerPlayer = CommandContextExtensionsKt.player$default(context, null, 1, null);
      val var10000: PokeboxCommand = INSTANCE;
      return execute$default(var10000, context, player, CollectionsKt.toList(PlayerExtensionsKt.party(player)), null, 8, null);
   }

   @JvmStatic
   fun `execute$lambda$5$lambda$4`(): Component {
      val var10000: MutableComponent = LAST_POKE_MESSAGE;
      return TextKt.red(var10000) as Component;
   }
}
