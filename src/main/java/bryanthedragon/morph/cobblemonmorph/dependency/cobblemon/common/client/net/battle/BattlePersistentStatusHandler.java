package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattlePersistentStatusPacket
import net.minecraft.client.Minecraft

public object BattlePersistentStatusHandler : ClientNetworkPacketHandler<BattlePersistentStatusPacket> {
   public open fun handle(packet: BattlePersistentStatusPacket, client: Minecraft) {
      val var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         val var5: ClientBattlePokemon = (var10000.getPokemonFromPNX(packet.getPnx()).component2() as ActiveClientBattlePokemon).getBattlePokemon();
         if (var5 != null) {
            var5.setStatus(packet.getStatus());
         }
      }
   }

   fun handleOnNettyThread(packet: BattlePersistentStatusPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
