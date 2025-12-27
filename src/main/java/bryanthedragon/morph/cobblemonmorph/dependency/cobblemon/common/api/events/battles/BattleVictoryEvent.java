package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor

public data BattleVictoryEvent(battle: PokemonBattle, winners: List<BattleActor>, losers: List<BattleActor>, wasWildCapture: Boolean) : BattleEvent {
   public open val battle: PokemonBattle
   public final val losers: List<BattleActor>
   public final val wasWildCapture: Boolean
   public final val winners: List<BattleActor>

   init {
      this.battle = battle;
      this.winners = winners;
      this.losers = losers;
      this.wasWildCapture = wasWildCapture;
   }

   public operator fun component1(): PokemonBattle {
      return this.battle;
   }

   public operator fun component2(): List<BattleActor> {
      return this.winners;
   }

   public operator fun component3(): List<BattleActor> {
      return this.losers;
   }

   public operator fun component4(): Boolean {
      return this.wasWildCapture;
   }

   public fun copy(
      battle: PokemonBattle = this.battle,
      winners: List<BattleActor> = this.winners,
      losers: List<BattleActor> = this.losers,
      wasWildCapture: Boolean = this.wasWildCapture
   ): BattleVictoryEvent {
      return new BattleVictoryEvent(battle, winners, losers, wasWildCapture);
   }

   public override fun toString(): String {
      return "BattleVictoryEvent(battle=${this.battle}, winners=${this.winners}, losers=${this.losers}, wasWildCapture=${this.wasWildCapture})";
   }

   public override fun hashCode(): Int {
      val var10000: Int = ((this.battle.hashCode() * 31 + this.winners.hashCode()) * 31 + this.losers.hashCode()) * 31;
      var var10001: Byte = this.wasWildCapture;
      if (this.wasWildCapture) {
         var10001 = 1;
      }

      return var10000 + var10001;
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BattleVictoryEvent) {
         return false;
      } else {
         val var2: BattleVictoryEvent = other as BattleVictoryEvent;
         if (!(this.battle == (other as BattleVictoryEvent).battle)) {
            return false;
         } else if (!(this.winners == var2.winners)) {
            return false;
         } else if (!(this.losers == var2.losers)) {
            return false;
         } else {
            return this.wasWildCapture == var2.wasWildCapture;
         }
      }
   }
}
