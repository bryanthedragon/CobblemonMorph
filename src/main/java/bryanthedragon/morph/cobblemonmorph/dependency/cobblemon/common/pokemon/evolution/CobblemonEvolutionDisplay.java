/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0080\b\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u0012\u0006\u0010 \u001a\u00020\u001f\u00a2\u0006\u0004\b!\u0010\"B%\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\b\u00a2\u0006\u0004\b!\u0010#J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ4\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\u00052\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\bH\u00c6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001a\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0016\u001a\u00020\u0015H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0004R \u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\r\u0010\u0019\u001a\u0004\b\u001a\u0010\nR\u001a\u0010\u000b\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000b\u0010\u001b\u001a\u0004\b\u001c\u0010\u0004R\u001a\u0010\f\u001a\u00020\u00058\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\f\u0010\u001d\u001a\u0004\b\u001e\u0010\u0007\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/CobblemonEvolutionDisplay;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;", "", "component1", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/pokemon/Species;", "component2", "()Lcom/cobblemon/mod/common/pokemon/Species;", "", "component3", "()Ljava/util/Set;", "id", "species", "aspects", "copy", "(Ljava/lang/String;Lcom/cobblemon/mod/common/pokemon/Species;Ljava/util/Set;)Lcom/cobblemon/mod/common/pokemon/evolution/CobblemonEvolutionDisplay;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "Ljava/util/Set;", "getAspects", "Ljava/lang/String;", "getId", "Lcom/cobblemon/mod/common/pokemon/Species;", "getSpecies", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "<init>", "(Ljava/lang/String;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "(Ljava/lang/String;Lcom/cobblemon/mod/common/pokemon/Species;Ljava/util/Set;)V", "common"})
public final class CobblemonEvolutionDisplay
implements EvolutionDisplay {
    @NotNull
    private final String id;
    @NotNull
    private final Species species;
    @NotNull
    private final Set<String> aspects;

    public CobblemonEvolutionDisplay(@NotNull String id, @NotNull Species species, @NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        this.id = id;
        this.species = species;
        this.aspects = aspects;
    }

    @Override
    @NotNull
    public String getId() {
        return this.id;
    }

    @Override
    @NotNull
    public Species getSpecies() {
        return this.species;
    }

    @Override
    @NotNull
    public Set<String> getAspects() {
        return this.aspects;
    }

    public CobblemonEvolutionDisplay(@NotNull String id, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this(id, pokemon.getSpecies(), pokemon.getAspects());
    }

    @NotNull
    public final String component1() {
        return this.id;
    }

    @NotNull
    public final Species component2() {
        return this.species;
    }

    @NotNull
    public final Set<String> component3() {
        return this.aspects;
    }

    @NotNull
    public final CobblemonEvolutionDisplay copy(@NotNull String id, @NotNull Species species, @NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        return new CobblemonEvolutionDisplay(id, species, aspects);
    }

    public static /* synthetic */ CobblemonEvolutionDisplay copy$default(CobblemonEvolutionDisplay cobblemonEvolutionDisplay, String string, Species species, Set set2, int n, Object object) {
        if ((n & 1) != 0) {
            string = cobblemonEvolutionDisplay.id;
        }
        if ((n & 2) != 0) {
            species = cobblemonEvolutionDisplay.species;
        }
        if ((n & 4) != 0) {
            set2 = cobblemonEvolutionDisplay.aspects;
        }
        return cobblemonEvolutionDisplay.copy(string, species, set2);
    }

    @NotNull
    public String toString() {
        return "CobblemonEvolutionDisplay(id=" + this.id + ", species=" + this.species + ", aspects=" + this.aspects + ")";
    }

    public int hashCode() {
        int result = this.id.hashCode();
        result = result * 31 + this.species.hashCode();
        result = result * 31 + ((Object)this.aspects).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CobblemonEvolutionDisplay)) {
            return false;
        }
        CobblemonEvolutionDisplay cobblemonEvolutionDisplay = (CobblemonEvolutionDisplay)other;
        if (!Intrinsics.areEqual((Object)this.id, (Object)cobblemonEvolutionDisplay.id)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.species, (Object)cobblemonEvolutionDisplay.species)) {
            return false;
        }
        return Intrinsics.areEqual(this.aspects, cobblemonEvolutionDisplay.aspects);
    }
}

