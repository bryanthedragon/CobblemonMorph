package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.ClosePasturePacket
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen

public object ClosePastureHandler : ClientNetworkPacketHandler<ClosePasturePacket> {
   public open fun handle(packet: ClosePasturePacket, client: Minecraft) {
      if (client.f_91080_ is PCGUI) {
         val var10000: Screen = client.f_91080_;
         (var10000 as PCGUI).getConfiguration().getExitFunction().invoke(var10000 as PCGUI);
      }
   }

   fun handleOnNettyThread(packet: ClosePasturePacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
