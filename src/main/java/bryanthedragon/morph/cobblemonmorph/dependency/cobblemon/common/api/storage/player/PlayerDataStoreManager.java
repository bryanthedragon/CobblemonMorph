package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ServerTaskTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.factory.JsonPlayerDataStoreFactory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.factory.PlayerDataStoreFactory
import java.util.UUID
import kotlin.jvm.functions.Function1
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import org.jetbrains.annotations.NotNull

public class PlayerDataStoreManager {
   private final var factory: PlayerDataStoreFactory = (new JsonPlayerDataStoreFactory()) as PlayerDataStoreFactory

   public fun setFactory(factory: PlayerDataStoreFactory) {
      this.factory = factory;
   }

   private fun registerSaveScheduler(): ScheduledTask {
      return new ScheduledTask.Builder().execute((new Function1<ScheduledTask, Unit>(this) {
         {
            super(1);
            this.this$0 = `$receiver`;
         }

         public final void invoke(@NotNull ScheduledTask it) {
            this.this$0.saveAll();
         }
      }) as (ScheduledTask?) -> Unit).delay(30.0F).interval(120.0F).infiniteIterations().tracker(ServerTaskTracker.INSTANCE).build();
   }

   public fun setup(server: MinecraftServer) {
      this.registerSaveScheduler();
      val var2: PlayerDataStoreFactory = this.factory;
      val var10000: JsonPlayerDataStoreFactory = this.factory as? JsonPlayerDataStoreFactory;
      if ((this.factory as? JsonPlayerDataStoreFactory) != null) {
         var10000.setup(server);
      }
   }

   public fun get(player: Player): PlayerData {
      val var10000: PlayerDataStoreFactory = this.factory;
      val var10001: UUID = player.m_20148_();
      return var10000.load(var10001);
   }

   public fun saveAll() {
      this.factory.saveAll();
   }

   public fun saveSingle(playerData: PlayerData) {
      this.factory.save(playerData);
   }

   public fun onPlayerDisconnect(player: ServerPlayer) {
      val var10000: PlayerDataStoreFactory = this.factory;
      val var10001: UUID = player.m_20148_();
      var10000.onPlayerDisconnect(var10001);
   }
}
