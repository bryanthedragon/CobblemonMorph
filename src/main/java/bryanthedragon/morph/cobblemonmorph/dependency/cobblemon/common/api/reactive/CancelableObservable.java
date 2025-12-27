package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import java.util.Arrays
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nEventObservables.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n*L\n1#1,48:1\n13579#2:49\n13580#2:52\n13579#2,2:55\n288#3,2:50\n17#4,2:53\n19#4:57\n*S KotlinDebug\n*F\n+ 1 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n*L\n28#1:49\n28#1:52\n40#1:55,2\n32#1:50,2\n40#1:53,2\n40#1:57\n*E\n"])
public open class CancelableObservable<T extends Cancelable> : EventObservable<T> {
   public open fun emit(vararg values: Any) {
      if (!this.getSubscriptions().isEmpty()) {
         for (Object element$iv : values) {
            val value: Cancelable = (Cancelable)`element$iv`;

            val `$this$firstOrNull$iv`: java.lang.Iterable;
            for (Object element$ivx : $this$firstOrNull$iv) {
               (`element$ivx` as ObservableSubscription).handle(value);
               if (value.isCanceled()) {
                  break;
               }
            }
         }
      }
   }

   public inline fun postThen(event: Any, ifCanceled: (Any) -> Unit = ..., ifSucceeded: (Any) -> Unit) {
      val `this_$iv`: EventObservable = this;
      val `events$iv`: Array<Cancelable> = new Cancelable[]{event};
      `this_$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

      for (Object element$iv$iv : events$iv) {
         if (((Cancelable)`element$iv$iv`).isCanceled()) {
            ifCanceled.invoke(`element$iv$iv`);
         } else {
            ifSucceeded.invoke(`element$iv$iv`);
         }
      }
   }
}
