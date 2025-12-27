package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import java.util.UUID

public class PlayerDialogueFaceProvider(playerId: UUID) : DialogueFaceProvider {
   public final val playerId: UUID

   init {
      this.playerId = playerId;
   }
}
