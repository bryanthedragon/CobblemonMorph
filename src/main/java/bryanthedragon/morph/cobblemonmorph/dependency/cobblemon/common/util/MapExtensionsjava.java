package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import java.util.ArrayList;
import kotlin.collections.Map.Entry

public fun <A, B> MutableMap<Any, Any>.removeIf(predicate: (Entry<Any, Any>) -> Boolean) {
   val toRemove: java.util.List = new ArrayList();

   for (java.util.Map.Entry entry : $this$removeIf.entrySet()) {
      if (predicate.invoke(key) as java.lang.Boolean) {
         toRemove.add(key.getKey());
      }
   }

   for (Object key : toRemove) {
      `$this$removeIf`.remove(var6);
   }
}
