/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.ranges.ClosedFloatingPointRange
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math;

import kotlin.Metadata;
import kotlin.ranges.ClosedFloatingPointRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u001b\u001a\u00020\u0002\u0012\u0006\u0010\u0017\u001a\u00020\u0002\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001a\u0010\t\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0096\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000e\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001f\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0015\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001b\u0010\u0018\u001a\u0004\b\u001c\u0010\u001a\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/util/math/DoubleRange;", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "value", "", "contains", "(D)Z", "", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "isEmpty", "()Z", "a", "b", "lessThanOrEquals", "(DD)Z", "", "toString", "()Ljava/lang/String;", "endInclusive", "D", "getEndInclusive", "()Ljava/lang/Double;", "start", "getStart", "<init>", "(DD)V", "common"})
public final class DoubleRange
implements ClosedFloatingPointRange<Double> {
    private final double start;
    private final double endInclusive;

    public DoubleRange(double start2, double endInclusive) {
        this.start = start2;
        this.endInclusive = endInclusive;
    }

    @NotNull
    public Double getStart() {
        return this.start;
    }

    @NotNull
    public Double getEndInclusive() {
        return this.endInclusive;
    }

    public boolean contains(double value2) {
        double d = this.getStart();
        return value2 <= this.getEndInclusive() ? d <= value2 : false;
    }

    public boolean isEmpty() {
        return this.getStart() > this.getEndInclusive();
    }

    public boolean lessThanOrEquals(double a, double b) {
        return a <= b;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof DoubleRange && this.getStart().doubleValue() == ((DoubleRange)other).getStart().doubleValue() && this.getEndInclusive().doubleValue() == ((DoubleRange)other).getEndInclusive().doubleValue();
    }

    public int hashCode() {
        return 31 * Double.hashCode(this.getStart()) + Double.hashCode(this.getEndInclusive());
    }

    @NotNull
    public String toString() {
        return this.getStart() + ".." + this.getEndInclusive();
    }
}

