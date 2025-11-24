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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \b2\u00020\u0001:\u0001\bB\t\b\u0016\u00a2\u0006\u0004\b\u0002\u0010\u0003B\u001d\b\u0016\u0012\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005\u00a2\u0006\u0004\b\u0002\u0010\u0007\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/spawning/TimeRange;", "Lcom/cobblemon/mod/common/api/spawning/IntRanges;", "<init>", "()V", "", "Lkotlin/ranges/IntRange;", "ranges", "([Lkotlin/ranges/IntRange;)V", "Companion", "common"})
public final class TimeRange
extends IntRanges {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final Map<String, TimeRange> timeRanges;

    public TimeRange() {
    }

    public TimeRange(IntRange ... ranges) {
        Intrinsics.checkNotNullParameter((Object)ranges, (String)"ranges");
        super(Arrays.copyOf(ranges, ranges.length));
    }

    static {
        Pair[] pairArray = new Pair[12];
        IntRange[] intRangeArray = new IntRange[]{new IntRange(0, 23999)};
        pairArray[0] = TuplesKt.to((Object)"any", (Object)new TimeRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(23460, 23999), new IntRange(0, 12541)};
        pairArray[1] = TuplesKt.to((Object)"day", (Object)new TimeRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(12542, 23459)};
        pairArray[2] = TuplesKt.to((Object)"night", (Object)new TimeRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(5000, 6999)};
        pairArray[3] = TuplesKt.to((Object)"noon", (Object)new TimeRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(17000, 18999)};
        pairArray[4] = TuplesKt.to((Object)"midnight", (Object)new TimeRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(22300, 23999), new IntRange(0, 999)};
        pairArray[5] = TuplesKt.to((Object)"dawn", (Object)new TimeRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(11834, 13701)};
        pairArray[6] = TuplesKt.to((Object)"dusk", (Object)new TimeRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(11834, 13701), new IntRange(22300, 23999), new IntRange(0, 166)};
        pairArray[7] = TuplesKt.to((Object)"twilight", (Object)new TimeRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(23000, 23999), new IntRange(0, 4999)};
        pairArray[8] = TuplesKt.to((Object)"morning", (Object)new TimeRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(7000, 12999)};
        pairArray[9] = TuplesKt.to((Object)"afternoon", (Object)new TimeRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(19000, 22999)};
        pairArray[10] = TuplesKt.to((Object)"predawn", (Object)new TimeRange(intRangeArray));
        intRangeArray = new IntRange[]{new IntRange(13000, 16999)};
        pairArray[11] = TuplesKt.to((Object)"evening", (Object)new TimeRange(intRangeArray));
        timeRanges = MapsKt.mutableMapOf((Pair[])pairArray);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR#\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/spawning/TimeRange$Companion;", "", "", "", "Lcom/cobblemon/mod/common/api/spawning/TimeRange;", "timeRanges", "Ljava/util/Map;", "getTimeRanges", "()Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Map<String, TimeRange> getTimeRanges() {
            return timeRanges;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

