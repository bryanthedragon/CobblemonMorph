package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.Permission
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import java.util.UUID
import net.minecraft.server.level.ServerPlayer

public class PermissiblePcLink(pc: PCStore, player: ServerPlayer, permission: Permission) : PCLink {
   private final val permission: Permission

   init {
      val var10002: UUID = player.m_20148_();
      super(pc, var10002);
      this.permission = permission;
   }

   public override fun isPermitted(player: ServerPlayer): Boolean {
      val result: Boolean = Cobblemon.INSTANCE.getPermissionValidator().hasPermission(player, this.permission);
      if (!result) {
         val var10000: PCLinkManager = PCLinkManager.INSTANCE;
         val var10001: UUID = player.m_20148_();
         var10000.removeLink(var10001);
      }

      return result;
   }
}
