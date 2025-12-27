package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket.PasturePokemonDataDTO
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class PokemonPasturedPacket(pasturePokemonDTO: PasturePokemonDataDTO) : NetworkPacket<PokemonPasturedPacket> {
   public open val id: ResourceLocation
   public final val pasturePokemonDTO: PasturePokemonDataDTO

   init {
      this.pasturePokemonDTO = pasturePokemonDTO;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      this.pasturePokemonDTO.encode(buffer);
   }

   override fun sendToPlayer(player: ServerPlayer) {
      NetworkPacket.DefaultImpls.sendToPlayer(this, player);
   }

   override fun sendToPlayers(players: MutableIterable<ServerPlayer>) {
      NetworkPacket.DefaultImpls.sendToPlayers(this, players);
   }

   override fun sendToAllPlayers() {
      NetworkPacket.DefaultImpls.sendToAllPlayers(this);
   }

   override fun sendToServer() {
      NetworkPacket.DefaultImpls.sendToServer(this);
   }

   override fun sendToPlayersAround(
      x: Double, y: Double, z: Double, distance: Double, worldKey: ResourceKey<Level>, exclusionCondition: (ServerPlayer?) -> java.lang.Boolean
   ) {
      NetworkPacket.DefaultImpls.sendToPlayersAround(this, x, y, z, distance, worldKey, exclusionCondition);
   }

   override fun toBuffer(): FriendlyByteBuf {
      return NetworkPacket.DefaultImpls.toBuffer(this);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): PokemonPasturedPacket {
         return new PokemonPasturedPacket(OpenPasturePacket.PasturePokemonDataDTO.Companion.decode(buffer));
      }
   }
}
