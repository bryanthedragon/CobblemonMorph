package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math

import kotlin.random.Random
import net.minecraft.world.phys.Vec3
import org.joml.Matrix3f
import org.joml.Quaternionf
import org.joml.Quaternionfc
import org.joml.Vector3f
import org.joml.Vector3fc

public infix fun Int.pow(power: Int): Int {
   return (int)Math.pow((double)`$this$pow`, (double)power);
}

public fun Double?.orMax(): Double {
   return `$this$orMax` ?: java.lang.Double.MAX_VALUE;
}

public fun Double?.orMin(): Double {
   return `$this$orMin` ?: -2.1474836E9F;
}

public fun Float?.orMax(): Float {
   return `$this$orMax` ?: 2.1474836E9F;
}

public fun Float?.orMin(): Float {
   return `$this$orMin` ?: -2.1474836E9F;
}

public fun Int?.orMax(): Int {
   return `$this$orMax` ?: Integer.MAX_VALUE;
}

public fun Int?.orMin(): Int {
   return `$this$orMin` ?: Integer.MIN_VALUE;
}

public fun Int.toRGB(): Triple<Double, Double, Double> {
   return new Triple((double)(`$this$toRGB` shr 16 and 255) / 255.0, (double)(`$this$toRGB` shr 8 and 255) / 255.0, (double)(`$this$toRGB` and 255) / 255.0);
}

public fun IntRange.intersects(other: IntRange): Boolean {
   var var2: Int = other.getFirst();
   var var3: Int = other.getLast();
   var var4: Int = `$this$intersects`.getStart();
   if (var2 > var4 || var4 > var3) {
      var2 = other.getFirst();
      var3 = other.getLast();
      var4 = `$this$intersects`.getEndInclusive();
      if (var2 > var4 || var4 > var3) {
         var2 = `$this$intersects`.getFirst();
         var3 = `$this$intersects`.getLast();
         var4 = other.getStart();
         if (var2 > var4 || var4 > var3) {
            return false;
         }
      }
   }

   return true;
}

public fun IntRange.intersection(other: IntRange): IntRange {
   return new IntRange(Math.max(other.getStart(), `$this$intersection`.getStart()), Math.min(other.getEndInclusive(), `$this$intersection`.getEndInclusive()));
}

public fun Pair<Float, Float>.random(): Float {
   return Random.Default.nextFloat()
         * ((`$this$random`.getSecond() as java.lang.Number).floatValue() - (`$this$random`.getFirst() as java.lang.Number).floatValue())
      + (`$this$random`.getFirst() as java.lang.Number).floatValue();
}

public fun Float.remap(from: Pair<Float, Float>, to: Pair<Float, Float>): Float {
   val fromMin: Float = (from.component1() as java.lang.Number).floatValue();
   val fromMax: Float = (from.component2() as java.lang.Number).floatValue();
   val toMin: Float = (to.component1() as java.lang.Number).floatValue();
   return (`$this$remap` - fromMin) / (fromMax - fromMin) * ((to.component2() as java.lang.Number).floatValue() - toMin) + toMin;
}

public fun Float.remap(start: FloatRange, end: FloatRange): Float {
   val var3: Pair = TuplesKt.to(start.getStart(), start.getEndInclusive());
   val fromMin: Float = (var3.component1() as java.lang.Number).floatValue();
   val fromMax: Float = (var3.component2() as java.lang.Number).floatValue();
   val var6: Pair = TuplesKt.to(end.getStart(), end.getEndInclusive());
   val toMin: Float = (var6.component1() as java.lang.Number).floatValue();
   return (`$this$remap` - fromMin) / (fromMax - fromMin) * ((var6.component2() as java.lang.Number).floatValue() - toMin) + toMin;
}

public fun Int.remap(from: Pair<Int, Int>, to: Pair<Int, Int>): Int {
   val fromMin: Int = (from.component1() as java.lang.Number).intValue();
   val fromMax: Int = (from.component2() as java.lang.Number).intValue();
   val toMin: Int = (to.component1() as java.lang.Number).intValue();
   return (`$this$remap` - fromMin) / (fromMax - fromMin) * ((to.component2() as java.lang.Number).intValue() - toMin) + toMin;
}

public fun Int.remap(start: IntRange, end: IntRange): Int {
   val var3: Pair = TuplesKt.to(start.getFirst(), start.getLast());
   val fromMin: Int = (var3.component1() as java.lang.Number).intValue();
   val fromMax: Int = (var3.component2() as java.lang.Number).intValue();
   val var6: Pair = TuplesKt.to(end.getFirst(), end.getLast());
   val toMin: Int = (var6.component1() as java.lang.Number).intValue();
   return (`$this$remap` - fromMin) / (fromMax - fromMin) * ((var6.component2() as java.lang.Number).intValue() - toMin) + toMin;
}

public fun Double.remap(from: Pair<Double, Double>, to: Pair<Double, Double>): Double {
   val fromMin: Double = (from.component1() as java.lang.Number).doubleValue();
   val fromMax: Double = (from.component2() as java.lang.Number).doubleValue();
   val toMin: Double = (to.component1() as java.lang.Number).doubleValue();
   return (`$this$remap` - fromMin) / (fromMax - fromMin) * ((to.component2() as java.lang.Number).doubleValue() - toMin) + toMin;
}

public fun Double.remap(start: DoubleRange, end: DoubleRange): Double {
   val var4: Pair = TuplesKt.to(start.getStart(), start.getEndInclusive());
   val fromMin: Double = (var4.component1() as java.lang.Number).doubleValue();
   val fromMax: Double = (var4.component2() as java.lang.Number).doubleValue();
   val var9: Pair = TuplesKt.to(end.getStart(), end.getEndInclusive());
   val toMin: Double = (var9.component1() as java.lang.Number).doubleValue();
   return (`$this$remap` - fromMin) / (fromMax - fromMin) * ((var9.component2() as java.lang.Number).doubleValue() - toMin) + toMin;
}

public fun convertSphericalToCartesian(radius: Double, theta: Double, psi: Double): Vec3 {
   return new Vec3(radius * Math.cos(theta) * Math.sin(psi), radius * Math.sin(theta) * Math.sin(psi), radius * Math.cos(psi));
}

public fun getRotationMatrix(from: Vec3, to: Vec3): Matrix3f {
   val q: Quaternionf = new Quaternionf(0.0, 0.0, 0.0, 1.0);
   q.rotateTo(
      (new Vector3f((float)from.f_82479_, (float)from.f_82480_, (float)from.f_82481_)) as Vector3fc,
      (new Vector3f((float)to.f_82479_, (float)to.f_82480_, (float)to.f_82481_)) as Vector3fc
   );
   val var10000: Matrix3f = new Matrix3f().identity().rotation(q as Quaternionfc);
   return var10000;
}

public operator fun Matrix3f.times(vec3d: Vec3): Vec3 {
   val vec3f: Vector3f = new Vector3f();
   `$this$times`.transform((float)vec3d.f_82479_, (float)vec3d.f_82480_, (float)vec3d.f_82481_, vec3f);
   return new Vec3(vec3f.x, vec3f.y, vec3f.z);
}
