package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PlayerInteractOptionsPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.RequestPlayerInteractionsPacket
import java.util.EnumSet
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public object RequestInteractionsHandler : ServerNetworkPacketHandler<RequestPlayerInteractionsPacket> {
   public open fun handle(packet: RequestPlayerInteractionsPacket, server: MinecraftServer, player: ServerPlayer) {
      val options: EnumSet = EnumSet.of(PlayerInteractOptionsPacket.Options.TRADE);
      if (BattleRegistry.INSTANCE.getBattleByParticipatingPlayerId(packet.getTargetId()) != null and Cobblemon.INSTANCE.getConfig().getAllowSpectating()) {
         options.add(PlayerInteractOptionsPacket.Options.SPECTATE_BATTLE);
      } else {
         options.add(PlayerInteractOptionsPacket.Options.BATTLE);
      }

      new PlayerInteractOptionsPacket(options, packet.getTargetId(), packet.getTargetNumericId(), packet.getPokemonId()).sendToPlayer(player);
   }

   fun handleOnNettyThread(packet: RequestPlayerInteractionsPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
