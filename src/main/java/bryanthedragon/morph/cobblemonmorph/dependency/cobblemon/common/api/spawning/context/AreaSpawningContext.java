package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext.StructureChunkCache;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;

import java.util.ArrayList;

import kotlin.jvm.functions.Function0;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;

public open class AreaSpawningContext(cause: SpawnCause, world: ServerLevel, position: BlockPos, light: Int, skyLight: Int, canSeeSky: Boolean, influences: MutableList<SpawningInfluence>, height: Int, nearbyBlocks: List<BlockState>, slice: WorldSlice) : SpawningContext {
   public open val canSeeSky: Boolean
   public open val cause: SpawnCause
   public final val height: Int
   public open val influences: MutableList<SpawningInfluence>
   public open val light: Int

   public final val nearbyBlockTypes: List<Block>
      public final get() {
         return this.nearbyBlockTypes$delegate.getValue() as MutableList<Block>;
      }


   public final val nearbyBlocks: List<BlockState>
   public open val position: BlockPos
   public open val skyLight: Int
   public final val slice: WorldSlice
   public open val world: ServerLevel

   init {
      this.cause = cause;
      this.world = world;
      this.position = position;
      this.light = light;
      this.skyLight = skyLight;
      this.canSeeSky = canSeeSky;
      this.influences = influences;
      this.height = height;
      this.nearbyBlocks = nearbyBlocks;
      this.slice = slice;
      this.nearbyBlockTypes$delegate = LazyKt.lazy((new Function0<java.util.List<? extends Block>>(this) {
         {
            super(0);
            this.this$0 = `$receiver`;
         }

         @NotNull
         public final java.util.List<Block> invoke() {
            val `$this$mapNotNull$iv`: java.lang.Iterable = this.this$0.getNearbyBlocks();
            val `destination$iv$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
               val var10000: Block = (`element$iv$iv$iv` as BlockState).m_60734_();
               if (var10000 != null) {
                  `destination$iv$iv`.add(var10000);
               }
            }

            return CollectionsKt.distinct(`destination$iv$iv` as java.util.List);
         }
      }) as Function0);
   }

   public override fun getStructureCache(pos: BlockPos): StructureChunkCache {
      return this.slice.getStructureCache(pos);
   }

   public open fun isSafeSpace(world: ServerLevel, pos: BlockPos, state: BlockState): Boolean {
      return !state.m_60838_(world as BlockGetter, pos);
   }

   public override fun postFilter(detail: SpawnDetail): Boolean {
      if (!super.postFilter(detail)) {
         return false;
      } else {
         if (detail.getWidth() > 1 || detail.getHeight() > 1) {
            val minX: Int = detail.getWidth();
            val maxX: Int = minX.intValue();
            val sizeX: Int = if ((if (maxX > 0) minX else null) != null) if (maxX > 0) minX else null else 1;
            val var15: Int = detail.getHeight();
            var itx: Int = var15.intValue();
            val sizeY: Int = if ((if (itx > 0) var15 else null) != null) if (itx > 0) var15 else null else 1;
            val var14: Int = (int)Math.floor((double)this.getPosition().m_123341_() + 0.5 - (double)((float)(sizeX - 1) / 2.0F)) - 1;
            val var16: Int = (int)Math.ceil((double)this.getPosition().m_123341_() + 0.5 + (double)((float)(sizeX + 1) / 2.0F)) + 1;
            itx = (int)((float)Math.ceil((double)((float)this.getPosition().m_123342_() + (float)(sizeY + 1) / 2.0F))) + 1;
            val var19: Int = (int)Math.floor((double)this.getPosition().m_123343_() + 0.5 - (double)((float)(sizeX - 1) / 2.0F)) - 1;
            val maxZ: Int = (int)Math.ceil((double)this.getPosition().m_123343_() + 0.5 + (double)((float)(sizeX + 1) / 2.0F)) + 1;
            val mutable: MutableBlockPos = new MutableBlockPos();

            for (int x = minX; x < maxX; x++) {
               var y: Int = this.getPosition().m_123342_() + 1;
               if (y <= itx) {
                  while (true) {
                     for (int z = minZ; z < maxZ; z++) {
                        val state: BlockState = this.getWorld().m_8055_(mutable.m_122178_(x, y, z) as BlockPos);
                        val var10001: ServerLevel = this.getWorld();
                        val var10002: BlockPos = mutable as BlockPos;
                        if (!this.isSafeSpace(var10001, var10002, state)) {
                           return false;
                        }
                     }

                     if (y == itx) {
                        break;
                     }

                     y++;
                  }
               }
            }
         }

         return true;
      }
   }
}
