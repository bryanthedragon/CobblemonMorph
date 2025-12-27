package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop

import java.util.LinkedHashMap
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.phys.Vec3

public interface DropEntry {
   public val maxSelectableTimes: Int
   public val percentage: Float
   public val quantity: Int

   public abstract fun drop(entity: LivingEntity?, world: ServerLevel, pos: Vec3, player: ServerPlayer?) {
   }

   public companion object {
      public final var defaultType: Class<out DropEntry>?
      public final val entryTypes: MutableMap<String, Class<out DropEntry>> = (new LinkedHashMap()) as java.util.Map

      public fun getByName(name: String): Class<out DropEntry>? {
         return entryTypes.get(name);
      }

      public fun <T : DropEntry> register(name: String, clazz: Class<Any>, isDefault: Boolean = false) {
         entryTypes.put(name, clazz);
         if (isDefault) {
            defaultType = clazz;
         }
      }
   }
}
