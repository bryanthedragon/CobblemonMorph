package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.BottomlessStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.RemoveClientPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.SwapClientPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.InitializePCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.MoveClientPCPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.SetPCPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.ArrayList;
import java.util.UUID
import java.util.Map.Entry
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nPCStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PCStore.kt\ncom/cobblemon/mod/common/api/storage/pc/PCStore\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,255:1\n1360#2:256\n1446#2,5:257\n1603#2,9:262\n1855#2:271\n1856#2:273\n1612#2:274\n1855#2:275\n1856#2:277\n1855#2,2:278\n1855#2,2:280\n1360#2:282\n1446#2,5:283\n1855#2,2:288\n1864#2,3:290\n1864#2,3:293\n1855#2:296\n1856#2:299\n1#3:272\n1#3:276\n215#4,2:297\n*S KotlinDebug\n*F\n+ 1 PCStore.kt\ncom/cobblemon/mod/common/api/storage/pc/PCStore\n*L\n54#1:256\n54#1:257,5\n55#1:262,9\n55#1:271\n55#1:273\n55#1:274\n67#1:275\n67#1:277\n77#1:278,2\n81#1:280,2\n104#1:282\n104#1:283,5\n104#1:288,2\n129#1:290,3\n171#1:293,3\n249#1:296\n249#1:299\n55#1:272\n250#1:297,2\n*E\n"])
public open class PCStore(uuid: UUID, name: MutableComponent) : PokemonStore<PCPosition> {
   public final val backupStore: BottomlessStore
   public final val boxes: MutableList<PCBox>
   protected final var lockedSize: Boolean
   public final val name: MutableComponent
   public final val observingUUIDs: MutableSet<UUID>
   public final val pcChangeObservable: SimpleObservable<Unit>
   public final val uuid: UUID

   init {
      this.uuid = uuid;
      this.name = name;
      this.boxes = new ArrayList<>();
      this.backupStore = new BottomlessStore(new UUID(0L, 0L));
      this.observingUUIDs = SetsKt.mutableSetOf(new UUID[]{this.uuid});
      this.pcChangeObservable = new SimpleObservable<>();
   }

   public constructor(uuid: UUID)  {
      val var10002: MutableComponent = LocalizationUtilsKt.lang("your_pc");
      this(uuid, var10002);
   }

   public override operator fun iterator(): Iterator<Pokemon> {
      val `$this$flatMap$iv`: java.lang.Iterable = this.boxes;
      val `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$flatMap$iv) {
         CollectionsKt.addAll(`destination$iv$iv`, CollectionsKt.toList(`element$iv$iv` as PCBox));
      }

      return (`destination$iv$iv` as java.util.List).iterator();
   }

   public open fun getObservingPlayers(): List<ServerPlayer> {
      val `$this$mapNotNull$iv`: java.lang.Iterable = this.observingUUIDs;
      val `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
         val var10000: ServerPlayer = PlayerExtensionsKt.getPlayer(`element$iv$iv$iv` as UUID);
         if (var10000 != null) {
            `destination$iv$iv`.add(var10000);
         }
      }

      return `destination$iv$iv` as MutableList<ServerPlayer>;
   }

   public fun addObserver(player: ServerPlayer) {
      val var10000: java.util.Set = this.observingUUIDs;
      val var10001: UUID = player.m_20148_();
      var10000.add(var10001);
      this.sendTo(player);
   }

   public fun removeObserver(playerID: UUID) {
      this.observingUUIDs.remove(playerID);
   }

   public open fun getFirstAvailablePosition(): PCPosition? {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val var10000: PCPosition = (`element$iv` as PCBox).getFirstAvailablePosition();
         if (var10000 != null) {
            return var10000;
         }
      }

      return null;
   }

   public open fun isValidPosition(position: PCPosition): Boolean {
      var var2: Int = this.boxes.size();
      val var3: Int = position.getBox();
      if (0 <= var3 && var3 < var2) {
         var2 = position.getSlot();
         if (0 <= var2 && var2 < 30) {
            return true;
         }
      }

      return false;
   }

   public override fun sendTo(player: ServerPlayer) {
      new InitializePCPacket(this).sendToPlayer(player);

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as PCBox).sendTo(player);
      }
   }

   public override fun initialize() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as PCBox).initialize();
      }

      this.backupStore.initialize();
   }

   public fun relocateEvictedBoxPokemon(pokemon: Pokemon) {
      val space: PCPosition = this.getFirstAvailablePosition();
      if (space != null) {
         this.set(space, pokemon);
      } else {
         this.backupStore.add(pokemon);
      }
   }

   public fun resize(newSize: Int, lockNewSize: Boolean = false, overflowHandler: (Pokemon) -> Unit = (new Function1<Pokemon, Unit>(this) {
         {
            super(1, receiver, PCStore::class.java, "relocateEvictedBoxPokemon", "relocateEvictedBoxPokemon(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", 0);
         }

         public final void invoke(@NotNull Pokemon p0) {
            (this.receiver as PCStore).relocateEvictedBoxPokemon(p0);
         }
      }) as Function1) {
      if (newSize <= 0) {
         throw new IllegalArgumentException("Invalid box count: Must be greater than zero.");
      } else {
         this.lockedSize = lockNewSize;
         if (this.boxes.size() > newSize) {
            val slicedBoxes: java.util.List = CollectionsKt.slice(this.boxes, RangesKt.until(newSize, this.boxes.size()));
            this.boxes.removeAll(slicedBoxes);
            val `$this$forEach$iv`: java.lang.Iterable = slicedBoxes;
            val `element$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : $this$flatMap$iv) {
               CollectionsKt.addAll(`element$iv`, `element$iv$iv` as PCBox);
            }

            for (Object element$ivx : $this$flatMap$iv) {
               overflowHandler.invoke(`element$ivx`);
            }
         } else {
            while (this.boxes.size() < newSize) {
               this.boxes.add(new PCBox(this));
            }

            this.tryRestoreBackedUpPokemon();
         }

         this.pcChangeObservable.emit(Unit.INSTANCE);
      }
   }

   public fun tryRestoreBackedUpPokemon() {
      var newPosition: PCPosition = this.getFirstAvailablePosition();

      for (java.util.List backedUpPokemon = CollectionsKt.toMutableList(this.backupStore.getPokemon());
         newPosition != null && !backedUpPokemon.isEmpty();
         newPosition = this.getFirstAvailablePosition()
      ) {
         this.set(newPosition, backedUpPokemon.remove(0) as Pokemon);
      }
   }

   public override fun saveToNBT(nbt: CompoundTag): CompoundTag {
      nbt.m_128376_("BoxCount", (short)this.boxes.size());
      nbt.m_128379_("BoxCountLocked", this.lockedSize);
      val `$this$forEachIndexed$iv`: java.lang.Iterable = this.boxes;
      var `index$iv`: Int = 0;

      for (Object item$iv : $this$forEachIndexed$iv) {
         val var7: Int = `index$iv`++;
         if (var7 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         nbt.m_128365_("Box$var7", (`item$iv` as PCBox).saveToNBT(new CompoundTag()) as Tag);
      }

      nbt.m_128365_("BackupStore", this.backupStore.saveToNBT(new CompoundTag()) as Tag);
      return nbt;
   }

   public override fun loadFromNBT(nbt: CompoundTag): PokemonStore<PCPosition> {
      val boxCountStored: Short = nbt.m_128448_("BoxCount");
      var boxNumber: Int = 0;

      for (short var4 = boxCountStored; boxNumber < var4; boxNumber++) {
         val var10000: java.util.List = this.boxes;
         val var10001: PCBox = new PCBox(this);
         val var10002: CompoundTag = nbt.m_128469_("Box$boxNumber");
         var10000.add(var10001.loadFromNBT(var10002));
      }

      this.lockedSize = nbt.m_128471_("BoxCountLocked");
      if (!this.lockedSize && this.boxes.size() != Cobblemon.INSTANCE.getConfig().getDefaultBoxCount()) {
         resize$default(this, Cobblemon.INSTANCE.getConfig().getDefaultBoxCount(), false, null, 4, null);
      } else {
         this.tryRestoreBackedUpPokemon();
      }

      this.removeDuplicates();
      return this as PokemonStore<PCPosition>;
   }

   public fun removeDuplicates() {
      val knownUUIDs: java.util.List = new ArrayList();

      for (PCBox box : this.boxes) {
         for (int i = 0; i < 30; i++) {
            val var10000: Pokemon = box.get(i);
            if (var10000 != null) {
               if (!knownUUIDs.contains(var10000.getUuid())) {
                  val var10001: UUID = var10000.getUuid();
                  knownUUIDs.add(var10001);
               } else {
                  box.set(i, null);
                  this.pcChangeObservable.emit(Unit.INSTANCE);
               }
            }
         }
      }
   }

   public override fun saveToJSON(json: JsonObject): JsonObject {
      json.addProperty("BoxCount", (short)this.boxes.size());
      json.addProperty("BoxCountLocked", this.lockedSize);
      val `$this$forEachIndexed$iv`: java.lang.Iterable = this.boxes;
      var `index$iv`: Int = 0;

      for (Object item$iv : $this$forEachIndexed$iv) {
         val var7: Int = `index$iv`++;
         if (var7 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         json.add("Box$var7", (`item$iv` as PCBox).saveToJSON(new JsonObject()) as JsonElement);
      }

      json.add("BackupStore", this.backupStore.saveToJSON(new JsonObject()) as JsonElement);
      return json;
   }

   public open operator fun set(position: PCPosition, pokemon: Pokemon) {
      super.set(position, pokemon);
      this.sendPacketToObservers(new SetPCPokemonPacket(this.uuid, position, pokemon));
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

   public open fun swap(position1: PCPosition, position2: PCPosition) {
      val pokemon1: Pokemon = this.get(position1);
      val pokemon2: Pokemon = this.get(position2);
      super.swap(position1, position2);
      if (pokemon1 != null && pokemon2 != null) {
         val var7: PokemonStore = this;
         val var8: UUID = pokemon1.getUuid();
         val var10005: UUID = pokemon2.getUuid();
         this.sendPacketToObservers(new SwapClientPokemonPacket(var7, var8, var10005));
      } else if (pokemon1 != null || pokemon2 != null) {
         val newPosition: PCPosition = if (pokemon1 == null) position1 else position2;
         var var10000: Pokemon = pokemon1;
         if (pokemon1 == null) {
            var10000 = pokemon2;
         }

         val var10003: UUID = this.uuid;
         val var10004: UUID = var10000.getUuid();
         this.sendPacketToObservers(new MoveClientPCPokemonPacket(var10003, var10004, newPosition));
      }
   }

   public override fun loadFromJSON(json: JsonObject): PokemonStore<PCPosition> {
      val boxCountStored: Short = json.get("BoxCount").getAsShort();
      var boxNumber: Int = 0;

      for (short var4 = boxCountStored; boxNumber < var4; boxNumber++) {
         val var10000: java.util.List = this.boxes;
         val var10001: PCBox = new PCBox(this);
         val var10002: JsonObject = json.getAsJsonObject("Box$boxNumber");
         var10000.add(var10001.loadFromJSON(var10002));
      }

      this.lockedSize = json.get("BoxCountLocked").getAsBoolean();
      if (!this.lockedSize && this.boxes.size() != Cobblemon.INSTANCE.getConfig().getDefaultBoxCount()) {
         resize$default(this, Cobblemon.INSTANCE.getConfig().getDefaultBoxCount(), false, null, 4, null);
      } else {
         this.tryRestoreBackedUpPokemon();
      }

      this.removeDuplicates();
      return this as PokemonStore<PCPosition>;
   }

   public override fun loadPositionFromNBT(nbt: CompoundTag): StoreCoordinates<PCPosition> {
      return new StoreCoordinates<>(this as PokemonStore<PCPosition>, new PCPosition(nbt.m_128448_("Box"), nbt.m_128445_("Slot")));
   }

   public open fun savePositionToNBT(position: PCPosition, nbt: CompoundTag) {
      nbt.m_128376_("Box", (short)position.getBox());
      nbt.m_128344_("Slot", (byte)position.getSlot());
   }

   public open fun getAnyChangeObservable(): SimpleObservable<Unit> {
      return this.pcChangeObservable;
   }

   protected open fun setAtPosition(position: PCPosition, pokemon: Pokemon?) {
      val var3: Int = this.boxes.size();
      val var4: Int = position.getBox();
      if (0 > var4 || var4 >= var3) {
         throw new IllegalArgumentException("Invalid box number ${position.getBox()}. Should be between 0 and ${this.boxes.size()}");
      } else {
         this.boxes.get(position.getBox()).set(position.getSlot(), pokemon);
      }
   }

   public open operator fun get(position: PCPosition): Pokemon? {
      val var2: Int = this.boxes.size();
      val var3: Int = position.getBox();
      return if (0 > var3 || var3 >= var2) null else this.boxes.get(position.getBox()).get(position.getSlot());
   }

   public fun clearPC() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val box: PCBox = `element$iv` as PCBox;

         for (Entry element$ivx : ((PCBox)element$iv).getNonEmptySlots().entrySet()) {
            this.remove(new PCPosition(box.getBoxNumber(), (`element$ivx`.getKey() as java.lang.Number).intValue()));
         }
      }
   }
}
