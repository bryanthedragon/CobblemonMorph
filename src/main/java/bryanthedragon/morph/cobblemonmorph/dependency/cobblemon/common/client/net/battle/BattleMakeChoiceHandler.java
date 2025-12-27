package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket
import net.minecraft.client.Minecraft

public object BattleMakeChoiceHandler : ClientNetworkPacketHandler<BattleMakeChoicePacket> {
   public open fun handle(packet: BattleMakeChoicePacket, client: Minecraft) {
      val var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         CobblemonClient.INSTANCE.getBattleOverlay().setPassedSeconds(0.0F);
         var10000.setMustChoose(true);
      }
   }

   fun handleOnNettyThread(packet: BattleMakeChoicePacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
