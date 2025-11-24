/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.StandardPreEvolution;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \n2\u00020\u0001:\u0001\nR\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00068&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;", "", "Lcom/cobblemon/mod/common/pokemon/FormData;", "getForm", "()Lcom/cobblemon/mod/common/pokemon/FormData;", "form", "Lcom/cobblemon/mod/common/pokemon/Species;", "getSpecies", "()Lcom/cobblemon/mod/common/pokemon/Species;", "species", "Companion", "common"})
public interface PreEvolution {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution$Companion.$$INSTANCE;

    @NotNull
    public Species getSpecies();

    @NotNull
    public FormData getForm();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution$Companion;", "", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "Lcom/cobblemon/mod/common/pokemon/FormData;", "form", "Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;", "of", "(Lcom/cobblemon/mod/common/pokemon/Species;Lcom/cobblemon/mod/common/pokemon/FormData;)Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        @NotNull
        public final PreEvolution of(@NotNull Species species, @NotNull FormData form2) {
            Intrinsics.checkNotNullParameter((Object)species, (String)"species");
            Intrinsics.checkNotNullParameter((Object)form2, (String)"form");
            return new StandardPreEvolution(species, form2);
        }

        public static /* synthetic */ PreEvolution of$default(Companion companion, Species species, FormData formData, int n, Object object) {
            if ((n & 2) != 0) {
                formData = species.getStandardForm();
            }
            return companion.of(species, formData);
        }

        static {
            $$INSTANCE = new Companion();
        }
    }
}

