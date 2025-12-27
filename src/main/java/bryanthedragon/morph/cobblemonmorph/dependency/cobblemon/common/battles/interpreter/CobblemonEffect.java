package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect.Type

internal data class CobblemonEffect(id: String, type: Type, rawData: String) : Effect {
   public open val id: String
   public open val rawData: String
   public open val type: Type

   init {
      this.id = id;
      this.type = type;
      this.rawData = rawData;
   }

   override fun getTypelessData(): java.lang.String {
      return Effect.DefaultImpls.getTypelessData(this);
   }

   public operator fun component1(): String {
      return this.id;
   }

   public operator fun component2(): Type {
      return this.type;
   }

   public operator fun component3(): String {
      return this.rawData;
   }

   public fun copy(id: String = this.id, type: Type = this.type, rawData: String = this.rawData): CobblemonEffect {
      return new CobblemonEffect(id, type, rawData);
   }

   public override fun toString(): String {
      return "CobblemonEffect(id=${this.id}, type=${this.type}, rawData=${this.rawData})";
   }

   public override fun hashCode(): Int {
      return (this.id.hashCode() * 31 + this.type.hashCode()) * 31 + this.rawData.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is CobblemonEffect) {
         return false;
      } else {
         val var2: CobblemonEffect = other as CobblemonEffect;
         if (!(this.id == (other as CobblemonEffect).id)) {
            return false;
         } else if (this.type != var2.type) {
            return false;
         } else {
            return this.rawData == var2.rawData;
         }
      }
   }
}
