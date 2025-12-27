package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class BenchMovePacket(isParty: Boolean, uuid: UUID, oldMove: MoveTemplate, newMove: MoveTemplate) : NetworkPacket<BenchMovePacket> {
   public open val id: ResourceLocation
   public final val isParty: Boolean
   public final val newMove: MoveTemplate
   public final val oldMove: MoveTemplate
   public final val uuid: UUID

   init {
      this.isParty = isParty;
      this.uuid = uuid;
      this.oldMove = oldMove;
      this.newMove = newMove;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeBoolean(this.isParty);
      buffer.m_130077_(this.uuid);
      buffer.m_130070_(this.oldMove.getName());
      buffer.m_130070_(this.newMove.getName());
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

      public fun decode(buffer: FriendlyByteBuf): BenchMovePacket {
         val isParty: Boolean = buffer.readBoolean();
         val uuid: UUID = buffer.m_130259_();
         var var10000: Moves = Moves.INSTANCE;
         var var10001: java.lang.String = buffer.m_130277_();
         val var6: MoveTemplate = var10000.getByName(var10001);
         var10000 = Moves.INSTANCE;
         var10001 = buffer.m_130277_();
         val var8: MoveTemplate = var10000.getByName(var10001);
         return new BenchMovePacket(isParty, uuid, var6, var8);
      }
   }
}
