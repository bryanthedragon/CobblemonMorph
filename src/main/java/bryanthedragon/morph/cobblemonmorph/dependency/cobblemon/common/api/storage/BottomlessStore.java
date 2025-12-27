package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull

public open class BottomlessStore(uuid: UUID) : PokemonStore<BottomlessPosition> {
   public final val pokemon: MutableList<Pokemon>
   public final val storeChangeObservable: SimpleObservable<Unit>
   public open val uuid: UUID

   init {
      this.uuid = uuid;
      this.pokemon = new ArrayList<>();
      this.storeChangeObservable = new SimpleObservable<>();
   }

   public override operator fun iterator(): MutableIterator<Pokemon> {
      return this.pokemon.iterator();
   }

   public open operator fun get(position: BottomlessPosition): Pokemon? {
      val var3: Int = position.getCurrentIndex();
      val it: Int = var3.intValue();
      val var2: Int = if (0 <= it && it < this.pokemon.size()) var3 else null;
      return if (var2 != null) this.pokemon.get(var2.intValue()) else null;
   }

   public open fun getFirstAvailablePosition(): BottomlessPosition {
      return new BottomlessPosition(this.pokemon.size());
   }

   public open fun isValidPosition(position: BottomlessPosition): Boolean {
      return position.getCurrentIndex() >= 0;
   }

   public operator fun get(index: Int): Pokemon? {
      val var2: Int = index;
      val it: Int = var2.intValue();
      val var10000: Int = if (0 <= it && it < this.pokemon.size()) var2 else null;
      return if (var10000 != null) this.pokemon.get(var10000.intValue()) else null;
   }

   public open fun getObservingPlayers(): Set<ServerPlayer> {
      return SetsKt.emptySet();
   }

   public override fun sendTo(player: ServerPlayer) {
   }

   public override fun initialize() {
      val `$this$forEachIndexed$iv`: java.lang.Iterable = this.pokemon;
      var `index$iv`: Int = 0;

      for (Object item$iv : $this$forEachIndexed$iv) {
         val var6: Int = `index$iv`++;
         if (var6 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         val pokemon: Pokemon = `item$iv` as Pokemon;
         (`item$iv` as Pokemon).getStoreCoordinates().set(new StoreCoordinates<>(this as PokemonStore<BottomlessPosition>, new BottomlessPosition(var6)));
         Observable.DefaultImpls.subscribe$default(
            pokemon.getChangeObservable().pipe(Observable.Companion.stopAfter((new Function1<Pokemon, java.lang.Boolean>(this) {
               {
                  super(1);
                  this.this$0 = `$receiver`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull Pokemon it) {
                  val var10000: StoreCoordinates = it.getStoreCoordinates().get();
                  return !((if (var10000 != null) var10000.getStore() else null) == this.this$0);
               }
            }) as (Pokemon?) -> java.lang.Boolean) as Transform<Pokemon, Pokemon>), null, (new Function1<Pokemon, Unit>(this) {
               {
                  super(1);
                  this.this$0 = `$receiver`;
               }

               public final void invoke(@NotNull Pokemon it) {
                  this.this$0.getStoreChangeObservable().emit(Unit.INSTANCE);
               }
            }) as Function1, 1, null
         );
      }
   }

   public override fun saveToNBT(nbt: CompoundTag): CompoundTag {
      val `$this$forEachIndexed$iv`: java.lang.Iterable = this.pokemon;
      var `index$iv`: Int = 0;

      for (Object item$iv : $this$forEachIndexed$iv) {
         val var7: Int = `index$iv`++;
         if (var7 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         nbt.m_128365_("Slot$var7", (`item$iv` as Pokemon).saveToNBT(new CompoundTag()) as Tag);
      }

      return nbt;
   }

   public open fun loadFromNBT(nbt: CompoundTag): BottomlessStore {
      val i: Int = -1;

      while (nbt.m_128441_("Slot" + ++i)) {
         val pokemonNBT: CompoundTag = nbt.m_128469_("Slot$i");

         try {
            val var10000: java.util.List = this.pokemon;
            val var10001: Pokemon = new Pokemon();
            var10000.add(var10001.loadFromNBT(pokemonNBT));
         } catch (var5: InvalidSpeciesException) {
            this.handleInvalidSpeciesNBT(pokemonNBT);
         }
      }

      return this;
   }

   public override fun saveToJSON(json: JsonObject): JsonObject {
      val `$this$forEachIndexed$iv`: java.lang.Iterable = this.pokemon;
      var `index$iv`: Int = 0;

      for (Object item$iv : $this$forEachIndexed$iv) {
         val var7: Int = `index$iv`++;
         if (var7 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         json.add("Slot$var7", (`item$iv` as Pokemon).saveToJSON(new JsonObject()) as JsonElement);
      }

      return json;
   }

   public open fun loadFromJSON(json: JsonObject): BottomlessStore {
      val i: Int = -1;

      while (json.has("Slot" + ++i)) {
         val pokemonJSON: JsonObject = json.getAsJsonObject("Slot$i");

         try {
            val var10000: java.util.List = this.pokemon;
            val var10001: Pokemon = new Pokemon();
            var10000.add(var10001.loadFromJSON(pokemonJSON));
         } catch (var5: InvalidSpeciesException) {
            this.handleInvalidSpeciesJSON(pokemonJSON);
         }
      }

      return this;
   }

   public override fun loadPositionFromNBT(nbt: CompoundTag): StoreCoordinates<BottomlessPosition> {
      return new StoreCoordinates<>(this as PokemonStore<BottomlessPosition>, new BottomlessPosition(nbt.m_128445_("Slot")));
   }

   public open fun savePositionToNBT(position: BottomlessPosition, nbt: CompoundTag) {
      nbt.m_128344_("Slot", (byte)position.getCurrentIndex());
   }

   public open fun getAnyChangeObservable(): SimpleObservable<Unit> {
      return this.storeChangeObservable;
   }

   protected open fun setAtPosition(position: BottomlessPosition, pokemon: Pokemon?) {
      if (position.getCurrentIndex() == this.pokemon.size() && pokemon != null) {
         this.pokemon.add(pokemon);
         this.storeChangeObservable.emit(Unit.INSTANCE);
      } else {
         var startIndex: Int = this.pokemon.size();
         var i: Int = position.getCurrentIndex();
         if (0 <= i && i < startIndex) {
            startIndex = position.getCurrentIndex();
            if (pokemon != null) {
               this.pokemon.add(position.getCurrentIndex(), pokemon);
               startIndex++;
            } else {
               this.pokemon.remove(position.getCurrentIndex());
            }

            i = startIndex;

            for (int var5 = this.pokemon.size(); i < var5; i++) {
               this.pokemon.get(i).getStoreCoordinates().set(new StoreCoordinates<>(this as PokemonStore<BottomlessPosition>, new BottomlessPosition(i)));
            }

            this.storeChangeObservable.emit(Unit.INSTANCE);
         }
      }
   }
}
