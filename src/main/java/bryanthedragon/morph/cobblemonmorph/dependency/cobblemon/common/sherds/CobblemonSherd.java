package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.sherds

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

public data CobblemonSherd(patternId: ResourceLocation, item: Item) {
   public final val item: Item
   public final val patternId: ResourceLocation

   init {
      this.patternId = patternId;
      this.item = item;
   }

   public operator fun component1(): ResourceLocation {
      return this.patternId;
   }

   public operator fun component2(): Item {
      return this.item;
   }

   public fun copy(patternId: ResourceLocation = this.patternId, item: Item = this.item): CobblemonSherd {
      return new CobblemonSherd(patternId, item);
   }

   public override fun toString(): String {
      return "CobblemonSherd(patternId=${this.patternId}, item=${this.item})";
   }

   public override fun hashCode(): Int {
      return this.patternId.hashCode() * 31 + this.item.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is CobblemonSherd) {
         return false;
      } else {
         val var2: CobblemonSherd = other as CobblemonSherd;
         if (!(this.patternId == (other as CobblemonSherd).patternId)) {
            return false;
         } else {
            return this.item == var2.item;
         }
      }
   }
}
