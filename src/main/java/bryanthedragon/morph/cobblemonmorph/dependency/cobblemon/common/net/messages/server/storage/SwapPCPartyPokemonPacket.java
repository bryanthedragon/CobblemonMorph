package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class SwapPCPartyPokemonPacket(partyPokemonID: UUID, partyPosition: PartyPosition, pcPokemonID: UUID, pcPosition: PCPosition) :
   NetworkPacket<SwapPCPartyPokemonPacket> {
   public open val id: ResourceLocation
   public final val partyPokemonID: UUID
   public final val partyPosition: PartyPosition
   public final val pcPokemonID: UUID
   public final val pcPosition: PCPosition

   init {
      this.partyPokemonID = partyPokemonID;
      this.partyPosition = partyPosition;
      this.pcPokemonID = pcPokemonID;
      this.pcPosition = pcPosition;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.partyPokemonID);
      PartyPosition.Companion.writePartyPosition(buffer, this.partyPosition);
      buffer.m_130077_(this.pcPokemonID);
      PCPosition.Companion.writePCPosition(buffer, this.pcPosition);
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

      public fun decode(buffer: FriendlyByteBuf): SwapPCPartyPokemonPacket {
         val var10002: UUID = buffer.m_130259_();
         val var10003: PartyPosition = PartyPosition.Companion.readPartyPosition(buffer);
         val var10004: UUID = buffer.m_130259_();
         return new SwapPCPartyPokemonPacket(var10002, var10003, var10004, PCPosition.Companion.readPCPosition(buffer));
      }
   }
}
