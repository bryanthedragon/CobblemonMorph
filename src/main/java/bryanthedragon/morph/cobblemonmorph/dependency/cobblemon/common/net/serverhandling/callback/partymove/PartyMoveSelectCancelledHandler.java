package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.callback.partymove

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartyMoveSelectCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.partymove.PartyMoveSelectCancelledPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object PartyMoveSelectCancelledHandler : ServerNetworkPacketHandler<PartyMoveSelectCancelledPacket> {
   public open fun handle(packet: PartyMoveSelectCancelledPacket, server: MinecraftServer, player: ServerPlayer) {
      PartyMoveSelectCallbacks.INSTANCE.handleCancelled(player, packet.getUuid());
   }

   fun handleOnNettyThread(packet: PartyMoveSelectCancelledPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
