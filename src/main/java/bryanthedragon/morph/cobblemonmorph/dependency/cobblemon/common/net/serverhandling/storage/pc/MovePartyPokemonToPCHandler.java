package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.MovePartyPokemonToPCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nMovePartyPokemonToPCHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MovePartyPokemonToPCHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/pc/MovePartyPokemonToPCHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,34:1\n1#2:35\n*E\n"])
public object MovePartyPokemonToPCHandler : ServerNetworkPacketHandler<MovePartyPokemonToPCPacket> {
   public open fun handle(packet: MovePartyPokemonToPCPacket, server: MinecraftServer, player: ServerPlayer) {
      val party: PlayerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
      val var10000: PCStore = PCLinkManager.INSTANCE.getPC(player);
      if (var10000 == null) {
         val `$this$handle_u24lambda_u240`: MovePartyPokemonToPCHandler = this;
         new ClosePCPacket(null).sendToPlayer(player);
      } else {
         val var13: Pokemon = party.get(packet.getPartyPosition());
         if (var13 != null) {
            if (var13.getUuid() == packet.getPokemonID()) {
               if (CollectionsKt.filterNotNull(party).size() != 1 || !Cobblemon.INSTANCE.getConfig().getPreventCompletePartyDeposit()) {
                  label35: {
                     val var14: PCPosition = packet.getPcPosition();
                     if (var14 != null) {
                        var15 = if (var10000.get(var14) == null) var14 else null;
                        if (var15 != null) {
                           break label35;
                        }
                     }

                     var15 = var10000.getFirstAvailablePosition();
                     if (var15 == null) {
                        return;
                     }
                  }

                  party.remove(packet.getPartyPosition());
                  var10000.set(var15, var13);
               }
            }
         }
      }
   }

   fun handleOnNettyThread(packet: MovePartyPokemonToPCPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
