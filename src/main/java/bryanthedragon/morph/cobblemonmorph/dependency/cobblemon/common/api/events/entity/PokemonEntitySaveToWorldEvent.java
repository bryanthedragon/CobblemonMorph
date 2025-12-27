package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity

public class PokemonEntitySaveToWorldEvent(pokemonEntity: PokemonEntity) : Cancelable {
   public final val pokemonEntity: PokemonEntity

   init {
      this.pokemonEntity = pokemonEntity;
   }
}
