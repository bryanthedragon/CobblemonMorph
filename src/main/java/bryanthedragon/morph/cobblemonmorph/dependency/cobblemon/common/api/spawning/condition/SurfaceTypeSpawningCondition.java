package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SurfaceSpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Merger
import net.minecraft.world.level.material.Fluid

public abstract class SurfaceTypeSpawningCondition<T extends SurfaceSpawningContext> : AreaTypeSpawningCondition<T> {
   public final var fluid: RegistryLikeCondition<Fluid>?
   public final var maxDepth: Int?
   public final var minDepth: Int?

   protected open fun fits(ctx: Any): Boolean {
      var var10000: Boolean;
      if (!super.fits((T)ctx)) {
         var10000 = 0;
      } else {
         if (this.minDepth != null) {
            var10000 = ctx.getDepth();
            val var10001: Int = this.minDepth;
            if (var10000 < var10001) {
               return false;
            }
         }

         if (this.maxDepth != null) {
            var10000 = ctx.getDepth();
            val var5: Int = this.maxDepth;
            if (var10000 > var5) {
               return false;
            }
         }

         label43: {
            if (!ctx.getBaseBlock().m_60819_().m_76178_()) {
               if (this.fluid == null) {
                  break label43;
               }

               val var4: RegistryLikeCondition = this.fluid;
               val var6: Fluid = ctx.getBaseBlock().m_60819_().m_76152_();
               if (var4.fits(var6, ctx.getFluidRegistry())) {
                  break label43;
               }
            }

            return false;
         }

         var10000 = 1;
      }

      return (boolean)var10000;
   }

   public override fun copyFrom(other: SpawningCondition<*>, merger: Merger) {
      super.copyFrom(other, merger);
      if (other is SurfaceTypeSpawningCondition) {
         this.minDepth = merger.mergeSingle(this.minDepth, (other as SurfaceTypeSpawningCondition).minDepth);
         this.maxDepth = merger.mergeSingle(this.minDepth, (other as SurfaceTypeSpawningCondition).minDepth);
         this.fluid = merger.mergeSingle(this.fluid, (other as SurfaceTypeSpawningCondition).fluid);
      }
   }
}
