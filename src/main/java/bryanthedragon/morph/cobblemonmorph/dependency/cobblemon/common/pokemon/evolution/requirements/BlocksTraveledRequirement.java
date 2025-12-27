package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public class BlocksTraveledRequirement(amount: Int) : EvolutionRequirement {
   public final val amount: Int

   init {
      this.amount = amount;
   }

   public constructor() : this(0)
   public override fun check(pokemon: Pokemon): Boolean {
      val var10000: PokemonEntity = pokemon.getEntity();
      if (var10000 == null) {
         return false;
      } else {
         return var10000.getBlocksTraveled() >= this.amount;
      }
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
