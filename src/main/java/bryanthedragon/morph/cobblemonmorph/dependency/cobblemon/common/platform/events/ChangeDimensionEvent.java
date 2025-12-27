package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events

import net.minecraft.server.level.ServerPlayer

public class ChangeDimensionEvent(player: ServerPlayer) {
   public final val player: ServerPlayer

   init {
      this.player = player;
   }
}
