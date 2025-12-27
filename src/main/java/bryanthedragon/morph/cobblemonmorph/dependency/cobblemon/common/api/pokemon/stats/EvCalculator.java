package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import java.util.ArrayList;
import java.util.HashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension

public interface EvCalculator {
   public open fun calculate(battlePokemon: BattlePokemon): Map<Stat, Int> {
   }

   public abstract fun calculate(battlePokemon: BattlePokemon, opponentPokemon: BattlePokemon): Map<Stat, Int> {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nEvCalculator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EvCalculator.kt\ncom/cobblemon/mod/common/api/pokemon/stats/EvCalculator$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,77:1\n766#2:78\n857#2,2:79\n1855#2:81\n1856#2:84\n215#3,2:82\n*S KotlinDebug\n*F\n+ 1 EvCalculator.kt\ncom/cobblemon/mod/common/api/pokemon/stats/EvCalculator$DefaultImpls\n*L\n32#1:78\n32#1:79,2\n33#1:81\n33#1:84\n35#1:82,2\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun calculate(`$this`: EvCalculator, battlePokemon: BattlePokemon): MutableMap<Stat, Int> {
         val total: HashMap = new HashMap();
         val `$this$forEach$iv`: java.lang.Iterable = battlePokemon.getFacedOpponents();
         val `element$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$forEach$iv) {
            if ((results as BattlePokemon).getHealth() == 0) {
               `element$iv`.add(results);
            }
         }

         for (Object element$ivx : $this$forEach$iv) {
            for (Entry element$ivxx : $this.calculate(battlePokemon, (BattlePokemon)element$ivx).entrySet()) {
               val stat: Stat = `element$ivxx`.getKey() as Stat;
               val value: Int = (`element$ivxx`.getValue() as java.lang.Number).intValue();
               var var10000: Int = total.get(stat) as Int;
               if (var10000 == null) {
                  var10000 = 0;
               }

               total.put(stat, var10000.intValue() + value);
            }
         }

         return total;
      }
   }
}
