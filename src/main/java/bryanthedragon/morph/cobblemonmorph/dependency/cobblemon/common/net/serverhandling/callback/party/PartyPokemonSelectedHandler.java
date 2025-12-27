package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.callback.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.party.PartyPokemonSelectedPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object PartyPokemonSelectedHandler : ServerNetworkPacketHandler<PartyPokemonSelectedPacket> {
   public open fun handle(packet: PartyPokemonSelectedPacket, server: MinecraftServer, player: ServerPlayer) {
      PartySelectCallbacks.INSTANCE.handleCallback(player, packet.getUuid(), packet.getIndex());
   }

   fun handleOnNettyThread(packet: PartyPokemonSelectedPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
