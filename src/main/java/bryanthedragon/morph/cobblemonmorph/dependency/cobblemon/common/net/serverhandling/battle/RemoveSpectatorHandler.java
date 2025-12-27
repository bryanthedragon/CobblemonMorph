package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.RemoveSpectatorPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object RemoveSpectatorHandler : ServerNetworkPacketHandler<RemoveSpectatorPacket> {
   public open fun handle(packet: RemoveSpectatorPacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: PokemonBattle = BattleRegistry.INSTANCE.getBattle(packet.getBattleId());
      if (var10000 != null) {
         val var4: java.util.Set = var10000.getSpectators();
         if (var4 != null) {
            var4.remove(player.m_20148_());
         }
      }
   }

   fun handleOnNettyThread(packet: RemoveSpectatorPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
