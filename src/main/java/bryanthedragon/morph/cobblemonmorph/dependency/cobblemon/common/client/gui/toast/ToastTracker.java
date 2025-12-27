package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.toast

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast.ToastPacket
import java.util.HashMap
import java.util.UUID
import net.minecraft.client.Minecraft

public object ToastTracker {
   private final val toasts: HashMap<UUID, CobblemonToast> = new HashMap()

   public fun handle(packet: ToastPacket, client: Minecraft) {
      var needsQueue: Boolean = false;
      var toast: CobblemonToast = toasts.get(packet.getUuid());
      if (toast == null) {
         toast = new CobblemonToast(packet);
         toasts.put(packet.getUuid(), toast);
         needsQueue = true;
      }

      toast.updateFrom$common(packet);
      if (needsQueue) {
         client.m_91300_().m_94922_(toast);
      }
   }
}
