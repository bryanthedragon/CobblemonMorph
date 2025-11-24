/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.PokemonStats;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0007\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u001d\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0014\u00a2\u0006\u0004\b\n\u0010\u000bR\u001a\u0010\r\u001a\u00020\f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u00048\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/pokemon/EVs;", "Lcom/cobblemon/mod/common/pokemon/PokemonStats;", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "key", "", "value", "add", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;I)I", "stat", "", "canSet", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;I)Z", "Lkotlin/ranges/IntRange;", "acceptableRange", "Lkotlin/ranges/IntRange;", "getAcceptableRange", "()Lkotlin/ranges/IntRange;", "defaultValue", "I", "getDefaultValue", "()I", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nEVs.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EVs.kt\ncom/cobblemon/mod/common/pokemon/EVs\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,60:1\n1179#2,2:61\n1253#2,4:63\n1#3:67\n*S KotlinDebug\n*F\n+ 1 EVs.kt\ncom/cobblemon/mod/common/pokemon/EVs\n*L\n23#1:61,2\n23#1:63,4\n*E\n"})
public final class EVs
extends PokemonStats {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final IntRange acceptableRange = new IntRange(0, 252);
    private final int defaultValue;
    public static final int MAX_STAT_VALUE = 252;
    public static final int MAX_TOTAL_VALUE = 510;

    @Override
    @NotNull
    public IntRange getAcceptableRange() {
        return this.acceptableRange;
    }

    @Override
    public int getDefaultValue() {
        return this.defaultValue;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    protected boolean canSet(@NotNull Stat stat, int value2) {
        void $this$associateTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)stat, (String)"stat");
        if (!super.canSet(stat, value2)) {
            return false;
        }
        Iterable $this$associate$iv = this;
        boolean $i$f$associate = false;
        int capacity$iv = RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)CollectionsKt.collectionSizeOrDefault((Iterable)$this$associate$iv, (int)10)), (int)16);
        Iterable iterable = $this$associate$iv;
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        boolean $i$f$associateTo = false;
        for (Object element$iv$iv : $this$associateTo$iv$iv) {
            Map map = destination$iv$iv;
            Map.Entry it = (Map.Entry)element$iv$iv;
            boolean bl = false;
            Pair pair = TuplesKt.to(it.getKey(), it.getValue());
            map.put(pair.getFirst(), pair.getSecond());
        }
        Map simulated = MapsKt.toMutableMap((Map)destination$iv$iv);
        Integer n = value2;
        simulated.put(stat, n);
        int simulatedTotal = CollectionsKt.sumOfInt((Iterable)simulated.values());
        return simulatedTotal <= 510;
    }

    /*
     * WARNING - void declaration
     */
    public final int add(@NotNull Stat key, int value2) {
        int possibleForTotal;
        int possibleForStat;
        int coercedValue;
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Iterable iterable = this;
        int n = 0;
        for (Object t : iterable) {
            void it;
            Map.Entry entry = (Map.Entry)t;
            int n2 = n;
            boolean bl = false;
            int n3 = ((Number)it.getValue()).intValue();
            n = n2 + n3;
        }
        int currentTotal = n;
        if (currentTotal == 510 && value2 > 0) {
            return 0;
        }
        int currentStat = this.getOrDefault(key);
        int newValue = currentStat + (coercedValue = RangesKt.coerceIn((int)value2, (int)(-currentStat), (int)Math.min(possibleForStat = 252 - currentStat, possibleForTotal = 510 - currentTotal)));
        if (newValue != currentStat) {
            this.set(key, newValue);
            return coercedValue;
        }
        return 0;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00058\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0007\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/pokemon/EVs$Companion;", "", "Lcom/cobblemon/mod/common/pokemon/EVs;", "createEmpty", "()Lcom/cobblemon/mod/common/pokemon/EVs;", "", "MAX_STAT_VALUE", "I", "MAX_TOTAL_VALUE", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final EVs createEmpty() {
            return Cobblemon.INSTANCE.getStatProvider().createEmptyEVs();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

