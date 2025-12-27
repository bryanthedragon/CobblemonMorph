package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.SwapPCPartyPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nSwapPCPartyPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SwapPCPartyPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/SwapPCPartyPokemonHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,32:1\n1#2:33\n*E\n"])
public object SwapPCPartyPokemonHandler : ServerNetworkPacketHandler<SwapPCPartyPokemonPacket> {
   public open fun handle(packet: SwapPCPartyPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      val party: PlayerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
      val var10000: PCStore = PCLinkManager.INSTANCE.getPC(player);
      if (var10000 == null) {
         val `$this$handle_u24lambda_u240`: SwapPCPartyPokemonHandler = this;
         new ClosePCPacket(null).sendToPlayer(player);
      } else {
         val var10: Pokemon = party.get(packet.getPartyPosition());
         if (var10 != null) {
            val var11: Pokemon = var10000.get(packet.getPcPosition());
            if (var11 != null) {
               if (var10.getUuid() == packet.getPartyPokemonID() && var11.getUuid() == packet.getPcPokemonID()) {
                  party.set(packet.getPartyPosition(), var11);
                  var10000.set(packet.getPcPosition(), var10);
               }
            }
         }
      }
   }

   fun handleOnNettyThread(packet: SwapPCPartyPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
