package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import java.util.UUID
import net.minecraft.server.level.ServerPlayer

public open class PCLink(pc: PCStore, playerID: UUID) {
   public open val pc: PCStore
   public open val playerID: UUID

   init {
      this.pc = pc;
      this.playerID = playerID;
   }

   public open fun isPermitted(player: ServerPlayer): Boolean {
      return true;
   }
}
