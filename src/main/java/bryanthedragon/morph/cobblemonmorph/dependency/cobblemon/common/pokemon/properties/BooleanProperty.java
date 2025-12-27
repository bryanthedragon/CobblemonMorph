package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public open class BooleanProperty(key: String,
      value: Boolean,
      pokemonApplicator: (Pokemon, Boolean) -> Unit,
      entityApplicator: (PokemonEntity, Boolean) -> Unit,
      pokemonMatcher: (Pokemon, Boolean) -> Boolean,
      entityMatcher: (PokemonEntity, Boolean) -> Boolean
   ) :
   CustomPokemonProperty {
   private final val entityApplicator: (PokemonEntity, Boolean) -> Unit
   private final val entityMatcher: (PokemonEntity, Boolean) -> Boolean
   public final val key: String
   private final val pokemonApplicator: (Pokemon, Boolean) -> Unit
   private final val pokemonMatcher: (Pokemon, Boolean) -> Boolean
   public final val value: Boolean

   init {
      this.key = key;
      this.value = value;
      this.pokemonApplicator = pokemonApplicator;
      this.entityApplicator = entityApplicator;
      this.pokemonMatcher = pokemonMatcher;
      this.entityMatcher = entityMatcher;
   }

   public override fun asString(): String {
      return "${this.key}=${this.value}";
   }

   public override fun apply(pokemon: Pokemon) {
      this.pokemonApplicator.invoke(pokemon, this.value);
   }

   public override fun apply(pokemonEntity: PokemonEntity) {
      this.entityApplicator.invoke(pokemonEntity, this.value);
   }

   public override fun matches(pokemon: Pokemon): Boolean {
      return this.pokemonMatcher.invoke(pokemon, this.value) as java.lang.Boolean;
   }

   public override fun matches(pokemonEntity: PokemonEntity): Boolean {
      return this.entityMatcher.invoke(pokemonEntity, this.value) as java.lang.Boolean;
   }
}
