package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon

public fun interface ExperienceCalculator {
   public abstract fun calculate(battlePokemon: BattlePokemon, opponentPokemon: BattlePokemon, participationMultiplier: Double): Int {
   }
}
