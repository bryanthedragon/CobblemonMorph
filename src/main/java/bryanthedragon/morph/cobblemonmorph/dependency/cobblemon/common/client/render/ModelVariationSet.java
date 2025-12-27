package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import net.minecraft.resources.ResourceLocation

public class ModelVariationSet(name: ResourceLocation = MiscUtilsKt.cobblemonResource("thing"),
   order: Int = 0,
   variations: MutableList<ModelAssetVariation> = (new ArrayList()) as java.util.List
) {
   public final val name: ResourceLocation
   public final val order: Int
   public final val variations: MutableList<ModelAssetVariation>

   init {
      this.name = name;
      this.order = order;
      this.variations = variations;
   }

   fun ModelVariationSet() {
      this(null, 0, null, 7, null);
   }
}
