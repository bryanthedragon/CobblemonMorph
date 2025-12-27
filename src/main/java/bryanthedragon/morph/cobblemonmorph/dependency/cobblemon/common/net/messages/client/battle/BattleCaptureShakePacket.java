package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class BattleCaptureShakePacket(targetPNX: String, direction: Boolean) : NetworkPacket<BattleCaptureShakePacket> {
   public final val direction: Boolean
   public open val id: ResourceLocation
   public final val targetPNX: String

   init {
      this.targetPNX = targetPNX;
      this.direction = direction;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.targetPNX);
      buffer.writeBoolean(this.direction);
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

      public fun decode(buffer: FriendlyByteBuf): BattleCaptureShakePacket {
         val var10002: java.lang.String = buffer.m_130277_();
         return new BattleCaptureShakePacket(var10002, buffer.readBoolean());
      }
   }
}
