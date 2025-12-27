package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier.Behavior
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.functions.Function2
import net.minecraft.world.entity.LivingEntity

public class MultiplierModifier(multiplier: Float, condition: (LivingEntity, Pokemon) -> Boolean = <unrepresentable>.INSTANCE as Function2) : CatchRateModifier {
   private final val condition: (LivingEntity, Pokemon) -> Boolean
   private final val multiplier: Float

   init {
      this.multiplier = multiplier;
      this.condition = condition;
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
      return this.condition.invoke(thrower, pokemon) as java.lang.Boolean;
   }

   public override fun modifyCatchRate(currentCatchRate: Float, thrower: LivingEntity, pokemon: Pokemon): Float {
      return if (this.isValid(thrower, pokemon))
         (this.behavior(thrower, pokemon).getMutator().invoke(currentCatchRate, this.value(thrower, pokemon)) as java.lang.Number).floatValue()
         else
         currentCatchRate;
   }
}
