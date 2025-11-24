
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class Log2Node
extends MathOperation {
    public Log2Node(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    private static double lowBits(double x, int low) {
        long transX = Double.doubleToRawLongBits(x);
        return Double.longBitsToDouble(transX & 0xFFFFFFFF00000000L | (long)low);
    }

    private static int highBits(double x) {
        long transducer = Double.doubleToRawLongBits(x);
        return (int)(transducer >> 32);
    }

    private static double highBits(double x, int high) {
        long transX = Double.doubleToRawLongBits(x);
        return Double.longBitsToDouble(transX & 0xFFFFFFFFL | (long)high << 32);
    }

    @CompilerDirectives.TruffleBoundary
    private static strictfp double log2Impl(double x) {
        double ss;
        int k;
        double xAbs = Math.abs(x);
        int hx = Log2Node.highBits(x);
        int ix = hx & Integer.MAX_VALUE;
        double cp = 0.9617966939259756;
        double cph = 0.9617967009544373;
        double cpl = -7.028461650952758E-9;
        int n = 0;
        if (ix < 0x100000) {
            n -= 53;
            ix = Log2Node.highBits(xAbs *= 9.007199254740992E15);
        }
        n += (ix >> 20) - 1023;
        int j = ix & 0xFFFFF;
        ix = j | 0x3FF00000;
        if (j <= 235662) {
            k = 0;
        } else if (j < 767610) {
            k = 1;
        } else {
            k = 0;
            ++n;
            ix -= 0x100000;
        }
        xAbs = Log2Node.highBits(xAbs, ix);
        double[] bp = new double[]{1.0, 1.5};
        double[] dph = new double[]{0.0, 0.5849624872207642};
        double[] dpl = new double[]{0.0, 1.350039202129749E-8};
        double l1 = 0.5999999999999946;
        double l2 = 0.4285714285785502;
        double l3 = 0.33333332981837743;
        double l4 = 0.272728123808534;
        double l5 = 0.23066074577556175;
        double l6 = 0.20697501780033842;
        double u = xAbs - bp[k];
        double v = 1.0 / (xAbs + bp[k]);
        double sh = ss = u * v;
        sh = Log2Node.lowBits(sh, 0);
        double th = 0.0;
        th = Log2Node.highBits(th, (ix >> 1 | 0x20000000) + 524288 + (k << 18));
        double tl = xAbs - (th - bp[k]);
        double sl = v * (u - sh * th - sh * tl);
        double s2 = ss * ss;
        double r = s2 * s2 * (0.5999999999999946 + s2 * (0.4285714285785502 + s2 * (0.33333332981837743 + s2 * (0.272728123808534 + s2 * (0.23066074577556175 + s2 * 0.20697501780033842)))));
        s2 = sh * sh;
        th = 3.0 + s2 + (r += sl * (sh + ss));
        th = Log2Node.lowBits(th, 0);
        tl = r - (th - 3.0 - s2);
        u = sh * th;
        v = sl * th + tl * ss;
        double ph = u + v;
        ph = Log2Node.lowBits(ph, 0);
        double pl = v - (ph - u);
        double zh = 0.9617967009544373 * ph;
        double zl = -7.028461650952758E-9 * ph + pl * 0.9617966939259756 + dpl[k];
        double t = n;
        double t1 = zh + zl + dph[k] + t;
        t1 = Log2Node.lowBits(t1, 0);
        double t2 = zl - (t1 - t - dph[k] - zh);
        return t1 + t2;
    }

    @Specialization
    protected double log2(double x) {
        if (x < 0.0 || Double.isNaN(x)) {
            return Double.NaN;
        }
        if (x == 0.0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (x == Double.POSITIVE_INFINITY) {
            return Double.POSITIVE_INFINITY;
        }
        return Log2Node.log2Impl(x);
    }

    @Specialization
    protected double log2(Object a) {
        return this.log2(this.toDouble(a));
    }
}

