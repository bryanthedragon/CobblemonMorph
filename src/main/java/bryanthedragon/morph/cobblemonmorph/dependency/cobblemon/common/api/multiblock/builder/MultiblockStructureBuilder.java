package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.builder

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.condition.MultiblockCondition
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.phys.shapes.VoxelShape

public interface MultiblockStructureBuilder {
   public val boundingBox: VoxelShape
   public val conditions: List<MultiblockCondition>

   public open fun validate(world: ServerLevel): Boolean {
   }

   public abstract fun form(world: ServerLevel) {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nMultiblockStructureBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MultiblockStructureBuilder.kt\ncom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,40:1\n1855#2,2:41\n*S KotlinDebug\n*F\n+ 1 MultiblockStructureBuilder.kt\ncom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder$DefaultImpls\n*L\n29#1:41,2\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun validate(`$this`: MultiblockStructureBuilder, world: ServerLevel): Boolean {
         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            if (!(`element$iv` as MultiblockCondition).test(world, `$this`.getBoundingBox())) {
               return false;
            }
         }

         `$this`.form(world);
         return true;
      }
   }
}
