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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J$\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\fH\u00d6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0015\u001a\u00020\u0014H\u00d6\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u001a\u0010\t\u001a\u00020\u00058\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\t\u0010\u0017\u001a\u0004\b\u0018\u0010\u0007R\u001a\u0010\b\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\u0019\u001a\u0004\b\u001a\u0010\u0004\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/StandardPreEvolution;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;", "Lcom/cobblemon/mod/common/pokemon/Species;", "component1", "()Lcom/cobblemon/mod/common/pokemon/Species;", "Lcom/cobblemon/mod/common/pokemon/FormData;", "component2", "()Lcom/cobblemon/mod/common/pokemon/FormData;", "species", "form", "copy", "(Lcom/cobblemon/mod/common/pokemon/Species;Lcom/cobblemon/mod/common/pokemon/FormData;)Lcom/cobblemon/mod/common/pokemon/evolution/StandardPreEvolution;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/pokemon/FormData;", "getForm", "Lcom/cobblemon/mod/common/pokemon/Species;", "getSpecies", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Species;Lcom/cobblemon/mod/common/pokemon/FormData;)V", "common"})
public final class StandardPreEvolution
implements PreEvolution {
    @NotNull
    private final Species species;
    @NotNull
    private final FormData form;

    public StandardPreEvolution(@NotNull Species species, @NotNull FormData form2) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter((Object)form2, (String)"form");
        this.species = species;
        this.form = form2;
    }

    @Override
    @NotNull
    public Species getSpecies() {
        return this.species;
    }

    @Override
    @NotNull
    public FormData getForm() {
        return this.form;
    }

    @NotNull
    public final Species component1() {
        return this.species;
    }

    @NotNull
    public final FormData component2() {
        return this.form;
    }

    @NotNull
    public final StandardPreEvolution copy(@NotNull Species species, @NotNull FormData form2) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter((Object)form2, (String)"form");
        return new StandardPreEvolution(species, form2);
    }

    public static /* synthetic */ StandardPreEvolution copy$default(StandardPreEvolution standardPreEvolution, Species species, FormData formData, int n, Object object) {
        if ((n & 1) != 0) {
            species = standardPreEvolution.species;
        }
        if ((n & 2) != 0) {
            formData = standardPreEvolution.form;
        }
        return standardPreEvolution.copy(species, formData);
    }

    @NotNull
    public String toString() {
        return "StandardPreEvolution(species=" + this.species + ", form=" + this.form + ")";
    }

    public int hashCode() {
        int result = this.species.hashCode();
        result = result * 31 + this.form.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StandardPreEvolution)) {
            return false;
        }
        StandardPreEvolution standardPreEvolution = (StandardPreEvolution)other;
        if (!Intrinsics.areEqual((Object)this.species, (Object)standardPreEvolution.species)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.form, (Object)standardPreEvolution.form);
    }
}

