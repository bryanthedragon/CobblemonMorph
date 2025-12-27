package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.BenchMovePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nBenchMoveHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BenchMoveHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/BenchMoveHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,45:1\n1#2:46\n2624#3,3:47\n1747#3,3:50\n*S KotlinDebug\n*F\n+ 1 BenchMoveHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/BenchMoveHandler\n*L\n31#1:47,3\n31#1:50,3\n*E\n"])
public object BenchMoveHandler : ServerNetworkPacketHandler<BenchMovePacket> {
   public open fun handle(packet: BenchMovePacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: PokemonStore;
      if (packet.isParty()) {
         var10000 = PlayerExtensionsKt.party(player);
      } else {
         val var20: PCStore = PCLinkManager.INSTANCE.getPC(player);
         if (var20 == null) {
            val var14: BenchMoveHandler = this;
            new ClosePCPacket(null).sendToPlayer(player);
            return;
         }

         var10000 = var20;
      }

      val var21: Pokemon = var10000.get(packet.getUuid());
      if (var21 != null) {
         var `$this$any$iv`: java.lang.Iterable = var21.getMoveSet();
         var var22: Boolean;
         if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
            var22 = true;
         } else {
            val var8: java.util.Iterator = `$this$any$iv`.iterator();

            while (true) {
               if (!var8.hasNext()) {
                  var22 = true;
                  break;
               }

               if ((var8.next() as Move).getTemplate() == packet.getOldMove()) {
                  var22 = false;
                  break;
               }
            }
         }

         if (!var22) {
            `$this$any$iv` = var21.getMoveSet();
            var var23: Boolean;
            if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
               var23 = false;
            } else {
               val var15: java.util.Iterator = `$this$any$iv`.iterator();

               while (true) {
                  if (!var15.hasNext()) {
                     var23 = false;
                     break;
                  }

                  if ((var15.next() as Move).getTemplate() == packet.getNewMove()) {
                     var23 = true;
                     break;
                  }
               }
            }

            if (!var23) {
               if (!var21.getAllAccessibleMoves().contains(packet.getNewMove())) {
                  Cobblemon.INSTANCE
                     .getLOGGER()
                     .warn(
                        "${player.m_7755_()} tried to bench ${packet.getOldMove().getName()} for ${packet.getNewMove().getName()} but it doesn't have ${packet.getNewMove()
                           .getName()} learned. Could be a hacker!"
                     );
                  return;
               }

               var21.exchangeMove(packet.getOldMove(), packet.getNewMove());
               return;
            }
         }

         var21.getMoveSet().update();
      }
   }

   fun handleOnNettyThread(packet: BenchMovePacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
