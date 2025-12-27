package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.predicate

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import net.minecraft.advancements.critereon.NbtPredicate
import net.minecraft.world.item.Item

public data NbtItemPredicate(item: RegistryLikeCondition<Item>, nbt: NbtPredicate = NbtPredicate.f_57471_) {
   public final val item: RegistryLikeCondition<Item>
   public final val nbt: NbtPredicate

   init {
      this.item = item;
      this.nbt = nbt;
   }

   public operator fun component1(): RegistryLikeCondition<Item> {
      return this.item;
   }

   public operator fun component2(): NbtPredicate {
      return this.nbt;
   }

   public fun copy(item: RegistryLikeCondition<Item> = this.item, nbt: NbtPredicate = this.nbt): NbtItemPredicate {
      return new NbtItemPredicate(item, nbt);
   }

   public override fun toString(): String {
      return "NbtItemPredicate(item=${this.item}, nbt=${this.nbt})";
   }

   public override fun hashCode(): Int {
      return this.item.hashCode() * 31 + this.nbt.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is NbtItemPredicate) {
         return false;
      } else {
         val var2: NbtItemPredicate = other as NbtItemPredicate;
         if (!(this.item == (other as NbtItemPredicate).item)) {
            return false;
         } else {
            return this.nbt == var2.nbt;
         }
      }
   }
}
