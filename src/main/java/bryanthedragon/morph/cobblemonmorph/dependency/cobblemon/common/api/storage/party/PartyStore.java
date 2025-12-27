package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.InvalidSpeciesException
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.RemoveClientPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.SwapClientPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.InitializePartyPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.MoveClientPartyPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.SetPartyPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.players.PlayerList
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nPartyStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartyStore.kt\ncom/cobblemon/mod/common/api/storage/party/PartyStore\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,275:1\n1#2:276\n1#2:301\n766#3:277\n857#3,2:278\n1864#3,3:280\n1549#3:283\n1620#3,3:284\n1855#3,2:287\n1855#3,2:289\n1603#3,9:291\n1855#3:300\n1856#3:302\n1612#3:303\n1045#3:304\n1855#3,2:305\n*S KotlinDebug\n*F\n+ 1 PartyStore.kt\ncom/cobblemon/mod/common/api/storage/party/PartyStore\n*L\n258#1:301\n90#1:277\n90#1:278,2\n100#1:280,3\n153#1:283\n153#1:284,3\n243#1:287,2\n247#1:289,2\n258#1:291,9\n258#1:300\n258#1:302\n258#1:303\n265#1:304\n268#1:305,2\n*E\n"])
public open class PartyStore(uuid: UUID) : PokemonStore<PartyPosition> {
   protected final val anyChangeObservable: SimpleObservable<Unit>
   public final var observerUUIDs: MutableList<UUID>
   protected final val slots: MutableList<Pokemon?>
   public open val uuid: UUID

   init {
      this.uuid = uuid;
      val var2: Byte = 6;
      val var3: ArrayList = new ArrayList(6);

      for (int var4 = 0; var4 < var2; var4++) {
         var3.add(null);
      }

      this.slots = var3;
      this.anyChangeObservable = new SimpleObservable<>();
      this.observerUUIDs = new ArrayList<>();
   }

   public override operator fun iterator(): Iterator<Pokemon> {
      return CollectionsKt.filterNotNull(this.slots).iterator();
   }

   public fun get(slot: Int): Pokemon? {
      val var2: Int = slot;
      val it: Int = var2.intValue();
      val var10000: Int = if (it < this.slots.size() && it >= 0) var2 else null;
      return if (var10000 != null) this.slots.get(var10000.intValue()) else null;
   }

   public open operator fun get(position: PartyPosition): Pokemon? {
      return this.get(position.getSlot());
   }

   public fun set(slot: Int, pokemon: Pokemon) {
      this.set(new PartyPosition(slot), pokemon);
   }

   protected open fun setAtPosition(position: PartyPosition, pokemon: Pokemon?) {
      if (position.getSlot() >= this.slots.size()) {
         throw new IllegalArgumentException("Slot position is out of bounds");
      } else {
         this.slots.set(position.getSlot(), pokemon);
         if (pokemon != null) {
            val var10000: StoreCoordinates = pokemon.getStoreCoordinates().get();
            if (!((if (var10000 != null) var10000.getStore() else null) == this)) {
               this.trackPokemon(pokemon);
            }
         }

         this.anyChangeObservable.emit(Unit.INSTANCE);
      }
   }

   public fun trackPokemon(pokemon: Pokemon) {
      Observable.DefaultImpls.subscribe$default(
         pokemon.getChangeObservable().pipe(Observable.Companion.stopAfter((new Function1<Pokemon, java.lang.Boolean>(pokemon, this) {
            {
               super(1);
               this.$pokemon = `$pokemon`;
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final java.lang.Boolean invoke(@NotNull Pokemon it) {
               val var10000: StoreCoordinates = this.$pokemon.getStoreCoordinates().get();
               return !((if (var10000 != null) var10000.getStore() else null) == this.this$0);
            }
         }) as (Pokemon?) -> java.lang.Boolean) as Transform<Pokemon, Pokemon>), null, (new Function1<Pokemon, Unit>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            public final void invoke(@NotNull Pokemon it) {
               this.this$0.getAnyChangeObservable().emit(Unit.INSTANCE);
            }
         }) as Function1, 1, null
      );
   }

   public open fun getFirstAvailablePosition(): PartyPosition? {
      var i: Int = 0;

      for (int var2 = this.slots.size(); i < var2; i++) {
         if (this.slots.get(i) == null) {
            return new PartyPosition(i);
         }
      }

      return null;
   }

   public open fun isValidPosition(position: PartyPosition): Boolean {
      val var2: Int = this.slots.size();
      val var3: Int = position.getSlot();
      return 0 <= var3 && var3 < var2;
   }

   public open fun getObservingPlayers(): List<ServerPlayer> {
      val var10000: MinecraftServer = DistributionUtilsKt.server();
      if (var10000 != null) {
         val var10: PlayerList = var10000.m_6846_();
         if (var10 != null) {
            val var11: java.util.List = var10.m_11314_();
            if (var11 != null) {
               val `$this$filter$iv`: java.lang.Iterable = var11;
               val `destination$iv$iv`: java.util.Collection = new ArrayList();

               for (Object element$iv$iv : $this$filter$iv) {
                  if (this.observerUUIDs.contains((`element$iv$iv` as ServerPlayer).m_20148_())) {
                     `destination$iv$iv`.add(`element$iv$iv`);
                  }
               }

               return `destination$iv$iv` as MutableList<ServerPlayer>;
            }
         }
      }

      return CollectionsKt.emptyList();
   }

   public fun size(): Int {
      return this.slots.size();
   }

   public fun occupied(): Int {
      return CollectionsKt.filterNotNull(this.slots).size();
   }

   public override fun sendTo(player: ServerPlayer) {
      CobblemonNetwork.INSTANCE.sendPacket(player, new InitializePartyPacket(false, this.getUuid(), this.slots.size()));
      val `$this$forEachIndexed$iv`: java.lang.Iterable = this.slots;
      var `index$iv`: Int = 0;

      for (Object item$iv : $this$forEachIndexed$iv) {
         val var7: Int = `index$iv`++;
         if (var7 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         val pokemon: Pokemon = `item$iv` as Pokemon;
         if (`item$iv` as Pokemon != null) {
            CobblemonNetwork.INSTANCE.sendPacket(player, new SetPartyPokemonPacket(this.getUuid(), new PartyPosition(var7), pokemon));
         }
      }
   }

   public open operator fun set(position: PartyPosition, pokemon: Pokemon) {
      super.set(position, pokemon);
      this.sendPacketToObservers(new SetPartyPokemonPacket(this.getUuid(), position, pokemon));
   }

   public override fun remove(pokemon: Pokemon): Boolean {
      val var10000: Boolean;
      if (super.remove(pokemon)) {
         val var10003: PokemonStore = this;
         val var10004: UUID = pokemon.getUuid();
         this.sendPacketToObservers(new RemoveClientPokemonPacket(var10003, var10004));
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public fun swap(slot1: Int, slot2: Int) {
      if (0 <= slot1 && slot1 < this.slots.size() && 0 <= slot2 && slot2 < this.slots.size()) {
         this.swap(new PartyPosition(slot1), new PartyPosition(slot2));
      }
   }

   public open fun swap(position1: PartyPosition, position2: PartyPosition) {
      val pokemon1: Pokemon = this.get(position1);
      val pokemon2: Pokemon = this.get(position2);
      super.swap(position1, position2);
      if (pokemon1 != null && pokemon2 != null) {
         val var7: PokemonStore = this;
         val var8: UUID = pokemon1.getUuid();
         val var10005: UUID = pokemon2.getUuid();
         this.sendPacketToObservers(new SwapClientPokemonPacket(var7, var8, var10005));
      } else if (pokemon1 != null || pokemon2 != null) {
         val newPosition: PartyPosition = if (pokemon1 == null) position1 else position2;
         var var10000: Pokemon = pokemon1;
         if (pokemon1 == null) {
            var10000 = pokemon2;
         }

         val var10003: UUID = this.getUuid();
         val var10004: UUID = var10000.getUuid();
         this.sendPacketToObservers(new MoveClientPartyPokemonPacket(var10003, var10004, newPosition));
      }
   }

   public override fun initialize() {
      var slot: Int = 0;

      for (int var2 = this.slots.size(); slot < var2; slot++) {
         val var10000: Pokemon = this.get(slot);
         if (var10000 != null) {
            var10000.getStoreCoordinates().set(new StoreCoordinates<>(this as PokemonStore<PartyPosition>, new PartyPosition(slot)));
            this.trackPokemon(var10000);
         }
      }
   }

   public fun toGappyList(): List<Pokemon?> {
      return CollectionsKt.toList(this.slots);
   }

   public fun <T : Any> mapNullPreserving(mapper: (Pokemon) -> Any): List<Any?> {
      val `$this$map$iv`: java.lang.Iterable = this.toGappyList();
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add(if (`item$iv$iv` as Pokemon != null) mapper.invoke(`item$iv$iv` as Pokemon) else null);
      }

      return `destination$iv$iv` as MutableList<T>;
   }

   public override fun saveToNBT(nbt: CompoundTag): CompoundTag {
      nbt.m_128405_("SlotCount", this.slots.size());
      var slot: Int = 0;

      for (int var3 = this.slots.size(); slot < var3; slot++) {
         val pokemon: Pokemon = this.get(slot);
         if (pokemon != null) {
            nbt.m_128365_("Slot$slot", pokemon.saveToNBT(new CompoundTag()) as Tag);
         }
      }

      return nbt;
   }

   public open fun loadFromNBT(nbt: CompoundTag): PartyStore {
      val slotCount: Int = nbt.m_128451_("SlotCount");

      while (slotCount > this.slots.size()) {
         CollectionsKt.removeLast(this.slots);
      }

      while (slotCount < this.slots.size()) {
         this.slots.add(null);
      }

      var slot: Int = 0;

      for (int var4 = this.slots.size(); slot < var4; slot++) {
         val pokemonNBT: CompoundTag = nbt.m_128469_("Slot$slot");

         try {
            if (!pokemonNBT.m_128456_()) {
               val var10000: java.util.List = this.slots;
               val var10002: Pokemon = new Pokemon();
               var10000.set(slot, var10002.loadFromNBT(pokemonNBT));
            }
         } catch (var7: InvalidSpeciesException) {
            this.handleInvalidSpeciesNBT(pokemonNBT);
         }
      }

      this.removeDuplicates();
      return this;
   }

   public override fun saveToJSON(json: JsonObject): JsonObject {
      json.addProperty("SlotCount", this.slots.size());
      var slot: Int = 0;

      for (int var3 = this.slots.size(); slot < var3; slot++) {
         val pokemon: Pokemon = this.get(slot);
         if (pokemon != null) {
            json.add("Slot$slot", pokemon.saveToJSON(new JsonObject()) as JsonElement);
         }
      }

      return json;
   }

   public open fun loadFromJSON(json: JsonObject): PartyStore {
      val slotCount: Int = json.get("SlotCount").getAsInt();

      while (slotCount > this.slots.size()) {
         CollectionsKt.removeLast(this.slots);
      }

      while (slotCount < this.slots.size()) {
         this.slots.add(null);
      }

      var slot: Int = 0;

      for (int var4 = this.slots.size(); slot < var4; slot++) {
         val key: java.lang.String = "Slot$slot";
         if (json.has(key)) {
            val pokemonJSON: JsonObject = json.get(key).getAsJsonObject();

            try {
               val var10000: java.util.List = this.slots;
               val var10002: Pokemon = new Pokemon();
               var10000.set(slot, var10002.loadFromJSON(pokemonJSON));
            } catch (var8: InvalidSpeciesException) {
               this.handleInvalidSpeciesJSON(pokemonJSON);
            }
         }
      }

      this.removeDuplicates();
      return this;
   }

   public fun removeDuplicates() {
      val knownUUIDs: java.util.List = new ArrayList();
      var slot: Int = 0;

      for (int var3 = this.slots.size(); slot < var3; slot++) {
         val var10000: Pokemon = this.get(slot);
         if (var10000 != null) {
            if (!knownUUIDs.contains(var10000.getUuid())) {
               val var10001: UUID = var10000.getUuid();
               knownUUIDs.add(var10001);
            } else {
               this.slots.set(slot, null);
               this.anyChangeObservable.emit(Unit.INSTANCE);
            }
         }
      }
   }

   public override fun loadPositionFromNBT(nbt: CompoundTag): StoreCoordinates<PartyPosition> {
      return new StoreCoordinates<>(this as PokemonStore<PartyPosition>, new PartyPosition(nbt.m_128445_("Slot")));
   }

   public open fun savePositionToNBT(position: PartyPosition, nbt: CompoundTag) {
      nbt.m_128344_("Slot", (byte)position.getSlot());
   }

   public override fun getAnyChangeObservable(): Observable<Unit> {
      return this.anyChangeObservable;
   }

   public fun heal() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as Pokemon).heal();
      }
   }

   public fun didSleep() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as Pokemon).didSleep();
      }
   }

   public fun getHealingRemainderPercent(): Float {
      var totalPercent: Float = 0.0F;

      for (Pokemon pokemon : this) {
         totalPercent += 1.0F - (float)pokemon.getCurrentHealth() / pokemon.getHp();
      }

      return totalPercent;
   }

   public fun toBattleTeam(clone: Boolean = false, checkHealth: Boolean = true, leadingPokemon: UUID? = null): List<BattlePokemon> {
      val `$this$sortedBy$iv`: java.lang.Iterable = this;
      val `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
         val var10000: BattlePokemon = if (clone)
            BattlePokemon.Companion.safeCopyOf(`element$iv$iv$iv` as Pokemon)
            else
            BattlePokemon.Companion.playerOwned(`element$iv$iv$iv` as Pokemon);
         if (var10000 != null) {
            `destination$iv$iv`.add(var10000);
         }
      }

      return CollectionsKt.sortedWith(`destination$iv$iv` as java.util.List, new PartyStore$toBattleTeam$$inlined$sortedBy$1(leadingPokemon, this));
   }

   public fun clearParty() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val it: Pokemon = `element$iv` as Pokemon;
         (`element$iv` as Pokemon).tryRecallWithAnimation();
         this.remove(it);
      }
   }
}
