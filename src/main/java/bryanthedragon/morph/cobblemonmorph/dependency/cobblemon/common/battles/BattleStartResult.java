package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle

public abstract class BattleStartResult {
   public open fun ifSuccessful(action: (PokemonBattle) -> Unit): BattleStartResult {
      return this;
   }

   public open fun ifErrored(action: (ErroredBattleStart) -> Unit): BattleStartResult {
      return this;
   }
}
