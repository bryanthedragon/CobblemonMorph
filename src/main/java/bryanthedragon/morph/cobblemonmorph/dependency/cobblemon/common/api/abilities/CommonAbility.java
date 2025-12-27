package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority

public open class CommonAbility(template: AbilityTemplate) : PotentialAbility {
   public open val priority: Priority
   public open val template: AbilityTemplate
   public open val type: CommonAbilityType

   init {
      this.template = template;
      this.priority = Priority.LOWEST;
      this.type = CommonAbilityType.INSTANCE;
   }

   public override fun isSatisfiedBy(aspects: Set<String>): Boolean {
      return true;
   }
}
