package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleMessageQueue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMessagePacket
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.locale.Language
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.FormattedText
import net.minecraft.network.chat.MutableComponent

public object BattleMessageHandler : ClientNetworkPacketHandler<BattleMessagePacket> {
   public open fun handle(packet: BattleMessagePacket, client: Minecraft) {
      val var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         val battle: ClientBattle = var10000;
         val textRenderer: Font = Minecraft.m_91087_().f_91062_;

         for (Component message : packet.getMessages()) {
            val var9: MutableComponent = message.m_6881_();
            val line: MutableComponent = TextKt.font(TextKt.bold(var9), CobblemonResources.INSTANCE.getDEFAULT_LARGE());
            val lines: java.util.List = Language.m_128107_().m_128112_(textRenderer.m_92865_().m_92414_(line as FormattedText, 142, line.m_7383_()));
            val var10: ClientBattleMessageQueue = battle.getMessages();
            var10.add(lines);
         }
      }
   }

   fun handleOnNettyThread(packet: BattleMessagePacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
