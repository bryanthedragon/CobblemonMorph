package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class BattleCaptureStartPacket(pokeBallType: ResourceLocation, aspects: Set<String>, targetPNX: String) : NetworkPacket<BattleCaptureStartPacket> {
   public final val aspects: Set<String>
   public open val id: ResourceLocation
   public final val pokeBallType: ResourceLocation
   public final val targetPNX: String

   init {
      this.pokeBallType = pokeBallType;
      this.aspects = aspects;
      this.targetPNX = targetPNX;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130085_(this.pokeBallType);
      buffer.m_236828_(this.aspects, BattleCaptureStartPacket::encode$lambda$0);
      buffer.m_130070_(this.targetPNX);
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
   fun `encode$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, aspect: java.lang.String) {
      `$buffer`.m_130070_(aspect);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): BattleCaptureStartPacket {
         val var10002: ResourceLocation = buffer.m_130281_();
         val var10003: java.util.List = buffer.m_236845_(BattleCaptureStartPacket.Companion::decode$lambda$0);
         val var2: java.util.Set = CollectionsKt.toSet(var10003);
         val var10004: java.lang.String = buffer.m_130277_();
         return new BattleCaptureStartPacket(var10002, var2, var10004);
      }

      @JvmStatic
      fun `decode$lambda$0`(it: FriendlyByteBuf): java.lang.String {
         return it.m_130277_();
      }
   }
}
