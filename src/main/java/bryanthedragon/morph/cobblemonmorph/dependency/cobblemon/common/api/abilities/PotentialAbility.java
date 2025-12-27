package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import java.util.ArrayList;

public interface PotentialAbility {
   public val priority: Priority
   public val template: AbilityTemplate
   public val type: PotentialAbilityType<*>

   public abstract fun isSatisfiedBy(aspects: Set<String>): Boolean {
   }

   public companion object {
      public final val types: MutableList<PotentialAbilityType<*>> = (new ArrayList()) as java.util.List
   }
}
