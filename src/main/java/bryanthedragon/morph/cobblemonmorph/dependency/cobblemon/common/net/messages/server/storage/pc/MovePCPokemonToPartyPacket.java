package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class MovePCPokemonToPartyPacket(pokemonID: UUID, pcPosition: PCPosition, partyPosition: PartyPosition?) : NetworkPacket<MovePCPokemonToPartyPacket> {
   public open val id: ResourceLocation
   public final val partyPosition: PartyPosition?
   public final val pcPosition: PCPosition
   public final val pokemonID: UUID

   init {
      this.pokemonID = pokemonID;
      this.pcPosition = pcPosition;
      this.partyPosition = partyPosition;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.pokemonID);
      PCPosition.Companion.writePCPosition(buffer, this.pcPosition);
      buffer.m_236821_(this.partyPosition, MovePCPokemonToPartyPacket::encode$lambda$0);
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

   @JvmStatic
   fun `encode$lambda$0`(pb: FriendlyByteBuf, value: PartyPosition) {
      val var10000: PartyPosition.Companion = PartyPosition.Companion;
      var10000.writePartyPosition(pb, value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): MovePCPokemonToPartyPacket {
         val var10002: UUID = buffer.m_130259_();
         return new MovePCPokemonToPartyPacket(
            var10002, PCPosition.Companion.readPCPosition(buffer), buffer.m_236868_(MovePCPokemonToPartyPacket.Companion::decode$lambda$0) as PartyPosition
         );
      }

      @JvmStatic
      fun `decode$lambda$0`(it: FriendlyByteBuf): PartyPosition {
         val var10000: PartyPosition.Companion = PartyPosition.Companion;
         return var10000.readPartyPosition(it);
      }
   }
}
