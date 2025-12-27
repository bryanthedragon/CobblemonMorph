package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension

public interface SpawningPrecalculation<T> {
   public abstract fun select(detail: SpawnDetail): List<Any> {
   }

   public abstract fun select(ctx: SpawningContext): Any? {
   }

   public open fun generate(details: List<SpawnDetail>, next: List<SpawningPrecalculation<*>>): PrecalculationResult<Any> {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nSpawningPrecalculation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningPrecalculation.kt\ncom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,135:1\n766#2:136\n857#2,2:137\n1360#2:139\n1446#2,2:140\n1549#2:142\n1620#2,3:143\n1448#2,3:146\n1490#2:149\n1520#2,3:150\n1523#2,3:160\n1179#2,2:163\n1253#2,4:165\n361#3,7:153\n*S KotlinDebug\n*F\n+ 1 SpawningPrecalculation.kt\ncom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation$DefaultImpls\n*L\n48#1:136\n48#1:137,2\n49#1:139\n49#1:140,2\n49#1:142\n49#1:143,3\n49#1:146,3\n50#1:149\n50#1:150,3\n50#1:160,3\n60#1:163,2\n60#1:165,4\n50#1:153,7\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun <T> generate(`$this`: SpawningPrecalculation<T>, details: MutableList<SpawnDetail>, next: MutableList<SpawningPrecalculation<?>>): PrecalculationResult<T> {
         var immediateNext: java.lang.Iterable = details;
         var `$i$f$associate`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : immediateNext) {
            if (!`$this`.select(`destination$iv$iv` as SpawnDetail).isEmpty()) {
               `$i$f$associate`.add(`destination$iv$iv`);
            }
         }

         immediateNext = `$i$f$associate` as java.util.List;
         `$i$f$associate` = new ArrayList();

         for (Object element$iv$ivx : immediateNext) {
            val var44: SpawnDetail = `element$iv$ivx` as SpawnDetail;
            val `element$iv$ivxx`: java.lang.Iterable = `$this`.select(`element$iv$ivx` as SpawnDetail);
            val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`element$iv$ivxx`, 10));

            for (Object item$iv$iv : element$iv$ivxx) {
               `destination$iv$ivx`.add(TuplesKt.to(`item$iv$iv`, var44));
            }

            CollectionsKt.addAll(`$i$f$associate`, `destination$iv$ivx` as java.util.List);
         }

         immediateNext = `$i$f$associate` as java.util.List;
         val `destination$iv$ivx`: java.util.Map = new LinkedHashMap();

         for (Object element$iv$ivx : immediateNext) {
            val var53: Any = (`element$iv$ivx` as Pair).getFirst();
            val var57: Any = `destination$iv$ivx`.get(var53);
            val var10000: Any;
            if (var57 == null) {
               val var60: Any = new ArrayList();
               `destination$iv$ivx`.put(var53, var60);
               var10000 = var60;
            } else {
               var10000 = var57;
            }

            (var10000 as java.util.List).add((`element$iv$ivx` as Pair).getSecond() as SpawnDetail);
         }

         val mapping: java.util.Map = MapsKt.toMap(`destination$iv$ivx`);
         if (next.isEmpty()) {
            return new FinalPrecalculationResult<>(`$this`, mapping);
         } else {
            val var29: SpawningPrecalculation = CollectionsKt.first(next) as SpawningPrecalculation;
            val var32: java.util.List = next.subList(1, next.size());
            val `$this$associate$iv`: java.lang.Iterable = mapping.entrySet();
            val `destination$iv$ivxx`: java.util.Map = new LinkedHashMap(
               RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(`$this$associate$iv`, 10)), 16)
            );

            for (Object element$iv$ivx : $this$associate$iv) {
               val var56: Pair = TuplesKt.to(
                  (`element$iv$ivx` as Entry).getKey(), var29.generate((`element$iv$ivx` as Entry).getValue() as MutableList<SpawnDetail>, var32)
               );
               `destination$iv$ivxx`.put(var56.getFirst(), var56.getSecond());
            }

            return new NestedPrecalculationResult<>(`$this`, `destination$iv$ivxx`);
         }
      }
   }
}
