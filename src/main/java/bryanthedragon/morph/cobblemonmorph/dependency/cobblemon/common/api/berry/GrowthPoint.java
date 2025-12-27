package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry

import net.minecraft.world.phys.Vec3

public data GrowthPoint(position: Vec3, rotation: Vec3) {
   public final val position: Vec3
   public final val rotation: Vec3

   init {
      this.position = position;
      this.rotation = rotation;
   }

   public operator fun component1(): Vec3 {
      return this.position;
   }

   public operator fun component2(): Vec3 {
      return this.rotation;
   }

   public fun copy(position: Vec3 = this.position, rotation: Vec3 = this.rotation): GrowthPoint {
      return new GrowthPoint(position, rotation);
   }

   public override fun toString(): String {
      return "GrowthPoint(position=${this.position}, rotation=${this.rotation})";
   }

   public override fun hashCode(): Int {
      return this.position.hashCode() * 31 + this.rotation.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is GrowthPoint) {
         return false;
      } else {
         val var2: GrowthPoint = other as GrowthPoint;
         if (!(this.position == (other as GrowthPoint).position)) {
            return false;
         } else {
            return this.rotation == var2.rotation;
         }
      }
   }
}
