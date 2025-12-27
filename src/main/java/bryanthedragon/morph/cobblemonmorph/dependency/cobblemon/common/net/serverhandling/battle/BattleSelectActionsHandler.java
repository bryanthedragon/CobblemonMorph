package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.exception.IllegalActionChoiceException
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleQueueRequestPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.BattleSelectActionsPacket
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nBattleSelectActionsHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleSelectActionsHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/battle/BattleSelectActionsHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,36:1\n1#2:37\n*E\n"])
public object BattleSelectActionsHandler : ServerNetworkPacketHandler<BattleSelectActionsPacket> {
   public open fun handle(packet: BattleSelectActionsPacket, server: MinecraftServer, player: ServerPlayer) {
      var var10000: PokemonBattle = BattleRegistry.INSTANCE.getBattle(packet.getBattleId());
      if (var10000 != null) {
         val var8: java.util.Iterator = var10000.getActors().iterator();

         while (true) {
            if (var8.hasNext()) {
               val var9: Any = var8.next();
               if (!CollectionsKt.contains((var9 as BattleActor).getPlayerUUIDs(), player.m_20148_())) {
                  continue;
               }

               var10000 = (PokemonBattle)var9;
               break;
            }

            var10000 = null;
            break;
         }

         val var14: BattleActor = var10000 as BattleActor;
         if (var10000 as BattleActor != null) {
            val actor: BattleActor = var14;
            if (var14.getMustChoose()) {
               try {
                  actor.setActionResponses(packet.getShowdownActionResponses());
               } catch (var12: IllegalActionChoiceException) {
                  val var10001: java.lang.String = var12.getMessage();
                  player.m_213846_(TextKt.red(var10001) as Component);
                  val var10003: ShowdownActionRequest = var14.getRequest();
                  var14.sendUpdate(new BattleQueueRequestPacket(var10003));
                  var14.sendUpdate(new BattleMakeChoicePacket());
               }
            }
         }
      }
   }

   fun handleOnNettyThread(packet: BattleSelectActionsPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
