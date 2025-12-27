package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.EntitySpawnResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.FlatContextWeightedSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.SpawningSelector
import java.util.ArrayList;
import kotlin.jvm.functions.Function1
import net.minecraft.world.entity.Entity

public abstract class TickingSpawner : Spawner {
   public final var active: Boolean
   public open val influences: MutableList<SpawningInfluence>
   public final var lastSpawnTime: Long
   public final val manager: SpawnerManager
   public open val name: String
   public final var removalCheckTicks: Int
   private final var selector: SpawningSelector
   public final val spawnedEntities: MutableList<Entity>
   public final var spawns: SpawnPool
   public final var tickTimerMultiplier: Float
   public abstract var ticksBetweenSpawns: Float
   public final var ticksUntilNextSpawn: Float

   open fun TickingSpawner(name: java.lang.String, spawns: SpawnPool, manager: SpawnerManager) {
      this.name = name;
      this.spawns = spawns;
      this.manager = manager;
      this.selector = new FlatContextWeightedSelector();
      this.influences = new ArrayList<>();
      this.active = true;
      this.spawnedEntities = new ArrayList<>();
      this.ticksUntilNextSpawn = 100.0F;
      this.tickTimerMultiplier = 1.0F;
   }

   public override fun canSpawn(): Boolean {
      return this.active;
   }

   public override fun getSpawningSelector(): SpawningSelector {
      return this.selector;
   }

   public override fun setSpawningSelector(selector: SpawningSelector) {
      this.selector = selector;
   }

   public override fun getSpawnPool(): SpawnPool {
      return this.spawns;
   }

   public override fun setSpawnPool(spawnPool: SpawnPool) {
      this.spawns = spawnPool;
   }

   public abstract fun run(cause: SpawnCause): Pair<SpawningContext, SpawnDetail>? {
   }

   public open fun tick() {
      val spawn: Int = this.removalCheckTicks++;
      this.getInfluences().removeIf(TickingSpawner::tick$lambda$0);
      if (this.removalCheckTicks == 60) {
         this.spawnedEntities.removeIf(TickingSpawner::tick$lambda$1);
         this.removalCheckTicks = 0;
      }

      if (this.active) {
         this.ticksUntilNextSpawn = this.ticksUntilNextSpawn - this.tickTimerMultiplier;
         if (this.ticksUntilNextSpawn <= 0.0F) {
            val var5: Pair = this.run(new SpawnCause(this, this.chooseBucket(), this.getCauseEntity()));
            this.ticksUntilNextSpawn = this.getTicksBetweenSpawns();
            if (var5 != null) {
               (var5.getSecond() as SpawnDetail).doSpawn(var5.getFirst() as SpawningContext).complete();
            }
         }
      }
   }

   public override fun <R> afterSpawn(action: SpawnAction<Any>, result: Any) {
      Spawner.DefaultImpls.afterSpawn(this, action, result);
      if (result is EntitySpawnResult) {
         this.spawnedEntities.addAll((result as EntitySpawnResult).getEntities());
      }

      this.lastSpawnTime = System.currentTimeMillis();
   }

   public open fun getCauseEntity(): Entity? {
      return null;
   }

   public fun getAllInfluences(): List<SpawningInfluence> {
      return CollectionsKt.plus(this.getInfluences(), this.manager.getInfluences());
   }

   public override fun copyInfluences(): MutableList<SpawningInfluence> {
      return CollectionsKt.toMutableList(this.getAllInfluences());
   }

   override fun getMatchingSpawns(ctx: SpawningContext): MutableList<SpawnDetail> {
      return Spawner.DefaultImpls.getMatchingSpawns(this, ctx);
   }

   override fun chooseBucket(): SpawnBucket {
      return Spawner.DefaultImpls.chooseBucket(this);
   }

   @JvmStatic
   fun `tick$lambda$0`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }

   @JvmStatic
   fun `tick$lambda$1`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}
