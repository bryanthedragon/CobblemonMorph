package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.MovePCPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nMovePCPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MovePCPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/pc/MovePCPokemonHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,35:1\n1#2:36\n*E\n"])
public object MovePCPokemonHandler : ServerNetworkPacketHandler<MovePCPokemonPacket> {
   public open fun handle(packet: MovePCPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: PCStore = PCLinkManager.INSTANCE.getPC(player);
      if (var10000 == null) {
         val `$this$handle_u24lambda_u240`: MovePCPokemonHandler = this;
         new ClosePCPacket(null).sendToPlayer(player);
      } else {
         val var9: Pokemon = var10000.get(packet.getOldPosition());
         if (var9 != null) {
            if (var9.getUuid() == packet.getPokemonID()) {
               if (var10000.isValidPosition(packet.getNewPosition())) {
                  if (var10000.get(packet.getNewPosition()) == null) {
                     var10000.move(var9, packet.getNewPosition());
                  }
               }
            }
         }
      }
   }

   fun handleOnNettyThread(packet: MovePCPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
