/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.CatmullRomCurveKt;
import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0010\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\n\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0002\u0012\u0006\u0010\u000e\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0015\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u0017\u0010\u0006\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u0007\u001a\u0004\b\u000b\u0010\tR\u0017\u0010\f\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u0007\u001a\u0004\b\r\u0010\tR\u0017\u0010\u000e\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u0007\u001a\u0004\b\u000f\u0010\t\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/util/math/CubedBezierCurve;", "", "", "t", "getY", "(D)D", "v0", "D", "getV0", "()D", "v1", "getV1", "v2", "getV2", "v3", "getV3", "<init>", "(DDDD)V", "common"})
public final class CubedBezierCurve {
    private final double v0;
    private final double v1;
    private final double v2;
    private final double v3;

    public CubedBezierCurve(double v0, double v1, double v2, double v3) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public final double getV0() {
        return this.v0;
    }

    public final double getV1() {
        return this.v1;
    }

    public final double getV2() {
        return this.v2;
    }

    public final double getV3() {
        return this.v3;
    }

    public final double getY(double t) {
        return CatmullRomCurveKt.cubicBezierP0(t, this.v0) + CatmullRomCurveKt.cubicBezierP1(t, this.v1) + CatmullRomCurveKt.cubicBezierP2(t, this.v2) + CatmullRomCurveKt.cubicBezierP3(t, this.v3);
    }
}

