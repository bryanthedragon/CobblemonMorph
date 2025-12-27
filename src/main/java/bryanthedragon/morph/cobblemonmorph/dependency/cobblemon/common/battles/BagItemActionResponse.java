package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem

public class BagItemActionResponse(bagItem: BagItem, target: BattlePokemon, data: String? = null) : ShowdownActionResponse(ShowdownActionResponseType.FORCE_PASS) {
   public final val bagItem: BagItem
   public final val data: String?
   public final val target: BattlePokemon

   init {
      this.bagItem = bagItem;
      this.target = target;
      this.data = data;
   }

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
      return "useitem ${this.target.getUuid()} ${this.bagItem.getItemName()} ${this.bagItem.getShowdownInput(this.target.getActor(), this.target, this.data)}";
   }
}
