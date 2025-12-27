package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

public class DefaultActionResponse : ShowdownActionResponse(ShowdownActionResponseType.DEFAULT) {
   public override fun isValid(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?, forceSwitch: Boolean): Boolean {
      return true;
   }

   public override fun toShowdownString(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?): String {
      return "default";
   }
}
