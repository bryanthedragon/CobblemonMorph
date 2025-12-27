package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import net.minecraft.core.Direction
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

public fun voxelShape(minX: Double, minY: Double, minZ: Double, maxX: Double, maxY: Double, maxZ: Double, direction: Direction): VoxelShape {
   var var10000: Double;
   switch (VectorShapeExtensionsKt.WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
      case 1:
         var10000 = minX;
         break;
      case 2:
         var10000 = 1 - maxX;
         break;
      case 3:
         var10000 = minZ;
         break;
      default:
         var10000 = 1 - maxZ;
   }

   switch (VectorShapeExtensionsKt.WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
      case 1:
         var10000 = maxX;
         break;
      case 2:
         var10000 = 1 - minX;
         break;
      case 3:
         var10000 = maxZ;
         break;
      default:
         var10000 = 1 - minZ;
   }

   switch (VectorShapeExtensionsKt.WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
      case 1:
         var10000 = minZ;
         break;
      case 2:
         var10000 = 1 - maxZ;
         break;
      case 3:
         var10000 = minX;
         break;
      default:
         var10000 = 1 - maxX;
   }

   switch (VectorShapeExtensionsKt.WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
      case 1:
         var10000 = maxZ;
         break;
      case 2:
         var10000 = 1 - minZ;
         break;
      case 3:
         var10000 = maxX;
         break;
      default:
         var10000 = 1 - minX;
   }

   val var24: VoxelShape = Shapes.m_83048_(var10000, minY, var10000, var10000, maxY, var10000);
   return var24;
}
// $VF: Class flags could not be determined
@JvmSynthetic
internal class WhenMappings {
   @JvmStatic
   fun {
      val var0: IntArray = new int[Direction.values().length];

      try {
         var0[Direction.NORTH.ordinal()] = 1;
      } catch (var4: NoSuchFieldError) {
      }

      try {
         var0[Direction.SOUTH.ordinal()] = 2;
      } catch (var3: NoSuchFieldError) {
      }

      try {
         var0[Direction.EAST.ordinal()] = 3;
      } catch (var2: NoSuchFieldError) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
