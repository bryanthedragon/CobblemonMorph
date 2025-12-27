package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry

import net.minecraft.world.phys.Vec3

public data GeometricPoint(x: Float, y: Float, z: Float) {
   public final val w: Float
   public final val x: Float
   public final val y: Float
   public final val z: Float

   init {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = 1.0F;
   }

   public operator fun plus(right: GeometricPoint): GeometricPoint {
      return Companion.add(this, right);
   }

   public operator fun times(scalar: Float): GeometricPoint {
      return Companion.multiply(this, scalar);
   }

   public fun toVec3d(): Vec3 {
      return new Vec3(this.x, this.y, this.z);
   }

   public constructor() : this(0.0F, 0.0F, 0.0F)
   public constructor(x: Double, y: Double, z: Double) : this((float)x, (float)y, (float)z)
   public constructor(vec3d: Vec3) : this(vec3d.f_82479_, vec3d.f_82480_, vec3d.f_82481_)
   public operator fun component1(): Float {
      return this.x;
   }

   public operator fun component2(): Float {
      return this.y;
   }

   public operator fun component3(): Float {
      return this.z;
   }

   public fun copy(x: Float = this.x, y: Float = this.y, z: Float = this.z): GeometricPoint {
      return new GeometricPoint(x, y, z);
   }

   public override fun toString(): String {
      return "GeometricPoint(x=${this.x}, y=${this.y}, z=${this.z})";
   }

   public override fun hashCode(): Int {
      return (java.lang.Float.hashCode(this.x) * 31 + java.lang.Float.hashCode(this.y)) * 31 + java.lang.Float.hashCode(this.z);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is GeometricPoint) {
         return false;
      } else {
         val var2: GeometricPoint = other as GeometricPoint;
         if (java.lang.Float.compare(this.x, (other as GeometricPoint).x) != 0) {
            return false;
         } else if (java.lang.Float.compare(this.y, var2.y) != 0) {
            return false;
         } else {
            return java.lang.Float.compare(this.z, var2.z) == 0;
         }
      }
   }

   public companion object {
      public fun add(left: GeometricPoint, right: GeometricPoint): GeometricPoint {
         return new GeometricPoint(left.getX() + right.getX(), left.getY() + right.getY(), left.getZ() + right.getZ());
      }

      public fun multiply(point: GeometricPoint, scalar: Float): GeometricPoint {
         return new GeometricPoint(point.getX() * scalar, point.getY() * scalar, point.getZ() * scalar);
      }
   }
}
