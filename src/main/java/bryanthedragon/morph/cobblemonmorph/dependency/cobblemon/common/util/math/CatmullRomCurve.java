package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math

public class CatmullRomCurve(nodes: List<Double>) {
   public final val nodes: List<Double>

   init {
      this.nodes = nodes;
   }

   public fun getY(t: Double): Double {
      val points: java.util.List = this.nodes;
      val p: Double = (this.nodes.size() - 1) * t;
      val intPoint: Int = (int)Math.floor(p);
      return CatmullRomCurveKt.catmullRom(
         p - (double)intPoint,
         (points.get(if (intPoint <= 0) 0 else intPoint - 1) as java.lang.Number).doubleValue(),
         (points.get(if (intPoint < 0) 0 else intPoint) as java.lang.Number).doubleValue(),
         (points.get(if (intPoint > points.size() - 2) points.size() - 1 else intPoint + 1) as java.lang.Number).doubleValue(),
         (points.get(if (intPoint > points.size() - 3) points.size() - 1 else intPoint + 2) as java.lang.Number).doubleValue()
      );
   }
}
