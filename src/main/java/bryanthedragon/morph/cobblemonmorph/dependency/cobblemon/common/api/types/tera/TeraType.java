package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ShowdownIdentifiable
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

public interface TeraType : ShowdownIdentifiable {
   public val displayName: Component
   public val id: ResourceLocation
   public val legalAsStatic: Boolean
}
