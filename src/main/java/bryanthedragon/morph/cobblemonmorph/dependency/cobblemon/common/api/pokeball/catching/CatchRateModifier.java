package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.world.entity.LivingEntity
import org.jetbrains.annotations.NotNull

public interface CatchRateModifier {
   public open fun isGuaranteed(): Boolean {
   }

   public abstract fun value(thrower: LivingEntity, pokemon: Pokemon): Float {
   }

   public abstract fun behavior(thrower: LivingEntity, pokemon: Pokemon): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier.Behavior {
   }

   public abstract fun isValid(thrower: LivingEntity, pokemon: Pokemon): Boolean {
   }

   public abstract fun modifyCatchRate(currentCatchRate: Float, thrower: LivingEntity, pokemon: Pokemon): Float {
   }

   public enum Behavior(mutator: (Float, Float) -> Float) {
      ADD(<unrepresentable>.INSTANCE),
      SUBTRACT(<unrepresentable>.INSTANCE),
      MULTIPLY(<unrepresentable>.INSTANCE),
      DIVIDE(<unrepresentable>.INSTANCE)
      public final val mutator: (Float, Float) -> Float

      init {
         this.mutator = mutator;
      }
   }

   public companion object {
      internal final val DUMMY: CatchRateModifier = (new CatchRateModifier() {
         @Override
         public float value(@NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
            return 1.0F;
         }

         @NotNull
         @Override
         public CatchRateModifier.Behavior behavior(@NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
            return CatchRateModifier.Behavior.ADD;
         }

         @Override
         public boolean isValid(@NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
            return false;
         }

         @Override
         public float modifyCatchRate(float currentCatchRate, @NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
            return 1.0F;
         }

         @Override
         public boolean isGuaranteed() {
            return CatchRateModifier.DefaultImpls.isGuaranteed(this);
         }
      }) as CatchRateModifier
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun isGuaranteed(`$this`: CatchRateModifier): Boolean {
         return false;
      }
   }
}
