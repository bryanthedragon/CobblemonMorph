package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon

public data BattleFaintedEvent(battle: PokemonBattle, killed: BattlePokemon, context: BattleContext) : BattleEvent {
   public open val battle: PokemonBattle
   public final val context: BattleContext
   public final val killed: BattlePokemon

   init {
      this.battle = battle;
      this.killed = killed;
      this.context = context;
   }

   public operator fun component1(): PokemonBattle {
      return this.battle;
   }

   public operator fun component2(): BattlePokemon {
      return this.killed;
   }

   public operator fun component3(): BattleContext {
      return this.context;
   }

   public fun copy(battle: PokemonBattle = this.battle, killed: BattlePokemon = this.killed, context: BattleContext = this.context): BattleFaintedEvent {
      return new BattleFaintedEvent(battle, killed, context);
   }

   public override fun toString(): String {
      return "BattleFaintedEvent(battle=${this.battle}, killed=${this.killed}, context=${this.context})";
   }

   public override fun hashCode(): Int {
      return (this.battle.hashCode() * 31 + this.killed.hashCode()) * 31 + this.context.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BattleFaintedEvent) {
         return false;
      } else {
         val var2: BattleFaintedEvent = other as BattleFaintedEvent;
         if (!(this.battle == (other as BattleFaintedEvent).battle)) {
            return false;
         } else if (!(this.killed == var2.killed)) {
            return false;
         } else {
            return this.context == var2.context;
         }
      }
   }
}
