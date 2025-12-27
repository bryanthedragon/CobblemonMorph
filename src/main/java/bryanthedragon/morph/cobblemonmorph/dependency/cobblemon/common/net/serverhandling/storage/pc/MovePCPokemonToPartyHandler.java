package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.MovePCPokemonToPartyPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nMovePCPokemonToPartyHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MovePCPokemonToPartyHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/pc/MovePCPokemonToPartyHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,31:1\n1#2:32\n*E\n"])
public object MovePCPokemonToPartyHandler : ServerNetworkPacketHandler<MovePCPokemonToPartyPacket> {
   public open fun handle(packet: MovePCPokemonToPartyPacket, server: MinecraftServer, player: ServerPlayer) {
      val party: PlayerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
      val var10000: PCStore = PCLinkManager.INSTANCE.getPC(player);
      if (var10000 == null) {
         val `$this$handle_u24lambda_u240`: MovePCPokemonToPartyHandler = this;
         new ClosePCPacket(null).sendToPlayer(player);
      } else {
         val var13: Pokemon = var10000.get(packet.getPcPosition());
         if (var13 != null) {
            if (var13.getUuid() == packet.getPokemonID()) {
               label31: {
                  val var14: PartyPosition = packet.getPartyPosition();
                  if (var14 != null) {
                     var15 = if (party.get(var14) == null) var14 else null;
                     if (var15 != null) {
                        break label31;
                     }
                  }

                  var15 = party.getFirstAvailablePosition();
                  if (var15 == null) {
                     return;
                  }
               }

               var10000.remove(packet.getPcPosition());
               party.set(var15, var13);
            }
         }
      }
   }

   fun handleOnNettyThread(packet: MovePCPokemonToPartyPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
