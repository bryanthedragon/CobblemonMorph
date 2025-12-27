package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nSimpleObservable.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SimpleObservable.kt\ncom/cobblemon/mod/common/api/reactive/SimpleObservable\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,42:1\n13579#2:43\n13580#2:46\n1855#3,2:44\n*S KotlinDebug\n*F\n+ 1 SimpleObservable.kt\ncom/cobblemon/mod/common/api/reactive/SimpleObservable\n*L\n36#1:43\n36#1:46\n39#1:44,2\n*E\n"])
public open class SimpleObservable<T> : Observable<T> {
   protected final val subscriptions: PrioritizedList<ObservableSubscription<Any>> = new PrioritizedList()

   public override fun subscribe(priority: Priority, handler: (Any) -> Unit): ObservableSubscription<Any> {
      val subscription: ObservableSubscription = new ObservableSubscription<>(this, handler);
      this.subscriptions.add(priority, subscription);
      return subscription;
   }

   public override fun unsubscribe(subscription: ObservableSubscription<Any>) {
      this.subscriptions.remove(subscription);
   }

   public open fun emit(vararg values: Any) {
      if (!this.subscriptions.isEmpty()) {
         for (Object element$iv : values) {
            val value: Any = `element$iv`;

            val `$this$forEach$iv`: java.lang.Iterable;
            for (Object element$ivx : $this$forEach$iv) {
               (`element$ivx` as ObservableSubscription).handle(value);
            }
         }
      }
   }

   override fun <O> pipe(transform: Transform<T, O>): Observable<O> {
      return Observable.DefaultImpls.pipe(this, transform);
   }

   override fun <O1, O2> pipe(t1: Transform<T, O1>, t2: Transform<O1, O2>): TransformObservable<T, O2> {
      return Observable.DefaultImpls.pipe(this, t1, t2);
   }

   override fun <O1, O2, O3> pipe(t1: Transform<T, O1>, t2: Transform<O1, O2>, t3: Transform<O2, O3>): TransformObservable<T, O3> {
      return Observable.DefaultImpls.pipe(this, t1, t2, t3);
   }

   override fun <O1, O2, O3, O4> pipe(t1: Transform<T, O1>, t2: Transform<O1, O2>, t3: Transform<O2, O3>, t4: Transform<O3, O4>): TransformObservable<T, O4> {
      return Observable.DefaultImpls.pipe(this, t1, t2, t3, t4);
   }

   override fun <O1, O2, O3, O4, O5> pipe(t1: Transform<T, O1>, t2: Transform<O1, O2>, t3: Transform<O2, O3>, t4: Transform<O3, O4>, t5: Transform<O4, O5>): TransformObservable<T, O5> {
      return Observable.DefaultImpls.pipe(this, t1, t2, t3, t4, t5);
   }

   override fun <O1, O2, O3, O4, O5, O6> pipe(
      t1: Transform<T, O1>, t2: Transform<O1, O2>, t3: Transform<O2, O3>, t4: Transform<O3, O4>, t5: Transform<O4, O5>, t6: Transform<O5, O6>
   ): TransformObservable<T, O6> {
      return Observable.DefaultImpls.pipe(this, t1, t2, t3, t4, t5, t6);
   }

   override fun await(): T {
      return Observable.DefaultImpls.await(this);
   }
}
