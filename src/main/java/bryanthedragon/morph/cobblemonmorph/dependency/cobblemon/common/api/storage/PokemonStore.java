package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonObject
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.markers.KMappedMarker
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nPokemonStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonStore.kt\ncom/cobblemon/mod/common/api/storage/PokemonStore\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,164:1\n1855#2,2:165\n1#3:167\n*S KotlinDebug\n*F\n+ 1 PokemonStore.kt\ncom/cobblemon/mod/common/api/storage/PokemonStore\n*L\n68#1:165,2\n*E\n"])
public abstract class PokemonStore<T extends StorePosition> : java.lang.Iterable<Pokemon>, KMappedMarker {
   public abstract val uuid: UUID

   public abstract operator fun get(position: Any): Pokemon? {
   }

   public abstract fun getFirstAvailablePosition(): Any? {
   }

   public abstract fun getObservingPlayers(): Iterable<ServerPlayer> {
   }

   public abstract fun sendTo(player: ServerPlayer) {
   }

   public abstract fun initialize() {
   }

   protected abstract fun setAtPosition(position: Any, pokemon: Pokemon?) {
   }

   public abstract fun isValidPosition(position: Any): Boolean {
   }

   public open fun sendPacketToObservers(packet: NetworkPacket<*>) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         CobblemonNetwork.INSTANCE.sendPacket(`element$iv` as ServerPlayer, packet);
      }
   }

   public open fun add(pokemon: Pokemon): Boolean {
      this.remove(pokemon);
      val var10000: StorePosition = this.getFirstAvailablePosition();
      if (var10000 == null) {
         return false;
      } else {
         this.set((T)var10000, pokemon);
         return true;
      }
   }

   public open operator fun set(position: Any, pokemon: Pokemon) {
      val existing: Pokemon = this.get((T)position);
      if (!(existing == pokemon)) {
         if (!(existing == pokemon) && existing != null) {
            this.remove(existing);
         }

         this.setAtPosition((T)position, pokemon);
         pokemon.getStoreCoordinates().set(new StoreCoordinates<>(this, (T)position));
      }
   }

   public open fun swap(position1: Any, position2: Any) {
      val pokemon1: Pokemon = this.get((T)position1);
      val pokemon2: Pokemon = this.get((T)position2);
      this.setAtPosition((T)position1, pokemon2);
      this.setAtPosition((T)position2, pokemon1);
      if (pokemon1 != null) {
         val var10000: SettableObservable = pokemon1.getStoreCoordinates();
         if (var10000 != null) {
            var10000.set(new StoreCoordinates<>(this, (T)position2));
         }
      }

      if (pokemon2 != null) {
         val var5: SettableObservable = pokemon2.getStoreCoordinates();
         if (var5 != null) {
            var5.set(new StoreCoordinates<>(this, (T)position1));
         }
      }
   }

   public fun move(pokemon: Pokemon, position: Any) {
      val var10000: StoreCoordinates = pokemon.getStoreCoordinates().get();
      if (var10000 != null) {
         if (var10000.getStore() == this) {
            val var10001: StorePosition = var10000.getPosition();
            this.swap((T)var10001, (T)position);
         }
      }
   }

   public open fun remove(position: Any): Boolean {
      val pokemon: Pokemon = this.get((T)position);
      return pokemon != null && this.remove(pokemon);
   }

   public open fun remove(pokemon: Pokemon): Boolean {
      val var10000: StoreCoordinates = pokemon.getStoreCoordinates().get();
      if (var10000 == null) {
         return false;
      } else if (!(var10000.getStore() == this)) {
         return false;
      } else if (!(this.get((T)var10000.getPosition()) == pokemon)) {
         return false;
      } else {
         pokemon.recall();
         pokemon.getStoreCoordinates().set(null);
         this.setAtPosition((T)var10000.getPosition(), null);
         return true;
      }
   }

   public operator fun get(uuid: UUID): Pokemon? {
      val var3: java.util.Iterator = this.iterator();

      var var10000: Any;
      while (true) {
         if (var3.hasNext()) {
            val var4: Any = var3.next();
            if (!((var4 as Pokemon).getUuid() == uuid)) {
               continue;
            }

            var10000 = var4;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as Pokemon;
   }

   public open fun handleInvalidSpeciesNBT(nbt: CompoundTag) {
   }

   public abstract fun saveToNBT(nbt: CompoundTag): CompoundTag {
   }

   public abstract fun loadFromNBT(nbt: CompoundTag): PokemonStore<Any> {
   }

   public open fun handleInvalidSpeciesJSON(json: JsonObject) {
   }

   public abstract fun saveToJSON(json: JsonObject): JsonObject {
   }

   public abstract fun loadFromJSON(json: JsonObject): PokemonStore<Any> {
   }

   public abstract fun savePositionToNBT(position: Any, nbt: CompoundTag) {
   }

   public abstract fun loadPositionFromNBT(nbt: CompoundTag): StoreCoordinates<Any> {
   }

   public abstract fun getAnyChangeObservable(): Observable<Unit> {
   }

   override fun iterator(): MutableIterator<Pokemon> {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
