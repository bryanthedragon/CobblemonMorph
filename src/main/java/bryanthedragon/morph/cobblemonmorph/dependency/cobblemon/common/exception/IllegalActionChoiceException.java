package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.exception

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor

public class IllegalActionChoiceException(actor: BattleActor, message: String) : IllegalArgumentException(message) {
   public final val actor: BattleActor

   init {
      this.actor = actor;
   }
}
