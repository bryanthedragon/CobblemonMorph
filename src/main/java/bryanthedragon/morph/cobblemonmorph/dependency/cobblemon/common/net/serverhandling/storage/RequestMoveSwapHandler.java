package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.RequestMoveSwapPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object RequestMoveSwapHandler : ServerNetworkPacketHandler<RequestMoveSwapPacket> {
   public open fun handle(packet: RequestMoveSwapPacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: Pokemon = Cobblemon.INSTANCE.getStorage().getParty(player).get(packet.getSlot());
      if (var10000 != null) {
         val var7: Move = var10000.getMoveSet().get(packet.getMove1());
         if (var7 != null) {
            val var8: Move = var10000.getMoveSet().get(packet.getMove2());
            if (var8 != null) {
               if (!(var7 == var8)) {
                  var10000.getMoveSet().swapMove(packet.getMove1(), packet.getMove2());
               }
            }
         }
      }
   }

   fun handleOnNettyThread(packet: RequestMoveSwapPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
