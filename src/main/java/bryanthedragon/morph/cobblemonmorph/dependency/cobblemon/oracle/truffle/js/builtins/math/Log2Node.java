package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class Log2Node extends MathOperation {
   public Log2Node(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   private static double lowBits(double x, int low) {
      long transX = Double.doubleToRawLongBits(x);
      return Double.longBitsToDouble(transX & -4294967296L | low);
   }

   private static int highBits(double x) {
      long transducer = Double.doubleToRawLongBits(x);
      return (int)(transducer >> 32);
   }

   private static double highBits(double x, int high) {
      long transX = Double.doubleToRawLongBits(x);
      return Double.longBitsToDouble(transX & 4294967295L | (long)high << 32);
   }

   @CompilerDirectives.TruffleBoundary
   private static strictfp double log2Impl(final double x) {
      double xAbs = Math.abs(x);
      int hx = highBits(x);
      int ix = hx & 2147483647;
      double cp = 0.9617966939259756;
      double cph = 0.9617967F;
      double cpl = -7.028461650952758E-9;
      int n = 0;
      if (ix < 1048576) {
         xAbs *= 9.007199E15F;
         n -= 53;
         ix = highBits(xAbs);
      }

      n += (ix >> 20) - 1023;
      int j = ix & 1048575;
      ix = j | 1072693248;
      int k;
      if (j <= 235662) {
         k = 0;
      } else if (j < 767610) {
         k = 1;
      } else {
         k = 0;
         n++;
         ix -= 1048576;
      }

      xAbs = highBits(xAbs, ix);
      double[] bp = new double[]{1.0, 1.5};
      double[] dph = new double[]{0.0, 0.5849625F};
      double[] dpl = new double[]{0.0, 1.350039202129749E-8};
      double l1 = 0.5999999999999946;
      double l2 = 0.4285714285785502;
      double l3 = 0.33333332981837743;
      double l4 = 0.272728123808534;
      double l5 = 0.23066074577556175;
      double l6 = 0.20697501780033842;
      double u = xAbs - bp[k];
      double v = 1.0 / (xAbs + bp[k]);
      double ss = u * v;
      double sh = lowBits(ss, 0);
      double th = 0.0;
      th = highBits(th, (ix >> 1 | 536870912) + 524288 + (k << 18));
      double tl = xAbs - (th - bp[k]);
      double sl = v * (u - sh * th - sh * tl);
      double s2 = ss * ss;
      double r = s2
         * s2
         * (
            0.5999999999999946
               + s2 * (0.4285714285785502 + s2 * (0.33333332981837743 + s2 * (0.272728123808534 + s2 * (0.23066074577556175 + s2 * 0.20697501780033842))))
         );
      r += sl * (sh + ss);
      s2 = sh * sh;
      th = 3.0 + s2 + r;
      th = lowBits(th, 0);
      tl = r - (th - 3.0 - s2);
      u = sh * th;
      v = sl * th + tl * ss;
      double ph = u + v;
      ph = lowBits(ph, 0);
      double pl = v - (ph - u);
      double zh = 0.9617967F * ph;
      double zl = -7.028461650952758E-9 * ph + pl * 0.9617966939259756 + dpl[k];
      double t = n;
      double t1 = zh + zl + dph[k] + t;
      t1 = lowBits(t1, 0);
      double t2 = zl - (t1 - t - dph[k] - zh);
      return t1 + t2;
   }

   @Specialization
   protected double log2(final double x) {
      if (x < 0.0 || Double.isNaN(x)) {
         return Double.NaN;
      } else if (x == 0.0) {
         return Double.NEGATIVE_INFINITY;
      } else {
         return x == Double.POSITIVE_INFINITY ? Double.POSITIVE_INFINITY : log2Impl(x);
      }
   }

   @Specialization
   protected double log2(Object a) {
      return this.log2(this.toDouble(a));
   }
}
