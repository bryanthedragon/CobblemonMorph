package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon

public open class BattleExperienceSource(battle: PokemonBattle, facedPokemon: List<BattlePokemon>) : ExperienceSource {
   public final val battle: PokemonBattle
   public final val facedPokemon: List<BattlePokemon>

   init {
      this.battle = battle;
      this.facedPokemon = facedPokemon;
   }

   override fun isBattle(): Boolean {
      return ExperienceSource.DefaultImpls.isBattle(this);
   }

   override fun isInteraction(): Boolean {
      return ExperienceSource.DefaultImpls.isInteraction(this);
   }

   override fun isCommand(): Boolean {
      return ExperienceSource.DefaultImpls.isCommand(this);
   }

   override fun isSidemod(): Boolean {
      return ExperienceSource.DefaultImpls.isSidemod(this);
   }
}
