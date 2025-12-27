package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

public class ForcePassActionResponse : ShowdownActionResponse(ShowdownActionResponseType.FORCE_PASS) {
   public override fun isValid(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?, forceSwitch: Boolean): Boolean {
      if (forceSwitch) {
         return false;
      } else if (showdownMoveSet == null) {
         return false;
      } else {
         return activeBattlePokemon.getActor().getExpectingPassActions().size() > 0;
      }
   }

   public override fun toShowdownString(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?): String {
      return "pass";
   }
}
