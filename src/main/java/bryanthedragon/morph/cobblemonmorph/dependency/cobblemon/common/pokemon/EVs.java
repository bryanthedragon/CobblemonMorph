package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nEVs.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EVs.kt\ncom/cobblemon/mod/common/pokemon/EVs\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,60:1\n1179#2,2:61\n1253#2,4:63\n1#3:67\n*S KotlinDebug\n*F\n+ 1 EVs.kt\ncom/cobblemon/mod/common/pokemon/EVs\n*L\n23#1:61,2\n23#1:63,4\n*E\n"])
public class EVs : PokemonStats {
   public open val acceptableRange: IntRange = new IntRange(0, 252)
   public open val defaultValue: Int

   protected override fun canSet(stat: Stat, value: Int): Boolean {
      if (!super.canSet(stat, value)) {
         return false;
      } else {
         val simulatedTotal: java.lang.Iterable = this;
         val `destination$iv$iv`: java.util.Map = new LinkedHashMap(
            RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(this, 10)), 16)
         );

         for (Object element$iv$iv : $this$associate$iv) {
            val var17: Pair = TuplesKt.to((`element$iv$iv` as Entry).getKey(), (`element$iv$iv` as Entry).getValue());
            `destination$iv$iv`.put(var17.getFirst(), var17.getSecond());
         }

         val simulated: java.util.Map = MapsKt.toMutableMap(`destination$iv$iv`);
         simulated.put(stat, value);
         return CollectionsKt.sumOfInt(simulated.values()) <= 510;
      }
   }

   public fun add(key: Stat, value: Int): Int {
      val currentStat: java.lang.Iterable = this;
      var possibleForStat: Int = 0;

      for (Object coercedValue : currentStat) {
         possibleForStat += ((coercedValue as Entry).getValue() as java.lang.Number).intValue();
      }

      if (possibleForStat == 510 && value > 0) {
         return 0;
      } else {
         val var12: Int = this.getOrDefault(key);
         val var15: Int = RangesKt.coerceIn(value, -var12, Math.min(252 - var12, 510 - possibleForStat));
         val var16: Int = var12 + var15;
         if (var12 + var15 != var12) {
            this.set(key, var16);
            return var15;
         } else {
            return 0;
         }
      }
   }

   public companion object {
      public const val MAX_STAT_VALUE: Int
      public const val MAX_TOTAL_VALUE: Int

      public fun createEmpty(): EVs {
         return Cobblemon.INSTANCE.getStatProvider().createEmptyEVs();
      }
   }
}
