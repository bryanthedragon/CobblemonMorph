package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class MovePCPokemonPacket(pokemonID: UUID, oldPosition: PCPosition, newPosition: PCPosition) : NetworkPacket<MovePCPokemonPacket> {
   public open val id: ResourceLocation
   public final val newPosition: PCPosition
   public final val oldPosition: PCPosition
   public final val pokemonID: UUID

   init {
      this.pokemonID = pokemonID;
      this.oldPosition = oldPosition;
      this.newPosition = newPosition;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.pokemonID);
      PCPosition.Companion.writePCPosition(buffer, this.oldPosition);
      PCPosition.Companion.writePCPosition(buffer, this.newPosition);
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

      public fun decode(buffer: FriendlyByteBuf): MovePCPokemonPacket {
         val var10002: UUID = buffer.m_130259_();
         return new MovePCPokemonPacket(var10002, PCPosition.Companion.readPCPosition(buffer), PCPosition.Companion.readPCPosition(buffer));
      }
   }
}
