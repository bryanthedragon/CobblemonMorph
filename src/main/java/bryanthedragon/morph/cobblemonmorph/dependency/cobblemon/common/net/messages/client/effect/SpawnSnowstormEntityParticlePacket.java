package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class SpawnSnowstormEntityParticlePacket(effectId: ResourceLocation, entityId: Int, locator: String = "root") :
   NetworkPacket<SpawnSnowstormEntityParticlePacket> {
   public final val effectId: ResourceLocation
   public final val entityId: Int
   public open val id: ResourceLocation
   public final val locator: String

   init {
      this.effectId = effectId;
      this.entityId = entityId;
      this.locator = locator;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130085_(this.effectId);
      buffer.writeInt(this.entityId);
      buffer.m_130070_(this.locator);
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

      public fun decode(buffer: FriendlyByteBuf): SpawnSnowstormEntityParticlePacket {
         val var10002: ResourceLocation = buffer.m_130281_();
         val var10003: Int = buffer.readInt();
         val var10004: java.lang.String = buffer.m_130277_();
         return new SpawnSnowstormEntityParticlePacket(var10002, var10003, var10004);
      }
   }
}
