package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.starter.ClientPlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter.SetClientPlayerDataPacket
import net.minecraft.client.Minecraft

public object SetClientPlayerDataHandler : ClientNetworkPacketHandler<SetClientPlayerDataPacket> {
   public open fun handle(packet: SetClientPlayerDataPacket, client: Minecraft) {
      CobblemonClient.INSTANCE
         .setClientPlayerData(new ClientPlayerData(packet.getPromptStarter(), packet.getStarterLocked(), packet.getStarterSelected(), packet.getStarterUUID()));
      if (packet.getResetStarterPrompt() == true) {
         CobblemonClient.INSTANCE.setCheckedStarterScreen(false);
         CobblemonClient.INSTANCE.getOverlay().resetAttachedToast();
      }
   }

   fun handleOnNettyThread(packet: SetClientPlayerDataPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
