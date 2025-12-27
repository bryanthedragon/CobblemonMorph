@file:SourceDebugExtension(["SMAP\nPlayerInventoryExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlayerInventoryExtensions.kt\ncom/cobblemon/mod/common/util/PlayerInventoryExtensionsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,32:1\n1855#2,2:33\n*S KotlinDebug\n*F\n+ 1 PlayerInventoryExtensions.kt\ncom/cobblemon/mod/common/util/PlayerInventoryExtensionsKt\n*L\n17#1:33,2\n*E\n"])

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import java.util.function.Predicate
import java.util.stream.Collectors
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.Ref.IntRef
import net.minecraft.core.NonNullList
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack

public fun Inventory.removeAmountIf(amount: Int, rule: Predicate<ItemStack>) {
   val `$this$forEach$iv`: java.lang.Iterable;
   for (Object element$iv : $this$forEach$iv) {
      val it: NonNullList = `element$iv` as NonNullList;
      val index: IntRef = new IntRef();
      val matches: java.util.List = it.stream()
         .map(PlayerInventoryExtensionsKt::removeAmountIf$lambda$2$lambda$0)
         .filter(PlayerInventoryExtensionsKt::removeAmountIf$lambda$2$lambda$1)
         .collect(Collectors.toList());
      var remaining: Int = amount;

      while (remaining > 0) {
         val var14: Pair = CollectionsKt.removeFirstOrNull(matches) as Pair;
         if (var14 == null) {
            break;
         }

         remaining -= `$this$removeAmountIf`.m_7407_((var14.getFirst() as java.lang.Number).intValue(), amount).m_41613_();
      }
   }
}

fun `removeAmountIf$lambda$2$lambda$0`(`$tmp0`: Function1, p0: Any): Pair {
   return `$tmp0`.invoke(p0) as Pair;
}

fun `removeAmountIf$lambda$2$lambda$1`(`$tmp0`: Function1, p0: Any): Boolean {
   return `$tmp0`.invoke(p0) as java.lang.Boolean;
}
