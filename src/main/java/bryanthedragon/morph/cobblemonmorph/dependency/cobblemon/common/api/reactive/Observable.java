package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes.EmitWhileTransform
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes.FilterTransform
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes.IgnoreFirstTransform
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes.MapTransform
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes.StopAfterTransform
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes.TakeFirstTransform
import java.util.Arrays
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.Ref.ObjectRef
import org.jetbrains.annotations.NotNull

public interface Observable<T> {
   public abstract fun subscribe(priority: Priority = ..., handler: (Any) -> Unit): ObservableSubscription<Any> {
   }

   public abstract fun unsubscribe(subscription: ObservableSubscription<Any>) {
   }

   public open fun <O> pipe(transform: Transform<Any, Any>): Observable<Any> {
   }

   public open fun <O1, O2> pipe(t1: Transform<Any, Any>, t2: Transform<Any, Any>): TransformObservable<Any, Any> {
   }

   public open fun <O1, O2, O3> pipe(t1: Transform<Any, Any>, t2: Transform<Any, Any>, t3: Transform<Any, Any>): TransformObservable<Any, Any> {
   }

   public open fun <O1, O2, O3, O4> pipe(t1: Transform<Any, Any>, t2: Transform<Any, Any>, t3: Transform<Any, Any>, t4: Transform<Any, Any>): TransformObservable<
         Any,
         Any
      > {
   }

   public open fun <O1, O2, O3, O4, O5> pipe(
      t1: Transform<Any, Any>,
      t2: Transform<Any, Any>,
      t3: Transform<Any, Any>,
      t4: Transform<Any, Any>,
      t5: Transform<Any, Any>
   ): TransformObservable<Any, Any> {
   }

   public open fun <O1, O2, O3, O4, O5, O6> pipe(
      t1: Transform<Any, Any>,
      t2: Transform<Any, Any>,
      t3: Transform<Any, Any>,
      t4: Transform<Any, Any>,
      t5: Transform<Any, Any>,
      t6: Transform<Any, Any>
   ): TransformObservable<Any, Any> {
   }

   public open fun await(): Any {
   }

   @SourceDebugExtension(["SMAP\nObservable.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Observable.kt\ncom/cobblemon/mod/common/api/reactive/Observable$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,86:1\n1#2:87\n*E\n"])
   public companion object {
      public fun <T> just(vararg values: Any): Observable<Any> {
         val var2: SingularObservable = new SingularObservable();
         var2.emit(Arrays.copyOf(values, values.length));
         return var2;
      }

      public fun <T> of(future: CompletableFuture<Any>): Observable<Any> {
         val observable: SingularObservable = new SingularObservable();
         future.thenAccept(Observable.Companion::of$lambda$1);
         return observable;
      }

      public fun <T> takeFirst(amount: Int = 1): TakeFirstTransform<Any> {
         return new TakeFirstTransform<>(amount);
      }

      public fun <T> ignoreFirst(amount: Int = 1): IgnoreFirstTransform<Any> {
         return new IgnoreFirstTransform<>(amount);
      }

      public fun <T> filter(predicate: (Any) -> Boolean): FilterTransform<Any> {
         return new FilterTransform<>(predicate);
      }

      public fun <T, O> map(mapping: (Any) -> Any): MapTransform<Any, Any> {
         return new MapTransform<>(mapping);
      }

      public fun <T> emitWhile(predicate: (Any) -> Boolean): EmitWhileTransform<Any> {
         return new EmitWhileTransform<>(predicate);
      }

      public fun <T> stopAfter(predicate: (Any) -> Boolean): StopAfterTransform<Any> {
         return new StopAfterTransform<>(predicate);
      }

      public fun <T> emitUntil(predicate: (Any) -> Boolean): EmitWhileTransform<Any> {
         return new EmitWhileTransform<>((new Function1<T, java.lang.Boolean>(predicate) {
            {
               super(1);
               this.$predicate = `$predicate`;
            }

            @NotNull
            public final java.lang.Boolean invoke(T it) {
               return !this.$predicate.invoke(it) as java.lang.Boolean;
            }
         }) as (T?) -> java.lang.Boolean);
      }

      public fun <T> tap(handler: (Any) -> Unit): MapTransform<Any, Any> {
         return this.map((new Function1<T, T>(handler) {
            {
               super(1);
               this.$handler = `$handler`;
            }

            public final T invoke(T it) {
               this.$handler.invoke(it);
               return (T)it;
            }
         }) as (T?) -> T);
      }

      @JvmStatic
      fun `of$lambda$1`(`$tmp0`: Function1, p0: Any) {
         `$tmp0`.invoke(p0);
      }
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T, O> pipe(`$this`: Observable<T>, transform: Transform<T, O>): Observable<O> {
         return new TransformObservable<>(`$this`, transform);
      }

      @JvmStatic
      fun <T, O1, O2> pipe(`$this`: Observable<T>, t1: Transform<T, O1>, t2: Transform<O1, O2>): TransformObservable<T, O2> {
         return new TransformObservable<>(`$this`, Observable.Companion.map((new Function1<T, O2>(t2, t1) {
            {
               super(1);
               this.$t2 = `$t2`;
               this.$t1 = `$t1`;
            }

            public final O2 invoke(T it) {
               return (O2)this.$t2.invoke(this.$t1.invoke((T)it));
            }
         }) as (T?) -> O2));
      }

      @JvmStatic
      fun <T, O1, O2, O3> pipe(`$this`: Observable<T>, t1: Transform<T, O1>, t2: Transform<O1, O2>, t3: Transform<O2, O3>): TransformObservable<T, O3> {
         return new TransformObservable<>(`$this`, Observable.Companion.map((new Function1<T, O3>(t3, t2, t1) {
            {
               super(1);
               this.$t3 = `$t3`;
               this.$t2 = `$t2`;
               this.$t1 = `$t1`;
            }

            public final O3 invoke(T it) {
               return (O3)this.$t3.invoke(this.$t2.invoke(this.$t1.invoke((T)it)));
            }
         }) as (T?) -> O3));
      }

      @JvmStatic
      fun <T, O1, O2, O3, O4> pipe(`$this`: Observable<T>, t1: Transform<T, O1>, t2: Transform<O1, O2>, t3: Transform<O2, O3>, t4: Transform<O3, O4>): TransformObservable<T, O4> {
         return new TransformObservable<>(`$this`, Observable.Companion.map((new Function1<T, O4>(t4, t3, t2, t1) {
            {
               super(1);
               this.$t4 = `$t4`;
               this.$t3 = `$t3`;
               this.$t2 = `$t2`;
               this.$t1 = `$t1`;
            }

            public final O4 invoke(T it) {
               return (O4)this.$t4.invoke(this.$t3.invoke(this.$t2.invoke(this.$t1.invoke((T)it))));
            }
         }) as (T?) -> O4));
      }

      @JvmStatic
      fun <T, O1, O2, O3, O4, O5> pipe(
         `$this`: Observable<T>, t1: Transform<T, O1>, t2: Transform<O1, O2>, t3: Transform<O2, O3>, t4: Transform<O3, O4>, t5: Transform<O4, O5>
      ): TransformObservable<T, O5> {
         return new TransformObservable<>(`$this`, Observable.Companion.map((new Function1<T, O5>(t5, t4, t3, t2, t1) {
            {
               super(1);
               this.$t5 = `$t5`;
               this.$t4 = `$t4`;
               this.$t3 = `$t3`;
               this.$t2 = `$t2`;
               this.$t1 = `$t1`;
            }

            public final O5 invoke(T it) {
               return (O5)this.$t5.invoke(this.$t4.invoke(this.$t3.invoke(this.$t2.invoke(this.$t1.invoke((T)it)))));
            }
         }) as (T?) -> O5));
      }

      @JvmStatic
      fun <T, O1, O2, O3, O4, O5, O6> pipe(
         `$this`: Observable<T>,
         t1: Transform<T, O1>,
         t2: Transform<O1, O2>,
         t3: Transform<O2, O3>,
         t4: Transform<O3, O4>,
         t5: Transform<O4, O5>,
         t6: Transform<O5, O6>
      ): TransformObservable<T, O6> {
         return new TransformObservable<>(`$this`, Observable.Companion.map((new Function1<T, O6>(t6, t5, t4, t3, t2, t1) {
            {
               super(1);
               this.$t6 = `$t6`;
               this.$t5 = `$t5`;
               this.$t4 = `$t4`;
               this.$t3 = `$t3`;
               this.$t2 = `$t2`;
               this.$t1 = `$t1`;
            }

            public final O6 invoke(T it) {
               return (O6)this.$t6.invoke(this.$t5.invoke(this.$t4.invoke(this.$t3.invoke(this.$t2.invoke(this.$t1.invoke((T)it))))));
            }
         }) as (T?) -> O6));
      }

      @JvmStatic
      fun <T> await(`$this`: Observable<T>): T {
         val result: ObjectRef = new ObjectRef();
         subscribe$default(`$this`, null, (new Function1<T, Unit>(result) {
            {
               super(1);
               this.$result = `$result`;
            }

            public final void invoke(T it) {
               this.$result.element = it;
            }
         }) as Function1, 1, null);

         while (result.element == null) {
            Thread.sleep(1L);
         }

         val var10000: Any = result.element;
         return (T)var10000;
      }
   }
}
