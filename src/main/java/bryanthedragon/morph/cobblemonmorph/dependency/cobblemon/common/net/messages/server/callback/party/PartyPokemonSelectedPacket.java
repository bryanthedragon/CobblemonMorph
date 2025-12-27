package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class PartyPokemonSelectedPacket(uuid: UUID, index: Int) : NetworkPacket<PartyPokemonSelectedPacket> {
   public open val id: ResourceLocation
   public final val index: Int
   public final val uuid: UUID

   init {
      this.uuid = uuid;
      this.index = index;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.uuid);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.index);
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

      public fun decode(buffer: FriendlyByteBuf): PartyPokemonSelectedPacket {
         val var10002: UUID = buffer.m_130259_();
         return new PartyPokemonSelectedPacket(var10002, NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE));
      }
   }
}
