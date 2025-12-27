package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.PokemonDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class SetPCPokemonPacket internal constructor(storeID: UUID, storePosition: PCPosition, pokemonDTO: PokemonDTO) : NetworkPacket<SetPCPokemonPacket> {
   public open val id: ResourceLocation
   public final val pokemonDTO: PokemonDTO
   public final val storeID: UUID
   public final val storePosition: PCPosition

   init {
      this.storeID = storeID;
      this.storePosition = storePosition;
      this.pokemonDTO = pokemonDTO;
      this.id = ID;
   }

   public constructor(storeID: UUID, storePosition: PCPosition, pokemon: Pokemon) : this(storeID, storePosition, new PokemonDTO(pokemon, true))
   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.storeID);
      PCPosition.Companion.writePCPosition(buffer, this.storePosition);
      this.pokemonDTO.encode(buffer);
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

   @SourceDebugExtension(["SMAP\nSetPCPokemonPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SetPCPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/storage/pc/SetPCPokemonPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,46:1\n1#2:47\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): SetPCPokemonPacket {
         val var10000: UUID = buffer.m_130259_();
         val var10001: PCPosition = PCPosition.Companion.readPCPosition(buffer);
         val var2: PokemonDTO = new PokemonDTO();
         var2.decode(buffer);
         return new SetPCPokemonPacket(var10000, var10001, var2);
      }
   }
}
