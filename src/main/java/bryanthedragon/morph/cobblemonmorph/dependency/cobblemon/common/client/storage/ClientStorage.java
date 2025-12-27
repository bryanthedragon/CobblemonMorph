package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nClientStorage.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientStorage.kt\ncom/cobblemon/mod/common/client/storage/ClientStorage\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,42:1\n1#2:43\n*E\n"])
public abstract class ClientStorage<T extends StorePosition> {
   public final val uuid: UUID

   open fun ClientStorage(uuid: UUID) {
      this.uuid = uuid;
   }

   public abstract fun findByUUID(uuid: UUID): Pokemon? {
   }

   public abstract fun set(position: Any, pokemon: Pokemon?) {
   }

   public abstract fun get(position: Any): Pokemon? {
   }

   public abstract fun getPosition(pokemon: Pokemon): Any? {
   }

   public fun swap(pokemonID1: UUID, pokemonID2: UUID) {
      val pokemon1: Pokemon = this.findByUUID(pokemonID1);
      val pokemon2: Pokemon = this.findByUUID(pokemonID2);
      val position1: StorePosition = if (pokemon1 != null) this.getPosition(pokemon1) else null;
      val position2: StorePosition = if (pokemon2 != null) this.getPosition(pokemon2) else null;
      if (position1 != null) {
         this.set((T)position1, pokemon2);
      }

      if (position2 != null) {
         this.set((T)position2, pokemon1);
      }
   }

   public fun remove(pokemonID: UUID) {
      val var10000: Pokemon = this.findByUUID(pokemonID);
      if (var10000 != null) {
         val var5: StorePosition = this.getPosition(var10000);
         if (var5 != null) {
            this.set((T)var5, null);
         }
      }
   }

   public fun move(pokemonID: UUID, newPosition: Any) {
      val var10000: Pokemon = this.findByUUID(pokemonID);
      if (var10000 != null) {
         val var6: StorePosition = this.getPosition(var10000);
         if (var6 != null) {
            this.set((T)var6, null);
            this.set((T)newPosition, var10000);
         }
      }
   }
}
