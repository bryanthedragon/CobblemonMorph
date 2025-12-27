package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleChallenge
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CurrentKeyAccessorKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.PartySendBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleChallengeNotificationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.chat.Component

public object BattleChallengeNotificationHandler : ClientNetworkPacketHandler<BattleChallengeNotificationPacket> {
   public open fun handle(packet: BattleChallengeNotificationPacket, client: Minecraft) {
      CobblemonClient.INSTANCE.getRequests().getBattleChallenges().add(new ClientBattleChallenge(packet.getBattleChallengeId(), packet.getChallengerId()));
      val var10000: LocalPlayer = client.f_91074_;
      if (client.f_91074_ != null) {
         val var3: Array<Any> = new Object[]{packet.getChallengerName(), null};
         val var10004: Component = CurrentKeyAccessorKt.boundKey(PartySendBinding.INSTANCE).m_84875_();
         var3[1] = var10004;
         var10000.m_5661_(LocalizationUtilsKt.lang("challenge.receiver", var3) as Component, true);
      }
   }

   fun handleOnNettyThread(packet: BattleChallengeNotificationPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
