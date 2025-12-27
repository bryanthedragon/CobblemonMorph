package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItemConvertible
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.HashMap
import kotlin.jvm.functions.Function2
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation

public abstract class PlatformRegistry<R extends Registry<T>, K extends ResourceKey<R>, T> {
   protected final val queue: HashMap<ResourceLocation, Any> = new HashMap()
   public abstract val registry: Any
   public abstract val registryKey: Any

   public open fun <E : Any> create(name: String, entry: Any): Any {
      this.queue.put(MiscUtilsKt.cobblemonResource(name), (T)entry);
      if (entry is BagItemConvertible) {
         BagItems.INSTANCE.getBagItems().add(Priority.NORMAL, (BagItemConvertible)entry);
      }

      return (E)entry;
   }

   public open fun register(consumer: (ResourceLocation, Any) -> Unit) {
      this.queue.forEach(PlatformRegistry::register$lambda$0);
   }

   public open fun all(): Collection<Any> {
      val var10000: java.util.Collection = this.queue.values();
      return CollectionsKt.toList(var10000);
   }

   @JvmStatic
   fun `register$lambda$0`(`$tmp0`: Function2, p0: Any, p1: Any) {
      `$tmp0`.invoke(p0, p1);
   }
}
