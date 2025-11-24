/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.CatmullRomCurveKt;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u001d\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/math/CatmullRomCurve;", "", "", "t", "getY", "(D)D", "", "nodes", "Ljava/util/List;", "getNodes", "()Ljava/util/List;", "<init>", "(Ljava/util/List;)V", "common"})
public final class CatmullRomCurve {
    @NotNull
    private final List<Double> nodes;

    public CatmullRomCurve(@NotNull List<Double> nodes) {
        Intrinsics.checkNotNullParameter(nodes, (String)"nodes");
        this.nodes = nodes;
    }

    @NotNull
    public final List<Double> getNodes() {
        return this.nodes;
    }

    public final double getY(double t) {
        List<Double> points = this.nodes;
        double p = (double)(points.size() - 1) * t;
        int intPoint = (int)Math.floor(p);
        double weight = p - (double)intPoint;
        double p0 = ((Number)points.get(intPoint <= 0 ? 0 : intPoint - 1)).doubleValue();
        double p1 = ((Number)points.get(intPoint < 0 ? 0 : intPoint)).doubleValue();
        double p2 = ((Number)points.get(intPoint > points.size() - 2 ? points.size() - 1 : intPoint + 1)).doubleValue();
        double p3 = ((Number)points.get(intPoint > points.size() - 3 ? points.size() - 1 : intPoint + 2)).doubleValue();
        return CatmullRomCurveKt.catmullRom(weight, p0, p1, p2, p3);
    }
}

