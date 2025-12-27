package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class BattleChallengePacket(targetedEntityId: Int, selectedPokemonId: UUID) : NetworkPacket<BattleChallengePacket> {
   public open val id: ResourceLocation
   public final val selectedPokemonId: UUID
   public final val targetedEntityId: Int

   init {
      this.targetedEntityId = targetedEntityId;
      this.selectedPokemonId = selectedPokemonId;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeInt(this.targetedEntityId);
      buffer.m_130077_(this.selectedPokemonId);
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

      public fun decode(buffer: FriendlyByteBuf): BattleChallengePacket {
         val var10002: Int = buffer.readInt();
         val var10003: UUID = buffer.m_130259_();
         return new BattleChallengePacket(var10002, var10003);
      }
   }
}
