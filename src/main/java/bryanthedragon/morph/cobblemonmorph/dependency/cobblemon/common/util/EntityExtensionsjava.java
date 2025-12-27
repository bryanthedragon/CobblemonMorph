package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import java.util.LinkedHashSet
import kotlin.jvm.functions.Function1
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Vec3i
import net.minecraft.core.Direction.Axis
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.Shapes

public fun Entity.setPositionSafely(pos: Vec3): Boolean {
   var result: Vec3 = pos;
   val eyes: Vec3 = pos.m_193103_(Axis.Y, pos.f_82480_ + (double)`$this$setPositionSafely`.m_20192_());
   var box: AABB = `$this$setPositionSafely`.m_20191_().m_82383_(pos);
   val conflicts: java.util.Set = new LinkedHashSet();
   if (!`$this$setPositionSafely`.m_9236_().m_186434_(`$this$setPositionSafely`, box).iterator().hasNext()) {
      `$this$setPositionSafely`.m_146884_(pos);
      return true;
   } else {
      val resultEyes: java.util.Iterator = BlockPos.m_121921_(box).iterator();

      while (resultEyes.hasNext()) {
         val resultEyeBox: BlockPos = resultEyes.next() as BlockPos;
         val collides: BlockState = `$this$setPositionSafely`.m_9236_().m_8055_(resultEyeBox);
         if (!collides.m_60795_()
            && collides.m_60828_(`$this$setPositionSafely`.m_9236_() as BlockGetter, resultEyeBox)
            && Shapes.m_83157_(
               collides.m_60812_(`$this$setPositionSafely`.m_9236_() as BlockGetter, resultEyeBox)
                  .m_83216_((double)resultEyeBox.m_123341_(), (double)resultEyeBox.m_123342_(), (double)resultEyeBox.m_123343_()),
               Shapes.m_83064_(box),
               BooleanOp.f_82689_
            )) {
            val target: BlockPos = Vec3ExtensionsKt.toBlockPos(eyes);

            for (Direction direction : Direction.values()) {
               if (!conflicts.contains(direction)) {
                  val conflict: Vec3 = BlockPosExtensionsKt.toVec3d(resultEyeBox);
                  if (target.m_121955_(direction.m_122436_()) == resultEyeBox) {
                     conflicts.add(direction);
                     switch (EntityExtensionsKt.WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
                        case 1:
                           return false;
                        case 2:
                           val var42: Vec3 = result.m_82549_(new Vec3(0.0, 0.0, (double)1 + (conflict.f_82481_ - box.f_82290_ + 0.125)));
                           result = var42;
                           break;
                        case 3:
                           val var41: Vec3 = result.m_82549_(new Vec3(0.0, 0.0, conflict.f_82481_ - box.f_82293_ - 0.125));
                           result = var41;
                           break;
                        case 4:
                           val var40: Vec3 = result.m_82549_(new Vec3((double)1 + (conflict.f_82479_ - box.f_82288_ + 0.125), 0.0, 0.0));
                           result = var40;
                           break;
                        case 5:
                           val var10000: Vec3 = result.m_82549_(new Vec3(conflict.f_82479_ - box.f_82291_ - 0.125, 0.0, 0.0));
                           result = var10000;
                        default:
                     }
                  }
               }
            }
         }
      }

      box = `$this$setPositionSafely`.m_20191_().m_82383_(result);
      if (!`$this$setPositionSafely`.m_9236_().m_186434_(`$this$setPositionSafely`, box).iterator().hasNext()) {
         `$this$setPositionSafely`.m_146884_(result);
         return true;
      } else {
         val var24: java.util.List = CollectionsKt.listOf(new java.lang.Double[]{1.0, -1.0, 2.0, -2.0});
         var var27: Double = 0.0;
         var var31: java.util.Iterator = var24.iterator();

         while (var31.hasNext()) {
            val var34: Double = (var31.next() as java.lang.Number).doubleValue();
            box = box.m_82386_(0.0, var34 - var27, 0.0);
            var27 = var34;
            if (!`$this$setPositionSafely`.m_9236_().m_186434_(`$this$setPositionSafely`, box).iterator().hasNext()) {
               val var38: Int = (int)(result.f_82480_ + var34);
               if (`$this$setPositionSafely`.m_9236_()
                  .m_186434_(`$this$setPositionSafely`, box.m_82386_(0.0, (double)((int)(result.f_82480_ + var34)) - result.f_82480_, 0.0))
                  .iterator()
                  .hasNext()) {
                  val var43: Vec3 = result.m_82520_(0.0, var34, 0.0);
                  `$this$setPositionSafely`.m_146884_(var43);
                  return true;
               }

               `$this$setPositionSafely`.m_146884_(new Vec3(result.f_82479_, (double)var38, result.f_82481_));
               return true;
            }
         }

         var27 = 0.0;
         box = `$this$setPositionSafely`.m_20191_().m_82383_(pos);
         var31 = var24.iterator();

         while (var31.hasNext()) {
            val var35: Double = (var31.next() as java.lang.Number).doubleValue();
            box = box.m_82386_(0.0, var35 - var27, 0.0);
            var27 = var35;
            if (!`$this$setPositionSafely`.m_9236_().m_186434_(`$this$setPositionSafely`, box).iterator().hasNext()) {
               val var39: Int = (int)(result.f_82480_ + var35);
               if (`$this$setPositionSafely`.m_9236_()
                  .m_186434_(`$this$setPositionSafely`, box.m_82386_(0.0, (double)((int)(result.f_82480_ + var35)) - result.f_82480_, 0.0))
                  .iterator()
                  .hasNext()) {
                  val var44: Vec3 = result.m_82520_(0.0, var35, 0.0);
                  `$this$setPositionSafely`.m_146884_(var44);
                  return true;
               }

               `$this$setPositionSafely`.m_146884_(new Vec3(result.f_82479_, (double)var39, result.f_82481_));
               return true;
            }
         }

         if (conflicts.size() >= 3) {
            `$this$setPositionSafely`.m_146884_(pos);
         }

         val var29: AABB = AABB.m_165882_(
            result.m_193103_(Axis.Y, result.f_82480_ + (double)`$this$setPositionSafely`.m_20192_()),
            (double)`$this$setPositionSafely`.m_20205_(),
            1.0E-6,
            (double)`$this$setPositionSafely`.m_20205_()
         );
         var var30: Boolean = false;
         var31 = BlockPos.m_121921_(var29).iterator();

         while (var31.hasNext()) {
            val var36: BlockPos = var31.next() as BlockPos;
            val var37: BlockState = `$this$setPositionSafely`.m_9236_().m_8055_(var36);
            var30 = !var37.m_60795_()
               && var37.m_60828_(`$this$setPositionSafely`.m_9236_() as BlockGetter, var36)
               && Shapes.m_83157_(
                  var37.m_60812_(`$this$setPositionSafely`.m_9236_() as BlockGetter, var36)
                     .m_83216_((double)var36.m_123341_(), (double)var36.m_123342_(), (double)var36.m_123343_()),
                  Shapes.m_83064_(box),
                  BooleanOp.f_82689_
               );
            if (var30) {
               break;
            }
         }

         if (var30) {
            `$this$setPositionSafely`.m_146884_(pos);
            return true;
         } else {
            `$this$setPositionSafely`.m_146884_(result);
            return true;
         }
      }
   }
}

public fun Entity.isStandingOnSandOrRedSand(): Boolean {
   val sandDepth: Int = 2;
   var a: Int = 1;

   while (true) {
      val sandBlockState: BlockState = `$this$isStandingOnSandOrRedSand`.m_9236_().m_8055_(`$this$isStandingOnSandOrRedSand`.m_20183_().m_6625_(a));
      val sandBlock: Block = sandBlockState.m_60734_();
      if (sandBlock == Blocks.f_49992_
         && !sandBlockState.m_60795_()
         && sandBlockState.m_60838_(`$this$isStandingOnSandOrRedSand`.m_9236_() as BlockGetter, `$this$isStandingOnSandOrRedSand`.m_20183_().m_6625_(a))) {
         return true;
      }

      if (sandBlock == Blocks.f_49993_
         && !sandBlockState.m_60795_()
         && sandBlockState.m_60838_(`$this$isStandingOnSandOrRedSand`.m_9236_() as BlockGetter, `$this$isStandingOnSandOrRedSand`.m_20183_().m_6625_(a))) {
         return true;
      }

      if (a == sandDepth) {
         return false;
      }

      a++;
   }
}

public fun Entity.isDusk(): Boolean {
   val time: Long = `$this$isDusk`.m_9236_().m_46468_() % 24000;
   return 12000L <= time && time < 13001L;
}

public fun Entity.isStandingOnSand(): Boolean {
   val sandDepth: Int = 2;
   var a: Int = 1;

   while (true) {
      val sandBlockState: BlockState = `$this$isStandingOnSand`.m_9236_().m_8055_(`$this$isStandingOnSand`.m_20183_().m_6625_(a));
      if (sandBlockState.m_60734_() == Blocks.f_49992_
         && !sandBlockState.m_60795_()
         && sandBlockState.m_60838_(`$this$isStandingOnSand`.m_9236_() as BlockGetter, `$this$isStandingOnSand`.m_20183_().m_6625_(a))) {
         return true;
      }

      if (a == sandDepth) {
         return false;
      }

      a++;
   }
}

public fun Entity.isStandingOnRedSand(): Boolean {
   val redSandDepth: Int = 2;
   var i: Int = 1;

   while (true) {
      val redSandBlockState: BlockState = `$this$isStandingOnRedSand`.m_9236_().m_8055_(`$this$isStandingOnRedSand`.m_20183_().m_6625_(i));
      if (redSandBlockState.m_60734_() == Blocks.f_49993_
         && !redSandBlockState.m_60795_()
         && redSandBlockState.m_60838_(`$this$isStandingOnRedSand`.m_9236_() as BlockGetter, `$this$isStandingOnRedSand`.m_20183_().m_6625_(i))) {
         return true;
      }

      if (i == redSandDepth) {
         return false;
      }

      i++;
   }
}

public fun Entity.distanceTo(pos: BlockPos): Double {
   return BlockPosExtensionsKt.toVec3d(pos).m_82546_(`$this$distanceTo`.m_20182_()).m_82553_();
}

public fun Entity.closestPosition(positions: Iterable<BlockPos>, filter: (BlockPos) -> Boolean = <unrepresentable>.INSTANCE as Function1): BlockPos? {
   var closest: BlockPos = null;
   var closestDistance: Double = java.lang.Double.MAX_VALUE;

   for (BlockPos position : positions) {
      if (filter.invoke(position) as java.lang.Boolean) {
         val distance: Double = distanceTo(`$this$closestPosition`, position);
         if (distance < closestDistance) {
            closest = new BlockPos(position as Vec3i);
            closestDistance = distance;
         }
      }
   }

   return closest;
}

@JvmSynthetic
fun `closestPosition$default`(var0: Entity, var1: java.lang.Iterable, var2: Function1, var3: Int, var4: Any): BlockPos {
   if ((var3 and 2) != 0) {
      var2 = <unrepresentable>.INSTANCE;
   }

   return closestPosition(var0, var1, var2);
}

public fun <T> SynchedEntityData.update(data: EntityDataAccessor<Any>, mutator: (Any) -> Any) {
   val value: Any = `$this$update`.m_135370_(data);
   val newValue: Any = mutator.invoke(value);
   if (!(value == newValue)) {
      `$this$update`.m_135381_(data, newValue);
   }
}
// $VF: Class flags could not be determined
@JvmSynthetic
internal class WhenMappings {
   @JvmStatic
   fun {
      val var0: IntArray = new int[Direction.values().length];

      try {
         var0[Direction.UP.ordinal()] = 1;
      } catch (var6: NoSuchFieldError) {
      }

      try {
         var0[Direction.NORTH.ordinal()] = 2;
      } catch (var5: NoSuchFieldError) {
      }

      try {
         var0[Direction.SOUTH.ordinal()] = 3;
      } catch (var4: NoSuchFieldError) {
      }

      try {
         var0[Direction.WEST.ordinal()] = 4;
      } catch (var3: NoSuchFieldError) {
      }

      try {
         var0[Direction.EAST.ordinal()] = 5;
      } catch (var2: NoSuchFieldError) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
