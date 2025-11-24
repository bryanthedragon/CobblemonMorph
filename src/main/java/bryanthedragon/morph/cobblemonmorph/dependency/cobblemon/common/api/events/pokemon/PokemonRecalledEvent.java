/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J&\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0013H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0019\u0010\t\u001a\u0004\u0018\u00010\u00058\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0016\u001a\u0004\b\u0017\u0010\u0007R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0018\u001a\u0004\b\u0019\u0010\u0004\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/PokemonRecalledEvent;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "component1", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "component2", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemon", "oldEntity", "copy", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Lcom/cobblemon/mod/common/api/events/pokemon/PokemonRecalledEvent;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getOldEntity", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "common"})
public final class PokemonRecalledEvent {
    @NotNull
    private final Pokemon pokemon;
    @Nullable
    private final PokemonEntity oldEntity;

    public PokemonRecalledEvent(@NotNull Pokemon pokemon, @Nullable PokemonEntity oldEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.pokemon = pokemon;
        this.oldEntity = oldEntity;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    @Nullable
    public final PokemonEntity getOldEntity() {
        return this.oldEntity;
    }

    @NotNull
    public final Pokemon component1() {
        return this.pokemon;
    }

    @Nullable
    public final PokemonEntity component2() {
        return this.oldEntity;
    }

    @NotNull
    public final PokemonRecalledEvent copy(@NotNull Pokemon pokemon, @Nullable PokemonEntity oldEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return new PokemonRecalledEvent(pokemon, oldEntity);
    }

    public static /* synthetic */ PokemonRecalledEvent copy$default(PokemonRecalledEvent pokemonRecalledEvent, Pokemon pokemon, PokemonEntity pokemonEntity, int n, Object object) {
        if ((n & 1) != 0) {
            pokemon = pokemonRecalledEvent.pokemon;
        }
        if ((n & 2) != 0) {
            pokemonEntity = pokemonRecalledEvent.oldEntity;
        }
        return pokemonRecalledEvent.copy(pokemon, pokemonEntity);
    }

    @NotNull
    public String toString() {
        return "PokemonRecalledEvent(pokemon=" + this.pokemon + ", oldEntity=" + this.oldEntity + ")";
    }

    public int hashCode() {
        int result = this.pokemon.hashCode();
        result = result * 31 + (this.oldEntity == null ? 0 : this.oldEntity.hashCode());
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PokemonRecalledEvent)) {
            return false;
        }
        PokemonRecalledEvent pokemonRecalledEvent = (PokemonRecalledEvent)other;
        if (!Intrinsics.areEqual((Object)this.pokemon, (Object)pokemonRecalledEvent.pokemon)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.oldEntity, (Object)pokemonRecalledEvent.oldEntity);
    }
}

