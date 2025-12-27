package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ability

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbilityType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability.AbilityChanger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random

@SourceDebugExtension(["SMAP\nAbilityTypeChanger.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AbilityTypeChanger.kt\ncom/cobblemon/mod/common/item/interactive/ability/AbilityTypeChanger\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,50:1\n766#2:51\n857#2,2:52\n766#2:54\n857#2,2:55\n1549#2:57\n1620#2,3:58\n288#2,2:61\n*S KotlinDebug\n*F\n+ 1 AbilityTypeChanger.kt\ncom/cobblemon/mod/common/item/interactive/ability/AbilityTypeChanger\n*L\n23#1:51\n23#1:52,2\n24#1:54\n24#1:55,2\n25#1:57\n25#1:58,3\n47#1:61,2\n*E\n"])
public open class AbilityTypeChanger<T extends PotentialAbility>(type: PotentialAbilityType<Any>, supportsChangingFrom: (PotentialAbilityType<*>?) -> Boolean) :
   AbilityChanger<T> {
   private final val supportsChangingFrom: (PotentialAbilityType<*>?) -> Boolean
   public open val type: PotentialAbilityType<Any>

   init {
      this.type = type;
      this.supportsChangingFrom = supportsChangingFrom;
   }

   public override fun queryPossible(pokemon: Pokemon): Set<AbilityTemplate> {
      var `$this$map$iv`: java.lang.Iterable = pokemon.getForm().getAbilities();
      var `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if ((`item$iv$iv` as PotentialAbility).getType() == this.getType()) {
            `destination$iv$iv`.add(`item$iv$iv`);
         }
      }

      `$this$map$iv` = `destination$iv$iv` as java.util.List;
      `destination$iv$iv` = new ArrayList();

      for (Object element$iv$ivx : $this$filter$iv) {
         if (!((`element$iv$ivx` as PotentialAbility).getTemplate() == pokemon.getAbility().getTemplate())) {
            `destination$iv$iv`.add(`element$iv$ivx`);
         }
      }

      `$this$map$iv` = `destination$iv$iv` as java.util.List;
      `destination$iv$iv` = new ArrayList(CollectionsKt.collectionSizeOrDefault(`destination$iv$iv` as java.util.List, 10));

      for (Object item$iv$iv : $this$filter$iv) {
         `destination$iv$iv`.add((var24 as PotentialAbility).getTemplate());
      }

      return CollectionsKt.toSet(`destination$iv$iv` as java.util.List);
   }

   public override fun performChange(pokemon: Pokemon): Boolean {
      if (!this.canChangeFrom(this.findCurrent(pokemon))) {
         return false;
      } else {
         val var10000: AbilityTemplate = CollectionsKt.randomOrNull(this.queryPossible(pokemon), Random.Default as Random) as AbilityTemplate;
         if (var10000 == null) {
            return false;
         } else {
            val old: AbilityTemplate = pokemon.getAbility().getTemplate();
            pokemon.updateAbility(var10000.create(false));
            return !(pokemon.getAbility().getTemplate() == old);
         }
      }
   }

   public override fun canChangeFrom(type: PotentialAbilityType<*>?): Boolean {
      return this.supportsChangingFrom.invoke(type) as java.lang.Boolean;
   }

   private fun findCurrent(pokemon: Pokemon): PotentialAbilityType<*>? {
      if (pokemon.getAbility().getForced()) {
         return null;
      } else {
         val var4: java.util.Iterator = pokemon.getForm().getAbilities().iterator();

         var var10000: Any;
         while (true) {
            if (var4.hasNext()) {
               val `element$iv`: Any = var4.next();
               if (!((`element$iv` as PotentialAbility).getTemplate() == pokemon.getAbility().getTemplate())) {
                  continue;
               }

               var10000 = `element$iv`;
               break;
            }

            var10000 = null;
            break;
         }

         return if (var10000 as PotentialAbility != null) (var10000 as PotentialAbility).getType() else null;
      }
   }
}
