package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import java.util.ArrayList;
import kotlin.random.Random

public fun <T> List<Any>.random(amount: Int): List<Any> {
   val values: java.util.List = new ArrayList();
   var i: Int = 1;
   if (1 <= amount) {
      while (true) {
         values.add(CollectionsKt.random(`$this$random`, Random.Default as Random));
         if (i == amount) {
            break;
         }

         i++;
      }
   }

   return values;
}

public fun <T> List<Any>.randomNoCopy(amount: Int): List<Any> {
   val toChooseFrom: java.util.List = CollectionsKt.toMutableList(`$this$randomNoCopy`);
   val values: java.util.List = new ArrayList();
   var amountLeft: Int = amount;

   while (amountLeft > 0 && !toChooseFrom.isEmpty()) {
      val random: Any = CollectionsKt.random(toChooseFrom, Random.Default as Random);
      toChooseFrom.remove(random);
      if (!values.contains(random)) {
         values.add(random);
         amountLeft--;
      }
   }

   return values;
}
