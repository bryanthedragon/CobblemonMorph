/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\b\n\u0002\u0010\u0006\n\u0002\b\u0013\u001a5\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a\u001d\u0010\t\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0000\u00a2\u0006\u0004\b\t\u0010\n\u001a\u001d\u0010\u000b\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0000\u00a2\u0006\u0004\b\u000b\u0010\n\u001a\u001d\u0010\f\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0000\u00a2\u0006\u0004\b\f\u0010\n\u001a\u001d\u0010\r\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0000\u00a2\u0006\u0004\b\r\u0010\n\u001a-\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u0000\u00a2\u0006\u0004\b\u000e\u0010\u000f\u001a\u001d\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0010\u0010\n\u001a\u001d\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0011\u0010\n\u001a\u001d\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0012\u0010\n\u00a8\u0006\u0013"}, d2={"", "t", "p0", "p1", "p2", "p3", "catmullRom", "(DDDDD)D", "p", "cubicBezierP0", "(DD)D", "cubicBezierP1", "cubicBezierP2", "cubicBezierP3", "quadraticBezier", "(DDDD)D", "quadraticBezierP0", "quadraticBezierP1", "quadraticBezierP2", "common"})
public final class CatmullRomCurveKt {
    public static final double catmullRom(double t, double p0, double p1, double p2, double p3) {
        double v0 = (p2 - p0) * 0.5;
        double v1 = (p3 - p1) * 0.5;
        double t2 = t * t;
        double t3 = t * t2;
        return ((double)2 * p1 - (double)2 * p2 + v0 + v1) * t3 + ((double)-3 * p1 + (double)3 * p2 - (double)2 * v0 - v1) * t2 + v0 * t + p1;
    }

    public static final double quadraticBezierP0(double t, double p) {
        double k = 1.0 - t;
        return k * k * p;
    }

    public static final double quadraticBezierP1(double t, double p) {
        return (double)2 * (1.0 - t) * t * p;
    }

    public static final double quadraticBezierP2(double t, double p) {
        return t * t * p;
    }

    public static final double quadraticBezier(double t, double p0, double p1, double p2) {
        return CatmullRomCurveKt.quadraticBezierP0(t, p0) + CatmullRomCurveKt.quadraticBezierP1(t, p1) + CatmullRomCurveKt.quadraticBezierP2(t, p2);
    }

    public static final double cubicBezierP0(double t, double p) {
        double k = 1.0 - t;
        return k * k * k * p;
    }

    public static final double cubicBezierP1(double t, double p) {
        double k = 1.0 - t;
        return (double)3 * k * k * t * p;
    }

    public static final double cubicBezierP2(double t, double p) {
        return (double)3 * (1.0 - t) * t * t * p;
    }

    public static final double cubicBezierP3(double t, double p) {
        return t * t * t * p;
    }
}

