package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.phys.Vec3

public data SpawningArea(cause: SpawnCause, world: ServerLevel, baseX: Int, baseY: Int, baseZ: Int, length: Int, height: Int, width: Int) {
   public final val baseX: Int
   public final val baseY: Int
   public final val baseZ: Int
   public final val cause: SpawnCause
   public final val height: Int
   public final val length: Int
   public final val width: Int
   public final val world: ServerLevel

   init {
      this.cause = cause;
      this.world = world;
      this.baseX = baseX;
      this.baseY = baseY;
      this.baseZ = baseZ;
      this.length = length;
      this.height = height;
      this.width = width;
   }

   public fun getCenter(): Vec3 {
      return new Vec3(this.baseX + this.length / 2.0, this.baseY + this.height / 2.0, this.baseZ + this.width / 2.0);
   }

   public operator fun component1(): SpawnCause {
      return this.cause;
   }

   public operator fun component2(): ServerLevel {
      return this.world;
   }

   public operator fun component3(): Int {
      return this.baseX;
   }

   public operator fun component4(): Int {
      return this.baseY;
   }

   public operator fun component5(): Int {
      return this.baseZ;
   }

   public operator fun component6(): Int {
      return this.length;
   }

   public operator fun component7(): Int {
      return this.height;
   }

   public operator fun component8(): Int {
      return this.width;
   }

   public fun copy(
      cause: SpawnCause = this.cause,
      world: ServerLevel = this.world,
      baseX: Int = this.baseX,
      baseY: Int = this.baseY,
      baseZ: Int = this.baseZ,
      length: Int = this.length,
      height: Int = this.height,
      width: Int = this.width
   ): SpawningArea {
      return new SpawningArea(cause, world, baseX, baseY, baseZ, length, height, width);
   }

   public override fun toString(): String {
      return "SpawningArea(cause=${this.cause}, world=${this.world}, baseX=${this.baseX}, baseY=${this.baseY}, baseZ=${this.baseZ}, length=${this.length}, height=${this.height}, width=${this.width})";
   }

   public override fun hashCode(): Int {
      return (
               (
                        (
                                 (
                                          ((this.cause.hashCode() * 31 + this.world.hashCode()) * 31 + Integer.hashCode(this.baseX)) * 31
                                             + Integer.hashCode(this.baseY)
                                       )
                                       * 31
                                    + Integer.hashCode(this.baseZ)
                              )
                              * 31
                           + Integer.hashCode(this.length)
                     )
                     * 31
                  + Integer.hashCode(this.height)
            )
            * 31
         + Integer.hashCode(this.width);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is SpawningArea) {
         return false;
      } else {
         val var2: SpawningArea = other as SpawningArea;
         if (!(this.cause == (other as SpawningArea).cause)) {
            return false;
         } else if (!(this.world == var2.world)) {
            return false;
         } else if (this.baseX != var2.baseX) {
            return false;
         } else if (this.baseY != var2.baseY) {
            return false;
         } else if (this.baseZ != var2.baseZ) {
            return false;
         } else if (this.length != var2.length) {
            return false;
         } else if (this.height != var2.height) {
            return false;
         } else {
            return this.width == var2.width;
         }
      }
   }
}
