/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ability;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbilityType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability.AbilityChanger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003B>\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012'\u0010\u0017\u001a#\u0012\u0019\u0012\u0017\u0012\u0002\b\u0003\u0018\u00010\u0004\u00a2\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00060\u0013\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u001d\u0010\u0007\u001a\u00020\u00062\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001d\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\tH\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\r\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012R5\u0010\u0017\u001a#\u0012\u0019\u0012\u0017\u0012\u0002\b\u0003\u0018\u00010\u0004\u00a2\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00060\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0018R \u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/item/interactive/ability/AbilityTypeChanger;", "Lcom/cobblemon/mod/common/api/abilities/PotentialAbility;", "T", "Lcom/cobblemon/mod/common/api/item/ability/AbilityChanger;", "Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;", "type", "", "canChangeFrom", "(Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;)Z", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "findCurrent", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;", "performChange", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "", "Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "queryPossible", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Ljava/util/Set;", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "other", "supportsChangingFrom", "Lkotlin/jvm/functions/Function1;", "Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;", "getType", "()Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;", "<init>", "(Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;Lkotlin/jvm/functions/Function1;)V", "common"})
@SourceDebugExtension(value={"SMAP\nAbilityTypeChanger.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AbilityTypeChanger.kt\ncom/cobblemon/mod/common/item/interactive/ability/AbilityTypeChanger\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,50:1\n766#2:51\n857#2,2:52\n766#2:54\n857#2,2:55\n1549#2:57\n1620#2,3:58\n288#2,2:61\n*S KotlinDebug\n*F\n+ 1 AbilityTypeChanger.kt\ncom/cobblemon/mod/common/item/interactive/ability/AbilityTypeChanger\n*L\n23#1:51\n23#1:52,2\n24#1:54\n24#1:55,2\n25#1:57\n25#1:58,3\n47#1:61,2\n*E\n"})
public class AbilityTypeChanger<T extends PotentialAbility>
implements AbilityChanger<T> {
    @NotNull
    private final PotentialAbilityType<T> type;
    @NotNull
    private final Function1<PotentialAbilityType<?>, Boolean> supportsChangingFrom;

    public AbilityTypeChanger(@NotNull PotentialAbilityType<T> type, @NotNull Function1<? super PotentialAbilityType<?>, Boolean> supportsChangingFrom) {
        Intrinsics.checkNotNullParameter(type, (String)"type");
        Intrinsics.checkNotNullParameter(supportsChangingFrom, (String)"supportsChangingFrom");
        this.type = type;
        this.supportsChangingFrom = supportsChangingFrom;
    }

    @Override
    @NotNull
    public PotentialAbilityType<T> getType() {
        return this.type;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Set<AbilityTemplate> queryPossible(@NotNull Pokemon pokemon) {
        void $this$mapTo$iv$iv;
        PotentialAbility it;
        Iterable $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Iterable $this$filter$iv = pokemon.getForm().getAbilities();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = (PotentialAbility)element$iv$iv;
            boolean bl = false;
            if (!Intrinsics.areEqual(it.getType(), this.getType())) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List ofType = (List)destination$iv$iv;
        $this$filter$iv = ofType;
        $i$f$filter = false;
        $this$filterTo$iv$iv = $this$filter$iv;
        destination$iv$iv = new ArrayList();
        $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = (PotentialAbility)element$iv$iv;
            boolean bl = false;
            if (!(!Intrinsics.areEqual((Object)it.getTemplate(), (Object)pokemon.getAbility().getTemplate()))) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$map$iv = (List)destination$iv$iv;
        boolean $i$f$map = false;
        $this$filterTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            it = (PotentialAbility)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.getTemplate());
        }
        return CollectionsKt.toSet((Iterable)((List)destination$iv$iv));
    }

    @Override
    public boolean performChange(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        PotentialAbilityType<?> currentType = this.findCurrent(pokemon);
        if (!this.canChangeFrom(currentType)) {
            return false;
        }
        Set<AbilityTemplate> possible = this.queryPossible(pokemon);
        AbilityTemplate abilityTemplate = (AbilityTemplate)CollectionsKt.randomOrNull((Collection)possible, (Random)((Random)Random.Default));
        if (abilityTemplate == null) {
            return false;
        }
        AbilityTemplate picked = abilityTemplate;
        AbilityTemplate old = pokemon.getAbility().getTemplate();
        pokemon.updateAbility(picked.create(false));
        return !Intrinsics.areEqual((Object)pokemon.getAbility().getTemplate(), (Object)old);
    }

    @Override
    public boolean canChangeFrom(@Nullable PotentialAbilityType<?> type) {
        return (Boolean)this.supportsChangingFrom.invoke(type);
    }

    private final PotentialAbilityType<?> findCurrent(Pokemon pokemon) {
        Object v0;
        block2: {
            if (pokemon.getAbility().getForced()) {
                return null;
            }
            Iterable $this$firstOrNull$iv = pokemon.getForm().getAbilities();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                PotentialAbility it = (PotentialAbility)element$iv;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getTemplate(), (Object)pokemon.getAbility().getTemplate())) continue;
                v0 = element$iv;
                break block2;
            }
            v0 = null;
        }
        PotentialAbility potentialAbility = v0;
        return potentialAbility != null ? potentialAbility.getType() : null;
    }
}

