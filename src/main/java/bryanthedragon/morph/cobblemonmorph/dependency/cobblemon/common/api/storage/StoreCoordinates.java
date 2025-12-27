package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.nbt.CompoundTag

public data StoreCoordinates<T extends StorePosition>(store: PokemonStore<Any>, position: Any) {
   public final val position: Any
   public final val store: PokemonStore<Any>

   init {
      this.store = store;
      this.position = (T)position;
   }

   public fun saveToNBT(nbt: CompoundTag) {
      this.store.savePositionToNBT(this.position, nbt);
   }

   public fun get(): Pokemon? {
      return this.store.get(this.position);
   }

   public fun remove(): Boolean {
      return this.store.remove(this.position);
   }

   public operator fun component1(): PokemonStore<Any> {
      return this.store;
   }

   public operator fun component2(): Any {
      return this.position;
   }

   public fun copy(store: PokemonStore<Any> = ..., position: Any = ...): StoreCoordinates<Any> {
      return new StoreCoordinates<>(store, (T)position);
   }

   public override fun toString(): String {
      return "StoreCoordinates(store=${this.store}, position=${this.position})";
   }

   public override fun hashCode(): Int {
      return this.store.hashCode() * 31 + this.position.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is StoreCoordinates) {
         return false;
      } else {
         val var2: StoreCoordinates = other as StoreCoordinates;
         if (!(this.store == (other as StoreCoordinates).store)) {
            return false;
         } else {
            return this.position == var2.position;
         }
      }
   }
}
