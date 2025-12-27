package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.builder.MultiblockStructureBuilder
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.condition.BlockRelativeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.RestorationTankBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.advancements.critereon.BlockPredicate
import net.minecraft.advancements.critereon.BlockPredicate.Builder
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

@SourceDebugExtension(["SMAP\nFossilMultiblockBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilMultiblockBuilder.kt\ncom/cobblemon/mod/common/block/multiblock/FossilMultiblockBuilder\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,170:1\n766#2:171\n857#2,2:172\n1549#2:174\n1620#2,3:175\n1549#2:178\n1620#2,2:179\n1855#2,2:181\n1622#2:183\n350#2,7:184\n766#2:191\n857#2,2:192\n*S KotlinDebug\n*F\n+ 1 FossilMultiblockBuilder.kt\ncom/cobblemon/mod/common/block/multiblock/FossilMultiblockBuilder\n*L\n89#1:171\n89#1:172,2\n93#1:174\n93#1:175,3\n98#1:178\n98#1:179,2\n102#1:181,2\n98#1:183\n110#1:184,7\n126#1:191\n126#1:192,2\n*E\n"])
public class FossilMultiblockBuilder(centerPos: BlockPos) : MultiblockStructureBuilder {
   public open val boundingBox: VoxelShape
   public final val centerPos: BlockPos
   public open val conditions: List<BlockRelativeCondition>

   init {
      this.centerPos = centerPos;
      val var10001: VoxelShape = Shapes.m_83110_(
         Shapes.m_83048_(
            (double)this.centerPos.m_123341_() - 1.0,
            (double)this.centerPos.m_123342_() - 1.0,
            (double)this.centerPos.m_123343_(),
            (double)this.centerPos.m_123341_() + 2.0,
            (double)this.centerPos.m_123342_() + 2.0,
            (double)this.centerPos.m_123343_() + 1.0
         ),
         Shapes.m_83048_(
            (double)this.centerPos.m_123341_(),
            (double)this.centerPos.m_123342_() - 1.0,
            (double)this.centerPos.m_123343_() - 1.0,
            (double)this.centerPos.m_123341_() + 1.0,
            (double)this.centerPos.m_123342_() + 2.0,
            (double)this.centerPos.m_123343_() + 2.0
         )
      );
      this.boundingBox = var10001;
      val var2: Array<BlockRelativeCondition> = new BlockRelativeCondition[2];
      var var10005: BlockPredicate = FOSSIL_ANALYZER_PRED;
      var var10006: BlockPredicate = MONITOR_PRED;
      var2[0] = new BlockRelativeCondition(var10005, var10006, new Direction[]{Direction.UP});
      var10005 = FOSSIL_ANALYZER_PRED;
      var10006 = RESTORATION_TANK_PRED;
      var2[1] = new BlockRelativeCondition(var10005, var10006, new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST});
      this.conditions = CollectionsKt.listOf(var2);
   }

   public override fun form(world: ServerLevel) {
      val blocks: java.util.List = MiscUtilsKt.blockPositionsAsList(this.getBoundingBox());
      val dirsToCheck: java.util.List = CollectionsKt.listOf(new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST});
      val fossilAnalyzerPositions: java.lang.Iterable = blocks;
      val monitorPos: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if (MONITOR_PRED.m_17914_(world, monitorEntity as BlockPos)) {
            monitorPos.add(monitorEntity);
         }
      }

      val var27: java.util.List = monitorPos as java.util.List;
      val var29: java.lang.Iterable = monitorPos as java.util.List;
      val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(monitorPos as java.util.List, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$ivx`.add(if (FOSSIL_ANALYZER_PRED.m_17914_(world, (var46 as BlockPos).m_7495_())) (var46 as BlockPos).m_7495_() else null);
      }

      val var28: java.util.List = `destination$iv$ivx` as java.util.List;
      val `$this$map$ivx`: java.lang.Iterable = `destination$iv$ivx` as java.util.List;
      val `destination$iv$ivxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`destination$iv$ivx` as java.util.List, 10));

      for (Object item$iv$iv : $this$map$ivx) {
         val var56: BlockPos = var52 as BlockPos;
         var var10000: BlockPos;
         if (var52 as BlockPos == null) {
            var10000 = null;
         } else {
            val `$this$filterTo$iv$iv`: java.util.Iterator = dirsToCheck.iterator();

            while (true) {
               if (!`$this$filterTo$iv$iv`.hasNext()) {
                  var10000 = null;
                  break;
               }

               val `$i$f$filterTo`: Direction = `$this$filterTo$iv$iv`.next() as Direction;
               if (RESTORATION_TANK_PRED.m_17914_(world, var56.m_121945_(`$i$f$filterTo`))) {
                  var10000 = var56.m_121945_(`$i$f$filterTo`);
                  break;
               }
            }
         }

         `destination$iv$ivxx`.add(var10000);
      }

      val var30: java.util.List = `destination$iv$ivxx` as java.util.List;
      var var40: Int = 0;
      val var44: java.util.Iterator = var30.iterator();

      var var67: Int;
      while (true) {
         if (!var44.hasNext()) {
            var67 = -1;
            break;
         }

         if (var44.next() as BlockPos != null) {
            var67 = var40;
            break;
         }

         var40++;
      }

      if (var67 == -1) {
         Cobblemon.INSTANCE.getLOGGER().error("FossilMultiblockBuilder form called on invalid structure! This should never happen!");
      } else {
         val var34: BlockPos = var27.get(var67) as BlockPos;
         val var68: Any = var28.get(var67);
         val var37: BlockPos = var68 as BlockPos;
         val var69: Any = var30.get(var67);
         val var41: BlockPos = var69 as BlockPos;
         val var49: BlockEntity = world.m_7702_(var34);
         val var45: MultiblockEntity = var49 as? MultiblockEntity;
         val var54: BlockEntity = world.m_7702_(var37);
         val var50: MultiblockEntity = var54 as? MultiblockEntity;
         val var58: BlockEntity = world.m_7702_(var41);
         val var55: MultiblockEntity = var58 as? MultiblockEntity;
         val var60: BlockEntity = world.m_7702_(var41.m_7494_());
         val var59: MultiblockEntity = var60 as? MultiblockEntity;
         val var61: FossilMultiblockStructure = new FossilMultiblockStructure(var34, var37, var41, 0, 0.0F, 24, null);
         val var62: java.lang.Iterable = dirsToCheck;
         val `destination$iv$ivxxx`: java.util.Collection = new ArrayList();

         for (Object element$iv$ivx : var62) {
            if (var41.m_121945_(`element$iv$ivx` as Direction) == var37) {
               `destination$iv$ivxxx`.add(`element$iv$ivx`);
            }
         }

         var61.setTankConnectorDirection(CollectionsKt.first(`destination$iv$ivxxx` as java.util.List) as Direction);
         if (var50 != null) {
            var50.setMultiblockStructure(var61);
         }

         if (var55 != null) {
            var55.setMultiblockStructure(var61);
         }

         if (var59 != null) {
            var59.setMultiblockStructure(var61);
         }

         if (var45 != null) {
            var45.setMultiblockStructure(var61);
         }

         var61.syncToClient(world as Level);
         var61.markDirty(world as Level);
         world.m_247517_(null, this.centerPos, CobblemonSounds.FOSSIL_MACHINE_ASSEMBLE, SoundSource.BLOCKS);
         if (var50 != null) {
            var50.setMultiblockBuilder(null);
         }

         if (var55 != null) {
            var55.setMultiblockBuilder(null);
         }

         if (var59 != null) {
            var59.setMultiblockBuilder(null);
         }

         if (var45 != null) {
            var45.setMultiblockBuilder(null);
         }
      }
   }

   override fun validate(world: ServerLevel): Boolean {
      return MultiblockStructureBuilder.DefaultImpls.validate(this, world);
   }

   @JvmStatic
   fun {
      val `$this$NBT_TO_CHECK_u24lambda_u246`: FossilMultiblockBuilder.Companion = Companion;
      val nbt: CompoundTag = new CompoundTag();
      nbt.m_128379_("Formed", false);
      NBT_TO_CHECK = nbt;
      MONITOR_PRED = Builder.m_17924_().m_146726_(new Block[]{CobblemonBlocks.MONITOR}).m_146724_(NBT_TO_CHECK).m_17931_();
      FOSSIL_ANALYZER_PRED = Builder.m_17924_().m_146726_(new Block[]{CobblemonBlocks.FOSSIL_ANALYZER}).m_146724_(NBT_TO_CHECK).m_17931_();
      RESTORATION_TANK_PRED = Builder.m_17924_()
         .m_146724_(NBT_TO_CHECK)
         .m_146726_(new Block[]{CobblemonBlocks.RESTORATION_TANK})
         .m_17929_(
            net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.m_67693_()
               .m_67697_(RestorationTankBlock.Companion.getPART() as Property, RestorationTankBlock.TankPart.BOTTOM)
               .m_67706_()
         )
         .m_17931_();
   }

   public companion object {
      public final val FOSSIL_ANALYZER_PRED: BlockPredicate
      public final val MONITOR_PRED: BlockPredicate
      public final val NBT_TO_CHECK: CompoundTag
      public final val RESTORATION_TANK_PRED: BlockPredicate
   }
}
