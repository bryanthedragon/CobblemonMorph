package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.MoveClientPokemonPacket
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class MoveClientPCPokemonPacket(storeID: UUID, pokemonID: UUID, newPosition: PCPosition) : MoveClientPokemonPacket(storeID, pokemonID, newPosition) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public open fun encodePosition(buffer: FriendlyByteBuf, position: PCPosition) {
      PCPosition.Companion.writePCPosition(buffer, position);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): MoveClientPCPokemonPacket {
         val var10002: UUID = buffer.m_130259_();
         val var10003: UUID = buffer.m_130259_();
         return new MoveClientPCPokemonPacket(var10002, var10003, PCPosition.Companion.readPCPosition(buffer));
      }
   }
}
