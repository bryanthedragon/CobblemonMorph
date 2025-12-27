package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class BattleHealthChangePacket(pnx: String, newHealth: Float, newMaxHealth: Float? = null) : NetworkPacket<BattleHealthChangePacket> {
   public open val id: ResourceLocation
   public final val newHealth: Float
   public final val newMaxHealth: Float?
   public final val pnx: String

   init {
      this.pnx = pnx;
      this.newHealth = newHealth;
      this.newMaxHealth = newMaxHealth;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.pnx);
      buffer.writeFloat(this.newHealth);
      buffer.m_236821_(this.newMaxHealth, BattleHealthChangePacket::encode$lambda$0);
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
   fun `encode$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, newMaxHealth: java.lang.Float) {
      `$buffer`.writeFloat(newMaxHealth);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): BattleHealthChangePacket {
         val var10002: java.lang.String = buffer.m_130277_();
         return new BattleHealthChangePacket(
            var10002, buffer.readFloat(), buffer.m_236868_(BattleHealthChangePacket.Companion::decode$lambda$0) as java.lang.Float
         );
      }

      @JvmStatic
      fun `decode$lambda$0`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.Float {
         return `$buffer`.readFloat();
      }
   }
}
