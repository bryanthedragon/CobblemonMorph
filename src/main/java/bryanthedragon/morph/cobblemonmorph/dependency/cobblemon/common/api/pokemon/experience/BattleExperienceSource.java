/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceSource;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0004\b\r\u0010\u000eR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/experience/BattleExperienceSource;", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "facedPokemon", "Ljava/util/List;", "getFacedPokemon", "()Ljava/util/List;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Ljava/util/List;)V", "common"})
public class BattleExperienceSource
implements ExperienceSource {
    @NotNull
    private final PokemonBattle battle;
    @NotNull
    private final List<BattlePokemon> facedPokemon;

    public BattleExperienceSource(@NotNull PokemonBattle battle2, @NotNull List<? extends BattlePokemon> facedPokemon) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter(facedPokemon, (String)"facedPokemon");
        this.battle = battle2;
        this.facedPokemon = facedPokemon;
    }

    @NotNull
    public final PokemonBattle getBattle() {
        return this.battle;
    }

    @NotNull
    public final List<BattlePokemon> getFacedPokemon() {
        return this.facedPokemon;
    }

    @Override
    public boolean isBattle() {
        return ExperienceSource.DefaultImpls.isBattle(this);
    }

    @Override
    public boolean isInteraction() {
        return ExperienceSource.DefaultImpls.isInteraction(this);
    }

    @Override
    public boolean isCommand() {
        return ExperienceSource.DefaultImpls.isCommand(this);
    }

    @Override
    public boolean isSidemod() {
        return ExperienceSource.DefaultImpls.isSidemod(this);
    }
}

