package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import net.minecraft.network.chat.MutableComponent

public data BattleStartedPreEvent(battle: PokemonBattle, reason: MutableComponent? = null) : Cancelable, BattleEvent {
   public open val battle: PokemonBattle
   public final var reason: MutableComponent?

   init {
      this.battle = battle;
      this.reason = reason;
   }

   public operator fun component1(): PokemonBattle {
      return this.battle;
   }

   public operator fun component2(): MutableComponent? {
      return this.reason;
   }

   public fun copy(battle: PokemonBattle = this.battle, reason: MutableComponent? = this.reason): BattleStartedPreEvent {
      return new BattleStartedPreEvent(battle, reason);
   }

   public override fun toString(): String {
      return "BattleStartedPreEvent(battle=${this.battle}, reason=${this.reason})";
   }

   public override fun hashCode(): Int {
      return this.battle.hashCode() * 31 + (if (this.reason == null) 0 else this.reason.hashCode());
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BattleStartedPreEvent) {
         return false;
      } else {
         val var2: BattleStartedPreEvent = other as BattleStartedPreEvent;
         if (!(this.battle == (other as BattleStartedPreEvent).battle)) {
            return false;
         } else {
            return this.reason == var2.reason;
         }
      }
   }
}
