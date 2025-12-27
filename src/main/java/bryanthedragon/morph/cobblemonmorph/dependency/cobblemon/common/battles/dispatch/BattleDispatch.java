package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle

public fun interface BattleDispatch {
   public abstract operator fun invoke(battle: PokemonBattle): DispatchResult {
   }
}
