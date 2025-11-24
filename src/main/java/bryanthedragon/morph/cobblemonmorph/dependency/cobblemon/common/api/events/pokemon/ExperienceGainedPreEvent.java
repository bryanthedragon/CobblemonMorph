/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceSource;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014R\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/ExperienceGainedPreEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "", "experience", "I", "getExperience", "()I", "setExperience", "(I)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;", "source", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;", "getSource", "()Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;I)V", "common"})
public final class ExperienceGainedPreEvent
extends Cancelable {
    @NotNull
    private final Pokemon pokemon;
    @NotNull
    private final ExperienceSource source;
    private int experience;

    public ExperienceGainedPreEvent(@NotNull Pokemon pokemon, @NotNull ExperienceSource source, int experience) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        this.pokemon = pokemon;
        this.source = source;
        this.experience = experience;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    @NotNull
    public final ExperienceSource getSource() {
        return this.source;
    }

    public final int getExperience() {
        return this.experience;
    }

    public final void setExperience(int n) {
        this.experience = n;
    }
}

