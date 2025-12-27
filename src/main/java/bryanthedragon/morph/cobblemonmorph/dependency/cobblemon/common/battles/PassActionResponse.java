package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

public object PassActionResponse : ShowdownActionResponse(ShowdownActionResponseType.PASS) {
   public override fun isValid(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?, forceSwitch: Boolean): Boolean {
      return true;
   }

   public override fun toShowdownString(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?): String {
      return "pass";
   }
}
