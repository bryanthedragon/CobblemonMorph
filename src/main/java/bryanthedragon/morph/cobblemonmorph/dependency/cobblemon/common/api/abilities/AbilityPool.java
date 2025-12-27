package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;

public open class AbilityPool : PrioritizedList<PotentialAbility> {
   public fun select(species: Species, aspects: Set<String>): Pair<Ability, Priority> {
      for (Priority priority : Priority.values()) {
         val var10000: java.util.List = this.getPriorityMap().get(priority);
         if (var10000 != null) {
            val `$this$filter$iv`: java.lang.Iterable = var10000;
            val `destination$iv$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : $this$filter$iv) {
               if ((`element$iv$iv` as PotentialAbility).isSatisfiedBy(aspects)) {
                  `destination$iv$iv`.add(`element$iv$iv`);
               }
            }

            val potentialAbilities: java.util.List = `destination$iv$iv` as java.util.List;
            if (!(`destination$iv$iv` as java.util.List).isEmpty()) {
               return TuplesKt.to(
                  AbilityTemplate.create$default(
                     (CollectionsKt.random(potentialAbilities, Random.Default as Random) as PotentialAbility).getTemplate(), false, 1, null
                  ),
                  priority
               );
            }
         }
      }

      Cobblemon.INSTANCE.getLOGGER().error("Unable to select an ability from the pool for $species and aspects: ${CollectionsKt.joinToString$default(aspects, null, null, null, 0, null, null, 63, null)}");
      Cobblemon.INSTANCE.getLOGGER().error("Usually this happens when a client is doing logic it shouldn't. Please show this to the Cobblemon developers!");
      new Exception().printStackTrace();
      return TuplesKt.to(AbilityTemplate.create$default(Abilities.INSTANCE.first(), false, 1, null), Priority.LOWEST);
   }
}
