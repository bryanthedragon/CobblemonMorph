package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nGiveAllPokemon.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GiveAllPokemon.kt\ncom/cobblemon/mod/common/command/GiveAllPokemon\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,53:1\n1045#2:54\n*S KotlinDebug\n*F\n+ 1 GiveAllPokemon.kt\ncom/cobblemon/mod/common/command/GiveAllPokemon\n*L\n45#1:54\n*E\n"])
public object GiveAllPokemon {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("giveallpokemon");
      dispatcher.register(
         ((PermissionUtilsKt.requiresWithPermission(
                  var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getGIVE_ALL_POKEMON(), <unrepresentable>.INSTANCE
               ) as LiteralArgumentBuilder)
               .then(
                  (Commands.m_82129_("min", IntegerArgumentType.integer(1) as ArgumentType)
                        .then(Commands.m_82129_("max", IntegerArgumentType.integer(1) as ArgumentType).executes(GiveAllPokemon::register$lambda$0)) as RequiredArgumentBuilder)
                     .executes(GiveAllPokemon::register$lambda$1)
               ) as LiteralArgumentBuilder)
            .executes(GiveAllPokemon::register$lambda$2) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>, range: IntRange): Int {
      val player: ServerPlayer = (context.getSource() as CommandSourceStack).m_81375_();
      val var10000: PCStore = PlayerExtensionsKt.party(player).getOverflowPC();
      if (var10000 == null) {
         return 0;
      } else {
         val pc: PCStore = var10000;

         for (Species species : CollectionsKt.sortedWith(PokemonSpecies.INSTANCE.getImplemented(), new GiveAllPokemon$execute$$inlined$sortedBy$1())) {
            pc.add(Species.create$default(var9, 0, 1, null));
         }

         return 1;
      }
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandContext): Int {
      val var10000: GiveAllPokemon = INSTANCE;
      return var10000.execute(it, new IntRange(IntegerArgumentType.getInteger(it, "min"), IntegerArgumentType.getInteger(it, "max")));
   }

   @JvmStatic
   fun `register$lambda$1`(it: CommandContext): Int {
      val var10000: GiveAllPokemon = INSTANCE;
      return var10000.execute(it, new IntRange(IntegerArgumentType.getInteger(it, "min"), Integer.MAX_VALUE));
   }

   @JvmStatic
   fun `register$lambda$2`(it: CommandContext): Int {
      val var10000: GiveAllPokemon = INSTANCE;
      return var10000.execute(it, new IntRange(1, Integer.MAX_VALUE));
   }
}
