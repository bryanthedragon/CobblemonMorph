package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class AcceptEvolutionPacket(pokemonUUID: UUID, evolutionId: String) : NetworkPacket<AcceptEvolutionPacket> {
   public final val evolutionId: String
   public open val id: ResourceLocation
   public final val pokemonUUID: UUID

   init {
      this.pokemonUUID = pokemonUUID;
      this.evolutionId = evolutionId;
      this.id = ID;
   }

   public constructor(pokemon: Pokemon, evolution: EvolutionDisplay)  {
      val var10001: UUID = pokemon.getUuid();
      this(var10001, evolution.getId());
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.pokemonUUID);
      buffer.m_130070_(this.evolutionId);
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

      public fun decode(buffer: FriendlyByteBuf): AcceptEvolutionPacket {
         val var10002: UUID = buffer.m_130259_();
         val var10003: java.lang.String = buffer.m_130277_();
         return new AcceptEvolutionPacket(var10002, var10003);
      }
   }
}
