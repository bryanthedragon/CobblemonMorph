package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission

import net.minecraft.resources.ResourceLocation

public interface Permission {
   public val identifier: ResourceLocation
   public val level: PermissionLevel
   public val literal: String
}
