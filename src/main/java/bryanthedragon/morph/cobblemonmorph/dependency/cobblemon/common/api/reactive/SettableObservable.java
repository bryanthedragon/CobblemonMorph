package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nSettableObservable.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SettableObservable.kt\ncom/cobblemon/mod/common/api/reactive/SettableObservable\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,63:1\n1855#2,2:64\n*S KotlinDebug\n*F\n+ 1 SettableObservable.kt\ncom/cobblemon/mod/common/api/reactive/SettableObservable\n*L\n55#1:64,2\n*E\n"])
public open class SettableObservable<T>(value: Any) : Observable<T> {
   public final val subscriptions: PrioritizedList<ObservableSubscription<Any>>
   private final var value: Any

   init {
      this.value = (T)value;
      this.subscriptions = new PrioritizedList<>();
   }

   public fun subscribeIncludingCurrent(priority: Priority = Priority.NORMAL, handler: (Any) -> Unit): ObservableSubscription<Any> {
      val subscription: ObservableSubscription = this.subscribe(priority, handler);
      subscription.handle(this.value);
      return subscription;
   }

   public override fun subscribe(priority: Priority, handler: (Any) -> Unit): ObservableSubscription<Any> {
      val subscription: ObservableSubscription = new ObservableSubscription<>(this, handler);
      this.subscriptions.add(priority, subscription);
      return subscription;
   }

   public override fun unsubscribe(subscription: ObservableSubscription<Any>) {
      this.subscriptions.remove(subscription);
   }

   public open fun set(newValue: Any) {
      if ((this.value == null || !this.value.equals(newValue)) && (this.value != null || newValue != null)) {
         this.emit((T)newValue);
      }
   }

   public open fun emit(newValue: Any) {
      this.value = (T)newValue;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as ObservableSubscription).handle(newValue);
      }
   }

   public open fun get(): Any {
      return this.value;
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
