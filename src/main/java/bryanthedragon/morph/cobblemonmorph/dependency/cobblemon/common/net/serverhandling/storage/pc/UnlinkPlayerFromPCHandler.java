package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.UnlinkPlayerFromPCPacket
import java.util.UUID
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object UnlinkPlayerFromPCHandler : ServerNetworkPacketHandler<UnlinkPlayerFromPCPacket> {
   public open fun handle(packet: UnlinkPlayerFromPCPacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: PCLinkManager = PCLinkManager.INSTANCE;
      val var10001: UUID = player.m_20148_();
      var10000.removeLink(var10001);
      PastureLinkManager.INSTANCE.getLinks().remove(player.m_20148_());
   }

   fun handleOnNettyThread(packet: UnlinkPlayerFromPCPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
