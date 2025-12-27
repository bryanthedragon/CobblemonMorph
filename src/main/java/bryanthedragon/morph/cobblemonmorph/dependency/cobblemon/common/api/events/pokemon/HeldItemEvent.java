package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.world.item.ItemStack

public interface HeldItemEvent {
   public val pokemon: Pokemon

   public data Post(pokemon: Pokemon, received: ItemStack, returned: ItemStack, decremented: Boolean) : HeldItemEvent {
      public final val decremented: Boolean
      public open val pokemon: Pokemon
      public final val received: ItemStack
      public final val returned: ItemStack

      init {
         this.pokemon = pokemon;
         this.received = received;
         this.returned = returned;
         this.decremented = decremented;
      }

      public operator fun component1(): Pokemon {
         return this.pokemon;
      }

      public operator fun component2(): ItemStack {
         return this.received;
      }

      public operator fun component3(): ItemStack {
         return this.returned;
      }

      public operator fun component4(): Boolean {
         return this.decremented;
      }

      public fun copy(
         pokemon: Pokemon = this.pokemon,
         received: ItemStack = this.received,
         returned: ItemStack = this.returned,
         decremented: Boolean = this.decremented
      ): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.HeldItemEvent.Post {
         return new HeldItemEvent.Post(pokemon, received, returned, decremented);
      }

      public override fun toString(): String {
         return "Post(pokemon=${this.pokemon}, received=${this.received}, returned=${this.returned}, decremented=${this.decremented})";
      }

      public override fun hashCode(): Int {
         val var10000: Int = ((this.pokemon.hashCode() * 31 + this.received.hashCode()) * 31 + this.returned.hashCode()) * 31;
         var var10001: Byte = this.decremented;
         if (this.decremented) {
            var10001 = 1;
         }

         return var10000 + var10001;
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is HeldItemEvent.Post) {
            return false;
         } else {
            val var2: HeldItemEvent.Post = other as HeldItemEvent.Post;
            if (!(this.pokemon == (other as HeldItemEvent.Post).pokemon)) {
               return false;
            } else if (!(this.received == var2.received)) {
               return false;
            } else if (!(this.returned == var2.returned)) {
               return false;
            } else {
               return this.decremented == var2.decremented;
            }
         }
      }
   }

   public data Pre(pokemon: Pokemon, receiving: ItemStack, returning: ItemStack, decrement: Boolean) : Cancelable, HeldItemEvent {
      public final var decrement: Boolean
      public open val pokemon: Pokemon
      public final var receiving: ItemStack
      public final var returning: ItemStack

      init {
         this.pokemon = pokemon;
         this.receiving = receiving;
         this.returning = returning;
         this.decrement = decrement;
      }

      public operator fun component1(): Pokemon {
         return this.pokemon;
      }

      public operator fun component2(): ItemStack {
         return this.receiving;
      }

      public operator fun component3(): ItemStack {
         return this.returning;
      }

      public operator fun component4(): Boolean {
         return this.decrement;
      }

      public fun copy(
         pokemon: Pokemon = this.pokemon,
         receiving: ItemStack = this.receiving,
         returning: ItemStack = this.returning,
         decrement: Boolean = this.decrement
      ): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.HeldItemEvent.Pre {
         return new HeldItemEvent.Pre(pokemon, receiving, returning, decrement);
      }

      public override fun toString(): String {
         return "Pre(pokemon=${this.pokemon}, receiving=${this.receiving}, returning=${this.returning}, decrement=${this.decrement})";
      }

      public override fun hashCode(): Int {
         val var10000: Int = ((this.pokemon.hashCode() * 31 + this.receiving.hashCode()) * 31 + this.returning.hashCode()) * 31;
         var var10001: Byte = this.decrement;
         if (this.decrement) {
            var10001 = 1;
         }

         return var10000 + var10001;
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is HeldItemEvent.Pre) {
            return false;
         } else {
            val var2: HeldItemEvent.Pre = other as HeldItemEvent.Pre;
            if (!(this.pokemon == (other as HeldItemEvent.Pre).pokemon)) {
               return false;
            } else if (!(this.receiving == var2.receiving)) {
               return false;
            } else if (!(this.returning == var2.returning)) {
               return false;
            } else {
               return this.decrement == var2.decrement;
            }
         }
      }
   }
}
