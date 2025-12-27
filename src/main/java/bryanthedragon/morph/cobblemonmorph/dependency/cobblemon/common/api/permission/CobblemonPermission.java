package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.resources.ResourceLocation

public data CobblemonPermission(node: String, level: PermissionLevel) : Permission {
   public open val identifier: ResourceLocation
   public open val level: PermissionLevel
   public open val literal: String
   private final val node: String

   init {
      this.node = node;
      this.level = level;
      this.identifier = MiscUtilsKt.cobblemonResource(this.node);
      this.literal = "cobblemon.${this.node}";
   }

   private operator fun component1(): String {
      return this.node;
   }

   public operator fun component2(): PermissionLevel {
      return this.level;
   }

   public fun copy(node: String = this.node, level: PermissionLevel = this.level): CobblemonPermission {
      return new CobblemonPermission(node, level);
   }

   public override fun toString(): String {
      return "CobblemonPermission(node=${this.node}, level=${this.level})";
   }

   public override fun hashCode(): Int {
      return this.node.hashCode() * 31 + this.level.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is CobblemonPermission) {
         return false;
      } else {
         val var2: CobblemonPermission = other as CobblemonPermission;
         if (!(this.node == (other as CobblemonPermission).node)) {
            return false;
         } else {
            return this.level === var2.level;
         }
      }
   }
}
