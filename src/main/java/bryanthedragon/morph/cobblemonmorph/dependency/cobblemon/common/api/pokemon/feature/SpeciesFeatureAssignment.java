/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\n\u0010\u000bR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0005\u001a\u0004\b\t\u0010\u0007\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureAssignment;", "", "", "", "features", "Ljava/util/List;", "getFeatures", "()Ljava/util/List;", "pokemon", "getPokemon", "<init>", "()V", "common"})
public final class SpeciesFeatureAssignment {
    @NotNull
    private final List<String> pokemon = CollectionsKt.emptyList();
    @NotNull
    private final List<String> features = CollectionsKt.emptyList();

    @NotNull
    public final List<String> getPokemon() {
        return this.pokemon;
    }

    @NotNull
    public final List<String> getFeatures() {
        return this.features;
    }
}

