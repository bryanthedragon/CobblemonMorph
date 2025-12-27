package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemTagCondition
import net.minecraft.resources.ResourceLocation

public data NaturalMaterial(content: Int = 0, item: ResourceLocation?, tag: ItemTagCondition? = null, returnItem: ResourceLocation? = null) {
   public final val content: Int
   public final val item: ResourceLocation?
   public final val returnItem: ResourceLocation?
   public final val tag: ItemTagCondition?

   init {
      this.content = content;
      this.item = item;
      this.tag = tag;
      this.returnItem = returnItem;
   }

   public operator fun component1(): Int {
      return this.content;
   }

   public operator fun component2(): ResourceLocation? {
      return this.item;
   }

   public operator fun component3(): ItemTagCondition? {
      return this.tag;
   }

   public operator fun component4(): ResourceLocation? {
      return this.returnItem;
   }

   public fun copy(
      content: Int = this.content,
      item: ResourceLocation? = this.item,
      tag: ItemTagCondition? = this.tag,
      returnItem: ResourceLocation? = this.returnItem
   ): NaturalMaterial {
      return new NaturalMaterial(content, item, tag, returnItem);
   }

   public override fun toString(): String {
      return "NaturalMaterial(content=${this.content}, item=${this.item}, tag=${this.tag}, returnItem=${this.returnItem})";
   }

   public override fun hashCode(): Int {
      return (
               (Integer.hashCode(this.content) * 31 + (if (this.item == null) 0 else this.item.hashCode())) * 31
                  + (if (this.tag == null) 0 else this.tag.hashCode())
            )
            * 31
         + (if (this.returnItem == null) 0 else this.returnItem.hashCode());
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is NaturalMaterial) {
         return false;
      } else {
         val var2: NaturalMaterial = other as NaturalMaterial;
         if (this.content != (other as NaturalMaterial).content) {
            return false;
         } else if (!(this.item == var2.item)) {
            return false;
         } else if (!(this.tag == var2.tag)) {
            return false;
         } else {
            return this.returnItem == var2.returnItem;
         }
      }
   }
}
