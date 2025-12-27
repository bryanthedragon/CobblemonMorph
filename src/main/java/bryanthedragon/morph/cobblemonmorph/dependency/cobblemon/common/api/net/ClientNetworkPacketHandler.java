package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net

import net.minecraft.client.Minecraft

public interface ClientNetworkPacketHandler<T extends NetworkPacket<T>> {
   public abstract fun handle(packet: Any, client: Minecraft) {
   }

   public open fun handleOnNettyThread(packet: Any) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T extends NetworkPacket<T>> handleOnNettyThread(`$this`: ClientNetworkPacketHandler<T>, packet: T) {
         val client: Minecraft = Minecraft.m_91087_();
         client.execute(ClientNetworkPacketHandler.DefaultImpls::handleOnNettyThread$lambda$0);
      }

      @JvmStatic
      fun `handleOnNettyThread$lambda$0`(`this$0`: ClientNetworkPacketHandler, `$packet`: NetworkPacket, `$client`: Minecraft) {
         `this$0`.handle((T)`$packet`, `$client`);
      }
   }
}
