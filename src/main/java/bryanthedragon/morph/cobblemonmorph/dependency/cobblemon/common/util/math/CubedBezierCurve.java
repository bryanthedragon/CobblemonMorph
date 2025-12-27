package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math

public class CubedBezierCurve(v0: Double, v1: Double, v2: Double, v3: Double) {
   public final val v0: Double
   public final val v1: Double
   public final val v2: Double
   public final val v3: Double

   init {
      this.v0 = v0;
      this.v1 = v1;
      this.v2 = v2;
      this.v3 = v3;
   }

   public fun getY(t: Double): Double {
      return CatmullRomCurveKt.cubicBezierP0(t, this.v0)
         + CatmullRomCurveKt.cubicBezierP1(t, this.v1)
         + CatmullRomCurveKt.cubicBezierP2(t, this.v2)
         + CatmullRomCurveKt.cubicBezierP3(t, this.v3);
   }
}
