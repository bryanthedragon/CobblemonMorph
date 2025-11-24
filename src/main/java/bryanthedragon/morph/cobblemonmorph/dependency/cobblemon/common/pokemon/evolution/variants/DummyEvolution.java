/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b$\u0010%J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0007\u0010\u0006R\"\u0010\b\u001a\u00020\u00048\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000f\u001a\u00020\u000e8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R \u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\"\u0010\u0019\u001a\u00020\u00048\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\t\u001a\u0004\b\u001a\u0010\u000b\"\u0004\b\u001b\u0010\rR \u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00138\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u0016\u001a\u0004\b\u001e\u0010\u0018R\u001a\u0010 \u001a\u00020\u001f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#\u00a8\u0006&"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/variants/DummyEvolution;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "evolve", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "test", "consumeHeldItem", "Z", "getConsumeHeldItem", "()Z", "setConsumeHeldItem", "(Z)V", "", "id", "Ljava/lang/String;", "getId", "()Ljava/lang/String;", "", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "learnableMoves", "Ljava/util/Set;", "getLearnableMoves", "()Ljava/util/Set;", "optional", "getOptional", "setOptional", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "requirements", "getRequirements", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "result", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getResult", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "<init>", "()V", "common"})
public final class DummyEvolution
implements Evolution {
    @NotNull
    private final String id;
    @NotNull
    private final PokemonProperties result = new PokemonProperties();
    private boolean optional;
    private boolean consumeHeldItem;
    @NotNull
    private final Set<EvolutionRequirement> requirements = new LinkedHashSet();
    @NotNull
    private final Set<MoveTemplate> learnableMoves = new LinkedHashSet();

    public DummyEvolution() {
        this.id = "dummy";
    }

    @Override
    @NotNull
    public String getId() {
        return this.id;
    }

    @Override
    @NotNull
    public PokemonProperties getResult() {
        return this.result;
    }

    @Override
    public boolean getOptional() {
        return this.optional;
    }

    @Override
    public void setOptional(boolean bl) {
        this.optional = bl;
    }

    @Override
    public boolean getConsumeHeldItem() {
        return this.consumeHeldItem;
    }

    @Override
    public void setConsumeHeldItem(boolean bl) {
        this.consumeHeldItem = bl;
    }

    @Override
    @NotNull
    public Set<EvolutionRequirement> getRequirements() {
        return this.requirements;
    }

    @Override
    @NotNull
    public Set<MoveTemplate> getLearnableMoves() {
        return this.learnableMoves;
    }

    @Override
    public boolean test(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return false;
    }

    @Override
    public boolean evolve(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return false;
    }

    @Override
    public void forceEvolve(@NotNull Pokemon pokemon) {
        Evolution.DefaultImpls.forceEvolve(this, pokemon);
    }

    @Override
    public void evolutionMethod(@NotNull Pokemon pokemon) {
        Evolution.DefaultImpls.evolutionMethod(this, pokemon);
    }

    @Override
    public void applyTo(@NotNull Pokemon pokemon) {
        Evolution.DefaultImpls.applyTo(this, pokemon);
    }
}

