package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.evolution.AcceptEvolutionPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nAcceptEvolutionHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AcceptEvolutionHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/evolution/AcceptEvolutionHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,23:1\n288#2,2:24\n*S KotlinDebug\n*F\n+ 1 AcceptEvolutionHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/evolution/AcceptEvolutionHandler\n*L\n20#1:24,2\n*E\n"])
public object AcceptEvolutionHandler : ServerNetworkPacketHandler<AcceptEvolutionPacket> {
   public open fun handle(packet: AcceptEvolutionPacket, server: MinecraftServer, player: ServerPlayer) {
      var var10000: Pokemon = PlayerExtensionsKt.party(player).get(packet.getPokemonUUID());
      if (var10000 != null) {
         val var8: java.util.Iterator = var10000.getEvolutionProxy().server().iterator();

         while (true) {
            if (var8.hasNext()) {
               val `element$iv`: Any = var8.next();
               if (!StringsKt.equals((`element$iv` as Evolution).getId(), packet.getEvolutionId(), true)) {
                  continue;
               }

               var10000 = (Pokemon)`element$iv`;
               break;
            }

            var10000 = null;
            break;
         }

         val var13: Evolution = var10000 as Evolution;
         if (var10000 as Evolution != null) {
            var10000.getEvolutionProxy().server().start(var13);
         }
      }
   }

   fun handleOnNettyThread(packet: AcceptEvolutionPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
