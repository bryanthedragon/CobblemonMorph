package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack

public data HeldItemUpdatedEvent(cause: LivingEntity?,
      pokemon: Pokemon,
      originalStack: ItemStack,
      decrement: Boolean,
      oldItem: ItemStack,
      newItem: ItemStack
   )
   : Cancelable {
   public final val cause: LivingEntity?
   public final val decrement: Boolean
   public final val newItem: ItemStack
   public final val oldItem: ItemStack
   public final val originalStack: ItemStack
   public final val pokemon: Pokemon

   init {
      this.cause = cause;
      this.pokemon = pokemon;
      this.originalStack = originalStack;
      this.decrement = decrement;
      this.oldItem = oldItem;
      this.newItem = newItem;
   }

   public operator fun component1(): LivingEntity? {
      return this.cause;
   }

   public operator fun component2(): Pokemon {
      return this.pokemon;
   }

   public operator fun component3(): ItemStack {
      return this.originalStack;
   }

   public operator fun component4(): Boolean {
      return this.decrement;
   }

   public operator fun component5(): ItemStack {
      return this.oldItem;
   }

   public operator fun component6(): ItemStack {
      return this.newItem;
   }

   public fun copy(
      cause: LivingEntity? = this.cause,
      pokemon: Pokemon = this.pokemon,
      originalStack: ItemStack = this.originalStack,
      decrement: Boolean = this.decrement,
      oldItem: ItemStack = this.oldItem,
      newItem: ItemStack = this.newItem
   ): HeldItemUpdatedEvent {
      return new HeldItemUpdatedEvent(cause, pokemon, originalStack, decrement, oldItem, newItem);
   }

   public override fun toString(): String {
      return "HeldItemUpdatedEvent(cause=${this.cause}, pokemon=${this.pokemon}, originalStack=${this.originalStack}, decrement=${this.decrement}, oldItem=${this.oldItem}, newItem=${this.newItem})";
   }

   public override fun hashCode(): Int {
      val var10000: Int = (((if (this.cause == null) 0 else this.cause.hashCode()) * 31 + this.pokemon.hashCode()) * 31 + this.originalStack.hashCode()) * 31;
      var var10001: Byte = this.decrement;
      if (this.decrement) {
         var10001 = 1;
      }

      return ((var10000 + var10001) * 31 + this.oldItem.hashCode()) * 31 + this.newItem.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is HeldItemUpdatedEvent) {
         return false;
      } else {
         val var2: HeldItemUpdatedEvent = other as HeldItemUpdatedEvent;
         if (!(this.cause == (other as HeldItemUpdatedEvent).cause)) {
            return false;
         } else if (!(this.pokemon == var2.pokemon)) {
            return false;
         } else if (!(this.originalStack == var2.originalStack)) {
            return false;
         } else if (this.decrement != var2.decrement) {
            return false;
         } else if (!(this.oldItem == var2.oldItem)) {
            return false;
         } else {
            return this.newItem == var2.newItem;
         }
      }
   }
}
