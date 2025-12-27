package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.GroundedSpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Merger
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.level.block.Block

@SourceDebugExtension(["SMAP\nGroundedSpawningCondition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GroundedSpawningCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/GroundedTypeSpawningCondition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,56:1\n2624#2,3:57\n*S KotlinDebug\n*F\n+ 1 GroundedSpawningCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/GroundedTypeSpawningCondition\n*L\n34#1:57,3\n*E\n"])
public abstract class GroundedTypeSpawningCondition<T extends GroundedSpawningContext> : AreaTypeSpawningCondition<T> {
   public final var neededBaseBlocks: MutableList<RegistryLikeCondition<Block>>?

   protected open fun fits(ctx: Any): Boolean {
      var var10000: Boolean;
      if (!super.fits((T)ctx)) {
         var10000 = 0;
      } else {
         if (this.getMinHeight() != null) {
            var10000 = ctx.getHeight();
            val var10001: Int = this.getMinHeight();
            if (var10000 < var10001) {
               return false;
            }
         }

         if (this.getMaxHeight() != null) {
            var10000 = ctx.getHeight();
            val var12: Int = this.getMaxHeight();
            if (var10000 > var12) {
               return false;
            }
         }

         if (this.neededBaseBlocks != null) {
            val var10: java.util.List = this.neededBaseBlocks;
            val `$this$none$iv`: java.lang.Iterable = var10;
            var var11: Boolean;
            if (var10 is java.util.Collection && (var10 as java.util.Collection).isEmpty()) {
               var11 = true;
            } else {
               val var4: java.util.Iterator = `$this$none$iv`.iterator();

               while (true) {
                  if (!var4.hasNext()) {
                     var11 = true;
                     break;
                  }

                  val it: RegistryLikeCondition = var4.next() as RegistryLikeCondition;
                  val var13: Block = ctx.getBaseBlock().m_60734_();
                  if (it.fits(var13, ctx.getBlockRegistry())) {
                     var11 = false;
                     break;
                  }
               }
            }

            if (var11) {
               return false;
            }
         }

         var10000 = 1;
      }

      return (boolean)var10000;
   }

   public override fun copyFrom(other: SpawningCondition<*>, merger: Merger) {
      super.copyFrom(other, merger);
      if (other is GroundedTypeSpawningCondition) {
         val var10001: java.util.Collection = merger.merge(this.neededBaseBlocks, (other as GroundedTypeSpawningCondition).neededBaseBlocks);
         this.neededBaseBlocks = if (var10001 != null) CollectionsKt.toMutableList(var10001) else null;
      }
   }
}
