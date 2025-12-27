package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party.MovePartyPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object MovePartyPokemonHandler : ServerNetworkPacketHandler<MovePartyPokemonPacket> {
   public open fun handle(packet: MovePartyPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      val party: PlayerPartyStore = PlayerExtensionsKt.party(player);
      val var10000: Pokemon = party.get(packet.getOldPosition());
      if (var10000 != null) {
         if (var10000.getUuid() == packet.getPokemonID()) {
            if (party.get(packet.getNewPosition()) == null) {
               party.move(var10000, packet.getNewPosition());
            }
         }
      }
   }

   fun handleOnNettyThread(packet: MovePartyPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
