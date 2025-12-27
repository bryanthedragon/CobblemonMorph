package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.prospecting.SpawningProspector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningArea;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBlockTags;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import kotlin.jvm.functions.Function1;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public object CobblemonSpawningProspector : SpawningProspector {
   public override fun prospect(spawner: Spawner, area: SpawningArea): WorldSlice {
      val world: ServerLevel = area.getWorld();
      var baseY: Int = area.getBaseY();
      var height: Int = area.getHeight();
      if (baseY < world.m_141937_()) {
         val minimumDistanceBetweenEntities: Int = world.m_141937_() - baseY;
         baseY += minimumDistanceBetweenEntities;
         height -= minimumDistanceBetweenEntities;
         if (height < 1) {
            throw new IllegalStateException("World slice was attempted with totally awful base and dimensions");
         }
      }

      if (baseY + height >= world.m_151558_()) {
         height -= baseY + height - 1 - world.m_151558_();
         if (height < 1) {
            throw new IllegalStateException("World slice was attempted with totally awful base and dimensions");
         }
      }

      val var33: Double = Cobblemon.INSTANCE.getConfig().getMinimumDistanceBetweenEntities();
      val var10000: java.util.List = area.getWorld()
         .m_45933_(
            area.getCause().getEntity(),
            AABB.m_165882_(
               new Vec3(
                  (double)area.getBaseX() + (double)area.getLength() / 2.0,
                  (double)baseY + (double)height / 2.0,
                  (double)area.getBaseZ() + (double)area.getWidth() / 2.0
               ),
               (double)area.getLength() + var33,
               (double)height + var33,
               (double)area.getWidth() + var33
            )
         );
      var defaultState: java.lang.Iterable = var10000;
      var skyLevel: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : defaultState) {
         if (yRange is LivingEntity) {
            skyLevel.add(yRange);
         }
      }

      defaultState = skyLevel as java.util.List;
      skyLevel = new ArrayList(CollectionsKt.collectionSizeOrDefault(skyLevel as java.util.List, 10));

      for (Object item$iv$iv : defaultState) {
         skyLevel.add((var49 as LivingEntity).m_20182_());
      }

      val nearbyEntityPositions: java.util.List = skyLevel as java.util.List;
      val var35: BlockState = Blocks.f_50069_.m_49966_();
      val var37: WorldSlice.BlockData = new WorldSlice.BlockData(var35, 0, 0);
      var var39: Int = 0;
      var var42: Int = area.getLength();

      val var46: Array<Array<Array<WorldSlice.BlockData>>>;
      for (var46 = new WorldSlice.BlockData[var42][][]; var39 < var42; var39++) {
         var var52: Int = 0;
         val var54: Int = height;

         val var18: Array<Array<WorldSlice.BlockData>>;
         for (var18 = new WorldSlice.BlockData[height][]; var52 < var54; var52++) {
            var var20: Int = 0;
            val query: Int = area.getWidth();

            val canSeeSky: Array<WorldSlice.BlockData>;
            for (canSeeSky = new WorldSlice.BlockData[query]; var20 < query; var20++) {
               canSeeSky[var20] = var37;
            }

            var18[var52] = canSeeSky;
         }

         var46[var39] = var18;
      }

      val blocks: Array<Array<Array<WorldSlice.BlockData>>> = var46;
      var42 = 0;
      val var47: Int = area.getLength();

      val var50: Array<Array<Int>>;
      for (var50 = new Integer[var47][]; var42 < var47; var42++) {
         var var55: Int = 0;
         val var57: Int = area.getWidth();

         val z: Array<Int>;
         for (z = new Integer[var57]; var55 < var57; var55++) {
            z[var55] = world.m_151558_();
         }

         var50[var42] = z;
      }

      val var40: Array<Array<Int>> = var50;
      val var44: MutableBlockPos = new MutableBlockPos();
      val var48: java.util.Map = new LinkedHashMap();
      val var51: IntProgression = RangesKt.reversed(RangesKt.until(baseY, baseY + height) as IntProgression);
      val var53: LevelLightEngine = world.m_5518_();
      var var56: Int = area.getBaseX();

      for (int var58 = area.getBaseX() + area.getLength(); x < var58; x++) {
         var var59: Int = area.getBaseZ();

         for (int var60 = area.getBaseZ() + area.getWidth(); z < var60; z++) {
            val var63: ChunkAccess = var48.computeIfAbsent(
               new Pair(SectionPos.m_123171_(var56), SectionPos.m_123171_(var59)), CobblemonSpawningProspector::prospect$lambda$1
            );
            if (var63 != null) {
               val var61: ChunkAccess = var63;
               var var62: Boolean = world.m_46861_(var44.m_122178_(var56, var51.getFirst(), var59) as BlockPos);
               var y: Int = var51.getFirst();
               val var24: Int = var51.getLast();
               val var25: Int = var51.getStep();
               if (var25 > 0 && y <= var24 || var25 < 0 && var24 <= y) {
                  while (true) {
                     val skyLight: Int = var53.m_75814_(LightLayer.SKY).m_7768_(var44.m_122178_(var56, y, var59) as BlockPos);
                     val state: BlockState = var61.m_8055_(var44.m_122178_(var56, y, var59) as BlockPos);
                     val var64: Array<WorldSlice.BlockData> = blocks[var56 - area.getBaseX()][y - baseY];
                     val var10001: Int = var59 - area.getBaseZ();
                     var64[var10001] = new WorldSlice.BlockData(state, world.m_46803_(var44 as BlockPos), skyLight);
                     if (var62) {
                        var40[var56 - area.getBaseX()][var59 - area.getBaseZ()] = y;
                     }

                     if (state.m_60819_().m_76178_() && !state.m_204336_(CobblemonBlockTags.SEES_SKY)) {
                        var62 = false;
                     }

                     if (y == var24) {
                        break;
                     }

                     y += var25;
                  }
               }
            }
         }
      }

      return new WorldSlice(area.getCause(), world, area.getBaseX(), baseY, area.getBaseZ(), blocks, var40, nearbyEntityPositions);
   }

   @JvmStatic
   fun `prospect$lambda$1`(`$tmp0`: Function1, p0: Any): ChunkAccess {
      return `$tmp0`.invoke(p0) as ChunkAccess;
   }
}
