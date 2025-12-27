package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

public class SpawnBucket {
   public final lateinit var name: String
   public final var weight: Float

   public constructor(name: String, weight: Float) : this() {
      this.setName(name);
      this.weight = weight;
   }

   public override fun hashCode(): Int {
      return this.getName().hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      return other is SpawnBucket && (other as SpawnBucket).getName() == this.getName();
   }
}
