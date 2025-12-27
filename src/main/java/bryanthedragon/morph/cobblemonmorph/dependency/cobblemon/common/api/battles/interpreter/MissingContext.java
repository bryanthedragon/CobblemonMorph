package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext.Type
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon

public data MissingContext(id: String = "error", turn: Int = 0, type: Type = BattleContext.Type.MISC, origin: BattlePokemon? = null) : BattleContext {
   public open val id: String
   public open val origin: BattlePokemon?
   public open val turn: Int
   public open val type: Type

   init {
      this.id = id;
      this.turn = turn;
      this.type = type;
      this.origin = origin;
   }

   public operator fun component1(): String {
      return this.id;
   }

   public operator fun component2(): Int {
      return this.turn;
   }

   public operator fun component3(): Type {
      return this.type;
   }

   public operator fun component4(): BattlePokemon? {
      return this.origin;
   }

   public fun copy(id: String = this.id, turn: Int = this.turn, type: Type = this.type, origin: BattlePokemon? = this.origin): MissingContext {
      return new MissingContext(id, turn, type, origin);
   }

   public override fun toString(): String {
      return "MissingContext(id=${this.id}, turn=${this.turn}, type=${this.type}, origin=${this.origin})";
   }

   public override fun hashCode(): Int {
      return ((this.id.hashCode() * 31 + Integer.hashCode(this.turn)) * 31 + this.type.hashCode()) * 31
         + (if (this.origin == null) 0 else this.origin.hashCode());
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is MissingContext) {
         return false;
      } else {
         val var2: MissingContext = other as MissingContext;
         if (!(this.id == (other as MissingContext).id)) {
            return false;
         } else if (this.turn != var2.turn) {
            return false;
         } else if (this.type != var2.type) {
            return false;
         } else {
            return this.origin == var2.origin;
         }
      }
   }

   fun MissingContext() {
      this(null, 0, null, null, 15, null);
   }
}
