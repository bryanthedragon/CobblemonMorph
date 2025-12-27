package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public class AttackDefenceRatioRequirement : EvolutionRequirement {
   public final val ratio: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.AttackDefenceRatioRequirement.AttackDefenceRatio =
      AttackDefenceRatioRequirement.AttackDefenceRatio.ATTACK_HIGHER

   public override fun check(pokemon: Pokemon): Boolean {
      var var10000: Boolean;
      switch (AttackDefenceRatioRequirement.WhenMappings.$EnumSwitchMapping$0[this.ratio.ordinal()]) {
         case 1:
            var10000 = pokemon.getAttack() > pokemon.getDefence();
            break;
         case 2:
            var10000 = pokemon.getDefence() > pokemon.getAttack();
            break;
         case 3:
            var10000 = pokemon.getAttack() == pokemon.getDefence();
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      return var10000;
   }

   public enum AttackDefenceRatio {
      ATTACK_HIGHER,
      DEFENCE_HIGHER,
      EQUAL   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
