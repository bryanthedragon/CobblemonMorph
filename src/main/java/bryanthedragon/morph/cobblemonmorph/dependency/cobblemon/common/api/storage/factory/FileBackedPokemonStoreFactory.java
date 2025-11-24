/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.factory;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.ObservableSubscription;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.SerializedStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.FileStoreAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.factory.PokemonStoreFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PCBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010#\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\b\b\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001VBI\u0012\f\u0010)\u001a\b\u0012\u0004\u0012\u00028\u00000(\u0012\u0006\u0010-\u001a\u00020\u001c\u0012\u0014\b\u0002\u00104\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00110\u0014\u0012\u0014\b\u0002\u0010?\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000e0\u0014\u00a2\u0006\u0004\bT\u0010UJA\u0010\u000b\u001a\u0004\u0018\u00018\u0002\"\b\b\u0001\u0010\u0004*\u00020\u0003\"\u000e\b\u0002\u0010\u0006*\b\u0012\u0004\u0012\u00028\u00010\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0019\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\r\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u00112\u0006\u0010\r\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013JU\u0010\u0016\u001a\u0004\u0018\u00018\u0002\"\b\b\u0001\u0010\u0004*\u00020\u0003\"\u000e\b\u0002\u0010\u0006*\b\u0012\u0004\u0012\u00028\u00010\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0014\b\u0002\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00028\u00020\u0014\u00a2\u0006\u0004\b\u0016\u0010\u0017JM\u0010\u0019\u001a\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0018R\b\u0012\u0004\u0012\u00028\u00000\u0000\"\b\b\u0001\u0010\u0004*\u00020\u0003\"\u000e\b\u0002\u0010\u0006*\b\u0012\u0004\u0012\u00028\u00010\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\u0007H\u0004\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0019\u0010\u001d\u001a\u00020\u001c2\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u0005\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0017\u0010 \u001a\u00020\u001f2\u0006\u0010\r\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b \u0010!J\u0019\u0010\"\u001a\u00020\u001f2\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u0005\u00a2\u0006\u0004\b\"\u0010#J\r\u0010$\u001a\u00020\u001f\u00a2\u0006\u0004\b$\u0010%J\u000f\u0010&\u001a\u00020\u001fH\u0016\u00a2\u0006\u0004\b&\u0010%J\u0019\u0010'\u001a\u00020\u001f2\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u0005\u00a2\u0006\u0004\b'\u0010#R \u0010)\u001a\b\u0012\u0004\u0012\u00028\u00000(8\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R\u001a\u0010-\u001a\u00020\u001c8\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100R\u001e\u00102\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005018\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b2\u00103R#\u00104\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00110\u00148\u0006\u00a2\u0006\f\n\u0004\b4\u00105\u001a\u0004\b6\u00107R\"\u00109\u001a\u0002088\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b9\u0010:\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R#\u0010?\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000e0\u00148\u0006\u00a2\u0006\f\n\u0004\b?\u00105\u001a\u0004\b@\u00107R*\u0010C\u001a\n B*\u0004\u0018\u00010A0A8\u0004@\u0004X\u0084\u000e\u00a2\u0006\u0012\n\u0004\bC\u0010D\u001a\u0004\bE\u0010F\"\u0004\bG\u0010HR \u0010K\u001a\b\u0012\u0004\u0012\u00020J0I8\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\bK\u0010L\u001a\u0004\bM\u0010NRD\u0010P\u001a,\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00050\u0007\u0012\u0016\u0012\u0014\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0018R\b\u0012\u0004\u0012\u00028\u00000\u00000O8\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\bP\u0010Q\u001a\u0004\bR\u0010S\u00a8\u0006W"}, d2={"Lcom/cobblemon/mod/common/api/storage/factory/FileBackedPokemonStoreFactory;", "S", "Lcom/cobblemon/mod/common/api/storage/factory/PokemonStoreFactory;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "E", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "T", "Ljava/lang/Class;", "storeClass", "Ljava/util/UUID;", "uuid", "getCustomStore", "(Ljava/lang/Class;Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "playerID", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "getPC", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "getPlayerParty", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "Lkotlin/Function1;", "constructor", "getStore", "(Ljava/lang/Class;Ljava/util/UUID;Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "Lcom/cobblemon/mod/common/api/storage/factory/FileBackedPokemonStoreFactory$StoreCache;", "getStoreCache", "(Ljava/lang/Class;)Lcom/cobblemon/mod/common/api/storage/factory/FileBackedPokemonStoreFactory$StoreCache;", "store", "", "isCached", "(Lcom/cobblemon/mod/common/api/storage/PokemonStore;)Z", "", "onPlayerDisconnect", "(Ljava/util/UUID;)V", "save", "(Lcom/cobblemon/mod/common/api/storage/PokemonStore;)V", "saveAll", "()V", "shutdown", "track", "Lcom/cobblemon/mod/common/api/storage/adapter/flatfile/FileStoreAdapter;", "adapter", "Lcom/cobblemon/mod/common/api/storage/adapter/flatfile/FileStoreAdapter;", "getAdapter", "()Lcom/cobblemon/mod/common/api/storage/adapter/flatfile/FileStoreAdapter;", "createIfMissing", "Z", "getCreateIfMissing", "()Z", "", "dirtyStores", "Ljava/util/Set;", "partyConstructor", "Lkotlin/jvm/functions/Function1;", "getPartyConstructor", "()Lkotlin/jvm/functions/Function1;", "", "passedTicks", "I", "getPassedTicks", "()I", "setPassedTicks", "(I)V", "pcConstructor", "getPcConstructor", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "saveExecutor", "Ljava/util/concurrent/ExecutorService;", "getSaveExecutor", "()Ljava/util/concurrent/ExecutorService;", "setSaveExecutor", "(Ljava/util/concurrent/ExecutorService;)V", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "Lcom/cobblemon/mod/common/platform/events/ServerTickEvent$Pre;", "saveSubscription", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "getSaveSubscription", "()Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "", "storeCaches", "Ljava/util/Map;", "getStoreCaches", "()Ljava/util/Map;", "<init>", "(Lcom/cobblemon/mod/common/api/storage/adapter/flatfile/FileStoreAdapter;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "StoreCache", "common"})
@SourceDebugExtension(value={"SMAP\nFileBackedPokemonStoreFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FileBackedPokemonStoreFactory.kt\ncom/cobblemon/mod/common/api/storage/factory/FileBackedPokemonStoreFactory\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,130:1\n361#2,7:131\n1549#3:138\n1620#3,3:139\n766#3:142\n857#3,2:143\n1855#3,2:145\n1855#3,2:149\n215#4,2:147\n*S KotlinDebug\n*F\n+ 1 FileBackedPokemonStoreFactory.kt\ncom/cobblemon/mod/common/api/storage/factory/FileBackedPokemonStoreFactory\n*L\n55#1:131,7\n102#1:138\n102#1:139,3\n126#1:142\n126#1:143,2\n126#1:145,2\n106#1:149,2\n127#1:147,2\n*E\n"})
public class FileBackedPokemonStoreFactory<S>
implements PokemonStoreFactory {
    @NotNull
    private final FileStoreAdapter<S> adapter;
    private final boolean createIfMissing;
    @NotNull
    private final Function1<UUID, PlayerPartyStore> partyConstructor;
    @NotNull
    private final Function1<UUID, PCStore> pcConstructor;
    private int passedTicks;
    @NotNull
    private final ObservableSubscription<ServerTickEvent.Pre> saveSubscription;
    private ExecutorService saveExecutor;
    @NotNull
    private final Map<Class<? extends PokemonStore<?>>, StoreCache<?, ?>> storeCaches;
    @NotNull
    private final Set<PokemonStore<?>> dirtyStores;

    public FileBackedPokemonStoreFactory(@NotNull FileStoreAdapter<S> adapter2, boolean createIfMissing, @NotNull Function1<? super UUID, ? extends PlayerPartyStore> partyConstructor, @NotNull Function1<? super UUID, ? extends PCStore> pcConstructor) {
        Intrinsics.checkNotNullParameter(adapter2, (String)"adapter");
        Intrinsics.checkNotNullParameter(partyConstructor, (String)"partyConstructor");
        Intrinsics.checkNotNullParameter(pcConstructor, (String)"pcConstructor");
        this.adapter = adapter2;
        this.createIfMissing = createIfMissing;
        this.partyConstructor = partyConstructor;
        this.pcConstructor = pcConstructor;
        this.saveSubscription = Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_TICK_PRE, null, (Function1)new Function1<ServerTickEvent.Pre, Unit>(this){
            final /* synthetic */ FileBackedPokemonStoreFactory<S> this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull ServerTickEvent.Pre it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                FileBackedPokemonStoreFactory<S> fileBackedPokemonStoreFactory = this.this$0;
                int n = fileBackedPokemonStoreFactory.getPassedTicks();
                fileBackedPokemonStoreFactory.setPassedTicks(n + 1);
                if (this.this$0.getPassedTicks() > 20 * Cobblemon.INSTANCE.getConfig().getPokemonSaveIntervalSeconds()) {
                    this.this$0.saveAll();
                    this.this$0.setPassedTicks(0);
                }
            }
        }, 1, null);
        this.saveExecutor = Executors.newSingleThreadExecutor();
        this.storeCaches = new LinkedHashMap();
        this.dirtyStores = new LinkedHashSet();
    }

    public /* synthetic */ FileBackedPokemonStoreFactory(FileStoreAdapter fileStoreAdapter, boolean bl, Function1 function1, Function1 function12, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            function1 = 1.INSTANCE;
        }
        if ((n & 8) != 0) {
            function12 = 2.INSTANCE;
        }
        this(fileStoreAdapter, bl, (Function1<? super UUID, ? extends PlayerPartyStore>)function1, (Function1<? super UUID, ? extends PCStore>)function12);
    }

    @NotNull
    protected final FileStoreAdapter<S> getAdapter() {
        return this.adapter;
    }

    protected final boolean getCreateIfMissing() {
        return this.createIfMissing;
    }

    @NotNull
    public final Function1<UUID, PlayerPartyStore> getPartyConstructor() {
        return this.partyConstructor;
    }

    @NotNull
    public final Function1<UUID, PCStore> getPcConstructor() {
        return this.pcConstructor;
    }

    public final int getPassedTicks() {
        return this.passedTicks;
    }

    public final void setPassedTicks(int n) {
        this.passedTicks = n;
    }

    @NotNull
    protected final ObservableSubscription<ServerTickEvent.Pre> getSaveSubscription() {
        return this.saveSubscription;
    }

    protected final ExecutorService getSaveExecutor() {
        return this.saveExecutor;
    }

    protected final void setSaveExecutor(ExecutorService executorService) {
        this.saveExecutor = executorService;
    }

    @NotNull
    protected final Map<Class<? extends PokemonStore<?>>, StoreCache<?, ?>> getStoreCaches() {
        return this.storeCaches;
    }

    @NotNull
    protected final <E extends StorePosition, T extends PokemonStore<E>> StoreCache<E, T> getStoreCache(@NotNull Class<T> storeClass) {
        StoreCache<Object, Object> storeCache;
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Map<Class<PokemonStore<?>>, StoreCache<?, ?>> $this$getOrPut$iv = this.storeCaches;
        boolean $i$f$getOrPut = false;
        StoreCache<?, ?> value$iv = $this$getOrPut$iv.get(storeClass);
        if (value$iv == null) {
            boolean bl = false;
            StoreCache answer$iv = new StoreCache();
            $this$getOrPut$iv.put(storeClass, answer$iv);
            storeCache = answer$iv;
        } else {
            storeCache = value$iv;
        }
        StoreCache<?, ?> cache = storeCache;
        return cache;
    }

    @Override
    @Nullable
    public PlayerPartyStore getPlayerParty(@NotNull UUID playerID) {
        Intrinsics.checkNotNullParameter((Object)playerID, (String)"playerID");
        return this.getStore(PlayerPartyStore.class, playerID, this.partyConstructor);
    }

    @Override
    @Nullable
    public PCStore getPC(@NotNull UUID playerID) {
        Intrinsics.checkNotNullParameter((Object)playerID, (String)"playerID");
        return this.getStore(PCStore.class, playerID, this.pcConstructor);
    }

    @Override
    @Nullable
    public <E extends StorePosition, T extends PokemonStore<E>> T getCustomStore(@NotNull Class<T> storeClass, @NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        return (T)FileBackedPokemonStoreFactory.getStore$default(this, storeClass, uuid2, null, 4, null);
    }

    @Nullable
    public final <E extends StorePosition, T extends PokemonStore<E>> T getStore(@NotNull Class<T> storeClass, @NotNull UUID uuid2, @NotNull Function1<? super UUID, ? extends T> constructor) {
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter(constructor, (String)"constructor");
        Map<UUID, T> cache = this.getStoreCache(storeClass).getCacheMap();
        PokemonStore cached = (PokemonStore)cache.get(uuid2);
        if (cached != null) {
            return (T)cached;
        }
        T t = this.adapter.load(storeClass, uuid2);
        if (t == null) {
            FileBackedPokemonStoreFactory $this$getStore_u24lambda_u241 = this;
            boolean bl = false;
            PokemonStore pokemonStore = $this$getStore_u24lambda_u241.createIfMissing ? (PokemonStore)constructor.invoke((Object)uuid2) : null;
            t = pokemonStore;
            if (pokemonStore == null) {
                return null;
            }
        }
        T loaded = t;
        ((PokemonStore)loaded).initialize();
        this.track((PokemonStore<?>)loaded);
        cache.put(uuid2, loaded);
        return loaded;
    }

    public static /* synthetic */ PokemonStore getStore$default(FileBackedPokemonStoreFactory fileBackedPokemonStoreFactory, Class clazz, UUID uUID, Function1 function1, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getStore");
        }
        if ((n & 4) != 0) {
            function1 = new Function1<UUID, T>(clazz){
                final /* synthetic */ Class<T> $storeClass;
                {
                    this.$storeClass = $storeClass;
                    super(1);
                }

                public final T invoke(@NotNull UUID it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    Object[] objectArray = new Class[]{UUID.class};
                    Constructor<T> constructor = this.$storeClass.getConstructor((Class<?>[])objectArray);
                    objectArray = new Object[]{it};
                    return (T)((PokemonStore)constructor.newInstance(objectArray));
                }
            };
        }
        return fileBackedPokemonStoreFactory.getStore(clazz, uUID, function1);
    }

    public final void save(@NotNull PokemonStore<?> store) {
        Intrinsics.checkNotNullParameter(store, (String)"store");
        SerializedStore<S> serialized = new SerializedStore<S>(store.getClass(), store.getUuid(), this.adapter.serialize(store));
        this.dirtyStores.remove(store);
        this.saveExecutor.submit(() -> FileBackedPokemonStoreFactory.save$lambda$2(this, serialized));
    }

    /*
     * WARNING - void declaration
     */
    public final void saveAll() {
        void $this$mapTo$iv$iv;
        Cobblemon.INSTANCE.getLOGGER().debug("Serializing " + this.dirtyStores.size() + " Pok\u00e9mon stores.");
        Iterable $this$map$iv = this.dirtyStores;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PokemonStore pokemonStore = (PokemonStore)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(new SerializedStore<S>(it.getClass(), it.getUuid(), this.adapter.serialize(it)));
        }
        List serializedStores = (List)destination$iv$iv;
        this.dirtyStores.clear();
        Cobblemon.INSTANCE.getLOGGER().debug("Queueing save.");
        this.saveExecutor.submit(() -> FileBackedPokemonStoreFactory.saveAll$lambda$5(serializedStores, this));
    }

    public final boolean isCached(@NotNull PokemonStore<?> store) {
        Intrinsics.checkNotNullParameter(store, (String)"store");
        StoreCache<?, ?> storeCache = this.storeCaches.get(store.getClass());
        return storeCache != null && (storeCache = storeCache.getCacheMap()) != null ? storeCache.containsKey(store.getUuid()) : false;
    }

    public final void track(@NotNull PokemonStore<?> store) {
        Intrinsics.checkNotNullParameter(store, (String)"store");
        DistributionUtilsKt.subscribeOnServer$default(store.getAnyChangeObservable().pipe(Observable.Companion.emitWhile((Function1)new Function1<Unit, Boolean>(this, store){
            final /* synthetic */ FileBackedPokemonStoreFactory<S> this$0;
            final /* synthetic */ PokemonStore<?> $store;
            {
                this.this$0 = $receiver;
                this.$store = $store;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull Unit it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return this.this$0.isCached(this.$store);
            }
        })), null, (Function0)new Function0<Unit>(this, store){
            final /* synthetic */ FileBackedPokemonStoreFactory<S> this$0;
            final /* synthetic */ PokemonStore<?> $store;
            {
                this.this$0 = $receiver;
                this.$store = $store;
                super(0);
            }

            public final void invoke() {
                FileBackedPokemonStoreFactory.access$getDirtyStores$p(this.this$0).add(this.$store);
            }
        }, 1, null);
    }

    @Override
    public void shutdown() {
        this.saveSubscription.unsubscribe();
        this.saveAll();
        this.saveExecutor.shutdown();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void onPlayerDisconnect(@NotNull UUID playerID) {
        Object element$iv;
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)playerID, (String)"playerID");
        Iterable $this$filter$iv = this.dirtyStores;
        boolean $i$f$filter = false;
        Iterator iterator = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            PokemonStore it = (PokemonStore)element$iv$iv;
            boolean bl = false;
            if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)playerID)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Object $this$forEach$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            element$iv = iterator.next();
            PokemonStore p0 = (PokemonStore)element$iv;
            boolean bl = false;
            this.save(p0);
        }
        $this$forEach$iv = this.storeCaches;
        $i$f$forEach = false;
        iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Object object = element$iv = iterator.next();
            boolean bl = false;
            StoreCache cache = (StoreCache)object.getValue();
            cache.getCacheMap().remove(playerID);
        }
    }

    @Override
    @Nullable
    public PCStore getPCForPlayer(@NotNull ServerPlayer player, @NotNull PCBlockEntity pcBlockEntity) {
        return PokemonStoreFactory.DefaultImpls.getPCForPlayer(this, player, pcBlockEntity);
    }

    private static final void save$lambda$2(FileBackedPokemonStoreFactory this$0, SerializedStore $serialized) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)$serialized, (String)"$serialized");
        this$0.adapter.save($serialized.getStoreClass(), $serialized.getUuid(), $serialized.getSerializedForm());
    }

    private static final void saveAll$lambda$5(List $serializedStores, FileBackedPokemonStoreFactory this$0) {
        Intrinsics.checkNotNullParameter((Object)$serializedStores, (String)"$serializedStores");
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Iterable $this$forEach$iv = $serializedStores;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SerializedStore it = (SerializedStore)element$iv;
            boolean bl = false;
            this$0.adapter.save(it.getStoreClass(), it.getUuid(), it.getSerializedForm());
        }
        Cobblemon.INSTANCE.getLOGGER().debug("Saved " + $serializedStores.size() + " Pok\u00e9mon stores.");
    }

    public static final /* synthetic */ Set access$getDirtyStores$p(FileBackedPokemonStoreFactory $this) {
        return $this.dirtyStores;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0084\u0004\u0018\u0000*\b\b\u0001\u0010\u0002*\u00020\u0001*\u000e\b\u0002\u0010\u0004*\b\u0012\u0004\u0012\u00028\u00010\u00032\u00020\u0005B\u0007\u00a2\u0006\u0004\b\f\u0010\rR#\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/storage/factory/FileBackedPokemonStoreFactory$StoreCache;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "E", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "T", "", "", "Ljava/util/UUID;", "cacheMap", "Ljava/util/Map;", "getCacheMap", "()Ljava/util/Map;", "<init>", "(Lcom/cobblemon/mod/common/api/storage/factory/FileBackedPokemonStoreFactory;)V", "common"})
    protected final class StoreCache<E extends StorePosition, T extends PokemonStore<E>> {
        @NotNull
        private final Map<UUID, T> cacheMap = new LinkedHashMap();

        @NotNull
        public final Map<UUID, T> getCacheMap() {
            return this.cacheMap;
        }
    }
}

