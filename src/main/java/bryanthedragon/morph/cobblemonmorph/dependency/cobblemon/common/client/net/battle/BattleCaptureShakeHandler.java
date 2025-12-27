package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBallDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureShakePacket
import net.minecraft.client.Minecraft

public object BattleCaptureShakeHandler : ClientNetworkPacketHandler<BattleCaptureShakePacket> {
   public open fun handle(packet: BattleCaptureShakePacket, client: Minecraft) {
      val var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         val var7: ClientBallDisplay = (var10000.getPokemonFromPNX(packet.getTargetPNX()).component2() as ActiveClientBattlePokemon).getBallCapturing();
         if (var7 != null) {
            var7.getShakeEmitter().emit(Unit.INSTANCE);
         }
      }
   }

   fun handleOnNettyThread(packet: BattleCaptureShakePacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
