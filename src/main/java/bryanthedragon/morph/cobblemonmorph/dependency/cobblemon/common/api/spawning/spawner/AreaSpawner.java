package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaContextResolver
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningContextCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.prospecting.SpawningProspector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt
import kotlin.jvm.functions.Function1
import net.minecraft.core.BlockPos
import net.minecraft.core.SectionPos
import net.minecraft.core.BlockPos.MutableBlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.math.BlockPos.Mutable
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.chunk.ChunkAccess
import net.minecraft.world.level.chunk.ChunkStatus
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

public abstract class AreaSpawner : TickingSpawner {
   public final var contextCalculators: List<AreaSpawningContextCalculator<*>> = SpawningContextCalculator.Companion.getPrioritizedAreaCalculators()
   public final var prospector: SpawningProspector = Cobblemon.INSTANCE.getProspector()
   public final var resolver: AreaContextResolver = Cobblemon.INSTANCE.getAreaContextResolver()

   open fun AreaSpawner(name: java.lang.String, spawns: SpawnPool, manager: SpawnerManager) {
      super(name, spawns, manager);
   }

   public abstract fun getArea(cause: SpawnCause): SpawningArea? {
   }

   public override fun run(cause: SpawnCause): Pair<SpawningContext, SpawnDetail>? {
      val area: SpawningArea = this.getArea(cause);
      val constrainedArea: SpawningArea = if (area != null) this.constrainArea(area) else null;
      if (constrainedArea != null) {
         val areaBox: AABB = AABB.m_165882_(new Vec3(Vec3ExtensionsKt.toVec3f(constrainedArea.getCenter())), 96.0, 1000.0, 96.0);
         val var10000: ServerLevel = constrainedArea.getWorld();
         label16:
         if (!WorldExtensionsKt.isBoxLoaded(var10000, areaBox)) {
            return null;
         } else {
            return if ((float)constrainedArea.getWorld().m_6443_(PokemonEntity.class, areaBox, AreaSpawner::run$lambda$0).size() / 9
                  >= Cobblemon.INSTANCE.getConfig().getPokemonPerChunk())
               null
               else
               this.getSpawningSelector().select(this, this.resolver.resolve(this, this.contextCalculators, this.prospector.prospect(this, constrainedArea)));
         }
      } else {
         return null;
      }
   }

   public fun isValidStartPoint(world: Level, chunk: ChunkAccess, startPos: Mutable): Boolean {
      val y: Int = startPos.m_123342_();
      if (world.m_46749_(startPos as BlockPos) && world.m_46749_(startPos.m_142448_(y + 1) as BlockPos)) {
         val mid: BlockState = chunk.m_8055_(startPos.m_142448_(y) as BlockPos);
         if (!chunk.m_8055_(startPos.m_142448_(y + 1) as BlockPos).m_60647_(world as BlockGetter, startPos as BlockPos, PathComputationType.AIR)) {
            return false;
         } else {
            return !mid.m_60795_();
         }
      } else {
         return false;
      }
   }

   public fun constrainArea(area: SpawningArea): SpawningArea? {
      val basePos: MutableBlockPos = new MutableBlockPos(area.getBaseX(), area.getBaseY(), area.getBaseZ());
      val originalY: Int = area.getBaseY();
      val var4: Pair = new Pair(SectionPos.m_123171_(area.getBaseX()), SectionPos.m_123171_(area.getBaseZ()));
      val chunkX: Int = (var4.component1() as java.lang.Number).intValue();
      val chunkZ: Int = (var4.component2() as java.lang.Number).intValue();
      if (!area.getWorld().m_143319_(ChunkPos.m_45589_(chunkX, chunkZ))) {
         return null;
      } else {
         val var10000: ChunkAccess = area.getWorld().m_46819_(chunkX, chunkZ, ChunkStatus.f_62326_);
         if (var10000 == null) {
            return null;
         } else {
            val chunk: ChunkAccess = var10000;
            var valid: Boolean = this.isValidStartPoint(area.getWorld() as Level, var10000, basePos);
            if (!valid) {
               val min: Int = 1;

               do {
                  var var10001: Level = area.getWorld() as Level;
                  var var10003: MutableBlockPos = basePos.m_142448_(originalY + min);
                  if (this.isValidStartPoint(var10001, chunk, var10003)) {
                     valid = true;
                     basePos.m_142448_(originalY + min);
                     break;
                  }

                  var10001 = area.getWorld() as Level;
                  var10003 = basePos.m_142448_(originalY - min);
                  if (this.isValidStartPoint(var10001, chunk, var10003)) {
                     valid = true;
                     basePos.m_142448_(originalY + min);
                     break;
                  }
               } while (++offset <= Cobblemon.INSTANCE.getConfig().getMaxVerticalCorrectionBlocks());
            }

            if (valid) {
               val var11: BlockPos = WorldExtensionsKt.squeezeWithinBounds(area.getWorld() as Level, basePos as BlockPos);
               val var12: Level = area.getWorld() as Level;
               val var14: BlockPos = basePos.m_7918_(area.getLength(), area.getHeight(), area.getWidth());
               val max: BlockPos = WorldExtensionsKt.squeezeWithinBounds(var12, var14);
               if (area.getWorld().m_46749_(var11)
                  && area.getWorld().m_46749_(max)
                  && var11.m_123341_() < max.m_123341_()
                  && var11.m_123342_() < max.m_123342_()
                  && var11.m_123343_() < max.m_123343_()) {
                  return new SpawningArea(
                     area.getCause(),
                     area.getWorld(),
                     var11.m_123341_(),
                     var11.m_123342_(),
                     var11.m_123343_(),
                     max.m_123341_() - var11.m_123341_(),
                     max.m_123342_() - var11.m_123342_(),
                     max.m_123343_() - var11.m_123343_()
                  );
               }
            }

            return null;
         }
      }
   }

   @JvmStatic
   fun `run$lambda$0`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }

   public companion object {
      public const val CHUNK_REACH: Int
   }
}
