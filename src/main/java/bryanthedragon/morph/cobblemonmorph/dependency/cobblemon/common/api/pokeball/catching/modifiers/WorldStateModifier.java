package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier.Behavior
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.world.entity.LivingEntity

public open class WorldStateModifier(calculator: (LivingEntity, PokemonEntity) -> Float) : CatchRateModifier {
   private final val calculator: (LivingEntity, PokemonEntity) -> Float

   init {
      this.calculator = calculator;
   }

   public override fun value(thrower: LivingEntity, pokemon: Pokemon): Float {
      val var10000: PokemonEntity = pokemon.getEntity();
      return if (var10000 == null) 1.0F else (this.calculator.invoke(thrower, var10000) as java.lang.Number).floatValue();
   }

   public override fun behavior(thrower: LivingEntity, pokemon: Pokemon): Behavior {
      return CatchRateModifier.Behavior.MULTIPLY;
   }

   public override fun isValid(thrower: LivingEntity, pokemon: Pokemon): Boolean {
      return true;
   }

   public override fun modifyCatchRate(currentCatchRate: Float, thrower: LivingEntity, pokemon: Pokemon): Float {
      return (this.behavior(thrower, pokemon).getMutator().invoke(currentCatchRate, this.value(thrower, pokemon)) as java.lang.Number).floatValue();
   }

   override fun isGuaranteed(): Boolean {
      return CatchRateModifier.DefaultImpls.isGuaranteed(this);
   }
}
