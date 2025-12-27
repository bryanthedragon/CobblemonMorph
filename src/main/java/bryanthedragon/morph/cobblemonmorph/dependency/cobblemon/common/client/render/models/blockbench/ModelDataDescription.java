package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench

public class ModelDataDescription(identifier: String,
   textureWidth: Int,
   textureHeight: Int,
   visibleBoundsWidth: Float,
   visibleBoundsHeight: Float,
   visibleBoundsOffset: List<Float>
) {
   public final val identifier: String
   public final val textureHeight: Int
   public final val textureWidth: Int
   public final val visibleBoundsHeight: Float
   public final val visibleBoundsOffset: List<Float>
   public final val visibleBoundsWidth: Float

   init {
      this.identifier = identifier;
      this.textureWidth = textureWidth;
      this.textureHeight = textureHeight;
      this.visibleBoundsWidth = visibleBoundsWidth;
      this.visibleBoundsHeight = visibleBoundsHeight;
      this.visibleBoundsOffset = visibleBoundsOffset;
   }
}
