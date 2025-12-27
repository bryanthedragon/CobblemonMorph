package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMadeInvalidChoicePacket
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen

public object BattleMadeInvalidChoiceHandler : ClientNetworkPacketHandler<BattleMadeInvalidChoicePacket> {
   public open fun handle(packet: BattleMadeInvalidChoicePacket, client: Minecraft) {
      val var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         var10000.setMustChoose(true);
         val gui: Screen = Minecraft.m_91087_().f_91080_;
         if (gui is BattleGUI) {
            (gui as BattleGUI).removeInvalidBattleActionSelection();
         }
      }
   }

   fun handleOnNettyThread(packet: BattleMadeInvalidChoicePacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
