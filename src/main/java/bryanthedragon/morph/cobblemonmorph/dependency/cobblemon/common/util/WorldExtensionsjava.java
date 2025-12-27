package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import java.util.ArrayList;
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Ref.BooleanRef
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.core.SectionPos
import net.minecraft.core.BlockPos.MutableBlockPos
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.FluidTags
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.Item
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.border.WorldBorder
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public final val biomeRegistry: Registry<Biome>
   public final get() {
      val var10000: Registry = `$this$biomeRegistry`.m_9598_().m_175515_(Registries.f_256952_);
      return var10000;
   }


public final val itemRegistry: Registry<Item>
   public final get() {
      val var10000: Registry = `$this$itemRegistry`.m_9598_().m_175515_(Registries.f_256913_);
      return var10000;
   }


public fun Level.playSoundServer(position: Vec3, sound: SoundEvent, category: SoundSource = SoundSource.NEUTRAL, volume: Float = 1.0F, pitch: Float = 1.0F) {
   (`$this$playSoundServer` as ServerLevel).m_6263_(null, position.f_82479_, position.f_82480_, position.f_82481_, sound, category, volume, pitch);
}

@JvmSynthetic
fun `playSoundServer$default`(var0: Level, var1: Vec3, var2: SoundEvent, var3: SoundSource, var4: Float, var5: Float, var6: Int, var7: Any) {
   if ((var6 and 4) != 0) {
      var3 = SoundSource.NEUTRAL;
   }

   if ((var6 and 8) != 0) {
      var4 = 1.0F;
   }

   if ((var6 and 16) != 0) {
      var5 = 1.0F;
   }

   playSoundServer(var0, var1, var2, var3, var4, var5);
}

public fun <T : ParticleOptions> Level.sendParticlesServer(particleType: Any, position: Vec3, particles: Int, offset: Vec3, speed: Double): Int {
   return (`$this$sendParticlesServer` as ServerLevel)
      .m_8767_(particleType, position.f_82479_, position.f_82480_, position.f_82481_, particles, offset.f_82479_, offset.f_82480_, offset.f_82481_, speed);
}

public fun Level.squeezeWithinBounds(pos: BlockPos): BlockPos {
   val border: WorldBorder = `$this$squeezeWithinBounds`.m_6857_();
   return new BlockPos(
      RangesKt.coerceIn(pos.m_123341_(), (int)border.m_61955_(), (int)border.m_61957_()),
      RangesKt.coerceIn(pos.m_123342_(), `$this$squeezeWithinBounds`.m_141937_(), `$this$squeezeWithinBounds`.m_151558_()),
      RangesKt.coerceIn(pos.m_123343_(), (int)border.m_61956_(), (int)border.m_61958_())
   );
}

public fun ServerLevel.isBoxLoaded(box: AABB): Boolean {
   val startChunkX: Int = SectionPos.m_175552_(box.f_82288_);
   val startChunkZ: Int = SectionPos.m_175552_(box.f_82290_);
   val endChunkX: Int = SectionPos.m_175552_(box.f_82291_);
   val endChunkZ: Int = SectionPos.m_175552_(box.f_82293_);
   var chunkX: Int = startChunkX;
   if (startChunkX <= endChunkX) {
      while (true) {
         var chunkZ: Int = startChunkZ;
         if (startChunkZ <= endChunkZ) {
            while (true) {
               if (!`$this$isBoxLoaded`.m_143319_(ChunkPos.m_45589_(chunkX, chunkZ))) {
                  return false;
               }

               if (chunkZ == endChunkZ) {
                  break;
               }

               chunkZ++;
            }
         }

         if (chunkX == endChunkX) {
            break;
         }

         chunkX++;
      }
   }

   return true;
}

public fun AABB.getRanges(): Triple<IntRange, IntRange, IntRange> {
   return new Triple(
      new IntRange(Mth.m_14107_(`$this$getRanges`.f_82288_), Mth.m_14165_(`$this$getRanges`.f_82291_)),
      new IntRange((int)`$this$getRanges`.f_82289_, Mth.m_14165_(`$this$getRanges`.f_82292_)),
      new IntRange((int)`$this$getRanges`.f_82290_, Mth.m_14165_(`$this$getRanges`.f_82293_))
   );
}

public fun BlockGetter.doForAllBlocksIn(box: AABB, useMutablePos: Boolean, action: (BlockState, BlockPos) -> Unit) {
   val mutable: MutableBlockPos = new MutableBlockPos();
   val var5: Triple = getRanges(box);
   val xRange: IntRange = var5.component1() as IntRange;
   val yRange: IntRange = var5.component2() as IntRange;
   val zRange: IntRange = var5.component3() as IntRange;
   var x: Int = xRange.getFirst();
   val var10: Int = xRange.getLast();
   if (x <= var10) {
      while (true) {
         var y: Int = yRange.getFirst();
         val var12: Int = yRange.getLast();
         if (y <= var12) {
            while (true) {
               var z: Int = zRange.getFirst();
               val var14: Int = zRange.getLast();
               if (z <= var14) {
                  while (true) {
                     val pos: BlockPos = if (useMutablePos) mutable.m_122178_(x, y, z) as BlockPos else new BlockPos(x, y, z);
                     val state: BlockState = `$this$doForAllBlocksIn`.m_8055_(pos);
                     action.invoke(state, pos);
                     if (z == var14) {
                        break;
                     }

                     z++;
                  }
               }

               if (y == var12) {
                  break;
               }

               y++;
            }
         }

         if (x == var10) {
            break;
         }

         x++;
      }
   }
}

public fun BlockGetter.getBlockStates(box: AABB): Iterable<BlockState> {
   val states: java.util.List = new ArrayList();
   doForAllBlocksIn(`$this$getBlockStates`, box, true, (new Function2<BlockState, BlockPos, Unit>(states) {
      {
         super(2);
         this.$states = `$states`;
      }

      public final void invoke(@NotNull BlockState state, @NotNull BlockPos var2) {
         this.$states.add(state);
      }
   }) as (BlockState?, BlockPos?) -> Unit);
   return states;
}

public fun BlockGetter.getBlockStatesWithPos(box: AABB): Iterable<Pair<BlockState, BlockPos>> {
   val states: java.util.List = new ArrayList();
   doForAllBlocksIn(`$this$getBlockStatesWithPos`, box, true, (new Function2<BlockState, BlockPos, Unit>(states) {
      {
         super(2);
         this.$states = `$states`;
      }

      public final void invoke(@NotNull BlockState state, @NotNull BlockPos pos) {
         this.$states.add(TuplesKt.to(state, pos));
      }
   }) as (BlockState?, BlockPos?) -> Unit);
   return states;
}

public fun BlockGetter.getWaterAndLavaIn(box: AABB): Pair<Boolean, Boolean> {
   val hasWater: BooleanRef = new BooleanRef();
   val hasLava: BooleanRef = new BooleanRef();
   doForAllBlocksIn(`$this$getWaterAndLavaIn`, box, true, (new Function2<BlockState, BlockPos, Unit>(hasWater, hasLava) {
      {
         super(2);
         this.$hasWater = `$hasWater`;
         this.$hasLava = `$hasLava`;
      }

      public final void invoke(@NotNull BlockState state, @NotNull BlockPos var2) {
         if (!this.$hasWater.element && state.m_60819_().m_205070_(FluidTags.f_13131_)) {
            this.$hasWater.element = true;
         }

         if (!this.$hasLava.element && state.m_60819_().m_205070_(FluidTags.f_13132_)) {
            this.$hasLava.element = true;
         }
      }
   }) as (BlockState?, BlockPos?) -> Unit);
   return TuplesKt.to(hasWater.element, hasLava.element);
}

public fun Entity.canFit(pos: BlockPos): Boolean {
   return canFit(`$this$canFit`, BlockPosExtensionsKt.toVec3d(pos));
}

public fun Entity.canFit(vec: Vec3): Boolean {
   return `$this$canFit`.m_9236_().m_45772_(`$this$canFit`.m_20191_().m_82383_(vec.m_82546_(`$this$canFit`.m_20182_())));
}

public fun Vec3.traceDownwards(world: Level, maxDistance: Float = 10.0F, stepDistance: Float = 0.5F): TraceResult? {
   var step: Float = stepDistance;
   val startPos: Vec3 = new Vec3(`$this$traceDownwards`.f_82479_, `$this$traceDownwards`.f_82480_, `$this$traceDownwards`.f_82481_);
   val direction: Vec3 = new Vec3(0.0, -1.0, 0.0);
   var lastBlockPos: BlockPos = Vec3ExtensionsKt.toBlockPos(startPos);

   while (step <= maxDistance) {
      val location: Vec3 = startPos.m_82549_(direction.m_82490_((double)step));
      step += stepDistance;
      val blockPos: BlockPos = Vec3ExtensionsKt.toBlockPos(location);
      if (!(blockPos == lastBlockPos)) {
         lastBlockPos = blockPos;
         if (!world.m_8055_(blockPos).m_60795_()) {
            return new TraceResult(location, blockPos, PlayerExtensionsKt.findDirectionForIntercept(startPos, location, blockPos));
         }
      }
   }

   return null;
}

@JvmSynthetic
fun `traceDownwards$default`(var0: Vec3, var1: Level, var2: Float, var3: Float, var4: Int, var5: Any): TraceResult {
   if ((var4 and 2) != 0) {
      var2 = 10.0F;
   }

   if ((var4 and 4) != 0) {
      var3 = 0.5F;
   }

   return traceDownwards(var0, var1, var2, var3);
}
