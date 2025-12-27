package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class RequestMoveSwapPacket(move1: Int, move2: Int, slot: Int) : NetworkPacket<RequestMoveSwapPacket> {
   public open val id: ResourceLocation
   public final val move1: Int
   public final val move2: Int
   public final val slot: Int

   init {
      this.move1 = move1;
      this.move2 = move2;
      this.slot = slot;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeInt(this.move1);
      buffer.writeInt(this.move2);
      buffer.writeInt(this.slot);
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

      public fun decode(buffer: FriendlyByteBuf): RequestMoveSwapPacket {
         return new RequestMoveSwapPacket(buffer.readInt(), buffer.readInt(), buffer.readInt());
      }
   }
}
