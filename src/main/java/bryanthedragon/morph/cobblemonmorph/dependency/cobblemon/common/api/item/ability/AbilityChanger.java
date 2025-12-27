package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.CommonAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.CommonAbilityType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbilityType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ability.AbilityTypeChanger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbilityType

public interface AbilityChanger<T extends PotentialAbility> {
   public val type: PotentialAbilityType<Any>

   public abstract fun queryPossible(pokemon: Pokemon): Set<AbilityTemplate> {
   }

   public abstract fun performChange(pokemon: Pokemon): Boolean {
   }

   public abstract fun canChangeFrom(type: PotentialAbilityType<*>?): Boolean {
   }

   public companion object {
      @JvmStatic
      public final val COMMON_ABILITY: AbilityChanger<CommonAbility> =
         (new AbilityTypeChanger(CommonAbilityType.INSTANCE, <unrepresentable>.INSTANCE)) as AbilityChanger

      @JvmStatic
      public final val HIDDEN_ABILITY: AbilityChanger<HiddenAbility> =
         (new AbilityTypeChanger(HiddenAbilityType.INSTANCE, <unrepresentable>.INSTANCE)) as AbilityChanger
      }
}
