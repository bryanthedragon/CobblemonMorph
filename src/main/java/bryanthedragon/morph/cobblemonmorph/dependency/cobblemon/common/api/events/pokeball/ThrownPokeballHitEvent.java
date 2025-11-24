/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/events/pokeball/ThrownPokeballHitEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "pokeBall", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "getPokeBall", "()Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemon", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getPokemon", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "<init>", "(Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "common"})
public final class ThrownPokeballHitEvent
extends Cancelable {
    @NotNull
    private final EmptyPokeBallEntity pokeBall;
    @NotNull
    private final PokemonEntity pokemon;

    public ThrownPokeballHitEvent(@NotNull EmptyPokeBallEntity pokeBall, @NotNull PokemonEntity pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokeBall, (String)"pokeBall");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.pokeBall = pokeBall;
        this.pokemon = pokemon;
    }

    @NotNull
    public final EmptyPokeBallEntity getPokeBall() {
        return this.pokeBall;
    }

    @NotNull
    public final PokemonEntity getPokemon() {
        return this.pokemon;
    }
}

