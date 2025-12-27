package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data

public interface ShowdownIdentifiable {
   public abstract fun showdownId(): String {
   }

   public companion object {
      internal final val REGEX: Regex = new Regex("[^a-z0-9]+")
   }
}
