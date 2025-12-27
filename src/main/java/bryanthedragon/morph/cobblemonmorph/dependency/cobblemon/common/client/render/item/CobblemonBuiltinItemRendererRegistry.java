package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item

import java.util.HashMap
import net.minecraft.world.item.Item

public object CobblemonBuiltinItemRendererRegistry {
   private final val renderers: HashMap<Item, CobblemonBuiltinItemRenderer> = new HashMap()

   public fun register(item: Item, renderer: CobblemonBuiltinItemRenderer) {
      renderers.put(item, renderer);
   }

   public fun rendererOf(item: Item): CobblemonBuiltinItemRenderer? {
      return renderers.get(item);
   }
}
