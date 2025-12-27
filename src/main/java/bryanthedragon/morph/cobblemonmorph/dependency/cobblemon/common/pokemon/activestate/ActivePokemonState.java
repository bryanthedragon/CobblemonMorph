package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity

public sealed class ActivePokemonState protected constructor() : PokemonState() {
   public abstract val entity: PokemonEntity?

   public abstract fun recall() {
   }
}
