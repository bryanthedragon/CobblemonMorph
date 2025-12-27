package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle

public data BattleStartedPostEvent(battle: PokemonBattle) : BattleEvent {
   public open val battle: PokemonBattle

   init {
      this.battle = battle;
   }

   public operator fun component1(): PokemonBattle {
      return this.battle;
   }

   public fun copy(battle: PokemonBattle = this.battle): BattleStartedPostEvent {
      return new BattleStartedPostEvent(battle);
   }

   public override fun toString(): String {
      return "BattleStartedPostEvent(battle=${this.battle})";
   }

   public override fun hashCode(): Int {
      return this.battle.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BattleStartedPostEvent) {
         return false;
      } else {
         return this.battle == (other as BattleStartedPostEvent).battle;
      }
   }
}
