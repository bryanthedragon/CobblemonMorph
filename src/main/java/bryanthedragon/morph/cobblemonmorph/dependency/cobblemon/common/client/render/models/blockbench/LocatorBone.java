package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench

public class LocatorBone(offset: List<Float> = CollectionsKt.listOf(new java.lang.Float[]{0.0F, 0.0F, 0.0F}),
   rotation: List<Float> = CollectionsKt.listOf(new java.lang.Float[]{0.0F, 0.0F, 0.0F})
) {
   public final var offset: List<Float>
   public final var rotation: List<Float>

   init {
      this.offset = offset;
      this.rotation = rotation;
   }

   fun LocatorBone() {
      this(null, null, 3, null);
   }
}
