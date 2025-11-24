/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.comparisons.ComparisonsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.InvalidSpeciesException;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.RemoveClientPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.SwapClientPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.InitializePartyPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.MoveClientPartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.SetPartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u000e\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010e\u001a\u00020Q\u00a2\u0006\u0004\bi\u0010jJ\r\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0005J\u001a\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0007\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\t\u0010\rJ\u0015\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u000eH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0011\u0010\u0011\u001a\u0004\u0018\u00010\u0002H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\r\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001d\u0010\u0019\u001a\u0010\u0012\f\u0012\n \u0018*\u0004\u0018\u00010\u00170\u00170\u0016H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\r\u0010\u001b\u001a\u00020\u0003\u00a2\u0006\u0004\b\u001b\u0010\u0005J\u000f\u0010\u001c\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u0005J\u0017\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\b0 H\u0096\u0002\u00a2\u0006\u0004\b!\u0010\"J\u0017\u0010%\u001a\u00020\u00002\u0006\u0010$\u001a\u00020#H\u0016\u00a2\u0006\u0004\b%\u0010&J\u0017\u0010)\u001a\u00020\u00002\u0006\u0010(\u001a\u00020'H\u0016\u00a2\u0006\u0004\b)\u0010*J\u001d\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00020+2\u0006\u0010(\u001a\u00020'H\u0016\u00a2\u0006\u0004\b,\u0010-J3\u00102\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0016\"\b\b\u0000\u0010/*\u00020.2\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00028\u000000\u00a2\u0006\u0004\b2\u00103J\r\u00104\u001a\u00020\u000b\u00a2\u0006\u0004\b4\u00105J\u0017\u00107\u001a\u00020\u001d2\u0006\u00106\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b7\u00108J\r\u00109\u001a\u00020\u0003\u00a2\u0006\u0004\b9\u0010\u0005J\u001f\u0010:\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010(\u001a\u00020'H\u0016\u00a2\u0006\u0004\b:\u0010;J\u0017\u0010<\u001a\u00020#2\u0006\u0010$\u001a\u00020#H\u0016\u00a2\u0006\u0004\b<\u0010=J\u0017\u0010>\u001a\u00020'2\u0006\u0010(\u001a\u00020'H\u0016\u00a2\u0006\u0004\b>\u0010?J\u0017\u0010A\u001a\u00020\u00032\u0006\u0010@\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\bA\u0010BJ \u0010C\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u00106\u001a\u00020\bH\u0096\u0002\u00a2\u0006\u0004\bC\u0010DJ\u001d\u0010C\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u000b2\u0006\u00106\u001a\u00020\b\u00a2\u0006\u0004\bC\u0010EJ!\u0010F\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00022\b\u00106\u001a\u0004\u0018\u00010\bH\u0014\u00a2\u0006\u0004\bF\u0010DJ\r\u0010G\u001a\u00020\u000b\u00a2\u0006\u0004\bG\u00105J\u001f\u0010J\u001a\u00020\u00032\u0006\u0010H\u001a\u00020\u00022\u0006\u0010I\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\bJ\u0010KJ\u001d\u0010J\u001a\u00020\u00032\u0006\u0010L\u001a\u00020\u000b2\u0006\u0010M\u001a\u00020\u000b\u00a2\u0006\u0004\bJ\u0010NJ3\u0010T\u001a\b\u0012\u0004\u0012\u00020S0\u00162\b\b\u0002\u0010O\u001a\u00020\u001d2\b\b\u0002\u0010P\u001a\u00020\u001d2\n\b\u0002\u0010R\u001a\u0004\u0018\u00010Q\u00a2\u0006\u0004\bT\u0010UJ\u0015\u0010V\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0016\u00a2\u0006\u0004\bV\u0010\u001aJ\u0015\u0010W\u001a\u00020\u00032\u0006\u00106\u001a\u00020\b\u00a2\u0006\u0004\bW\u0010XR \u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00030Y8\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\bZ\u0010[\u001a\u0004\b\u000f\u0010\\R(\u0010^\u001a\b\u0012\u0004\u0012\u00020Q0]8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b^\u0010_\u001a\u0004\b`\u0010\u001a\"\u0004\ba\u0010bR\"\u0010c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0]8\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\bc\u0010_\u001a\u0004\bd\u0010\u001aR\u001a\u0010e\u001a\u00020Q8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\be\u0010f\u001a\u0004\bg\u0010h\u00a8\u0006k"}, d2={"Lcom/cobblemon/mod/common/api/storage/party/PartyStore;", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "", "clearParty", "()V", "didSleep", "position", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "get", "(Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "slot", "(I)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/reactive/Observable;", "getAnyChangeObservable", "()Lcom/cobblemon/mod/common/api/reactive/Observable;", "getFirstAvailablePosition", "()Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "", "getHealingRemainderPercent", "()F", "", "Lnet/minecraft/server/level/ServerPlayer;", "kotlin.jvm.PlatformType", "getObservingPlayers", "()Ljava/util/List;", "heal", "initialize", "", "isValidPosition", "(Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;)Z", "", "iterator", "()Ljava/util/Iterator;", "Lcom/google/gson/JsonObject;", "json", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/storage/party/PartyStore;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/storage/party/PartyStore;", "Lcom/cobblemon/mod/common/api/storage/StoreCoordinates;", "loadPositionFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/storage/StoreCoordinates;", "", "T", "Lkotlin/Function1;", "mapper", "mapNullPreserving", "(Lkotlin/jvm/functions/Function1;)Ljava/util/List;", "occupied", "()I", "pokemon", "remove", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "removeDuplicates", "savePositionToNBT", "(Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;Lnet/minecraft/nbt/CompoundTag;)V", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "player", "sendTo", "(Lnet/minecraft/server/level/ServerPlayer;)V", "set", "(Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "(ILcom/cobblemon/mod/common/pokemon/Pokemon;)V", "setAtPosition", "size", "position1", "position2", "swap", "(Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;)V", "slot1", "slot2", "(II)V", "clone", "checkHealth", "Ljava/util/UUID;", "leadingPokemon", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "toBattleTeam", "(ZZLjava/util/UUID;)Ljava/util/List;", "toGappyList", "trackPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "anyChangeObservable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "observerUUIDs", "Ljava/util/List;", "getObserverUUIDs", "setObserverUUIDs", "(Ljava/util/List;)V", "slots", "getSlots", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "(Ljava/util/UUID;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPartyStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartyStore.kt\ncom/cobblemon/mod/common/api/storage/party/PartyStore\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,275:1\n1#2:276\n1#2:301\n766#3:277\n857#3,2:278\n1864#3,3:280\n1549#3:283\n1620#3,3:284\n1855#3,2:287\n1855#3,2:289\n1603#3,9:291\n1855#3:300\n1856#3:302\n1612#3:303\n1045#3:304\n1855#3,2:305\n*S KotlinDebug\n*F\n+ 1 PartyStore.kt\ncom/cobblemon/mod/common/api/storage/party/PartyStore\n*L\n258#1:301\n90#1:277\n90#1:278,2\n100#1:280,3\n153#1:283\n153#1:284,3\n243#1:287,2\n247#1:289,2\n258#1:291,9\n258#1:300\n258#1:302\n258#1:303\n265#1:304\n268#1:305,2\n*E\n"})
public class PartyStore
extends PokemonStore<PartyPosition> {
    @NotNull
    private final UUID uuid;
    @NotNull
    private final List<Pokemon> slots;
    @NotNull
    private final SimpleObservable<Unit> anyChangeObservable;
    @NotNull
    private List<UUID> observerUUIDs;

    public PartyStore(@NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        this.uuid = uuid2;
        int n = 6;
        PartyStore partyStore = this;
        ArrayList<Object> arrayList = new ArrayList<Object>(n);
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int n4 = n3 = n2++;
            ArrayList<Object> arrayList2 = arrayList;
            boolean bl = false;
            arrayList2.add(null);
        }
        partyStore.slots = arrayList;
        this.anyChangeObservable = new SimpleObservable();
        this.observerUUIDs = new ArrayList();
    }

    @Override
    @NotNull
    public UUID getUuid() {
        return this.uuid;
    }

    @NotNull
    protected final List<Pokemon> getSlots() {
        return this.slots;
    }

    @NotNull
    protected final SimpleObservable<Unit> getAnyChangeObservable() {
        return this.anyChangeObservable;
    }

    @NotNull
    public final List<UUID> getObserverUUIDs() {
        return this.observerUUIDs;
    }

    public final void setObserverUUIDs(@NotNull List<UUID> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.observerUUIDs = list;
    }

    @Override
    @NotNull
    public Iterator<Pokemon> iterator() {
        return CollectionsKt.filterNotNull((Iterable)this.slots).iterator();
    }

    @Override
    @Nullable
    public final Pokemon get(int slot) {
        Pokemon pokemon;
        Integer n = slot;
        int it = ((Number)n).intValue();
        boolean bl = false;
        Integer n2 = it < this.slots.size() && it >= 0 ? n : null;
        if (n2 != null) {
            it = ((Number)n2).intValue();
            boolean bl2 = false;
            pokemon = this.slots.get(it);
        } else {
            pokemon = null;
        }
        return pokemon;
    }

    @Override
    @Nullable
    public Pokemon get(@NotNull PartyPosition position) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        return this.get(position.getSlot());
    }

    @Override
    public final void set(int slot, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.set(new PartyPosition(slot), pokemon);
    }

    @Override
    protected void setAtPosition(@NotNull PartyPosition position, @Nullable Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        if (position.getSlot() >= this.slots.size()) {
            throw new IllegalArgumentException("Slot position is out of bounds");
        }
        this.slots.set(position.getSlot(), pokemon);
        if (pokemon != null) {
            StoreCoordinates<?> storeCoordinates = pokemon.getStoreCoordinates().get();
            if (!Intrinsics.areEqual(storeCoordinates != null ? storeCoordinates.getStore() : null, (Object)this)) {
                this.trackPokemon(pokemon);
            }
        }
        Unit[] unitArray = new Unit[]{Unit.INSTANCE};
        this.anyChangeObservable.emit((Unit[])unitArray);
    }

    public final void trackPokemon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Observable.DefaultImpls.subscribe$default(pokemon.getChangeObservable().pipe(Observable.Companion.stopAfter((Function1)new Function1<Pokemon, Boolean>(pokemon, this){
            final /* synthetic */ Pokemon $pokemon;
            final /* synthetic */ PartyStore this$0;
            {
                this.$pokemon = $pokemon;
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull Pokemon it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                StoreCoordinates<?> storeCoordinates = this.$pokemon.getStoreCoordinates().get();
                return !Intrinsics.areEqual(storeCoordinates != null ? storeCoordinates.getStore() : null, (Object)this.this$0);
            }
        })), null, (Function1)new Function1<Pokemon, Unit>(this){
            final /* synthetic */ PartyStore this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull Pokemon it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                Unit[] unitArray = new Unit[]{Unit.INSTANCE};
                this.this$0.getAnyChangeObservable().emit((Unit[])unitArray);
            }
        }, 1, null);
    }

    @Override
    @Nullable
    public PartyPosition getFirstAvailablePosition() {
        int n = this.slots.size();
        for (int i = 0; i < n; ++i) {
            if (this.slots.get(i) != null) continue;
            return new PartyPosition(i);
        }
        return null;
    }

    @Override
    public boolean isValidPosition(@NotNull PartyPosition position) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        int n = this.slots.size();
        int n2 = position.getSlot();
        return 0 <= n2 ? n2 < n : false;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public List<ServerPlayer> getObservingPlayers() {
        List list;
        Object object = DistributionUtilsKt.server();
        if (object != null && (object = object.m_6846_()) != null && (object = object.m_11314_()) != null) {
            void $this$filterTo$iv$iv;
            Iterable $this$filter$iv = (Iterable)object;
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                ServerPlayer it = (ServerPlayer)element$iv$iv;
                boolean bl = false;
                if (!this.observerUUIDs.contains(it.m_20148_())) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            list = (List)destination$iv$iv;
        } else {
            list = CollectionsKt.emptyList();
        }
        return list;
    }

    public final int size() {
        return this.slots.size();
    }

    public final int occupied() {
        return ((Collection)CollectionsKt.filterNotNull((Iterable)this.slots)).size();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void sendTo(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        CobblemonNetwork.INSTANCE.sendPacket(player, new InitializePartyPacket(false, this.getUuid(), this.slots.size()));
        Iterable $this$forEachIndexed$iv = this.slots;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void pokemon;
            int n;
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Pokemon pokemon2 = (Pokemon)item$iv;
            int index = n;
            boolean bl = false;
            if (pokemon == null) continue;
            CobblemonNetwork.INSTANCE.sendPacket(player, new SetPartyPokemonPacket(this.getUuid(), new PartyPosition(index), (Pokemon)pokemon));
        }
    }

    @Override
    public void set(@NotNull PartyPosition position, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        super.set((StorePosition)position, pokemon);
        this.sendPacketToObservers(new SetPartyPokemonPacket(this.getUuid(), position, pokemon));
    }

    @Override
    public boolean remove(@NotNull Pokemon pokemon) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        if (super.remove(pokemon)) {
            PokemonStore pokemonStore = this;
            UUID uUID = pokemon.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
            this.sendPacketToObservers(new RemoveClientPokemonPacket(pokemonStore, uUID));
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }

    @Override
    public final void swap(int slot1, int slot2) {
        block4: {
            block3: {
                boolean bl = 0 <= slot1 ? slot1 < this.slots.size() : false;
                if (!bl) break block3;
                if (0 <= slot2 ? slot2 < this.slots.size() : false) break block4;
            }
            return;
        }
        this.swap(new PartyPosition(slot1), new PartyPosition(slot2));
    }

    @Override
    public void swap(@NotNull PartyPosition position1, @NotNull PartyPosition position2) {
        Intrinsics.checkNotNullParameter((Object)position1, (String)"position1");
        Intrinsics.checkNotNullParameter((Object)position2, (String)"position2");
        Pokemon pokemon1 = this.get(position1);
        Pokemon pokemon2 = this.get(position2);
        super.swap((StorePosition)position1, (StorePosition)position2);
        if (pokemon1 != null && pokemon2 != null) {
            PokemonStore pokemonStore = this;
            UUID uUID = pokemon1.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon1.uuid");
            UUID uUID2 = pokemon2.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"pokemon2.uuid");
            this.sendPacketToObservers(new SwapClientPokemonPacket(pokemonStore, uUID, uUID2));
        } else if (pokemon1 != null || pokemon2 != null) {
            PartyPosition newPosition = pokemon1 == null ? position1 : position2;
            Pokemon pokemon = pokemon1;
            if (pokemon == null) {
                Pokemon pokemon3 = pokemon2;
                pokemon = pokemon3;
                Intrinsics.checkNotNull((Object)pokemon3);
            }
            Pokemon pokemon4 = pokemon;
            UUID uUID = this.getUuid();
            UUID uUID3 = pokemon4.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID3, (String)"pokemon.uuid");
            this.sendPacketToObservers(new MoveClientPartyPokemonPacket(uUID, uUID3, newPosition));
        }
    }

    @Override
    public void initialize() {
        int n = this.slots.size();
        for (int slot = 0; slot < n; ++slot) {
            Pokemon pokemon;
            if (this.get(slot) == null) continue;
            pokemon.getStoreCoordinates().set(new StoreCoordinates<StorePosition>(this, new PartyPosition(slot)));
            this.trackPokemon(pokemon);
        }
    }

    @NotNull
    public final List<Pokemon> toGappyList() {
        return CollectionsKt.toList((Iterable)this.slots);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final <T> List<T> mapNullPreserving(@NotNull Function1<? super Pokemon, ? extends T> mapper) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(mapper, (String)"mapper");
        Iterable $this$map$iv = this.toGappyList();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            Pokemon pokemon = (Pokemon)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            void v0 = it;
            collection.add(v0 != null ? mapper.invoke((Object)v0) : null);
        }
        return (List)destination$iv$iv;
    }

    @Override
    @NotNull
    public CompoundTag saveToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.m_128405_("SlotCount", this.slots.size());
        int n = this.slots.size();
        for (int slot = 0; slot < n; ++slot) {
            Pokemon pokemon = this.get(slot);
            if (pokemon == null) continue;
            nbt.m_128365_("Slot" + slot, (Tag)pokemon.saveToNBT(new CompoundTag()));
        }
        return nbt;
    }

    @NotNull
    public PartyStore loadFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        int slotCount = nbt.m_128451_("SlotCount");
        while (slotCount > this.slots.size()) {
            CollectionsKt.removeLast(this.slots);
        }
        while (slotCount < this.slots.size()) {
            this.slots.add(null);
        }
        int n = this.slots.size();
        for (int slot = 0; slot < n; ++slot) {
            CompoundTag pokemonNBT = nbt.m_128469_("Slot" + slot);
            try {
                if (pokemonNBT.m_128456_()) continue;
                Pokemon pokemon = new Pokemon();
                Intrinsics.checkNotNullExpressionValue((Object)pokemonNBT, (String)"pokemonNBT");
                this.slots.set(slot, pokemon.loadFromNBT(pokemonNBT));
                continue;
            }
            catch (InvalidSpeciesException invalidSpeciesException) {
                Intrinsics.checkNotNullExpressionValue((Object)pokemonNBT, (String)"pokemonNBT");
                this.handleInvalidSpeciesNBT(pokemonNBT);
            }
        }
        this.removeDuplicates();
        return this;
    }

    @Override
    @NotNull
    public JsonObject saveToJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty("SlotCount", (Number)this.slots.size());
        int n = this.slots.size();
        for (int slot = 0; slot < n; ++slot) {
            Pokemon pokemon = this.get(slot);
            if (pokemon == null) continue;
            json.add("Slot" + slot, (JsonElement)pokemon.saveToJSON(new JsonObject()));
        }
        return json;
    }

    @NotNull
    public PartyStore loadFromJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        int slotCount = json.get("SlotCount").getAsInt();
        while (slotCount > this.slots.size()) {
            CollectionsKt.removeLast(this.slots);
        }
        while (slotCount < this.slots.size()) {
            this.slots.add(null);
        }
        int n = this.slots.size();
        for (int slot = 0; slot < n; ++slot) {
            String key = "Slot" + slot;
            if (!json.has(key)) continue;
            JsonObject pokemonJSON = json.get(key).getAsJsonObject();
            try {
                Pokemon pokemon = new Pokemon();
                Intrinsics.checkNotNullExpressionValue((Object)pokemonJSON, (String)"pokemonJSON");
                this.slots.set(slot, pokemon.loadFromJSON(pokemonJSON));
                continue;
            }
            catch (InvalidSpeciesException invalidSpeciesException) {
                Intrinsics.checkNotNullExpressionValue((Object)pokemonJSON, (String)"pokemonJSON");
                this.handleInvalidSpeciesJSON(pokemonJSON);
            }
        }
        this.removeDuplicates();
        return this;
    }

    public final void removeDuplicates() {
        List knownUUIDs = new ArrayList();
        int n = this.slots.size();
        for (int slot = 0; slot < n; ++slot) {
            Pokemon pokemon;
            if (this.get(slot) == null) continue;
            if (!knownUUIDs.contains(pokemon.getUuid())) {
                UUID uUID = pokemon.getUuid();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
                knownUUIDs.add(uUID);
                continue;
            }
            this.slots.set(slot, null);
            Unit[] unitArray = new Unit[]{Unit.INSTANCE};
            this.anyChangeObservable.emit((Unit[])unitArray);
        }
    }

    @Override
    @NotNull
    public StoreCoordinates<PartyPosition> loadPositionFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        byte slot = nbt.m_128445_("Slot");
        return new StoreCoordinates<StorePosition>(this, new PartyPosition(slot));
    }

    @Override
    public void savePositionToNBT(@NotNull PartyPosition position, @NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.m_128344_("Slot", (byte)position.getSlot());
    }

    @Override
    @NotNull
    public Observable<Unit> getAnyChangeObservable() {
        return this.anyChangeObservable;
    }

    public final void heal() {
        Iterable $this$forEach$iv = this;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Pokemon it = (Pokemon)element$iv;
            boolean bl = false;
            it.heal();
        }
    }

    public final void didSleep() {
        Iterable $this$forEach$iv = this;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Pokemon it = (Pokemon)element$iv;
            boolean bl = false;
            it.didSleep();
        }
    }

    public final float getHealingRemainderPercent() {
        float totalPercent = 0.0f;
        for (Pokemon pokemon : this) {
            totalPercent += 1.0f - (float)pokemon.getCurrentHealth() / (float)pokemon.getHp();
        }
        return totalPercent;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<BattlePokemon> toBattleTeam(boolean clone, boolean checkHealth, @Nullable UUID leadingPokemon) {
        void $this$sortedBy$iv;
        void $this$mapNotNullTo$iv$iv;
        Iterable $this$mapNotNull$iv = this;
        boolean $i$f$mapNotNull = false;
        Iterable iterable = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv$iv$iv.iterator();
        while (iterator.hasNext()) {
            BattlePokemon it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator.next();
            boolean bl = false;
            Pokemon it = (Pokemon)element$iv$iv;
            boolean bl2 = false;
            if ((clone ? BattlePokemon.Companion.safeCopyOf(it) : BattlePokemon.Companion.playerOwned(it)) == null) continue;
            it$iv$iv = it$iv$iv;
            boolean bl3 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        $this$mapNotNull$iv = (List)destination$iv$iv;
        boolean $i$f$sortedBy = false;
        return CollectionsKt.sortedWith((Iterable)$this$sortedBy$iv, (Comparator)new Comparator(leadingPokemon, this){
            final /* synthetic */ UUID $leadingPokemon$inlined;
            final /* synthetic */ PartyStore this$0;
            {
                this.$leadingPokemon$inlined = uUID;
                this.this$0 = partyStore;
            }

            public final int compare(T a, T b) {
                BattlePokemon it = (BattlePokemon)a;
                boolean bl = false;
                int n = Intrinsics.areEqual((Object)it.getUuid(), (Object)this.$leadingPokemon$inlined) ? 0 : CollectionsKt.indexOf((Iterable)this.this$0, (Object)it.getOriginalPokemon()) + 1;
                it = (BattlePokemon)b;
                Comparable comparable = Integer.valueOf(n);
                bl = false;
                return ComparisonsKt.compareValues((Comparable)comparable, (Comparable)Integer.valueOf(Intrinsics.areEqual((Object)it.getUuid(), (Object)this.$leadingPokemon$inlined) ? 0 : CollectionsKt.indexOf((Iterable)this.this$0, (Object)it.getOriginalPokemon()) + 1));
            }
        });
    }

    public static /* synthetic */ List toBattleTeam$default(PartyStore partyStore, boolean bl, boolean bl2, UUID uUID, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toBattleTeam");
        }
        if ((n & 1) != 0) {
            bl = false;
        }
        if ((n & 2) != 0) {
            bl2 = true;
        }
        if ((n & 4) != 0) {
            uUID = null;
        }
        return partyStore.toBattleTeam(bl, bl2, uUID);
    }

    public final void clearParty() {
        Iterable $this$forEach$iv = this;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Pokemon it = (Pokemon)element$iv;
            boolean bl = false;
            it.tryRecallWithAnimation();
            this.remove(it);
        }
    }
}

