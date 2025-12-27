package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class SwapPartyPokemonPacket(pokemon1ID: UUID, position1: PartyPosition, pokemon2ID: UUID, position2: PartyPosition) :
   NetworkPacket<SwapPartyPokemonPacket> {
   public open val id: ResourceLocation
   public final val pokemon1ID: UUID
   public final val pokemon2ID: UUID
   public final val position1: PartyPosition
   public final val position2: PartyPosition

   init {
      this.pokemon1ID = pokemon1ID;
      this.position1 = position1;
      this.pokemon2ID = pokemon2ID;
      this.position2 = position2;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.pokemon1ID);
      PartyPosition.Companion.writePartyPosition(buffer, this.position1);
      buffer.m_130077_(this.pokemon2ID);
      PartyPosition.Companion.writePartyPosition(buffer, this.position2);
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

      public fun decode(buffer: FriendlyByteBuf): SwapPartyPokemonPacket {
         val var10002: UUID = buffer.m_130259_();
         val var10003: PartyPosition = PartyPosition.Companion.readPartyPosition(buffer);
         val var10004: UUID = buffer.m_130259_();
         return new SwapPartyPokemonPacket(var10002, var10003, var10004, PartyPosition.Companion.readPartyPosition(buffer));
      }
   }
}
