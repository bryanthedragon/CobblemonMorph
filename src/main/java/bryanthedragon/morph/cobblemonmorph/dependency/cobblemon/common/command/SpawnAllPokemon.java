package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

public object SpawnAllPokemon {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("spawnallpokemon");
      dispatcher.register(
         ((PermissionUtilsKt.requiresWithPermission(
                  var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getSPAWN_ALL_POKEMON(), <unrepresentable>.INSTANCE
               ) as LiteralArgumentBuilder)
               .then(
                  (Commands.m_82129_("min", IntegerArgumentType.integer(1) as ArgumentType)
                        .then(Commands.m_82129_("max", IntegerArgumentType.integer(1) as ArgumentType).executes(SpawnAllPokemon::register$lambda$0)) as RequiredArgumentBuilder)
                     .executes(SpawnAllPokemon::register$lambda$1)
               ) as LiteralArgumentBuilder)
            .executes(SpawnAllPokemon::register$lambda$2) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>, range: IntRange): Int {
      val player: ServerPlayer = (context.getSource() as CommandSourceStack).m_81375_();

      for (Species species : PokemonSpecies.INSTANCE.getImplemented()) {
         val var6: Int = range.getFirst();
         val var7: Int = range.getLast();
         val var8: Int = species.getNationalPokedexNumber();
         if (var6 <= var8 && var8 <= var7) {
            Cobblemon.INSTANCE.getLOGGER().debug(species.getName());
            val var10000: Pokemon = Species.create$default(species, 0, 1, null);
            val var10001: Level = player.m_9236_();
            val var9: ServerLevel = var10001 as ServerLevel;
            val var10002: Vec3 = player.m_20182_();
            Pokemon.sendOut$default(var10000, var9, var10002, null, null, 8, null);
         }
      }

      return 1;
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandContext): Int {
      val var10000: SpawnAllPokemon = INSTANCE;
      return var10000.execute(it, new IntRange(IntegerArgumentType.getInteger(it, "min"), IntegerArgumentType.getInteger(it, "max")));
   }

   @JvmStatic
   fun `register$lambda$1`(it: CommandContext): Int {
      val var10000: SpawnAllPokemon = INSTANCE;
      return var10000.execute(it, new IntRange(IntegerArgumentType.getInteger(it, "min"), Integer.MAX_VALUE));
   }

   @JvmStatic
   fun `register$lambda$2`(it: CommandContext): Int {
      val var10000: SpawnAllPokemon = INSTANCE;
      return var10000.execute(it, new IntRange(1, Integer.MAX_VALUE));
   }
}
