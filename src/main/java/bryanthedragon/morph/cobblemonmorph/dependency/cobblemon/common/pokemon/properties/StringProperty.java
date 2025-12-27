package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public class StringProperty(key: String, value: String, applicator: (Pokemon, String) -> Unit, matcher: (Pokemon, String) -> Boolean) : CustomPokemonProperty {
   private final val applicator: (Pokemon, String) -> Unit
   public final val key: String
   private final val matcher: (Pokemon, String) -> Boolean
   public final val value: String

   init {
      this.key = key;
      this.value = value;
      this.applicator = applicator;
      this.matcher = matcher;
   }

   public override fun apply(pokemon: Pokemon) {
      this.applicator.invoke(pokemon, this.value);
   }

   public override fun matches(pokemon: Pokemon): Boolean {
      return this.matcher.invoke(pokemon, this.value) as java.lang.Boolean;
   }

   public override fun asString(): String {
      return "${this.key}=${this.value}";
   }

   override fun apply(pokemonEntity: PokemonEntity) {
      CustomPokemonProperty.DefaultImpls.apply(this, pokemonEntity);
   }

   override fun matches(pokemonEntity: PokemonEntity): Boolean {
      return CustomPokemonProperty.DefaultImpls.matches(this, pokemonEntity);
   }
}
