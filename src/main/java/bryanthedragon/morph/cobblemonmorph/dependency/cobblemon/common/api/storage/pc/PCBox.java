package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.InvalidSpeciesException
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.SetPCBoxPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.ArrayList;
import java.util.LinkedHashMap
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.markers.KMappedMarker
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nPCBox.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PCBox.kt\ncom/cobblemon/mod/common/api/storage/pc/PCBox\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,150:1\n13644#2,3:151\n766#3:154\n857#3,2:155\n1271#3,2:157\n1285#3,4:159\n*S KotlinDebug\n*F\n+ 1 PCBox.kt\ncom/cobblemon/mod/common/api/storage/pc/PCBox\n*L\n80#1:151,3\n149#1:154\n149#1:155,2\n149#1:157,2\n149#1:159,4\n*E\n"])
public open class PCBox(pc: PCStore) : java.lang.Iterable<Pokemon>, KMappedMarker {
   public final val boxChangeEmitter: SimpleObservable<Unit>

   public final val boxNumber: Int
      public final get() {
         return this.pc.getBoxes().indexOf(this);
      }


   protected final var emit: Boolean
   public final val pc: PCStore
   protected final val pokemon: Array<Pokemon?>

   public final val unoccupiedSlots: Int
      public final get() {
         return 30 - ArraysKt.filterNotNull(this.pokemon).size();
      }


   init {
      this.pc = pc;
      this.boxChangeEmitter = new SimpleObservable<>();
      this.emit = true;
      var var2: Int = 0;

      val var3: Array<Pokemon>;
      for (var3 = new Pokemon[30]; var2 < 30; var2++) {
         var3[var2] = null;
      }

      this.pokemon = var3;
   }

   public override operator fun iterator(): Iterator<Pokemon> {
      return ArraysKt.filterNotNull(this.pokemon).iterator();
   }

   public open operator fun get(index: Int): Pokemon? {
      return if (0 <= index && index < 30) this.pokemon[index] else null;
   }

   public open operator fun set(index: Int, pokemon: Pokemon?) {
      if (0 <= index && index < 30) {
         this.pokemon[index] = pokemon;
         label34:
         if (pokemon != null) {
            val previousCoordinates: StoreCoordinates = pokemon.getStoreCoordinates().get();
            val position: StorePosition = if (previousCoordinates != null) previousCoordinates.getPosition() else null;
            pokemon.getStoreCoordinates().set(new StoreCoordinates<>(this.pc, new PCPosition(this.getBoxNumber(), index)));
            if ((if (previousCoordinates != null) previousCoordinates.getStore() else null) == this) {
               if ((position as PCPosition).getBox() == this.getBoxNumber()) {
                  break label34;
               }
            }

            this.trackPokemon(pokemon);
         }

         if (this.emit) {
            this.boxChangeEmitter.emit(Unit.INSTANCE);
         }
      }
   }

   public fun getFirstAvailablePosition(): PCPosition? {
      for (int index = 0; index < 30; index++) {
         if (this.pokemon[index] == null) {
            return new PCPosition(this.getBoxNumber(), index);
         }
      }

      return null;
   }

   public open fun initialize() {
      val box: Int = this.getBoxNumber();
      var `index$iv`: Int = 0;

      val `$this$forEachIndexed$iv`: Any;
      for (Object item$iv : $this$forEachIndexed$iv) {
         val slot: Int = `index$iv`++;
         if (`item$iv` != null) {
            ((Pokemon)`item$iv`).getStoreCoordinates().set(new StoreCoordinates<>(this.pc, new PCPosition(box, slot)));
            this.trackPokemon((Pokemon)`item$iv`);
         }
      }

      Observable.DefaultImpls.subscribe$default(this.boxChangeEmitter, null, (new Function1<Unit, Unit>(this) {
         {
            super(1);
            this.this$0 = `$receiver`;
         }

         public final void invoke(@NotNull Unit it) {
            this.this$0.getPc().getPcChangeObservable().emit(Unit.INSTANCE);
         }
      }) as Function1, 1, null);
   }

   public fun trackPokemon(pokemon: Pokemon) {
      Observable.DefaultImpls.subscribe$default(
         pokemon.getChangeObservable().pipe(Observable.Companion.stopAfter((new Function1<Pokemon, java.lang.Boolean>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final java.lang.Boolean invoke(@NotNull Pokemon it) {
               val var10000: StoreCoordinates = it.getStoreCoordinates().get();
               if (var10000 == null) {
                  return true;
               } else {
                  if (var10000.getStore() == this.this$0) {
                     val var3: StorePosition = var10000.getPosition();
                     if ((var3 as PCPosition).getBox() == this.this$0.getBoxNumber()) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }) as (Pokemon?) -> java.lang.Boolean) as Transform<Pokemon, Pokemon>), null, (new Function1<Pokemon, Unit>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            public final void invoke(@NotNull Pokemon it) {
               this.this$0.getBoxChangeEmitter().emit(Unit.INSTANCE);
            }
         }) as Function1, 1, null
      );
   }

   public fun sendTo(player: ServerPlayer) {
      new SetPCBoxPokemonPacket(this).sendToPlayer(player);
   }

   public open fun saveToNBT(nbt: CompoundTag): CompoundTag {
      for (int slot = 0; slot < 30; slot++) {
         val var10000: Pokemon = this.pokemon[slot];
         if (this.pokemon[slot] != null) {
            nbt.m_128365_("Slot$slot", var10000.saveToNBT(new CompoundTag()) as Tag);
         }
      }

      return nbt;
   }

   public open fun saveToJSON(json: JsonObject): JsonObject {
      for (int slot = 0; slot < 30; slot++) {
         val var10000: Pokemon = this.pokemon[slot];
         if (this.pokemon[slot] != null) {
            json.add("Slot$slot", var10000.saveToJSON(new JsonObject()) as JsonElement);
         }
      }

      return json;
   }

   public open fun loadFromJSON(json: JsonObject): PCBox {
      for (int slot = 0; slot < 30; slot++) {
         if (json.has("Slot$slot")) {
            val pokemonJson: JsonObject = json.getAsJsonObject("Slot$slot");

            try {
               val var6: Array<Pokemon> = this.pokemon;
               val var10002: Pokemon = new Pokemon();
               var6[slot] = var10002.loadFromJSON(pokemonJson);
            } catch (var5: InvalidSpeciesException) {
               val var10000: PCStore = this.pc;
               var10000.handleInvalidSpeciesJSON(pokemonJson);
            }
         }
      }

      return this;
   }

   public open fun loadFromNBT(nbt: CompoundTag): PCBox {
      for (int slot = 0; slot < 30; slot++) {
         if (nbt.m_128441_("Slot$slot")) {
            val pokemonNBT: CompoundTag = nbt.m_128469_("Slot$slot");

            try {
               val var6: Array<Pokemon> = this.pokemon;
               val var10002: Pokemon = new Pokemon();
               var6[slot] = var10002.loadFromNBT(pokemonNBT);
            } catch (var5: InvalidSpeciesException) {
               val var10000: PCStore = this.pc;
               var10000.handleInvalidSpeciesNBT(pokemonNBT);
            }
         }
      }

      return this;
   }

   public fun getNonEmptySlots(): Map<Int, Pokemon> {
      var `$this$associateWith$iv`: java.lang.Iterable = RangesKt.until(0, 30) as java.lang.Iterable;
      val `$this$associateWithTo$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if (this.get((`element$iv$iv` as java.lang.Number).intValue()) != null) {
            `$this$associateWithTo$iv$iv`.add(`element$iv$iv`);
         }
      }

      `$this$associateWith$iv` = `$this$associateWithTo$iv$iv` as java.util.List;
      val `result$iv`: LinkedHashMap = new LinkedHashMap(
         RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(`$this$associateWithTo$iv$iv` as java.util.List, 10)), 16)
      );

      for (Object element$iv$ivx : $this$filter$iv) {
         val var10000: java.util.Map = `result$iv`;
         val var20: Pokemon = this.get((`element$iv$ivx` as java.lang.Number).intValue());
         var10000.put(`element$iv$ivx`, var20);
      }

      return `result$iv`;
   }
}
