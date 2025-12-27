package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon

public object CobblemonEmptyHeldItemManager : BaseCobblemonHeldItemManager {
   public override fun handleStartInstruction(pokemon: BattlePokemon, battle: PokemonBattle, battleMessage: BattleMessage) {
   }

   public override fun handleEndInstruction(pokemon: BattlePokemon, battle: PokemonBattle, battleMessage: BattleMessage) {
   }
}
