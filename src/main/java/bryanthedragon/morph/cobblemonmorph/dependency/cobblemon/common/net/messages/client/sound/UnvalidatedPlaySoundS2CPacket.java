package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.sound

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.Level

internal class UnvalidatedPlaySoundS2CPacket(sound: ResourceLocation, category: SoundSource, x: Double, y: Double, z: Double, volume: Float, pitch: Float) :
   NetworkPacket<UnvalidatedPlaySoundS2CPacket> {
   public final val category: SoundSource
   public open val id: ResourceLocation
   public final val pitch: Float
   public final val sound: ResourceLocation
   public final val volume: Float
   public final val x: Double
   public final val y: Double
   public final val z: Double

   init {
      this.sound = sound;
      this.category = category;
      this.x = x;
      this.y = y;
      this.z = z;
      this.volume = volume;
      this.pitch = pitch;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130085_(this.sound);
      buffer.m_130068_(this.category as java.lang.Enum);
      buffer.writeDouble(this.x);
      buffer.writeDouble(this.y);
      buffer.writeDouble(this.z);
      buffer.writeFloat(this.volume);
      buffer.writeFloat(this.pitch);
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

      public fun decode(buffer: FriendlyByteBuf): UnvalidatedPlaySoundS2CPacket {
         val var10002: ResourceLocation = buffer.m_130281_();
         val var10003: java.lang.Enum = buffer.m_130066_(SoundSource.class);
         return new UnvalidatedPlaySoundS2CPacket(
            var10002, var10003 as SoundSource, buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readFloat(), buffer.readFloat()
         );
      }
   }
}
