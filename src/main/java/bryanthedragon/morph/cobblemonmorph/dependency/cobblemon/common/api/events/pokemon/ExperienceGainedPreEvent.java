package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceSource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public class ExperienceGainedPreEvent(pokemon: Pokemon, source: ExperienceSource, experience: Int) : Cancelable {
   public final var experience: Int
   public final val pokemon: Pokemon
   public final val source: ExperienceSource

   init {
      this.pokemon = pokemon;
      this.source = source;
      this.experience = experience;
   }
}
