package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.callback.move

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.move.MoveSelectCancelledPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object MoveSelectCancelledHandler : ServerNetworkPacketHandler<MoveSelectCancelledPacket> {
   public open fun handle(packet: MoveSelectCancelledPacket, server: MinecraftServer, player: ServerPlayer) {
      MoveSelectCallbacks.INSTANCE.handleCancelled(player, packet.getUuid());
   }

   fun handleOnNettyThread(packet: MoveSelectCancelledPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
