package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.callback.move

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.move.MoveSelectedPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object MoveSelectedHandler : ServerNetworkPacketHandler<MoveSelectedPacket> {
   public open fun handle(packet: MoveSelectedPacket, server: MinecraftServer, player: ServerPlayer) {
      MoveSelectCallbacks.INSTANCE.handleCallback(player, packet.getUuid(), packet.getIndex());
   }

   fun handleOnNettyThread(packet: MoveSelectedPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
