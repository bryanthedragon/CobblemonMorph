package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext.StructureChunkCache;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WorldSlice(cause: SpawnCause, world: ServerLevel, baseX: Int, baseY: Int, baseZ: Int, vararg blocks: Any, vararg skyLevel: Any, nearbyEntityPositions: List<Vec3>) {
   public final val baseX: Int
   public final val baseY: Int
   public final val baseZ: Int
   public final val blocks: Array<Array<Array<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice.BlockData>>>
   public final val cause: SpawnCause
   public final val height: Int
   public final val length: Int
   public final var nearbyEntityPositions: List<Vec3>
   public final val skyLevel: Array<Array<Int>>
   private final val structureChunkCaches: MutableMap<ChunkPos, StructureChunkCache>
   public final val width: Int
   public final val world: ServerLevel

   init {
      this.cause = cause;
      this.world = world;
      this.baseX = baseX;
      this.baseY = baseY;
      this.baseZ = baseZ;
      this.blocks = blocks;
      this.skyLevel = skyLevel;
      this.nearbyEntityPositions = nearbyEntityPositions;
      this.length = (this.blocks as Array<Any>).length;
      this.height = (this.blocks[0] as Array<Any>).length;
      this.width = this.blocks[0][0].length;
      this.structureChunkCaches = new LinkedHashMap<>();
   }

   public fun getStructureCache(pos: BlockPos): StructureChunkCache {
      val `$this$getOrPut$iv`: java.util.Map = this.structureChunkCaches;
      val `key$iv`: Any = new ChunkPos(pos);
      val `value$iv`: Any = `$this$getOrPut$iv`.get(`key$iv`);
      val var10000: Any;
      if (`value$iv` == null) {
         val var7: Any = new SpawningContext.StructureChunkCache();
         `$this$getOrPut$iv`.put(`key$iv`, var7);
         var10000 = var7;
      } else {
         var10000 = `value$iv`;
      }

      return var10000 as SpawningContext.StructureChunkCache;
   }

   public fun isInBounds(x: Int, y: Int, z: Int): Boolean {
      return x >= this.baseX
         && x < this.baseX + this.length
         && y >= this.baseY
         && y < this.baseY + this.height
         && z >= this.baseZ
         && z < this.baseZ + this.width;
   }

   public fun setBlockData(x: Int, y: Int, z: Int): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice.BlockData {
      return this.blocks[x - this.baseX][y - this.baseY][z - this.baseZ];
   }

   public fun getBlockData(position: BlockPos): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice.BlockData {
      return this.getBlockData(position.m_123341_(), position.m_123342_(), position.m_123343_());
   }

   public fun getBlockStatePos(x: Int, y: Int, z: Int, elseBlock: BlockState = stoneState): BlockState {
      return if (!this.isInBounds(x, y, z)) elseBlock else this.blocks[x - this.baseX][y - this.baseY][z - this.baseZ].getState();
   }

   public fun getBlockState(position: BlockPos, elseBlock: BlockState = stoneState): BlockState {
      return this.getBlockState(position.m_123341_(), position.m_123342_(), position.m_123343_(), elseBlock);
   }

   public fun getLight(x: Int, y: Int, z: Int, elseLight: Int = 0): Int {
      return if (!this.isInBounds(x, y, z)) elseLight else this.getBlockData(x, y, z).getLight();
   }

   public fun getLight(position: BlockPos, elseLight: Int = 0): Int {
      return this.getLight(position.m_123341_(), position.m_123342_(), position.m_123343_(), elseLight);
   }

   public fun getSkyLight(x: Int, y: Int, z: Int, elseLight: Int = 0): Int {
      return if (!this.isInBounds(x, y, z)) elseLight else this.getBlockData(x, y, z).getSkyLight();
   }

   public fun getSkyLight(position: BlockPos, elseLight: Int = 0): Int {
      return this.getSkyLight(position.m_123341_(), position.m_123342_(), position.m_123343_(), elseLight);
   }

   public fun skySpaceAbove(x: Int, y: Int, z: Int): Int {
      return if (this.isInBounds(x, y, z) && this.skyLevel[x - this.baseX][z - this.baseZ] <= y) Math.max(0, this.world.m_151558_() - y) else 0;
   }

   public fun skySpaceAbove(position: BlockPos): Int {
      return this.skySpaceAbove(position.m_123341_(), position.m_123342_(), position.m_123343_());
   }

   public fun canSeeSky(x: Int, y: Int, z: Int, elseCanSeeSky: Boolean = false): Boolean {
      return if (!this.isInBounds(x, y, z)) elseCanSeeSky else y >= this.skyLevel[x - this.baseX][z - this.baseZ];
   }

   public fun canSeeSky(position: BlockPos, elseCanSeeSky: Boolean = false): Boolean {
      return this.canSeeSky(position.m_123341_(), position.m_123342_(), position.m_123343_(), elseCanSeeSky);
   }

   public fun nearbyBlocks(position: BlockPos, maxHorizontalRadius: Int, maxVerticalRadius: Int): List<BlockState> {
      return this.nearbyBlocks(position.m_123341_(), position.m_123342_(), position.m_123343_(), maxHorizontalRadius, maxVerticalRadius);
   }

   public fun nearbyBlocks(centerX: Int, centerY: Int, centerZ: Int, maxHorizontalRadius: Int, maxVerticalRadius: Int): List<BlockState> {
      val blocks: java.util.List = new ArrayList();
      val minX: Int = RangesKt.coerceAtLeast(centerX - maxHorizontalRadius, this.baseX);
      val minY: Int = RangesKt.coerceAtLeast(centerY - maxVerticalRadius, this.baseY);
      val minZ: Int = RangesKt.coerceAtLeast(centerZ - maxHorizontalRadius, this.baseZ);
      val maxX: Int = RangesKt.coerceAtMost(centerX + maxHorizontalRadius, this.baseX + this.length);
      val maxY: Int = RangesKt.coerceAtMost(centerY + maxVerticalRadius, this.baseY + this.height);
      val maxZ: Int = RangesKt.coerceAtMost(centerZ + maxHorizontalRadius, this.baseZ + this.width);
      var x: Int = minX;
      if (minX <= maxX) {
         while (true) {
            var y: Int = minY;
            if (minY <= maxY) {
               while (true) {
                  var z: Int = minZ;
                  if (minZ <= maxZ) {
                     while (true) {
                        blocks.add(getBlockState$default(this, x, y, z, null, 8, null));
                        if (z == maxZ) {
                           break;
                        }

                        z++;
                     }
                  }

                  if (y == maxY) {
                     break;
                  }

                  y++;
               }
            }

            if (x == maxX) {
               break;
            }

            x++;
         }
      }

      return blocks;
   }

   public fun horizontalSpace(position: BlockPos, condition: (BlockState) -> Boolean, maximum: Int): Int {
      return this.horizontalSpace(position.m_123341_(), position.m_123342_(), position.m_123343_(), condition, maximum);
   }

   public fun horizontalSpace(centerX: Int, centerY: Int, centerZ: Int, condition: (BlockState) -> Boolean, maximum: Int): Int {
      var space: Int = 1;

      for (int radius = 1; radius <= maximum; space += 2) {
         val minX: Int = centerX - radius;
         val maxX: Int = centerX + radius;
         val minZ: Int = centerZ - radius;
         val maxZ: Int = centerZ + radius;
         if (!this.isInBounds(minX, centerY, minZ) || !this.isInBounds(maxX, centerY, centerZ + radius)) {
            return space;
         }

         var x: Int = minX;

         for (int z = minZ; z <= maxZ; z++) {
            if (!condition.invoke(getBlockState$default(this, x, centerY, z, null, 8, null)) as java.lang.Boolean) {
               return space;
            }
         }

         x = maxX;

         for (int var17 = minZ; var17 <= maxZ; var17++) {
            if (!condition.invoke(getBlockState$default(this, x, centerY, var17, null, 8, null)) as java.lang.Boolean) {
               return space;
            }
         }

         var var18: Int = minZ;

         for (int var15 = minX + 1; var15 < maxX; var15++) {
            if (!condition.invoke(getBlockState$default(this, var15, centerY, var18, null, 8, null)) as java.lang.Boolean) {
               return space;
            }
         }

         var18 = maxZ;

         for (int var16 = minX + 1; var16 < maxX; var16++) {
            if (!condition.invoke(getBlockState$default(this, var16, centerY, var18, null, 8, null)) as java.lang.Boolean) {
               return space;
            }
         }

         radius++;
      }

      return space;
   }

   public fun heightSpace(centerX: Int, centerY: Int, centerZ: Int, condition: (BlockState) -> Boolean, maximum: Int): Int {
      var space: Int;
      for (space = 1; space <= maximum; space++) {
         val y: Int = centerY + space;
         if (centerY + space >= this.baseY + this.height) {
            return space;
         }

         if (!condition.invoke(getBlockState$default(this, centerX, y, centerZ, null, 8, null)) as java.lang.Boolean) {
            return space;
         }
      }

      return space;
   }

   public fun depthSpace(centerX: Int, centerY: Int, centerZ: Int, condition: (BlockState) -> Boolean, maximum: Int): Int {
      var space: Int;
      for (space = 1; space <= maximum; space++) {
         val y: Int = centerY - space;
         if (centerY - space < this.baseY) {
            return space;
         }

         if (!condition.invoke(getBlockState$default(this, centerX, y, centerZ, null, 8, null)) as java.lang.Boolean) {
            return space;
         }
      }

      return space;
   }

   public class BlockData(state: BlockState, light: Int, skyLight: Int) {
      public final val light: Int
      public final val skyLight: Int
      public final val state: BlockState

      init {
         this.state = state;
         this.light = light;
         this.skyLight = skyLight;
      }
   }

   public companion object {
      public final val stoneState: BlockState
   }
}
