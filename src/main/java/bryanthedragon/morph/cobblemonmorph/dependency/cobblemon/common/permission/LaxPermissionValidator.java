package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.permission

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.Permission
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.PermissionValidator
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.server.level.ServerPlayer

public class LaxPermissionValidator : PermissionValidator {
   public override fun initialize() {
      Cobblemon.INSTANCE
         .getLOGGER()
         .info(
            "Booting LaxPermissionValidator, permissions will be checked using Minecrafts permission level system, see https://minecraft.fandom.com/wiki/Permission_level"
         );
   }

   public override fun hasPermission(player: ServerPlayer, permission: Permission): Boolean {
      return player.m_20310_(permission.getLevel().getNumericalValue());
   }

   public override fun hasPermission(source: SharedSuggestionProvider, permission: Permission): Boolean {
      return source.m_6761_(permission.getLevel().getNumericalValue());
   }
}
