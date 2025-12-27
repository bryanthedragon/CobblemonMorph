package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ForcePassActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleApplyPassResponsePacket
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen

@SourceDebugExtension(["SMAP\nBattleApplyPassResponseHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleApplyPassResponseHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleApplyPassResponseHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,31:1\n288#2,2:32\n*S KotlinDebug\n*F\n+ 1 BattleApplyPassResponseHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleApplyPassResponseHandler\n*L\n21#1:32,2\n*E\n"])
public object BattleApplyPassResponseHandler : ClientNetworkPacketHandler<BattleApplyPassResponsePacket> {
   public open fun handle(packet: BattleApplyPassResponsePacket, client: Minecraft) {
      val var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         val var8: java.util.Iterator = var10000.getPendingActionRequests().iterator();

         while (true) {
            if (var8.hasNext()) {
               val `element$iv`: Any = var8.next();
               if ((`element$iv` as SingleActionRequest).getResponse() != null) {
                  continue;
               }

               var13 = `element$iv`;
               break;
            }

            var13 = null;
            break;
         }

         val var14: SingleActionRequest = var13 as SingleActionRequest;
         if (var13 as SingleActionRequest != null) {
            val res: ForcePassActionResponse = new ForcePassActionResponse();
            val var12: Screen = Minecraft.m_91087_().f_91080_;
            if (var12 is BattleGUI) {
               (var12 as BattleGUI).selectAction(var14, res);
            } else {
               var14.setResponse(res);
               var10000.checkForFinishedChoosing();
            }
         }
      }
   }

   fun handleOnNettyThread(packet: BattleApplyPassResponsePacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
