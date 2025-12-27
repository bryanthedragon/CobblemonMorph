package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public open class IntProperty(key: String,
      value: Int,
      pokemonApplicator: (Pokemon, Int) -> Unit,
      entityApplicator: (PokemonEntity, Int) -> Unit,
      pokemonMatcher: (Pokemon, Int) -> Boolean,
      entityMatcher: (PokemonEntity, Int) -> Boolean
   ) :
   CustomPokemonProperty {
   private final val entityApplicator: (PokemonEntity, Int) -> Unit
   private final val entityMatcher: (PokemonEntity, Int) -> Boolean
   public final val key: String
   private final val pokemonApplicator: (Pokemon, Int) -> Unit
   private final val pokemonMatcher: (Pokemon, Int) -> Boolean
   public final val value: Int

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
