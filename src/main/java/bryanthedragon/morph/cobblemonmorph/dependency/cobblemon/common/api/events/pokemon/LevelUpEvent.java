package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public class LevelUpEvent(pokemon: Pokemon, oldLevel: Int, newLevel: Int) {
   public final var newLevel: Int
   public final val oldLevel: Int
   public final val pokemon: Pokemon

   init {
      this.pokemon = pokemon;
      this.oldLevel = oldLevel;
      this.newLevel = newLevel;
   }
}
