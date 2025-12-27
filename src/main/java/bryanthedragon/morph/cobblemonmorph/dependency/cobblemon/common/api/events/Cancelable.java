package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events

public abstract class Cancelable {
   public final var isCanceled: Boolean
      private set

   public fun cancel() {
      this.isCanceled = true;
   }
}
