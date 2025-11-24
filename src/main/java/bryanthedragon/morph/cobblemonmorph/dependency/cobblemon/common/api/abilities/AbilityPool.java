/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Ability;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\u00a2\u0006\u0004\b\r\u0010\u000eJ/\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b2\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/abilities/AbilityPool;", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "Lcom/cobblemon/mod/common/api/abilities/PotentialAbility;", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "", "", "aspects", "Lkotlin/Pair;", "Lcom/cobblemon/mod/common/api/abilities/Ability;", "Lcom/cobblemon/mod/common/api/Priority;", "select", "(Lcom/cobblemon/mod/common/pokemon/Species;Ljava/util/Set;)Lkotlin/Pair;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nAbilityPool.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AbilityPool.kt\ncom/cobblemon/mod/common/api/abilities/AbilityPool\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,38:1\n766#2:39\n857#2,2:40\n*S KotlinDebug\n*F\n+ 1 AbilityPool.kt\ncom/cobblemon/mod/common/api/abilities/AbilityPool\n*L\n27#1:39\n27#1:40,2\n*E\n"})
public class AbilityPool
extends PrioritizedList<PotentialAbility> {
    /*
     * WARNING - void declaration
     */
    @NotNull
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final Pair<Ability, Priority> select(@NotNull Species species, @NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        for (Priority priority : Priority.values()) {
            void $this$filterTo$iv$iv;
            List list = this.getPriorityMap().get((Object)priority);
            if (list == null) continue;
            Iterable $this$filter$iv = list;
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                PotentialAbility it = (PotentialAbility)element$iv$iv;
                boolean bl = false;
                if (!it.isSatisfiedBy(aspects)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            List potentialAbilities = (List)destination$iv$iv;
            if (!(!((Collection)potentialAbilities).isEmpty())) continue;
            return TuplesKt.to((Object)AbilityTemplate.create$default(((PotentialAbility)CollectionsKt.random((Collection)potentialAbilities, (Random)((Random)Random.Default))).getTemplate(), false, 1, null), (Object)((Object)priority));
        }
        Cobblemon.INSTANCE.getLOGGER().error("Unable to select an ability from the pool for " + species + " and aspects: " + CollectionsKt.joinToString$default((Iterable)aspects, null, null, null, (int)0, null, null, (int)63, null));
        Cobblemon.INSTANCE.getLOGGER().error("Usually this happens when a client is doing logic it shouldn't. Please show this to the Cobblemon developers!");
        new Exception().printStackTrace();
        return TuplesKt.to((Object)AbilityTemplate.create$default(Abilities.INSTANCE.first(), false, 1, null), (Object)((Object)Priority.LOWEST));
    }
}

