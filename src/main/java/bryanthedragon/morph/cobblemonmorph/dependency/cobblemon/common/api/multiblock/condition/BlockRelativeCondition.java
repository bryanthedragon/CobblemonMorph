package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.advancements.critereon.BlockPredicate
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.phys.shapes.VoxelShape

@SourceDebugExtension(["SMAP\nBlockRelativeCondition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BlockRelativeCondition.kt\ncom/cobblemon/mod/common/api/multiblock/condition/BlockRelativeCondition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,47:1\n766#2:48\n857#2,2:49\n1855#2:51\n1856#2:54\n13579#3,2:52\n*S KotlinDebug\n*F\n+ 1 BlockRelativeCondition.kt\ncom/cobblemon/mod/common/api/multiblock/condition/BlockRelativeCondition\n*L\n35#1:48\n35#1:49,2\n36#1:51\n36#1:54\n37#1:52,2\n*E\n"])
public class BlockRelativeCondition(relToBlock: BlockPredicate, targetBlock: BlockPredicate, vararg directionsToCheck: Any = Direction.values()) :
   MultiblockCondition {
   public final val directionsToCheck: Array<Direction>
   public final val relToBlock: BlockPredicate
   public final val targetBlock: BlockPredicate

   init {
      this.relToBlock = relToBlock;
      this.targetBlock = targetBlock;
      this.directionsToCheck = directionsToCheck;
   }

   public override fun test(world: ServerLevel, box: VoxelShape): Boolean {
      val `$this$forEach$iv`: java.lang.Iterable = MiscUtilsKt.blockPositionsAsList(box);
      val `element$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if (this.relToBlock.m_17914_(world, `$this$forEach$iv` as BlockPos)) {
            `element$iv`.add(`$this$forEach$iv`);
         }
      }

      for (Object element$ivx : $this$filter$iv) {
         val var20: BlockPos = `element$ivx` as BlockPos;

         val var22: Any;
         for (Object element$ivxx : var22) {
            if (this.targetBlock.m_17914_(world, var20.m_121945_((Direction)`element$ivxx`))) {
               return true;
            }
         }
      }

      return false;
   }
}
