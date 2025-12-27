package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbilityType
import com.google.gson.JsonElement

public object HiddenAbilityType : PotentialAbilityType<HiddenAbility> {
   public open fun parseFromJSON(element: JsonElement): HiddenAbility? {
      val str: java.lang.String = if (element.isJsonPrimitive()) element.getAsString() else null;
      val var10000: HiddenAbility;
      if (str != null && StringsKt.startsWith$default(str, "h:", false, 2, null)) {
         val abilityString: java.lang.String = StringsKt.substringAfter$default(str, "h:", null, 2, null);
         val ability: AbilityTemplate = Abilities.INSTANCE.get(abilityString);
         if (ability != null) {
            var10000 = new HiddenAbility(ability);
         } else {
            Cobblemon.INSTANCE.getLOGGER().error("Hidden ability referred to unknown ability: $abilityString");
            var10000 = null;
         }
      } else {
         var10000 = null;
      }

      return var10000;
   }
}
