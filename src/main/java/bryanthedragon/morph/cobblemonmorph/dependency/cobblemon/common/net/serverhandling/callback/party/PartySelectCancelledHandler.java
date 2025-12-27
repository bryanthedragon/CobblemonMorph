package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.callback.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.party.PartySelectCancelledPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object PartySelectCancelledHandler : ServerNetworkPacketHandler<PartySelectCancelledPacket> {
   public open fun handle(packet: PartySelectCancelledPacket, server: MinecraftServer, player: ServerPlayer) {
      PartySelectCallbacks.INSTANCE.handleCancelled(player, packet.getUuid());
   }

   fun handleOnNettyThread(packet: PartySelectCancelledPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
