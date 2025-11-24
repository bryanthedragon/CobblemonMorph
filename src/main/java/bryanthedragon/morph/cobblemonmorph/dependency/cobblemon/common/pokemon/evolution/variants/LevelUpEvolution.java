/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PassiveEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0016\u0018\u0000 +2\u00020\u0001:\u0001+B\t\b\u0016\u00a2\u0006\u0004\b(\u0010)BK\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010$\u001a\u00020#\u0012\u0006\u0010\u001b\u001a\u00020\u0004\u0012\u0006\u0010\n\u001a\u00020\u0004\u0012\f\u0010!\u001a\b\u0012\u0004\u0012\u00020 0\u0015\u0012\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015\u0012\u0006\u0010\u001e\u001a\u00020\u0004\u00a2\u0006\u0004\b(\u0010*J\u001a\u0010\u0005\u001a\u00020\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\"\u0010\n\u001a\u00020\u00048\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0011\u001a\u00020\u00108\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R \u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\"\u0010\u001b\u001a\u00020\u00048\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u000b\u001a\u0004\b\u001c\u0010\r\"\u0004\b\u001d\u0010\u000fR\u001a\u0010\u001e\u001a\u00020\u00048\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001e\u0010\u000b\u001a\u0004\b\u001f\u0010\rR \u0010!\u001a\b\u0012\u0004\u0012\u00020 0\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\u0018\u001a\u0004\b\"\u0010\u001aR\u001a\u0010$\u001a\u00020#8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/variants/LevelUpEvolution;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/PassiveEvolution;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "consumeHeldItem", "Z", "getConsumeHeldItem", "()Z", "setConsumeHeldItem", "(Z)V", "", "id", "Ljava/lang/String;", "getId", "()Ljava/lang/String;", "", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "learnableMoves", "Ljava/util/Set;", "getLearnableMoves", "()Ljava/util/Set;", "optional", "getOptional", "setOptional", "permanent", "getPermanent", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "requirements", "getRequirements", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "result", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getResult", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "<init>", "()V", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;ZZLjava/util/Set;Ljava/util/Set;Z)V", "Companion", "common"})
public class LevelUpEvolution
implements PassiveEvolution {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final String id;
    @NotNull
    private final PokemonProperties result;
    private boolean optional;
    private boolean consumeHeldItem;
    @NotNull
    private final Set<EvolutionRequirement> requirements;
    @NotNull
    private final Set<MoveTemplate> learnableMoves;
    private final boolean permanent;
    @NotNull
    public static final String ADAPTER_VARIANT = "level_up";
    @NotNull
    public static final String ALTERNATIVE_ADAPTER_VARIANT = "passive";

    public LevelUpEvolution(@NotNull String id, @NotNull PokemonProperties result, boolean optional, boolean consumeHeldItem, @NotNull Set<EvolutionRequirement> requirements, @NotNull Set<MoveTemplate> learnableMoves, boolean permanent) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)result, (String)"result");
        Intrinsics.checkNotNullParameter(requirements, (String)"requirements");
        Intrinsics.checkNotNullParameter(learnableMoves, (String)"learnableMoves");
        this.id = id;
        this.result = result;
        this.optional = optional;
        this.consumeHeldItem = consumeHeldItem;
        this.requirements = requirements;
        this.learnableMoves = learnableMoves;
        this.permanent = permanent;
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
    public boolean getPermanent() {
        return this.permanent;
    }

    public LevelUpEvolution() {
        this("id", new PokemonProperties(), true, true, new LinkedHashSet(), new LinkedHashSet(), false);
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof LevelUpEvolution && StringsKt.equals((String)((LevelUpEvolution)other).getId(), (String)this.getId(), (boolean)true);
    }

    public int hashCode() {
        int result = this.getId().hashCode();
        result = 31 * result + ADAPTER_VARIANT.hashCode();
        return result;
    }

    @Override
    public boolean attemptEvolution(@NotNull Pokemon pokemon) {
        return PassiveEvolution.DefaultImpls.attemptEvolution(this, pokemon);
    }

    @Override
    public boolean test(@NotNull Pokemon pokemon) {
        return PassiveEvolution.DefaultImpls.test(this, pokemon);
    }

    @Override
    public boolean evolve(@NotNull Pokemon pokemon) {
        return PassiveEvolution.DefaultImpls.evolve(this, pokemon);
    }

    @Override
    public void forceEvolve(@NotNull Pokemon pokemon) {
        PassiveEvolution.DefaultImpls.forceEvolve(this, pokemon);
    }

    @Override
    public void evolutionMethod(@NotNull Pokemon pokemon) {
        PassiveEvolution.DefaultImpls.evolutionMethod(this, pokemon);
    }

    @Override
    public void applyTo(@NotNull Pokemon pokemon) {
        PassiveEvolution.DefaultImpls.applyTo(this, pokemon);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/variants/LevelUpEvolution$Companion;", "", "", "ADAPTER_VARIANT", "Ljava/lang/String;", "ALTERNATIVE_ADAPTER_VARIANT", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

