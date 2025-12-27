package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission

import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.server.level.ServerPlayer

public interface PermissionValidator {
   public abstract fun initialize() {
   }

   public abstract fun hasPermission(player: ServerPlayer, permission: Permission): Boolean {
   }

   public abstract fun hasPermission(source: SharedSuggestionProvider, permission: Permission): Boolean {
   }
}
