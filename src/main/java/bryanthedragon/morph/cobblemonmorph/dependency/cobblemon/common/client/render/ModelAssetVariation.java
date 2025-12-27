package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render

import java.util.LinkedHashSet
import net.minecraft.resources.ResourceLocation

public class ModelAssetVariation(aspects: MutableSet<String> = (new LinkedHashSet()) as java.util.Set,
   poser: ResourceLocation? = null,
   model: ResourceLocation? = null,
   texture: ModelTextureSupplier? = null,
   layers: List<ModelLayer>? = null
) {
   public final val aspects: MutableSet<String>
   public final val layers: List<ModelLayer>?
   public final val model: ResourceLocation?
   public final val poser: ResourceLocation?
   public final val texture: ModelTextureSupplier?

   init {
      this.aspects = aspects;
      this.poser = poser;
      this.model = model;
      this.texture = texture;
      this.layers = layers;
   }

   fun ModelAssetVariation() {
      this(null, null, null, null, null, 31, null);
   }
}
