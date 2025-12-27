package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

public class ForfeitActionResponse : ShowdownActionResponse(ShowdownActionResponseType.FORFEIT) {
   public override fun isValid(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?, forceSwitch: Boolean): Boolean {
      return true;
   }

   public override fun toShowdownString(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?): String {
      return "forfeit";
   }
}
