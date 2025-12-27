package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Merger
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.level.block.Block

@SourceDebugExtension(["SMAP\nAreaSpawningCondition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AreaSpawningCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/AreaTypeSpawningCondition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,64:1\n2624#2,2:65\n1747#2,3:67\n2626#2:70\n*S KotlinDebug\n*F\n+ 1 AreaSpawningCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/AreaTypeSpawningCondition\n*L\n36#1:65,2\n36#1:67,3\n36#1:70\n*E\n"])
public abstract class AreaTypeSpawningCondition<T extends AreaSpawningContext> : SpawningCondition<T> {
   public final var maxHeight: Int?
   public final var minHeight: Int?
   public final var neededNearbyBlocks: MutableList<RegistryLikeCondition<Block>>?

   protected open fun fits(ctx: Any): Boolean {
      if (!super.fits((T)ctx)) {
         return false;
      } else {
         if (this.minHeight != null) {
            val var10000: Int = ctx.getHeight();
            val var10001: Int = this.minHeight;
            if (var10000 < var10001) {
               return false;
            }
         }

         if (this.maxHeight != null) {
            val var14: Int = ctx.getHeight();
            val var18: Int = this.maxHeight;
            if (var14 > var18) {
               return false;
            }
         }

         if (this.neededNearbyBlocks != null) {
            val var15: java.util.List = this.neededNearbyBlocks;
            val `$this$none$iv`: java.lang.Iterable = var15;
            var var17: Boolean;
            if (var15 is java.util.Collection && (var15 as java.util.Collection).isEmpty()) {
               var17 = true;
            } else {
               val var4: java.util.Iterator = `$this$none$iv`.iterator();

               while (true) {
                  if (!var4.hasNext()) {
                     var17 = true;
                     break;
                  }

                  val cond: RegistryLikeCondition = var4.next() as RegistryLikeCondition;
                  val `$this$any$iv`: java.lang.Iterable = ctx.getNearbyBlockTypes();
                  var var16: Boolean;
                  if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
                     var16 = false;
                  } else {
                     val var10: java.util.Iterator = `$this$any$iv`.iterator();

                     while (true) {
                        if (!var10.hasNext()) {
                           var16 = false;
                           break;
                        }

                        if (cond.fits(var10.next() as Block, ctx.getBlockRegistry())) {
                           var16 = true;
                           break;
                        }
                     }
                  }

                  if (var16) {
                     var17 = false;
                     break;
                  }
               }
            }

            if (var17) {
               return false;
            }
         }

         return true;
      }
   }

   public override fun copyFrom(other: SpawningCondition<*>, merger: Merger) {
      super.copyFrom(other, merger);
      if (other is AreaTypeSpawningCondition) {
         merger.mergeSingle(this.minHeight, (other as AreaTypeSpawningCondition).minHeight);
         merger.mergeSingle(this.maxHeight, (other as AreaTypeSpawningCondition).maxHeight);
         val var10001: java.util.Collection = merger.merge(this.neededNearbyBlocks, (other as AreaTypeSpawningCondition).neededNearbyBlocks);
         this.neededNearbyBlocks = if (var10001 != null) CollectionsKt.toMutableList(var10001) else null;
      }
   }
}
