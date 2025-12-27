package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import java.util.ArrayList;
import java.util.Arrays
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nSingularObservable.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SingularObservable.kt\ncom/cobblemon/mod/common/api/reactive/SingularObservable\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,46:1\n1855#2,2:47\n*S KotlinDebug\n*F\n+ 1 SingularObservable.kt\ncom/cobblemon/mod/common/api/reactive/SingularObservable\n*L\n30#1:47,2\n*E\n"])
public open class SingularObservable<T> : SimpleObservable<T> {
   private final var completed: Boolean
   private final var completedValue: MutableList<Any> = (new ArrayList()) as java.util.List

   public override fun subscribe(priority: Priority, handler: (Any) -> Unit): ObservableSubscription<Any> {
      val subscription: ObservableSubscription = new ObservableSubscription<>(this, handler);
      if (this.completed) {
         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            handler.invoke(`element$iv`);
         }
      } else {
         this.getSubscriptions().add(priority, subscription);
      }

      return subscription;
   }

   public override fun emit(vararg values: Any) {
      if (this.completed) {
         throw new IllegalStateException("This observable is already completed!");
      } else {
         this.completed = true;
         CollectionsKt.addAll(this.completedValue, values);
         super.emit((T[])Arrays.copyOf(values, values.length));
         this.getSubscriptions().clear();
      }
   }
}
