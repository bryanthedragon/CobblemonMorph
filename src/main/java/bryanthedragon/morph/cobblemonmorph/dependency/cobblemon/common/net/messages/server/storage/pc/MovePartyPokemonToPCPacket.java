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

public class MovePartyPokemonToPCPacket(pokemonID: UUID, partyPosition: PartyPosition, pcPosition: PCPosition?) : NetworkPacket<MovePartyPokemonToPCPacket> {
   public open val id: ResourceLocation
   public final val partyPosition: PartyPosition
   public final val pcPosition: PCPosition?
   public final val pokemonID: UUID

   init {
      this.pokemonID = pokemonID;
      this.partyPosition = partyPosition;
      this.pcPosition = pcPosition;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.pokemonID);
      PartyPosition.Companion.writePartyPosition(buffer, this.partyPosition);
      buffer.m_236821_(this.pcPosition, MovePartyPokemonToPCPacket::encode$lambda$0);
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
   fun `encode$lambda$0`(pb: FriendlyByteBuf, value: PCPosition) {
      val var10000: PCPosition.Companion = PCPosition.Companion;
      var10000.writePCPosition(pb, value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): MovePartyPokemonToPCPacket {
         val var10002: UUID = buffer.m_130259_();
         return new MovePartyPokemonToPCPacket(
            var10002, PartyPosition.Companion.readPartyPosition(buffer), buffer.m_236868_(MovePartyPokemonToPCPacket.Companion::decode$lambda$0) as PCPosition
         );
      }

      @JvmStatic
      fun `decode$lambda$0`(it: FriendlyByteBuf): PCPosition {
         val var10000: PCPosition.Companion = PCPosition.Companion;
         return var10000.readPCPosition(it);
      }
   }
}
