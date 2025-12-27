package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class SwapClientPokemonPacket internal constructor(storeIsParty: Boolean, storeID: UUID, pokemonID1: UUID, pokemonID2: UUID) :
   NetworkPacket<SwapClientPokemonPacket> {
   public open val id: ResourceLocation
   public final val pokemonID1: UUID
   public final val pokemonID2: UUID
   public final val storeID: UUID
   public final val storeIsParty: Boolean

   init {
      this.storeIsParty = storeIsParty;
      this.storeID = storeID;
      this.pokemonID1 = pokemonID1;
      this.pokemonID2 = pokemonID2;
      this.id = ID;
   }

   public constructor(store: PokemonStore<*>, pokemonID1: UUID, pokemonID2: UUID) : this(store is PartyStore, store.getUuid(), pokemonID1, pokemonID2)
   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeBoolean(this.storeIsParty);
      buffer.m_130077_(this.storeID);
      buffer.m_130077_(this.pokemonID1);
      buffer.m_130077_(this.pokemonID2);
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

      public fun decode(buffer: FriendlyByteBuf): SwapClientPokemonPacket {
         val var10002: Boolean = buffer.readBoolean();
         val var10003: UUID = buffer.m_130259_();
         val var10004: UUID = buffer.m_130259_();
         val var10005: UUID = buffer.m_130259_();
         return new SwapClientPokemonPacket(var10002, var10003, var10004, var10005);
      }
   }
}
