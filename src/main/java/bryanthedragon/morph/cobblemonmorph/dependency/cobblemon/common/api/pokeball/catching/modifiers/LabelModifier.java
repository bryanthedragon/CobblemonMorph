package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier.Behavior
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Arrays
import net.minecraft.world.entity.LivingEntity

public class LabelModifier(multiplier: Float, matching: Boolean, vararg labels: String) : CatchRateModifier {
   public final val labels: Array<out String>
   public final val matching: Boolean
   public final val multiplier: Float

   init {
      this.multiplier = multiplier;
      this.matching = matching;
      this.labels = labels;
   }

   public override fun isGuaranteed(): Boolean {
      return false;
   }

   public override fun value(thrower: LivingEntity, pokemon: Pokemon): Float {
      return this.multiplier;
   }

   public override fun behavior(thrower: LivingEntity, pokemon: Pokemon): Behavior {
      return CatchRateModifier.Behavior.MULTIPLY;
   }

   public override fun isValid(thrower: LivingEntity, pokemon: Pokemon): Boolean {
      return if (this.matching)
         pokemon.hasLabels(Arrays.copyOf(this.labels, this.labels.length))
         else
         !pokemon.hasLabels(Arrays.copyOf(this.labels, this.labels.length));
   }

   public override fun modifyCatchRate(currentCatchRate: Float, thrower: LivingEntity, pokemon: Pokemon): Float {
      return (this.behavior(thrower, pokemon).getMutator().invoke(currentCatchRate, this.value(thrower, pokemon)) as java.lang.Number).floatValue();
   }
}
