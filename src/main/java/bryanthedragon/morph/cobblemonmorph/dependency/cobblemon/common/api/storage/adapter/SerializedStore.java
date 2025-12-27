package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import java.util.UUID

public data SerializedStore<S>(storeClass: Class<out PokemonStore<*>>, uuid: UUID, serializedForm: Any) {
   public final val serializedForm: Any
   public final val storeClass: Class<out PokemonStore<*>>
   public final val uuid: UUID

   init {
      this.storeClass = storeClass;
      this.uuid = uuid;
      this.serializedForm = (S)serializedForm;
   }

   public operator fun component1(): Class<out PokemonStore<*>> {
      return this.storeClass;
   }

   public operator fun component2(): UUID {
      return this.uuid;
   }

   public operator fun component3(): Any {
      return this.serializedForm;
   }

   public fun copy(storeClass: Class<out PokemonStore<*>> = this.storeClass, uuid: UUID = this.uuid, serializedForm: Any = this.serializedForm): SerializedStore<
         Any
      > {
      return new SerializedStore<>(storeClass, uuid, (S)serializedForm);
   }

   public override fun toString(): String {
      return "SerializedStore(storeClass=${this.storeClass}, uuid=${this.uuid}, serializedForm=${this.serializedForm})";
   }

   public override fun hashCode(): Int {
      return (this.storeClass.hashCode() * 31 + this.uuid.hashCode()) * 31 + (if (this.serializedForm == null) 0 else this.serializedForm.hashCode());
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is SerializedStore) {
         return false;
      } else {
         val var2: SerializedStore = other as SerializedStore;
         if (!(this.storeClass == (other as SerializedStore).storeClass)) {
            return false;
         } else if (!(this.uuid == var2.uuid)) {
            return false;
         } else {
            return this.serializedForm == var2.serializedForm;
         }
      }
   }
}
