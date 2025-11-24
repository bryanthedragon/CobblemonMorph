/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.ArraysKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.IntRange
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u001d\b\u0016\u0012\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u000f\"\u00020\b\u00a2\u0006\u0004\b\u0010\u0010\u0011B\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0012J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0086\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R(\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/spawning/IntRanges;", "", "", "value", "", "contains", "(I)Z", "", "Lkotlin/ranges/IntRange;", "ranges", "Ljava/util/List;", "getRanges", "()Ljava/util/List;", "setRanges", "(Ljava/util/List;)V", "", "<init>", "([Lkotlin/ranges/IntRange;)V", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nIntRanges.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IntRanges.kt\ncom/cobblemon/mod/common/api/spawning/IntRanges\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,25:1\n1747#2,3:26\n*S KotlinDebug\n*F\n+ 1 IntRanges.kt\ncom/cobblemon/mod/common/api/spawning/IntRanges\n*L\n24#1:26,3\n*E\n"})
public class IntRanges {
    @NotNull
    private List<IntRange> ranges;

    public IntRanges() {
        this.ranges = new ArrayList();
    }

    public IntRanges(IntRange ... ranges) {
        Intrinsics.checkNotNullParameter((Object)ranges, (String)"ranges");
        this();
        this.ranges = ArraysKt.toMutableList((Object[])ranges);
    }

    @NotNull
    public final List<IntRange> getRanges() {
        return this.ranges;
    }

    public final void setRanges(@NotNull List<IntRange> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.ranges = list;
    }

    public final boolean contains(int value2) {
        boolean bl;
        block3: {
            Iterable $this$any$iv = this.ranges;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    IntRange it = (IntRange)element$iv;
                    boolean bl2 = false;
                    int n = it.getFirst();
                    boolean bl3 = value2 <= it.getLast() ? n <= value2 : false;
                    if (!bl3) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }
}

