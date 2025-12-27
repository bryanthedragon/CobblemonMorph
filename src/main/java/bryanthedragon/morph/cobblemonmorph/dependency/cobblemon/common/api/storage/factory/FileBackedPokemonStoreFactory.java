package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.factory

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.ObservableSubscription
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.SerializedStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.FileStoreAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PCBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent.Pre
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.UUID
import java.util.Map.Entry
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nFileBackedPokemonStoreFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FileBackedPokemonStoreFactory.kt\ncom/cobblemon/mod/common/api/storage/factory/FileBackedPokemonStoreFactory\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,130:1\n361#2,7:131\n1549#3:138\n1620#3,3:139\n766#3:142\n857#3,2:143\n1855#3,2:145\n1855#3,2:149\n215#4,2:147\n*S KotlinDebug\n*F\n+ 1 FileBackedPokemonStoreFactory.kt\ncom/cobblemon/mod/common/api/storage/factory/FileBackedPokemonStoreFactory\n*L\n55#1:131,7\n102#1:138\n102#1:139,3\n126#1:142\n126#1:143,2\n126#1:145,2\n106#1:149,2\n127#1:147,2\n*E\n"])
public open class FileBackedPokemonStoreFactory<S>(adapter: FileStoreAdapter<Any>,
      createIfMissing: Boolean,
      partyConstructor: (UUID) -> PlayerPartyStore = <unrepresentable>.INSTANCE as Function1,
      pcConstructor: (UUID) -> PCStore = <unrepresentable>.INSTANCE as Function1
   ) :
   PokemonStoreFactory {
   protected final val adapter: FileStoreAdapter<Any>
   protected final val createIfMissing: Boolean
   private final val dirtyStores: MutableSet<PokemonStore<*>>
   public final val partyConstructor: (UUID) -> PlayerPartyStore
   public final var passedTicks: Int
   public final val pcConstructor: (UUID) -> PCStore
   protected final var saveExecutor: ExecutorService
   protected final val saveSubscription: ObservableSubscription<Pre>
   protected final val storeCaches: MutableMap<
      Class<out PokemonStore<*>>,
      bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.factory.FileBackedPokemonStoreFactory.StoreCache<*, *>
   >

   init {
      this.adapter = adapter;
      this.createIfMissing = createIfMissing;
      this.partyConstructor = partyConstructor;
      this.pcConstructor = pcConstructor;
      this.saveSubscription = Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_TICK_PRE, null, (new Function1<ServerTickEvent.Pre, Unit>(this) {
         {
            super(1);
            this.this$0 = `$receiver`;
         }

         public final void invoke(@NotNull ServerTickEvent.Pre it) {
            this.this$0.setPassedTicks(this.this$0.getPassedTicks() + 1);
            if (this.this$0.getPassedTicks() > 20 * Cobblemon.INSTANCE.getConfig().getPokemonSaveIntervalSeconds()) {
               this.this$0.saveAll();
               this.this$0.setPassedTicks(0);
            }
         }
      }) as Function1, 1, null);
      this.saveExecutor = Executors.newSingleThreadExecutor();
      this.storeCaches = new LinkedHashMap<>();
      this.dirtyStores = new LinkedHashSet<>();
   }

   protected fun <E : StorePosition, T : PokemonStore<Any>> getStoreCache(storeClass: Class<Any>): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.factory.FileBackedPokemonStoreFactory.StoreCache<
         Any,
         Any
      > {
      val `$this$getOrPut$iv`: java.util.Map = this.storeCaches;
      val `value$iv`: Any = this.storeCaches.get(storeClass);
      val var10000: Any;
      if (`value$iv` == null) {
         val var7: Any = new FileBackedPokemonStoreFactory.StoreCache(this);
         `$this$getOrPut$iv`.put(storeClass, var7);
         var10000 = var7;
      } else {
         var10000 = `value$iv`;
      }

      return var10000 as FileBackedPokemonStoreFactory<S>StoreCache<E, T>;
   }

   public override fun getPlayerParty(playerID: UUID): PlayerPartyStore? {
      return this.getStore(PlayerPartyStore.class, playerID, this.partyConstructor);
   }

   public override fun getPC(playerID: UUID): PCStore? {
      return this.getStore(PCStore.class, playerID, this.pcConstructor);
   }

   public override fun <E : StorePosition, T : PokemonStore<Any>> getCustomStore(storeClass: Class<Any>, uuid: UUID): Any? {
      return (T)getStore$default(this, storeClass, uuid, null, 4, null);
   }

   public fun <E : StorePosition, T : PokemonStore<Any>> getStore(storeClass: Class<Any>, uuid: UUID, constructor: (UUID) -> Any = ...): Any? {
      val cache: java.util.Map = this.getStoreCache(storeClass).getCacheMap();
      val cached: PokemonStore = cache.get(uuid) as PokemonStore;
      if (cached != null) {
         return (T)cached;
      } else {
         var var10000: PokemonStore = this.adapter.load(storeClass, uuid);
         if (var10000 == null) {
            var10000 = if (this.createIfMissing) constructor.invoke(uuid) as PokemonStore else null;
            if (var10000 == null) {
               return null;
            }
         }

         var10000.initialize();
         this.track(var10000);
         cache.put(uuid, var10000);
         return (T)var10000;
      }
   }

   public fun save(store: PokemonStore<*>) {
      val serialized: SerializedStore = new SerializedStore((Class<? extends PokemonStore<?>>)store.getClass(), store.getUuid(), this.adapter.serialize(store));
      this.dirtyStores.remove(store);
      this.saveExecutor.submit(FileBackedPokemonStoreFactory::save$lambda$2);
   }

   public fun saveAll() {
      Cobblemon.INSTANCE.getLOGGER().debug("Serializing ${this.dirtyStores.size()} Pokémon stores.");
      val `$this$map$iv`: java.lang.Iterable = this.dirtyStores;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.dirtyStores, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add(
            new SerializedStore(
               (Class<? extends PokemonStore<?>>)(`item$iv$iv` as PokemonStore).getClass(),
               (`item$iv$iv` as PokemonStore).getUuid(),
               this.adapter.serialize(`item$iv$iv` as PokemonStore)
            )
         );
      }

      val serializedStores: java.util.List = `destination$iv$iv` as java.util.List;
      this.dirtyStores.clear();
      Cobblemon.INSTANCE.getLOGGER().debug("Queueing save.");
      this.saveExecutor.submit(FileBackedPokemonStoreFactory::saveAll$lambda$5);
   }

   public fun isCached(store: PokemonStore<*>): Boolean {
      val var10000: FileBackedPokemonStoreFactory.StoreCache = this.storeCaches.get(store.getClass());
      if (var10000 != null) {
         val var2: java.util.Map = var10000.getCacheMap();
         if (var2 != null) {
            return var2.containsKey(store.getUuid());
         }
      }

      return false;
   }

   public fun track(store: PokemonStore<*>) {
      DistributionUtilsKt.subscribeOnServer$default(
         store.getAnyChangeObservable().pipe(Observable.Companion.emitWhile((new Function1<Unit, java.lang.Boolean>(this, store) {
            {
               super(1);
               this.this$0 = `$receiver`;
               this.$store = `$store`;
            }

            @NotNull
            public final java.lang.Boolean invoke(@NotNull Unit it) {
               return this.this$0.isCached(this.$store);
            }
         }) as (Unit?) -> java.lang.Boolean) as Transform<Unit, Unit>), null, (new Function0<Unit>(this, store) {
            {
               super(0);
               this.this$0 = `$receiver`;
               this.$store = `$store`;
            }

            public final void invoke() {
               FileBackedPokemonStoreFactory.access$getDirtyStores$p(this.this$0).add(this.$store);
            }
         }) as Function0, 1, null
      );
   }

   public override fun shutdown() {
      this.saveSubscription.unsubscribe();
      this.saveAll();
      this.saveExecutor.shutdown();
   }

   public override fun onPlayerDisconnect(playerID: UUID) {
      val `$this$forEach$iv`: java.lang.Iterable = this.dirtyStores;
      val `element$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if ((cache as PokemonStore).getUuid() == playerID) {
            `element$iv`.add(cache);
         }
      }

      for (Object element$ivx : $this$filter$iv) {
         this.save(`element$ivx` as PokemonStore<?>);
      }

      for (Entry element$ivx : this.storeCaches.entrySet()) {
         (`element$ivx`.getValue() as FileBackedPokemonStoreFactory.StoreCache).getCacheMap().remove(playerID);
      }
   }

   override fun getPCForPlayer(player: ServerPlayer, pcBlockEntity: PCBlockEntity): PCStore? {
      return PokemonStoreFactory.DefaultImpls.getPCForPlayer(this, player, pcBlockEntity);
   }

   @JvmStatic
   fun `save$lambda$2`(`this$0`: FileBackedPokemonStoreFactory, `$serialized`: SerializedStore) {
      `this$0`.adapter.save(`$serialized`.getStoreClass(), `$serialized`.getUuid(), (S)`$serialized`.getSerializedForm());
   }

   @JvmStatic
   fun `saveAll$lambda$5`(`$serializedStores`: java.util.List, `this$0`: FileBackedPokemonStoreFactory) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         `this$0`.adapter
            .save(
               (`element$iv` as SerializedStore).getStoreClass(),
               (`element$iv` as SerializedStore).getUuid(),
               (S)(`element$iv` as SerializedStore).getSerializedForm()
            );
      }

      Cobblemon.INSTANCE.getLOGGER().debug("Saved ${`$serializedStores`.size()} Pokémon stores.");
   }

   protected inner class StoreCache<E extends StorePosition, T extends PokemonStore<E>> {
      public final val cacheMap: MutableMap<UUID, Any>

      init {
         this.this$0 = `this$0`;
         this.cacheMap = new LinkedHashMap<>();
      }
   }
}
