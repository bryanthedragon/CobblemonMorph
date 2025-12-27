package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity

public class ThrownPokeballHitEvent(pokeBall: EmptyPokeBallEntity, pokemon: PokemonEntity) : Cancelable {
   public final val pokeBall: EmptyPokeBallEntity
   public final val pokemon: PokemonEntity

   init {
      this.pokeBall = pokeBall;
      this.pokemon = pokemon;
   }
}
