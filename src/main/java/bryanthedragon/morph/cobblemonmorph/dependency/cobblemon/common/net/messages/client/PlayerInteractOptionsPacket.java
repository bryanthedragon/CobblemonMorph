package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import java.util.EnumSet
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class PlayerInteractOptionsPacket(options: EnumSet<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PlayerInteractOptionsPacket.Options>,
      targetId: UUID,
      numericTargetId: Int,
      selectedPokemonId: UUID
   ) :
   NetworkPacket<PlayerInteractOptionsPacket> {
   public open val id: ResourceLocation
   public final val numericTargetId: Int
   public final val options: EnumSet<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PlayerInteractOptionsPacket.Options>
   public final val selectedPokemonId: UUID
   public final val targetId: UUID

   init {
      this.options = options;
      this.targetId = targetId;
      this.numericTargetId = numericTargetId;
      this.selectedPokemonId = selectedPokemonId;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_245616_(this.options, PlayerInteractOptionsPacket.Options::class.java);
      buffer.m_130077_(this.targetId);
      buffer.writeInt(this.numericTargetId);
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

      public fun decode(buffer: FriendlyByteBuf): PlayerInteractOptionsPacket {
         val var10002: EnumSet = buffer.m_247336_(PlayerInteractOptionsPacket.Options.class);
         val var10003: UUID = buffer.m_130259_();
         val var10004: Int = buffer.readInt();
         val var10005: UUID = buffer.m_130259_();
         return new PlayerInteractOptionsPacket(var10002, var10003, var10004, var10005);
      }
   }

   public enum Options {
      BATTLE,
      SPECTATE_BATTLE,
      TRADE   }
}
