package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities

import com.google.gson.JsonElement

public object CommonAbilityType : PotentialAbilityType<CommonAbility> {
   public open fun parseFromJSON(element: JsonElement): CommonAbility? {
      val str: java.lang.String = if (element.isJsonPrimitive()) element.getAsString() else null;
      val var10000: CommonAbility;
      if (str != null) {
         val ability: AbilityTemplate = Abilities.INSTANCE.get(str);
         var10000 = if (ability != null) new CommonAbility(ability) else null;
      } else {
         var10000 = null;
      }

      return var10000;
   }
}
