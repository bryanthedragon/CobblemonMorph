package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.Locale

public object UncatchableProperty : CustomPokemonPropertyType<FlagProperty> {
   public open val keys: Set<String> = SetsKt.setOf("uncatchable")
   public open val needsKey: Boolean = true

   public open fun fromString(value: String?): FlagProperty? {
      if (value != null) {
         var var10000: java.util.List = CollectionsKt.listOf(new java.lang.String[]{"true", "yes"});
         var var10001: java.lang.String = value.toLowerCase(Locale.ROOT);
         if (!var10000.contains(var10001)) {
            var10000 = CollectionsKt.listOf(new java.lang.String[]{"false", "no"});
            var10001 = value.toLowerCase(Locale.ROOT);
            return if (var10000.contains(var10001)) this.catchable() else null;
         }
      }

      return this.uncatchable();
   }

   public fun catchable(): FlagProperty {
      return new FlagProperty(CollectionsKt.first(this.getKeys()) as java.lang.String, true);
   }

   public fun uncatchable(): FlagProperty {
      return new FlagProperty(CollectionsKt.first(this.getKeys()) as java.lang.String, false);
   }

   public fun isCatchable(pokemonEntity: PokemonEntity): Boolean {
      return !PokemonProperties.Companion.parse$default(
            PokemonProperties.Companion, CollectionsKt.first(this.getKeys()) as java.lang.String, null, null, 6, null
         )
         .matches(pokemonEntity);
   }

   public open fun examples(): Set<String> {
      return SetsKt.setOf(new java.lang.String[]{"yes", "no"});
   }
}
