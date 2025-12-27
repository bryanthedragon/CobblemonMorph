package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle

public class SuccessfulBattleStart(battle: PokemonBattle) : BattleStartResult {
   public final val battle: PokemonBattle

   init {
      this.battle = battle;
   }

   public override fun ifSuccessful(action: (PokemonBattle) -> Unit): BattleStartResult {
      action.invoke(this.battle);
      return this;
   }
}
