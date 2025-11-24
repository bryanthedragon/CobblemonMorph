/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbilityType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/pokemon/properties/HiddenAbilityProperty;", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonProperty;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "apply", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "", "asString", "()Ljava/lang/String;", "", "matches", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nHiddenAbilityPropertyType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HiddenAbilityPropertyType.kt\ncom/cobblemon/mod/common/pokemon/properties/HiddenAbilityProperty\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,43:1\n76#2:44\n96#2,5:45\n76#2:53\n96#2,5:54\n766#3:50\n857#3,2:51\n1#4:59\n*S KotlinDebug\n*F\n+ 1 HiddenAbilityPropertyType.kt\ncom/cobblemon/mod/common/pokemon/properties/HiddenAbilityProperty\n*L\n33#1:44\n33#1:45,5\n40#1:53\n40#1:54,5\n34#1:50\n34#1:51,2\n*E\n"})
public final class HiddenAbilityProperty
implements CustomPokemonProperty {
    @Override
    @NotNull
    public String asString() {
        return "hiddenability";
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void apply(@NotNull Pokemon pokemon) {
        void $this$filterTo$iv$iv;
        Object it;
        Iterable $this$flatMapTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Map $this$flatMap$iv = pokemon.getForm().getAbilities().getMapping();
        boolean $i$f$flatMap = false;
        Map map = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Map.Entry element$iv$iv : $this$flatMapTo$iv$iv.entrySet()) {
            it = element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = (List)it.getValue();
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        Iterable $this$filter$iv = (List)destination$iv$iv;
        boolean $i$f$filter = false;
        $this$flatMapTo$iv$iv = $this$filter$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Map.Entry element$iv$iv : $this$filterTo$iv$iv) {
            it = (PotentialAbility)((Object)element$iv$iv);
            boolean bl = false;
            if (!Intrinsics.areEqual(it.getType(), (Object)HiddenAbilityType.INSTANCE)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List possible = (List)destination$iv$iv;
        PotentialAbility potentialAbility = (PotentialAbility)CollectionsKt.randomOrNull((Collection)possible, (Random)((Random)Random.Default));
        if (potentialAbility == null) {
            return;
        }
        PotentialAbility picked = potentialAbility;
        pokemon.updateAbility(picked.getTemplate().create(false));
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean matches(@NotNull Pokemon pokemon) {
        Object v0;
        block2: {
            void $this$flatMapTo$iv$iv;
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Map $this$flatMap$iv = pokemon.getForm().getAbilities().getMapping();
            boolean $i$f$flatMap = false;
            Map map = $this$flatMap$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$flatMapTo = false;
            Iterator iterator = $this$flatMapTo$iv$iv.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry element$iv$iv;
                Map.Entry it = element$iv$iv = iterator.next();
                boolean bl = false;
                Iterable list$iv$iv = (List)it.getValue();
                CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
            }
            Iterable iterable = (List)destination$iv$iv;
            for (Object e : iterable) {
                PotentialAbility it = (PotentialAbility)e;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getTemplate(), (Object)pokemon.getAbility().getTemplate())) continue;
                v0 = e;
                break block2;
            }
            v0 = null;
        }
        PotentialAbility potentialAbility = v0;
        return Intrinsics.areEqual(potentialAbility != null ? potentialAbility.getType() : null, (Object)HiddenAbilityType.INSTANCE);
    }

    @Override
    public void apply(@NotNull PokemonEntity pokemonEntity) {
        CustomPokemonProperty.DefaultImpls.apply(this, pokemonEntity);
    }

    @Override
    public boolean matches(@NotNull PokemonEntity pokemonEntity) {
        return CustomPokemonProperty.DefaultImpls.matches(this, pokemonEntity);
    }
}

