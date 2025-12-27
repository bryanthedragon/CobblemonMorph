package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle

import java.util.UUID

public class ClientBattleChallenge(challengeId: UUID, challengerId: UUID) {
   public final val challengeId: UUID
   public final val challengerId: UUID

   init {
      this.challengeId = challengeId;
      this.challengerId = challengerId;
   }
}
