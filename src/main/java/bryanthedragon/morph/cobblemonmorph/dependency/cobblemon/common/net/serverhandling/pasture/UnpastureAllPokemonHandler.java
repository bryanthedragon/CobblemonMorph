package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLink
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.ClosePasturePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.PokemonUnpasturedPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.UnpastureAllPokemonPacket
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.block.entity.BlockEntity

@SourceDebugExtension(["SMAP\nUnpastureAllPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UnpastureAllPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pasture/UnpastureAllPokemonHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,28:1\n1549#2:29\n1620#2,3:30\n1855#2,2:33\n*S KotlinDebug\n*F\n+ 1 UnpastureAllPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pasture/UnpastureAllPokemonHandler\n*L\n26#1:29\n26#1:30,3\n26#1:33,2\n*E\n"])
public object UnpastureAllPokemonHandler : ServerNetworkPacketHandler<UnpastureAllPokemonPacket> {
   public open fun handle(packet: UnpastureAllPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: PastureLink = PastureLinkManager.INSTANCE.getLinkByPlayer(player);
      if (var10000 == null) {
         CobblemonNetwork.INSTANCE.sendPacket(player, new ClosePasturePacket());
      } else {
         val `$i$f$forEach`: BlockEntity = player.m_9236_().m_7702_(var10000.getPos());
         val var22: PokemonPastureBlockEntity = `$i$f$forEach` as? PokemonPastureBlockEntity;
         if ((`$i$f$forEach` as? PokemonPastureBlockEntity) != null) {
            val var10001: UUID = player.m_20148_();
            val `$this$forEach$iv`: java.lang.Iterable = var22.releaseAllPokemon(var10001);
            val `element$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$forEach$iv`, 10));

            for (Object item$iv$iv : $this$map$iv) {
               `element$iv`.add(new PokemonUnpasturedPacket(`item$iv$iv` as UUID));
            }

            for (Object element$ivx : $this$map$iv) {
               (`element$ivx` as PokemonUnpasturedPacket).sendToPlayer(player);
            }
         }
      }
   }

   fun handleOnNettyThread(packet: UnpastureAllPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
