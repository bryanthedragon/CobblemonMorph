package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate

public data AddExperienceResult(oldLevel: Int, newLevel: Int, newMoves: Set<MoveTemplate>, experienceAdded: Int) {
   public final val experienceAdded: Int
   public final val newLevel: Int
   public final val newMoves: Set<MoveTemplate>
   public final val oldLevel: Int

   init {
      this.oldLevel = oldLevel;
      this.newLevel = newLevel;
      this.newMoves = newMoves;
      this.experienceAdded = experienceAdded;
   }

   public operator fun component1(): Int {
      return this.oldLevel;
   }

   public operator fun component2(): Int {
      return this.newLevel;
   }

   public operator fun component3(): Set<MoveTemplate> {
      return this.newMoves;
   }

   public operator fun component4(): Int {
      return this.experienceAdded;
   }

   public fun copy(
      oldLevel: Int = this.oldLevel,
      newLevel: Int = this.newLevel,
      newMoves: Set<MoveTemplate> = this.newMoves,
      experienceAdded: Int = this.experienceAdded
   ): AddExperienceResult {
      return new AddExperienceResult(oldLevel, newLevel, newMoves, experienceAdded);
   }

   public override fun toString(): String {
      return "AddExperienceResult(oldLevel=${this.oldLevel}, newLevel=${this.newLevel}, newMoves=${this.newMoves}, experienceAdded=${this.experienceAdded})";
   }

   public override fun hashCode(): Int {
      return ((Integer.hashCode(this.oldLevel) * 31 + Integer.hashCode(this.newLevel)) * 31 + this.newMoves.hashCode()) * 31
         + Integer.hashCode(this.experienceAdded);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is AddExperienceResult) {
         return false;
      } else {
         val var2: AddExperienceResult = other as AddExperienceResult;
         if (this.oldLevel != (other as AddExperienceResult).oldLevel) {
            return false;
         } else if (this.newLevel != var2.newLevel) {
            return false;
         } else if (!(this.newMoves == var2.newMoves)) {
            return false;
         } else {
            return this.experienceAdded == var2.experienceAdded;
         }
      }
   }
}
