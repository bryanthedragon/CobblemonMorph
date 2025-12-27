package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.ParentWidget
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public abstract class BattleActionSelection : ParentWidget {
   public final val battleGUI: BattleGUI

   public final val opacity: Float
      public final get() {
         return this.battleGUI.getOpacity();
      }


   public final val request: SingleActionRequest

   open fun BattleActionSelection(battleGUI: BattleGUI, request: SingleActionRequest, x: Int, y: Int, width: Int, height: Int, name: MutableComponent) {
      super(x, y, width, height, name as Component);
      this.battleGUI = battleGUI;
      this.request = request;
   }
}
