package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.tags

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.StringProperty

public object PokemonFlagProperty : CustomPokemonPropertyType<StringProperty> {
   private const val KEY: String = "tag"
   public open val keys: Set<String> = SetsKt.setOf("tag")
   public open val needsKey: Boolean = true

   public open fun fromString(value: String?): StringProperty? {
      return if (value == null) null else new StringProperty("tag", value, <unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE);
   }

   public open fun examples(): Set<String> {
      return SetsKt.emptySet();
   }
}
