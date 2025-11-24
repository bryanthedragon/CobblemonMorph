/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J#\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ+\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0007\u0010\n\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/stats/EvCalculator;", "", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "", "calculate", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Ljava/util/Map;", "opponentPokemon", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Ljava/util/Map;", "common"})
public interface EvCalculator {
    @NotNull
    public Map<Stat, Integer> calculate(@NotNull BattlePokemon var1);

    @NotNull
    public Map<Stat, Integer> calculate(@NotNull BattlePokemon var1, @NotNull BattlePokemon var2);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nEvCalculator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EvCalculator.kt\ncom/cobblemon/mod/common/api/pokemon/stats/EvCalculator$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,77:1\n766#2:78\n857#2,2:79\n1855#2:81\n1856#2:84\n215#3,2:82\n*S KotlinDebug\n*F\n+ 1 EvCalculator.kt\ncom/cobblemon/mod/common/api/pokemon/stats/EvCalculator$DefaultImpls\n*L\n32#1:78\n32#1:79,2\n33#1:81\n33#1:84\n35#1:82,2\n*E\n"})
    public static final class DefaultImpls {
        /*
         * WARNING - void declaration
         */
        @NotNull
        public static Map<Stat, Integer> calculate(@NotNull EvCalculator $this, @NotNull BattlePokemon battlePokemon) {
            void $this$filterTo$iv$iv;
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            HashMap total = new HashMap();
            Iterable $this$filter$iv = battlePokemon.getFacedOpponents();
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                BattlePokemon it = (BattlePokemon)element$iv$iv;
                boolean bl = false;
                if (!(it.getHealth() == 0)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$forEach$iv = (List)destination$iv$iv;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Map<Stat, Integer> results2;
                BattlePokemon opponent = (BattlePokemon)element$iv;
                boolean bl = false;
                Map<Stat, Integer> $this$forEach$iv2 = results2 = $this.calculate(battlePokemon, opponent);
                boolean $i$f$forEach2 = false;
                Iterator<Map.Entry<Stat, Integer>> iterator = $this$forEach$iv2.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Stat, Integer> element$iv2;
                    Map.Entry<Stat, Integer> entry = element$iv2 = iterator.next();
                    boolean bl2 = false;
                    Stat stat = entry.getKey();
                    int value2 = ((Number)entry.getValue()).intValue();
                    Integer n = (Integer)total.get(stat);
                    if (n == null) {
                        n = 0;
                    }
                    Intrinsics.checkNotNullExpressionValue((Object)n, (String)"total[stat] ?: 0");
                    int newValue = ((Number)n).intValue();
                    Integer n2 = newValue += value2;
                    ((Map)total).put(stat, n2);
                }
            }
            return total;
        }
    }
}

