package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleEndPacket
import net.minecraft.client.Minecraft

public object BattleEndHandler : ClientNetworkPacketHandler<BattleEndPacket> {
   public open fun handle(packet: BattleEndPacket, client: Minecraft) {
      CobblemonClient.INSTANCE.endBattle();
   }

   fun handleOnNettyThread(packet: BattleEndPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
