package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import java.util.ArrayList;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public abstract class DataRegistrySyncPacket<T, N extends NetworkPacket<N>> : NetworkPacket<N> {
   public final var buffer: FriendlyByteBuf?
   internal final val entries: ArrayList<Any>
   private final val registryEntries: Collection<Any>

   open fun DataRegistrySyncPacket(registryEntries: MutableCollection<T>) {
      this.registryEntries = registryEntries;
      this.entries = new ArrayList<>();
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_236828_(this.registryEntries, this::encodeEntry);
   }

   internal fun decodeBuffer(buffer: FriendlyByteBuf) {
      this.buffer = buffer;
      buffer.retain();
   }

   public abstract fun encodeEntry(buffer: FriendlyByteBuf, entry: Any) {
   }

   public abstract fun decodeEntry(buffer: FriendlyByteBuf): Any? {
   }

   public abstract fun synchronizeDecoded(entries: Collection<Any>) {
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
}
