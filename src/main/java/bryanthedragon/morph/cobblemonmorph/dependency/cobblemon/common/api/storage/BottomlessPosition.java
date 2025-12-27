package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage

public class BottomlessPosition(currentIndex: Int) : StorePosition {
   public final val currentIndex: Int

   init {
      this.currentIndex = currentIndex;
   }
}
