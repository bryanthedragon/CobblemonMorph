package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive

public class ObservableSubscription<T>(observable: Observable<Any>, handler: (Any) -> Unit) {
   public final var alive: Boolean
   private final val handler: (Any) -> Unit
   private final val observable: Observable<Any>

   init {
      this.observable = observable;
      this.handler = handler;
      this.alive = true;
   }

   public fun handle(value: Any) {
      this.handler.invoke(value);
   }

   public fun unsubscribe() {
      this.observable.unsubscribe(this);
      this.alive = false;
   }
}
