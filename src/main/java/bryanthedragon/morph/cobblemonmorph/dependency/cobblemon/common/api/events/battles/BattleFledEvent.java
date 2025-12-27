package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor

public data BattleFledEvent(battle: PokemonBattle, player: PlayerBattleActor) : BattleEvent {
   public open val battle: PokemonBattle
   public final val player: PlayerBattleActor

   init {
      this.battle = battle;
      this.player = player;
   }

   public operator fun component1(): PokemonBattle {
      return this.battle;
   }

   public operator fun component2(): PlayerBattleActor {
      return this.player;
   }

   public fun copy(battle: PokemonBattle = this.battle, player: PlayerBattleActor = this.player): BattleFledEvent {
      return new BattleFledEvent(battle, player);
   }

   public override fun toString(): String {
      return "BattleFledEvent(battle=${this.battle}, player=${this.player})";
   }

   public override fun hashCode(): Int {
      return this.battle.hashCode() * 31 + this.player.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BattleFledEvent) {
         return false;
      } else {
         val var2: BattleFledEvent = other as BattleFledEvent;
         if (!(this.battle == (other as BattleFledEvent).battle)) {
            return false;
         } else {
            return this.player == var2.player;
         }
      }
   }
}
