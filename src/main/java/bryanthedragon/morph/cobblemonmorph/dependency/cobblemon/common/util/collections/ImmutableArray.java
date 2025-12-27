package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.collections

public class ImmutableArray<T>(vararg values: Any) {
   private final val values: Array<out Any>

   init {
      this.values = (T[])values;
   }

   public operator fun get(index: Int): Any {
      return this.values[index];
   }
}
