package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.MoveTileOffscreenAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleFaintPacket
import java.util.concurrent.ConcurrentLinkedQueue
import net.minecraft.client.Minecraft

public object BattleFaintHandler : ClientNetworkPacketHandler<BattleFaintPacket> {
   public open fun handle(packet: BattleFaintPacket, client: Minecraft) {
      val var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         val var3: Pair = var10000.getPokemonFromPNX(packet.getPnx());
         if (var3 != null) {
            val var4: ActiveClientBattlePokemon = var3.getSecond() as ActiveClientBattlePokemon;
            if (var4 != null) {
               val var5: ConcurrentLinkedQueue = var4.getAnimations();
               if (var5 != null) {
                  var5.add(new MoveTileOffscreenAnimation(0.0F, 1, null));
               }
            }
         }
      }
   }

   fun handleOnNettyThread(packet: BattleFaintPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
