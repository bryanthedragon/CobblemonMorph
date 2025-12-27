package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import java.util.ArrayList;

public enum MergeMode : Merger {
   REPLACE,
   INSERT,
   KEEP
   public class INSERT : MergeMode {
      fun INSERT(`$enum$name`: java.lang.String, `$enum$ordinal`: Int) {
         super(null);
      }

      public override fun <T> merge(base: MutableCollection<Any>?, other: MutableCollection<Any>?): MutableCollection<Any>? {
         var var10000: java.util.Collection;
         if (other == null) {
            var10000 = base;
         } else {
            var10000 = base;
            if (base == null) {
               var10000 = new ArrayList();
            }

            var10000.addAll(other);
            var10000 = var10000;
         }

         return var10000;
      }

      public override fun <T> mergeSingle(base: Any?, other: Any?): Any? {
         return (T)MergeMode.KEEP.mergeSingle(base, other);
      }
   }

   public class KEEP : MergeMode {
      fun KEEP(`$enum$name`: java.lang.String, `$enum$ordinal`: Int) {
         super(null);
      }

      public override fun <T> merge(base: MutableCollection<Any>?, other: MutableCollection<Any>?): MutableCollection<Any>? {
         if (base != null) {
            val var10000: java.util.List = CollectionsKt.toMutableList(base);
            if (var10000 != null) {
               return var10000;
            }
         }

         return other;
      }

      public override fun <T> mergeSingle(base: Any?, other: Any?): Any? {
         var var10000: Any = base;
         if (base == null) {
            var10000 = other;
         }

         return (T)var10000;
      }
   }

   public class REPLACE : MergeMode {
      fun REPLACE(`$enum$name`: java.lang.String, `$enum$ordinal`: Int) {
         super(null);
      }

      public override fun <T> merge(base: MutableCollection<Any>?, other: MutableCollection<Any>?): MutableCollection<Any>? {
         if (other != null) {
            val var10000: java.util.List = CollectionsKt.toMutableList(other);
            if (var10000 != null) {
               return var10000;
            }
         }

         return base;
      }

      public override fun <T> mergeSingle(base: Any?, other: Any?): Any? {
         var var10000: Any = other;
         if (other == null) {
            var10000 = base;
         }

         return (T)var10000;
      }
   }
}
