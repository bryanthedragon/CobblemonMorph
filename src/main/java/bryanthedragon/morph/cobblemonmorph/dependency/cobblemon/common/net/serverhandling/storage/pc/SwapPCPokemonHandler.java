package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.SwapPCPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nSwapPCPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SwapPCPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/pc/SwapPCPokemonHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,26:1\n1#2:27\n*E\n"])
public object SwapPCPokemonHandler : ServerNetworkPacketHandler<SwapPCPokemonPacket> {
   public open fun handle(packet: SwapPCPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: PCStore = PCLinkManager.INSTANCE.getPC(player);
      if (var10000 == null) {
         val `$this$handle_u24lambda_u240`: SwapPCPokemonHandler = this;
         new ClosePCPacket(null).sendToPlayer(player);
      } else {
         val var7: Pokemon = var10000.get(packet.getPosition1());
         if ((if (var7 != null) var7.getUuid() else null) == packet.getPokemon1ID()) {
            val var8: Pokemon = var10000.get(packet.getPosition2());
            if ((if (var8 != null) var8.getUuid() else null) == packet.getPokemon2ID()) {
               var10000.swap(packet.getPosition1(), packet.getPosition2());
               return;
            }
         }
      }
   }

   fun handleOnNettyThread(packet: SwapPCPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
