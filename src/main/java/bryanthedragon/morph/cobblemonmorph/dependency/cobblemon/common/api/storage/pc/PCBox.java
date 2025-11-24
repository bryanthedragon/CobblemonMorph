/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.markers.KMappedMarker
 *  kotlin.ranges.RangesKt
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.InvalidSpeciesException;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.SetPCBoxPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.RangesKt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\b\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u00108\u001a\u000207\u00a2\u0006\u0004\bB\u0010CJ\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0004\b\b\u0010\tJ\u0019\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00020\n\u00a2\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u0010H\u0096\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0017\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0015\u0010!\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u001f\u00a2\u0006\u0004\b!\u0010\"J\"\u0010$\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u00032\b\u0010#\u001a\u0004\u0018\u00010\u0002H\u0096\u0002\u00a2\u0006\u0004\b$\u0010%J\u0015\u0010&\u001a\u00020\r2\u0006\u0010#\u001a\u00020\u0002\u00a2\u0006\u0004\b&\u0010'R\u001d\u0010)\u001a\b\u0012\u0004\u0012\u00020\r0(8\u0006\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R\u0011\u0010/\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b-\u0010.R\"\u00101\u001a\u0002008\u0004@\u0004X\u0084\u000e\u00a2\u0006\u0012\n\u0004\b1\u00102\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u0017\u00108\u001a\u0002078\u0006\u00a2\u0006\f\n\u0004\b8\u00109\u001a\u0004\b:\u0010;R\"\u0010#\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020<8\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\b#\u0010=\u001a\u0004\b>\u0010?R\u0011\u0010A\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b@\u0010.\u00a8\u0006D"}, d2={"Lcom/cobblemon/mod/common/api/storage/pc/PCBox;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "index", "get", "(I)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "getFirstAvailablePosition", "()Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "", "getNonEmptySlots", "()Ljava/util/Map;", "", "initialize", "()V", "", "iterator", "()Ljava/util/Iterator;", "Lcom/google/gson/JsonObject;", "json", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/storage/pc/PCBox;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/storage/pc/PCBox;", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sendTo", "(Lnet/minecraft/server/level/ServerPlayer;)V", "pokemon", "set", "(ILcom/cobblemon/mod/common/pokemon/Pokemon;)V", "trackPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "boxChangeEmitter", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getBoxChangeEmitter", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getBoxNumber", "()I", "boxNumber", "", "emit", "Z", "getEmit", "()Z", "setEmit", "(Z)V", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "pc", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "getPc", "()Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "", "[Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()[Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getUnoccupiedSlots", "unoccupiedSlots", "<init>", "(Lcom/cobblemon/mod/common/api/storage/pc/PCStore;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPCBox.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PCBox.kt\ncom/cobblemon/mod/common/api/storage/pc/PCBox\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,150:1\n13644#2,3:151\n766#3:154\n857#3,2:155\n1271#3,2:157\n1285#3,4:159\n*S KotlinDebug\n*F\n+ 1 PCBox.kt\ncom/cobblemon/mod/common/api/storage/pc/PCBox\n*L\n80#1:151,3\n149#1:154\n149#1:155,2\n149#1:157,2\n149#1:159,4\n*E\n"})
public class PCBox
implements Iterable<Pokemon>,
KMappedMarker {
    @NotNull
    private final PCStore pc;
    @NotNull
    private final SimpleObservable<Unit> boxChangeEmitter;
    private boolean emit;
    @NotNull
    private final Pokemon[] pokemon;

    public PCBox(@NotNull PCStore pc) {
        Intrinsics.checkNotNullParameter((Object)pc, (String)"pc");
        this.pc = pc;
        this.boxChangeEmitter = new SimpleObservable();
        this.emit = true;
        int n = 0;
        Pokemon[] pokemonArray = new Pokemon[30];
        PCBox pCBox = this;
        while (n < 30) {
            int n2 = n++;
            pokemonArray[n2] = null;
        }
        pCBox.pokemon = pokemonArray;
    }

    @NotNull
    public final PCStore getPc() {
        return this.pc;
    }

    @Override
    @NotNull
    public Iterator<Pokemon> iterator() {
        return ArraysKt.filterNotNull((Object[])this.pokemon).iterator();
    }

    @NotNull
    public final SimpleObservable<Unit> getBoxChangeEmitter() {
        return this.boxChangeEmitter;
    }

    protected final boolean getEmit() {
        return this.emit;
    }

    protected final void setEmit(boolean bl) {
        this.emit = bl;
    }

    @NotNull
    protected final Pokemon[] getPokemon() {
        return this.pokemon;
    }

    @Nullable
    public Pokemon get(int index) {
        return (0 <= index ? index < 30 : false) ? this.pokemon[index] : null;
    }

    public void set(int index, @Nullable Pokemon pokemon) {
        block4: {
            block5: {
                block6: {
                    StoreCoordinates<?> previousCoordinates;
                    boolean bl = 0 <= index ? index < 30 : false;
                    if (!bl) break block4;
                    this.pokemon[index] = pokemon;
                    if (pokemon == null) break block5;
                    StoreCoordinates<?> storeCoordinates = previousCoordinates = pokemon.getStoreCoordinates().get();
                    Object position = storeCoordinates != null ? storeCoordinates.getPosition() : null;
                    pokemon.getStoreCoordinates().set(new StoreCoordinates<StorePosition>(this.pc, new PCPosition(this.getBoxNumber(), index)));
                    StoreCoordinates<?> storeCoordinates2 = previousCoordinates;
                    if (!Intrinsics.areEqual(storeCoordinates2 != null ? storeCoordinates2.getStore() : null, (Object)this)) break block6;
                    Intrinsics.checkNotNull(position, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition");
                    if (((PCPosition)position).getBox() == this.getBoxNumber()) break block5;
                }
                this.trackPokemon(pokemon);
            }
            if (this.emit) {
                Unit[] unitArray = new Unit[]{Unit.INSTANCE};
                this.boxChangeEmitter.emit((Unit[])unitArray);
            }
        }
    }

    public final int getBoxNumber() {
        return this.pc.getBoxes().indexOf(this);
    }

    public final int getUnoccupiedSlots() {
        return 30 - ((Collection)ArraysKt.filterNotNull((Object[])this.pokemon)).size();
    }

    @Nullable
    public final PCPosition getFirstAvailablePosition() {
        for (int index = 0; index < 30; ++index) {
            if (this.pokemon[index] != null) continue;
            return new PCPosition(this.getBoxNumber(), index);
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    public void initialize() {
        int box = this.getBoxNumber();
        Pokemon[] $this$forEachIndexed$iv = this.pokemon;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Pokemon item$iv : $this$forEachIndexed$iv) {
            void pokemon;
            int n = index$iv++;
            Pokemon pokemon2 = item$iv;
            int slot = n;
            boolean bl = false;
            if (pokemon == null) continue;
            PCPosition position = new PCPosition(box, slot);
            pokemon.getStoreCoordinates().set(new StoreCoordinates<StorePosition>(this.pc, position));
            this.trackPokemon((Pokemon)pokemon);
        }
        Observable.DefaultImpls.subscribe$default(this.boxChangeEmitter, null, (Function1)new Function1<Unit, Unit>(this){
            final /* synthetic */ PCBox this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull Unit it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                Unit[] unitArray = new Unit[]{Unit.INSTANCE};
                this.this$0.getPc().getPcChangeObservable().emit((Unit[])unitArray);
            }
        }, 1, null);
    }

    public final void trackPokemon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Observable.DefaultImpls.subscribe$default(pokemon.getChangeObservable().pipe(Observable.Companion.stopAfter((Function1)new Function1<Pokemon, Boolean>(this){
            final /* synthetic */ PCBox this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            /*
             * Enabled aggressive block sorting
             */
            @NotNull
            public final Boolean invoke(@NotNull Pokemon it) {
                boolean bl;
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                StoreCoordinates<?> storeCoordinates = it.getStoreCoordinates().get();
                if (storeCoordinates == null) {
                    return true;
                }
                StoreCoordinates<?> coordinates = storeCoordinates;
                if (Intrinsics.areEqual(coordinates.getStore(), (Object)this.this$0)) {
                    ? obj = coordinates.getPosition();
                    Intrinsics.checkNotNull(obj, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition");
                    if (((PCPosition)obj).getBox() == this.this$0.getBoxNumber()) {
                        bl = false;
                        return bl;
                    }
                }
                bl = true;
                return bl;
            }
        })), null, (Function1)new Function1<Pokemon, Unit>(this){
            final /* synthetic */ PCBox this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull Pokemon it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                Unit[] unitArray = new Unit[]{Unit.INSTANCE};
                this.this$0.getBoxChangeEmitter().emit((Unit[])unitArray);
            }
        }, 1, null);
    }

    public final void sendTo(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        new SetPCBoxPokemonPacket(this).sendToPlayer(player);
    }

    @NotNull
    public CompoundTag saveToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        for (int slot = 0; slot < 30; ++slot) {
            Pokemon pokemon;
            if (this.pokemon[slot] == null) continue;
            nbt.m_128365_("Slot" + slot, (Tag)pokemon.saveToNBT(new CompoundTag()));
        }
        return nbt;
    }

    @NotNull
    public JsonObject saveToJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        for (int slot = 0; slot < 30; ++slot) {
            Pokemon pokemon;
            if (this.pokemon[slot] == null) continue;
            json.add("Slot" + slot, (JsonElement)pokemon.saveToJSON(new JsonObject()));
        }
        return json;
    }

    @NotNull
    public PCBox loadFromJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        for (int slot = 0; slot < 30; ++slot) {
            if (!json.has("Slot" + slot)) continue;
            JsonObject pokemonJson = json.getAsJsonObject("Slot" + slot);
            try {
                Pokemon pokemon = new Pokemon();
                Intrinsics.checkNotNullExpressionValue((Object)pokemonJson, (String)"pokemonJson");
                this.pokemon[slot] = pokemon.loadFromJSON(pokemonJson);
                continue;
            }
            catch (InvalidSpeciesException invalidSpeciesException) {
                Intrinsics.checkNotNullExpressionValue((Object)pokemonJson, (String)"pokemonJson");
                this.pc.handleInvalidSpeciesJSON(pokemonJson);
            }
        }
        return this;
    }

    @NotNull
    public PCBox loadFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        for (int slot = 0; slot < 30; ++slot) {
            if (!nbt.m_128441_("Slot" + slot)) continue;
            CompoundTag pokemonNBT = nbt.m_128469_("Slot" + slot);
            try {
                Pokemon pokemon = new Pokemon();
                Intrinsics.checkNotNullExpressionValue((Object)pokemonNBT, (String)"pokemonNBT");
                this.pokemon[slot] = pokemon.loadFromNBT(pokemonNBT);
                continue;
            }
            catch (InvalidSpeciesException invalidSpeciesException) {
                Intrinsics.checkNotNullExpressionValue((Object)pokemonNBT, (String)"pokemonNBT");
                this.pc.handleInvalidSpeciesNBT(pokemonNBT);
            }
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Map<Integer, Pokemon> getNonEmptySlots() {
        int it;
        void $this$filterTo$iv$iv;
        Iterable $this$filter$iv = (Iterable)RangesKt.until((int)0, (int)30);
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = ((Number)element$iv$iv).intValue();
            boolean bl = false;
            if (!(this.get(it) != null)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$associateWith$iv = (List)destination$iv$iv;
        boolean $i$f$associateWith = false;
        LinkedHashMap result$iv = new LinkedHashMap(RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)CollectionsKt.collectionSizeOrDefault((Iterable)$this$associateWith$iv, (int)10)), (int)16));
        Iterable $this$associateWithTo$iv$iv = $this$associateWith$iv;
        boolean $i$f$associateWithTo = false;
        for (Object element$iv$iv : $this$associateWithTo$iv$iv) {
            Pokemon pokemon;
            it = ((Number)element$iv$iv).intValue();
            Object t = element$iv$iv;
            Map map = result$iv;
            boolean bl = false;
            Intrinsics.checkNotNull((Object)this.get(it));
            map.put(t, pokemon);
        }
        return result$iv;
    }
}

