package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle

public open class BattleCountableContext(times: Int, battle: PokemonBattle) : CountableContext(times) {
   public final var battle: PokemonBattle

   init {
      this.battle = battle;
   }
}
