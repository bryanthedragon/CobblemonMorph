package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api

import java.util.ArrayList;
import java.util.LinkedHashMap
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.markers.KMappedMarker

@SourceDebugExtension(["SMAP\nPrioritizedList.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PrioritizedList.kt\ncom/cobblemon/mod/common/api/PrioritizedList\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,59:1\n13579#2:60\n13580#2:62\n1#3:61\n1855#4,2:63\n*S KotlinDebug\n*F\n+ 1 PrioritizedList.kt\ncom/cobblemon/mod/common/api/PrioritizedList\n*L\n29#1:60\n29#1:62\n42#1:63,2\n*E\n"])
public open class PrioritizedList<T> : java.lang.Iterable<T>, KMappedMarker {
   public final val mapping: Map<Priority, List<Any>>
      public final get() {
         return MapsKt.toMap(this.priorityMap);
      }


   protected final val ordered: ArrayList<Any> = new ArrayList()
   protected final val priorityMap: MutableMap<Priority, MutableList<Any>> = (new LinkedHashMap()) as java.util.Map

   private fun reorder() {
      this.ordered.clear();

      val `$this$forEach$iv`: Any;
      for (Object element$iv : $this$forEach$iv) {
         val var10000: java.util.List = this.priorityMap.get(`element$iv`);
         if (var10000 != null) {
            this.ordered.addAll(var10000);
         }
      }
   }

   public fun add(priority: Priority, value: Any) {
      this.priorityMap.putIfAbsent(priority, new ArrayList<>());
      val var10000: java.util.List = this.priorityMap.get(priority);
      if (var10000 != null) {
         var10000.add(value);
      }

      this.reorder();
   }

   public fun remove(value: Any) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as java.util.List).remove(value);
      }

      this.reorder();
   }

   public fun remove(priority: Priority, value: Any) {
      val var10000: java.util.List = this.priorityMap.get(priority);
      if (var10000 != null) {
         var10000.remove(value);
      }

      this.reorder();
   }

   public fun clear() {
      this.priorityMap.clear();
      this.ordered.clear();
   }

   public fun isEmpty(): Boolean {
      return this.ordered.isEmpty();
   }

   public override operator fun iterator(): MutableIterator<Any> {
      val var10000: java.util.Iterator = this.ordered.iterator();
      return var10000;
   }
}
