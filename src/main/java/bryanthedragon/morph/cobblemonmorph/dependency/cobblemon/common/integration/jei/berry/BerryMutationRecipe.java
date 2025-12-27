package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.BerryItem

public data BerryMutationRecipe(berryOne: BerryItem, berryTwo: BerryItem, berryResult: BerryItem) {
   public final val berryOne: BerryItem
   public final val berryResult: BerryItem
   public final val berryTwo: BerryItem

   init {
      this.berryOne = berryOne;
      this.berryTwo = berryTwo;
      this.berryResult = berryResult;
   }

   public operator fun component1(): BerryItem {
      return this.berryOne;
   }

   public operator fun component2(): BerryItem {
      return this.berryTwo;
   }

   public operator fun component3(): BerryItem {
      return this.berryResult;
   }

   public fun copy(berryOne: BerryItem = this.berryOne, berryTwo: BerryItem = this.berryTwo, berryResult: BerryItem = this.berryResult): BerryMutationRecipe {
      return new BerryMutationRecipe(berryOne, berryTwo, berryResult);
   }

   public override fun toString(): String {
      return "BerryMutationRecipe(berryOne=${this.berryOne}, berryTwo=${this.berryTwo}, berryResult=${this.berryResult})";
   }

   public override fun hashCode(): Int {
      return (this.berryOne.hashCode() * 31 + this.berryTwo.hashCode()) * 31 + this.berryResult.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BerryMutationRecipe) {
         return false;
      } else {
         val var2: BerryMutationRecipe = other as BerryMutationRecipe;
         if (!(this.berryOne == (other as BerryMutationRecipe).berryOne)) {
            return false;
         } else if (!(this.berryTwo == var2.berryTwo)) {
            return false;
         } else {
            return this.berryResult == var2.berryResult;
         }
      }
   }
}
