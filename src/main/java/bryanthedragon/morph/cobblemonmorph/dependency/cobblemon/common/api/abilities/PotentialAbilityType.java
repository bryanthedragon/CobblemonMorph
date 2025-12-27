package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities

import com.google.gson.JsonElement

public interface PotentialAbilityType<T extends PotentialAbility> {
   public abstract fun parseFromJSON(element: JsonElement): Any? {
   }
}
