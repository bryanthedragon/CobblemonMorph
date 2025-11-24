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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.starter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\b\u00a2\u0006\u0004\b#\u0010$J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ.\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\bH\u00c6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001a\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0016\u001a\u00020\u0015H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0019\u001a\u00020\u0018H\u00d6\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aR\u0017\u0010\u000b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u001b\u001a\u0004\b\u001c\u0010\u0004R\"\u0010\r\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010\u001d\u001a\u0004\b\u001e\u0010\n\"\u0004\b\u001f\u0010 R\u0017\u0010\f\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\f\u0010!\u001a\u0004\b\"\u0010\u0007\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/api/events/starter/StarterChosenEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lnet/minecraft/server/level/ServerPlayer;", "component1", "()Lnet/minecraft/server/level/ServerPlayer;", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "component2", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "component3", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "player", "properties", "pokemon", "copy", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/events/starter/StarterChosenEvent;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "setPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getProperties", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "common"})
public final class StarterChosenEvent
extends Cancelable {
    @NotNull
    private final ServerPlayer player;
    @NotNull
    private final PokemonProperties properties;
    @NotNull
    private Pokemon pokemon;

    public StarterChosenEvent(@NotNull ServerPlayer player, @NotNull PokemonProperties properties2, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.player = player;
        this.properties = properties2;
        this.pokemon = pokemon;
    }

    @NotNull
    public final ServerPlayer getPlayer() {
        return this.player;
    }

    @NotNull
    public final PokemonProperties getProperties() {
        return this.properties;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    public final void setPokemon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"<set-?>");
        this.pokemon = pokemon;
    }

    @NotNull
    public final ServerPlayer component1() {
        return this.player;
    }

    @NotNull
    public final PokemonProperties component2() {
        return this.properties;
    }

    @NotNull
    public final Pokemon component3() {
        return this.pokemon;
    }

    @NotNull
    public final StarterChosenEvent copy(@NotNull ServerPlayer player, @NotNull PokemonProperties properties2, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return new StarterChosenEvent(player, properties2, pokemon);
    }

    public static /* synthetic */ StarterChosenEvent copy$default(StarterChosenEvent starterChosenEvent, ServerPlayer serverPlayer, PokemonProperties pokemonProperties, Pokemon pokemon, int n, Object object) {
        if ((n & 1) != 0) {
            serverPlayer = starterChosenEvent.player;
        }
        if ((n & 2) != 0) {
            pokemonProperties = starterChosenEvent.properties;
        }
        if ((n & 4) != 0) {
            pokemon = starterChosenEvent.pokemon;
        }
        return starterChosenEvent.copy(serverPlayer, pokemonProperties, pokemon);
    }

    @NotNull
    public String toString() {
        return "StarterChosenEvent(player=" + this.player + ", properties=" + this.properties + ", pokemon=" + this.pokemon + ")";
    }

    public int hashCode() {
        int result = this.player.hashCode();
        result = result * 31 + this.properties.hashCode();
        result = result * 31 + this.pokemon.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StarterChosenEvent)) {
            return false;
        }
        StarterChosenEvent starterChosenEvent = (StarterChosenEvent)other;
        if (!Intrinsics.areEqual((Object)this.player, (Object)starterChosenEvent.player)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.properties, (Object)starterChosenEvent.properties)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.pokemon, (Object)starterChosenEvent.pokemon);
    }
}

