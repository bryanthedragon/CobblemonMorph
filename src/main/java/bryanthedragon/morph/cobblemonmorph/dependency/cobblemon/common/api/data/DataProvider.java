package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data

import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

public interface DataProvider {
   public abstract fun <T : DataRegistry> register(registry: Any): Any {
   }

   public abstract fun fromIdentifier(registryIdentifier: ResourceLocation): DataRegistry? {
   }

   public abstract fun sync(player: ServerPlayer) {
   }

   public abstract fun doAfterSync(player: ServerPlayer, action: () -> Unit) {
   }
}
