package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class InitializePCPacket internal constructor(storeID: UUID, boxCount: Int, hasOverflowed: Boolean) : NetworkPacket<InitializePCPacket> {
   public final val boxCount: Int
   public final val hasOverflowed: Boolean
   public open val id: ResourceLocation
   public final val storeID: UUID

   init {
      this.storeID = storeID;
      this.boxCount = boxCount;
      this.hasOverflowed = hasOverflowed;
      this.id = ID;
   }

   public constructor(pc: PCStore) : this(pc.getUuid(), pc.getBoxes().size(), CollectionsKt.any(pc.getBackupStore()))
   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.storeID);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_SHORT, this.boxCount);
      buffer.writeBoolean(this.hasOverflowed);
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

      public fun decode(buffer: FriendlyByteBuf): InitializePCPacket {
         val var10002: UUID = buffer.m_130259_();
         return new InitializePCPacket(var10002, NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_SHORT), buffer.readBoolean());
      }
   }
}
