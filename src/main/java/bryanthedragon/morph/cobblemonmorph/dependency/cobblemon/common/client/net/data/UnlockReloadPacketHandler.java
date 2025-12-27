package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.data.CobblemonDataProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.UnlockReloadPacket
import net.minecraft.client.Minecraft

internal object UnlockReloadPacketHandler : ClientNetworkPacketHandler<UnlockReloadPacket> {
   public open fun handle(packet: UnlockReloadPacket, client: Minecraft) {
      CobblemonDataProvider.INSTANCE.setCanReload$common(true);
   }

   fun handleOnNettyThread(packet: UnlockReloadPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
