package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.starter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.SelectStarterPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object SelectStarterPacketHandler : ServerNetworkPacketHandler<SelectStarterPacket> {
   public open fun handle(packet: SelectStarterPacket, server: MinecraftServer, player: ServerPlayer) {
      Cobblemon.INSTANCE.getStarterHandler().chooseStarter(player, packet.getCategoryName(), packet.getSelected());
   }

   fun handleOnNettyThread(packet: SelectStarterPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
