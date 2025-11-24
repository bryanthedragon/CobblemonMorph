/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.math.MathKt
 *  kotlin.random.Random
 *  kotlin.ranges.RangesKt
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.stat;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatTypeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.adapters.CobblemonStatTypeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\bA\u0010BJ\u0015\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J#\u0010\n\u001a\u00020\t2\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\u0006H\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u001f\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0019\u0010\u001c\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u001b\u001a\u00020\u001aH\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u001aH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001dJ\u001f\u0010!\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b!\u0010\"J\u0017\u0010#\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u001aH\u0002\u00a2\u0006\u0004\b#\u0010$J\u001d\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010&\u001a\u00020%H\u0016\u00a2\u0006\u0004\b'\u0010(J\u0017\u0010*\u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b*\u0010+J\u0017\u0010.\u001a\u00020\t2\u0006\u0010-\u001a\u00020,H\u0016\u00a2\u0006\u0004\b.\u0010/J\u0017\u0010.\u001a\u00020\t2\u0006\u00101\u001a\u000200H\u0016\u00a2\u0006\u0004\b.\u00102J!\u00104\u001a\u0002032\u0006\u00101\u001a\u0002002\b\u0010-\u001a\u0004\u0018\u00010,H\u0016\u00a2\u0006\u0004\b4\u00105R \u00107\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u0007068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b7\u00108R \u0010:\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u000209068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b:\u00108R \u0010;\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u000209068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b;\u00108R\u001a\u0010=\u001a\u00020<8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b=\u0010>\u001a\u0004\b?\u0010@\u00a8\u0006C"}, d2={"Lcom/cobblemon/mod/common/pokemon/stat/CobblemonStatProvider;", "Lcom/cobblemon/mod/common/api/pokemon/stats/StatProvider;", "", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "all", "()Ljava/util/Collection;", "", "", "map", "", "allocate", "(Ljava/util/Map;)V", "Lcom/cobblemon/mod/common/pokemon/EVs;", "createEmptyEVs", "()Lcom/cobblemon/mod/common/pokemon/EVs;", "minPerfectIVs", "Lcom/cobblemon/mod/common/pokemon/IVs;", "createEmptyIVs", "(I)Lcom/cobblemon/mod/common/pokemon/IVs;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "stat", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;)V", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "fromIdentifier", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "fromIdentifierOrThrow", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "getStatForPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;)I", "identifierLookup", "(Lnet/minecraft/resources/ResourceLocation;)I", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat$Type;", "type", "ofType", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat$Type;)Ljava/util/Collection;", "ordinal", "ordinalLookup", "(I)Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "Lcom/cobblemon/mod/common/pokemon/FormData;", "form", "provide", "(Lcom/cobblemon/mod/common/pokemon/FormData;)V", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "(Lcom/cobblemon/mod/common/pokemon/Species;)V", "", "toShowdown", "(Lcom/cobblemon/mod/common/pokemon/Species;Lcom/cobblemon/mod/common/pokemon/FormData;)Ljava/lang/String;", "", "identifierToOrdinal", "Ljava/util/Map;", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stats;", "ordinalToStat", "stats", "Lcom/cobblemon/mod/common/api/pokemon/stats/StatTypeAdapter;", "typeAdapter", "Lcom/cobblemon/mod/common/api/pokemon/stats/StatTypeAdapter;", "getTypeAdapter", "()Lcom/cobblemon/mod/common/api/pokemon/stats/StatTypeAdapter;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonStatProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonStatProvider.kt\ncom/cobblemon/mod/common/pokemon/stat/CobblemonStatProvider\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,137:1\n1#2:138\n1855#3,2:139\n1855#3,2:141\n8811#4,2:143\n9071#4,4:145\n8811#4,2:149\n9071#4,4:151\n8676#4,2:155\n9358#4,4:157\n*S KotlinDebug\n*F\n+ 1 CobblemonStatProvider.kt\ncom/cobblemon/mod/common/pokemon/stat/CobblemonStatProvider\n*L\n64#1:139,2\n122#1:141,2\n38#1:143,2\n38#1:145,4\n39#1:149,2\n39#1:151,4\n40#1:155,2\n40#1:157,4\n*E\n"})
public final class CobblemonStatProvider
implements StatProvider {
    @NotNull
    public static final CobblemonStatProvider INSTANCE;
    @NotNull
    private static final StatTypeAdapter typeAdapter;
    @NotNull
    private static final Map<ResourceLocation, Stats> stats;
    @NotNull
    private static final Map<Integer, Stats> ordinalToStat;
    @NotNull
    private static final Map<ResourceLocation, Integer> identifierToOrdinal;

    private CobblemonStatProvider() {
    }

    @Override
    @NotNull
    public StatTypeAdapter getTypeAdapter() {
        return typeAdapter;
    }

    @Override
    @NotNull
    public Collection<Stat> all() {
        return Stats.Companion.getALL();
    }

    @Override
    @NotNull
    public Collection<Stat> ofType(@NotNull Stat.Type type) {
        Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
        return switch (WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) {
            case 1 -> Stats.Companion.getBATTLE_ONLY();
            case 2 -> Stats.Companion.getPERMANENT();
            default -> throw new NoWhenBranchMatchedException();
        };
    }

    @Override
    public void provide(@NotNull Species species) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        this.allocate((Map<Stat, Integer>)species.getBaseStats());
    }

    @Override
    public void provide(@NotNull FormData form2) {
        block0: {
            Intrinsics.checkNotNullParameter((Object)form2, (String)"form");
            Map<Stat, Integer> map = form2.get_baseStats$common();
            if (map == null) break block0;
            Map<Stat, Integer> it = map;
            boolean bl = false;
            this.allocate(it);
        }
    }

    @Override
    @NotNull
    public String toShowdown(@NotNull Species species, @Nullable FormData form2) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Object object = form2;
        if (object == null || (object = ((FormData)object).getBaseStats()) == null) {
            object = species.getBaseStats();
        }
        Object baseStats = object;
        return "baseStats: { hp: " + baseStats.get(Stats.HP) + ", atk: " + baseStats.get(Stats.ATTACK) + ", def: " + baseStats.get(Stats.DEFENCE) + ", spa: " + baseStats.get(Stats.SPECIAL_ATTACK) + ", spd: " + baseStats.get(Stats.SPECIAL_DEFENCE) + ", spe: " + baseStats.get(Stats.SPEED) + " }";
    }

    @Override
    @NotNull
    public EVs createEmptyEVs() {
        EVs evs = new EVs();
        Iterable $this$forEach$iv = this.ofType(Stat.Type.PERMANENT);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Stat stat = (Stat)element$iv;
            boolean bl = false;
            evs.set(stat, evs.getDefaultValue());
        }
        return evs;
    }

    @Override
    @NotNull
    public IVs createEmptyIVs(int minPerfectIVs) {
        IVs ivs = new IVs();
        for (Stat stat : this.ofType(Stat.Type.PERMANENT)) {
            ivs.set(stat, Random.Default.nextInt(32));
        }
        if (minPerfectIVs > 0) {
            List perfectStats = CollectionsKt.take((Iterable)CollectionsKt.shuffled((Iterable)this.ofType(Stat.Type.PERMANENT)), (int)minPerfectIVs);
            for (Stat stat : perfectStats) {
                ivs.set(stat, 31);
            }
        }
        return ivs;
    }

    @Override
    public int getStatForPokemon(@NotNull Pokemon pokemon, @NotNull Stat stat) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)stat, (String)"stat");
        Map<Stat, Integer> stats = pokemon.getForm().getBaseStats();
        int iv = pokemon.getIvs().getOrDefault(stat);
        Integer n = pokemon.getForm().getBaseStats().get(stat);
        Intrinsics.checkNotNull((Object)n);
        int base = ((Number)n).intValue();
        int ev = pokemon.getEvs().getOrDefault(stat);
        int level = pokemon.getLevel();
        return stat == Stats.HP ? (Intrinsics.areEqual((Object)pokemon.getSpecies().getResourceIdentifier(), (Object)Pokemon.Companion.getSHEDINJA$common()) ? 1 : (int)MathKt.truncate((double)(MathKt.truncate((double)(2.0 * (double)base + (double)iv + MathKt.truncate((double)((double)ev / 4.0)) + (double)100)) * (double)level / 100.0 + (double)10))) : pokemon.getEffectiveNature().modifyStat(stat, (2 * base + iv + ev / 4) * level / 100 + 5);
    }

    @Override
    @Nullable
    public Stat fromIdentifier(@NotNull ResourceLocation identifier) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        return stats.get(identifier);
    }

    @Override
    @NotNull
    public Stat fromIdentifierOrThrow(@NotNull ResourceLocation identifier) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Stat stat = this.fromIdentifier(identifier);
        if (stat == null) {
            throw new IllegalArgumentException("No stat was found with the identifier " + identifier);
        }
        return stat;
    }

    @Override
    @NotNull
    public Stat decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        int ordinal = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
        return this.ordinalLookup(ordinal);
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer, @NotNull Stat stat) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter((Object)stat, (String)"stat");
        int ordinal = this.identifierLookup(stat.getIdentifier());
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, ordinal);
    }

    private final void allocate(Map<Stat, Integer> map) {
        Iterable $this$forEach$iv = Stats.Companion.getPERMANENT();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Stat stat = (Stat)element$iv;
            boolean bl = false;
            map.putIfAbsent(stat, 1);
        }
    }

    private final Stat ordinalLookup(int ordinal) {
        Stats stats = ordinalToStat.get(ordinal);
        if (stats == null) {
            throw new IllegalArgumentException("Cannot find the stat with the ordinal " + ordinal + ", this should only happen if there is a custom Stat implementation but no StatProvider to go alongside it");
        }
        return stats;
    }

    private final int identifierLookup(ResourceLocation identifier) {
        Integer n = identifierToOrdinal.get(identifier);
        if (n == null) {
            throw new IllegalArgumentException("Cannot find the stat to encode, this should only happen if there is a custom Stat implementation but no StatProvider to go alongside it on the server side");
        }
        return n;
    }

    /*
     * WARNING - void declaration
     */
    static {
        void $this$associateTo$iv$iv;
        Stats it;
        Map map;
        Map map2;
        Stats[] $this$associateByTo$iv$iv;
        INSTANCE = new CobblemonStatProvider();
        typeAdapter = CobblemonStatTypeAdapter.INSTANCE;
        Stats[] $this$associateBy$iv = Stats.values();
        boolean $i$f$associateBy = false;
        int capacity$iv = RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)$this$associateBy$iv.length), (int)16);
        Stats[] statsArray = $this$associateBy$iv;
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        boolean $i$f$associateByTo = false;
        for (Stats element$iv$iv : $this$associateByTo$iv$iv) {
            map2 = element$iv$iv;
            map = destination$iv$iv;
            boolean bl = false;
            map.put(it.getIdentifier(), element$iv$iv);
        }
        stats = destination$iv$iv;
        $this$associateBy$iv = Stats.values();
        $i$f$associateBy = false;
        capacity$iv = RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)$this$associateBy$iv.length), (int)16);
        $this$associateByTo$iv$iv = $this$associateBy$iv;
        destination$iv$iv = new LinkedHashMap(capacity$iv);
        $i$f$associateByTo = false;
        int n = $this$associateByTo$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            Stats element$iv$iv;
            it = element$iv$iv = $this$associateByTo$iv$iv[i];
            map = destination$iv$iv;
            boolean bl = false;
            map.put(it.ordinal(), element$iv$iv);
        }
        ordinalToStat = destination$iv$iv;
        Stats[] $this$associate$iv = Stats.values();
        boolean $i$f$associate = false;
        capacity$iv = RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)$this$associate$iv.length), (int)16);
        $this$associateByTo$iv$iv = $this$associate$iv;
        destination$iv$iv = new LinkedHashMap(capacity$iv);
        boolean $i$f$associateTo = false;
        for (Stats element$iv$iv : $this$associateTo$iv$iv) {
            map2 = destination$iv$iv;
            Stats it2 = element$iv$iv;
            boolean bl = false;
            Pair pair = TuplesKt.to((Object)it2.getIdentifier(), (Object)it2.ordinal());
            map2.put(pair.getFirst(), pair.getSecond());
        }
        identifierToOrdinal = destination$iv$iv;
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Stat.Type.values().length];
            try {
                nArray[Stat.Type.BATTLE_ONLY.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Stat.Type.PERMANENT.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

