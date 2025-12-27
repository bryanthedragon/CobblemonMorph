package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.starter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.starter.RequestStarterScreenPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player

public object RequestStarterScreenHandler : ServerNetworkPacketHandler<RequestStarterScreenPacket> {
   public open fun handle(packet: RequestStarterScreenPacket, server: MinecraftServer, player: ServerPlayer) {
      val playerData: PlayerData = Cobblemon.INSTANCE.getPlayerData().get(player as Player);
      if (playerData.getStarterSelected()) {
         val var5: MutableComponent = LocalizationUtilsKt.lang("ui.starter.alreadyselected");
         player.m_213846_(TextKt.red(var5) as Component);
      } else if (playerData.getStarterLocked()) {
         val var10001: MutableComponent = LocalizationUtilsKt.lang("ui.starter.cannotchoose");
         player.m_213846_(TextKt.red(var10001) as Component);
      } else {
         Cobblemon.INSTANCE.getStarterHandler().requestStarterChoice(player);
      }
   }

   fun handleOnNettyThread(packet: RequestStarterScreenPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
