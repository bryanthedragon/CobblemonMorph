package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.MoveClientPokemonPacket
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class MoveClientPartyPokemonPacket(storeID: UUID, pokemonID: UUID, newPosition: PartyPosition) : MoveClientPokemonPacket(storeID, pokemonID, newPosition) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public open fun encodePosition(buffer: FriendlyByteBuf, position: PartyPosition) {
      PartyPosition.Companion.writePartyPosition(buffer, this.getNewPosition());
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): MoveClientPartyPokemonPacket {
         val var10002: UUID = buffer.m_130259_();
         val var10003: UUID = buffer.m_130259_();
         return new MoveClientPartyPokemonPacket(var10002, var10003, PartyPosition.Companion.readPartyPosition(buffer));
      }
   }
}
