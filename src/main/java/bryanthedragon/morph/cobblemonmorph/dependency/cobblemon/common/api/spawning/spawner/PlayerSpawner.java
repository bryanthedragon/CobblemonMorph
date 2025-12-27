package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.UUID
import kotlin.random.Random
import kotlin.random.Random.Default
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.util.Mth
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

public class PlayerSpawner(player: ServerPlayer, spawns: SpawnPool, manager: SpawnerManager) : AreaSpawner {
   public open var ticksBetweenSpawns: Float
   public final val uuid: UUID

   init {
      val var10001: java.lang.String = player.m_7755_().getString();
      super(var10001, spawns, manager);
      val var4: UUID = player.m_20148_();
      this.uuid = var4;
      this.ticksBetweenSpawns = Cobblemon.INSTANCE.getConfig().getTicksBetweenSpawnAttempts();
   }

   public open fun getCauseEntity(): ServerPlayer? {
      return PlayerExtensionsKt.getPlayer(this.uuid);
   }

   public override fun getArea(cause: SpawnCause): SpawningArea? {
      val var10000: ServerPlayer = PlayerExtensionsKt.getPlayer(this.uuid);
      if (var10000 == null) {
         return null;
      } else {
         val sliceDiameter: Int = Cobblemon.INSTANCE.getConfig().getWorldSliceDiameter();
         val sliceHeight: Int = Cobblemon.INSTANCE.getConfig().getWorldSliceHeight();
         val rand: Default = Random.Default;
         val center: Vec3 = var10000.m_20182_();
         val r: Float = MiscUtilsKt.nextBetween(
            rand as Random,
            Cobblemon.INSTANCE.getConfig().getMinimumSliceDistanceFromPlayer(),
            Cobblemon.INSTANCE.getConfig().getMaximumSliceDistanceFromPlayer()
         );
         val thetatemp: Double = Math.atan(var10000.m_20184_().f_82481_ / var10000.m_20184_().f_82479_)
            + MiscUtilsKt.nextBetween(rand as Random, (float) (-Math.PI / 2), (float) (Math.PI / 2));
         val theta: Double = if (var10000.m_20184_().m_165924_() < 0.1)
            rand.nextDouble() * 2 * (float) Math.PI
            else
            (if (var10000.m_20184_().f_82479_ < 0.0) (float) Math.PI - thetatemp else thetatemp);
         val x: Double = center.f_82479_ + r * Math.cos(theta);
         val z: Double = center.f_82481_ + r * Math.sin(theta);
         val var10003: Level = var10000.m_9236_();
         return new SpawningArea(
            cause,
            var10003 as ServerLevel,
            Mth.m_14165_(x - (double)((float)sliceDiameter / 2.0F)),
            Mth.m_14165_(center.f_82480_ - (double)((float)sliceHeight / 2.0F)),
            Mth.m_14165_(z - (double)((float)sliceDiameter / 2.0F)),
            sliceDiameter,
            sliceHeight,
            sliceDiameter
         );
      }
   }
}
