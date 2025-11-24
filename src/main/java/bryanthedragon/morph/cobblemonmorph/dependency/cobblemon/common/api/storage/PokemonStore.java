/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.markers.KMappedMarker
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMappedMarker;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0011\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00020\u00040\u0003B\u0007\u00a2\u0006\u0004\bD\u0010!J\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001a\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\t\u001a\u00028\u0000H\u00a6\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\fH\u0086\u0002\u00a2\u0006\u0004\b\n\u0010\u000eJ\u0015\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH&\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0011\u0010\u0013\u001a\u0004\u0018\u00018\u0000H&\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0015\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u0003H&\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u001cH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010 \u001a\u00020\u0010H&\u00a2\u0006\u0004\b \u0010!J\u0017\u0010\"\u001a\u00020\u00062\u0006\u0010\t\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\"\u0010#J\u001d\u0010$\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0019\u001a\u00020\u0018H&\u00a2\u0006\u0004\b$\u0010%J\u001d\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001d\u001a\u00020\u001cH&\u00a2\u0006\u0004\b&\u0010'J\u001d\u0010)\u001a\b\u0012\u0004\u0012\u00028\u00000(2\u0006\u0010\u001d\u001a\u00020\u001cH&\u00a2\u0006\u0004\b)\u0010*J\u001d\u0010+\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\t\u001a\u00028\u0000\u00a2\u0006\u0004\b+\u0010,J\u0017\u0010-\u001a\u00020\u00062\u0006\u0010\t\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b-\u0010#J\u0017\u0010-\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b-\u0010\bJ\u001f\u0010.\u001a\u00020\u00102\u0006\u0010\t\u001a\u00028\u00002\u0006\u0010\u001d\u001a\u00020\u001cH&\u00a2\u0006\u0004\b.\u0010/J\u0017\u00100\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018H&\u00a2\u0006\u0004\b0\u00101J\u0017\u00102\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cH&\u00a2\u0006\u0004\b2\u00103J\u001b\u00106\u001a\u00020\u00102\n\u00105\u001a\u0006\u0012\u0002\b\u000304H\u0016\u00a2\u0006\u0004\b6\u00107J\u0017\u00109\u001a\u00020\u00102\u0006\u00108\u001a\u00020\u0015H&\u00a2\u0006\u0004\b9\u0010:J \u0010;\u001a\u00020\u00102\u0006\u0010\t\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u0004H\u0096\u0002\u00a2\u0006\u0004\b;\u0010<J!\u0010=\u001a\u00020\u00102\u0006\u0010\t\u001a\u00028\u00002\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H$\u00a2\u0006\u0004\b=\u0010<J\u001f\u0010@\u001a\u00020\u00102\u0006\u0010>\u001a\u00028\u00002\u0006\u0010?\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b@\u0010AR\u0014\u0010\r\u001a\u00020\f8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bB\u0010C\u00a8\u0006E"}, d2={"Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "T", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "add", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "position", "get", "(Lcom/cobblemon/mod/common/api/storage/StorePosition;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Ljava/util/UUID;", "uuid", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/reactive/Observable;", "", "getAnyChangeObservable", "()Lcom/cobblemon/mod/common/api/reactive/Observable;", "getFirstAvailablePosition", "()Lcom/cobblemon/mod/common/api/storage/StorePosition;", "Lnet/minecraft/server/level/ServerPlayer;", "getObservingPlayers", "()Ljava/lang/Iterable;", "Lcom/google/gson/JsonObject;", "json", "handleInvalidSpeciesJSON", "(Lcom/google/gson/JsonObject;)V", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "handleInvalidSpeciesNBT", "(Lnet/minecraft/nbt/CompoundTag;)V", "initialize", "()V", "isValidPosition", "(Lcom/cobblemon/mod/common/api/storage/StorePosition;)Z", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "Lcom/cobblemon/mod/common/api/storage/StoreCoordinates;", "loadPositionFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/storage/StoreCoordinates;", "move", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/storage/StorePosition;)V", "remove", "savePositionToNBT", "(Lcom/cobblemon/mod/common/api/storage/StorePosition;Lnet/minecraft/nbt/CompoundTag;)V", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "packet", "sendPacketToObservers", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "player", "sendTo", "(Lnet/minecraft/server/level/ServerPlayer;)V", "set", "(Lcom/cobblemon/mod/common/api/storage/StorePosition;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "setAtPosition", "position1", "position2", "swap", "(Lcom/cobblemon/mod/common/api/storage/StorePosition;Lcom/cobblemon/mod/common/api/storage/StorePosition;)V", "getUuid", "()Ljava/util/UUID;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonStore.kt\ncom/cobblemon/mod/common/api/storage/PokemonStore\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,164:1\n1855#2,2:165\n1#3:167\n*S KotlinDebug\n*F\n+ 1 PokemonStore.kt\ncom/cobblemon/mod/common/api/storage/PokemonStore\n*L\n68#1:165,2\n*E\n"})
public abstract class PokemonStore<T extends StorePosition>
implements Iterable<Pokemon>,
KMappedMarker {
    @NotNull
    public abstract UUID getUuid();

    @Nullable
    public abstract Pokemon get(@NotNull T var1);

    @Nullable
    public abstract T getFirstAvailablePosition();

    @NotNull
    public abstract Iterable<ServerPlayer> getObservingPlayers();

    public abstract void sendTo(@NotNull ServerPlayer var1);

    public abstract void initialize();

    protected abstract void setAtPosition(@NotNull T var1, @Nullable Pokemon var2);

    public abstract boolean isValidPosition(@NotNull T var1);

    public void sendPacketToObservers(@NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        Iterable<ServerPlayer> $this$forEach$iv = this.getObservingPlayers();
        boolean $i$f$forEach = false;
        Iterator<ServerPlayer> iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            ServerPlayer element$iv;
            ServerPlayer it = element$iv = iterator.next();
            boolean bl = false;
            CobblemonNetwork.INSTANCE.sendPacket(it, packet);
        }
    }

    public boolean add(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.remove(pokemon);
        T t = this.getFirstAvailablePosition();
        if (t == null) {
            return false;
        }
        T position = t;
        this.set(position, pokemon);
        return true;
    }

    public void set(@NotNull T position, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter(position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Pokemon existing = this.get(position);
        if (Intrinsics.areEqual((Object)existing, (Object)pokemon)) {
            return;
        }
        if (!Intrinsics.areEqual((Object)existing, (Object)pokemon) && existing != null) {
            this.remove(existing);
        }
        this.setAtPosition(position, pokemon);
        pokemon.getStoreCoordinates().set(new StoreCoordinates<T>(this, position));
    }

    public void swap(@NotNull T position1, @NotNull T position2) {
        block1: {
            Object object;
            Intrinsics.checkNotNullParameter(position1, (String)"position1");
            Intrinsics.checkNotNullParameter(position2, (String)"position2");
            Pokemon pokemon1 = this.get(position1);
            Pokemon pokemon2 = this.get(position2);
            this.setAtPosition(position1, pokemon2);
            this.setAtPosition(position2, pokemon1);
            Object object2 = pokemon1;
            if (object2 != null && (object2 = ((Pokemon)object2).getStoreCoordinates()) != null) {
                ((SettableObservable)object2).set(new StoreCoordinates<T>(this, position2));
            }
            if ((object = pokemon2) == null || (object = ((Pokemon)object).getStoreCoordinates()) == null) break block1;
            ((SettableObservable)object).set(new StoreCoordinates<T>(this, position1));
        }
    }

    public final void move(@NotNull Pokemon pokemon, @NotNull T position) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(position, (String)"position");
        StoreCoordinates<?> storeCoordinates = pokemon.getStoreCoordinates().get();
        if (storeCoordinates == null) {
            return;
        }
        StoreCoordinates<?> currentPosition = storeCoordinates;
        if (!Intrinsics.areEqual(currentPosition.getStore(), (Object)this)) {
            return;
        }
        Object obj = currentPosition.getPosition();
        Intrinsics.checkNotNull(obj, (String)"null cannot be cast to non-null type T of bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore");
        this.swap(obj, position);
    }

    public boolean remove(@NotNull T position) {
        Intrinsics.checkNotNullParameter(position, (String)"position");
        Pokemon pokemon = this.get(position);
        if (pokemon != null) {
            return this.remove(pokemon);
        }
        return false;
    }

    public boolean remove(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        StoreCoordinates<?> storeCoordinates = pokemon.getStoreCoordinates().get();
        if (storeCoordinates == null) {
            return false;
        }
        StoreCoordinates<?> currentPosition = storeCoordinates;
        if (!Intrinsics.areEqual(currentPosition.getStore(), (Object)this)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.get(currentPosition.getPosition()), (Object)pokemon)) {
            return false;
        }
        pokemon.recall();
        pokemon.getStoreCoordinates().set(null);
        this.setAtPosition(currentPosition.getPosition(), null);
        return true;
    }

    @Nullable
    public final Pokemon get(@NotNull UUID uuid2) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
            Iterable iterable = this;
            for (Object t : iterable) {
                Pokemon it = (Pokemon)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)uuid2)) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    public void handleInvalidSpeciesNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
    }

    @NotNull
    public abstract CompoundTag saveToNBT(@NotNull CompoundTag var1);

    @NotNull
    public abstract PokemonStore<T> loadFromNBT(@NotNull CompoundTag var1);

    public void handleInvalidSpeciesJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
    }

    @NotNull
    public abstract JsonObject saveToJSON(@NotNull JsonObject var1);

    @NotNull
    public abstract PokemonStore<T> loadFromJSON(@NotNull JsonObject var1);

    public abstract void savePositionToNBT(@NotNull T var1, @NotNull CompoundTag var2);

    @NotNull
    public abstract StoreCoordinates<T> loadPositionFromNBT(@NotNull CompoundTag var1);

    @NotNull
    public abstract Observable<Unit> getAnyChangeObservable();

    @Override
    public Iterator<Pokemon> iterator() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}

