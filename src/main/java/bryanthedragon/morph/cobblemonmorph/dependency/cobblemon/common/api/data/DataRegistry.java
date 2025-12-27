package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager

public interface DataRegistry {
   public val id: ResourceLocation
   public val observable: SimpleObservable<out DataRegistry>
   public val type: PackType

   public abstract fun reload(manager: ResourceManager) {
   }

   public abstract fun sync(player: ServerPlayer) {
   }
}
