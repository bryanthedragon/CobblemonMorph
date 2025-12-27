package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public class BattleMessagePacket(messages: List<Component>) : NetworkPacket<BattleMessagePacket> {
   public open val id: ResourceLocation
   public final val messages: List<Component>

   init {
      this.messages = messages;
      this.id = ID;
   }

   public constructor(vararg messages: Component) : this(ArraysKt.toList(messages))
   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_236828_(this.messages, BattleMessagePacket::encode$lambda$0);
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
   fun `encode$lambda$0`(pb: FriendlyByteBuf, value: Component) {
      pb.m_130083_(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): BattleMessagePacket {
         val var10002: java.util.List = buffer.m_236845_(BattleMessagePacket.Companion::decode$lambda$0);
         return new BattleMessagePacket(var10002);
      }

      @JvmStatic
      fun `decode$lambda$0`(it: FriendlyByteBuf): Component {
         return it.m_130238_();
      }
   }
}
