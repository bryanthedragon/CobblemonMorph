package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.callback.partymove

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartyMoveSelectCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.partymove.PartyPokemonMoveSelectedPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object PartyPokemonMoveSelectedHandler : ServerNetworkPacketHandler<PartyPokemonMoveSelectedPacket> {
   public open fun handle(packet: PartyPokemonMoveSelectedPacket, server: MinecraftServer, player: ServerPlayer) {
      PartyMoveSelectCallbacks.INSTANCE.handleCallback(player, packet.getUuid(), packet.getPokemonIndex(), packet.getMoveIndex());
   }

   fun handleOnNettyThread(packet: PartyPokemonMoveSelectedPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
