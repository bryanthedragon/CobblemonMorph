package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nTransformObservable.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TransformObservable.kt\ncom/cobblemon/mod/common/api/reactive/TransformObservable\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,52:1\n1#2:53\n*E\n"])
public class TransformObservable<I, O>(observable: Observable<Any>, transform: Transform<Any, Any>) : SimpleObservable<O> {
   private final val observable: Observable<Any>
   public final var rootSubscription: ObservableSubscription<Any>?
   private final val transform: Transform<Any, Any>

   init {
      this.observable = observable;
      this.transform = transform;
   }

   public override fun subscribe(priority: Priority, handler: (Any) -> Unit): ObservableSubscription<Any> {
      if (this.rootSubscription == null) {
         this.rootSubscription = this.observable.subscribe(priority, (new Function1<I, Unit>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            public final void invoke(I it) {
               this.this$0.parentHandler((I)it);
            }
         }) as (I?) -> Unit);
      }

      return super.subscribe(priority, handler);
   }

   public fun terminate() {
      if (this.rootSubscription != null) {
         this.observable.unsubscribe(this.rootSubscription);
      }
   }

   public fun parentHandler(input: Any) {
      try {
         this.emit(this.transform.invoke((I)input));
      } catch (var3: NoTransformThrowable) {
         if (var3.getTerminate()) {
            this.terminate();
         }
      }
   }
}
