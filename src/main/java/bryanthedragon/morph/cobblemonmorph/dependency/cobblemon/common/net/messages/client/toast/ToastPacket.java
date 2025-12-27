package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

public class ToastPacket(title: Component,
      description: Component,
      icon: ItemStack,
      frameTexture: ResourceLocation,
      progress: Float,
      progressColor: Int,
      uuid: UUID,
      behaviour: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast.ToastPacket.Behaviour
   ) :
   NetworkPacket<ToastPacket> {
   public final val behaviour: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast.ToastPacket.Behaviour
   public final val description: Component
   public final val frameTexture: ResourceLocation
   public final val icon: ItemStack
   public open val id: ResourceLocation
   public final val progress: Float
   public final val progressColor: Int
   public final val title: Component
   public final val uuid: UUID

   init {
      this.title = title;
      this.description = description;
      this.icon = icon;
      this.frameTexture = frameTexture;
      this.progress = progress;
      this.progressColor = progressColor;
      this.uuid = uuid;
      this.behaviour = behaviour;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130083_(this.title);
      buffer.m_130083_(this.description);
      buffer.m_130055_(this.icon);
      buffer.m_130085_(this.frameTexture);
      buffer.writeFloat(this.progress);
      buffer.writeInt(this.progressColor);
      buffer.m_130077_(this.uuid);
      buffer.m_130068_(this.behaviour);
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

   public enum Behaviour {
      SHOW_OR_UPDATE,
      HIDE   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): ToastPacket {
         val var10002: Component = buffer.m_130238_();
         val var10003: Component = buffer.m_130238_();
         val var10004: ItemStack = buffer.m_130267_();
         val var10005: ResourceLocation = buffer.m_130281_();
         val var10006: Float = buffer.readFloat();
         val var10007: Int = buffer.readInt();
         val var10008: UUID = buffer.m_130259_();
         val var10009: java.lang.Enum = buffer.m_130066_(ToastPacket.Behaviour.class);
         return new ToastPacket(var10002, var10003, var10004, var10005, var10006, var10007, var10008, var10009 as ToastPacket.Behaviour);
      }
   }
}
