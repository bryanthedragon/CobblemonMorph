/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceSource;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\u0014\u001a\u00020\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\u0004\b\u001b\u0010\u001cR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u001d\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0004\u001a\u0004\b\u0015\u0010\u0006R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/ExperienceGainedPostEvent;", "", "", "currentLevel", "I", "getCurrentLevel", "()I", "experience", "getExperience", "", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "learnedMoves", "Ljava/util/Set;", "getLearnedMoves", "()Ljava/util/Set;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "previousLevel", "getPreviousLevel", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;", "source", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;", "getSource", "()Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;IIILjava/util/Set;)V", "common"})
public final class ExperienceGainedPostEvent {
    @NotNull
    private final Pokemon pokemon;
    @NotNull
    private final ExperienceSource source;
    private final int experience;
    private final int previousLevel;
    private final int currentLevel;
    @NotNull
    private final Set<MoveTemplate> learnedMoves;

    public ExperienceGainedPostEvent(@NotNull Pokemon pokemon, @NotNull ExperienceSource source, int experience, int previousLevel, int currentLevel, @NotNull Set<MoveTemplate> learnedMoves) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        Intrinsics.checkNotNullParameter(learnedMoves, (String)"learnedMoves");
        this.pokemon = pokemon;
        this.source = source;
        this.experience = experience;
        this.previousLevel = previousLevel;
        this.currentLevel = currentLevel;
        this.learnedMoves = learnedMoves;
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

    public final int getPreviousLevel() {
        return this.previousLevel;
    }

    public final int getCurrentLevel() {
        return this.currentLevel;
    }

    @NotNull
    public final Set<MoveTemplate> getLearnedMoves() {
        return this.learnedMoves;
    }
}

