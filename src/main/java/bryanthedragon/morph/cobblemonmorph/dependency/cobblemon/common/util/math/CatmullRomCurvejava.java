package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math

public fun catmullRom(t: Double, p0: Double, p1: Double, p2: Double, p3: Double): Double {
   return (2 * p1 - 2 * p2 + (p2 - p0) * 0.5 + (p3 - p1) * 0.5) * (t * (t * t))
      + (-3 * p1 + 3 * p2 - 2 * ((p2 - p0) * 0.5) - (p3 - p1) * 0.5) * (t * t)
      + (p2 - p0) * 0.5 * t
      + p1;
}

public fun quadraticBezierP0(t: Double, p: Double): Double {
   return (1 - t) * (1 - t) * p;
}

public fun quadraticBezierP1(t: Double, p: Double): Double {
   return 2 * (1 - t) * t * p;
}

public fun quadraticBezierP2(t: Double, p: Double): Double {
   return t * t * p;
}

public fun quadraticBezier(t: Double, p0: Double, p1: Double, p2: Double): Double {
   return quadraticBezierP0(t, p0) + quadraticBezierP1(t, p1) + quadraticBezierP2(t, p2);
}

public fun cubicBezierP0(t: Double, p: Double): Double {
   return (1 - t) * (1 - t) * (1 - t) * p;
}

public fun cubicBezierP1(t: Double, p: Double): Double {
   return 3 * (1 - t) * (1 - t) * t * p;
}

public fun cubicBezierP2(t: Double, p: Double): Double {
   return 3 * (1 - t) * t * t * p;
}

public fun cubicBezierP3(t: Double, p: Double): Double {
   return t * t * t * p;
}
