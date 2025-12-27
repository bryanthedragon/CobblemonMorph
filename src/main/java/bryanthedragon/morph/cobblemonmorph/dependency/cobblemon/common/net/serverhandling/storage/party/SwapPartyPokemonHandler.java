package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party.SwapPartyPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object SwapPartyPokemonHandler : ServerNetworkPacketHandler<SwapPartyPokemonPacket> {
   public open fun handle(packet: SwapPartyPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      val party: PlayerPartyStore = PlayerExtensionsKt.party(player);
      var var10000: Pokemon = party.get(packet.getPosition1());
      if (var10000 != null) {
         var10000 = party.get(packet.getPosition2());
         if (var10000 != null) {
            if (var10000.getUuid() == packet.getPokemon1ID()
               && var10000.getUuid() == packet.getPokemon2ID()
               && !(packet.getPosition1() == packet.getPosition2())) {
               party.swap(packet.getPosition1(), packet.getPosition2());
            }
         }
      }
   }

   fun handleOnNettyThread(packet: SwapPartyPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
