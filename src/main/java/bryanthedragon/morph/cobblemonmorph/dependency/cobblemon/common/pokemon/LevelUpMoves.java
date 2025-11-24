/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.comparisons.ComparisonsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0005\u0018\u00002*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005B\u0007\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\u0006\u0010\u0006\u001a\u00020\u0002\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/pokemon/LevelUpMoves;", "Ljava/util/HashMap;", "", "", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "Lkotlin/collections/HashMap;", "level", "", "getLevelUpMovesUpTo", "(I)Ljava/util/Set;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nLevelUpMoves.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LevelUpMoves.kt\ncom/cobblemon/mod/common/pokemon/LevelUpMoves\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,14:1\n766#2:15\n857#2,2:16\n1045#2:18\n1360#2:19\n1446#2,5:20\n*S KotlinDebug\n*F\n+ 1 LevelUpMoves.kt\ncom/cobblemon/mod/common/pokemon/LevelUpMoves\n*L\n13#1:15\n13#1:16,2\n13#1:18\n13#1:19\n13#1:20,5\n*E\n"})
public final class LevelUpMoves
extends HashMap<Integer, List<MoveTemplate>> {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Set<MoveTemplate> getLevelUpMovesUpTo(int level) {
        void $this$flatMapTo$iv$iv;
        Map.Entry it;
        Iterable $this$filterTo$iv$iv;
        Set<Map.Entry<Integer, List<MoveTemplate>>> set2 = this.entrySet();
        Intrinsics.checkNotNullExpressionValue(set2, (String)"entries");
        Iterable $this$filter$iv = set2;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = (Map.Entry)element$iv$iv;
            boolean bl = false;
            Object k = it.getKey();
            Intrinsics.checkNotNullExpressionValue(k, (String)"it.key");
            if (!(((Number)k).intValue() <= level)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$sortedBy$iv = (List)destination$iv$iv;
        boolean $i$f$sortedBy = false;
        Iterable $this$flatMap$iv = CollectionsKt.sortedWith((Iterable)$this$sortedBy$iv, (Comparator)new Comparator(){

            public final int compare(T a, T b) {
                Map.Entry it = (Map.Entry)a;
                boolean bl = false;
                Comparable comparable = (Integer)it.getKey();
                it = (Map.Entry)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues((Comparable)comparable2, (Comparable)((Integer)it.getKey()));
            }
        });
        boolean $i$f$flatMap = false;
        $this$filterTo$iv$iv = $this$flatMap$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            it = (Map.Entry)element$iv$iv;
            boolean bl = false;
            Object v = it.getValue();
            Intrinsics.checkNotNullExpressionValue(v, (String)"it.value");
            Iterable list$iv$iv = (List)v;
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        return CollectionsKt.toSet((Iterable)((List)destination$iv$iv));
    }
}

