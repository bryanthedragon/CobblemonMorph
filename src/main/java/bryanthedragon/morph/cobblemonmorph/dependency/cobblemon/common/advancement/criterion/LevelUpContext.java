package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public open class LevelUpContext(level: Int, pokemon: Pokemon) {
   public final var level: Int
   public final var pokemon: Pokemon

   init {
      this.level = level;
      this.pokemon = pokemon;
   }
}
