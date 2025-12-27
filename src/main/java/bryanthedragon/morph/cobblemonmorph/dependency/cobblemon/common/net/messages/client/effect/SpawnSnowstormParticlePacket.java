package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

public class SpawnSnowstormParticlePacket(effectId: ResourceLocation, position: Vec3) : NetworkPacket<SpawnSnowstormParticlePacket> {
   public final val effectId: ResourceLocation
   public open val id: ResourceLocation
   public final val position: Vec3

   init {
      this.effectId = effectId;
      this.position = position;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130085_(this.effectId);
      buffer.writeDouble(this.position.f_82479_);
      buffer.writeDouble(this.position.f_82480_);
      buffer.writeDouble(this.position.f_82481_);
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

      public fun decode(buffer: FriendlyByteBuf): SpawnSnowstormParticlePacket {
         val var10002: ResourceLocation = buffer.m_130281_();
         return new SpawnSnowstormParticlePacket(var10002, new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
      }
   }
}
