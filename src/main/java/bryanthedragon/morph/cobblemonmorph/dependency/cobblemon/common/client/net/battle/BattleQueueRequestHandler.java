package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleQueueRequestPacket
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer

@SourceDebugExtension(["SMAP\nBattleQueueRequestHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleQueueRequestHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleQueueRequestHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,23:1\n1#2:24\n*E\n"])
public object BattleQueueRequestHandler : ClientNetworkPacketHandler<BattleQueueRequestPacket> {
   public open fun handle(packet: BattleQueueRequestPacket, client: Minecraft) {
      var var10000: ClientBattle = CobblemonClient.INSTANCE.getBattle();
      if (var10000 != null) {
         val var6: java.util.Iterator = var10000.getSide1().getActors().iterator();

         while (true) {
            if (var6.hasNext()) {
               val var7: Any = var6.next();
               val var11: UUID = (var7 as ClientBattleActor).getUuid();
               val var10001: LocalPlayer = Minecraft.m_91087_().f_91074_;
               if (!(var11 == (if (var10001 != null) var10001.m_20148_() else null))) {
                  continue;
               }

               var10000 = (ClientBattle)var7;
               break;
            }

            var10000 = null;
            break;
         }

         val var12: ClientBattleActor = var10000 as ClientBattleActor;
         if (var10000 as ClientBattleActor != null) {
            var10000 = CobblemonClient.INSTANCE.getBattle();
            if (var10000 != null) {
               var10000.setPendingActionRequests(SingleActionRequest.Companion.composeFrom(var12, packet.getRequest()));
            }
         }
      }
   }

   fun handleOnNettyThread(packet: BattleQueueRequestPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
