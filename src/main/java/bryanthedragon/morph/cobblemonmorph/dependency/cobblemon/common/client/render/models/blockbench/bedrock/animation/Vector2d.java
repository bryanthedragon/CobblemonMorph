package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

private data class Vector2d(a: Double, b: Double) {
   public final val a: Double
   public final val b: Double

   init {
      this.a = a;
      this.b = b;
   }

   public operator fun component1(): Double {
      return this.a;
   }

   public operator fun component2(): Double {
      return this.b;
   }

   public fun copy(a: Double = this.a, b: Double = this.b): Vector2d {
      return new Vector2d(a, b);
   }

   public override fun toString(): String {
      return "Vector2d(a=${this.a}, b=${this.b})";
   }

   public override fun hashCode(): Int {
      return java.lang.Double.hashCode(this.a) * 31 + java.lang.Double.hashCode(this.b);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is Vector2d) {
         return false;
      } else {
         val var2: Vector2d = other as Vector2d;
         if (java.lang.Double.compare(this.a, (other as Vector2d).a) != 0) {
            return false;
         } else {
            return java.lang.Double.compare(this.b, var2.b) == 0;
         }
      }
   }
}
