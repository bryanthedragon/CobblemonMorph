/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.IntRange
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.IntRanges;
import java.util.Arrays;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \b2\u00020\u0001:\u0001\bB\t\b\u0016\u00a2\u0006\u0004\b\u0002\u0010\u0003B\u001d\b\u0016\u0012\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005\u00a2\u0006\u0004\b\u0002\u0010\u0007\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/spawning/MoonPhaseRange;", "Lcom/cobblemon/mod/common/api/spawning/IntRanges;", "<init>", "()V", "", "Lkotlin/ranges/IntRange;", "ranges", "([Lkotlin/ranges/IntRange;)V", "Companion", "common"})
public final class MoonPhaseRange
extends IntRanges {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final Map<String, MoonPhaseRange> moonPhaseRanges;

    public MoonPhaseRange() {
    }

    public MoonPhaseRange(IntRange ... ranges) {
        Intrinsics.checkNotNullParameter((Object)ranges, (String)"ranges");
        super(Arrays.copyOf(ranges, ranges.length));
    }

    static {
        Pair[] pairArray = new Pair[7];
        IntRange[] intRangeArray = new IntRange[]{new IntRange(3, 3), new IntRange(5, 5)};
        pairArray[0] = TuplesKt.to((Object)"crescent", (Object)new MoonPhaseRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(1, 1), new IntRange(7, 7)};
        pairArray[1] = TuplesKt.to((Object)"gibbous", (Object)new MoonPhaseRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(0, 0)};
        pairArray[2] = TuplesKt.to((Object)"full", (Object)new MoonPhaseRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(4, 4)};
        pairArray[3] = TuplesKt.to((Object)"new", (Object)new MoonPhaseRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(2, 2), new IntRange(6, 6)};
        pairArray[4] = TuplesKt.to((Object)"quarter", (Object)new MoonPhaseRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(5, 5), new IntRange(7, 7)};
        pairArray[5] = TuplesKt.to((Object)"waxing", (Object)new MoonPhaseRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(1, 1), new IntRange(3, 3)};
        pairArray[6] = TuplesKt.to((Object)"waning", (Object)new MoonPhaseRange(intRangeArray));
        moonPhaseRanges = MapsKt.mutableMapOf((Pair[])pairArray);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR#\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/spawning/MoonPhaseRange$Companion;", "", "", "", "Lcom/cobblemon/mod/common/api/spawning/MoonPhaseRange;", "moonPhaseRanges", "Ljava/util/Map;", "getMoonPhaseRanges", "()Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Map<String, MoonPhaseRange> getMoonPhaseRanges() {
            return moonPhaseRanges;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

