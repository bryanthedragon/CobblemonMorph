package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math

import org.joml.Quaternionf
import org.joml.Vector3f

public fun Quaternionf.fromEulerXYZDegrees(vector: Vector3f): Quaternionf {
   return fromEulerXYZ(
      `$this$fromEulerXYZDegrees`, (float)Math.toRadians((double)vector.x), (float)Math.toRadians((double)vector.y), (float)Math.toRadians((double)vector.z)
   );
}

public fun Quaternionf.fromEulerXYZ(x: Float, y: Float, z: Float): Quaternionf {
   hamiltonProduct(`$this$fromEulerXYZ`, new Quaternionf((float)Math.sin((double)(x / 2.0F)), 0.0F, 0.0F, (float)Math.cos((double)(x / 2.0F))));
   hamiltonProduct(`$this$fromEulerXYZ`, new Quaternionf(0.0F, (float)Math.sin((double)(y / 2.0F)), 0.0F, (float)Math.cos((double)(y / 2.0F))));
   hamiltonProduct(`$this$fromEulerXYZ`, new Quaternionf(0.0F, 0.0F, (float)Math.sin((double)(z / 2.0F)), (float)Math.cos((double)(z / 2.0F))));
   return `$this$fromEulerXYZ`;
}

public fun Quaternionf.hamiltonProduct(other: Quaternionf): Quaternionf {
   val f: Float = `$this$hamiltonProduct`.x;
   val g: Float = `$this$hamiltonProduct`.y;
   val h: Float = `$this$hamiltonProduct`.z;
   val i: Float = `$this$hamiltonProduct`.w;
   val j: Float = other.x;
   val k: Float = other.y;
   val l: Float = other.z;
   val m: Float = other.w;
   `$this$hamiltonProduct`.x = `$this$hamiltonProduct`.w * other.x
      + `$this$hamiltonProduct`.x * other.w
      + `$this$hamiltonProduct`.y * other.z
      - `$this$hamiltonProduct`.z * other.y;
   `$this$hamiltonProduct`.y = i * k - f * l + g * m + h * j;
   `$this$hamiltonProduct`.z = i * l + f * k - g * j + h * m;
   `$this$hamiltonProduct`.w = i * m - f * j - g * k - h * l;
   return `$this$hamiltonProduct`;
}
