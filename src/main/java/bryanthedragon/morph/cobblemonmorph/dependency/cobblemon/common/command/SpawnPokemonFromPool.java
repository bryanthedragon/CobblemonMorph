package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonWorldSpawnerManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.EntitySpawnResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.PlayerSpawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningArea
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import java.util.UUID
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity

public object SpawnPokemonFromPool {
   public const val ALIAS: String = "forcespawn"
   public const val NAME: String = "spawnpokemonfrompool"
   private final val UNABLE_TO_SPAWN: MutableComponent = LocalizationUtilsKt.commandLang("spawnpokemonfrompool.unable_to_spawn")

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("spawnpokemonfrompool");
      val spawnPokemonFromPoolCommand: LiteralCommandNode = dispatcher.register(
         ((PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getSPAWN_POKEMON(), false, 2, null) as LiteralArgumentBuilder)
               .then(Commands.m_82129_("amount", IntegerArgumentType.integer(1) as ArgumentType).executes(SpawnPokemonFromPool::register$lambda$0)) as LiteralArgumentBuilder)
            .executes(SpawnPokemonFromPool::register$lambda$1) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(spawnPokemonFromPoolCommand, "forcespawn"));
   }

   private fun execute(context: CommandContext<CommandSourceStack>, amount: Int): Int {
      val player: ServerPlayer = (context.getSource() as CommandSourceStack).m_81375_();
      val var10000: java.util.Map = CobblemonWorldSpawnerManager.INSTANCE.getSpawnersForPlayers();
      val var10001: UUID = player.m_20148_();
      val spawner: PlayerSpawner = MapsKt.getValue(var10000, var10001) as PlayerSpawner;
      var spawnsTriggered: Int = 0;
      var i: Int = 1;
      if (1 <= amount) {
         while (true) {
            val var13: SpawningArea = spawner.getArea(new SpawnCause(spawner, spawner.chooseBucket(), spawner.getCauseEntity() as Entity));
            if (var13 != null) {
               val contexts: java.util.List = spawner.getResolver()
                  .resolve(spawner, spawner.getContextCalculators(), spawner.getProspector().prospect(spawner, var13));
               if (contexts.isEmpty()) {
                  val var14: MutableComponent = UNABLE_TO_SPAWN;
                  player.m_213846_(TextKt.red(var14) as Component);
               } else {
                  val result: Pair = spawner.getSpawningSelector().select(spawner, contexts);
                  if (result == null) {
                     val var15: MutableComponent = UNABLE_TO_SPAWN;
                     player.m_213846_(TextKt.red(var15) as Component);
                  } else {
                     (result.getSecond() as SpawnDetail)
                        .doSpawn(result.getFirst() as SpawningContext)
                        .getFuture()
                        .thenApply(SpawnPokemonFromPool::execute$lambda$2);
                     spawnsTriggered++;
                  }
               }
            }

            if (i == amount) {
               break;
            }

            i++;
         }
      }

      return spawnsTriggered;
   }

   @JvmStatic
   fun `register$lambda$0`(context: CommandContext): Int {
      val var10000: SpawnPokemonFromPool = INSTANCE;
      return var10000.execute(context, IntegerArgumentType.getInteger(context, "amount"));
   }

   @JvmStatic
   fun `register$lambda$1`(context: CommandContext): Int {
      val var10000: SpawnPokemonFromPool = INSTANCE;
      return var10000.execute(context, 1);
   }

   @JvmStatic
   fun `execute$lambda$2`(`$player`: ServerPlayer, it: Any): Unit {
      if (it is EntitySpawnResult) {
         for (Entity entity : ((EntitySpawnResult)it).getEntities()) {
            val var4: Array<Any> = new Object[1];
            val var10004: Component = entity.m_5446_();
            var4[0] = var10004;
            val var10001: MutableComponent = LocalizationUtilsKt.commandLang("spawnpokemonfrompool.success", var4);
            `$player`.m_213846_(TextKt.green(var10001) as Component);
         }
      }

      return Unit.INSTANCE;
   }
}
