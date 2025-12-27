package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public abstract class MoveClientPokemonPacket<T extends StorePosition, N extends NetworkPacket<N>> : NetworkPacket<N> {
   public final val newPosition: Any
   public final val pokemonID: UUID
   public final val storeID: UUID

   open fun MoveClientPokemonPacket(storeID: UUID, pokemonID: UUID, newPosition: T) {
      this.storeID = storeID;
      this.pokemonID = pokemonID;
      this.newPosition = (T)newPosition;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.storeID);
      buffer.m_130077_(this.pokemonID);
      this.encodePosition(buffer, this.newPosition);
   }

   public abstract fun encodePosition(buffer: FriendlyByteBuf, position: Any) {
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
}
