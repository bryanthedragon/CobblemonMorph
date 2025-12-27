package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry

public data GeometricNormal(x: Float, y: Float, z: Float) {
   public final val w: Float
   public final val x: Float
   public final val y: Float
   public final val z: Float

   init {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public operator fun plus(right: GeometricNormal): GeometricNormal {
      return Companion.add(this, right);
   }

   public operator fun times(scalar: Float): GeometricNormal {
      return Companion.multiply(this, scalar);
   }

   public constructor() : this(0.0F, 0.0F, 0.0F)
   public operator fun component1(): Float {
      return this.x;
   }

   public operator fun component2(): Float {
      return this.y;
   }

   public operator fun component3(): Float {
      return this.z;
   }

   public fun copy(x: Float = this.x, y: Float = this.y, z: Float = this.z): GeometricNormal {
      return new GeometricNormal(x, y, z);
   }

   public override fun toString(): String {
      return "GeometricNormal(x=${this.x}, y=${this.y}, z=${this.z})";
   }

   public override fun hashCode(): Int {
      return (java.lang.Float.hashCode(this.x) * 31 + java.lang.Float.hashCode(this.y)) * 31 + java.lang.Float.hashCode(this.z);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is GeometricNormal) {
         return false;
      } else {
         val var2: GeometricNormal = other as GeometricNormal;
         if (java.lang.Float.compare(this.x, (other as GeometricNormal).x) != 0) {
            return false;
         } else if (java.lang.Float.compare(this.y, var2.y) != 0) {
            return false;
         } else {
            return java.lang.Float.compare(this.z, var2.z) == 0;
         }
      }
   }

   public companion object {
      public fun add(left: GeometricNormal, right: GeometricNormal): GeometricNormal {
         return new GeometricNormal(left.getX() + right.getX(), left.getY() + right.getY(), left.getZ() + right.getZ());
      }

      public fun multiply(normal: GeometricNormal, scalar: Float): GeometricNormal {
         return new GeometricNormal(normal.getX() * scalar, normal.getY() * scalar, normal.getZ() * scalar);
      }
   }
}
