package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity
import net.minecraft.server.level.ServerPlayer

public fun interface PasturePermissionController {
   public abstract fun permit(player: ServerPlayer, pastureBlockEntity: PokemonPastureBlockEntity): PasturePermissions? {
   }
}
