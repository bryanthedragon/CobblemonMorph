package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.settings

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings.ServerSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.settings.ServerSettingsPacket
import net.minecraft.client.Minecraft

public object ServerSettingsPacketHandler : ClientNetworkPacketHandler<ServerSettingsPacket> {
   public open fun handle(packet: ServerSettingsPacket, client: Minecraft) {
      ServerSettings.INSTANCE.setPreventCompletePartyDeposit(packet.getPreventCompletePartyDeposit());
      ServerSettings.INSTANCE.setDisplayEntityLevelLabel(packet.getDisplayEntityLevelLabel());
   }

   fun handleOnNettyThread(packet: ServerSettingsPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
