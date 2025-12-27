package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nIntRanges.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IntRanges.kt\ncom/cobblemon/mod/common/api/spawning/IntRanges\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,25:1\n1747#2,3:26\n*S KotlinDebug\n*F\n+ 1 IntRanges.kt\ncom/cobblemon/mod/common/api/spawning/IntRanges\n*L\n24#1:26,3\n*E\n"])
public open class IntRanges {
   public final var ranges: MutableList<IntRange> = (new ArrayList()) as java.util.List

   public constructor(vararg ranges: IntRange) : this() {
      this.ranges = ArraysKt.toMutableList(ranges);
   }

   public operator fun contains(value: Int): Boolean {
      val `$this$any$iv`: java.lang.Iterable = this.ranges;
      val var10000: Boolean;
      if (this.ranges is java.util.Collection && this.ranges.isEmpty()) {
         var10000 = false;
      } else {
         for (Object element$iv : $this$any$iv) {
            if (value <= (`element$iv` as IntRange).getLast() && (`element$iv` as IntRange).getFirst() <= value) {
               return true;
            }
         }

         var10000 = false;
      }

      return var10000;
   }
}
