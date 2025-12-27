package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SubmergedSpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Merger
import net.minecraft.world.level.material.Fluid

public abstract class SubmergedTypeSpawningCondition<T extends SubmergedSpawningContext> : AreaTypeSpawningCondition<T> {
   public final var fluid: RegistryLikeCondition<Fluid>?
   public final var fluidIsSource: Boolean?
   public final var maxDepth: Int?
   public final var minDepth: Int?

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
            val var8: Int = this.getMaxHeight();
            if (var10000 > var8) {
               return false;
            }
         }

         if (this.minDepth != null) {
            var10000 = ctx.getDepth();
            val var9: Int = this.minDepth;
            if (var10000 < var9) {
               return false;
            }
         }

         if (this.maxDepth != null) {
            var10000 = ctx.getDepth();
            val var10: Int = this.maxDepth;
            if (var10000 > var10) {
               return false;
            }
         }

         if (this.fluidIsSource != null) {
            val var6: Boolean = ctx.getFluid().m_76170_();
            val var11: java.lang.Boolean = this.fluidIsSource;
            if (var6 != var11) {
               return false;
            }
         }

         label64: {
            if (!ctx.getFluid().m_76178_()) {
               if (this.fluid == null) {
                  break label64;
               }

               val var7: RegistryLikeCondition = this.fluid;
               val var12: Fluid = ctx.getFluid().m_76152_();
               if (var7.fits(var12, ctx.getFluidRegistry())) {
                  break label64;
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
      if (other is SubmergedTypeSpawningCondition) {
         this.minDepth = merger.mergeSingle(this.minDepth, (other as SubmergedTypeSpawningCondition).minDepth);
         this.maxDepth = merger.mergeSingle(this.minDepth, (other as SubmergedTypeSpawningCondition).minDepth);
         this.fluidIsSource = merger.mergeSingle(this.fluidIsSource, (other as SubmergedTypeSpawningCondition).fluidIsSource);
         this.fluid = merger.mergeSingle(this.fluid, (other as SubmergedTypeSpawningCondition).fluid);
      }
   }
}
