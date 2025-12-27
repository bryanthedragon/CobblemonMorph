package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.IntRanges
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.util.ArrayList;
import java.util.Locale
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nTimeRangeAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TimeRangeAdapter.kt\ncom/cobblemon/mod/common/util/adapters/IntRangesAdapter\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,59:1\n26#2:60\n1855#3,2:61\n37#4,2:63\n*S KotlinDebug\n*F\n+ 1 TimeRangeAdapter.kt\ncom/cobblemon/mod/common/util/adapters/IntRangesAdapter\n*L\n40#1:60\n44#1:61,2\n57#1:63,2\n*E\n"])
public class IntRangesAdapter<T extends IntRanges>(ranges: Map<String, Any>, initializer: (Array<IntRange>) -> Any) : JsonDeserializer<T>, JsonSerializer<T> {
   public final val initializer: (Array<IntRange>) -> Any
   public final val ranges: Map<String, Any>

   init {
      this.ranges = ranges;
      this.initializer = initializer;
   }

   public open fun serialize(src: Any, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
      return (new JsonPrimitive(CollectionsKt.joinToString$default(src.getRanges(), null, null, null, 0, null, <unrepresentable>.INSTANCE, 31, null))) as JsonElement;
   }

   public open fun deserialize(json: JsonElement, t: Type, ctx: JsonDeserializationContext): Any {
      val str: java.lang.String = json.getAsString();
      if (StringsKt.split$default(str, new java.lang.String[]{","}, false, 0, 6, null).isEmpty()) {
         return (T)this.initializer.invoke(new IntRange[0]);
      } else {
         val var15: java.util.List = new ArrayList();

         val `$this$toTypedArray$iv`: java.lang.Iterable;
         for (Object element$iv : $this$toTypedArray$iv) {
            val range: java.util.List = StringsKt.split$default(`element$iv` as java.lang.String, new java.lang.String[]{"-"}, false, 0, 6, null);
            if (range.size() == 2 && MiscUtilsKt.isInt(range.get(0) as java.lang.String) && MiscUtilsKt.isInt(range.get(1) as java.lang.String)) {
               var15.add(new IntRange(Integer.parseInt(range.get(0) as java.lang.String), Integer.parseInt(range.get(1) as java.lang.String)));
            } else if (range.size() == 1) {
               val var21: java.util.Map = this.ranges;
               val var10001: java.lang.String = (range.get(0) as java.lang.String).toLowerCase(Locale.ROOT);
               val var19: IntRanges = var21.get(var10001) as IntRanges;
               if (var19 != null) {
                  var15.addAll(var19.getRanges());
               } else if (MiscUtilsKt.isInt(range.get(0) as java.lang.String)) {
                  var15.add(new IntRange(Integer.parseInt(range.get(0) as java.lang.String), Integer.parseInt(range.get(0) as java.lang.String)));
               }
            }
         }

         return (T)this.initializer.invoke(var15.toArray(new IntRange[0]));
      }
   }
}
