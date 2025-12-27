package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render

import org.joml.Vector4f

public class ModelLayer {
   public final val emissive: Boolean
   public final val enabled: Boolean = true
   public final val name: String = ""
   public final val texture: ModelTextureSupplier?
   public final val tint: Vector4f = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F)
   public final val translucent: Boolean
}
