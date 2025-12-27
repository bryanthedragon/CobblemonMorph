package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net

import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

public interface ServerNetworkPacketHandler<T extends NetworkPacket<T>> {
   public abstract fun handle(packet: Any, server: MinecraftServer, player: ServerPlayer) {
   }

   public open fun handleOnNettyThread(packet: Any, server: MinecraftServer, player: ServerPlayer) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T extends NetworkPacket<T>> handleOnNettyThread(`$this`: ServerNetworkPacketHandler<T>, packet: T, server: MinecraftServer, player: ServerPlayer) {
         server.execute(ServerNetworkPacketHandler.DefaultImpls::handleOnNettyThread$lambda$0);
      }

      @JvmStatic
      fun `handleOnNettyThread$lambda$0`(`this$0`: ServerNetworkPacketHandler, `$packet`: NetworkPacket, `$server`: MinecraftServer, `$player`: ServerPlayer) {
         `this$0`.handle((T)`$packet`, `$server`, `$player`);
      }
   }
}
