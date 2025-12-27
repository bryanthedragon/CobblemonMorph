package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType

public object HiddenAbilityPropertyType : CustomPokemonPropertyType<HiddenAbilityProperty> {
   public open val keys: Set<String> = SetsKt.setOf(new java.lang.String[]{"hiddenability", "ha"})
   public open val needsKey: Boolean = true

   public open fun fromString(value: String?): HiddenAbilityProperty {
      return new HiddenAbilityProperty();
   }

   public open fun examples(): Set<String> {
      return SetsKt.emptySet();
   }
}
