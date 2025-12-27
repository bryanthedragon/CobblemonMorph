package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLink
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.ClosePasturePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.PasturePokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.core.Direction
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.properties.Property

public object PasturePokemonHandler : ServerNetworkPacketHandler<PasturePokemonPacket> {
   public open fun handle(packet: PasturePokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: PastureLink = PastureLinkManager.INSTANCE.getLinkByPlayer(player);
      if (var10000 != null) {
         if (!(var10000.getLinkId() == packet.getPastureId())) {
            CobblemonNetwork.INSTANCE.sendPacket(player, new ClosePasturePacket());
         } else {
            val var12: Pokemon = Cobblemon.INSTANCE.getStorage().getPC(var10000.getPcId()).get(packet.getPokemonId());
            if (var12 != null) {
               val direction: BlockEntity = player.m_9236_().m_7702_(var10000.getPos());
               val var13: PokemonPastureBlockEntity = direction as? PokemonPastureBlockEntity;
               if ((direction as? PokemonPastureBlockEntity) != null) {
                  val var11: Direction = player.m_9236_().m_8055_(var10000.getPos()).m_61143_(HorizontalDirectionalBlock.f_54117_ as Property) as Direction;
                  if (var12.getTetheringId() == null) {
                     if (var13.canAddPokemon(player, var12, var10000.getPermissions().getMaxPokemon())) {
                        var13.tether(player, var12, var11);
                     }
                  }
               }
            }
         }
      }
   }

   fun handleOnNettyThread(packet: PasturePokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}
