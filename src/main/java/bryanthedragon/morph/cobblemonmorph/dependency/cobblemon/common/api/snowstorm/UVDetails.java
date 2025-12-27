package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

public class UVDetails {
   public final var endU: Float
   public final var endV: Float
   public final var startU: Float
   public final var startV: Float

   public fun set(startU: Double, startV: Double, endU: Double, endV: Double): UVDetails {
      this.startU = (float)startU;
      this.startV = (float)startV;
      this.endU = (float)endU;
      this.endV = (float)endV;
      return this;
   }
}
