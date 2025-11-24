/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.BottomlessPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.InvalidSpeciesException;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010)\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010;\u001a\u00020:\u00a2\u0006\u0004\b?\u0010@J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0086\u0002\u00a2\u0006\u0004\b\u0005\u0010\tJ\u0015\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000e\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0015\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0014\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u0019H\u0096\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u001cH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0017\u0010\"\u001a\u00020\u00002\u0006\u0010!\u001a\u00020 H\u0016\u00a2\u0006\u0004\b\"\u0010#J\u001d\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00020$2\u0006\u0010!\u001a\u00020 H\u0016\u00a2\u0006\u0004\b%\u0010&J\u001f\u0010'\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010!\u001a\u00020 H\u0016\u00a2\u0006\u0004\b'\u0010(J\u0017\u0010)\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cH\u0016\u00a2\u0006\u0004\b)\u0010*J\u0017\u0010+\u001a\u00020 2\u0006\u0010!\u001a\u00020 H\u0016\u00a2\u0006\u0004\b+\u0010,J\u0017\u0010.\u001a\u00020\u000b2\u0006\u0010-\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b.\u0010/J!\u00101\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u00022\b\u00100\u001a\u0004\u0018\u00010\u0004H\u0014\u00a2\u0006\u0004\b1\u00102R\u001d\u00100\u001a\b\u0012\u0004\u0012\u00020\u0004038\u0006\u00a2\u0006\f\n\u0004\b0\u00104\u001a\u0004\b5\u00106R\u001d\u00107\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8\u0006\u00a2\u0006\f\n\u0004\b7\u00108\u001a\u0004\b9\u0010\rR\u001a\u0010;\u001a\u00020:8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b;\u0010<\u001a\u0004\b=\u0010>\u00a8\u0006A"}, d2={"Lcom/cobblemon/mod/common/api/storage/BottomlessStore;", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "Lcom/cobblemon/mod/common/api/storage/BottomlessPosition;", "position", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "get", "(Lcom/cobblemon/mod/common/api/storage/BottomlessPosition;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "index", "(I)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "getAnyChangeObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getFirstAvailablePosition", "()Lcom/cobblemon/mod/common/api/storage/BottomlessPosition;", "", "Lnet/minecraft/server/level/ServerPlayer;", "getObservingPlayers", "()Ljava/util/Set;", "initialize", "()V", "", "isValidPosition", "(Lcom/cobblemon/mod/common/api/storage/BottomlessPosition;)Z", "", "iterator", "()Ljava/util/Iterator;", "Lcom/google/gson/JsonObject;", "json", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/storage/BottomlessStore;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/storage/BottomlessStore;", "Lcom/cobblemon/mod/common/api/storage/StoreCoordinates;", "loadPositionFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/storage/StoreCoordinates;", "savePositionToNBT", "(Lcom/cobblemon/mod/common/api/storage/BottomlessPosition;Lnet/minecraft/nbt/CompoundTag;)V", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "player", "sendTo", "(Lnet/minecraft/server/level/ServerPlayer;)V", "pokemon", "setAtPosition", "(Lcom/cobblemon/mod/common/api/storage/BottomlessPosition;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "", "Ljava/util/List;", "getPokemon", "()Ljava/util/List;", "storeChangeObservable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getStoreChangeObservable", "Ljava/util/UUID;", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "(Ljava/util/UUID;)V", "common"})
@SourceDebugExtension(value={"SMAP\nBottomlessStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BottomlessStore.kt\ncom/cobblemon/mod/common/api/storage/BottomlessStore\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,118:1\n1#2:119\n1864#3,3:120\n1864#3,3:123\n1864#3,3:126\n*S KotlinDebug\n*F\n+ 1 BottomlessStore.kt\ncom/cobblemon/mod/common/api/storage/BottomlessStore\n*L\n45#1:120,3\n54#1:123,3\n72#1:126,3\n*E\n"})
public class BottomlessStore
extends PokemonStore<BottomlessPosition> {
    @NotNull
    private final UUID uuid;
    @NotNull
    private final List<Pokemon> pokemon;
    @NotNull
    private final SimpleObservable<Unit> storeChangeObservable;

    public BottomlessStore(@NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        this.uuid = uuid2;
        this.pokemon = new ArrayList();
        this.storeChangeObservable = new SimpleObservable();
    }

    @Override
    @NotNull
    public UUID getUuid() {
        return this.uuid;
    }

    @NotNull
    public final List<Pokemon> getPokemon() {
        return this.pokemon;
    }

    @NotNull
    public final SimpleObservable<Unit> getStoreChangeObservable() {
        return this.storeChangeObservable;
    }

    @Override
    @NotNull
    public Iterator<Pokemon> iterator() {
        return this.pokemon.iterator();
    }

    @Override
    @Nullable
    public Pokemon get(@NotNull BottomlessPosition position) {
        Pokemon pokemon;
        Integer n;
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Integer n2 = position.getCurrentIndex();
        int it = ((Number)n2).intValue();
        boolean bl = false;
        Integer n3 = n = (0 <= it ? it < this.pokemon.size() : false) ? n2 : null;
        if (n != null) {
            it = ((Number)n).intValue();
            boolean bl2 = false;
            pokemon = this.pokemon.get(it);
        } else {
            pokemon = null;
        }
        return pokemon;
    }

    @Override
    @NotNull
    public BottomlessPosition getFirstAvailablePosition() {
        return new BottomlessPosition(this.pokemon.size());
    }

    @Override
    public boolean isValidPosition(@NotNull BottomlessPosition position) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        return position.getCurrentIndex() >= 0;
    }

    @Override
    @Nullable
    public final Pokemon get(int index) {
        Pokemon pokemon;
        Integer n = index;
        int it = ((Number)n).intValue();
        boolean bl = false;
        Integer n2 = (0 <= it ? it < this.pokemon.size() : false) ? n : null;
        if (n2 != null) {
            it = ((Number)n2).intValue();
            boolean bl2 = false;
            pokemon = this.pokemon.get(it);
        } else {
            pokemon = null;
        }
        return pokemon;
    }

    @NotNull
    public Set<ServerPlayer> getObservingPlayers() {
        return SetsKt.emptySet();
    }

    @Override
    public void sendTo(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void initialize() {
        Iterable $this$forEachIndexed$iv = this.pokemon;
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
            pokemon.getStoreCoordinates().set(new StoreCoordinates<StorePosition>(this, new BottomlessPosition(index)));
            Observable.DefaultImpls.subscribe$default(pokemon.getChangeObservable().pipe(Observable.Companion.stopAfter((Function1)new Function1<Pokemon, Boolean>(this){
                final /* synthetic */ BottomlessStore this$0;
                {
                    this.this$0 = $receiver;
                    super(1);
                }

                @NotNull
                public final Boolean invoke(@NotNull Pokemon it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    StoreCoordinates<?> storeCoordinates = it.getStoreCoordinates().get();
                    return !Intrinsics.areEqual(storeCoordinates != null ? storeCoordinates.getStore() : null, (Object)this.this$0);
                }
            })), null, (Function1)new Function1<Pokemon, Unit>(this){
                final /* synthetic */ BottomlessStore this$0;
                {
                    this.this$0 = $receiver;
                    super(1);
                }

                public final void invoke(@NotNull Pokemon it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    Unit[] unitArray = new Unit[]{Unit.INSTANCE};
                    this.this$0.getStoreChangeObservable().emit((Unit[])unitArray);
                }
            }, 1, null);
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public CompoundTag saveToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        Iterable $this$forEachIndexed$iv = this.pokemon;
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
            nbt.m_128365_("Slot" + index, (Tag)pokemon.saveToNBT(new CompoundTag()));
        }
        return nbt;
    }

    @NotNull
    public BottomlessStore loadFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        int i = -1;
        while (nbt.m_128441_("Slot" + ++i)) {
            CompoundTag pokemonNBT = nbt.m_128469_("Slot" + i);
            try {
                Pokemon pokemon = new Pokemon();
                Intrinsics.checkNotNullExpressionValue((Object)pokemonNBT, (String)"pokemonNBT");
                this.pokemon.add(pokemon.loadFromNBT(pokemonNBT));
            }
            catch (InvalidSpeciesException invalidSpeciesException) {
                Intrinsics.checkNotNullExpressionValue((Object)pokemonNBT, (String)"pokemonNBT");
                this.handleInvalidSpeciesNBT(pokemonNBT);
            }
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public JsonObject saveToJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Iterable $this$forEachIndexed$iv = this.pokemon;
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
            json.add("Slot" + index, (JsonElement)pokemon.saveToJSON(new JsonObject()));
        }
        return json;
    }

    @NotNull
    public BottomlessStore loadFromJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        int i = -1;
        while (json.has("Slot" + ++i)) {
            JsonObject pokemonJSON = json.getAsJsonObject("Slot" + i);
            try {
                Pokemon pokemon = new Pokemon();
                Intrinsics.checkNotNullExpressionValue((Object)pokemonJSON, (String)"pokemonJSON");
                this.pokemon.add(pokemon.loadFromJSON(pokemonJSON));
            }
            catch (InvalidSpeciesException invalidSpeciesException) {
                Intrinsics.checkNotNullExpressionValue((Object)pokemonJSON, (String)"pokemonJSON");
                this.handleInvalidSpeciesJSON(pokemonJSON);
            }
        }
        return this;
    }

    @Override
    @NotNull
    public StoreCoordinates<BottomlessPosition> loadPositionFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        byte slot = nbt.m_128445_("Slot");
        return new StoreCoordinates<StorePosition>(this, new BottomlessPosition(slot));
    }

    @Override
    public void savePositionToNBT(@NotNull BottomlessPosition position, @NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.m_128344_("Slot", (byte)position.getCurrentIndex());
    }

    @NotNull
    public SimpleObservable<Unit> getAnyChangeObservable() {
        return this.storeChangeObservable;
    }

    @Override
    protected void setAtPosition(@NotNull BottomlessPosition position, @Nullable Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        if (position.getCurrentIndex() == this.pokemon.size() && pokemon != null) {
            this.pokemon.add(pokemon);
            Unit[] unitArray = new Unit[]{Unit.INSTANCE};
            this.storeChangeObservable.emit((Unit[])unitArray);
        } else {
            int n = this.pokemon.size();
            int n2 = position.getCurrentIndex();
            boolean bl = 0 <= n2 ? n2 < n : false;
            if (bl) {
                int startIndex = position.getCurrentIndex();
                if (pokemon != null) {
                    this.pokemon.add(position.getCurrentIndex(), pokemon);
                    ++startIndex;
                } else {
                    this.pokemon.remove(position.getCurrentIndex());
                }
                int n3 = this.pokemon.size();
                for (int i = startIndex; i < n3; ++i) {
                    this.pokemon.get(i).getStoreCoordinates().set(new StoreCoordinates<StorePosition>(this, new BottomlessPosition(i)));
                }
                Unit[] unitArray = new Unit[]{Unit.INSTANCE};
                this.storeChangeObservable.emit((Unit[])unitArray);
            }
        }
    }
}

