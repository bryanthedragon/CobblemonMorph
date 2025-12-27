package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.PlayerSpawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.PlayerSpawnerFactory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.gamerules.CobblemonGameRules
import java.util.LinkedHashMap
import java.util.UUID
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.GameRules

public object CobblemonWorldSpawnerManager : SpawnerManager {
   public final var spawnersForPlayers: MutableMap<UUID, PlayerSpawner> = (new LinkedHashMap()) as java.util.Map

   public fun onPlayerLogin(player: ServerPlayer) {
      if (Cobblemon.INSTANCE.getConfig().getEnableSpawning()) {
         var var5: Boolean;
         label19: {
            val var10000: MinecraftServer = DistributionUtilsKt.server();
            if (var10000 != null) {
               val var4: GameRules = var10000.m_129900_();
               if (var4 != null) {
                  var5 = !var4.m_46207_(CobblemonGameRules.DO_POKEMON_SPAWNING);
                  break label19;
               }
            }

            var5 = false;
         }

         if (!var5) {
            val spawner: PlayerSpawner = PlayerSpawnerFactory.INSTANCE.create(this, player);
            val var3: java.util.Map = spawnersForPlayers;
            val var6: UUID = player.m_20148_();
            var3.put(var6, spawner);
            this.registerSpawner(spawner);
            return;
         }
      }
   }

   public fun onPlayerLogout(player: ServerPlayer) {
      val spawner: PlayerSpawner = spawnersForPlayers.get(player.m_20148_());
      if (spawner != null) {
         spawnersForPlayers.remove(player.m_20148_());
         this.unregisterSpawner(spawner);
      }
   }

   @JvmStatic
   fun {
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_PLAYER_LOGIN, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_PLAYER_LOGOUT, null, <unrepresentable>.INSTANCE, 1, null);
   }
}
