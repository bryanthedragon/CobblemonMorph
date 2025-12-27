package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility

public class HiddenAbility(template: AbilityTemplate) : PotentialAbility {
   public open val priority: Priority
   public open val template: AbilityTemplate
   public open val type: HiddenAbilityType

   init {
      this.template = template;
      this.priority = Priority.LOW;
      this.type = HiddenAbilityType.INSTANCE;
   }

   public override fun isSatisfiedBy(aspects: Set<String>): Boolean {
      return false;
   }
}
