package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.toast

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.toast.ToastTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast.ToastPacket
import net.minecraft.client.Minecraft

public object ToastPacketHandler : ClientNetworkPacketHandler<ToastPacket> {
   public open fun handle(packet: ToastPacket, client: Minecraft) {
      client.m_201446_(ToastPacketHandler::handle$lambda$0);
   }

   fun handleOnNettyThread(packet: ToastPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }

   @JvmStatic
   fun `handle$lambda$0`(`$packet`: ToastPacket, `$client`: Minecraft) {
      ToastTracker.INSTANCE.handle(`$packet`, `$client`);
   }
}
