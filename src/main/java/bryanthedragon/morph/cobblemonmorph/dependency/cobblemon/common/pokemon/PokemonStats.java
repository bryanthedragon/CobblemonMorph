/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.markers.KMappedMarker
 *  kotlin.ranges.ClosedRange
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  kotlin.text.StringsKt
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.ClosedRange;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0010&\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010)\n\u0002\u0010'\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\b\u0004\b&\u0018\u00002\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u0001B\u0007\u00a2\u0006\u0004\bD\u00102J\u001f\u0010\b\u001a\u00020\u00072\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0004H\u0014\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\nH\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001b\u0010\u0012\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\u0014\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u00a2\u0006\u0004\b\u0014\u0010\u0013J\u001a\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0015\u001a\u00020\u0003H\u0086\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0018\u0010\u0019J\"\u0010\u001c\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u001b0\u001aH\u0096\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0015\u0010 \u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u001e\u00a2\u0006\u0004\b \u0010!J\u0015\u0010$\u001a\u00020\u00002\u0006\u0010#\u001a\u00020\"\u00a2\u0006\u0004\b$\u0010%J\u0015\u0010(\u001a\u00020\u00002\u0006\u0010'\u001a\u00020&\u00a2\u0006\u0004\b(\u0010)J\u0015\u0010*\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u001e\u00a2\u0006\u0004\b*\u0010!J\u0015\u0010+\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"\u00a2\u0006\u0004\b+\u0010,J\u0015\u0010-\u001a\u00020&2\u0006\u0010'\u001a\u00020&\u00a2\u0006\u0004\b-\u0010.J \u0010/\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0004H\u0096\u0002\u00a2\u0006\u0004\b/\u00100J\r\u00101\u001a\u00020\u0010\u00a2\u0006\u0004\b1\u00102R\u0014\u00106\u001a\u0002038&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b4\u00105R\u0014\u00109\u001a\u00020\u00048&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b7\u00108R\u0016\u0010:\u001a\u00020\u00078\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b:\u0010;R\u001d\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00000<8\u0006\u00a2\u0006\f\n\u0004\b=\u0010>\u001a\u0004\b?\u0010@R \u0010B\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040A8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bB\u0010C\u00a8\u0006E"}, d2={"Lcom/cobblemon/mod/common/pokemon/PokemonStats;", "", "", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "", "stat", "value", "", "canSet", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;I)Z", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "", "cleanStatIdentifier", "(Lnet/minecraft/resources/ResourceLocation;)Ljava/lang/String;", "Lkotlin/Function0;", "", "action", "doThenEmit", "(Lkotlin/jvm/functions/Function0;)V", "doWithoutEmitting", "key", "get", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;)Ljava/lang/Integer;", "getOrDefault", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;)I", "", "", "iterator", "()Ljava/util/Iterator;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/google/gson/JsonObject;", "json", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/pokemon/PokemonStats;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/pokemon/PokemonStats;", "saveToBuffer", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "set", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;I)V", "update", "()V", "Lkotlin/ranges/IntRange;", "getAcceptableRange", "()Lkotlin/ranges/IntRange;", "acceptableRange", "getDefaultValue", "()I", "defaultValue", "emit", "Z", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "stats", "Ljava/util/Map;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonStats.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonStats.kt\ncom/cobblemon/mod/common/pokemon/PokemonStats\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,127:1\n215#2,2:128\n215#2,2:132\n1855#3,2:130\n1855#3,2:134\n*S KotlinDebug\n*F\n+ 1 PokemonStats.kt\ncom/cobblemon/mod/common/pokemon/PokemonStats\n*L\n66#1:128,2\n85#1:132,2\n77#1:130,2\n97#1:134,2\n*E\n"})
public abstract class PokemonStats
implements Iterable<Map.Entry<? extends Stat, ? extends Integer>>,
KMappedMarker {
    @NotNull
    private final SimpleObservable<PokemonStats> observable = new SimpleObservable();
    @NotNull
    private final Map<Stat, Integer> stats = new LinkedHashMap();
    private boolean emit = true;

    @NotNull
    public abstract IntRange getAcceptableRange();

    public abstract int getDefaultValue();

    @Override
    @NotNull
    public Iterator<Map.Entry<Stat, Integer>> iterator() {
        return this.stats.entrySet().iterator();
    }

    @NotNull
    public final SimpleObservable<PokemonStats> getObservable() {
        return this.observable;
    }

    public final void doWithoutEmitting(@NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        this.emit = false;
        action2.invoke();
        this.emit = true;
    }

    public final void doThenEmit(@NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        this.doWithoutEmitting(action2);
        this.update();
    }

    public final void update() {
        if (this.emit) {
            PokemonStats[] pokemonStatsArray = new PokemonStats[]{this};
            this.observable.emit((PokemonStats[])pokemonStatsArray);
        }
    }

    @Nullable
    public final Integer get(@NotNull Stat key) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        return this.stats.get(key);
    }

    public void set(@NotNull Stat key, int value2) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        if (this.canSet(key, value2)) {
            Integer n = value2;
            this.stats.put(key, n);
            this.update();
        }
    }

    protected boolean canSet(@NotNull Stat stat, int value2) {
        Intrinsics.checkNotNullParameter((Object)stat, (String)"stat");
        IntRange intRange = this.getAcceptableRange();
        int n = intRange.getFirst();
        return value2 <= intRange.getLast() ? n <= value2 : false;
    }

    @NotNull
    public final CompoundTag saveToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        Map<Stat, Integer> $this$forEach$iv = this.stats;
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<Stat, Integer>> iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Stat, Integer> element$iv;
            Map.Entry<Stat, Integer> entry = element$iv = iterator.next();
            boolean bl = false;
            Stat stat = entry.getKey();
            int value2 = ((Number)entry.getValue()).intValue();
            if (value2 == this.getDefaultValue()) continue;
            nbt.m_128376_(this.cleanStatIdentifier(stat.getIdentifier()), (short)value2);
        }
        return nbt;
    }

    @NotNull
    public final PokemonStats loadFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        this.stats.clear();
        Iterable $this$forEach$iv = Stats.Companion.getPERMANENT();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Stat stat = (Stat)element$iv;
            boolean bl = false;
            String identifier = this.cleanStatIdentifier(stat.getIdentifier());
            this.set(stat, RangesKt.coerceIn((int)nbt.m_128448_(identifier), (ClosedRange)((ClosedRange)this.getAcceptableRange())));
        }
        return this;
    }

    @NotNull
    public final JsonObject saveToJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Map<Stat, Integer> $this$forEach$iv = this.stats;
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<Stat, Integer>> iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Stat, Integer> element$iv;
            Map.Entry<Stat, Integer> entry = element$iv = iterator.next();
            boolean bl = false;
            Stat stat = entry.getKey();
            int value2 = ((Number)entry.getValue()).intValue();
            if (value2 == this.getDefaultValue()) continue;
            json.addProperty(this.cleanStatIdentifier(stat.getIdentifier()), (Number)value2);
        }
        return json;
    }

    @NotNull
    public final PokemonStats loadFromJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        this.stats.clear();
        Iterable $this$forEach$iv = Stats.Companion.getPERMANENT();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Stat stat = (Stat)element$iv;
            boolean bl = false;
            String identifier = this.cleanStatIdentifier(stat.getIdentifier());
            JsonElement jsonElement = json.get(identifier);
            this.set(stat, jsonElement != null ? RangesKt.coerceIn((int)jsonElement.getAsInt(), (ClosedRange)((ClosedRange)this.getAcceptableRange())) : this.getDefaultValue());
        }
        return this;
    }

    public final void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.stats.size());
        for (Map.Entry<Stat, Integer> entry : this.stats.entrySet()) {
            Stat stat = entry.getKey();
            int value2 = ((Number)entry.getValue()).intValue();
            Cobblemon.INSTANCE.getStatProvider().encode(buffer, stat);
            NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_SHORT, value2);
        }
    }

    public final void loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.stats.clear();
        int n = buffer.readUnsignedByte();
        int n2 = 0;
        while (n2 < n) {
            int it = n2++;
            boolean bl = false;
            Stat stat = Cobblemon.INSTANCE.getStatProvider().decode(buffer);
            int value2 = buffer.readUnsignedShort();
            Integer n3 = value2;
            this.stats.put(stat, n3);
        }
    }

    public final int getOrDefault(@NotNull Stat stat) {
        Intrinsics.checkNotNullParameter((Object)stat, (String)"stat");
        Integer n = this.get(stat);
        return n != null ? n.intValue() : this.getDefaultValue();
    }

    private final String cleanStatIdentifier(ResourceLocation identifier) {
        String string = identifier.toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"identifier.toString()");
        return StringsKt.substringAfter$default((String)string, (String)"cobblemon:", null, (int)2, null);
    }
}

