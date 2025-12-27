package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class RunPosableMoLangPacket(entityId: Int, expressions: Set<String>) : NetworkPacket<RunPosableMoLangPacket> {
   public final val entityId: Int
   public final val expressions: Set<String>
   public open val id: ResourceLocation

   init {
      this.entityId = entityId;
      this.expressions = expressions;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeInt(this.entityId);
      buffer.m_236828_(this.expressions, RunPosableMoLangPacket::encode$lambda$0);
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

   @JvmStatic
   fun `encode$lambda$0`(pb: FriendlyByteBuf, value: java.lang.String) {
      pb.m_130070_(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): RunPosableMoLangPacket {
         val var10002: Int = buffer.readInt();
         val var10003: java.util.List = buffer.m_236845_(RunPosableMoLangPacket.Companion::decode$lambda$0);
         return new RunPosableMoLangPacket(var10002, CollectionsKt.toSet(var10003));
      }

      @JvmStatic
      fun `decode$lambda$0`(pb: FriendlyByteBuf): java.lang.String {
         return pb.m_130277_();
      }
   }
}
