package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleChallengeExpiredPacket
import kotlin.jvm.functions.Function1
import net.minecraft.client.Minecraft

public object BattleChallengeExpiredHandler : ClientNetworkPacketHandler<BattleChallengeExpiredPacket> {
   public open fun handle(packet: BattleChallengeExpiredPacket, client: Minecraft) {
      CobblemonClient.INSTANCE.getRequests().getBattleChallenges().removeIf(BattleChallengeExpiredHandler::handle$lambda$0);
   }

   fun handleOnNettyThread(packet: BattleChallengeExpiredPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }

   @JvmStatic
   fun `handle$lambda$0`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}
