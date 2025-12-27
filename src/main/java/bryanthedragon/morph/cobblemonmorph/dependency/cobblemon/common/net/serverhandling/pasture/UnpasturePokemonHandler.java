package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLink
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.ClosePasturePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.PokemonUnpasturedPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.UnpasturePokemonPacket
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.block.entity.BlockEntity

@SourceDebugExtension(["SMAP\nUnpasturePokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UnpasturePokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pasture/UnpasturePokemonHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,34:1\n1#2:35\n*E\n"])
public object UnpasturePokemonHandler : ServerNetworkPacketHandler<UnpasturePokemonPacket> {
   public open fun handle(packet: UnpasturePokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      var var10000: PastureLink = PastureLinkManager.INSTANCE.getLinkByPlayer(player);
      if (var10000 == null) {
         CobblemonNetwork.INSTANCE.sendPacket(player, new ClosePasturePacket());
      } else {
         val var7: BlockEntity = player.m_9236_().m_7702_(var10000.getPos());
         val var13: PokemonPastureBlockEntity = var7 as? PokemonPastureBlockEntity;
         if ((var7 as? PokemonPastureBlockEntity) != null) {
            val var9: java.util.Iterator = var13.getTetheredPokemon().iterator();

            while (true) {
               if (var9.hasNext()) {
                  val var10: Any = var9.next();
                  if (!((var10 as PokemonPastureBlockEntity.Tethering).getPokemonId() == packet.getPokemonId())) {
                     continue;
                  }

                  var10000 = (PastureLink)var10;
                  break;
               }

               var10000 = null;
               break;
            }

            val tethered: PokemonPastureBlockEntity.Tethering = var10000 as PokemonPastureBlockEntity.Tethering;
            if (var10000 as PokemonPastureBlockEntity.Tethering != null && (var10000 as PokemonPastureBlockEntity.Tethering).getPlayerId() == player.m_20148_()
               )
             {
               var13.releasePokemon(tethered.getPokemonId());
               CobblemonNetwork.INSTANCE.sendPacket(player, new PokemonUnpasturedPacket(packet.getPokemonId()));
            } else {
               CobblemonNetwork.INSTANCE.sendPacket(player, new ClosePasturePacket());
            }
         }
      }
   }

   fun handleOnNettyThread(packet: UnpasturePokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
