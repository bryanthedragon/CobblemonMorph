package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.level.Level

@SourceDebugExtension(["SMAP\nBattleMusicPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleMusicPacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleMusicPacket\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,44:1\n1#2:45\n*E\n"])
public class BattleMusicPacket(music: SoundEvent? = null, volume: Float = 1.0F, pitch: Float = 1.0F) : NetworkPacket<BattleMusicPacket> {
   public open val id: ResourceLocation
   public final var music: SoundEvent?
   public final var pitch: Float
   public final var volume: Float

   init {
      this.music = music;
      this.volume = volume;
      this.pitch = pitch;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      if (this.music == null || buffer.m_130085_(this.music.m_11660_()) == null) {
         buffer.m_130085_(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default("", null, 1, null));
      }

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

   fun BattleMusicPacket() {
      this(null, 0.0F, 0.0F, 7, null);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): BattleMusicPacket {
         return new BattleMusicPacket(BuiltInRegistries.f_256894_.m_7745_(buffer.m_130281_()) as SoundEvent, buffer.readFloat(), buffer.readFloat());
      }
   }
}
