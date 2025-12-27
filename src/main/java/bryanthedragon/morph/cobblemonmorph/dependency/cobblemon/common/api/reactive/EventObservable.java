package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive

import java.util.Arrays
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nEventObservables.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,48:1\n13579#2,2:49\n*S KotlinDebug\n*F\n+ 1 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n*L\n18#1:49,2\n*E\n"])
public open class EventObservable<T> : SimpleObservable<T> {
   public inline fun post(vararg events: Any, then: (Any) -> Unit = <unrepresentable>.INSTANCE as Function1) {
      this.emit((T[])Arrays.copyOf(events, events.length));

      for (Object element$iv : events) {
         then.invoke(`element$iv`);
      }
   }
}
