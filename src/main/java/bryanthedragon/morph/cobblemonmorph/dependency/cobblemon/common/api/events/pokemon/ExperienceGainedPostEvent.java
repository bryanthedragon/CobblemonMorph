package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceSource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public class ExperienceGainedPostEvent(pokemon: Pokemon,
   source: ExperienceSource,
   experience: Int,
   previousLevel: Int,
   currentLevel: Int,
   learnedMoves: MutableSet<MoveTemplate>
) {
   public final val currentLevel: Int
   public final val experience: Int
   public final val learnedMoves: MutableSet<MoveTemplate>
   public final val pokemon: Pokemon
   public final val previousLevel: Int
   public final val source: ExperienceSource

   init {
      this.pokemon = pokemon;
      this.source = source;
      this.experience = experience;
      this.previousLevel = previousLevel;
      this.currentLevel = currentLevel;
      this.learnedMoves = learnedMoves;
   }
}
