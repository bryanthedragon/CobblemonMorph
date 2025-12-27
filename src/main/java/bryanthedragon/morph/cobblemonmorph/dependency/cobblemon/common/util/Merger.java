package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

public interface Merger {
   public abstract fun <T> merge(base: MutableCollection<Any>?, other: MutableCollection<Any>?): MutableCollection<Any>? {
   }

   public abstract fun <T> mergeSingle(base: Any?, other: Any?): Any? {
   }
}
