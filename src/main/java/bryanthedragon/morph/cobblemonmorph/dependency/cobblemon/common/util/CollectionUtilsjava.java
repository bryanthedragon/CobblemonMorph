@file:SourceDebugExtension(["SMAP\nCollectionUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CollectionUtils.kt\ncom/cobblemon/mod/common/util/CollectionUtilsKt\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,61:1\n13579#2,2:62\n13579#2,2:64\n1855#3,2:66\n1855#3,2:68\n1855#3,2:70\n*S KotlinDebug\n*F\n+ 1 CollectionUtils.kt\ncom/cobblemon/mod/common/util/CollectionUtilsKt\n*L\n22#1:62,2\n28#1:64,2\n34#1:66,2\n37#1:68,2\n59#1:70,2\n*E\n"])

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.PokemonStats
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag

public fun ivsOf(vararg entries: Pair<Stat, Int>): PokemonStats {
   val stats: IVs = new IVs();

   for (Object element$iv : entries) {
      stats.set(`element$iv`.component1() as Stat, (`element$iv`.component2() as java.lang.Number).intValue());
   }

   return stats;
}

public fun evsOf(vararg entries: Pair<Stat, Int>): PokemonStats {
   val stats: EVs = new EVs();

   for (Object element$iv : entries) {
      stats.set(`element$iv`.component1() as Stat, (`element$iv`.component2() as java.lang.Number).intValue());
   }

   return stats;
}

public fun <T> Iterable<Any>.weightedSelection(weightFunction: (Any) -> Number): Any? {
   var weightSum: Float = 0.0F;

   for (Object element$iv : $this$weightedSelection) {
      weightSum += Math.max(0.0F, (weightFunction.invoke(`element$iv`) as java.lang.Number).floatValue());
   }

   val chosenSum: Float = Random.Default.nextFloat() * weightSum;
   weightSum = 0.0F;

   for (Object element$iv : $this$weightedSelection) {
      val weight: Float = (weightFunction.invoke(`element$iv`) as java.lang.Number).floatValue();
      if (weight > 0.0F) {
         weightSum += weight;
         if (weightSum >= chosenSum) {
            return (T)`element$iv`;
         }
      }
   }

   return null;
}

public fun <T> MutableList<Any>.swap(index1: Int, index2: Int) {
   val t1: Any = `$this$swap`.get(index1);
   `$this$swap`.set(index1, `$this$swap`.get(index2));
   `$this$swap`.set(index2, t1);
}

public fun Collection<Tag>.toNbtList(): ListTag {
   val nbtList: ListTag = new ListTag();

   val `$this$forEach$iv`: java.lang.Iterable;
   for (Object element$iv : $this$forEach$iv) {
      nbtList.add(`element$iv` as Tag);
   }

   return nbtList;
}
