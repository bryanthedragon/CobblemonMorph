package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public class FriendshipRequirement : EvolutionRequirement {
   public final val amount: Int

   public override fun check(pokemon: Pokemon): Boolean {
      return pokemon.getFriendship() >= this.amount;
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
