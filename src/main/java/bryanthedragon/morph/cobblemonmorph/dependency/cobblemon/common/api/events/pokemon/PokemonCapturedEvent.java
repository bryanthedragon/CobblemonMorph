/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\b\u00a2\u0006\u0004\b \u0010!J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ.\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\bH\u00c6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001a\u0010\u0012\u001a\u00020\u00112\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0015\u001a\u00020\u0014H\u00d6\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0018\u001a\u00020\u0017H\u00d6\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019R\u0017\u0010\f\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u001a\u001a\u0004\b\u001b\u0010\u0007R\u0017\u0010\r\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001c\u001a\u0004\b\u001d\u0010\nR\u0017\u0010\u000b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u001e\u001a\u0004\b\u001f\u0010\u0004\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/PokemonCapturedEvent;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "component1", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lnet/minecraft/server/level/ServerPlayer;", "component2", "()Lnet/minecraft/server/level/ServerPlayer;", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "component3", "()Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "pokemon", "player", "pokeBallEntity", "copy", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;)Lcom/cobblemon/mod/common/api/events/pokemon/PokemonCapturedEvent;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "getPokeBallEntity", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;)V", "common"})
public final class PokemonCapturedEvent {
    @NotNull
    private final Pokemon pokemon;
    @NotNull
    private final ServerPlayer player;
    @NotNull
    private final EmptyPokeBallEntity pokeBallEntity;

    public PokemonCapturedEvent(@NotNull Pokemon pokemon, @NotNull ServerPlayer player, @NotNull EmptyPokeBallEntity pokeBallEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokeBallEntity, (String)"pokeBallEntity");
        this.pokemon = pokemon;
        this.player = player;
        this.pokeBallEntity = pokeBallEntity;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    @NotNull
    public final ServerPlayer getPlayer() {
        return this.player;
    }

    @NotNull
    public final EmptyPokeBallEntity getPokeBallEntity() {
        return this.pokeBallEntity;
    }

    @NotNull
    public final Pokemon component1() {
        return this.pokemon;
    }

    @NotNull
    public final ServerPlayer component2() {
        return this.player;
    }

    @NotNull
    public final EmptyPokeBallEntity component3() {
        return this.pokeBallEntity;
    }

    @NotNull
    public final PokemonCapturedEvent copy(@NotNull Pokemon pokemon, @NotNull ServerPlayer player, @NotNull EmptyPokeBallEntity pokeBallEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokeBallEntity, (String)"pokeBallEntity");
        return new PokemonCapturedEvent(pokemon, player, pokeBallEntity);
    }

    public static /* synthetic */ PokemonCapturedEvent copy$default(PokemonCapturedEvent pokemonCapturedEvent, Pokemon pokemon, ServerPlayer serverPlayer, EmptyPokeBallEntity emptyPokeBallEntity, int n, Object object) {
        if ((n & 1) != 0) {
            pokemon = pokemonCapturedEvent.pokemon;
        }
        if ((n & 2) != 0) {
            serverPlayer = pokemonCapturedEvent.player;
        }
        if ((n & 4) != 0) {
            emptyPokeBallEntity = pokemonCapturedEvent.pokeBallEntity;
        }
        return pokemonCapturedEvent.copy(pokemon, serverPlayer, emptyPokeBallEntity);
    }

    @NotNull
    public String toString() {
        return "PokemonCapturedEvent(pokemon=" + this.pokemon + ", player=" + this.player + ", pokeBallEntity=" + this.pokeBallEntity + ")";
    }

    public int hashCode() {
        int result = this.pokemon.hashCode();
        result = result * 31 + this.player.hashCode();
        result = result * 31 + this.pokeBallEntity.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PokemonCapturedEvent)) {
            return false;
        }
        PokemonCapturedEvent pokemonCapturedEvent = (PokemonCapturedEvent)other;
        if (!Intrinsics.areEqual((Object)this.pokemon, (Object)pokemonCapturedEvent.pokemon)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.player, (Object)pokemonCapturedEvent.player)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.pokeBallEntity, (Object)pokemonCapturedEvent.pokeBallEntity);
    }
}

